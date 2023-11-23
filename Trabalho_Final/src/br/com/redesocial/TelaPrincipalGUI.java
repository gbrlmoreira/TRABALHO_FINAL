package br.com.redesocial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TelaPrincipalGUI {

    private static ScannerWrapper scannerWrapper;

    public static void main(String[] args) {
        scannerWrapper = new ScannerWrapper();

        JFrame frame = new JFrame("Rede Social Newton Paiva");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("------------------------\nRede Social Newton Paiva\n------------------------");
        titleLabel.setBounds(10, 20, 400, 40);
        panel.add(titleLabel);

        JButton cadastroButton = new JButton("Fazer cadastro");
        cadastroButton.setBounds(10, 80, 150, 25);
        panel.add(cadastroButton);

        JButton loginButton = new JButton("Fazer login");
        loginButton.setBounds(170, 80, 150, 25);
        panel.add(loginButton);

        JButton sairButton = new JButton("Sair");
        sairButton.setBounds(330, 80, 60, 25);
        panel.add(sairButton);

        ActionListener cadastroListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
            }
        };

        ActionListener loginListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        };

        ActionListener sairListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        cadastroButton.addActionListener(cadastroListener);
        loginButton.addActionListener(loginListener);
        sairButton.addActionListener(sairListener);
    }

    private static void fazerCadastro() {
        String nome = JOptionPane.showInputDialog("Digite o seu nome:");
        String email = JOptionPane.showInputDialog("Digite o seu email:");
        String senha = JOptionPane.showInputDialog("Digite a sua senha:");

        Usuario novoUsuario = new Usuario(nome, email, senha);
        boolean sucessoCadastro = novoUsuario.fazerRegistro(nome, email, senha);

        if (sucessoCadastro) {
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível realizar o cadastro.");
        }
    }

    private static void fazerLogin() {
        String email = JOptionPane.showInputDialog("Digite o seu email:");
        String senha = JOptionPane.showInputDialog("Digite a sua senha:");

        Usuario usuarioLogado = new Usuario(email, senha);
        boolean loginBemSucedido = usuarioLogado.fazerLogin();

        if (loginBemSucedido) {
            JOptionPane.showMessageDialog(null, "Login realizado com sucesso!\nSeu código de amizade é: " + usuarioLogado.getIdUsuario());
            exibirMenuUsuario(usuarioLogado);
        } else {
            JOptionPane.showMessageDialog(null, "Email ou senha incorretos. Login falhou.");
        }
    }

    private static void exibirMenuUsuario(Usuario usuario) {
        int opcao;
        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    "Menu:\n" +
                            "1. Adicionar amigo\n" +
                            "2. Listar amigos\n" +
                            "3. Excluir amigo\n" +
                            "4. Enviar mensagem\n" +
                            "5. Mostrar mensagens\n" +
                            "0. Sair\n" +
                            "Digite a opção desejada:"
            ));

            switch (opcao) {
                case 1:
                    int codigoAmigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do usuário que deseja adicionar:"));
                    usuario.adicionarAmigo(codigoAmigo);
                    break;
                case 2:
                    List<Usuario> amigos = usuario.listarAmigos();
                    StringBuilder amigosString = new StringBuilder("=== AMIGOS ===\n");
                    for (Usuario amigo : amigos) {
                        amigosString.append(amigo.getNome()).append("\n");
                    }
                    if (amigos.isEmpty()) {
                        amigosString.append("Não há amigos registrados em sua conta.");
                    }
                    JOptionPane.showMessageDialog(null, amigosString.toString());
                    break;
                case 3:
                    List<Usuario> amigosExcluir = usuario.listarAmigos();
                    StringBuilder amigosExcluirString = new StringBuilder("Seus amigos: \n");
                    for (int i = 0; i < amigosExcluir.size(); i++) {
                        amigosExcluirString.append((i + 1)).append(". ")
                                .append(amigosExcluir.get(i).getNome())
                                .append(" (Código: ").append(amigosExcluir.get(i).getIdUsuario()).append(")\n");
                    }

                    int indiceAmigo = Integer.parseInt(JOptionPane.showInputDialog(amigosExcluirString.toString() +
                            "Informe o número do amigo que deseja excluir:"));

                    if (indiceAmigo > 0 && indiceAmigo <= amigosExcluir.size()) {
                        Usuario amigoSelecionado = amigosExcluir.get(indiceAmigo - 1);
                        boolean resultado = usuario.excluirAmigo(amigoSelecionado.getIdUsuario());
                        if (resultado) {
                            JOptionPane.showMessageDialog(null, "A exclusão do amigo foi realizada com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "A exclusão do amigo não pôde ser concluída.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Número inválido.");
                    }
                    break;
                case 4:
                    List<Usuario> amigosEnviarMsg = usuario.listarAmigos();
                    if (amigosEnviarMsg.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Você não tem amigos para enviar mensagens.");
                    } else {
                        StringBuilder escolhaAmigoString = new StringBuilder("Selecione um amigo para enviar uma mensagem:\n");
                        for (int i = 0; i < amigosEnviarMsg.size(); i++) {
                            escolhaAmigoString.append((i + 1)).append(". ").append(amigosEnviarMsg.get(i).getNome()).append("\n");
                        }
                        int escolha = Integer.parseInt(JOptionPane.showInputDialog(escolhaAmigoString.toString()));
                        if (escolha < 1 || escolha > amigosEnviarMsg.size()) {
                            JOptionPane.showMessageDialog(null, "Escolha inválida.");
                        } else {
                            Usuario amigoEscolhido = amigosEnviarMsg.get(escolha - 1);
                            String conteudoMensagem = JOptionPane.showInputDialog("Digite sua mensagem para " + amigoEscolhido.getNome() + ":");
                            LocalDateTime agora = LocalDateTime.now();
                            Chat novaMensagem = new Chat(conteudoMensagem, usuario, amigoEscolhido);
                            novaMensagem.setDTA_ENVIO(agora);
                            novaMensagem.enviarMensagem();
                        }
                    }
                    break;
                case 5:
                    List<Usuario> amigosVerMensagens = usuario.listarAmigos();
                    if (amigosVerMensagens.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Você não tem amigos para ver as mensagens.");
                    } else {
                        StringBuilder escolhaAmigoString = new StringBuilder("Selecione um amigo para ver as mensagens:\n");
                        for (int i = 0; i < amigosVerMensagens.size(); i++) {
                            escolhaAmigoString.append((i + 1)).append(". ").append(amigosVerMensagens.get(i).getNome()).append("\n");
                        }
                        int escolhaAmigo = Integer.parseInt(JOptionPane.showInputDialog(escolhaAmigoString.toString()));

                        if (escolhaAmigo < 1 || escolhaAmigo > amigosVerMensagens.size()) {
                            JOptionPane.showMessageDialog(null, "Escolha inválida.");
                        } else {
                            Usuario amigoSelecionado = amigosVerMensagens.get(escolhaAmigo - 1);
                            List<Chat> mensagens = Chat.listarMensagens(usuario, amigoSelecionado);
                            if (mensagens.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Não há mensagens entre você e " + amigoSelecionado.getNome() + ".");
                            } else {
                                StringBuilder mensagensString = new StringBuilder();
                                for (Chat mensagem : mensagens) {
                                    mensagensString.append("De: ").append(mensagem.getCOD_REMETENTE().getNome()).append("\n")
                                            .append("Para: ").append(mensagem.getCOD_DESTINATARIO().getNome()).append("\n")
                                            .append("Mensagem: ").append(mensagem.getDES_MENSAGEM()).append("\n")
                                            .append("Data/Hora: ").append(mensagem.getDTA_ENVIO()).append("\n-----\n");
                                }
                                JOptionPane.showMessageDialog(null, mensagensString.toString());
                            }
                        }
                    }
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Desconectando...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private static class ScannerWrapper {
        private Scanner scanner;

        public ScannerWrapper() {
            this.scanner = new Scanner(System.in);
        }

        public int nextInt() {
            return Integer.parseInt(scanner.nextLine());
        }
    }
}
