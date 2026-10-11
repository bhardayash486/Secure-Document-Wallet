package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.User;
import dao.UserDAO;

public class Register extends JFrame implements ActionListener
{
    JTextField txtUser;
    JPasswordField txtPwd,txtConfirm;
    JButton btnReg,btnLogin,btnBack;

    Register()
    {
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,50);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Register Form");
        h2.setBounds(330,60,250,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/registerUser.png"));
        Image i2 = i1.getImage().getScaledInstance(300,300,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel icon = new JLabel(i3);
        icon.setBounds(550,50,360,360);
        add(icon);

        // Label and Text Field for Username.
        JLabel lblUsername = new JLabel("Enter Username");
        lblUsername.setBounds(50,150,180,40);
        lblUsername.setFont(new Font("Serif",Font.BOLD,23));
        lblUsername.setForeground(new Color(50,50,50));
        add(lblUsername);

        txtUser = new JTextField();
        txtUser.setBounds(270,150,300,40);
        txtUser.setFont(new Font("Serif",Font.BOLD,25));
        txtUser.setBackground(Color.white);
        txtUser.setForeground(Color.black);
        txtUser.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtUser);

        // Label and Password Field for Password.
        JLabel lblPwd = new JLabel("Enter Password");
        lblPwd.setBounds(50,200,180,40);
        lblPwd.setFont(new Font("Serif",Font.BOLD,23));
        lblPwd.setForeground(new Color(50,50,50));
        add(lblPwd);

        txtPwd = new JPasswordField();
        txtPwd.setBounds(270,200,300,40);
        txtPwd.setFont(new Font("Serif",Font.BOLD,25));
        txtPwd.setBackground(Color.white);
        txtPwd.setForeground(Color.black);
        txtPwd.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtPwd);

        // Label and Password Field for Confirm Password.
        JLabel lblCpwd = new JLabel("Confirm Password");
        lblCpwd.setBounds(50,250,230,40);
        lblCpwd.setFont(new Font("Serif",Font.BOLD,23));
        lblCpwd.setForeground(new Color(50,50,50));
        add(lblCpwd);

        txtConfirm = new JPasswordField();
        txtConfirm.setBounds(270,250,300,40);
        txtConfirm.setFont(new Font("Serif",Font.BOLD,25));
        txtConfirm.setBackground(Color.white);
        txtConfirm.setForeground(Color.black);
        txtConfirm.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtConfirm);

        // Register, Login & Back Button.
        btnReg = new JButton("Register");
        btnReg.setBounds(250,330,150,50);
        btnReg.setBackground(new Color(0,102,204));
        btnReg.setForeground(Color.white);
        btnReg.setFont(new Font("Serif",Font.BOLD,22));
        btnReg.addActionListener(this);
        add(btnReg);

        btnLogin = new JButton("<html><u>Back to Login</u></html>");
        btnLogin.setBounds(320,380,200,50);
        btnLogin.setFont(new Font("Serif",Font.PLAIN,22));
        btnLogin.setForeground(new Color(0,102,204));
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(this);
        add(btnLogin);

        btnBack = new JButton("Back");
        btnBack.setBounds(430,330,150,50);
        btnBack.setBackground(new Color(204,0,0));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Serif",Font.BOLD,22));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new SplashScreen();
            }
        });
        add(btnBack);

        setSize(900,500);
        setTitle("Secure Document Wallet - Register");
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Register();
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnReg)
        {
            String username = txtUser.getText().trim();
            String pwd = txtPwd.getText().trim();
            String cpwd = txtConfirm.getText().trim();

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
            if(cpwd.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Confirm Password.");
                return;
            }

            // Check Password and Confirm Password are Match or not.
            if(!pwd.equals(cpwd))
            {
                JOptionPane.showMessageDialog(null, "Confirm Password and Password Do Not Match.");
                return;
            }

            // Set Username and Password Model User Class.
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(pwd);

            // Call Data Access Object to Register User in db.
            UserDAO regUserDao = new UserDAO();
            if(regUserDao.registerUser(user))
            {
                JOptionPane.showMessageDialog(null, "Username Registration Successful.");
                txtUser.setText("");
                txtPwd.setText("");
                txtConfirm.setText("");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Username Already Exists.");
            }
        }
        else if(e.getSource() == btnLogin)
        {
            setVisible(false);
            new Login();
        }
    }
}