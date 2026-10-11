package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dao.CategoryDAO;
import dao.DocumentDAO;

import model.User;
import model.Category;
import model.Document;

import java.util.ArrayList;
import java.io.*;

public class UploadDocument extends JFrame implements ActionListener
{
    JComboBox<String> categoryBox;
    JTextField txtDocName,txtSelectedFilePath;
    JTextArea txtDocDescription;
    JButton btnBrowse,btnUpload,btnClear,btnBack;
    User user;

    UploadDocument(User user)
    {
        this.user = user;
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,40);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Upload Document");
        h2.setBounds(290,50,350,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        // Label and ComboBox for Choose Category.
        JLabel lblCategory = new JLabel("Choose Category");
        lblCategory.setBounds(100,100,220,40);
        lblCategory.setFont(new Font("Serif",Font.BOLD,23));
        lblCategory.setForeground(new Color(50,50,50));
        add(lblCategory);

        categoryBox = new JComboBox<>();
        categoryBox.setBounds(400,100,300,40);
        categoryBox.setFont(new Font("Serif",Font.BOLD,25));
        categoryBox.setBackground(Color.white);
        categoryBox.setForeground(Color.black);
        categoryBox.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        categoryBox.addItem("None");
        add(categoryBox);
        loadCategories();

        // Label and Text Field for Documet Name.
        JLabel lblDocName = new JLabel("Document Name");
        lblDocName.setBounds(100,150,220,40);
        lblDocName.setFont(new Font("Serif",Font.BOLD,23));
        lblDocName.setForeground(new Color(50,50,50));
        add(lblDocName);

        txtDocName = new JTextField();
        txtDocName.setBounds(400,150,300,40);
        txtDocName.setFont(new Font("Serif",Font.BOLD,25));
        txtDocName.setBackground(Color.white);
        txtDocName.setForeground(Color.black);
        txtDocName.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtDocName);

        // Label and Text Area & JScrollPane for Documet Description.
        JLabel lblDocDes = new JLabel("Document Description");
        lblDocDes.setBounds(100,200,300,40);
        lblDocDes.setFont(new Font("Serif",Font.BOLD,23));
        lblDocDes.setForeground(new Color(50,50,50));
        add(lblDocDes);

        txtDocDescription = new JTextArea();
        txtDocDescription.setFont(new Font("Serif",Font.BOLD,25));
        txtDocDescription.setBackground(Color.white);
        txtDocDescription.setForeground(Color.black);
        txtDocDescription.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));

        JScrollPane areaJsp = new JScrollPane(txtDocDescription);
        areaJsp.setBounds(400,200,300,100);
        add(areaJsp);

        // Label and Text Field for File Path.
        JLabel lblDocFilePath = new JLabel("Document File Path");
        lblDocFilePath.setBounds(100,310,260,40);
        lblDocFilePath.setFont(new Font("Serif",Font.BOLD,23));
        lblDocFilePath.setForeground(new Color(50,50,50));
        add(lblDocFilePath);

        txtSelectedFilePath = new JTextField();
        txtSelectedFilePath.setBounds(400,310,300,40);
        txtSelectedFilePath.setFont(new Font("Serif",Font.BOLD,25));
        txtSelectedFilePath.setEditable(false);
        txtSelectedFilePath.setBackground(Color.white);
        txtSelectedFilePath.setForeground(Color.black);
        txtSelectedFilePath.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtSelectedFilePath);

        // Buttons
        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(710,310,150,40);
        btnBrowse.setBackground(new Color(0,153,73));
        btnBrowse.setForeground(Color.white);
        btnBrowse.setFont(new Font("Serif",Font.BOLD,22));
        add(btnBrowse);
        btnBrowse.addActionListener(this);

        btnUpload = new JButton("Upload");
        btnUpload.setBounds(180,370,150,40);
        btnUpload.setBackground(new Color(0,102,204));
        btnUpload.setForeground(Color.white);
        btnUpload.setFont(new Font("Serif",Font.BOLD,22));
        add(btnUpload);
        btnUpload.addActionListener(this);

        btnClear = new JButton("Clear");
        btnClear.setBounds(350,370,150,40);
        btnClear.setBackground(new Color(0,102,204));
        btnClear.setForeground(Color.white);
        btnClear.setFont(new Font("Serif",Font.BOLD,22));
        add(btnClear);
        btnClear.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(520,370,150,40);
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
        setTitle("Secure Document Wallet - Upload Document "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void loadCategories()
    {
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getAllCategories();

        for(Category category : categoryList)
        {
            categoryBox.addItem(category.getCategoryName());
        }
    }

    public static void main(String[] args)
    {
//        new UploadDocument();
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == btnBrowse)
        {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                txtSelectedFilePath.setText(file.getAbsolutePath());
            }
        }
        else if(ae.getSource() == btnUpload)
        {
            String documentName = txtDocName.getText().trim();
            String documentDes = txtDocDescription.getText().trim();
            String documentFilePath = txtSelectedFilePath.getText().trim();

            // Validation.
            if(categoryBox.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Please Choose Category.");
                return;
            }
            if(documentName.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Document Name.");
                return;
            }
            if(documentDes.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Document Description.");
                return;
            }
            if(documentFilePath.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Select a Document File.");
                return;
            }

            // Reads the selected file.
            File file = new File(txtSelectedFilePath.getText());
            String filePath = file.getAbsolutePath();
            String fileType = "";

            int dot = file.getName().lastIndexOf('.');
            if(dot != -1)
            {
                fileType = file.getName().substring(dot + 1);
            }
            long fileSize = file.length();

            Document document = new Document();
            CategoryDAO categoryDAO = new CategoryDAO();
            int categoryId = categoryDAO.getCategoryId(categoryBox.getSelectedItem().toString());

            document.setUserId(user.getUserId());
            document.setCategoryId(categoryId);
            document.setDocumentName(documentName);
            document.setDocDescription(documentDes);
            document.setDocFilePath(filePath);
            document.setDocFileType(fileType);
            document.setDocFileSize(fileSize);

            // Data Access Object for Save Documet.
            DocumentDAO documentDAO = new DocumentDAO();
            if(documentDAO.uploadDocument(document))
            {
                JOptionPane.showMessageDialog(null, "Document Uploaded Successfully.");
                txtDocName.setText("");
                txtDocDescription.setText("");
                txtSelectedFilePath.setText("");
                categoryBox.setSelectedIndex(0);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Unable To Upload Document.");
            }
        }
        else if(ae.getSource() == btnClear)
        {
            txtDocName.setText("");
            txtDocDescription.setText("");
            txtSelectedFilePath.setText("");
            categoryBox.setSelectedIndex(0);
        }
    }
}