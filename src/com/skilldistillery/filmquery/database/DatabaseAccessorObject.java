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

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public DatabaseAccessorObject() {

	}

//----------------------------------------------------------------------------------
	public List<Film> getFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
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
				List<Actor> actors = getActorsByFilmId(filmId);
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, actors);
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

//----------------------------------------------------------------------------------
//Implement the getFilmById method that takes an int film ID, and returns a 
//Film object (or null, if the film ID returns no data.)
	@Override
	public Film getFilmById(int filmId) {
		Film film = null;
//		Film film = new Film();
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features" + " FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				film = new Film();
				film.setFilmId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setDesc(rs.getString(3));
				film.setReleaseYear(rs.getShort(4));
				film.setLangId(rs.getInt(5));
				film.setRentDur(rs.getInt(6));
				film.setRate(rs.getDouble(7));
				film.setLength(rs.getInt(8));
				film.setRepCost(rs.getDouble(9));
				film.setRating(rs.getString(10));
				film.setFeatures(rs.getString(11));
				film.setActors(getActorsByFilmId(filmId));
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

//----------------------------------------------------------------------------------
	// Implement getActorById method that takes an int actor ID, and returns
	// an Actor object (or null, if the actor ID returns no data.)
	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create
//----------------------------------------------------------------------------------																													// the
			// object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
//			actor.setFilms(getFilmsByActorId(actorId)); // An Actor has Films

		}
		return actor;

	}

//Implement getActorsByFilmId with an appropriate List implementation that 
//will be populated using a ResultSet and returned.
	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		try {
			String user = "student";
			String pass = "student";

			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT id, first_name, last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
					+ " WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt(1));
				actor.setFirstName(rs.getString(2));
				actor.setLastName(rs.getString(3));
				actors.add(actor);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public String getLanguageById(int langId) {
		String lang = null;
		try {
			String user = "student";
			String pass = "student";

			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT name FROM language where id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, langId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				lang = rs.getString(1);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lang;
	}

	@Override
	public List<Film> getFilmsByKeyword(String searchTerm) {
		List<Film> keywordFilmList = new ArrayList<>();
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			
			System.out.println("Our search term :  " + searchTerm);

			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features"
					+ " FROM film WHERE title like '%dino%'" + " OR description like '%dino%'";

			PreparedStatement stmt = conn.prepareStatement(sql);
			
			System.out.println("Statement :  " + stmt);
			
//			stmt.setString(1, searchTerm);
//			stmt.setString(2, searchTerm);
			ResultSet rs = stmt.executeQuery();
			System.out.println("size of the result set : " + rs.getFetchSize());
			if (rs.next()) {
				Film film = new Film();
				film.setFilmId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setDesc(rs.getString(3));
				film.setReleaseYear(rs.getShort(4));
				film.setLangId(rs.getInt(5));
				film.setRentDur(rs.getInt(6));
				film.setRate(rs.getDouble(7));
				film.setLength(rs.getInt(8));
				film.setRepCost(rs.getDouble(9));
				film.setRating(rs.getString(10));
				film.setFeatures(rs.getString(11));
				film.setActors(getActorsByFilmId(film.getFilmId()));
				keywordFilmList.add(film);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keywordFilmList;
	}

}
