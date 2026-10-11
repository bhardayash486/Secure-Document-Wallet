package session;

public class UserSession 
{
    private static int userId;
    private static String userName;

    // Getter and Setter Methods for User Id.
    public static int getUserId()
    {
        return userId;
    }
    public static void setUserId(int userId)
    {
        UserSession.userId = userId;
    }

    // Getter and Setter Methods for User Name.
    public static String getUsername()
    {
        return userName;
    }
    public static void setUsername(String userName)
    {
        UserSession.userName = userName;
    }

    // Clears User Session.
    public static void clear()
    {
        userId = 0;
        userName = null;
    }
}