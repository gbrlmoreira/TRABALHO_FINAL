package br.com.redesocial;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private long idmensagem;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private Usuario remetente;
    private Usuario destinatario;

    public Chat(int idmensagem, String conteudo, LocalDateTime dataEnvio, Usuario remetente, Usuario destinatario) {
        this.idmensagem = idmensagem;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }

    public Chat(String conteudo, Usuario remetente, Usuario destinatario) {
        this.conteudo = conteudo;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }

    public long getIdmensagem() {
        return idmensagem;
    }

    public void setIdmensagem(long idmensagem) {
        this.idmensagem = idmensagem;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }
    // Método para enviar mensagem
    public void enviarMensagem() {
        Database bancoDeDados = new Database();
        Connection conexao = bancoDeDados.conectar();

        String query = "INSERT INTO mensagem (DES_MENSAGEM, DTA_ENVIO, COD_REMETENTE, COD_DESTINATARIO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, this.conteudo);
            statement.setTimestamp(2, Timestamp.valueOf(this.dataEnvio));
            statement.setInt(3, remetente.getCOD_USUARIO());
            statement.setInt(4, destinatario.getCOD_USUARIO());
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                // Se a operação de envio de mensagem for bem-sucedida, obtemos o ID gerado automaticamente e o definimos como atributo da mensagem
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.idmensagem = rs.getInt(1);
                    }
                }
                System.out.println("Mensagem Enviada!");
            } else {
                System.out.println("Mensagem Não Enviada!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    // Método para listar mensagens
    public static List<Chat> listarMensagens(Usuario remetente, Usuario destinatario) {
        Database bancoDeDados = new Database();
        Connection conexao = bancoDeDados.conectar();

        String query = "SELECT COD_MENSAGEM, DES_MENSAGEM, DTA_ENVIO FROM mensagem WHERE (COD_REMETENTE = ? AND COD_DESTINATARIO = ?) OR (COD_REMETENTE = ? AND COD_DESTINATARIO = ?) ORDER BY DTA_ENVIO ASC";
        try (PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setInt(1, remetente.getCOD_USUARIO());
            statement.setInt(2, destinatario.getCOD_USUARIO());
            statement.setInt(3, destinatario.getCOD_USUARIO());
            statement.setInt(4, remetente.getCOD_USUARIO());
            try (ResultSet resultado = statement.executeQuery()) {
                List<Chat> mensagens = new ArrayList<>();
                while (resultado.next()) {
                    int id = resultado.getInt("COD_MENSAGEM");
                    String conteudo = resultado.getString("DES_MENSAGEM");
                    LocalDateTime dataHora = resultado.getTimestamp("DTA_ENVIO").toLocalDateTime();
                    Chat mensagem = new Chat(id, conteudo, dataHora, remetente, destinatario);
                    mensagens.add(mensagem);
                }
                return mensagens;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar mensagens: " + e.getMessage());
            return null;
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }
}