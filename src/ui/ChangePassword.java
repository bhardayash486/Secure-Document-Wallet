package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.User;
import dao.UserDAO;

public class ChangePassword extends JFrame implements ActionListener
{
    JPasswordField txtCurrentPwd,txtNewPwd,txtConfirmPwd;
    JButton btnChangePwd,btnBack;
    User user;

    ChangePassword(User user)
    {
        this.user = user;
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,40);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Change Password");
        h2.setBounds(330,50,330,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        // Label and Password Field for Current Password.
        JLabel lblCurrentPwd = new JLabel("Current Password");
        lblCurrentPwd.setBounds(180,120,220,40);
        lblCurrentPwd.setFont(new Font("Serif",Font.BOLD,23));
        lblCurrentPwd.setForeground(new Color(50,50,50));
        add(lblCurrentPwd);

        txtCurrentPwd = new JPasswordField();
        txtCurrentPwd.setBounds(420,120,250,40);
        txtCurrentPwd.setFont(new Font("Serif",Font.BOLD,25));
        txtCurrentPwd.setBackground(Color.white);
        txtCurrentPwd.setForeground(Color.black);
        txtCurrentPwd.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(txtCurrentPwd);

        // Label and Password Field for New Password.
        JLabel lblNewPwd = new JLabel("New Password");
        lblNewPwd.setBounds(180,180,220,40);
        lblNewPwd.setFont(new Font("Serif",Font.BOLD,23));
        lblNewPwd.setForeground(new Color(50,50,50));
        add(lblNewPwd);

        txtNewPwd = new JPasswordField();
        txtNewPwd.setBounds(420,180,250,40);
        txtNewPwd.setFont(new Font("Serif",Font.BOLD,25));
        txtNewPwd.setBackground(Color.white);
        txtNewPwd.setForeground(Color.black);
        txtNewPwd.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(txtNewPwd);

        // Label and Password Field for Confirm Password.
        JLabel lblConfirmPwd = new JLabel("Confirm Password");
        lblConfirmPwd.setBounds(180,240,220,40);
        lblConfirmPwd.setFont(new Font("Serif",Font.BOLD,23));
        lblConfirmPwd.setForeground(new Color(50,50,50));
        add(lblConfirmPwd);

        txtConfirmPwd = new JPasswordField();
        txtConfirmPwd.setBounds(420,240,250,40);
        txtConfirmPwd.setFont(new Font("Serif",Font.BOLD,25));
        txtConfirmPwd.setBackground(Color.white);
        txtConfirmPwd.setForeground(Color.black);
        txtConfirmPwd.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(txtConfirmPwd);

        // Buttons.
        btnChangePwd = new JButton("Change Password");
        btnChangePwd.setBounds(180,330,250,40);
        btnChangePwd.setBackground(new Color(0,153,76));
        btnChangePwd.setForeground(Color.white);
        btnChangePwd.setFont(new Font("Serif",Font.BOLD,22));
        add(btnChangePwd);
        btnChangePwd.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(460,330,250,40);
        btnBack.setBackground(new Color(204,0,0));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Serif",Font.BOLD,22));
        add(btnBack);
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new Dashboard(user);
            }
            
        });

        setSize(900,500);
        setTitle("Secure Document Wallet - Change Password "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
//        new ChangePassword();
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == btnChangePwd)
        {
            String currentPwd = txtCurrentPwd.getText().trim();
            String newPwd = txtNewPwd.getText().trim();
            String confirmPwd = txtConfirmPwd.getText().trim();

            // Validation.
            if(currentPwd.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Current Password.");
                return;
            }
            if(newPwd.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter New Password.");
                return;
            }
            if(confirmPwd.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Confirm Password.");
                return;
            }

            // Check New Password and Confirm Password are Match or not.
            if(!newPwd.equals(confirmPwd))
            {
                JOptionPane.showMessageDialog(null, "Confirm Password and New Password Do Not Match.");
                return;
            }

            // Creates UserDAO Class Object To Change Passsword.
            UserDAO userDao = new UserDAO();
            boolean changed = userDao.changePassword(user.getUserId(), currentPwd, newPwd);
            if(changed)
            {
                JOptionPane.showMessageDialog(null, "Password Changed Successfully.");
                txtCurrentPwd.setText("");
                txtNewPwd.setText("");
                txtConfirmPwd.setText("");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Current Password is Incorrect.");
            }
        }
    }
}