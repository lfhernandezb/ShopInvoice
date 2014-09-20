import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.ini4j.Wini;

import cl.nic.dte.util.Utilities;
import cl.sii.siiDte.AUTORIZACIONDocument;
import cl.sii.siiDte.AutorizacionType;
import cl.sii.siiDte.DTEDocument;
import cl.sii.siiDte.FechaType;
import cl.sii.siiDte.MedioPagoType;
import cl.sii.siiDte.DTEDefType.Documento.Detalle;
import cl.sii.siiDte.DTEDefType.Documento.Encabezado.IdDoc;
import cl.sii.siiDte.DTEDefType.Documento.Encabezado.Receptor;
import cl.sii.siiDte.DTEDefType.Documento.Encabezado.Totales;

import bd.Folio;
import bd.Order;
import bd.OrderDetail;
import bd.OrderInvoice;


/**
 * 
 */

/**
 * @author Owner
 *
 */
public class ShopInvoice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		if (args.length != 3) {
			System.err
					.println("Utilice: java ShopInvoice <template.xml> <certDigital.p12> <password>");
			System.exit(-1);
		}

		X509Certificate cert;
		PrivateKey key;

		AutorizacionType caf;
		DTEDocument doc;
		
		Logger logger = Logger.getLogger(ShopInvoice.class.getName());

		String config_file_name;
		java.sql.Connection conn;
		Wini ini;
		Folio folio = null;
		
		conn = null;

		ArrayList<AbstractMap.SimpleEntry<String, String>> listParameters;
		ArrayList<OrderInvoice> listaOrderInvoice;
		ArrayList<OrderDetail> listaOrderDetail;

		try {
			
        	// leo archivo de configuracion
        	
        	ini = new Wini();
        	
        	config_file_name = System.getProperty("config_file");
        	
        	File f = new File(config_file_name);
        	
        	if (!f.exists()) {
        		throw new Exception("Config file does not exists");
        	}
        	
        	ini.load(new File(config_file_name));
        	
        	// abro conexion a la BD
        	Class.forName("com.mysql.jdbc.Driver");
        	conn = DriverManager.getConnection("jdbc:mysql://" + ini.get("DB", "host") + "/" + ini.get("DB", "database"), 
        			ini.get("DB", "user"), ini.get("DB", "password"));
        	
        	conn.setAutoCommit(false);
        	
        	logger.debug("conn: " + conn);

        	// obtengo las ordenes no facturadas, es decir, los registros en 'order_invoice' que no estan en 'dte_order_invoice'
        	
    		listParameters = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
    		
			listParameters.clear();
			
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("sin dte", ""));
			
			listaOrderInvoice = OrderInvoice.seek(conn, listParameters, null, null, 0, 10);
			
			if (!listaOrderInvoice.isEmpty()) {
				// ok
				Order order;
				
				for (OrderInvoice oi : listaOrderInvoice) {
					
		        	folio = Folio.getNextFree(conn, (long) 33);
		        	
		        	logger.debug(folio.toString());
		        	
		    		// Leo Autorizacion
		    		// Debo meter el namespace porque SII no lo genera
		    		HashMap<String, String> namespaces = new HashMap<String, String>();
		    		namespaces.put("", "http://www.sii.cl/SiiDte");
		    		XmlOptions opts = new XmlOptions();
		    		opts.setLoadSubstituteNamespaces(namespaces);

		    		caf = AUTORIZACIONDocument.Factory.parse(folio.get_caf(), opts)
		    				.getAUTORIZACION();

		    		// Construyo base a partir del template
		    		doc = DTEDocument.Factory.parse(new File(args[0]));

		    		// leo certificado y llave privada del archivo pkcs12
		    		KeyStore ks = KeyStore.getInstance("PKCS12");
		    		ks.load(new FileInputStream(args[1]), args[2].toCharArray());
		    		String alias = ks.aliases().nextElement();
		    		System.out.println("Usando certificado " + alias
		    				+ " del archivo PKCS12: " + args[1]);

		    		cert = (X509Certificate) ks.getCertificate(alias);
		    		key = (PrivateKey) ks.getKey(alias, args[2].toCharArray());

		    		// Agrego al doc datos desde prestashop

		    		// IdDoc
		    		IdDoc iddoc = doc.getDTE().getDocumento().getEncabezado().addNewIdDoc();
		    		iddoc.setFolio(folio.get_numero());
		    		iddoc.setTipoDTE(BigInteger.valueOf(33));
		    		doc.getDTE().getDocumento().setID("N_" + caf.getCAF().getDA().getRE() + "_" + iddoc.getTipoDTE() + "_" + iddoc.getFolio());	    		

		    		iddoc.xsetFchEmis(FechaType.Factory.newValue(Utilities.fechaFormat
		    				.format(new Date())));

		    		iddoc.setIndServicio(BigInteger.valueOf(3));
		    		iddoc.setFmaPago(BigInteger.valueOf(1));

		    		Calendar cal = Calendar.getInstance();
		    		cal.add(Calendar.DAY_OF_MONTH, 45);
		    		iddoc.xsetFchCancel(FechaType.Factory.newValue(Utilities.fechaFormat
		    				.format(new Date())));

		    		iddoc.setMedioPago(MedioPagoType.Enum.forString("LT"));
		    		iddoc.setFmaPago(BigInteger.valueOf(2));
		    		
		    		// Receptor
		    		Receptor recp = doc.getDTE().getDocumento().getEncabezado().getReceptor();
		    				//.addNewReceptor();
		    		recp.setRUTRecep("60803000-K");
		    		recp.setRznSocRecep("Servicio de Impuestos Internos");
		    		recp.setGiroRecep("GOBIERNO CENTRAL Y ADMINISTRACION PUB.");
		    		recp.setContacto("Director Impuestos Internos");
		    		recp.setDirRecep("Teatinos 120");
		    		recp.setCmnaRecep("Santiago");
		    		recp.setCiudadRecep("Santiago");
					
		    		
		    		// Totales
		    		// obtengo totales desde 'orders'
		    		
		    		order = Order.getById(conn, oi.get_id_order().toString());
		    		
		    		Totales tot = doc.getDTE().getDocumento().getEncabezado().getTotales();
		    				//.addNewTotales();
		    		tot.setMntNeto(order.get_total_products().longValue());
		    		tot.setTasaIVA(BigDecimal.valueOf(order.get_carrier_tax_rate().longValue()));
		    		tot.setIVA(tot.getMntNeto() * tot.getTasaIVA().longValue() / 100);
		    		tot.setMntTotal(tot.getMntNeto() + tot.getIVA());

		    		// Agrego detalles
		    		// obtengo detalle desde 'order_detail'
		    		
					listParameters.clear();
					
					listParameters.add(new AbstractMap.SimpleEntry<String, String>("id_order", oi.get_id_order().toString()));
					
					listaOrderDetail = OrderDetail.seek(conn, listParameters, null, null, 0, 10);
					
					if (listaOrderDetail.size() == 0) {
						logger.debug("Orden " + oi.get_id_order().toString() + " no tiene itemes");
						continue;
					}
					
					Detalle[] det = new Detalle[listaOrderDetail.size()];
					
					int i = 0;
					
					for (OrderDetail od : listaOrderDetail) {
						
			    		
			    		det[i] = Detalle.Factory.newInstance();
			    		det[i].setNroLinDet(i + 1);
			    		det[i].setNmbItem(od.get_product_reference() + "\t" + StringEscapeUtils.escapeHtml3(od.get_product_name()));
			    		det[i].setQtyItem(BigDecimal.valueOf(od.get_product_quantity()));
			    		det[i].setPrcItem(BigDecimal.valueOf(od.get_product_price().longValue()));
			    		det[i].setMontoItem(det[i].getQtyItem().longValue() * det[i].getPrcItem().longValue());
			    		
			    		i++;
					}

		    		doc.getDTE().getDocumento().setDetalleArray(det);
		    		
					
		    		
		    		
		    		
		    		
		    		
		    		
		    		
		    		// Timbro

		    		doc.getDTE().timbrar(caf.getCAF(), caf.getPrivateKey(null));

		    		// antes de firmar le doy formato a los datos
		    		opts = new XmlOptions();
		    		opts.setSaveImplicitNamespaces(namespaces);
		    		opts.setLoadSubstituteNamespaces(namespaces);
		    		opts.setSavePrettyPrint();
		    		opts.setSavePrettyPrintIndent(0);

		    		// releo el doc para que se reflejen los cambios de formato
		    		doc = DTEDocument.Factory.parse(doc.newInputStream(opts), opts);

		    		// firmo
		    		doc.getDTE().sign(key, cert);

		    		// Guardo
		    		opts = new XmlOptions();
		    		opts.setCharacterEncoding("ISO-8859-1");
		    		opts.setSaveImplicitNamespaces(namespaces);
		    		//doc.save(System.out, opts);
		    		String strFileHeader = "dte_" + folio.get_tipo().toString() + "_" + folio.get_numero().toString();
		    		doc.save(new File(strFileHeader + ".xml"), opts);
		    		
		    		Utilities.generatePDF(new FileInputStream(strFileHeader + ".xml"), 
		    			new FileInputStream("plantilla_pdf.xsl"), new FileOutputStream(strFileHeader + ".pdf"));
		    		
				}
				
			}
			else {
				logger.debug("No hay ordenes para procesar");
			}

        	
        	

        	

        	
        	conn.commit();
        	
    		conn.close();
    		
        	

        }
		catch (SQLException ex) {
			// TODO Auto-generated catch block
			
        	logger.debug("SQLException: " + ex.getMessage());
        	logger.debug("SQLState: " + ex.getSQLState());
        	logger.debug("VendorError: " + ex.getErrorCode());
			ex.printStackTrace();
			
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
        catch (Exception ex) {
        	logger.debug("Exception: " + ex.getMessage());
        	ex.printStackTrace();
        }
        
        
	}

}
