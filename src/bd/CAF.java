package bd;

//import java.util.Date;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class CAF
{
	private Integer _id;
	private String _rut_emisor;
	private BigInteger _tipo;
	private Long _desde;
	private Long _hasta;
	private String _contenido;
	
	private final static String _str_sql = 
			"  SELECT c.id_caf AS id, c.rut_emisor, c.tipo, c.desde, c.hasta, c.contenido, " +
			"  DATE_FORMAT(c.fecha_carga, '%d-%m-%Y') AS fecha_carga" +
		 	"  FROM caf c";	 	

	public CAF() {
		_id = null;
		_rut_emisor = null;
		_tipo = null;
		_desde = null;
		_hasta = null;
		_contenido = null;
	}

	/**
	 * @return the _id
	 */
	public Integer get_id() {
		return _id;
	}

	/**
	 * @return the _rut_emisor
	 */
	public String get_rut_emisor() {
		return _rut_emisor;
	}

	/**
	 * @return the _tipo
	 */
	public BigInteger get_tipo() {
		return _tipo;
	}

	/**
	 * @return the _desde
	 */
	public Long get_desde() {
		return _desde;
	}

	/**
	 * @return the _hasta
	 */
	public Long get_hasta() {
		return _hasta;
	}

	/**
	 * @return the _contenido
	 */
	public String get_contenido() {
		return _contenido;
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}
	
	/**
	 * @param _rut_emisor the _rut_emisor to set
	 */
	public void set_rut_emisor(String _rut_emisor) {
		this._rut_emisor = _rut_emisor;
	}

	/**
	 * @param _tipo the _tipo to set
	 */
	public void set_tipo(BigInteger _tipo) {
		this._tipo = _tipo;
	}

	/**
	 * @param _desde the _desde to set
	 */
	public void set_desde(Long _desde) {
		this._desde = _desde;
	}

	/**
	 * @param _hasta the _hasta to set
	 */
	public void set_hasta(Long _hasta) {
		this._hasta = _hasta;
	}

	/**
	 * @param _contenido the _contenido to set
	 */
	public void set_contenido(String _contenido) {
		this._contenido = _contenido;
	}


	public static CAF fromRS(ResultSet p_rs) throws SQLException {
		CAF ret = new CAF();
		
		try {
			ret.set_id(p_rs.getInt("id"));
			ret.set_rut_emisor(p_rs.getString("rut_emisor"));
			ret.set_tipo(BigInteger.valueOf(p_rs.getLong("tipo")));
			ret.set_rut_emisor(p_rs.getString("rut_emisor"));
			ret.set_desde(p_rs.getLong("desde"));
			ret.set_hasta(p_rs.getLong("hasta"));
			ret.set_contenido(p_rs.getString("contenido"));
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
	
	public static CAF getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		CAF ret = null;
		
		String str_sql = _str_sql +
			"  WHERE c." + p_key + " = " + p_value +
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
	
	public static CAF getByAlias(Connection p_conn, String p_rut_emisor) throws Exception {
		return getByParameter(p_conn, "rut_emisor", "'" + p_rut_emisor + "'");
	}
	
    public static ArrayList<CAF> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<CAF> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<CAF>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("rut_emisor")) {
					array_clauses.add("rut_emisor = " + p.getValue());
				}
				else if (p.getKey().equals("tipo")) {
					array_clauses.add("c.tipo = " + p.getValue());
				}
				else if (p.getKey().equals("desde")) {
					array_clauses.add("c.desde > " + p.getValue());
				}
				else if (p.getKey().equals("hasta")) {
					array_clauses.add("c.hasta < " + p.getValue());
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

    public int update(Connection p_conn) throws Exception {

    	int ret = -1;
    	Statement stmt = null;

    	String str_sql =
			"  UPDATE caf" +
			"  SET rut_emisor = " + (_rut_emisor != null ? "'" + _rut_emisor + "'" : "null") + "," +
			"  tipo = " + (_tipo != null ? "'" + _tipo + "'" : "null") + "," +
			"  desde = " + (_desde != null ? "'" + _desde + "'" : "null") + "," +
			"  hasta = " + (_hasta != null ? "'" + _hasta + "'" : "null") + "," +
			"  contenido = " + (_contenido != null ? "'" + _contenido + "'" : "null") +
			"  WHERE id_caf = " + Integer.toString(this._id);
		
		try {
			stmt = p_conn.createStatement();
			
			ret = stmt.executeUpdate(str_sql);
			/*
			if (stmt.executeUpdate(str_sql) < 1) {
				throw new Exception("No hubo filas afectadas");
			}
			*/
			
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
			"  INSERT INTO caf" +
			"  (" +
			"  rut_emisor," +
			"  tipo," +
			"  desde," +
			"  hasta," +
			"  contenido" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_rut_emisor != null ? "'" + _rut_emisor + "'" : "null") + "," +
			"  " + (_tipo != null ? "'" + _tipo + "'" : "null") + "," +
			"  " + (_desde != null ? "'" + _desde + "'" : "null") + "," +
			"  " + (_hasta != null ? "'" + _hasta + "'" : "null") + "," +
			"  " + (_contenido != null ? "'" + _contenido + "'" : "null") +
			"  )";
		
		try {
			stmt = p_conn.createStatement();
			
			ret = stmt.executeUpdate(str_sql, Statement.RETURN_GENERATED_KEYS);
			/*
			if (stmt.executeUpdate(str_sql) < 1) {
				throw new Exception("No hubo filas afectadas");
			}
			*/
			
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CAF [_id=" + _id + ", _rut_emisor=" + _rut_emisor + ", _desde="
				+ _desde + ", _hasta=" + _hasta
				+ ", _contenido=" + _contenido + "]";
	}
	
}