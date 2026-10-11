package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import dao.CategoryDAO;
import model.Category;
import model.User;
import java.util.ArrayList;

public class Categories extends JFrame implements ActionListener,MouseListener
{
    JTextField txtCategory;
    JButton btnAdd,btnUpdate,btnDelete,btnClear,btnBack;
    JTable tableCategory;
    DefaultTableModel model;
    User user;
    int selectedCategoryId = -1;

    Categories(User user)
    {
        this.user = user;
        getContentPane().setBackground(new Color(245,248,252));

        JLabel h1 = new JLabel("Secure Document Wallet");
        h1.setBounds(230,10,550,50);
        h1.setFont(new Font("Serif",Font.BOLD,42));
        h1.setForeground(new Color(0,82,164));
        add(h1);

        JLabel h2 = new JLabel("Category Management");
        h2.setBounds(300,45,390,50);
        h2.setFont(new Font("Serif",Font.BOLD,32));
        h2.setForeground(new Color(0,102,204));
        add(h2);

        // Label and Text Field for Category.
        JLabel lblCategoryName = new JLabel("Category Name");
        lblCategoryName.setBounds(30,100,250,40);
        lblCategoryName.setFont(new Font("Serif",Font.BOLD,23));
        lblCategoryName.setForeground(new Color(50,50,50));
        add(lblCategoryName);

        txtCategory = new JTextField();
        txtCategory.setBounds(210,100,300,40);
        txtCategory.setFont(new Font("Serif",Font.BOLD,25));
        txtCategory.setBackground(Color.white);
        txtCategory.setForeground(Color.black);
        txtCategory.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(txtCategory);

        //Buttons.
        btnAdd = new JButton("Add");
        btnAdd.setBounds(30,150,150,40);
        btnAdd.setBackground(new Color(0,153,76));
        btnAdd.setForeground(Color.white);
        btnAdd.setFont(new Font("Serif",Font.BOLD,22));
        add(btnAdd);
        btnAdd.addActionListener(this);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190,150,150,40);
        btnUpdate.setBackground(new Color(0,102,204));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setFont(new Font("Serif",Font.BOLD,22));
        add(btnUpdate);
        btnUpdate.addActionListener(this);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(350,150,150,40);
        btnDelete.setBackground(new Color(204,0,0));
        btnDelete.setForeground(Color.white);
        btnDelete.setFont(new Font("Serif",Font.BOLD,22));
        add(btnDelete);
        btnDelete.addActionListener(this);

        btnClear = new JButton("Clear");
        btnClear.setBounds(510,150,150,40);
        btnClear.setBackground(new Color(0,102,204));
        btnClear.setForeground(Color.white);
        btnClear.setFont(new Font("Serif",Font.BOLD,22));
        add(btnClear);
        btnClear.addActionListener(this);

        btnBack = new JButton("Back");
        btnBack.setBounds(670,150,150,40);
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

        // Category Table.
        model = new DefaultTableModel(new Object[]{"Category Id","Category Name"},0)
        {
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };

        tableCategory = new JTable(model);
        tableCategory.setBackground(Color.white);
        tableCategory.setForeground(Color.black);
        tableCategory.setFont(new Font("Serif",Font.PLAIN,20));
        tableCategory.setRowHeight(28);
        tableCategory.getTableHeader().setReorderingAllowed(false);
        tableCategory.setShowGrid(true);
        tableCategory.setGridColor(new Color(220,220,220));
        tableCategory.setSelectionBackground(new Color(180,210,240));
        tableCategory.setSelectionForeground(Color.black);
        tableCategory.addMouseListener(this);

        JScrollPane jsp = new JScrollPane(tableCategory);
        jsp.setBounds(20,200,850,230);
        add(jsp);
        loadCategories();

        JTableHeader header = tableCategory.getTableHeader();
        header.setFont(new Font("Cambria",Font.BOLD,25));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);

        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
        cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0;i < tableCategory.getColumnCount(); i++)
        {
            tableCategory.getColumnModel().getColumn(i).setCellRenderer(cellRender);
        }

        setSize(900,500);
        setTitle("Secure Document Wallet - Categories "+user.getUsername());
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
//        new Categories();
    }

    // Load Categories into the Table.
    public void loadCategories()
    {
        model.setRowCount(0);

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getAllCategories();

        for(Category category:categoryList)
        {
            model.addRow(new Object[]{category.getCategoryId(),category.getCategoryName()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == btnAdd)
        {
            String categoryName = txtCategory.getText().trim();

            // Validation.
            if(categoryName.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Category Name.");
                return;
            }

            Category category = new Category();
            category.setCategoryName(categoryName);

            CategoryDAO categoryDAO = new CategoryDAO();
            if(categoryDAO.addCategory(category))
            {
                JOptionPane.showMessageDialog(null, "Category Inserted Successfully.");
                txtCategory.setText("");
                loadCategories();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Category Already Exists.");
            }
        }
        else if(ae.getSource() == btnUpdate)
        {
            // Checks Category Selected or Not.
            if(selectedCategoryId == -1)
            {
                JOptionPane.showMessageDialog(null, "Please Select a Category.");
                return;
            }

            String categoryName = txtCategory.getText().trim();

            //Validation.
            if(categoryName.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Enter Category Name.");
                return;
            }

            Category category = new Category();
            category.setCategoryId(selectedCategoryId);
            category.setCategoryName(categoryName);

            CategoryDAO categoryDAO = new CategoryDAO();
            if(categoryDAO.updateCategory(category))
            {
                JOptionPane.showMessageDialog(null, "Category Updated Successfully.");
                txtCategory.setText("");
                selectedCategoryId = -1;
                loadCategories();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Unable To Update Category.");
            }
        }
        else if(ae.getSource() == btnDelete)
        {
            if(selectedCategoryId == -1)
            {
                JOptionPane.showMessageDialog(null, "Please Select a Category.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(null,"Are You Sure Want To Delete This Category?","Delete Confirmation",JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION)
            {
                CategoryDAO categoryDAO = new CategoryDAO();
                if(categoryDAO.deleteCategory(selectedCategoryId))
                {
                    JOptionPane.showMessageDialog(null, "Category Deleted Successfully.");
                    txtCategory.setText("");
                    selectedCategoryId = -1;
                    loadCategories();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Unable To Delete Category.");
                }
            }
        }
        else if(ae.getSource() == btnClear)
        {
            txtCategory.setText("");
            selectedCategoryId = -1;
            tableCategory.clearSelection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int row = tableCategory.getSelectedRow(); // Gets the row number that the user clicked.
        if(row != -1)
        {
            selectedCategoryId = Integer.parseInt(tableCategory.getValueAt(row, 0).toString());
            txtCategory.setText(tableCategory.getValueAt(row, 1).toString());
        }
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