package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreen extends JFrame implements ActionListener
{
    JButton btnLogin,btnReg,btnBack;
    SplashScreen()
    {
        getContentPane().setBackground(new Color(245,248,252));

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("icons/SDW.png"));
        Image i2 = image.getImage().getScaledInstance(900,500,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel lblIcon = new JLabel(i3);
        lblIcon.setBounds(0,0,900,500);
        setContentPane(lblIcon);

        // Register, Login & Back Button.
        btnReg = new JButton("Register");
        btnReg.setBounds(200,360,150,50);
        btnReg.setBackground(new Color(0,102,204));
        btnReg.setForeground(Color.white);
        btnReg.setFont(new Font("Serif",Font.BOLD,22));
        btnReg.addActionListener(this);
        lblIcon.add(btnReg);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(370,360,150,50);
        btnLogin.setBackground(new Color(0,102,204));
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Serif",Font.BOLD,25));
        btnLogin.addActionListener(this);
        lblIcon.add(btnLogin);

        btnBack = new JButton("Back");
        btnBack.setBounds(540,360,150,50);
        btnBack.setBackground(new Color(204,0,0));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Serif",Font.BOLD,25));
        btnBack.addActionListener(this);
        lblIcon.add(btnBack);

        setSize(900,500);
        setTitle("Secure Document Wallet");
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new SplashScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnReg)
        {
            setVisible(false);
            new Register();
        }
        else if(e.getSource() == btnLogin)
        {
            setVisible(false);
            new Login();
        }
        else
        {
            dispose();
        }
    }
}