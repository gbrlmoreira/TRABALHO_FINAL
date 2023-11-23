package br.com.redesocial;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("------------------------");
        System.out.println("Rede Social Newton Paiva");
        System.out.println("------------------------");
        boolean sair = false;
        Usuario usuarioLogado = null;

        while (!sair) {
            // Exibir o menu de opções
            System.out.println("\nMenu:");
            System.out.println("1. Fazer cadastro");
            System.out.println("2. Fazer login");
            System.out.println("0. Sair");

            System.out.print("Digite a opção desejada: ");
            int opcao1 = in.nextInt();
            in.nextLine(); // Limpar o buffer do scanner

            switch (opcao1) {
                case 1:
                    System.out.print("Digite o seu nome: ");
                    String nomeCadastro = in.nextLine();
                    System.out.print("Digite o seu email: ");
                    String emailCadastro = in.nextLine();
                    System.out.print("Digite a sua senha: ");
                    String senhaCadastro = in.nextLine();

                    Usuario novoUsuario = new Usuario();
                    boolean sucessoCadastro = novoUsuario.fazerRegistro();

                    if (sucessoCadastro) {
                        System.out.println("Cadastro realizado com sucesso!");
                    } else {
                        System.out.println("Não foi possível realizar o cadastro.");
                    }
                    break;
                case 2:
                    // Solicitar email e senha ao usuário
                    System.out.print("Digite o seu email: ");
                    String email = in.nextLine();
                    System.out.print("Digite a sua senha: ");
                    String senha = in.nextLine();

                    // Fazer login
                    usuarioLogado = new Usuario(email, senha);
                    boolean loginBemSucedido = usuarioLogado.fazerLogin();

                    if (loginBemSucedido) {
                        System.out.println("Login realizado com sucesso!");
                        System.out.println("Seu código de amizade é: " + usuarioLogado.getIdUsuario());
                        exibirMenuUsuario(in, usuarioLogado);
                    } else {
                        System.out.println("Email ou senha incorretos. Login falhou.");
                    }
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        in.close();
    }

    private static void exibirMenuUsuario(Scanner scanner, Usuario usuario) {
        int opcao;
        do {
            // Exibir o menu de opções
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar amigo");
            System.out.println("2. Listar amigos");
            System.out.println("3. Excluir amigo");
            System.out.println("4. Enviar mensagem");
            System.out.println("5. Mostrar mensagens");
            System.out.println("0. Sair");
            System.out.print("Digite a opção desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.println("Digite o código do usuário que deseja adicionar: ");
                    int codigoAmigo = scanner.nextInt();
                    usuario.adicionarAmigo(codigoAmigo);
                    break;
                case 2:
                    List<Usuario> amigos = usuario.listarAmigos();
                    System.out.println("=== AMIGOS ===");
                    for (Usuario amigo : amigos) {
                        System.out.println(amigo.getNome());
                    }
                    if (amigos.isEmpty()) {
                        System.out.println("Não há amigos registrados em sua conta.");
                    }
                    break;

                case 3:
                    List<Usuario> amigosExcluir = usuario.listarAmigos();
                    System.out.println("Seus amigos: ");
                    for (int i = 0; i < amigosExcluir.size(); i++) {
                        System.out.println((i + 1) + ". " + amigosExcluir.get(i).getNome() + " (Código: " + amigosExcluir.get(i).getIdUsuario() + ")");
                    }

                    // Pedir ao usuário para escolher um amigo para excluir
                    System.out.println("Informe o número do amigo que deseja excluir: ");
                    int indiceAmigo = scanner.nextInt();

                    if (indiceAmigo > 0 && indiceAmigo <= amigosExcluir.size()) {
                        Usuario amigoSelecionado = amigosExcluir.get(indiceAmigo - 1);
                        boolean resultado = usuario.excluirAmigo(amigoSelecionado.getIdUsuario());
                        if (resultado) {
                            System.out.println("A exclusão do amigo foi realizada com sucesso.");
                        } else {
                            System.out.println("A exclusão do amigo não pôde ser concluída.");
                        }
                    } else {
                        System.out.println("Número inválido.");
                    }

                    break;

                case 4:
                    List<Usuario> amigosEnviarMsg = usuario.listarAmigos();
                    if (amigosEnviarMsg.isEmpty()) {
                        System.out.println("Você não tem amigos para enviar mensagens.");
                    } else {
                        System.out.println("Selecione um amigo para enviar uma mensagem:");
                        for (int i = 0; i < amigosEnviarMsg.size(); i++) {
                            System.out.println((i + 1) + ". " + amigosEnviarMsg.get(i).getNome());
                        }
                        int escolha = scanner.nextInt();
                        scanner.nextLine();

                        if (escolha < 1 || escolha > amigosEnviarMsg.size()) {
                            System.out.println("Escolha inválida.");
                        } else {
                            Usuario amigoEscolhido = amigosEnviarMsg.get(escolha - 1);

                            System.out.println("Digite sua mensagem para " + amigoEscolhido.getNome() + ":");
                            String conteudoMensagem = scanner.nextLine();

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
                        System.out.println("Você não tem amigos para ver as mensagens.");
                    } else {
                        System.out.println("Selecione um amigo para ver as mensagens:");
                        for (int i = 0; i < amigosVerMensagens.size(); i++) {
                            System.out.println((i + 1) + ". " + amigosVerMensagens.get(i).getNome());
                        }
                        int escolhaAmigo = scanner.nextInt();
                        scanner.nextLine();

                        if (escolhaAmigo < 1 || escolhaAmigo > amigosVerMensagens.size()) {
                            System.out.println("Escolha inválida.");
                        } else {
                            Usuario amigoSelecionado = amigosVerMensagens.get(escolhaAmigo - 1);

                            List<Chat> mensagens = Chat.listarMensagens(usuario, amigoSelecionado);
                            if (mensagens.isEmpty()) {
                                System.out.println("Não há mensagens entre você e " + amigoSelecionado.getNome() + ".");
                            } else {
                                for (Chat mensagem : mensagens) {
                                    System.out.println("De: " + mensagem.getCOD_REMETENTE().getNome());
                                    System.out.println("Para: " + mensagem.getCOD_DESTINATARIO().getNome());
                                    System.out.println("Mensagem: " + mensagem.getDES_MENSAGEM());
                                    System.out.println("Data/Hora: " + mensagem.getDTA_ENVIO());
                                    System.out.println("-----");
                                }
                            }
                        }
                    }
                    break;

                case 0:
                    System.out.println("Desconectando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }
}
