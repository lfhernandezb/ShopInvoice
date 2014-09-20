import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;

import bd.CAF;
import bd.Folio;

import org.ini4j.Wini;

import cl.sii.siiDte.AUTORIZACIONDocument;
import cl.sii.siiDte.AutorizacionType;

/**
 * 
 */

/**
 * @author Owner
 *
 */
public class LoadCaf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length != 1) {
			System.err
					.println("Utilice: java LoadCaf " 
							+ "<caf.xml>");
			System.exit(-1);
		}

		Logger logger = Logger.getLogger(LoadCaf.class.getName());
		
		String config_file_name, caf_content;
		java.sql.Connection conn;
		Wini ini;
		
		AutorizacionType caf;
		ArrayList<AbstractMap.SimpleEntry<String, String>> listParameters;
		ArrayList<CAF> listaCAF;
		
		conn = null;
		
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
        	
        	
    		// Leo Autorizacion
    		// Debo meter el namespace porque SII no lo genera
    		HashMap<String, String> namespaces = new HashMap<String, String>();
    		namespaces.put("", "http://www.sii.cl/SiiDte");
    		XmlOptions opts = new XmlOptions();
    		opts.setLoadSubstituteNamespaces(namespaces);
    		
    		caf_content = IOUtils.toString(new FileInputStream(args[0]));

    		caf = AUTORIZACIONDocument.Factory.parse(caf_content, opts).getAUTORIZACION();
    		
    		logger.debug("RE: " + caf.getCAF().getDA().getRE());
    		logger.debug("TD: " + caf.getCAF().getDA().getTD());
    		logger.debug("RNG D: " + caf.getCAF().getDA().getRNG().getD());
    		logger.debug("RNG H: " + caf.getCAF().getDA().getRNG().getH());
    		
    		// esta ya este caf en la base de datos?
    		
    		listParameters = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
    		
			listParameters.clear();
			
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("rut_emisor", "'" + caf.getCAF().getDA().getRE() + "'"));
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("tipo", String.valueOf(caf.getCAF().getDA().getTD().toString())));
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("desde", String.valueOf(caf.getCAF().getDA().getRNG().getD())));
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("desde", String.valueOf(caf.getCAF().getDA().getRNG().getH())));
			
			listaCAF = CAF.seek(conn, listParameters, null, null, 0, 10);
			
			if (listaCAF.isEmpty()) {
				// ok, insertamos
				long i;
				Folio folio;
				CAF c = new CAF();
				
				c.set_rut_emisor(caf.getCAF().getDA().getRE());
				c.set_tipo(caf.getCAF().getDA().getTD());
				c.set_desde(caf.getCAF().getDA().getRNG().getD());
				c.set_hasta(caf.getCAF().getDA().getRNG().getH());
				c.set_contenido(caf_content);
				
				c.insert(conn);
				
				for (i = c.get_desde(); i <= c.get_hasta(); i++) {
					folio = new Folio();
					
					folio.set_id_caf(c.get_id());
					folio.set_tipo(c.get_tipo());
					folio.set_numero(i);
					
					folio.insert(conn);
				}
			}
			else {
				throw new Exception("El CAF ya esta en la base de datos!");
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
