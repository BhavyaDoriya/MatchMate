package user;
import util.DatabaseConnector;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Session {
    private static String currentUsername;
    private static User currentUserObject;
    public static void setCurrentUsername(String username){
        currentUsername=username;
    }
    public static String getCurrentUsername() {
        return currentUsername;
    }
    public static void setCurrentUserObject(User u)
    {
        currentUserObject=u;
    }
    public static User getCurrentUserObject()
    {
        return currentUserObject;
    }
    public static User getUserObject(String username)
    {
        String query="call getUserData(?)";
        try
        {
            CallableStatement cst= DatabaseConnector.getConnection().prepareCall(query);
            cst.setString(1,username);
            ResultSet rs=cst.executeQuery();
            rs.next();
            User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),rs.getBinaryStream(16) ,rs.getString(17),rs.getString(18));
            return u;
        } catch (SQLException e) {
            System.out.println("Connection lost!");
            return null;
        }

    }

}
