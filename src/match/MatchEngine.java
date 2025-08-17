package match;

import ds.CustomLinkedList;
import user.*;
import util.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MatchEngine {
    CustomLinkedList LL=new CustomLinkedList();
    CustomLinkedList mutualLikes=new CustomLinkedList();
    CustomLinkedList likedUser=new CustomLinkedList();
    CustomLinkedList likedByUser=new CustomLinkedList();
    CustomLinkedList matchesByAge=new CustomLinkedList();
    CustomLinkedList matchesByCity=new CustomLinkedList();
    CustomLinkedList matchesByCityANDAge=new CustomLinkedList();
    CustomLinkedList likedUserSortedByAge=new CustomLinkedList();
    CustomLinkedList likedUserSortedByCity=new CustomLinkedList();
    CustomLinkedList likedUserSortedByCityANDAge=new CustomLinkedList();
    CustomLinkedList likedByUserSortedByAge=new CustomLinkedList();
    CustomLinkedList likedByUserSortedByCity=new CustomLinkedList();
    CustomLinkedList likedByUserSortedByCityANDAge=new CustomLinkedList();
    CustomLinkedList mutualLikesSortedByAge=new CustomLinkedList();
    CustomLinkedList mutualLikesSortedByCity=new CustomLinkedList();
    CustomLinkedList mutualLikesSortedByCityANDAge=new CustomLinkedList();

    static Scanner sc= new Scanner(System.in);

    public void findMatches()
    {
        User u=Session.getCurrentUserObject();
        String gender_pref=u.getGender_preference();
        String gender=u.getGender();

            String query="SELECT * FROM users where username!=? AND gender_preference=? AND gender=?";
            try
            {
                PreparedStatement pst= DatabaseConnector.getConnection().prepareStatement(query);
                pst.setString(1,u.getUsername());
                pst.setString(2,gender);
                pst.setString(3,gender_pref);
                ResultSet rs= pst.executeQuery();
                while(rs.next())
                {
                    String matchedUsername=rs.getString("username");
                    User o=Session.getUserObject(matchedUsername);
                    LL.insertAtLast(o);
                }

            }catch (SQLException e)
            {
                System.out.println("Connection lost!");
            }
        }
    public void findMatchesByAge(int startRangeOfAge)
    {
        User u=Session.getCurrentUserObject();
        String gender_pref=u.getGender_preference();
        String gender=u.getGender();

        String query="SELECT * FROM users where username!=? AND gender_preference=? AND gender=? AND age BETWEEN ? AND (?+9)";
        try
        {
            PreparedStatement pst= DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1,u.getUsername());
            pst.setString(2,gender);
            pst.setString(3,gender_pref);
            pst.setInt(4,startRangeOfAge);
            pst.setInt(5,startRangeOfAge);
            ResultSet rs= pst.executeQuery();
            while(rs.next())
            {
                String matchedUsername=rs.getString("username");
                User o=Session.getUserObject(matchedUsername);
                matchesByAge.insertAtLast(o);
            }

        }catch (SQLException e)
        {
            System.out.println("Connection lost!");
        }
    }
    public void findMatchesByCity(String city)
    {
        User u=Session.getCurrentUserObject();
        String gender_pref=u.getGender_preference();
        String gender=u.getGender();

        String query="SELECT * FROM users where username!=? AND gender_preference=? AND gender=? AND city=? ";
        try
        {
            PreparedStatement pst= DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1,u.getUsername());
            pst.setString(2,gender);
            pst.setString(3,gender_pref);
            pst.setString(4,city);
            ResultSet rs= pst.executeQuery();
            while(rs.next())
            {
                String matchedUsername=rs.getString("username");
                User o=Session.getUserObject(matchedUsername);
                matchesByCity.insertAtLast(o);
            }

        }catch (SQLException e)
        {
            System.out.println("Connection lost!");
        }
    }
    public void findMatchesByCityANDAge(int startRangeOfAge,String city)
    {
        User u=Session.getCurrentUserObject();
        String gender_pref=u.getGender_preference();
        String gender=u.getGender();

        String query="SELECT * FROM users where username!=? AND gender_preference=? AND gender=? AND age BETWEEN ? AND (?+9) AND city=? ";
        try
        {
            PreparedStatement pst= DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1,u.getUsername());
            pst.setString(2,gender);
            pst.setString(3,gender_pref);
            pst.setInt(4,startRangeOfAge);
            pst.setInt(5,startRangeOfAge);
            pst.setString(6,city);
            ResultSet rs= pst.executeQuery();
            while(rs.next())
            {
                String matchedUsername=rs.getString("username");
                User o=Session.getUserObject(matchedUsername);
                matchesByCityANDAge.insertAtLast(o);
            }

        }catch (SQLException e)
        {
            System.out.println("Connection lost!");
        }
    }
    public void shortListedProfile()
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liked " +
                "WHERE l.liker = ?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likerName=rs.getString("username");
                likedByUser.insertAtLast(Session.getUserObject(likerName));
            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching liked users!");
        }
    }
    public void shortListedProfileSortedByAge(int startRangeOfAge)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liked " +
                "WHERE l.liker = ? AND u.age BETWEEN ? AND (?+9)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2,startRangeOfAge);
            pst.setInt(3,startRangeOfAge);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likerName=rs.getString("username");
                likedByUserSortedByAge.insertAtLast(Session.getUserObject(likerName));
            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching liked users!");
        }

    }
    public void shortListedProfileSortedByCity(String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liked " +
                "WHERE l.liker = ? AND u.city=?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2,city);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likerName=rs.getString("username");
                likedByUserSortedByCity.insertAtLast(Session.getUserObject(likerName));
            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching liked users!");
        }

    }
    public void shortListedProfileSortedByCityANDAge(int startRangeOfAge,String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liked " +
                "WHERE l.liker = ? AND u.city=? AND age BETWEEN ? AND (?+9)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2,city);
            pst.setInt(3,startRangeOfAge);
            pst.setInt(4,startRangeOfAge);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likerName=rs.getString("username");
                likedByUserSortedByCityANDAge.insertAtLast(Session.getUserObject(likerName));
            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching liked users!");
        }
    }
    public void findMutualLikes() {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l1 ON u.username = l1.liked " +
                "JOIN likes l2 ON l1.liked = l2.liker AND l1.liker = l2.liked " +
                "WHERE l1.liker = ?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String mutualLikerName=rs.getString("username");
                mutualLikes.insertAtLast(Session.getUserObject(mutualLikerName));

            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching mutual likes!");
        }
    }
    public void findMutualLikesSortedByAge(int startRangeOfAge)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l1 ON u.username = l1.liked " +
                "JOIN likes l2 ON l1.liked = l2.liker AND l1.liker = l2.liked " +
                "WHERE l1.liker = ? AND u.age BETWEEN ? AND (?+9)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2,startRangeOfAge);
            pst.setInt(3,startRangeOfAge);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String mutualLikerName=rs.getString("username");
                mutualLikesSortedByAge.insertAtLast(Session.getUserObject(mutualLikerName));

            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching mutual likes!");
        }
    }
    public void findMutualLikesSortedByCity(String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l1 ON u.username = l1.liked " +
                "JOIN likes l2 ON l1.liked = l2.liker AND l1.liker = l2.liked " +
                "WHERE l1.liker = ? AND u.city=?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2,city);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String mutualLikerName=rs.getString("username");
                mutualLikesSortedByCity.insertAtLast(Session.getUserObject(mutualLikerName));

            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching mutual likes!");
        }
    }
    public void findMutualLikesSortedByCityANDAge(int startRangeOfAge,String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l1 ON u.username = l1.liked " +
                "JOIN likes l2 ON l1.liked = l2.liker AND l1.liker = l2.liked " +
                "WHERE l1.liker = ? AND u.age BETWEEN ? AND (?+9) AND u.city=?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2,startRangeOfAge);
            pst.setInt(3,startRangeOfAge);
            pst.setString(4,city);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String mutualLikerName=rs.getString("username");
                mutualLikesSortedByCityANDAge.insertAtLast(Session.getUserObject(mutualLikerName));

            }
        } catch (SQLException e) {
            System.out.println("Connection lost while fetching mutual likes!");
        }
    }
    public void findUsersWhoLikedMe() {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liker " +
                "WHERE l.liked = ?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likedByUsername=rs.getString("username");
                likedUser.insertAtLast(Session.getUserObject(likedByUsername));
            }

        } catch (SQLException e) {
            System.out.println("Connection lost while fetching users who liked you!");
        }
    }
    public void findUsersWhoLikedMeSortedByAge(int startRangeOfAge)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liker " +
                "WHERE l.liked = ? AND u.age BETWEEN ? AND (?+9)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2,startRangeOfAge);
            pst.setInt(3,startRangeOfAge);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String likedByUsername=rs.getString("username");
                likedUserSortedByAge.insertAtLast(Session.getUserObject(likedByUsername));
            }

        } catch (SQLException e) {
            System.out.println("Connection lost while fetching users who liked you!");
        }
    }
    public void findUsersWhoLikedMeSortedByCity(String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liker " +
                "WHERE l.liked = ? AND u.city=?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2,city);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String likedByUsername=rs.getString("username");
                likedUserSortedByCity.insertAtLast(Session.getUserObject(likedByUsername));
            }

        } catch (SQLException e) {
            System.out.println("Connection lost while fetching users who liked you!");
        }
    }
    public void findUsersWhoLikedMeSortedByCityANDAge(int startRangeOfAge,String city)
    {
        User u = Session.getCurrentUserObject();
        String username = u.getUsername();

        String query = "SELECT u.* FROM users u " +
                "JOIN likes l ON u.username = l.liker " +
                "WHERE l.liked = ? AND u.city=? AND u.age BETWEEN ? AND (?+9)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2,city);
            pst.setInt(3,startRangeOfAge);
            pst.setInt(4,startRangeOfAge);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String likedByUsername=rs.getString("username");
                likedUserSortedByCityANDAge.insertAtLast(Session.getUserObject(likedByUsername));
            }

        } catch (SQLException e) {
            System.out.println("Connection lost while fetching users who liked you!");
        }
    }
}