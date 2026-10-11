package dao;

import database.DBConnection;
import model.Document;
import java.sql.*;
import java.util.ArrayList;

public class DocumentDAO
{
    // Columns: doc_id,	user_id, category_id, doc_name, description, file_path, file_type, file_size, upload_date.
    // Upload Document.
    public boolean uploadDocument(Document document)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            String insertDoc = "Insert Into documents (user_id,category_id,doc_name,description,file_path,file_type,file_size) Values (?,?,?,?,?,?,?) ";
            PreparedStatement pstmt = dbConnect.con.prepareStatement(insertDoc);
            pstmt.setInt(1, document.getUserId());
            pstmt.setInt(2, document.getCategoryId());
            pstmt.setString(3, document.getDocumentName());
            pstmt.setString(4, document.getDocDescription());
            pstmt.setString(5, document.getDocFilePath());
            pstmt.setString(6, document.getDocFileType());
            pstmt.setLong(7, document.getDocFileSize());

            return pstmt.executeUpdate() > 0;
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

    // Get All Documents.
    public ArrayList<Document> getAllDocument(int userId)
    {
        ArrayList<Document> documentList = new ArrayList<>();
        DBConnection dbConnect = new DBConnection();
        try
        {
            String getAllDocs = "Select d.*, c.category_name From documents d Join categories c On d.category_id = c.category_id Where user_id = ?";

            PreparedStatement pstmt = dbConnect.con.prepareStatement(getAllDocs);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Document document = new Document();

                document.setDocumentId(rs.getInt("doc_id"));
                document.setUserId(rs.getInt("user_id"));
                document.setCategoryId(rs.getInt("category_id"));
                document.setCategoryName(rs.getString("category_name"));
                document.setDocumentName(rs.getString("doc_name"));
                document.setDocDescription(rs.getString("description"));
                document.setDocFilePath(rs.getString("file_path"));
                document.setDocFileType(rs.getString("file_type"));
                document.setDocFileSize(rs.getLong("file_size"));
                document.setDocUploadDate(rs.getTimestamp("upload_date"));

                documentList.add(document);
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
        return documentList;
    }

    // Update Document.
    public boolean updateDocument(Document document)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            String updateDoc = "Update documents Set category_id = ?, doc_name = ?, description = ? Where doc_id = ? And user_id = ?";

            PreparedStatement pstmt = dbConnect.con.prepareStatement(updateDoc);
            pstmt.setInt(1, document.getCategoryId());
            pstmt.setString(2, document.getDocumentName());
            pstmt.setString(3, document.getDocDescription());
            pstmt.setInt(4, document.getDocumentId());
            pstmt.setInt(5, document.getUserId());

            return pstmt.executeUpdate() > 0;
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

    // Delete Document.
    public boolean deleteDocument(int documentId, int userId)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Delete From documents Where doc_id = ? And user_id = ?");
            pstmt.setInt(1, documentId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
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

    // Search Document.
    public ArrayList<Document> searchDocument(int userId, String keyword)
    {
        ArrayList<Document> documentList = new ArrayList<>();
        DBConnection dbConnect = new DBConnection();
        try
        {
            String searchDoc = "Select d.*, c.category_name From documents d Join categories c On d.category_id = c.category_id"
                + " Where d.user_id = ? And (d.doc_name Like ? Or d.description Like ? Or c.category_name Like ?)";

            PreparedStatement pstmt = dbConnect.con.prepareStatement(searchDoc);
            pstmt.setInt(1, userId);
            pstmt.setString(2,"%" + keyword + "%");
            pstmt.setString(3,"%" + keyword + "%");
            pstmt.setString(4,"%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
               Document document = new Document();

                document.setDocumentId(rs.getInt("doc_id"));
                document.setUserId(rs.getInt("user_id"));
                document.setCategoryId(rs.getInt("category_id"));
                document.setCategoryName(rs.getString("category_name"));
                document.setDocumentName(rs.getString("doc_name"));
                document.setDocDescription(rs.getString("description"));
                document.setDocFilePath(rs.getString("file_path"));
                document.setDocFileType(rs.getString("file_type"));
                document.setDocFileSize(rs.getLong("file_size"));
                document.setDocUploadDate(rs.getTimestamp("upload_date"));

                documentList.add(document);
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
        return documentList;
    }

    // Get Document Path.
    public String getDocumentPath(int documentId,int userId)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select file_path From documents Where doc_id = ? And user_id = ?");
            pstmt.setInt(1, documentId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                return rs.getString("file_path");
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
        return null;
    }

    // Get Total Documents.
    public int getTotalDocuments(int userId)
    {
        int totalDocs = 0;
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select Count(*) From documents Where user_id = ?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                totalDocs = rs.getInt("Count(*)");
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
        return totalDocs;
    }

    // Get Total Categories used by User.
    public int getTotalCategories(int userId)
    {
        int totalCategories = 0;
        DBConnection dbConnect = new DBConnection();
        try
        {
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select Count(Distinct category_id) From documents Where user_id = ?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                totalCategories = rs.getInt(1);
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
        return totalCategories;
    }
}