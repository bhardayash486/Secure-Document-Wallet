package database;

import java.sql.*;

public class DBConnection
{
    public Connection con;

    public DBConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/document_wallet","root","");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try 
        {
            if(con != null && !con.isClosed())
            {
                con.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}