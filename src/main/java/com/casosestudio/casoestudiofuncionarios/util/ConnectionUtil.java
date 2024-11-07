package com.casosestudio.casoestudiofuncionarios.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionUtil {
    private static final String URL = "jdbc:postgresql://localhost:5433/gestion_rrhh";
    private static final String USER = "postgres";
    private static final String PASSWORD = "base1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
