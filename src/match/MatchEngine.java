package match;

import ds.CustomLinkedList;
import user.*;
import util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchEngine {
    CustomLinkedList LL;
    MatchEngine()
    {
        LL=new CustomLinkedList();
    }
    void findMatches()
    {
        User u=Session.getCurrentUserObject();
        int age=u.getAge();
        String city=u.getCity();
        String gender_pref=u.getGender_preference();
        String gender=u.getGender();

        String query="SELECT * FROM users where username!=? AND age BETWEEN (?-5) AND (?+5) AND gender_preference=? AND gender=?  AND city=?";
        try
        {
            PreparedStatement pst= DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1,u.getUsername());
            pst.setInt(2,age);
            pst.setInt(3,age);
            pst.setString(4,gender);
            pst.setString(5,gender_pref);
            pst.setString(6,city);
            ResultSet rs= pst.getResultSet();
            while(rs.next())
            {
                User o=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),
                        rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),
                        rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),
                        rs.getString(14),rs.getString(15),rs.getBinaryStream(16) ,rs.getString(17),
                        rs.getString(18));
                        LL.insertAtLast(u);
            }
        }catch (SQLException e)
        {
            System.out.println("Connection lost!");
        }
    }
}
