package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SakilaDataManager {

    private final BasicDataSource dataSource;
    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public SakilaDataManager() {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(URL);
        this.dataSource.setUsername(USER);
        this.dataSource.setPassword(PASSWORD);
    }

    public List<Actor> getActorsByName(String name) {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
            String query = """
                    SELECT *
                    FROM actor
                    WHERE last_name LIKE ? or first_name LIKE ?""";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + name + "%");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("actor_id");
                String first = results.getString("first_name");
                String last = results.getString("last_name");

                Actor actor = new Actor(id, first, last);
                actors.add(actor);
            }

        } catch (SQLException e) {
            System.err.println("\nThere was a problem with SQL\n");
        }
        return actors;
    }

    public List<Film> getFilmByActorId(int i) {
        List<Film> films = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
            String query = """
                    SELECT
                        *
                    FROM
                        film
                            JOIN
                        film_actor ON film.film_id = film_actor.film_id
                            JOIN
                        actor ON film_actor.actor_id = actor.actor_id
                    WHERE
                        actor.actor_id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, i);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("film_id");
                String title = results.getString("title");
                String description = results.getString("description");
                int year = results.getInt("release_year");
                int length = results.getInt("length");

                Film film = new Film(id, title, description, year, length);
                films.add(film);
            }
        }
        catch (SQLException e) {
            System.err.println("\nThere was a problem with SQL\n");
        }
        return films;
    }

    public void printActorList(List<Actor> actors) {
        for (Actor actor : actors) {
            System.out.println(actor);
        }
    }

    public void printFilmList(List<Film> films) {
        for (Film film : films) {
            System.out.println(film);
        }
    }

}
