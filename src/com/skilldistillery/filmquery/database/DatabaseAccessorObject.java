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
		List<Actor> ListOfActors = new ArrayList<>();
		Connection conn = DriverManager.getConnection(url, user, pass);

		String sql = "Select actor.id, actor.first_name, actor.last_name "
				+ "From actor JOIN film_actor ON actor.id = film_actor.actor_id "
				+ " JOIN film ON film.id = film_actor.film_id " + "WHERE film.id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet result = stmt.executeQuery();

		while (result.next()) {
			int id = result.getInt("actor.id");
			String firsttName = result.getString("actor.first_name");
			String lastName = result.getString("actor.last_name");
			Actor a = new Actor(id, firsttName, lastName);
			ListOfActors.add(a);

		}
		// close
		result.close();
		stmt.close();
		conn.close();
		return ListOfActors;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		// ...
		String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor WHERE id = ?";
		Connection conn = DriverManager.getConnection(url, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(result.getInt("actor.id"));
			actor.setFirstName(result.getString("actor.first_name"));
			actor.setLastName(result.getString("actor.last_name"));
			actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		}
		// close
		result.close();
		stmt.close();
		conn.close();
		return actor;
	}

	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration, ";
			sql += " film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int filmId = result.getInt("film.id");
				String title = result.getString("film.title");
				String desc = result.getString("film.description");
				short releaseYear = result.getShort("film.release_year");
				int langId = result.getInt("film.language_id");
				int rentDur = result.getInt("film.rental_duration");
				double rate = result.getDouble("film.rental_rate");
				int length = result.getInt("film.length");
				double repCost = result.getDouble("film.replacement_cost");
				String rating = result.getString("film.rating");
				String features = result.getString("film.special_features");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				film.setLanguage(findLanguageAsString(langId));
				films.add(film);
			}
			result.close();
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
		// ...
		String sql = "SELECT id, title, description, rating, release_year FROM film WHERE id = ?";
		Connection conn = DriverManager.getConnection(url, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(result.getInt(1));
			film.setTitle(result.getString(2));
			film.setDescription(result.getString(3));
			film.setRating(result.getString(4));
			film.setYear(result.getShort(5));
			film.setActors(findActorsByFilmId(filmId)); // An Actor has Films
			film.setLanguage(findLanguageAsString(filmId));
		}
		// closing
		result.close();
		stmt.close();
		conn.close();

		return film;
	}

	public List<Film> findFilmsByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		keyword = "%" + keyword + "%";
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration, "
					+ "film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features "
					+ "FROM film " + "WHERE title LIKE ? " + "OR description LIKE ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int filmId = result.getInt("film.id");
				String title = result.getString("film.title");
				String desc = result.getString("film.description");
				short releaseYear = result.getShort("film.release_year");
				int langId = result.getInt("film.language_id");
				int rentDur = result.getInt("film.rental_duration");
				double rate = result.getDouble("film.rental_rate");
				int length = result.getInt("film.length");
				double repCost = result.getDouble("film.replacement_cost");
				String rating = result.getString("film.rating");
				String features = result.getString("film.special_features");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(findLanguageAsString(filmId));
				films.add(film);
			}
			result.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	public String findLanguageAsString(int filmId) throws SQLException {
		String languageName = "";
		// ...
		String sql = "Select * from language JOIN film ON film.language_id = language.id Where film.id = ?";
		Connection conn = DriverManager.getConnection(url, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			languageName = result.getString("language.name");
		}
		// closing
		result.close();
		stmt.close();
		conn.close();

		return languageName;
	}

}
