package match;

import user.Session;
import user.User;
import util.ConsoleColors;
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

            System.out.println(ConsoleColors.GREEN+"Profile liked successfully!"+ConsoleColors.RESET);

            User likedUser = Session.getUserObject(liked_username);
                System.out.println(ConsoleColors.GREEN+"Sending Like notification to: " + likedUser.getFirst_name()+ConsoleColors.RESET);
                EmailUtil.sendMail(
                        likedUser.getEmail(),
                        "You got a new Like! ❤️",
                        "Hi " + likedUser.getFirst_name() + ",\n\n"
                                + Session.getCurrentUserObject().getFirst_name()
                                + " liked your profile!"
                );
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(ConsoleColors.YELLOW+"You have already liked this profile."+ConsoleColors.RESET);
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED+"Database error/Connection stopped: " + e.getMessage()+ConsoleColors.RESET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void UnlikeUser(String unlikedUsername) {
        String query = "DELETE FROM likes WHERE liker = ? AND liked = ?";

        try {
            PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
            pst.setString(1, Session.getCurrentUsername());
            pst.setString(2, unlikedUsername);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println(ConsoleColors.GREEN+"Profile unliked successfully!"+ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.YELLOW+"You had not liked this profile before."+ConsoleColors.RESET);
            }
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED+"Database error while unliking profile: " + e.getMessage()+ConsoleColors.RESET);
        }

    }
}
