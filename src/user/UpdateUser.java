package user;

import ExceptionHandling.UpdateCancelledException;
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
    static Scanner sc=new Scanner(System.in);
    public static void editProfile()
    {
        while (true) {
            System.out.println("1. Update first Name/last Name");
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
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        try {
                            new UpdateUser().updateName();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 2:
                        try {
                            new UpdateUser().updateUsername();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        }
                        System.out.println();
                        break;
                    case 3:
                        try {
                            new UpdateUser().updatePassword();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 4:
                        try {
                            new UpdateUser().updateContact();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 5:
                        try {
                            new UpdateUser().updateAddress();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 6:
                        try {
                            new UpdateUser().updateAge();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 7:
                        try {
                            new UpdateUser().updateGenderPreference();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 8:
                        try {
                            new UpdateUser().updateGender();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 9:
                        try {
                            new UpdateUser().updateProfilePicture();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 10:
                        try {
                            new UpdateUser().updateQualification();
                            System.out.println("Back to Edit profile page!");
                        } catch (UpdateCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Back to Edit profile page!");
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Back to Edit profile page!");
                        }
                        break;
                    case 11:
                        System.out.println("Returning to HomePage!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        System.out.println("Try again!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Integer Value Only");
                sc.nextLine();
            }
        }
    }
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
        else         Session.getCurrentUserObject().setLast_name(newVal);

        System.out.println(which + " updated successfully!");
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
        System.out.println("Password updated successfully!");
    }

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
        } else  {
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
            Session.getCurrentUserObject().setImage_stream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
        } catch (IOException e) {

        }
        System.out.println("Profile picture updated successfully!");
    }

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
        Session.setCurrentUsername(newUsername);
        Session.setCurrentUserObject(Session.getUserObject(newUsername));
        System.out.println("Username updated successfully!");
    }
    static boolean checkUserNameDoesNotExist(String username)
    {
        if(username.trim().isEmpty())
        {
            System.out.println("Username must not be empty/Null!");
            return false;
        }
        else
        {
            String query="call getUserData(?)";
            try {
                CallableStatement cst=DatabaseConnector.getConnection().prepareCall(query);
                cst.setString(1,username);
                ResultSet rs=cst.executeQuery();
                if(rs.next())
                {
                    System.out.println("Username already exists!");
                    return false;
                }
                else
                {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Connection Lost!");
                return false;
            }
        }
    }
}
