package user;

import ExceptionHandling.UpdateCancelledException;
import util.DatabaseConnector;
import util.InputUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

public class UpdateUser {
    static Scanner sc=new Scanner(System.in);
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
        System.out.println("11.Exit");
        int choice= sc.nextInt();
        switch(choice)
        {
            case 1:
                try {
                    new UpdateUser().updateName();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 2:
                try {
                    new UpdateUser().updateUsername();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 3:
                try {
                    new UpdateUser().updatePassword();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 4:
                try {
                    new UpdateUser().updateContact();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 5:
                try {
                    new UpdateUser().updateAddress();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 6:
                try {
                    new UpdateUser().updateAge();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 7:
                try {
                    new UpdateUser().updateGenderPreference();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 8:
                try {
                    new UpdateUser().updateGender();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 9:
                try {
                    new UpdateUser().updateProfilePicture();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 10:
                try {
                    new UpdateUser().updateQualification();
                }catch (UpdateCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                editProfile();
                break;
            case 11:
                break;
        }
    }
    /** 1) Update first OR last name */
    public void updateName() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        // choose which field
        String which = InputUtils.promptUntilValid(
                "Do you want to update First Name or Last Name: ",
                s -> s.equalsIgnoreCase("First Name") || s.equalsIgnoreCase("Last Name"),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        boolean isFirst = which.equalsIgnoreCase("First Name");
        String column = isFirst ? "first_name" : "last_name";
        String prompt = isFirst ? "Enter your new first name: " : "Enter your new last name: ";

        // get new value
        String newVal = InputUtils.promptUntilValid(
                prompt,
                s -> !s.isEmpty(),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        // run update
        String sql = "UPDATE users SET " + column + " = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newVal);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        // reflect in-memory
        if (isFirst) Session.getCurrentUserObject().setFirst_name(newVal);
        else         Session.getCurrentUserObject().setLast_name(newVal);

        System.out.println(which + " updated successfully!");
    }

    /** 2) Update password */
    public void updatePassword() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        // verify current
        InputUtils.promptUntilValid(
                "Enter your current password: ",
                s -> s.equals(Session.getCurrentUserObject().getPassword()),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        // new password
        String newPass = InputUtils.promptUntilValid(
                "Enter your new password: ",
                UserManager::verifyPassword,
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        // update DB
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPass);
            ps.setString(2, username);
            ps.executeUpdate();
        }

        Session.getCurrentUserObject().setPassword(newPass);
        System.out.println("Password updated successfully!");
    }

    /** 3) Update E‑mail OR Mobile Number */
    public void updateContact() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String which = InputUtils.promptUntilValid(
                "Would you like to update E-mail or Mobile Number ? ",
                s -> s.equalsIgnoreCase("E-mail")
                        || s.equalsIgnoreCase("Email")
                        || s.equalsIgnoreCase("Mobile Number"),
                () -> new UpdateCancelledException("Updation cancelled by user")
        );

        if (which.equalsIgnoreCase("E-mail") || which.equalsIgnoreCase("Email")) {
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
            System.out.println("E-Mail updated successfully!");
        } else /* Mobile Number */ {
            String newMob = InputUtils.promptUntilValid(
                    "Enter your new Mobile Number : ",
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
            System.out.println("Mobile Number updated successfully!");
        }
    }

    /** 4) Update City OR (City & State) */
    public void updateAddress() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();

        String which = InputUtils.promptUntilValid(
                "Would you like to update City or State & City? ",
                s -> s.equalsIgnoreCase("City")
                        || s.equalsIgnoreCase("State & City")
                        || s.equalsIgnoreCase("State and City"),
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
            System.out.println("City updated successfully!");
        } else {
            // both city and state
            String newCity = InputUtils.promptUntilValid(
                    "Enter your new city : ",
                    s -> !s.isEmpty(),
                    () -> new UpdateCancelledException("Updation cancelled by user")
            );
            String newState = InputUtils.promptUntilValid(
                    "Enter your new State : ",
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
            System.out.println("Address (city & state) updated successfully!");
        }
    }

    /** 5) Recompute age from birth_date in memory only */
    public void updateAge() throws SQLException {
        String username=Session.getCurrentUsername();
        int newAge=User.getUserAge(Session.getCurrentUserObject().getBirth_date());
        Session.getCurrentUserObject().setAge(newAge);
        String query="Update Users SET age=? where username=?";
        PreparedStatement pst=DatabaseConnector.getConnection().prepareStatement(query);
        pst.setInt(1,newAge);
        pst.setString(2,username);
        pst.executeUpdate();
        System.out.println("Age recomputed successfully!");
    }

    /** 6) Update gender preference */
    public void updateGenderPreference() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();
        String pref = InputUtils.promptUntilValid(
                "Enter gender preference(male/female/other): ",
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
        System.out.println("Gender preference updated successfully!");
    }

    /** 7) Update gender */
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
        System.out.println("Gender updated successfully!");
    }

    /** 8) Update profile picture */
    public void updateProfilePicture() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();
        String path = InputUtils.promptUntilValid(
                "Enter your new image path: ",
                s -> {
                    try {
                        new FileInputStream(s).close();
                        return true;
                    } catch (Exception ex) {
                        System.out.println("File not found!");
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
            // refresh memory stream
            Session.getCurrentUserObject().setImage_stream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            // never happens – we already tested above
        } catch (IOException e) {

        }
        System.out.println("Profile picture updated successfully!");
    }

    /** 9) Update qualification */
    public void updateQualification() throws UpdateCancelledException, SQLException {
        String username = Session.getCurrentUsername();
        String qual = InputUtils.promptUntilValid(
                "Enter your qualification :",
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
        System.out.println("Qualification updated successfully!");
    }
    /** 10) Update username */
    public void updateUsername() throws UpdateCancelledException, SQLException {
        String oldUsername = Session.getCurrentUsername();

        // prompt for a new username (or B to cancel)
        String newUsername = InputUtils.promptUntilValid(
                "Enter your new username: ",
                s -> {
                    // check non‑empty
                    if (s.isEmpty()) return false;
                    // check uniqueness by calling your stored proc
                    try (Connection con = DatabaseConnector.getConnection();
                         CallableStatement cst = con.prepareCall("CALL getUserData(?)")) {
                        cst.setString(1, s);
                        try (ResultSet rs = cst.executeQuery()) {
                            return !rs.next();  // valid if no row
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error checking username uniqueness!");
                        return false;
                    }
                },
                () -> new UpdateCancelledException("Updation cancelled by user.")
        );

        // update in database
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, oldUsername);
            ps.executeUpdate();
        }

        // reflect in-memory
        Session.getCurrentUserObject().setUsername(newUsername);
        Session.setCurrentUsername(newUsername);

        System.out.println("Username updated successfully!");
    }

}
