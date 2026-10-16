package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Conexion {
    private static final Logger logger = LoggerFactory.getLogger(Conexion.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/apijdbc";
    private static final String USER = "postgres";
    private static final String PASS = "16103014";

    public static Connection conectar() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
            logger.info("Conexión realizada exitosamente a PostgreSQL");
        } catch (ClassNotFoundException e) {
            logger.error("Error: Driver de PostgreSQL no encontrado", e);
            throw new SQLException("Driver no encontrado", e);
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos", e);
            throw e;
        }
        return connection;
    }
}