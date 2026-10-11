package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.User;
import dao.UserDAO;
import session.UserSession;

public class Login extends JFrame implements ActionListener
{
    JTextField txtUser;
    JPasswordField txtPwd;
    JButton btnLogin,btnReg,btnBack;

    Login()
    {
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,50);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Login Form");
        h2.setBounds(350,60,250,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/loginUser.png"));
        Image i2 = i1.getImage().getScaledInstance(300,300,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel lblIcon = new JLabel(i3);
        lblIcon.setBounds(550,50,360,360);
        add(lblIcon);

        // Label and Text Field for Username.
        JLabel lblUsername = new JLabel("Enter Username");
        lblUsername.setBounds(50,170,180,40);
        lblUsername.setFont(new Font("Serif",Font.BOLD,23));
        lblUsername.setForeground(new Color(50,50,50));
        add(lblUsername);

        txtUser = new JTextField();
        txtUser.setBounds(270,170,300,40);
        txtUser.setFont(new Font("Serif",Font.BOLD,25));
        txtUser.setBackground(Color.white);
        txtUser.setForeground(Color.black);
        txtUser.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtUser);

        // Label and Password Field for Password.
        JLabel lblPwd = new JLabel("Enter Password");
        lblPwd.setBounds(50,230,180,40);
        lblPwd.setFont(new Font("Serif",Font.BOLD,25));
        lblPwd.setForeground(new Color(50,50,50));
        add(lblPwd);

        txtPwd = new JPasswordField();
        txtPwd.setBounds(270,230,300,40);
        txtPwd.setFont(new Font("Serif",Font.BOLD,25));
        txtPwd.setBackground(Color.white);
        txtPwd.setForeground(Color.black);
        txtPwd.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtPwd);

        // Buttons.
        btnLogin = new JButton("Login");
        btnLogin.setBounds(250,330,150,50);
        btnLogin.setBackground(new Color(0,102,204));
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Serif",Font.BOLD,22));
        add(btnLogin);
        btnLogin.addActionListener(this);

        btnReg = new JButton("<html><u>Register 👉</u></html>");
        btnReg.setBounds(320,380,200,50);
        btnReg.setFont(new Font("Serif",Font.PLAIN,22));
        btnReg.setForeground(new Color(0,102,204));
        btnReg.setBorderPainted(false);
        btnReg.setContentAreaFilled(false);
        btnReg.setFocusPainted(false);
        btnReg.setOpaque(false);
        btnReg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnReg);
        btnReg.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(430,330,150,50);
        btnBack.setBackground(new Color(204,0,0));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Serif",Font.BOLD,22));
        add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new SplashScreen();
            }
        });

        setSize(900,500);
        setTitle("Secure Document Wallet - Login");
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnLogin)
        {
            String username = txtUser.getText().trim();
            String pwd = txtPwd.getText().trim();

            // Validation.
            if(username.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Username.");
                return;
            }
            if(pwd.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Password.");
                return;
            }

            // Set Usename and Password Model User To Login.
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(pwd);

            // Call Data Access Object to Verify User.
            UserDAO loginUserDao = new UserDAO();
            User loginUser = loginUserDao.loginUser(user); // Checks Data with DAO.
            if(loginUser != null)
            {
                // Creates User Session.
                UserSession.setUserId(loginUser.getUserId());
                UserSession.setUsername(loginUser.getUsername());

                JOptionPane.showMessageDialog(null, "Login Successful. Welcome, "+loginUser.getUsername());
                setVisible(false);
                new Dashboard(loginUser);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Username and Password.");
            }
        }
        else if(e.getSource() == btnReg)
        {
            setVisible(false);
            new Register();
        }
    }
}