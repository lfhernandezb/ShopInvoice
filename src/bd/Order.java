package bd;

//import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class Order
{
	private Long _id;
	private Long _id_customer;
	private Long _id_address_invoice;
	private String _payment;
	private Float _total_discounts;
	private Float _total_discounts_tax_incl;
	private Float _total_discounts_tax_excl;
	private Float _total_paid;
	private Float _total_paid_tax_incl;
	private Float _total_paid_tax_excl;
	private Float _total_products;
	private Float _carrier_tax_rate;
	
	private final static String _str_sql = 
			"  SELECT o.id_order AS id, o.id_customer AS id_customer, o.id_address_invoice AS id_address_invoice, o.payment AS payment, " +
			"  o.total_discounts AS total_discounts, o.total_discounts_tax_incl AS total_discounts_tax_incl, " +
			"  o.total_discounts_tax_excl AS total_discounts_tax_excl, o.total_paid AS total_paid, " +
			"  o.total_paid_tax_incl AS total_paid_tax_incl, o.total_paid_tax_excl AS total_paid_tax_excl, " +
			"  o.total_products AS total_products, o.carrier_tax_rate AS carrier_tax_rate" +
		 	"  FROM orders o";	 	

	public Order() {
		_id = null;
		_id_customer = null;
		_id_address_invoice = null;
		_payment = null;
		_total_discounts = null;
		_total_discounts_tax_incl = null;
		_total_discounts_tax_excl = null;
		_total_paid = null;
		_total_paid_tax_incl = null;
		_total_paid_tax_excl = null;
		_total_products = null;
		_carrier_tax_rate = null;
	}

	/**
	 * @return the _id
	 */
	public Long get_id() {
		return _id;
	}

	/**
	 * @return the _id_customer
	 */
	public Long get_id_customer() {
		return _id_customer;
	}

	/**
	 * @return the _id_address_invoice
	 */
	public Long get_id_address_invoice() {
		return _id_address_invoice;
	}

	/**
	 * @return the _payment
	 */
	public String get_payment() {
		return _payment;
	}

	/**
	 * @return the _total_discounts
	 */
	public Float get_total_discounts() {
		return _total_discounts;
	}

	/**
	 * @return the _total_discounts_tax_incl
	 */
	public Float get_total_discounts_tax_incl() {
		return _total_discounts_tax_incl;
	}

	/**
	 * @return the _total_discounts_tax_excl
	 */
	public Float get_total_discounts_tax_excl() {
		return _total_discounts_tax_excl;
	}

	/**
	 * @return the _total_paid
	 */
	public Float get_total_paid() {
		return _total_paid;
	}

	/**
	 * @return the _total_paid_tax_incl
	 */
	public Float get_total_paid_tax_incl() {
		return _total_paid_tax_incl;
	}

	/**
	 * @return the _total_paid_tax_excl
	 */
	public Float get_total_paid_tax_excl() {
		return _total_paid_tax_excl;
	}

	/**
	 * @return the _total_products
	 */
	public Float get_total_products() {
		return _total_products;
	}

	/**
	 * @return the _carrier_tax_rate
	 */
	public Float get_carrier_tax_rate() {
		return _carrier_tax_rate;
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(Long _id) {
		this._id = _id;
	}
	
	/**
	 * @param _id_customer the _id_customer to set
	 */
	public void set_id_customer(Long _id_customer) {
		this._id_customer = _id_customer;
	}

	/**
	 * @param _id_address_invoice the _id_address_invoice to set
	 */
	public void set_id_address_invoice(Long _id_address_invoice) {
		this._id_address_invoice = _id_address_invoice;
	}

	/**
	 * @param _payment the _payment to set
	 */
	public void set_payment(String _payment) {
		this._payment = _payment;
	}

	/**
	 * @param _total_discounts the _total_discounts to set
	 */
	public void set_total_discounts(Float _total_discounts) {
		this._total_discounts = _total_discounts;
	}

	/**
	 * @param _total_discounts_tax_incl the _total_discounts_tax_incl to set
	 */
	public void set_total_discounts_tax_incl(Float _total_discounts_tax_incl) {
		this._total_discounts_tax_incl = _total_discounts_tax_incl;
	}

	/**
	 * @param _total_discounts_tax_excl the _total_discounts_tax_excl to set
	 */
	public void set_total_discounts_tax_excl(Float _total_discounts_tax_excl) {
		this._total_discounts_tax_excl = _total_discounts_tax_excl;
	}

	/**
	 * @param _total_paid the _total_paid to set
	 */
	public void set_total_paid(Float _total_paid) {
		this._total_paid = _total_paid;
	}

	/**
	 * @param _total_paid_tax_incl the _total_paid_tax_incl to set
	 */
	public void set_total_paid_tax_incl(Float _total_paid_tax_incl) {
		this._total_paid_tax_incl = _total_paid_tax_incl;
	}

	/**
	 * @param _total_paid_tax_excl the _total_paid_tax_excl to set
	 */
	public void set_total_paid_tax_excl(Float _total_paid_tax_excl) {
		this._total_paid_tax_excl = _total_paid_tax_excl;
	}

	/**
	 * @param _total_products the _total_products to set
	 */
	public void set_total_products(Float _total_products) {
		this._total_products = _total_products;
	}

	/**
	 * @param _carrier_tax_rate the _carrier_tax_rate to set
	 */
	public void set_carrier_tax_rate(Float _carrier_tax_rate) {
		this._carrier_tax_rate = _carrier_tax_rate;
	}


	public static Order fromRS(ResultSet p_rs) throws SQLException {
		Order ret = new Order();
		
		try {
			ret.set_id(p_rs.getLong("id"));
			ret.set_id_customer(p_rs.getLong("id_customer"));
			ret.set_id_address_invoice(p_rs.getLong("id_address_invoice"));
			ret.set_payment(p_rs.getString("payment"));
			ret.set_total_discounts(p_rs.getFloat("total_discounts"));
			ret.set_total_discounts_tax_incl(p_rs.getFloat("total_discounts_tax_incl"));
			ret.set_total_discounts_tax_excl(p_rs.getFloat("total_discounts_tax_excl"));
			ret.set_total_paid(p_rs.getFloat("total_paid"));
			ret.set_total_paid_tax_incl(p_rs.getFloat("total_paid_tax_incl"));
			ret.set_total_paid_tax_excl(p_rs.getFloat("total_paid_tax_excl"));
			ret.set_total_products(p_rs.getFloat("total_products"));
			ret.set_carrier_tax_rate(p_rs.getFloat("carrier_tax_rate"));
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
	
	public static Order getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		Order ret = null;
		
		String str_sql = _str_sql +
			"  WHERE o." + p_key + " = " + p_value +
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
			// in reverse-order of their creation
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
	
	public static Order getByAlias(Connection p_conn, String p_id_address_invoice) throws Exception {
		return getByParameter(p_conn, "id_address_invoice", "'" + p_id_address_invoice + "'");
	}
	
	public static Order getByEmail(Connection p_conn, String p_total_paid_tax_excl) throws Exception {
		return getByParameter(p_conn, "total_paid_tax_excl", "'" + p_total_paid_tax_excl + "'");
	}
	
	public static Order getByMovil(Connection p_conn, String p_total_paid) throws Exception {
		return getByParameter(p_conn, "total_paid", "'" + p_total_paid + "'");
	}

	public static Order getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_order", p_id);
	}
	
    public static ArrayList<Order> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws SQLException {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<Order> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<Order>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("id_comunidad")) {
					array_clauses.add("c.id_comunidad = " + p.getValue());
				}
				else if (p.getKey().equals("id_customer")) {
					array_clauses.add("cm.id_customer = " + p.getValue());
				}
				else if (p.getKey().equals("latitud_mayor")) {
					array_clauses.add("up.latitud > " + p.getValue());
				}
				else if (p.getKey().equals("latitud_menor")) {
					array_clauses.add("up.latitud < " + p.getValue());
				}
				else if (p.getKey().equals("longitud_mayor")) {
					array_clauses.add("up.longitud > " + p.getValue());
				}
				else if (p.getKey().equals("longitud_menor")) {
					array_clauses.add("up.longitud < " + p.getValue());
				}
				else if (p.getKey().equals("posicion_reciente")) {
					array_clauses.add("up.fecha > DATE_ADD(now(), INTERVAL -" + p.getValue() + " MINUTE)");
				}
				else if (p.getKey().equals("id_distinto")) {
					array_clauses.add("o.id_order <> " + p.getValue());
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
			
	        if (p_order != null && p_direction != null) {
	        	str_sql += " ORDER BY " + p_order + " " + p_direction;
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
			// in reverse-order of their creation
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
			"  UPDATE orders" +
			"  SET id_address_invoice = " + (_id_address_invoice != null ? "'" + _id_address_invoice + "'" : "null") + "," +
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
			"  WHERE id_order = " + Integer.toString(this._id);
		
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
			// in reverse-order of their creation
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
			"  INSERT INTO orders" +
			"  (" +
			"  id_customer_FK," +
			"  id_address_invoice," +
			"  payment," +
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
			"  " + (_id_customer != null ? "'" + _id_customer + "'" : "null") + "," +
			"  " + (_id_address_invoice != null ? "'" + _id_address_invoice + "'" : "null") + "," +
			"  " + (_payment != null ? "'" + _payment + "'" : "null") + "," +
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
			// in reverse-order of their creation
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
		return "Order [_id=" + _id + ", _id_address_invoice=" + _id_address_invoice + ", _total_discounts="
				+ _total_discounts + ", _total_discounts_tax_incl=" + _total_discounts_tax_incl
				+ ", _total_paid=" + _total_paid + ", _total_paid_tax_excl=" + _total_paid_tax_excl + "]";
	}
	
}