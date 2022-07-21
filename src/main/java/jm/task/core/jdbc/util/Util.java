package jm.task.core.jdbc.util;

import java.util.*;
import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.net.URISyntaxException;


public class Util {
    private static Connection connection = null;
    private static Util instance = null;
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

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
}
