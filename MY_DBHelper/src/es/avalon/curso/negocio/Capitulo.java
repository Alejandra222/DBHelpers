package es.avalon.curso.negocio;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

public class Capitulo {

	private String titulo;
	private int paginas;
	private String capitulo_libro;
	public Capitulo() {
		super();
	}
	public Capitulo(String titulo) {
		super();
		this.titulo = titulo;
	}
	
	
	public Capitulo(String titulo, String capitulo_libro) {
		super();
		this.titulo = titulo;
		this.capitulo_libro = capitulo_libro;
	}
	public Capitulo(String titulo, int paginas, String capitulo_libro) {
		super();
		this.titulo = titulo;
		this.paginas = paginas;
		this.capitulo_libro = capitulo_libro;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	public String getCapitulo_libro() {
		return capitulo_libro;
	}
	public void setCapitulo_libro(String capitulo_libro) {
		this.capitulo_libro = capitulo_libro;
	}
	
	
	public static List<Capitulo>todosLosCapitulos(){
		
		List<Capitulo> lista = new ArrayList<Capitulo>();
		
		String sql ="Select * from Capitulo";
		
		try(Connection conexion= DBHelper.crearConexion();
			PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet st = sentencia.executeQuery(sql)){
			
			
			while(st.next()) {
				
				Capitulo c = new Capitulo(st.getString("titulo"), st.getInt("paginas"), st.getString("libro_titulo"));
				lista.add(c);
			}
			
			System.out.println("Capitulos encontrado: "+lista.size());
			
		} catch (Exception e) {
			System.out.println("Error todosLosCapitulos "+e);
		}
		
		return lista;
	}
	
	
	public void insertarCapitulo() {
		
		String sql="insert into capitulo(titulo, paginas, libro_titulo) value(?,?,?)";
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {
			
			sentencia.setString(1, getTitulo());
			sentencia.setInt(2, getPaginas());
			sentencia.setString(3, getCapitulo_libro());
			sentencia.executeUpdate();
			
			System.out.println(getTitulo()+" fue insertado");
			
		} catch (Exception e) {
			System.out.println("Error insertarCapitulo: "+e);
		}
	}
	
	public void deleteCapitulo() {
		
		String sql ="Delete from capitulo where titulo=? and libro_titulo=?";
		
		try(Connection conexion =DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {
			
			sentencia.setString(1, getTitulo());
			sentencia.setString(2, getCapitulo_libro());
			sentencia.executeUpdate();
			
			System.out.println(getTitulo()+" fue eliminado");
			
		} catch (Exception e) {
		System.out.println("Error borrarCapitulo: "+e);
		}
	}
	
	public static List<Capitulo> buscarUnCapituloPorTituloYLibro(String titulo, String libro) {

		System.out.println("buscarUnCapituloPorTituloYLibro le llega"+ titulo+ "  "+libro);
		List<Capitulo> capitulos = new ArrayList<Capitulo>();
		String sql = "Select * from capitulo where titulo= ? and libro_titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, titulo);
			sentencia.setString(2, libro);
			ResultSet rs = sentencia.executeQuery();
			
			while(rs.next()){

			Capitulo capitulo = new Capitulo(rs.getString("titulo"), rs.getInt("paginas"), rs.getString("libro_titulo"));
			capitulos.add(capitulo);
			
			}
			System.out.println(rs.getString("titulo") + " encontrado");
		} catch (Exception e) {
			System.out.println("Error buscarUnCapituloPorTituloYLibro " + e);
		}
		return capitulos;
	}
	
	public static Capitulo editarUnCapituloPorTituloYLibro(String titulo, String libro) {

		System.out.println("editarUnCapituloPorTituloYLibro le llega"+ titulo+ "  "+libro);
		Capitulo capitulo = null;
		String sql = "Select * from capitulo where titulo= ? and libro_titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, titulo);
			sentencia.setString(2, libro);
			ResultSet rs = sentencia.executeQuery();
			rs.next();	

			 capitulo = new Capitulo(rs.getString("titulo"), rs.getInt("paginas"), rs.getString("libro_titulo"));
			
				
			System.out.println(rs.getString("titulo") + " encontrado");
		} catch (Exception e) {
			System.out.println("Error buscarUnCapituloPorTituloYLibro " + e);
		}
		return capitulo;
	}
	
	
	public void updateCapitulo() {

		String sql = "update capitulo set paginas= ?, libro_titulo= ? where titulo= ?";
		
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			
			sentencia.setInt(1, getPaginas());
			sentencia.setString(2, getCapitulo_libro());
			sentencia.setString(3, getTitulo());
			sentencia.executeUpdate();

			System.out.println(getTitulo() + " actualizado");
		} catch (Exception e) {
			System.out.println("Error UpdateLibro: " + e);
		}
	}
	
	
	public static List<Capitulo> buscarCapitulosPorTitulo(String capitulo) {
		System.out.println("buscarCapitulosPorTitulo le llega: "+capitulo);
		List<Capitulo> lista = new ArrayList<Capitulo>();
		
		String sql = "Select * from capitulo where titulo= ?";
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {
			
			
			sentencia.setString(1, capitulo);
			ResultSet rs = sentencia.executeQuery();
			
			while(rs.next()) {
			Capitulo c = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")), rs.getString("libro_titulo"));
			lista.add(c);
			}
			
		} catch (Exception e) {
			System.out.println("Error buscarCapitulosPorTitulo: "+e);
		}
		return lista;
	}
	
	public static List<Capitulo> listarCapitulosDeLibro(String libro){
		
		System.out.println("listarCapitulosDeLibro le llega: "+libro);
		List<Capitulo> lista = new ArrayList<Capitulo>();
		
		String sql = "Select * from capitulo where libro_titulo= ?";
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {
			
			
			sentencia.setString(1, libro);
			ResultSet rs = sentencia.executeQuery();
			
			while(rs.next()) {
			Capitulo c = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")), rs.getString("libro_titulo"));
			lista.add(c);
			}
			
		} catch (Exception e) {
			System.out.println("Error listarCapitulosDeLibro: "+e);
		}
		return lista;
		
	}
	
public static List<Capitulo> filtrarCampoCapitulo(String filtro, String libro){
		
		System.out.println(libro+"  filtrarCampoCapitulo recibe: "+filtro);
		
		List<Capitulo> lista = new ArrayList<Capitulo>();
		
		String sql;
		if(libro != null || libro.equals("")) {
			sql = "Select * from capitulo where libro_titulo='"+libro+"' order by "+filtro;
		}else {
			
		 sql = "Select * from capitulo order by "+filtro;
		}
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {
			
			while(rs.next()) {
				String titulo =rs.getString("titulo");
				int paginas =rs.getInt("paginas");
				String l =rs.getString("libro_titulo");
				Capitulo lib = new Capitulo(titulo,paginas,l);
				lista.add(lib);
			}
			
			System.out.println("filtrarCampoCapitulo Capitulos encontrados: "+lista.size());
				
		} catch (Exception e) {
			System.out.println("Error en filtrarCampoCapitulo "+e);
		}
		return lista;
		
		
	}

}
