package bd;

//import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class DteOrderInvoice
{
	private Integer _id;
	private Integer _id_dte;
	private Long _id_order_invoice;
	
	private final static String _str_sql = 
			"  SELECT doi.id_dte_order_invoice AS id, doi.id_dte_FK AS id_dte, doi.id_order_invoice_FK AS id_order_invoice" +
		 	"  FROM dte_dte_order_invoice_invoice doi";	 	

	public DteOrderInvoice() {
		_id = null;
		_id_dte = null;
		_id_order_invoice = null;
	}

	/**
	 * @return the _id
	 */
	public Integer get_id() {
		return _id;
	}

	/**
	 * @return the _id_dte
	 */
	public Integer get_id_dte() {
		return _id_dte;
	}

	/**
	 * @return the _id_order_invoice
	 */
	public Long get_id_order_invoice() {
		return _id_order_invoice;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(Integer _id) {
		this._id = _id;
	}
	
	/**
	 * @param _id_dte the _id_dte to set
	 */
	public void set_id_dte(Integer _id_dte) {
		this._id_dte = _id_dte;
	}

	/**
	 * @param _id_order_invoice the _id_order_invoice to set
	 */
	public void set_id_order_invoice(Long _id_order_invoice) {
		this._id_order_invoice = _id_order_invoice;
	}

	public static DteOrderInvoice fromRS(ResultSet p_rs) throws SQLException {
		DteOrderInvoice ret = new DteOrderInvoice();
		
		try {
			ret.set_id(p_rs.getInt("id"));
			ret.set_id_dte(p_rs.getInt("id_dte"));
			ret.set_id_order_invoice(p_rs.getLong("id_order_invoice"));
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
			throw ex;
		}
						
		return ret;
	}
    
	
	/*
	public static function seek(p_param) {
		global fc;
		
		str_sql =
			"  SELECT r.id_repuesto as id, p.descripcion as plataforma, f.descripcion as fabricante, m.descripcion as modelo, re.descripcion as radio_estacion, c.descripcion as ciudad, rg.descripcion as region" +
		 	"  FROM repuesto r" +
			"  JOIN plataforma p ON p.id_plataforma = r.id_plataforma_FK" +
			"  JOIN radio_estacion re ON re.id_radio_estacion = r.id_radio_estacion_FK" +
		    "  JOIN ciudad c ON c.id_ciudad = re.id_ciudad_FK" +
		    "  JOIN region rg ON rg.id_region = c.id_region_FK" +
			"  JOIN modelo m ON m.id_modelo = r.id_modelo_FK" +
			"  JOIN fabricante f ON f.id_fabricante = m.id_fabricante_FK" +
			"  WHERE f.descripcion LIKE "%p_param%"" +
			"  OR m.descripcion LIKE "%p_param%"" +
			"  OR r.descripcion LIKE "%p_param%"" +
			"  OR p.descripcion LIKE "%p_param%"" +
			"  AND r.ubicacion IS NULL";
		
		// echo str_sql . "<br>";
		
		return fc.getLink().QueryArray(str_sql, MYSQL_ASSOC);
	}
	*/
	
	public static DteOrderInvoice getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		DteOrderInvoice ret = null;
		
		String str_sql = _str_sql +
			"  WHERE doi." + p_key + " = " + p_value +
			"  LIMIT 0, 1";
		
		//System.out.println(str_sql);
		
		// assume that conn is an already created JDBC connection (see previous examples)
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = p_conn.createStatement();
			//System.out.println("stmt = p_conn.createStatement() ok");
			rs = stmt.executeQuery(str_sql);
			//System.out.println("rs = stmt.executeQuery(str_sql) ok");

			// Now do something with the ResultSet ....
			
			if (rs.next()) {
				//System.out.println("rs.next() ok");
				ret = fromRS(rs);
				//System.out.println("fromRS(rs) ok");
			}
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
			throw new Exception("Error al obtener registro");
		}
		catch (Exception e){
			// handle any errors
			throw new Exception("Excepcion del tipo " + e.getClass() + " Info: " + e.getMessage());
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-dte_order_invoice of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { 
					
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					
				} // ignore
				stmt = null;
			}
		}		
		
		return ret;		
	}
	
	public static DteOrderInvoice getByAlias(Connection p_conn, String p_id_order_invoice) throws Exception {
		return getByParameter(p_conn, "id_order_invoice", "'" + p_id_order_invoice + "'");
	}
	
	public static DteOrderInvoice getByEmail(Connection p_conn, String p_total_paid_tax_excl) throws Exception {
		return getByParameter(p_conn, "total_paid_tax_excl", "'" + p_total_paid_tax_excl + "'");
	}
	
	public static DteOrderInvoice getByMovil(Connection p_conn, String p_total_paid) throws Exception {
		return getByParameter(p_conn, "total_paid", "'" + p_total_paid + "'");
	}

	public static DteOrderInvoice getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_dte_order_invoice", p_id);
	}
	
    public static ArrayList<DteOrderInvoice> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_dte_order_invoice, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<DteOrderInvoice> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<DteOrderInvoice>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("id")) {
					array_clauses.add("doi.id_dte_order_ivoice = " + p.getValue());
				}
				else if (p.getKey().equals("id_dte")) {
					array_clauses.add("doi.id_dte_FK = " + p.getValue());
				}
				else if (p.getKey().equals("id_order_invoice")) {
					array_clauses.add("doi.id_order_invoice_FK = " + p.getValue());
				}
				else {
					throw new Exception("Parametro  no soportado: " + p.getKey());
				}
			}
								
	        boolean bFirstTime = false;
	        
	        for(String clause : array_clauses) {
	            if (!bFirstTime) {
	                 bFirstTime = true;
	                 str_sql += " WHERE ";
	            }
	            else {
	                 str_sql += " AND ";
	            }
	            str_sql += clause;
	        }
			
	        if (p_dte_order_invoice != null && p_direction != null) {
	        	str_sql += " ORDER BY " + p_dte_order_invoice + " " + p_direction;
	        }
	        
	        if (p_offset != -1 && p_limit != -1) {
	        	str_sql += "  LIMIT " +  Integer.toString(p_offset) + ", " + Integer.toString(p_limit);
	        }
			
	        //echo "<br>" . str_sql . "<br>";
		
			stmt = p_conn.createStatement();
			
			rs = stmt.executeQuery(str_sql);
			
			while (rs.next()) {
				ret.add(fromRS(rs));
			}
			/*
			if (ret.size() == 0) {
				ret = null;
			}
			*/
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
			throw ex;
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-dte_order_invoice of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { 
					
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					
				} // ignore
				stmt = null;
			}
		}		

		return ret;
	}
    /*
    public int update(Connection p_conn) throws Exception {

    	int ret = -1;
    	Statement stmt = null;

    	String str_sql =
			"  UPDATE dte_dte_order_invoice_invoice" +
			"  SET id_order_invoice = " + (_id_order_invoice != null ? "'" + _id_order_invoice + "'" : "null") + "," +
			"  payment = " + (_payment != null ? "'" + _payment + "'" : "null") + "," +
			"  total_discounts = " + (_total_discounts != null ? "'" + _total_discounts + "'" : "null") + "," +
			"  total_discounts_tax_incl = " + (_total_discounts_tax_incl != null ? "'" + _total_discounts_tax_incl + "'" : "null") + "," +
			"  total_discounts_tax_excl = " + (_total_discounts_tax_excl != null ? "'" + _total_discounts_tax_excl + "'" : "null") + "," +
			"  total_paid = " + (_total_paid != null ? "'" + _total_paid + "'" : "null") + "," +
			"  total_paid_tax_incl = " + (_total_paid_tax_incl != null ? "'" + _total_paid_tax_incl + "'" : "null") + "," +
			"  total_paid_tax_excl = " + (_total_paid_tax_excl != null ? "'" + _total_paid_tax_excl + "'" : "null") + "," +
			"  total_products = " + (_total_products != null ? "STR_TO_DATE('" + _total_products + "', '%d-%m-%Y')" : "null") + "," +
			"  carrier_tax_rate = " + (_carrier_tax_rate != null ? "'" + _carrier_tax_rate + "'" : "null") + "," +
			"  foto = " + (_foto != null ? "'" + _foto + "'" : "null") + "," +
			"  antecedentes_emergencia = " + (_antecedentes_emergencia != null ? "'" + _antecedentes_emergencia + "'" : "null") +
			"  WHERE id_dte_order_invoice = " + Integer.toString(this._id);
		
		try {
			stmt = p_conn.createStatement();
			
			ret = stmt.executeUpdate(str_sql);
			
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
			throw new Exception("Error al actualizar registro '" + str_sql + "'");
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-dte_order_invoice of their creation
			// if they are no-longer needed
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					
				} // ignore
				stmt = null;
			}
		}
		
		return ret;
	}
	*/
	public int insert(Connection p_conn) throws Exception {
		
    	int ret = -1;
		Statement stmt = null;
    	ResultSet rs = null;

    	String str_sql =
			"  INSERT INTO dte_dte_order_invoice_invoice" +
			"  (" +
			"  id_dte_FK," +
			"  id_order_invoice_FK" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_id_dte != null ? "'" + _id_dte + "'" : "null") + "," +
			"  " + (_id_order_invoice != null ? "'" + _id_order_invoice + "'" : "null") +
			"  )";
		
		try {
			stmt = p_conn.createStatement();
			
			ret = stmt.executeUpdate(str_sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				_id = rs.getInt(1);
			} else {
				// throw an exception from here
				throw new Exception("Error al obtener id");
			}

			rs.close();
			rs = null;
			//System.out.println("Key returned from getGeneratedKeys():" + _id.toString());
			
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
			throw new Exception("Error al insertar registro '" + str_sql + "'");
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-dte_order_invoice of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { 
					
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					
				} // ignore
				stmt = null;
			}
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DteOrderInvoice [_id=" + _id + ", _id_order_invoice=" + _id_order_invoice + "]";
	}
	
}