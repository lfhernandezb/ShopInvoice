package bd;

//import java.util.Date;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class Folio
{
	private Integer _id;
	private Integer _id_caf;
	private BigInteger _tipo;
	private Long _numero;
	private Boolean _utilizado;
	private String _caf;
	
	private final static String _str_sql = 
			"  SELECT f.id_folio AS id, f.id_caf_FK AS id_caf, f.tipo AS tipo, f.numero AS numero, 0+f.utilizado AS utilizado, c.rut_emisor AS rut_emisor, " +
			"  c.contenido AS caf" +
		 	"  FROM folio f" +
		 	"  JOIN caf c ON c.id_caf = f.id_caf_FK";	 	

	public Folio() {
		_id = null;
		_id_caf = null;
		_tipo = null;
		_numero = null;
		_utilizado = null;
		_caf = null;
	}

	/**
	 * @return the _id
	 */
	public Integer get_id() {
		return _id;
	}

	/**
	 * @return the _id_caf
	 */
	public Integer get_id_caf() {
		return _id_caf;
	}

	/**
	 * @return the _tipo
	 */
	public BigInteger get_tipo() {
		return _tipo;
	}

	/**
	 * @return the _numero
	 */
	public Long get_numero() {
		return _numero;
	}

	/**
	 * @return the _utilizado
	 */
	public boolean is_utilizado() {
		return _utilizado;
	}
	
	/**
	 * @return the _contenido
	 */
	public String get_caf() {
		return _caf;
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}
	
	/**
	 * @param _id_caf the _id_caf to set
	 */
	public void set_id_caf(int _id_caf) {
		this._id_caf = _id_caf;
	}

	/**
	 * @param _tipo the _tipo to set
	 */
	public void set_tipo(BigInteger _tipo) {
		this._tipo = _tipo;
	}

	/**
	 * @param _numero the _numero to set
	 */
	public void set_numero(Long _numero) {
		this._numero = _numero;
	}

	/**
	 * @param _utilizado the _utilizado to set
	 */
	public void set_utilizado(boolean _utilizado) {
		this._utilizado = _utilizado;
	}

	/**
	 * @param _caf the _caf to set
	 */
	public void set_caf(String _caf) {
		this._caf = _caf;
	}

	public static Folio fromRS(ResultSet p_rs) throws SQLException {
		Folio ret = new Folio();
		
		try {
			ret.set_id(p_rs.getInt("id"));
			ret.set_id_caf(p_rs.getInt("id_caf"));
			ret.set_tipo(BigInteger.valueOf(p_rs.getLong("tipo")));
			ret.set_numero(p_rs.getLong("numero"));
			ret.set_caf(p_rs.getString("caf"));
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
	
	public static Folio getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		Folio ret = null;
		
		String str_sql = _str_sql +
			"  WHERE f." + p_key + " = " + p_value +
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
	
	public static Folio getByAlias(Connection p_conn, String p_tipo) throws Exception {
		return getByParameter(p_conn, "tipo", "'" + p_tipo + "'");
	}
	
	public static Folio getByEmail(Connection p_conn, String p_email) throws Exception {
		return getByParameter(p_conn, "email", "'" + p_email + "'");
	}
	
	public static Folio getByMovil(Connection p_conn, String p_movil) throws Exception {
		return getByParameter(p_conn, "movil", "'" + p_movil + "'");
	}

	public static Folio getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_folio", p_id);
	}
	
    public static ArrayList<Folio> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<Folio> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<Folio>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("libre")) {
					array_clauses.add("f.utilizado = b'0'");
				}
				else if (p.getKey().equals("utilizado")) {
					array_clauses.add("f.utilizado = b'1'");
				}
				else if (p.getKey().equals("tipo")) {
					array_clauses.add("f.tipo = " + p.getValue());
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

    public static Folio getNextFree(Connection p_conn, Long p_tipo) throws Exception {
    	
		ArrayList<AbstractMap.SimpleEntry<String, String>> listParameters;
		ArrayList<Folio> listaFolio;
		Folio f = null;

		try {
    		listParameters = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
    		
			listParameters.clear();
			
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("libre", ""));
			listParameters.add(new AbstractMap.SimpleEntry<String, String>("tipo", String.valueOf(p_tipo)));
			
			listaFolio = Folio.seek(p_conn, listParameters, "f.numero", "ASC", 0, 1);
			
			if (!listaFolio.isEmpty()) {
				// ok, folio libre
				
				f = listaFolio.get(0);
				
				f.set_utilizado(true);
				
				f.update(p_conn);
			}
			else {
				throw new Exception("No quedan folios libres!");
			}
			
			return f;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
    }

    public int update(Connection p_conn) throws Exception {

    	int ret = -1;
    	Statement stmt = null;

    	String str_sql =
			"  UPDATE folio" +
			"  SET tipo = " + (_tipo != null ? "'" + _tipo + "'" : "null") + "," +
			"  numero = " + (_numero != null ? "'" + _numero + "'" : "null") + "," +
			"  utilizado = " + (_utilizado != null ? "b'" + (_utilizado ? 1 : 0) + "'" : "null") +
			"  WHERE id_folio = " + Integer.toString(this._id);
		
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
			"  INSERT INTO folio" +
			"  (" +
			"  id_caf_FK," +
			"  tipo," +
			"  numero" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_id_caf != null ? "'" + _id_caf + "'" : "null") + "," +
			"  " + (_tipo != null ? "'" + _tipo + "'" : "null") + "," +
			"  " + (_numero != null ? "'" + _numero + "'" : "null") +
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
		return "Folio [_id=" + _id + ", _tipo=" + _tipo + ", _numero="
				+ _numero + ", _tipo=" + _tipo + "]";
	}
	
}