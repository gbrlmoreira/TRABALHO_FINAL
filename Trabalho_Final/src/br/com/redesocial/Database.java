package br.com.redesocial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:postgresql://localhost:5432/Trabalho_Final";
    private static final String usuario = "postgre";
    private static final String senha = "123456";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    public void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("A conexão com o banco de dados foi encerrada.");
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao encerrar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}
