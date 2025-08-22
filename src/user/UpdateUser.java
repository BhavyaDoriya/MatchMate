package user;

import ExceptionHandling.UpdateCancelledException;
import util.ConsoleColors;
import util.DatabaseConnector;
import util.InputUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdateUser {
    static Scanner sc = new Scanner(System.in);

    // ================================
    // Main Edit Profile Menu
    // ================================
    public static void editProfile()
    {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n                                                                     --- Edit Profile Menu ---" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"1.  Update First Name / Last Name"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"2.  Update Username"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"3.  Update Password"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"4.  Update Contact Details"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"5.  Update Address (City, State)"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"6.  Recompute Age"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"7.  Update Gender Preferences"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"8.  Update Gender"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"9.  Update Profile Picture"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"10. Update Qualification"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD+"11. Exit"+ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD +"Enter choice: " + ConsoleColors.RESET);
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        try {
                            new UpdateUser().updateName();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(ConsoleColors.RED+e.getMessage()+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 2:
                        try {
                            new UpdateUser().updateUsername();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(ConsoleColors.RED+e.getMessage()+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+e.getMessage()+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 3:
                        try {
                            new UpdateUser().updatePassword();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(ConsoleColors.RED+e.getMessage()+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 4:
                        try {
                            new UpdateUser().updateContact();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 5:
                        try {
                            new UpdateUser().updateAddress();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 6:
                        try {
                            new UpdateUser().updateAge();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 7:
                        try {
                            new UpdateUser().updateGenderPreference();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.GREEN+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 8:
                        try {
                            new UpdateUser().updateGender();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 9:
                        try {
                            new UpdateUser().updateProfilePicture();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 10:
                        try {
                            new UpdateUser().updateQualification();
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED+"Connection lost!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Back to Edit profile page!"+ConsoleColors.RESET);
                        }
                        break;
                    case 11:
                        System.out.println();
                        System.out.println(ConsoleColors.GREEN+"Returning to HomePage!"+ConsoleColors.RESET);
                        return;
                    default:
                        System.out.println(ConsoleColors.RED+"Invalid choice!"+ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED+"Try again!"+ConsoleColors.RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED+"Please Enter Integer Value Only"+ConsoleColors.RESET);
                sc.nextLine();
            }
        }
    }


    // ================================
    // Update Methods
    // ================================

    public void updateName() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String which = InputUtils.promptUntilValid(
                "Do you want to update First Name or Last Name: ",
                s -> s.equalsIgnoreCase("First Name") || s.equalsIgnoreCase("Last Name"),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        boolean isFirst = which.equalsIgnoreCase("First Name");
        String column = isFirst ? "first_name" : "last_name";
        String prompt = isFirst ? "Enter your new first name: " : "Enter your new last name: ";

        String newVal = InputUtils.promptUntilValid(
                prompt,
                s -> !s.isEmpty(),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String sql = "UPDATE users SET " + column + " = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newVal);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        if (isFirst) Session.getCurrentUserObject().setFirst_name(newVal);
        else Session.getCurrentUserObject().setLast_name(newVal);

        System.out.println(ConsoleColors.GREEN_BOLD + which + " updated successfully!" + ConsoleColors.RESET);
    }

    public void updateUsername() throws UpdateCancelledException, SQLException {
        String oldUsername = Session.getCurrentUsername();

        String newUsername = InputUtils.promptUntilValid(
                "Enter your new username: ",
                UpdateUser::checkUserNameDoesNotExist,
                () -> new UpdateCancelledException("Updation cancelled by user.")
        );

        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, oldUsername);
            ps.executeUpdate();
        }

        // Refresh session
        Session.setCurrentUsername(newUsername);
        Session.setCurrentUserObject(Session.getUserObject(newUsername));

        System.out.println(ConsoleColors.GREEN_BOLD + "Username updated successfully!" + ConsoleColors.RESET);
    }

    public void updatePassword() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        InputUtils.promptUntilValid(
                "Enter your current password: ",
                s -> s.equals(Session.getCurrentUserObject().getPassword()),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String newPass = InputUtils.promptUntilValid(
                "Enter your new password: ",
                UserManager::verifyPassword,
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPass);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        Session.getCurrentUserObject().setPassword(newPass);
        System.out.println(ConsoleColors.GREEN_BOLD + "Password updated successfully!" + ConsoleColors.RESET);
    }

    public void updateContact() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String which = InputUtils.promptUntilValid(
                "Would you like to update Email or Mobile Number? ",
                s -> s.equalsIgnoreCase("Email") || s.equalsIgnoreCase("Mobile Number"),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        if (which.equalsIgnoreCase("Email")) {
            String newEmail = InputUtils.promptUntilValid(
                    "Enter your new email address: ",
                    UserManager::verifyEmail,
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );

            String sql = "UPDATE users SET email = ? WHERE username = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, newEmail);
                ps.setString(2, username);
                ps.executeUpdate();
            }
            Session.getCurrentUserObject().setEmail(newEmail);
            System.out.println(ConsoleColors.GREEN_BOLD + "Email updated successfully!" + ConsoleColors.RESET);

        } else {
            String newMob = InputUtils.promptUntilValid(
                    "Enter your new Mobile Number: ",
                    UserManager::verifyMobileNumber,
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );

            String sql = "UPDATE users SET mobile_number = ? WHERE username = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, newMob);
                ps.setString(2, username);
                ps.executeUpdate();
            }
            Session.getCurrentUserObject().setMobile_number(newMob);
            System.out.println(ConsoleColors.GREEN_BOLD + "Mobile Number updated successfully!" + ConsoleColors.RESET);
        }
    }

    public void updateAddress() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String which = InputUtils.promptUntilValid(
                "Would you like to update City or State & City? ",
                s -> s.equalsIgnoreCase("City") || s.equalsIgnoreCase("State & City") || s.equalsIgnoreCase("State and City"),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        if (which.equalsIgnoreCase("City")) {
            String newCity = InputUtils.promptUntilValid(
                    "Enter your new city: ",
                    s -> !s.isEmpty(),
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );

            String sql = "UPDATE users SET city = ? WHERE username = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, newCity);
                ps.setString(2, username);
                ps.executeUpdate();
            }
            Session.getCurrentUserObject().setCity(newCity);
            System.out.println(ConsoleColors.GREEN_BOLD + "City updated successfully!" + ConsoleColors.RESET);

        } else {
            String newCity = InputUtils.promptUntilValid(
                    "Enter your new city: ",
                    s -> !s.isEmpty(),
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );

            String newState = InputUtils.promptUntilValid(
                    "Enter your new state: ",
                    s -> !s.isEmpty(),
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );

            String sql = "UPDATE users SET city = ?, state = ? WHERE username = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, newCity);
                ps.setString(2, newState);
                ps.setString(3, username);
                ps.executeUpdate();
            }
            Session.getCurrentUserObject().setCity(newCity);
            Session.getCurrentUserObject().setState(newState);
            System.out.println(ConsoleColors.GREEN_BOLD + "Address (city & state) updated successfully!" + ConsoleColors.RESET);
        }
    }

    public void updateAge() throws SQLException {
        String username = Session.getCurrentUsername();
        int newAge = User.getUserAge(Session.getCurrentUserObject().getBirth_date());

        Session.getCurrentUserObject().setAge(newAge);

        String sql = "UPDATE users SET age = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newAge);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        System.out.println(ConsoleColors.GREEN_BOLD + "Age recomputed successfully!" + ConsoleColors.RESET);
    }

    public void updateGenderPreference() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String pref = InputUtils.promptUntilValid(
                "Enter gender preference (male/female/other): ",
                UserManager::verifyGender,
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String sql = "UPDATE users SET gender_preference = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pref);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        Session.getCurrentUserObject().setGender_preference(pref);
        System.out.println(ConsoleColors.GREEN_BOLD + "Gender preference updated successfully!" + ConsoleColors.RESET);
    }

    public void updateGender() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String gender = InputUtils.promptUntilValid(
                "Enter gender (male/female/other): ",
                UserManager::verifyGender,
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String sql = "UPDATE users SET gender = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gender);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        Session.getCurrentUserObject().setGender(gender);
        System.out.println(ConsoleColors.GREEN_BOLD + "Gender updated successfully!" + ConsoleColors.RESET);
    }

    public void updateProfilePicture() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String path = InputUtils.promptUntilValid(
                "Enter your new image path: ",
                s -> {
                    try {
                        new FileInputStream(s).close();
                        return true;
                    } catch (Exception ex) {
                        System.out.println(ConsoleColors.RED + "File not found!" + ConsoleColors.RESET);
                        return false;
                    }
                },
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        try (InputStream fis = new FileInputStream(path);
             Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE users SET profile_picture = ? WHERE username = ?"
             )) {
            ps.setBlob(1, fis);
            ps.setString(2, username);
            ps.executeUpdate();

            Session.getCurrentUserObject().setImage_stream(new FileInputStream(path));
            System.out.println(ConsoleColors.GREEN_BOLD + "Profile picture updated successfully!" + ConsoleColors.RESET);

        } catch (FileNotFoundException ignored) {
        } catch (IOException ignored) {
        }
    }

    public void updateQualification() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String qual = InputUtils.promptUntilValid(
                "Enter your qualification: ",
                s -> !s.isEmpty(),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        String sql = "UPDATE users SET qualification = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, qual);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        Session.getCurrentUserObject().setQualification(qual);
        System.out.println(ConsoleColors.GREEN_BOLD + "Qualification updated successfully!" + ConsoleColors.RESET);
    }

    // ================================
    // Username availability checker
    // ================================
    public static boolean checkUserNameDoesNotExist(String username) {
        String u = username == null ? "" : username.trim();

        if (u.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Username must not be empty/null!" + ConsoleColors.RESET);
            return false;
        }
        if (u.length() < 3) {
            System.out.println(ConsoleColors.RED + "Username must be at least 3 characters." + ConsoleColors.RESET);
            return false;
        }
        // optional: restrict to letters/digits/._ starting with a letter
        if (!u.matches("^[A-Za-z][A-Za-z0-9._]{2,}$")) {
            System.out.println(ConsoleColors.RED + "Only letters, digits, dot, underscore; must start with a letter." + ConsoleColors.RESET);
            return false;
        }

        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println(ConsoleColors.YELLOW + "Username already exists!" + ConsoleColors.RESET);
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Connection Lost!" + ConsoleColors.RESET);
            return false;
        }
    }
}
