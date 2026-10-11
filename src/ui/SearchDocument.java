package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import model.User;
import model.Document;

import dao.DocumentDAO;
import java.util.ArrayList;

public class SearchDocument extends JFrame implements ActionListener
{
    JTextField txtSearch;
    JButton btnSearch,btnClear,btnBack;
    JTable tableDocument;
    DefaultTableModel model;
    User user;

    SearchDocument(User user)
    {
        getContentPane().setBackground(new Color(245,248,252));
        this.user = user;

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,40);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Search Document");
        h2.setBounds(330,45,350,40);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        // Label and Text Field for Search.
        JLabel lblSearch = new JLabel("Search");
        lblSearch.setBounds(10,90,100,30);
        lblSearch.setFont(new Font("Serif",Font.BOLD,23));
        lblSearch.setForeground(new Color(50,50,50));
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(100,90,290,35);
        txtSearch.setFont(new Font("Serif",Font.BOLD,25));
        txtSearch.setBackground(Color.white);
        txtSearch.setForeground(Color.black);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtSearch);

        // DB Columns: doc_id, user_id, category_id, doc_name, description, file_path, file_type, file_size, upload_date	
        // Document Table.
        model = new DefaultTableModel(new Object[]{"Category","Document Name","Description","File Type","File Size","Upload Date"},0)
        {
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };

        tableDocument = new JTable(model);
        tableDocument.setBackground(Color.white);
        tableDocument.setForeground(Color.black);
        tableDocument.setFont(new Font("Serif",Font.PLAIN,15));
        tableDocument.setRowHeight(28);
        tableDocument.getTableHeader().setReorderingAllowed(false);
        tableDocument.setShowGrid(true);
        tableDocument.setGridColor(new Color(220,220,220));
        tableDocument.setSelectionBackground(new Color(180,210,240));
        tableDocument.setSelectionForeground(Color.black);

        JScrollPane jsp = new JScrollPane(tableDocument);
        jsp.setBounds(5,140,875,300);
        add(jsp);
        loadDocuments();

        JTableHeader header = tableDocument.getTableHeader();
        header.setFont(new Font("Cambria",Font.BOLD,18));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);

        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
        cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0;i < tableDocument.getColumnCount(); i++)
        {
            tableDocument.getColumnModel().getColumn(i).setCellRenderer(cellRender);
        }

        // Buttons.
        btnSearch = new JButton("Search");
        btnSearch.setBounds(400,90,150,35);
        btnSearch.setBackground(new Color(0,153,76));
        btnSearch.setForeground(Color.white);
        btnSearch.setFont(new Font("Serif",Font.BOLD,22));
        add(btnSearch);
        btnSearch.addActionListener(this);

        btnClear = new JButton("Clear");
        btnClear.setBounds(560,90,150,35);
        btnClear.setBackground(new Color(0,102,204));
        btnClear.setForeground(Color.white);
        btnClear.setFont(new Font("Serif",Font.BOLD,22));
        add(btnClear);
        btnClear.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(720,90,150,35);
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
        setTitle("Secure Document Wallet - Search Documents "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
//        new SearchDocument();
    }

    public void loadDocuments()
    {
        model.setRowCount(0);

        DocumentDAO docDAO = new DocumentDAO();
        ArrayList<Document> docList = docDAO.getAllDocument(user.getUserId());
        for(Document doc : docList)
        {
            model.addRow(new Object[]
            {
                doc.getCategoryName(),
                doc.getDocumentName(),
                doc.getDocDescription(),
                doc.getDocFileType(),
                doc.getDocFileSize(),
                doc.getDocUploadDate()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == btnSearch)
        {
            String keyword = txtSearch.getText().trim();

            // Validation.
            if(keyword.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Document Name To Search.");
                return;
            }

            DocumentDAO docDAO = new DocumentDAO();
            ArrayList<Document> documentList = docDAO.searchDocument(user.getUserId(),keyword);

            model.setRowCount(0);
            if(documentList.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No Document Found.");
                return;
            }
            for(Document document : documentList)
            {
                model.addRow(new Object[]
                {
                    document.getCategoryName(),
                    document.getDocumentName(),
                    document.getDocDescription(),
                    document.getDocFileType(),
                    document.getDocFileSize(),
                    document.getDocUploadDate(),
                });
            }
        }
        else if(ae.getSource() == btnClear)
        {
            txtSearch.setText("");
            loadDocuments();
        }
    }
}