package match;

import user.Session;
import util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class LikeManager {

    void LikeUser(String liked_username)
    {
        String query = "INSERT INTO likes (liker, liked) VALUES (?, ?)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, Session.getCurrentUsername());
            pst.setString(2, liked_username);

            pst.executeUpdate();
            System.out.println("Profile liked successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            // Duplicate primary key or foreign key violation
            System.out.println("You have already liked this profile.");
        } catch (SQLException e) {
            System.out.println("Database error/Connection stopped: " + e.getMessage());
        }
    }

}
