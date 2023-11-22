package br.com.redesocial;

import java.sql.*;

public class DB {
    private static final String url = "jdbc:postgresql://localhost:5432/Trabalho_Final";
    private static final String user = "postgres";
    private static final String password = "123456";

    public Connection conectar() {
        try {
            Connection conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o banco de dados estabelecida.");
            return conexao;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    public void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException e) {
                System.out.println("Erro ao encerrar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}
