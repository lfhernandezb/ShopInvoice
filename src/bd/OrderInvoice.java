package bd;

//import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class OrderInvoice
{
	private Long _id;
	private Long _id_order;
	private Long _number;
	private String _date_add;
	
	private final static String _str_sql = 
			"  SELECT oi.id_order_invoice AS id, oi.id_order AS id_order, oi.number AS number, " +
			"  DATE_FORMAT(oi.date_add, '%d-%m-%Y') AS date_add" +
		 	"  FROM order_invoice oi" +
		 	"  LEFT JOIN dte_order_invoice doi ON doi.id_order_invoice_FK = oi.id_order_invoice";	 	

	public OrderInvoice() {
		_id = null;
		_id_order = null;
		_number = null;
		_date_add = null;
	}

	/**
	 * @return the _id
	 */
	public Long get_id() {
		return _id;
	}

	/**
	 * @return the _id_order
	 */
	public Long get_id_order() {
		return _id_order;
	}

	/**
	 * @return the _number
	 */
	public Long get_number() {
		return _number;
	}

	/**
	 * @return the _date_add
	 */
	public String get_date_add() {
		return _date_add;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(Long _id) {
		this._id = _id;
	}
	
	/**
	 * @param _id_order the _id_order to set
	 */
	public void set_id_order(Long _id_order) {
		this._id_order = _id_order;
	}

	/**
	 * @param _number the _number to set
	 */
	public void set_number(Long _number) {
		this._number = _number;
	}

	/**
	 * @param _date_add the _date_add to set
	 */
	public void set_date_add(String _date_add) {
		this._date_add = _date_add;
	}

	public static OrderInvoice fromRS(ResultSet p_rs) throws SQLException {
		OrderInvoice ret = new OrderInvoice();
		
		try {
			ret.set_id(p_rs.getLong("id"));
			ret.set_id_order(p_rs.getLong("id_order"));
			ret.set_number(p_rs.getLong("number"));
			ret.set_date_add(p_rs.getString("date_add"));
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
	
	public static OrderInvoice getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		OrderInvoice ret = null;
		
		String str_sql = _str_sql +
			"  WHERE oi." + p_key + " = " + p_value +
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
			// in reverse-order_invoice of their creation
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
	
	public static OrderInvoice getByAlias(Connection p_conn, String p_number) throws Exception {
		return getByParameter(p_conn, "number", "'" + p_number + "'");
	}
	
	public static OrderInvoice getByEmail(Connection p_conn, String p_total_paid_tax_excl) throws Exception {
		return getByParameter(p_conn, "total_paid_tax_excl", "'" + p_total_paid_tax_excl + "'");
	}
	
	public static OrderInvoice getByMovil(Connection p_conn, String p_total_paid) throws Exception {
		return getByParameter(p_conn, "total_paid", "'" + p_total_paid + "'");
	}

	public static OrderInvoice getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_order_invoice", p_id);
	}
	
    public static ArrayList<OrderInvoice> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order_invoice, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<OrderInvoice> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<OrderInvoice>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("sin dte")) {
					array_clauses.add("doi.id_dte_FK IS NULL");
				}
				else if (p.getKey().equals("con dte")) {
					array_clauses.add("doi.id_dte_FK IS NOT NULL");
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
			
	        if (p_order_invoice != null && p_direction != null) {
	        	str_sql += " ORDER BY " + p_order_invoice + " " + p_direction;
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
			// in reverse-order_invoice of their creation
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
			"  UPDATE order_invoice" +
			"  SET number = " + (_number != null ? "'" + _number + "'" : "null") + "," +
			"  date_add = " + (_date_add != null ? "'" + _date_add + "'" : "null") + "," +
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
			"  WHERE id_order_invoice = " + Integer.toString(this._id);
		
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
			// in reverse-order_invoice of their creation
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
	
	public int insert(Connection p_conn) throws Exception {
		
    	int ret = -1;
		Statement stmt = null;
    	ResultSet rs = null;

    	String str_sql =
			"  INSERT INTO order_invoice" +
			"  (" +
			"  id_order_FK," +
			"  number," +
			"  date_add," +
			"  total_discounts," +
			"  total_discounts_tax_incl," +
			"  total_discounts_tax_excl," +
			"  total_paid," +
			"  total_paid_tax_incl," +
			"  total_paid_tax_excl," +
			"  total_products," +
			"  carrier_tax_rate," +
			"  foto," +
			"  antecedentes_emergencia" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_id_order != null ? "'" + _id_order + "'" : "null") + "," +
			"  " + (_number != null ? "'" + _number + "'" : "null") + "," +
			"  " + (_date_add != null ? "'" + _date_add + "'" : "null") + "," +
			"  " + (_total_discounts != null ? "'" + _total_discounts + "'" : "null") + "," +
			"  " + (_total_discounts_tax_incl != null ? "'" + _total_discounts_tax_incl + "'" : "null") + "," +
			"  " + (_total_discounts_tax_excl != null ? "'" + _total_discounts_tax_excl + "'" : "null") + "," +
			"  " + (_total_paid != null ? "'" + _total_paid + "'" : "null") + "," +
			"  " + (_total_paid_tax_incl != null ? "'" + _total_paid_tax_incl + "'" : "null") + "," +
			"  " + (_total_paid_tax_excl != null ? "'" + _total_paid_tax_excl + "'" : "null") + "," +
			"  " + (_total_products != null ? "STR_TO_DATE('" + _total_products + "', '%d-%m-%Y')" : "null") + "," +
			"  " + (_carrier_tax_rate != null ? "'" + _carrier_tax_rate + "'" : "null") + "," +
			"  " + (_foto != null ? "'" + _foto + "'" : "null") + "," +
			"  " + (_antecedentes_emergencia != null ? "'" + _antecedentes_emergencia + "'" : "null") +
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
			// in reverse-order_invoice of their creation
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
	*/
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderInvoice [_id=" + _id + ", _number=" + _number + "]";
	}
	
}