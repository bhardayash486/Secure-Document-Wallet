package dao;

import database.DBConnection;
import model.User;
import util.PasswordUtil;
import java.sql.*;

public class UserDAO
{
    public boolean registerUser(User user)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            // Check If Username Already Exists.
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select * From users Where username = ?");
            pstmt.setString(1, user.getUsername());
            ResultSet checkUser = pstmt.executeQuery();
            if(checkUser.next())
            {
                return false; //Username already exists.
            }

            // Generate Slat and Hash Password.
            String slat = PasswordUtil.slat();
            String hashedPwd = PasswordUtil.hashPwd(user.getPasswordHash(), slat);

            // Insert User.
            PreparedStatement insertStatement = dbConnect.con.prepareStatement("Insert Into users (username,password_hash,slate) Values(?,?,?)");
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, hashedPwd);
            insertStatement.setString(3, slat);

            return insertStatement.executeUpdate() > 0;
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

    // Login
    public User loginUser(User user)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            // Check Username.
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select * From users Where username = ?");
            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                String storedHash = rs.getString("password_hash");
                String storedSlate = rs.getString("slate");

                // Verify Password.
                if(user.getUsername().equals(rs.getString("username")) && PasswordUtil.verifyPwd(user.getPasswordHash(), storedHash, storedSlate))
                {
                    User loginUser = new User();
                    loginUser.setUserId(rs.getInt("user_id"));
                    loginUser.setUsername(rs.getString("username"));
                    return loginUser;
                }
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

    // Change Password.
    public boolean changePassword(int userId, String currentPwd, String newPwd)
    {
        DBConnection dbConnect = new DBConnection();
        try
        {
            // Get the old password hash and slate from db for this user.
            PreparedStatement pstmt = dbConnect.con.prepareStatement("Select password_hash, slate From users Where user_id = ?");
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            // If user exists.
            if(rs.next())
            {
                // Read stored hash and slate from database.
                String oldHash = rs.getString("password_hash");
                String oldSlate = rs.getString("slate");

                // Hash the password user entered with the old slate 
                String currentHash = PasswordUtil.hashPwd(currentPwd, oldSlate);

                // Verify if entered current password is correct.
                if(!oldHash.equals(currentHash))
                {
                    return false; //Wrong current password.
                }

                // Generate new slate and hash for the new password.
                String newSlate = PasswordUtil.slat();
                String newHash = PasswordUtil.hashPwd(newPwd, newSlate);

                // Update database with new hash and new slate.
                PreparedStatement updateUser = dbConnect.con.prepareStatement("Update users Set password_hash = ?, slate = ? Where user_id = ?");
                updateUser.setString(1, newHash);
                updateUser.setString(2, newSlate);
                updateUser.setInt(3, userId);

                return updateUser.executeUpdate() > 0;
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
        return false;
    }
}