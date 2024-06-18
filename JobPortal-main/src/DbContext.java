package PortalDeVagas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbContext {
    private Connection connection;

    public void conectarBanco() {
        try {
            // Carregar o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Estabelecer conexão com o banco de dados
            String url = "jdbc:postgresql://localhost:5432/portaldevagas";
            String user = "postgres";
            String password = "1302";
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void desconectarBanco() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean executarUpdateSql(String sql) throws SQLException {
        if (connection == null) {
            throw new SQLException("Conexão com o banco de dados não estabelecida.");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public ResultSet executarQuerySql(String sql) throws SQLException {
        if (connection == null) {
            throw new SQLException("Conexão com o banco de dados não estabelecida.");
        }

        PreparedStatement pstmt = connection.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public Connection getConnection() {
        return connection;
    }
}