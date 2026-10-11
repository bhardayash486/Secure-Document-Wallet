package model;

public class User
{
    private int userId;
    private String username;
    private String pwd_hash;
    private String slate;

    // Default Constructor.
    public User()
    {
    }

    // Parameterize Constructor.
    public User(int userId, String username, String pwd_hash,String slate)
    {
        this.userId = userId;
        this.username = username;
        this.pwd_hash = pwd_hash;
        this.slate = slate;
    }

    // Getter and Setter Methods for User Id.
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    // Getter and Setter Methods for Username.
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    // Getter and Setter Methods for Password.
    public String getPasswordHash()
    {
        return pwd_hash;
    }
    public void setPasswordHash(String pwd_hash)
    {
        this.pwd_hash = pwd_hash;
    }

    // Getter and Setter Methods for Slate.
    public String getSlate()
    {
        return slate;
    }
    public void setSlate(String slate)
    {
        this.slate = slate;
    }
}