package dao;

import database.DBConnection;
import model.Category;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO
{
    // Add Category.
    public boolean addCategory(Category category)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            // Check Category Exists or Not.
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select * From categories Where category_name = ?");
            pstmt.setString(1, category.getCategoryName());
            ResultSet checkCategory = pstmt.executeQuery();
            if(checkCategory.next())
            {
                return false;
            }

            //Insert Category.
            PreparedStatement insertCategory = dbConnect.con.prepareStatement("Insert Into categories (category_name) Values(?)");
            insertCategory.setString(1, category.getCategoryName());
            return insertCategory.executeUpdate() > 0;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(dbConnect != null)
            {
                dbConnect.close();
            }
        }
    }
    // Get All Categories.
    public ArrayList<Category> getAllCategories()
    {
        ArrayList<Category> categoryList = new ArrayList<>();
        DBConnection dbConnect = new DBConnection();
        try
        {
            // Get Category From Database.
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select * From categories");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));

                categoryList.add(category);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(dbConnect != null)
            {
                dbConnect.close();
            }
        }
        return categoryList;
    }
    // Update Category.
    public boolean updateCategory(Category category)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement updateCategory = dbConnect.con.prepareStatement("Update categories Set category_name = ? Where category_id = ?");
            updateCategory.setString(1, category.getCategoryName());
            updateCategory.setInt(2, category.getCategoryId());
            return updateCategory.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(dbConnect != null)
            {
                dbConnect.close();
            }
        }
    }
    // Delete Category.
    public boolean deleteCategory(int category_id)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement deleteCategory = dbConnect.con.prepareStatement("Delete From categories Where Category_id = ?");
            deleteCategory.setInt(1, category_id);
            return deleteCategory.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(dbConnect != null)
            {
                dbConnect.close();
            }
        }
    }
    // Get Category Id.
     public int getCategoryId(String categoryName)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select category_id From categories Where category_name = ?");
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                return rs.getInt("category_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(dbConnect != null)
            {
                dbConnect.close();
            }
        }
        return 0;
    }
}