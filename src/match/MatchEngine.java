package match;

import ds.CustomLinkedList;
import user.*;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MatchEngine {
    CustomLinkedList LL=new CustomLinkedList();
    CustomLinkedList mutualLikes=new CustomLinkedList();
    CustomLinkedList likedUser=new CustomLinkedList();
    CustomLinkedList likedByUser=new CustomLinkedList();
    static Scanner sc= new Scanner(System.in);
    public static void editProfile()
    {
        System.out.println("1. Update first Name-last Name");
        System.out.println("2. Update username");
        System.out.println("3. Update Password");
        System.out.println("4. Update contact details");
        System.out.println("5. Update address(city ,state)");
        System.out.println("6. Update age");
        System.out.println("7. Update gender preferences");
        System.out.println("8. Update Gender");
        System.out.println("9. Update profile picture");
        System.out.println("10.Update qualification");
        System.out.println("11. Exit");
        int choice= sc.nextInt();
        switch(choice)
        {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
        }
    }
    public void findMatches()
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
            ResultSet rs= pst.executeQuery();
            while(rs.next())
            {
                User o=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),
                        rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),
                        rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),
                        rs.getString(14),rs.getString(15),rs.getBinaryStream(16) ,rs.getString(17),
                        rs.getString(18));
                LL.insertAtLast(o);
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
                // Replace this with actual display logic
                String likerName=rs.getString("username");
                likedByUser.insertAtLast(Session.getUserObject(likerName));
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
                // Replace this with actual display logic
                String mutualLikerName=rs.getString("username");
                mutualLikes.insertAtLast(Session.getUserObject(mutualLikerName));

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
//
                String likedByUsername=rs.getString("username");
                likedUser.insertAtLast(Session.getUserObject(likedByUsername));
            }

        } catch (SQLException e) {
            System.out.println("Connection lost while fetching users who liked you!");
        }
    }

}