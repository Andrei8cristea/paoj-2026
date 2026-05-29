package com.pao.laboratory12.util;

import java.io.*;
import java.sql.*;

/**
 * Ruleaza schema SQL la startup, util pentru H2 in-memory sau recreare SQLite.
 * Apeleaza din Main inainte de orice operatie pe BD.
 */
public class SchemaInitializer {

    private static final String RESOURCES_DIR =
            "src/com/pao/laboratory12/resources/";

    public static void init(Connection conn) throws SQLException, IOException {
        String url = conn.getMetaData().getURL();
        String schemaFile;

        if (url.contains("mysql")) {
            schemaFile = "schema-mysql.sql";
        } else if (url.contains("sqlite")) {
            schemaFile = "schema-sqlite.sql";
        } else {
            schemaFile = "schema-mysql.sql";  // H2 e compatibil MySQL
        }

        // Citim fisierul din resources/ (de pe disc)
        File file = new File(RESOURCES_DIR + schemaFile);
        if (!file.exists()) {
            throw new IOException("Schema file not found: " + file.getAbsolutePath());
        }
        try (InputStream is = new FileInputStream(file)) {
            String sql = new String(is.readAllBytes());
            // Executam fiecare statement separat (split dupa ";")
            for (String stmt : sql.split(";")) {
                // Eliminam liniile de comentariu (incep cu --)
                String cleaned = stmt.lines()
                        .filter(line -> !line.trim().startsWith("--"))
                        .reduce("", (a, b) -> a + "\n" + b)
                        .trim();
                if (!cleaned.isEmpty()) {
                    try (Statement s = conn.createStatement()) {
                        s.execute(cleaned);
                    }
                }
            }
        }
        System.out.println("[DB] Schema initializata din " + schemaFile);
    }
}
