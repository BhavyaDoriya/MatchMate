package match;

import user.Session;
import user.User;
import util.DatabaseConnector;
import util.EmailUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class LikeManager {
    public void LikeUser(String liked_username) {
        String query = "INSERT INTO likes (liker, liked) VALUES (?, ?)";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, Session.getCurrentUsername());
            pst.setString(2, liked_username);
            pst.executeUpdate();

            System.out.println("Profile liked successfully!");

            User likedUser = Session.getUserObject(liked_username);
                System.out.println("Sending Like notification to: " + likedUser.getFirst_name());
                EmailUtil.sendMail(
                        likedUser.getEmail(),
                        "You got a new Like! ❤️",
                        "Hi " + likedUser.getFirst_name() + ",\n\n"
                                + Session.getCurrentUserObject().getFirst_name()
                                + " liked your profile!"
                );
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("You have already liked this profile.");
        } catch (SQLException e) {
            System.out.println("Database error/Connection stopped: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
