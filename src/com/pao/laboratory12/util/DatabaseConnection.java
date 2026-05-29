package com.pao.laboratory12.util;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Calea relativa catre db.properties (de la working directory = radacina proiectului)
    private static final String DB_PROPS_PATH =
            "src/com/pao/laboratory12/resources/db.properties";

    // Constructor privat — nimeni din afară nu poate face "new DatabaseConnection()"
    private DatabaseConnection() throws IOException, SQLException {
        Properties props = new Properties();
        // Citim db.properties din fisierul de pe disc
        File propsFile = new File(DB_PROPS_PATH);
        if (!propsFile.exists()) {
            throw new IOException("Nu gasesc db.properties la: " + propsFile.getAbsolutePath());
        }
        try (InputStream is = new FileInputStream(propsFile)) {
            props.load(is);
        }
        String url  = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        // Pentru SQLite: user si pass pot fi goale — DriverManager accepta null
        this.connection = DriverManager.getConnection(url, user, pass);

        // SQLite: activam foreign key constraints (ignorat de MySQL)
        try (var stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ignored) {
            // Pe MySQL, PRAGMA nu exista — ignoram silentios
        }
    }

    /**
     * Returneaza unica instanta DatabaseConnection.
     * Thread-safe cu synchronized (suficient pentru proiect single-threaded).
     */
    public static synchronized DatabaseConnection getInstance()
            throws IOException, SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /** Expune conexiunea pentru repository-uri. */
    public Connection getConnection() {
        return connection;
    }

    /** Inchide conexiunea la finalul aplicatiei. */
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
