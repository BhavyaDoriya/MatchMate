package user;

import util.DatabaseConnector;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Session {
    public static String currentUsername;
    private static User currentUserObject;
    public static String getCurrentUsername() {
        return currentUsername;
    }
    public static void setCurrentUserObject()
    {
        String query="call getUserData(?)";
        try
        {
            CallableStatement cst= DatabaseConnector.getConnection().prepareCall(query);
            cst.setString(1,currentUsername);
            ResultSet rs=cst.executeQuery();
            rs.next();
            currentUserObject=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),rs.getBinaryStream(16) ,rs.getString(17),rs.getString(18));
        } catch (SQLException e) {
            System.out.println("Connection lost!");
        }

    }
    public static User getCurrentUserObject()
    {
        return currentUserObject;
    }
}
