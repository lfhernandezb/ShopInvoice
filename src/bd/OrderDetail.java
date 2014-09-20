package bd;

//import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class OrderDetail
{
	private Long _id;
	private Long _id_order;
	private Long _product_id;
	private String _product_name;
	private Float _product_quantity;
	private Float _product_price;
	private String _product_reference;
	private Float _total_price_tax_incl;
	private Float _total_price_tax_excl;
	private Float _unit_price_tax_incl;
	private Float _unit_price_tax_excl;
	private Float _original_product_price;
	
	private final static String _str_sql = 
			"  SELECT od.id_order_detail AS id, od.id_order AS id_order, od.product_id AS product_id, od.product_name AS product_name, " +
			"  od.product_quantity AS product_quantity, od.product_price AS product_price, " +
			"  od.product_reference AS product_reference, od.total_price_tax_incl AS total_price_tax_incl, " +
			"  od.total_price_tax_excl AS total_price_tax_excl, od.unit_price_tax_incl AS unit_price_tax_incl, " +
			"  od.unit_price_tax_excl AS unit_price_tax_excl, od.original_product_price AS original_product_price" +
		 	"  FROM order_detail od";	 	

	public OrderDetail() {
		_id = null;
		_id_order = null;
		_product_id = null;
		_product_name = null;
		_product_quantity = null;
		_product_price = null;
		_product_reference = null;
		_total_price_tax_incl = null;
		_total_price_tax_excl = null;
		_unit_price_tax_incl = null;
		_unit_price_tax_excl = null;
		_original_product_price = null;
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
	 * @return the _product_id
	 */
	public Long get_product_id() {
		return _product_id;
	}

	/**
	 * @return the _product_name
	 */
	public String get_product_name() {
		return _product_name;
	}

	/**
	 * @return the _product_quantity
	 */
	public Float get_product_quantity() {
		return _product_quantity;
	}

	/**
	 * @return the _product_price
	 */
	public Float get_product_price() {
		return _product_price;
	}

	/**
	 * @return the _product_reference
	 */
	public String get_product_reference() {
		return _product_reference;
	}

	/**
	 * @return the _total_price_tax_incl
	 */
	public Float get_total_price_tax_incl() {
		return _total_price_tax_incl;
	}

	/**
	 * @return the _total_price_tax_excl
	 */
	public Float get_total_price_tax_excl() {
		return _total_price_tax_excl;
	}

	/**
	 * @return the _unit_price_tax_incl
	 */
	public Float get_unit_price_tax_incl() {
		return _unit_price_tax_incl;
	}

	/**
	 * @return the _unit_price_tax_excl
	 */
	public Float get_unit_price_tax_excl() {
		return _unit_price_tax_excl;
	}

	/**
	 * @return the _original_product_price
	 */
	public Float get_original_product_price() {
		return _original_product_price;
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
	 * @param _product_id the _product_id to set
	 */
	public void set_product_id(Long _product_id) {
		this._product_id = _product_id;
	}

	/**
	 * @param _product_name the _product_name to set
	 */
	public void set_product_name(String _product_name) {
		this._product_name = _product_name;
	}

	/**
	 * @param _product_quantity the _product_quantity to set
	 */
	public void set_product_quantity(Float _product_quantity) {
		this._product_quantity = _product_quantity;
	}

	/**
	 * @param _product_price the _product_price to set
	 */
	public void set_product_price(Float _product_price) {
		this._product_price = _product_price;
	}

	/**
	 * @param _product_reference the _product_reference to set
	 */
	public void set_product_reference(String _product_reference) {
		this._product_reference = _product_reference;
	}

	/**
	 * @param _total_price_tax_incl the _total_price_tax_incl to set
	 */
	public void set_total_price_tax_incl(Float _total_price_tax_incl) {
		this._total_price_tax_incl = _total_price_tax_incl;
	}

	/**
	 * @param _total_price_tax_excl the _total_price_tax_excl to set
	 */
	public void set_total_price_tax_excl(Float _total_price_tax_excl) {
		this._total_price_tax_excl = _total_price_tax_excl;
	}

	/**
	 * @param _unit_price_tax_incl the _unit_price_tax_incl to set
	 */
	public void set_unit_price_tax_incl(Float _unit_price_tax_incl) {
		this._unit_price_tax_incl = _unit_price_tax_incl;
	}

	/**
	 * @param _unit_price_tax_excl the _unit_price_tax_excl to set
	 */
	public void set_unit_price_tax_excl(Float _unit_price_tax_excl) {
		this._unit_price_tax_excl = _unit_price_tax_excl;
	}

	/**
	 * @param _original_product_price the _original_product_price to set
	 */
	public void set_original_product_price(Float _original_product_price) {
		this._original_product_price = _original_product_price;
	}


	public static OrderDetail fromRS(ResultSet p_rs) throws SQLException {
		OrderDetail ret = new OrderDetail();
		
		try {
			ret.set_id(p_rs.getLong("id"));
			ret.set_id_order(p_rs.getLong("id_order"));
			ret.set_product_id(p_rs.getLong("product_id"));
			ret.set_product_name(p_rs.getString("product_name"));
			ret.set_product_quantity(p_rs.getFloat("product_quantity"));
			ret.set_product_price(p_rs.getFloat("product_price"));
			ret.set_product_reference(p_rs.getString("product_reference"));
			ret.set_total_price_tax_incl(p_rs.getFloat("total_price_tax_incl"));
			ret.set_total_price_tax_excl(p_rs.getFloat("total_price_tax_excl"));
			ret.set_unit_price_tax_incl(p_rs.getFloat("unit_price_tax_incl"));
			ret.set_unit_price_tax_excl(p_rs.getFloat("unit_price_tax_excl"));
			ret.set_original_product_price(p_rs.getFloat("original_product_price"));
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
	
	public static OrderDetail getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		OrderDetail ret = null;
		
		String str_sql = _str_sql +
			"  WHERE od." + p_key + " = " + p_value +
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
			// in reverse-order_detail of their creation
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
	
	public static OrderDetail getByAlias(Connection p_conn, String p_product_id) throws Exception {
		return getByParameter(p_conn, "product_id", "'" + p_product_id + "'");
	}
	
	public static OrderDetail getByEmail(Connection p_conn, String p_unit_price_tax_incl) throws Exception {
		return getByParameter(p_conn, "unit_price_tax_incl", "'" + p_unit_price_tax_incl + "'");
	}
	
	public static OrderDetail getByMovil(Connection p_conn, String p_total_price_tax_incl) throws Exception {
		return getByParameter(p_conn, "total_price_tax_incl", "'" + p_total_price_tax_incl + "'");
	}

	public static OrderDetail getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_order_detail", p_id);
	}
	
    public static ArrayList<OrderDetail> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order_detail, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<OrderDetail> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<OrderDetail>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("id_order")) {
					array_clauses.add("od.id_order = " + p.getValue());
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
			
	        if (p_order_detail != null && p_direction != null) {
	        	str_sql += " ORDER BY " + p_order_detail + " " + p_direction;
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
		catch (Exception ex){
			// handle any errors
			System.out.println("Exception: " + ex.getMessage());
			
			throw ex;
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order_detail of their creation
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
			"  UPDATE order_detail" +
			"  SET product_id = " + (_product_id != null ? "'" + _product_id + "'" : "null") + "," +
			"  product_name = " + (_product_name != null ? "'" + _product_name + "'" : "null") + "," +
			"  product_quantity = " + (_product_quantity != null ? "'" + _product_quantity + "'" : "null") + "," +
			"  product_price = " + (_product_price != null ? "'" + _product_price + "'" : "null") + "," +
			"  product_reference = " + (_product_reference != null ? "'" + _product_reference + "'" : "null") + "," +
			"  total_price_tax_incl = " + (_total_price_tax_incl != null ? "'" + _total_price_tax_incl + "'" : "null") + "," +
			"  total_price_tax_excl = " + (_total_price_tax_excl != null ? "'" + _total_price_tax_excl + "'" : "null") + "," +
			"  unit_price_tax_incl = " + (_unit_price_tax_incl != null ? "'" + _unit_price_tax_incl + "'" : "null") + "," +
			"  unit_price_tax_excl = " + (_unit_price_tax_excl != null ? "STR_TO_DATE('" + _unit_price_tax_excl + "', '%d-%m-%Y')" : "null") + "," +
			"  original_product_price = " + (_original_product_price != null ? "'" + _original_product_price + "'" : "null") + "," +
			"  foto = " + (_foto != null ? "'" + _foto + "'" : "null") + "," +
			"  antecedentes_emergencia = " + (_antecedentes_emergencia != null ? "'" + _antecedentes_emergencia + "'" : "null") +
			"  WHERE id_order_detail = " + Integer.toString(this._id);
		
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
			// in reverse-order_detail of their creation
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
			"  INSERT INTO order_detail" +
			"  (" +
			"  id_order_FK," +
			"  product_id," +
			"  product_name," +
			"  product_quantity," +
			"  product_price," +
			"  product_reference," +
			"  total_price_tax_incl," +
			"  total_price_tax_excl," +
			"  unit_price_tax_incl," +
			"  unit_price_tax_excl," +
			"  original_product_price," +
			"  foto," +
			"  antecedentes_emergencia" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_id_order != null ? "'" + _id_order + "'" : "null") + "," +
			"  " + (_product_id != null ? "'" + _product_id + "'" : "null") + "," +
			"  " + (_product_name != null ? "'" + _product_name + "'" : "null") + "," +
			"  " + (_product_quantity != null ? "'" + _product_quantity + "'" : "null") + "," +
			"  " + (_product_price != null ? "'" + _product_price + "'" : "null") + "," +
			"  " + (_product_reference != null ? "'" + _product_reference + "'" : "null") + "," +
			"  " + (_total_price_tax_incl != null ? "'" + _total_price_tax_incl + "'" : "null") + "," +
			"  " + (_total_price_tax_excl != null ? "'" + _total_price_tax_excl + "'" : "null") + "," +
			"  " + (_unit_price_tax_incl != null ? "'" + _unit_price_tax_incl + "'" : "null") + "," +
			"  " + (_unit_price_tax_excl != null ? "STR_TO_DATE('" + _unit_price_tax_excl + "', '%d-%m-%Y')" : "null") + "," +
			"  " + (_original_product_price != null ? "'" + _original_product_price + "'" : "null") + "," +
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
			// in reverse-order_detail of their creation
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
		return "OrderDetail [_id=" + _id + ", _product_id=" + _product_id + ", _product_quantity="
				+ _product_quantity + ", _product_price=" + _product_price
				+ ", _total_price_tax_incl=" + _total_price_tax_incl + ", _unit_price_tax_incl=" + _unit_price_tax_incl + "]";
	}
	
}