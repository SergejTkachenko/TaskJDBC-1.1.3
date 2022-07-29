package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static SessionFactory currentSessionFactory;

    public static SessionFactory getCurrentSessionFactory() {
        if (currentSessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.setProperty("hibernate.connection.url",
                        "jdbc:mysql://localhost/users?serverTimezone=Europe/Moscow&useSSL=false");
                properties.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "12345qwert");
                properties.setProperty("current_session_context_class", "threat");
                properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
                properties.setProperty("show_sql", "true");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");

                currentSessionFactory = new Configuration().setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
                System.out.println("Session has been created");

            } catch (Exception e) {
                System.out.println("There is a problem with creating session");
                e.printStackTrace();
            }
        }
        return currentSessionFactory;
    }

    private Util() {
        try {
            if (connection == null || connection.isClosed())
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = getConnection()) {
                System.out.println("Connection successful");
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e);
        }
    }
    public static Connection getConnection() throws SQLException, IOException {

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream
                (Paths.get(Objects.requireNonNull(Util.class.getResource("/database.properties")).toURI()))) {
            properties.load(input);
        } catch (URISyntaxException | IOException e) {
            throw new IOException("Configuration file is not found, check resources");
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
