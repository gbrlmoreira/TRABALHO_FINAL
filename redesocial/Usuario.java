import br.com.redesocial.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String email;
    private String senha;

    public Usuario(int idUsuario, String nomeUsuario, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
    }
}

    // Getters e setters omitidos para reduzir o código, mas você deve mantê-los conforme necessário.

    // Método para realizar o login
    public boolean fazerLogin(String email, String senha) {
        DB database = new DB();
        try (Connection conexao = database.conectar()) {
            String query = "SELECT * FROM usuario WHERE DES_EMAIL = ? AND DES_SENHA = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, senha);
                ResultSet resultado = statement.executeQuery();

                if (resultado.next()) {
                    // Se encontrarmos um usuário com o e-mail e senha fornecidos, definimos seus atributos e retornamos true
                    setAtributos(resultado);
                    return true;
                } else {
                    // Se não encontrarmos um usuário com o e-mail e senha fornecidos, retornamos false
                    return false;
                }
            }
        } catch (SQLException e) {
            // Em caso de erro ao executar a consulta SQL, imprimimos a mensagem de erro e retornamos false
            System.out.println("Erro ao fazer login: " + e.getMessage());
            return false;
        }
    }

    // Método para realizar o registro
    public boolean fazerRegistro(String DES_EMAIL, String DES_SENHA, String NOM_USUARIO) {
        DB bancoDeDados = new DB();
        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "INSERT INTO usuario (NOM_USUARIO, DES_EMAIL, DES_SENHA) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setString(1, NOM_USUARIO);
                statement.setString(2, DES_EMAIL);
                statement.setString(3, DES_SENHA);
                int resultado = statement.executeUpdate();

                return resultado > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fazer registro: " + e.getMessage());
            return false;
        }
    }

    // Método para adicionar amigo
    public void adicionarAmigo(int COD_AMIGO) {
        DB bancoDeDados = new DB();
        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "INSERT INTO amigo (COD_USUARIO, COD_AMIGO) VALUES (?, ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, this.COD_USUARIO);
                statement.setInt(2, COD_AMIGO);
                int linhasAfetadas = statement.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Amigo adicionado com sucesso!");
                } else {
                    System.out.println("Falha ao adicionar amigo.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar amigo: " + e.getMessage());
        }
    }

    // Método para listar amigos
    public List<Usuario> listarAmigos() {
        DB database = new DB();
        List<Usuario> amigos = new ArrayList<>();

        try (Connection conexao = database.conectar()) {
            String query = "SELECT u.COD_USUARIO, u.NOM_USUARIO FROM AMIGO a JOIN USUARIO u ON a.COD_AMIGO = u.COD_USUARIO WHERE a.COD_USUARIO = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, this.COD_USUARIO);
                try (ResultSet resultado = statement.executeQuery()) {
                    while (resultado.next()) {
                        int codAmigo = resultado.getInt("COD_USUARIO");
                        Usuario amigo = getUsuarioByCod(conexao, codAmigo);
                        amigos.add(amigo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar amigos: " + e.getMessage());
        }

        return amigos;
    }

    // Método para excluir amigo
    public boolean excluirAmigo(int COD_AMIGO) {
        DB database = new DB();
        boolean sucesso = false;

        // Primeiro, verificamos se a amizade existe
        if (isAmigo(COD_AMIGO)) {
            String query = "DELETE FROM amigo WHERE (COD_USUARIO = ? AND COD_AMIGO = ?) OR (COD_USUARIO = ? AND COD_AMIGO = ?)";
            try (Connection conexao = database.conectar(); PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, this.COD_USUARIO);
                statement.setInt(2, COD_AMIGO);
                statement.setInt(3, COD_AMIGO);
                statement.setInt(4, this.COD_USUARIO);

                int linhasAfetadas = statement.executeUpdate();

                sucesso = linhasAfetadas > 0;
            } catch (SQLException e) {
                System.out.println("Erro ao excluir amigo: " + e.getMessage());
            }
        }

        return sucesso;
    }

    // Método privado para verificar se dois usuários são amigos
    private boolean isAmigo(int COD_AMIGO) {
        DB database = new DB();
        boolean saoAmigos = false;

        String query = "SELECT COUNT(1) FROM amigo WHERE (COD_USUARIO = ? AND COD_AMIGO = ?) OR (COD_USUARIO = ? AND COD_AMIGO = ?)";
        try (Connection conexao = database.conectar(); PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setInt(1, this.COD_USUARIO);
            statement.setInt(2, COD_AMIGO);
            statement.setInt(3, COD_AMIGO);
            statement.setInt(4, this.COD_USUARIO);

            try (ResultSet resultado = statement.executeQuery()) {
                saoAmigos = resultado.next() && resultado.getInt(1) > 0;
            }
        } catch (
