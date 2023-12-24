package com.personalplugsite.data.repos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.personalplugsite.data.entities.Person;

import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties("repo")
@Slf4j
public class PersonRepo {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    public Person getPersonFromDb(String nameSearch) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE name = ?");
                ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, nameSearch);

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Process the retrieved data
                log.info("ID: {}, Name: {}, Email: {}", id, name, email);

                return new Person(id, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no person found
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
