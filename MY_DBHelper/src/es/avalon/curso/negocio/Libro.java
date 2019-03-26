package es.avalon.curso.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Libro {

	private String titulo;
	private String autor;
	private int paginas;

	public Libro() {
		super();
	}

	public Libro(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Libro(String titulo, String autor, int paginas) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public static List<Libro> buscarTodos() {

		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql);) {

			while (rs.next()) {
				Libro l = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("paginas"));
				lista.add(l);
			}

			System.out.println("bucarTodos total libros: " + lista.size());
		} catch (Exception e) {
			System.out.println("Error buscarTodos: " + e);
		}
		return lista;
	}

	public void insertar() {

		String sql = "insert into libro(titulo, autor, paginas) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, getTitulo());
			sentencia.setString(2, getAutor());
			sentencia.setInt(3, getPaginas());
			sentencia.executeUpdate();

			System.out.println(getTitulo() + " insertado");
		} catch (Exception e) {
			System.out.println("Error al insertar Libro: " + e);
		}

	}

	public void deleteLibro() {

		String sql = "delete from libro where titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, getTitulo());
			sentencia.executeUpdate();

			System.out.println(getTitulo() + " eliminado");
		} catch (Exception e) {
			System.out.println("Error deleteLibro " + e);
		}

	}

	public static Libro buscarUnLibroPorTitulo(String titulo) {

		Libro lib = null;
		String sql = "Select * from libro where titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();
			rs.next();

			lib = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("paginas"));

			System.out.println(rs.getString("titulo") + " encontrado");
		} catch (Exception e) {
			System.out.println("Erro buscarUnLibroPorTitulo " + e);
		}
		return lib;
	}

	public void updateLibro() {

		String sql = "update libro set autor= ?, paginas= ? where titulo= ?";
		
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, getAutor());
			sentencia.setInt(2, getPaginas());
			sentencia.setString(3, getTitulo());
			sentencia.executeUpdate();

			System.out.println(getTitulo() + " actualizado");
		} catch (Exception e) {
			System.out.println("Error UpdateLibro: " + e);
		}
	}
	
	
	public static List<Libro> filtrarPorTitulo(String titulo) {

		System.out.println("filtrarPorTitulo le llega: " + titulo);
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro where titulo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, titulo);

			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {
				Libro or = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("paginas"));
				lista.add(or);
			}

			System.out.println("filtrarPorTitulo libros encontrados: "+lista.size());
		} catch (Exception e) {
			System.out.println("Error filtrarPorTitulo: " + e);
		}
		return lista;

	}
	
	public static List<Libro> filtrarCampoLibro(String filtro){
		
		System.out.println("filtrarCampo recibe: "+filtro);
		
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro order by "+filtro;
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery();) {
			
			while(rs.next()) {
				String titulo =rs.getString("titulo");
				String autor =rs.getString("autor");
				int paginas =rs.getInt("paginas");
				Libro lib = new Libro(titulo,autor,paginas);
				lista.add(lib);
			}
			
			System.out.println("filtrarCampoLibro libros encontrados: "+lista.size());
				
		} catch (Exception e) {
			System.out.println("Error en filtrarCampoLibro "+e);
		}
		return lista;
		
		
	}

}
