package user;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import util.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ExceptionHandling.*;

/**
 * UserManager - handles registration, login, profile generation, deletion, and helpers.
 */
public class UserManager {

    public void Register() throws RegistrationCancelledException, SQLException {
        System.out.println();
        String first = InputUtils.promptUntilValid(
                "Enter first name: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        String last = InputUtils.promptUntilValid(
                "Enter last name: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        System.out.println(ConsoleColors.YELLOW+"PASSWORD MUST BE 8 CHARACTERS LONG,NO SPACE ALLOWED,MUST CONTAIN ATLEAST 1 SPECIAL CHARACTER!"+ConsoleColors.RESET);
        String pass = InputUtils.promptUntilValid(
                "Enter password: ",
                UserManager::verifyPassword,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        System.out.println(ConsoleColors.YELLOW+"NOTE: YOU MUST BE 21 YEARS OLD OR OLDER!"+ConsoleColors.RESET);
        String dob = InputUtils.promptUntilValid(
                "Enter birth date (YYYY-MM-DD): ",
                UserManager::verifyBirthDate,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        String gender = InputUtils.promptUntilValid(
                "Enter gender (Male/Female/Other): ",
                UserManager::verifyGender,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        String pref = InputUtils.promptUntilValid(
                "Enter gender preference (Male/Female/Other): ",
                UserManager::verifyGender,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        int height = InputUtils.promptInt(
                "Enter height in cm: ",
                UserManager::verifyHeight,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );

        System.out.println(ConsoleColors.YELLOW+"NOTE: MOBILE NUMBER MUST START 6–9,AND SHOULD CONTAIN 10 DIGITS!"+ConsoleColors.RESET);
        String mobile = InputUtils.promptUntilValid(
                "Enter mobile number: ",
                UserManager::verifyMobileNumber,
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );

        String email = "";
        do {
            email = InputUtils.promptUntilValid(
                    "Enter email: ",
                    UserManager::verifyEmail,
                    () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
            );

            String otp = OTPGenerator.generateOTP(6);
            System.out.println(ConsoleColors.YELLOW+"Sending OTP to your email..."+ConsoleColors.RESET);
            boolean sent = EmailUtil.sendMail(email, "Your OTP code", "Your otp for registration is " + otp);
            if (!sent) {
                System.out.println(ConsoleColors.RED+"Failed to send OTP to email. Try again later."+ConsoleColors.RESET);
                continue;
            }
            try {
                String userOTPInput = InputUtils.promptUntilValid(
                        "Enter the OTP sent to your email/Press [B] to re-enter you email: ",
                        s -> s.equals(otp),
                        () -> new GoBackException(ConsoleColors.RED+"User chose to re enter his email!"+ConsoleColors.RESET)
                );
                break;
            } catch (GoBackException e) {
                System.out.println();
                System.out.println(e.getMessage());
            }

        } while (true);

        String city = InputUtils.promptUntilValid(
                "Enter city: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        String state = InputUtils.promptUntilValid(
                "Enter state: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        String qual = InputUtils.promptUntilValid(
                "Enter qualification: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );

        String diet = InputUtils.promptOptional(
                "Dietary choice (veg/non-veg/vegan)/[S]kip: ",
                "Not Mentioned",
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );
        while (!diet.equals("Not Mentioned") && !verifyDietaryPreference(diet)) {
            System.out.println(ConsoleColors.YELLOW+"Invalid—try again or 'S' to skip."+ConsoleColors.RESET);
            diet = InputUtils.promptOptional(
                    "Dietary choice (veg/non-veg/vegan)/[S]kip: ",
                    "Not Mentioned",
                    () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
            );
        }

        String bio = InputUtils.promptOptional(
                "Bio/[S]kip: ",
                "Not mentioned",
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
        );

        InputStream pic = null;
        while (true) {
            String choice = InputUtils.promptUntilValid(
                    "Upload picture? Yes/[S]kip/[B]ack: ",
                    s -> s.equalsIgnoreCase("Yes")
                            || s.equalsIgnoreCase("S")
                            || s.equalsIgnoreCase("B"),
                    () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)
            );
            if (choice.equalsIgnoreCase("B"))
                throw new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET);
            if (choice.equalsIgnoreCase("S"))
                break;

            String path = InputUtils.promptUntilValid(
                    "Enter image path/[B]ack: ",
                    p -> {
                        try {
                            new FileInputStream(p).close(); // existence check
                            return true;
                        } catch (Exception e) {
                            System.out.println(ConsoleColors.RED+"Invalid path"+ConsoleColors.RESET);
                            return false;
                        }
                    },
                    () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)            );
            try {
                pic = new FileInputStream(path);
                break;
            } catch (FileNotFoundException e) {
                // won't happen because we checked above, but kept as-is
            }
        }

        String username = generateUsername(first, last);

        String sql = """
        INSERT INTO users 
          (first_name,last_name,birth_date,age,gender,gender_preference,
           height,mobile_number,email,city,state,qualification,
           dietary_choice,bio,profile_picture,username,password)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """;
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int idx = 1;
            ps.setString(idx++, first);
            ps.setString(idx++, last);
            ps.setString(idx++, dob);
            ps.setInt(idx++, User.getUserAge(dob));
            ps.setString(idx++, gender);
            ps.setString(idx++, pref);
            ps.setInt(idx++, height);
            ps.setString(idx++, mobile);
            ps.setString(idx++, email);
            ps.setString(idx++, city);
            ps.setString(idx++, state);
            ps.setString(idx++, qual);
            ps.setString(idx++, diet);
            ps.setString(idx++, bio);
            ps.setBlob(idx++, pic);
            ps.setString(idx++, username);
            ps.setString(idx, pass);
            try {
                ps.executeUpdate();
            } catch (MysqlDataTruncation e) {
                System.out.println(ConsoleColors.RED+"Invalid birth-date entered try again!"+ConsoleColors.RESET);
            }
        }

        System.out.println(ConsoleColors.YELLOW+"\nAnswer a few compatibility questions!"+ConsoleColors.RESET);

        String q1 = InputUtils.promptUntilValid(
                "Do you like traveling? (Yes/No): ",
                s -> s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String q2 = InputUtils.promptUntilValid(
                "Are you a morning person? (Yes/No): ",
                s -> s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String q3 = InputUtils.promptUntilValid(
                "Do you enjoy watching sports? (Yes/No): ",
                s -> s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String q4 = InputUtils.promptUntilValid(
                "Do you like pets? (Yes/No): ",
                s -> s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String q5 = InputUtils.promptUntilValid(
                "Do you enjoy reading books? (Yes/No): ",
                s -> s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String q6 = InputUtils.promptUntilValid(
                "Would you prefer city life or countryside life? (City/Countryside): ",
                s -> s.equalsIgnoreCase("City") || s.equalsIgnoreCase("Countryside"),
                () -> new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled"+ConsoleColors.RESET)        );

        String compSql = """
            INSERT INTO compatibility(username, travel, morning_person, sports, pets, books, lifestyle)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(compSql)) {
            ps.setString(1, username);
            ps.setString(2, q1);
            ps.setString(3, q2);
            ps.setString(4, q3);
            ps.setString(5, q4);
            ps.setString(6, q5);
            ps.setString(7, q6);
            ps.executeUpdate();
        }
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"Registration successful!"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"Your username: " + username+ConsoleColors.RESET);
        String profileDetails =
                "Hi " + first + ",\n\n" +
                        "Your registration is successful! Here are your profile details:\n\n" +
                        "Username: " + username + "\n" +
                        "Name: " + first + " " + last + "\n" +
                        "Birth Date: " + dob + "\n" +
                        "Age: " + User.getUserAge(dob) + " years\n" +
                        "Height: " + height + " cm\n" +
                        "Gender: " + gender + "\n" +
                        "Preferences: " + pref + "\n" +
                        "Dietary Choice: " + diet + "\n" +
                        "City: " + city + "\n" +
                        "State: " + state + "\n" +
                        "Bio: " + bio + "\n\n" +
                        "Start finding your matches today!";

        System.out.println(ConsoleColors.GREEN+"Sending profile details to your email..."+ConsoleColors.RESET);
        EmailUtil.sendMail(
                email,
                "Welcome to MatchMate ❤️",
                profileDetails
        );

    }

    public boolean Login() throws LoginCancelledException, SQLException {
        System.out.println();
        while (true) {
            String enteredUsername = InputUtils.promptUntilValid(
                    "Enter username (or U if you forgot username): ",
                    s -> !s.isEmpty(),
                    () -> new LoginCancelledException("Login cancelled by user.")
            );

            if (enteredUsername.trim().equalsIgnoreCase("U")) {
                try {
                    new EmailUtil().forgotUsername();
                } catch (GoBackException e) {
                    System.out.println(ConsoleColors.YELLOW+"Back to Login!"+ConsoleColors.RESET);
                }
                continue;
            }

            String enteredPassword = "";
            try {
                enteredPassword = InputUtils.promptUntilValid(
                        "Enter password (or F if you forgot password)/Enter [B] to re-enter username: ",
                        s -> !s.isEmpty(),
                        () -> new GoBackException(ConsoleColors.YELLOW+"User chose to Re-enter username."+ConsoleColors.RESET)
                );
            } catch (GoBackException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (enteredPassword.equalsIgnoreCase("F")) {
                try {
                    new EmailUtil().forgotPassword();
                } catch (GoBackException e) {
                    System.out.println(ConsoleColors.YELLOW+"Back to Login!"+ConsoleColors.RESET);
                }
                continue;
            }

            String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, enteredUsername);
                ps.setString(2, enteredPassword);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println();
                        System.out.println(ConsoleColors.GREEN+"Login Successful"+ConsoleColors.RESET);
                        Session.setCurrentUsername(enteredUsername);
                        Session.setCurrentUserObject(Session.getUserObject(enteredUsername));
                        return true;
                    }
                }
            }

            String retry = InputUtils.promptUntilValid(
                    ConsoleColors.RED+"Incorrect username or password. [C]ontinue / [B]ack: "+ConsoleColors.RESET,
                    s -> s.equalsIgnoreCase("C") || s.equalsIgnoreCase("B"),
                    () -> new LoginCancelledException("Login cancelled by user.")
            );
        }
    }

    public static boolean verifyPassword(String pass) {
        if (pass.length() < 8) {
            System.out.println(ConsoleColors.RED+"Password must be 8 characters long! "+ConsoleColors.RESET);
            return false;
        } else {
            boolean hasSpecialChar = false;
            boolean hasSpace = false;
            for (int i = 0; i < pass.length(); i++) {
                if (!Character.isLetter(pass.charAt(i)) && !Character.isDigit(pass.charAt(i))) {
                    hasSpecialChar = true;
                }
                if (pass.charAt(i) == ' ') {
                    hasSpace = true;
                }
            }
            if (hasSpecialChar && !hasSpace) {
                return true;
            } else {
                System.out.println(ConsoleColors.RED+"There should be atleast one special character & Space not allowed!"+ConsoleColors.RESET);
                return false;
            }
        }
    }

    static boolean verifyBirthDate(String bd) {
        if (bd.length() != 10) {
            System.out.println(ConsoleColors.RED+"Please Enter Birthdate in YYYY-MM-DD format!"+ConsoleColors.RESET);
            return false;
        } else {
            if (bd.charAt(4) != '-' || bd.charAt(7) != '-') {
                System.out.println(ConsoleColors.RED+"Please Enter Birthdate in YYYY-MM-DD format!"+ConsoleColors.RESET);
                return false;
            } else {
                for (int i = 0; i < bd.length(); i++) {
                    if (Character.isDigit(bd.charAt(i)) || (bd.charAt(i) == '-' && (i == 4 || i == 7))) {
                        // ok
                    } else {
                        System.out.println(ConsoleColors.RED+"Invalid birth-date format"+ConsoleColors.RESET);
                        return false;
                    }
                }

                int age = User.getUserAge(bd);
                if (verifyAge(age)) {
                    return true;
                } else {
                    System.out.println(ConsoleColors.RED+"Invalid birth-date/You must be 21 years old or older"+ConsoleColors.RESET);
                    return false;
                }
            }
        }
    }

    public static boolean verifyAge(int age) {
        if (age >= 21 && age <= 100) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verifyMobileNumber(String mo) {
        if (mo.length() != 10) {
            return false;
        } else if (mo.charAt(0) == '9' || mo.charAt(0) == '8' || mo.charAt(0) == '7' || mo.charAt(0) == '6') {
            for (int i = 0; i < mo.length(); i++) {
                if (!Character.isDigit(mo.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkEmailExists(String email) {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String q = "SELECT * FROM Users where email=?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean verifyEmail(String email) {
        boolean isEmailFormatValid = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                isEmailFormatValid = true;
                break;
            }
        }
        if (isEmailFormatValid) {
            boolean emailExist = checkEmailExists(email);
            if (emailExist) {
                System.out.println(ConsoleColors.RED+"This E-mail already exists!"+ConsoleColors.RESET);
                return false;
            } else {
                return true;
            }
        } else {
            System.out.println(ConsoleColors.RED+"Invalid E-mail format!"+ConsoleColors.RESET);
            return false;
        }
    }

    public static boolean verifyGender(String gender) {
        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other")) {
            return true;
        } else {
            System.out.println(ConsoleColors.RED+"INVALID GENDER ENTRY! Please enter again"+ConsoleColors.RESET);
            return false;
        }

    }

    static boolean verifyDietaryPreference(String dietaryPreference) {
        if (dietaryPreference.equalsIgnoreCase("veg") || dietaryPreference.equalsIgnoreCase("non-veg")
                || dietaryPreference.equalsIgnoreCase("vegan")) {
            return true;
        } else {
            System.out.println(ConsoleColors.RED+"INVALID DIETARY PREFERENCE ENTRY! Please enter again"+ConsoleColors.RESET);
            return false;
        }
    }

    static boolean verifyHeight(int height) {
        if (height > 272 || height < 50) {
            System.out.println(ConsoleColors.RED+"Invalid height!"+ConsoleColors.RESET);
            return false;
        }
        return true;
    }

    public static String generateUsername(String firstName, String lastName) {
        String prefix = "MM" + firstName.toUpperCase().charAt(0) + lastName.toUpperCase().charAt(0);
        try {
            Connection con = DatabaseConnector.getConnection();
            String query = "SELECT COUNT(*) FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            int nextNumber;
            String username = prefix + "00";
            do {
                nextNumber = count + 1;

            } while (!UpdateUser.checkUserNameDoesNotExist(username + nextNumber));
            username += nextNumber;
            rs.close();
            ps.close();
            con.close();
            return username;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegistrationCancelledException(ConsoleColors.RED+"Registration cancelled by some connection error!"+ConsoleColors.RESET);
        }

    }

    public void deleteAccount() throws SQLException {
        String username = Session.getCurrentUsername();
        String query = "DELETE from users where username=?";
        PreparedStatement pst = DatabaseConnector.getConnection().prepareStatement(query);
        pst.setString(1, username);
        pst.executeUpdate();
        Session.setCurrentUserObject(null);
        Session.setCurrentUsername(null);
        pst.close();
    }

    public void generateUserProfile(User u, boolean showContact) {
        System.out.println(ConsoleColors.YELLOW+"NOTE: Profiles will be saved in C://profile_documents"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+"Downloading profile..."+ConsoleColors.RESET);
        File dir = new File("C://profile_documents");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String content =
                "Username             : " + u.getUsername() + "\n" +
                        "Name                 : " + u.getFirst_name() + " " + u.getLast_name() + "\n" +
                        "Birth Date           : " + u.getBirth_date() + "\n" +
                        "Age                  : " + u.getAge() + " years" + "\n" +
                        "Height               : " + u.getHeight() + " cm" + "\n" +
                        "Gender               : " + u.getGender() + "\n" +
                        "Gender Preferences   : " + u.getGender_preference() + "\n" +
                        "Qualification        : " + u.getQualification() + "\n" +
                        "Dietary Preferences  : " + u.getDietary_choice() + "\n" +
                        "City                 : " + u.getCity() + "\n" +
                        "State                : " + u.getState() + "\n" +
                        "Bio                  : " + u.getBio() + "\n";

        if (showContact) {
            content +=
                    "Phone no.            : " + u.getMobile_number() + "\n" +
                            "E-mail               : " + u.getEmail() + "\n";
        }
        File arr[] = dir.listFiles();
        int count = arr.length;
        count++;
        try {
            FileWriter fw = new FileWriter("C://profile_documents/downloaded_profile_"+u.getUsername()+count + ".txt");
            fw.write(content);
            fw.close();
            System.out.println(ConsoleColors.GREEN+"Profile download successfully!"+ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED+"Try again...some error occurred with file writing!"+ConsoleColors.RESET);
            e.printStackTrace();
        }

    }
}
