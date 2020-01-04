package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	// static field block
	private static String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private String user = "student";
	private String pass = "student";
	// constructors
	
	// methods
	
	
	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors= new ArrayList<>();
		Connection conn = DriverManager.getConnection(url, user, pass);
		
		String sql = "Select actor.id, actor.first_name, actor.last_name "
				+ "From actor JOIN film_actor ON actor.id = film_actor.actor_id "
				+ 			" JOIN film ON film.id = film_actor.film_id "
				+ "WHERE film.id = ?";
		

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,filmId);
		System.out.println(stmt);
		ResultSet rs = stmt.executeQuery();
		
		 while (rs.next()) {
		     int id = rs.getInt("actor.id");
		      String firstName = rs.getString("first_name");
		      String lastName = rs.getString("actor.last_name");
		      Actor a = new Actor(id, firstName, lastName);
		      actors.add(a);
		      
		 }
		return actors;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		  Actor actor = null;
		  //...
		  String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		  Connection conn = DriverManager.getConnection(url, user, pass);
		  PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1,actorId);
		  ResultSet actorResult = stmt.executeQuery();
		  if (actorResult.next()) {
		    actor = new Actor(); // Create the object
		    // Here is our mapping of query columns to our object fields:
		    actor.setId(actorResult.getInt(1));
		    actor.setFirstName(actorResult.getString(2));
		    actor.setLastName(actorResult.getString(3));
		    actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		  }
		  //...
		  return actor;
		}

	public List<Film> findFilmsByActorId(int actorId) {
		  List<Film> films = new ArrayList<>();
		  try {
		    Connection conn = DriverManager.getConnection(url, user, pass);
		    String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
		                sql += " rental_rate, length, replacement_cost, rating, special_features "
		               +  " FROM film JOIN film_actor ON film.id = film_actor.film_id "
		               + " WHERE actor_id = ?";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setInt(1, actorId);
		    ResultSet rs = stmt.executeQuery();
		    while (rs.next()) {
		      int filmId = rs.getInt(1);
		      String title = rs.getString(2);
		      String desc = rs.getString(3);
		      short releaseYear = rs.getShort(4);
		      int langId = rs.getInt(5);
		      int rentDur = rs.getInt(6);
		      double rate = rs.getDouble(7);
		      int length = rs.getInt(8);
		      double repCost = rs.getDouble(9);
		      String rating = rs.getString(10);
		      String features = rs.getString(11);
		      Film film = new Film(filmId, title, desc, releaseYear, langId,
		                           rentDur, rate, length, repCost, rating, features);
		      films.add(film);
		    }
		    rs.close();
		    stmt.close();
		    conn.close();
		  } catch (SQLException e) {
		    e.printStackTrace();
		  }
		  return films;
		}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		  Film film = null;
		  //...
		  String sql = "SELECT id, title, description, rating FROM film WHERE id = ?";
		  Connection conn = DriverManager.getConnection(url, user, pass);
		  PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1,filmId);
		  ResultSet rs = stmt.executeQuery();
		  if (rs.next()) {
		    film = new Film(); // Create the object
		    // Here is our mapping of query columns to our object fields:
		    film.setId(rs.getInt(1));
		    film.setTitle(rs.getString(2));
		    film.setDescription(rs.getString(3));
		    film.setRating(rs.getString(4)); // An Actor has Films
		    film.setActors(findActorsByFilmId(filmId)); // An Actor has Films
		  }
		  //...
		  return film;
	}


}
