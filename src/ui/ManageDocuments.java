package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import model.User;
import model.Document;
import model.Category;

import dao.DocumentDAO;
import dao.CategoryDAO;

import java.util.ArrayList;
import java.io.*;

public class ManageDocuments extends JFrame implements ActionListener,MouseListener
{
    JButton btnUpdate,btnDelete,btnOpen,btnBack;
    JTable tableDocument;
    DefaultTableModel model;
    User user;
    int selectedDocId = -1;

    ManageDocuments(User user)
    {
        this.user = user;
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,40);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Manage Document");
        h2.setBounds(330,45,350,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        // DB Columns: doc_id, user_id, category_id, doc_name, description, file_path, file_type, file_size, upload_date	
        // Document Table.
        model = new DefaultTableModel(new Object[]{"Document Id","Category","Document Name","Description","File Type","File Size","Upload Date"},0)
        {
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };

        tableDocument = new JTable(model);
        tableDocument.setBackground(Color.white);
        tableDocument.setForeground(Color.black);
        tableDocument.setFont(new Font("Serif",Font.PLAIN,13));
        tableDocument.setRowHeight(28);
        tableDocument.getTableHeader().setReorderingAllowed(false);
        tableDocument.setShowGrid(true);
        tableDocument.setGridColor(new Color(220,220,220));
        tableDocument.setSelectionBackground(new Color(180,210,240));
        tableDocument.setSelectionForeground(Color.black);
        tableDocument.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int row = tableDocument.getSelectedRow();
                if(row != -1)
                {
                    selectedDocId = Integer.parseInt(model.getValueAt(row, 0).toString());
                }
            }
        });

        JScrollPane jsp = new JScrollPane(tableDocument);
        jsp.setBounds(5,100,875,270);
        add(jsp);
        loadDocuments();

        JTableHeader header = tableDocument.getTableHeader();
        header.setFont(new Font("Cambria",Font.BOLD,15));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);

        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
        cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0;i < tableDocument.getColumnCount(); i++)
        {
            tableDocument.getColumnModel().getColumn(i).setCellRenderer(cellRender);
        }

        // Buttons.
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100,380,150,50);
        btnUpdate.setBackground(new Color(0,102,204));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setFont(new Font("Serif",Font.BOLD,22));
        add(btnUpdate);
        btnUpdate.addActionListener(this);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(270,380,150,50);
        btnDelete.setBackground(new Color(204,0,0));
        btnDelete.setForeground(Color.white);
        btnDelete.setFont(new Font("Serif",Font.BOLD,22));
        add(btnDelete);
        btnDelete.addActionListener(this);

        btnOpen = new JButton("Open");
        btnOpen.setBounds(440,380,150,50);
        btnOpen.setBackground(new Color(0,153,76));
        btnOpen.setForeground(Color.white);
        btnOpen.setFont(new Font("Serif",Font.BOLD,22));
        add(btnOpen);
        btnOpen.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(610,380,150,50);
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
        setTitle("Secure Document Wallet - Manage Documents "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] args)
    {
//        new ManageDocuments();
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
                doc.getDocumentId(),
                doc.getCategoryName(),
                doc.getDocumentName(),
                doc.getDocDescription(),
                doc.getDocFileType(),
                doc.getDocFileSize(),
                doc.getDocUploadDate(),
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == btnUpdate)
        {
            if(selectedDocId == -1)
            {
                JOptionPane.showMessageDialog(null, "Please Select A Document.");
                return;
            }

            // Create DAO object to fetch categories from the database.
            CategoryDAO categoryDAO = new CategoryDAO();
            ArrayList<Category> categoryList = categoryDAO.getAllCategories(); // Retrieve all categories.
            JComboBox<String> categoryBox = new JComboBox<>(); // Display Category Names.
            for(Category category : categoryList)
            {
                categoryBox.addItem(category.getCategoryName());
            }

            // Display the ComboBox inside a dialog for category selection.
            int option = JOptionPane.showConfirmDialog(null, categoryBox,"Select Category",JOptionPane.OK_CANCEL_OPTION);
            if(option != JOptionPane.OK_OPTION)
            {
                return;
            }
            
            // Get the Selected Category.
            String categoryName = categoryBox.getSelectedItem().toString();
            int categoryId = categoryDAO.getCategoryId(categoryName);

            // For Update Document Namr.
            String currentDocName = model.getValueAt(tableDocument.getSelectedRow(), 2).toString(); //Get which row number user clicked in table.
            String documentName = JOptionPane.showInputDialog(null, "Enter Document Name : ", currentDocName);
            if(documentName == null || documentName.trim().isEmpty())
            {
                return;
            }

            // For Update Document Description.
            String currentDocDes = model.getValueAt(tableDocument.getSelectedRow(), 3).toString();
            String documentDes = JOptionPane.showInputDialog(null, "Enter Document Description : ",currentDocDes);
            if(documentDes == null || documentDes.trim().isEmpty())
            {
                return;
            }

            Document document = new Document();

            document.setDocumentId(selectedDocId);
            document.setUserId(user.getUserId());
            document.setCategoryId(categoryId);
            document.setDocumentName(documentName);
            document.setDocDescription(documentDes);

            DocumentDAO docDAO = new DocumentDAO();
            if(docDAO.updateDocument(document))
            {
                JOptionPane.showMessageDialog(null, "Document Updated Successfully.");
                loadDocuments();
                selectedDocId = -1;
                tableDocument.clearSelection();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Unable To Update Document.");
            }
        }
        else if(ae.getSource() == btnDelete)
        {
            if(selectedDocId == -1)
            {
                JOptionPane.showMessageDialog(null, "Please Select A Document.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(null, "Are You Sure, You Want To Delete This Document?","Confirm Delete",JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION)
            {
                DocumentDAO documentDAO = new DocumentDAO();
                if(documentDAO.deleteDocument(selectedDocId, user.getUserId()))
                {
                    JOptionPane.showMessageDialog(null, "Document Deleted Successfully.");
                    loadDocuments();
                    selectedDocId = -1;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Unable To Delete Document.");
                }
            }
        }
        else if(ae.getSource() == btnOpen)
        {
            if(selectedDocId == -1)
            {
                JOptionPane.showMessageDialog(null, "Please Select A Document.");
                return;
            }

            DocumentDAO docDAO = new DocumentDAO();
            String filePath = docDAO.getDocumentPath(selectedDocId, user.getUserId());

            if(filePath != null)
            {
                try
                {
                    Desktop.getDesktop().open(new File(filePath));
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(null, "Unable To Open Document.");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Document Not Found.");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}