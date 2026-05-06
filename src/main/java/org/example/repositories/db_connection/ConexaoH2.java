package org.example.repositories.db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoH2 {
    private static final String URL = "jdbc:h2:mem:escola;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String SENHA = "";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(URL, USER, SENHA);
    }
}
