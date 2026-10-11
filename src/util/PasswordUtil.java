package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil
{
    // Generates a random 16-byte salt for password hashing.
    public static String slat()
    {
        byte[] slate = new byte[16];
        new SecureRandom().nextBytes(slate);
        return Base64.getEncoder().encodeToString(slate);
    }

    // Creates a SHA-256 hash of the password using the generated salt.
    public static String hashPwd(String pwd, String slate)
    {
        try
        {
            MessageDigest pwdDigest = MessageDigest.getInstance("SHA-256");
            pwdDigest.update(Base64.getDecoder().decode(slate));
            byte[] hashPwd = pwdDigest.digest(pwd.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashPwd);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Hashing Password.",e);
        }
    }

    //Verifies the entered password by hashing it again and comparing it with the stored hash.
    public static boolean verifyPwd(String inputPwd,String storedHash,String slate)
    {
        String hash = hashPwd(inputPwd,slate);
        return hash.equals(storedHash);
    }
}