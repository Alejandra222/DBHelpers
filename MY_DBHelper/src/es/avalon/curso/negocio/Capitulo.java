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
				
				Capitulo c = new Capitulo(st.getString("titulo"), st.getInt("paginas"), st.getString("capitulo_libro"));
				lista.add(c);
			}
			
			System.out.println("Capitulos encontrado: "+lista.size());
			
		} catch (Exception e) {
			System.out.println("Error todosLosCapitulos "+e);
		}
		
		return lista;
	}
	
	
	public void insertarCapitulo() {
		
		String sql="insert into capitulo(titulo, paginas, capitulo_libro) value(?,?,?)";
		
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
		
		String sql ="Delete from capitulo where titulo=? and capitulo_libro=?";
		
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
	
	public static Capitulo buscarUnCapituloPorTitulo(String titulo) {

		System.out.println("*****************"+ titulo);
		Capitulo capitulo = null;
		String sql = "Select * from capitulo where titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();
			rs.next();

			capitulo = new Capitulo(rs.getString("titulo"), rs.getInt("paginas"), rs.getString("capitulo_libro"));

			System.out.println(rs.getString("titulo") + " encontrado");
		} catch (Exception e) {
			System.out.println("Erro buscarUnCapituloPorTitulo " + e);
		}
		return capitulo;
	}
	
	public void updateCapitulo() {

		String sql = "update capitulo set paginas= ?, capitulo_libro= ? where titulo= ?";
		
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
			Capitulo c = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")), rs.getString("capitulo_libro"));
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
		
		String sql = "Select * from capitulo where capitulo_libro= ?";
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {
			
			
			sentencia.setString(1, libro);
			ResultSet rs = sentencia.executeQuery();
			
			while(rs.next()) {
			Capitulo c = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")), rs.getString("capitulo_libro"));
			lista.add(c);
			}
			
		} catch (Exception e) {
			System.out.println("Error listarCapitulosDeLibro: "+e);
		}
		return lista;
		
	}
}
