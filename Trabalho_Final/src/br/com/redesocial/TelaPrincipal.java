package br.com.redesocial;

import br.com.redesocial.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    //ELEMENTS LOGIN
    private JPanel body = new JPanel();
    private JPanel sectionHome = new JPanel(new BorderLayout());
    private JPanel divMenu = new JPanel();
    private JPanel divContent = new JPanel();
    private JLabel jLabelTitulo = new JLabel("Bem-vindo, Nome Usuário"); //MENSAGEM
    private JLabel jLabelPageHome = new JLabel(""); //MENSAGEM
    private JButton chatButton = new JButton("Chat");
    private JPanel divRepeatingGroup = new JPanel();
    private JButton perfilButton = new JButton("Meu Perfil");
    private JButton listUsereButton = new JButton("Meus amigos");
    private JButton findUsereButton = new JButton("Adicionar amigos");
    private JButton logoutButton = new JButton("Logout");
    JLabel logoArroba = new JLabel(new ImageIcon("./src/main/resources/img/arrobaiconwhite.png"));    //LOGOS DO SISTEMA

    public static void main(String[] args, User currentUser) {
        Service service = new Service();
        service.setUp();
        new HomeUI(currentUser, service);
    }

    public HomeUI(User currentUser, Service service){
        chatButton(currentUser,service);//INICIA A PAGINA COM O SECTION HOME VISIVEL
        menu();//METODO RESPONSAVEL POR CONSTRUIR O MENU

        Font fonte = jLabelPageHome.getFont();
        Font fonteMaior = new Font(fonte.getName(), fonte.getStyle(), 24);
        jLabelPageHome.setFont(fonteMaior);
        jLabelTitulo.setText("Bem-vindo, " + currentUser.getNome());
        divContent.setBackground(new Color(255, 255, 255));
        sectionHome.add(divMenu, BorderLayout.WEST);
        sectionHome.add(divContent, BorderLayout.CENTER);

        body.setLayout(new GridLayout(1, 1, 20, 0));
        body.setBackground(new Color(255, 255, 255));
        body.add(sectionHome);


        this.setTitle("arrob@ - Home");
        this.setSize(1100,800);
        this.setResizable(false);
        this.getContentPane().add(body);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //DEFINE PARAMETROS DE LAYOUT DO SHOW MENSAGEM DIALOG
        UIManager.put("OptionPane.background", new Color(255, 255, 255));
        UIManager.getLookAndFeelDefaults().put("Panel.background", new Color(255, 255, 255));

        //BOTOES

        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perfilButton(currentUser, service);
            }

        });
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatButton(currentUser, service);
            }
        });
        listUsereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listUsereButton(currentUser, service);
            }
        });
        findUsereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findUsereButton(currentUser,service);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Icon iconLogout = new ImageIcon("./src/main/resources/img/exit.gif");
                UIManager.put("OptionPane.questionIcon", iconLogout);
                int confirmLogout = JOptionPane.showConfirmDialog(HomeUI.this, "Tem certeza de que deseja fazer logout?", "Confirmação de Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirmLogout == JOptionPane.YES_OPTION) {
                    // O usuário clicou em "Sim", faça o logout
                    logoutButton(service);
                }


            }
        });

    }

    //METODOS
    private void menu() {//METODO RESPONSAVEL POR CONSTRUIR O MENU


        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setVgap(20);
        divMenu.setLayout(layout);
        divMenu.setPreferredSize(new Dimension(200, 0));
        divMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
        divMenu.setBackground(new Color(59, 74, 178));

        divMenu.add(logoArroba).setPreferredSize(new Dimension(100, 100));
        divMenu.add(jLabelTitulo).setForeground(Color.white);
        jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        divMenu.add(chatButton).setPreferredSize(new Dimension(150, 35));
        divMenu.add(perfilButton).setPreferredSize(new Dimension(150, 35));
        divMenu.add(listUsereButton).setPreferredSize(new Dimension(150, 35));
        divMenu.add(findUsereButton).setPreferredSize(new Dimension(150, 35));
        divMenu.add(logoutButton).setPreferredSize(new Dimension(150, 35));


    }
    private void chatButton(User currentUser, Service service) {
        removeContent();

        jLabelPageHome.setText("Chat");
        jLabelPageHome.setBackground(new Color(15, 173, 53));

        divContent.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divContent.add(jLabelPageHome);
        divContent.add(divRepeatingGroup);

        divRepeatingGroup.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divRepeatingGroup.setPreferredSize(new Dimension(800, 700));
        divRepeatingGroup.setBackground(new Color(255, 255, 255));

        //DISPLAY LIST
        var chats = service.listChats(currentUser);


        for (Chat chat : chats) {
            Integer codChat = chat.getCodChat();
            User user1 = chat.getUser1();
            User user2 = chat.getUser2();
            String nameChat;

            if(user1.getCodUser() == currentUser.getCodUser()){
                nameChat = user2.getNome();
            }else{
                nameChat = user1.getNome();
            }

            JLabel imgUser = new JLabel(new ImageIcon("./src/main/resources/img/imguser.png"));    //LOGOS DO SISTEMA

            JPanel divRGCellPerfil = new JPanel();
            divRGCellPerfil.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
            divRGCellPerfil.setPreferredSize(new Dimension(390, 70));
            divRGCellPerfil.setBackground(new Color(240, 244, 253));

            JPanel divRGCellBtns = new JPanel();
            divRGCellBtns.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
            divRGCellBtns.setPreferredSize(new Dimension(390, 70));
            divRGCellBtns.setBackground(new Color(240, 244, 253));

            JPanel divRGCell = new JPanel();
            divRGCell.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            divRGCell.setPreferredSize(new Dimension(800, 75));
            divRGCell.setBackground(new Color(240, 244, 253));

            JLabel jLabelNameUser = new JLabel(nameChat); //MENSAGEM
            JButton chatUserButton = new JButton(String.valueOf("Chat"));

            divRGCellPerfil.add(imgUser);
            divRGCellPerfil.add(jLabelNameUser);
            divRGCellBtns.add(chatUserButton);
            divRGCell.add(divRGCellPerfil);
            divRGCell.add(divRGCellBtns);
            divRepeatingGroup.add(divRGCell);

            chatUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chatInButton(chat, service, currentUser);
                }
            });


        }

        refreshUI();
    }
    private void chatInButton(Chat Chat, Service service, User currentUser) {
        removeContent();
        User user1 = Chat.getUser1();
        User user2 = Chat.getUser2();
        String nameChat;
        if(user1.getCodUser() == currentUser.getCodUser()){
            nameChat = user2.getNome();
        }else{
            nameChat = user1.getNome();
        }

        jLabelPageHome.setText("Chat - " + nameChat);
        jLabelPageHome.setBackground(new Color(15, 173, 53));

        divContent.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divContent.add(jLabelPageHome);
        divContent.add(divRepeatingGroup);

        divRepeatingGroup.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divRepeatingGroup.setPreferredSize(new Dimension(800, 600));
        divRepeatingGroup.setBackground(new Color(255, 255, 255));




        var chat = service.listMessageChat(Chat.getCodChat());


        for (Message message : chat) {
            int codMessage = message.getCodMessage();
            User remetente = message.getRemetente();
            String desc = message.getDescMensagem();

            if(remetente.getCodUser() == service.getCurrentUser().getCodUser()){

                JLabel imgUser = new JLabel(new ImageIcon("./src/main/resources/img/imguser.png"));    //LOGOS DO SISTEMA

                JPanel divRGCellPerfil = new JPanel();
                divRGCellPerfil.setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
                divRGCellPerfil.setPreferredSize(new Dimension(800, 70));
                divRGCellPerfil.setBackground(new Color(240, 244, 253));

                JPanel divRGCellDesc = new JPanel();
                divRGCellDesc.setLayout(new GridLayout(2, 1, 0, 0));
                divRGCellDesc.setPreferredSize(new Dimension(300, 40));
                divRGCellDesc.setBackground(new Color(240, 244, 253));

                JPanel divRGCell = new JPanel();
                divRGCell.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                divRGCell.setPreferredSize(new Dimension(800, 75));
                divRGCell.setBackground(new Color(240, 244, 253));

                JLabel jLabelNameUser = new JLabel(remetente.getNome()); //MENSAGEM
                JLabel jLabelDescMsg = new JLabel(desc); //MENSAGEM
                jLabelNameUser.setHorizontalAlignment(SwingConstants.RIGHT);
                jLabelDescMsg.setHorizontalAlignment(SwingConstants.RIGHT);


                divRGCellDesc.add(jLabelNameUser);
                divRGCellDesc.add(jLabelDescMsg);
                divRGCell.add(divRGCellPerfil);
                divRGCellPerfil.add(divRGCellDesc);
                divRGCellPerfil.add(imgUser);
                divRepeatingGroup.add(divRGCell);

            } else{
                JLabel imgUser = new JLabel(new ImageIcon("./src/main/resources/img/imguser.png"));    //LOGOS DO SISTEMA

                JPanel divRGCellPerfil = new JPanel();
                divRGCellPerfil.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
                divRGCellPerfil.setPreferredSize(new Dimension(800, 70));
                divRGCellPerfil.setBackground(new Color(240, 244, 253));

                JPanel divRGCellDesc = new JPanel();
                divRGCellDesc.setLayout(new GridLayout(2, 1, 0, 0));
                divRGCellDesc.setPreferredSize(new Dimension(300, 40));
                divRGCellDesc.setBackground(new Color(240, 244, 253));

                JPanel divRGCell = new JPanel();
                divRGCell.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                divRGCell.setPreferredSize(new Dimension(800, 75));
                divRGCell.setBackground(new Color(240, 244, 253));

                JLabel jLabelNameUser = new JLabel(remetente.getNome()); //MENSAGEM
                JLabel jLabelDescMsg = new JLabel(desc); //MENSAGEM



                divRGCellPerfil.add(imgUser);
                divRGCellDesc.add(jLabelNameUser);
                divRGCellDesc.add(jLabelDescMsg);
                divRGCell.add(divRGCellPerfil);
                divRGCellPerfil.add(divRGCellDesc);
                divRepeatingGroup.add(divRGCell);
            }


        }

        JPanel divsendmsg = new JPanel();
        divsendmsg.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        divsendmsg.setBackground(new Color(255,255,255));

        JTextField sendMsgBox = new JTextField();
        sendMsgBox.setColumns(60);

        JButton sendMessageBtn = new JButton("Enviar");

        divsendmsg.add(sendMsgBox);
        divsendmsg.add(sendMessageBtn);

        divContent.add(divsendmsg);
        sendMessageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.sendMessage(Chat,sendMsgBox.getText(),currentUser);
                chatInButton(Chat,service,currentUser);
            }
        });

        refreshUI();

    }
    private void perfilButton(User currentUser, Service service) {

        removeContent();

        jLabelPageHome.setText("Meu Perfil");

        JPanel sectionCreateAccount = new JPanel();
        JPanel divCredential = new JPanel();
        JPanel divName = new JPanel();
        JPanel divEmail = new JPanel();
        JPanel divPassword = new JPanel();
        JPanel divBtns = new JPanel();
        JLabel loginPlaceHolderName= new JLabel("Nome:");
        JTextField loginUserName = new JTextField(currentUser.getNome());
        JLabel loginPlaceHolderEmail= new JLabel("Email:");
        JTextField loginUserEmail = new JTextField(currentUser.getEmail());
        JLabel loginPlaceHolderPassword = new JLabel("Senha:");
        JPasswordField loginPassword = new JPasswordField(new String(currentUser.getSenha()));
        JButton updateButton = new JButton("Salvar Alterações");


        JLabel logoAccount = new JLabel(new ImageIcon("./src/main/resources/img/arrobaicon.png"));    //LOGOS DO SISTEMA

        Color color = new Color(255, 255, 255);

        divName.setBackground(color);
        divName.add(loginPlaceHolderName);
        divName.add(loginUserName);
        loginUserName.setColumns(20);

        divEmail.setBackground(color);
        divEmail.add(loginPlaceHolderEmail);
        divEmail.add(loginUserEmail);
        loginUserEmail.setColumns(20);

        divPassword.setBackground(color);
        divPassword.add(loginPlaceHolderPassword);
        divPassword.add(loginPassword);
        loginPassword.setColumns(20);

        divCredential.setLayout(new GridLayout(4, 1));
        divCredential.setBackground(color);
        divCredential.add(divName);
        divCredential.add(divEmail);
        divCredential.add(divPassword);

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setVgap(20);
        divBtns.setLayout(layout);
        divBtns.setBackground(color);
        divBtns.add(updateButton).setPreferredSize(new Dimension(150, 35));

        sectionCreateAccount.setLayout(new GridLayout(3, 3, 20, 25));
        sectionCreateAccount.setBackground(color);
        sectionCreateAccount.add(logoAccount);
        sectionCreateAccount.add(divCredential);
        sectionCreateAccount.add(divBtns);

        divContent.add(jLabelPageHome);
        divContent.add(sectionCreateAccount);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.updateCurrentUser(loginUserName.getText(),loginUserEmail.getText(),loginPassword.getPassword(), currentUser);
                dispose();
                HomeUI homeUI = new HomeUI(currentUser, service);

            }
        });

        refreshUI();

    }
    private void listUsereButton(User currentUser, Service service) {
        removeContent();

        jLabelPageHome.setText("Meus Amigos");
        jLabelPageHome.setBackground(new Color(15, 173, 53));


        divContent.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divContent.add(jLabelPageHome);
        divContent.add(divRepeatingGroup);

        divRepeatingGroup.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divRepeatingGroup.setPreferredSize(new Dimension(800, 700));
        divRepeatingGroup.setBackground(new Color(255, 255, 255));


        var users = service.listUsersFriends(currentUser);

        for (User user : users) {
            int idUser = user.getCodUser();
            String nome = user.getNome();
            String email = user.getEmail();

            JLabel imgUser = new JLabel(new ImageIcon("./src/main/resources/img/imguser.png"));    //LOGOS DO SISTEMA

            JPanel divRGCellPerfil = new JPanel();
            divRGCellPerfil.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
            divRGCellPerfil.setPreferredSize(new Dimension(390, 70));
            divRGCellPerfil.setBackground(new Color(240, 244, 253));

            JPanel divRGCellBtns = new JPanel();
            divRGCellBtns.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
            divRGCellBtns.setPreferredSize(new Dimension(390, 70));
            divRGCellBtns.setBackground(new Color(240, 244, 253));

            JPanel divRGCell = new JPanel();
            divRGCell.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            divRGCell.setPreferredSize(new Dimension(800, 75));
            divRGCell.setBackground(new Color(240, 244, 253));

            JLabel jLabelNameUser = new JLabel(nome); //MENSAGEM
            JButton removeButton = new JButton(String.valueOf("Remover"));
            JButton chatUserButton = new JButton(String.valueOf("Chat"));

            divRGCellPerfil.add(imgUser);
            divRGCellPerfil.add(jLabelNameUser);
            divRGCellBtns.add(removeButton);
            divRGCellBtns.add(chatUserButton);
            divRGCell.add(divRGCellPerfil);
            divRGCell.add(divRGCellBtns);
            divRepeatingGroup.add(divRGCell);

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Icon iconRemoveUser = new ImageIcon("./src/main/resources/img/removeUser.gif");
                    UIManager.put("OptionPane.questionIcon", iconRemoveUser);
                    int confirmRemoveUser = JOptionPane.showConfirmDialog(HomeUI.this, "Tem certeza de que deseja remover amizade?", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (confirmRemoveUser == JOptionPane.YES_OPTION) {
                        service.removeFriend(currentUser, user);
                        HomeUI homeUI = new HomeUI(currentUser, service);
                        listUsereButton(currentUser, service);
                        dispose();

                    }
                }
            });

            chatUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Chat chatListFriend = service.openCreateChat(currentUser, user);
                    chatInButton(chatListFriend, service, currentUser);

                }
            });
        }

        refreshUI();



    }
    private void findUsereButton(User currentUser, Service service) {
        removeContent();

        jLabelPageHome.setText("Adicionar amigos");

        JPanel divsearchBox = new JPanel();
        divsearchBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        divsearchBox.setBackground(new Color(255,255,255));

        JTextField searchBox = new JTextField();
        searchBox.setColumns(30);

        divsearchBox.add(jLabelPageHome);

        divContent.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divContent.add(divsearchBox);
        divContent.add(divRepeatingGroup);

        divRepeatingGroup.setLayout(new FlowLayout(FlowLayout.CENTER,400,20));
        divRepeatingGroup.setPreferredSize(new Dimension(800, 500));
        divRepeatingGroup.setBackground(new Color(255, 255, 255));

        //DISPLAY LIST
        var users = service.allUser(currentUser);

        //DISPLAY LIST

        for (User user : users) {
            int idUser = user.getCodUser();
            String nome = user.getNome();
            String email = user.getEmail();

            JLabel imgUser = new JLabel(new ImageIcon("./src/main/resources/img/imguser.png"));    //LOGOS DO SISTEMA

            JPanel divRGCellPerfil = new JPanel();
            divRGCellPerfil.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
            divRGCellPerfil.setPreferredSize(new Dimension(390, 70));
            divRGCellPerfil.setBackground(new Color(240, 244, 253));

            JPanel divRGCellBtns = new JPanel();
            divRGCellBtns.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
            divRGCellBtns.setPreferredSize(new Dimension(390, 70));
            divRGCellBtns.setBackground(new Color(240, 244, 253));

            JPanel divRGCell = new JPanel();
            divRGCell.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            divRGCell.setPreferredSize(new Dimension(800, 75));
            divRGCell.setBackground(new Color(240, 244, 253));

            JLabel jLabelNameUser = new JLabel(nome); //MENSAGEM
            JButton addUserButton = new JButton(String.valueOf("Adicionar"));

            divRGCellPerfil.add(imgUser);
            divRGCellPerfil.add(jLabelNameUser);
            divRGCellBtns.add(addUserButton);
            divRGCell.add(divRGCellPerfil);
            divRGCell.add(divRGCellBtns);
            divRepeatingGroup.add(divRGCell);

            addUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    service.addFriend(currentUser, user);
                    HomeUI homeUI = new HomeUI(currentUser, service);
                    findUsereButton(currentUser, service);
                    dispose();

                }
            });
        }



        refreshUI();




    }
    private void logoutButton(Service service) {

        LoginUI loginUI = new LoginUI(service);
        service.setCurrentUser(null);
        dispose();
    }
    private void removeContent() {
        divContent.removeAll();
        divRepeatingGroup.removeAll();
    }
    private void refreshUI() {
        divContent.revalidate();
        divContent.repaint();
    }

}
