package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.User;
import dao.DocumentDAO;

import session.UserSession;

public class Dashboard extends JFrame implements ActionListener
{
    JButton btnUpload,btnManage,btnCategory,btnSearch,btnChangePwd,btnLogout;
    User user;

    Dashboard(User user)
    {
        this.user = user;
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,50);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel lblWelcome = new JLabel("Welcome, "+user.getUsername());
        lblWelcome.setBounds(350,62,350,35);
        lblWelcome.setFont(new Font("Serif",Font.BOLD,28));
        lblWelcome.setForeground(new Color(60,60,60));
        add(lblWelcome);

        DocumentDAO documentDAO = new DocumentDAO();
        // Information Labels.
        JLabel lblTotalDocs = new JLabel("Total Documents: "+documentDAO.getTotalDocuments(user.getUserId()));
        lblTotalDocs.setBounds(150,110,300,40);
        lblTotalDocs.setFont(new Font("Serif",Font.BOLD,25));
        lblTotalDocs.setForeground(new Color(0,102,204));
        add(lblTotalDocs);

        JLabel lblCategories = new JLabel("Total Categories: "+documentDAO.getTotalCategories(user.getUserId()));
        lblCategories.setBounds(500,110,300,40);
        lblCategories.setFont(new Font("Serif",Font.BOLD,25));
        lblCategories.setForeground(new Color(0,102,204));
        add(lblCategories);

        //Buttons.
        btnUpload = new JButton("Upload");
        btnUpload.setBounds(100,190,150,50);
        btnUpload.setBackground(new Color(0,153,76));
        btnUpload.setForeground(Color.white);
        btnUpload.setFont(new Font("Serif",Font.BOLD,22));
        add(btnUpload);
        btnUpload.addActionListener(this);

        btnManage = new JButton("Manage");
        btnManage.setBounds(280,190,150,50);
        btnManage.setBackground(new Color(0,102,204));
        btnManage.setForeground(Color.white);
        btnManage.setFont(new Font("Serif",Font.BOLD,22));
        add(btnManage);
        btnManage.addActionListener(this);

        btnCategory = new JButton("Categories");
        btnCategory.setBounds(460,190,150,50);
        btnCategory.setBackground(new Color(0,102,204));
        btnCategory.setForeground(Color.white);
        btnCategory.setFont(new Font("Serif",Font.BOLD,22));
        add(btnCategory);
        btnCategory.addActionListener(this);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(640,190,150,50);
        btnSearch.setBackground(new Color(0,102,204));
        btnSearch.setForeground(Color.white);
        btnSearch.setFont(new Font("Serif",Font.BOLD,22));
        add(btnSearch);
        btnSearch.addActionListener(this);

        btnChangePwd = new JButton("Change Password");
        btnChangePwd.setBounds(325,270,250,50);
        btnChangePwd.setBackground(new Color(0,102,204));
        btnChangePwd.setForeground(Color.white);
        btnChangePwd.setFont(new Font("Serif",Font.BOLD,22));
        add(btnChangePwd);
        btnChangePwd.addActionListener(this);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(375,350,150,50);
        btnLogout.setBackground(new Color(204,0,0));
        btnLogout.setForeground(Color.white);
        btnLogout.setFont(new Font("Serif",Font.BOLD,22));
        add(btnLogout);
        btnLogout.addActionListener(this);

        setSize(900,500);
        setTitle("Secure Document Wallet - Dashboard "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
//        new Dashboard(user.getUsername());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnUpload)
        {
            setVisible(false);
            new UploadDocument(user);
        }
        if(e.getSource() == btnManage)
        {
            setVisible(false);
            new ManageDocuments(user);
        }
        if(e.getSource() == btnCategory)
        {
            setVisible(false);
            new Categories(user);
        }
        if(e.getSource() == btnSearch)
        {
            setVisible(false);
            new SearchDocument(user);
        }
        if(e.getSource() == btnChangePwd)
        {
            setVisible(false);
            new ChangePassword(user);
        }
        if(e.getSource() == btnLogout)
        {
            UserSession.clear();
            dispose();
            new Login();
        }
    }
}