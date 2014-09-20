package bd;

//import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;

public class DTE
{
	private Integer _id;
	private Long _id_folio;
	private String _dte;
	private String _enviodte;
	private String _resultado_validacion;
	private String _resultado_sii;
	private Boolean _valido;
	private Boolean _enviado;
	
	private final static String _str_sql = 
			"  SELECT d.id_dte AS id, d.id_folio_FK AS id_folio, d.dte AS dte, " +
			"  d.enviodte AS enviodte, d.resultado_validacion AS resultado_validacion, " +
			"  d.resultado_sii AS resultado_sii, 0+d.valido AS valido, " +
			"  0+d.enviado AS enviado" +
		 	"  FROM dte d";	 	

	public DTE() {
		_id = null;
		_id_folio = null;
		_dte = null;
		_enviodte = null;
		_resultado_validacion = null;
		_resultado_sii = null;
		_valido = null;
		_enviado = null;
	}

	/**
	 * @return the _id
	 */
	public Integer get_id() {
		return _id;
	}

	/**
	 * @return the _id_folio
	 */
	public Long get_id_folio() {
		return _id_folio;
	}

	/**
	 * @return the _dte
	 */
	public String get_dte() {
		return _dte;
	}

	/**
	 * @return the _enviodte
	 */
	public String get_enviodte() {
		return _enviodte;
	}

	/**
	 * @return the _resultado_validacion
	 */
	public String get_resultado_validacion() {
		return _resultado_validacion;
	}

	/**
	 * @return the _resultado_sii
	 */
	public String get_resultado_sii() {
		return _resultado_sii;
	}

	/**
	 * @return the _valido
	 */
	public Boolean get_valido() {
		return _valido;
	}

	/**
	 * @return the _enviado
	 */
	public Boolean get_enviado() {
		return _enviado;
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(Integer _id) {
		this._id = _id;
	}
	
	/**
	 * @param _id_folio the _id_folio to set
	 */
	public void set_id_folio(Long _id_folio) {
		this._id_folio = _id_folio;
	}

	/**
	 * @param _dte the _dte to set
	 */
	public void set_dte(String _dte) {
		this._dte = _dte;
	}

	/**
	 * @param _enviodte the _enviodte to set
	 */
	public void set_enviodte(String _enviodte) {
		this._enviodte = _enviodte;
	}

	/**
	 * @param _resultado_validacion the _resultado_validacion to set
	 */
	public void set_resultado_validacion(String _resultado_validacion) {
		this._resultado_validacion = _resultado_validacion;
	}

	/**
	 * @param _resultado_sii the _resultado_sii to set
	 */
	public void set_resultado_sii(String _resultado_sii) {
		this._resultado_sii = _resultado_sii;
	}

	/**
	 * @param _valido the _valido to set
	 */
	public void set_valido(Boolean _valido) {
		this._valido = _valido;
	}

	/**
	 * @param _enviado the _enviado to set
	 */
	public void set_enviado(Boolean _enviado) {
		this._enviado = _enviado;
	}


	public static DTE fromRS(ResultSet p_rs) throws SQLException {
		DTE ret = new DTE();
		
		try {
			ret.set_id(p_rs.getInt("id"));
			ret.set_id_folio(p_rs.getLong("id_folio"));
			ret.set_dte(p_rs.getString("dte"));
			ret.set_enviodte(p_rs.getString("enviodte"));
			ret.set_resultado_validacion(p_rs.getString("resultado_validacion"));
			ret.set_resultado_sii(p_rs.getString("resultado_sii"));
			ret.set_valido(p_rs.getBoolean("valido"));
			ret.set_enviado(p_rs.getBoolean("enviado"));
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
	
	public static DTE getByParameter(Connection p_conn, String p_key, String p_value) throws Exception {
		DTE ret = null;
		
		String str_sql = _str_sql +
			"  WHERE d." + p_key + " = " + p_value +
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
			// in reverse-dte of their creation
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
	
	public static DTE getByAlias(Connection p_conn, String p_id_address_invoice) throws Exception {
		return getByParameter(p_conn, "id_address_invoice", "'" + p_id_address_invoice + "'");
	}
	
	public static DTE getByEmail(Connection p_conn, String p_valido_tax_excl) throws Exception {
		return getByParameter(p_conn, "valido_tax_excl", "'" + p_valido_tax_excl + "'");
	}
	
	public static DTE getByMovil(Connection p_conn, String p_valido) throws Exception {
		return getByParameter(p_conn, "valido", "'" + p_valido + "'");
	}

	public static DTE getById(Connection p_conn, String p_id) throws Exception {
		return getByParameter(p_conn, "id_dte", p_id);
	}
	
    public static ArrayList<DTE> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_dte, String p_direction, int p_offset, int p_limit) throws Exception {
    	Statement stmt = null;
    	ResultSet rs = null;
    	String str_sql;
    	ArrayList<DTE> ret;
    	
    	str_sql = "";
    	
		try {
			ArrayList<String> array_clauses = new ArrayList<String>();
			
			ret = new ArrayList<DTE>();
			
			str_sql = _str_sql;
			
			for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
				if (p.getKey().equals("id_folio")) {
					array_clauses.add("d.id_folio_FK = " + p.getValue());
				}
				else if (p.getKey().equals("id")) {
					array_clauses.add("d.id_dte = " + p.getValue());
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
			
	        if (p_dte != null && p_direction != null) {
	        	str_sql += " ORDER BY " + p_dte + " " + p_direction;
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
			// in reverse-dte of their creation
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
			"  UPDATE dte" +
			"  SET" +
			"  dte = " + (_dte != null ? "'" + _dte + "'" : "null") + "," +
			"  enviodte = " + (_enviodte != null ? "'" + _enviodte + "'" : "null") + "," +
			"  resultado_validacion = " + (_resultado_validacion != null ? "'" + _resultado_validacion + "'" : "null") + "," +
			"  resultado_sii = " + (_resultado_sii != null ? "'" + _resultado_sii + "'" : "null") + "," +
			"  valido = " + (_valido != null ? "b'" + (_valido ? 1 : 0) + "'" : "null") + "," +
			"  enviado = " + (_enviado != null ? "b'" + (_enviado ? 1 : 0) + "'" : "null") +
			"  WHERE id_dte = " + Integer.toString(this._id);
		
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
			// in reverse-dte of their creation
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
			"  INSERT INTO dte" +
			"  (" +
			"  id_folio_FK," +
			"  dte," +
			"  enviodte," +
			"  resultado_validacion," +
			"  resultado_sii," +
			"  valido," +
			"  enviado" +
			"  )" +
			"  VALUES" +
			"  (" +
			"  " + (_id_folio != null ? "'" + _id_folio + "'" : "null") + "," +
			"  " + (_dte != null ? "'" + _dte + "'" : "null") + "," +
			"  " + (_enviodte != null ? "'" + _enviodte + "'" : "null") + "," +
			"  " + (_resultado_validacion != null ? "'" + _resultado_validacion + "'" : "null") + "," +
			"  " + (_resultado_sii != null ? "'" + _resultado_sii + "'" : "null") + "," +
			"  " + (_valido != null ? "b'" + (_valido ? 1 : 0) + "'" : "null") + "," +
			"  " + (_enviado != null ? "b'" + (_enviado ? 1 : 0) + "'" : "null") +
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
			// in reverse-dte of their creation
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
		return "DTE [_id=" + _id + ", _dte=" + _dte + ", _enviodte="
				+ _enviodte + ", _resultado_validacion=" + _resultado_validacion
				+ ", _valido=" + _valido + ", _enviado=" + _enviado + "]";
	}
	
}