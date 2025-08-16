package user;
import util.DatabaseConnector;
import util.EmailOTP;
import util.InputUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import ExceptionHandling.*;
import util.OTPGenerator;

public class UserManager {
    static Scanner sc=new Scanner(System.in);
    public void Register()
            throws RegistrationCancelledException, SQLException
    {
        String first = InputUtils.promptUntilValid(
                "Enter first name: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String last = InputUtils.promptUntilValid(
                "Enter last name: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String pass = InputUtils.promptUntilValid(
                "Enter password: ",
                UserManager::verifyPassword,
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String dob = InputUtils.promptUntilValid(
                "Enter birth date (YYYY-MM-DD): ",
                UserManager::verifyBirthDate,
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String gender = InputUtils.promptUntilValid(
                "Enter gender (Male/Female/Other): ",
                UserManager::verifyGender,
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String pref = InputUtils.promptUntilValid(
                "Enter gender preference (Male/Female/Other): ",
                UserManager::verifyGender,
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        int height = InputUtils.promptInt(
                "Enter height in cm: ",
                UserManager::verifyHeight,
                () -> new RegistrationCancelledException("Registration cancelled")
        );

        System.out.println("Note: Mobile must start 6–9, 10 digits");
        String mobile = InputUtils.promptUntilValid(
                "Enter mobile number: ",
                UserManager::verifyMobileNumber,
                () -> new RegistrationCancelledException("Registration cancelled")
        );

        String email = InputUtils.promptUntilValid(
                "Enter email: ",
                UserManager::verifyEmail,
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String otp = OTPGenerator.generateOTP(6);
        boolean sent = EmailOTP.sendOTP(email, otp);
        if (!sent) {
            System.out.println("Failed to send OTP to email. Try again later.");
            return;
        }

        String userOTPInput = InputUtils.promptUntilValid(
                "Enter the OTP sent to your email: ",
                s -> s.equals(otp),
                () -> new RegistrationCancelledException("Registration cancelled")
        );

        String city = InputUtils.promptUntilValid(
                "Enter city: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String state = InputUtils.promptUntilValid(
                "Enter state: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        String qual = InputUtils.promptUntilValid(
                "Enter qualification: ",
                s -> !s.isEmpty(),
                () -> new RegistrationCancelledException("Registration cancelled")
        );

        String diet = InputUtils.promptOptional(
                "Dietary choice (veg/non-veg/vegan)/[S]kip: ",
                "Not Mentioned",
                () -> new RegistrationCancelledException("Registration cancelled")
        );
        while (!diet.equals("Not Mentioned") && !verifyDietaryPreference(diet)) {
            System.out.println("Invalid—try again or 'S' to skip.");
            diet = InputUtils.promptOptional(
                    "Dietary choice (veg/non-veg/vegan)/[S]kip: ",
                    "Not Mentioned",
                    () -> new RegistrationCancelledException("Registration cancelled")
            );
        }

        String bio = InputUtils.promptOptional(
                "Bio/[S]kip: ",
                "Not mentioned",
                () -> new RegistrationCancelledException("Registration cancelled")
        );

        // profile picture
        InputStream pic = null;
        while (true) {
            String choice = InputUtils.promptUntilValid(
                    "Upload picture? Yes/[S]kip/[B]ack: ",
                    s -> s.equalsIgnoreCase("Yes")
                            || s.equalsIgnoreCase("S")
                            || s.equalsIgnoreCase("B"),
                    () -> new RegistrationCancelledException("Registration cancelled")
            );
            if (choice.equalsIgnoreCase("B"))
                throw new RegistrationCancelledException("Registration cancelled");
            if (choice.equalsIgnoreCase("S"))
                break;

            String path = InputUtils.promptUntilValid(
                    "Enter image path/[B]ack: ",
                    p -> {
                        try {
                            new FileInputStream(p).close();
                            return true;
                        } catch (Exception e) {
                            System.out.println("Invalid path");
                            return false;
                        }
                    },
                    () -> new RegistrationCancelledException("Registration cancelled")
            );
            try
            {
                pic = new FileInputStream(path);
                break;
            } catch (FileNotFoundException e) {
                //Will never happen
            }
        }

        // auto‑gen username
        String username;
        do {
            username = generateUsername(first, last);
        } while (username == null);

        // insert
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
            ps.setInt   (idx++, User.getUserAge(dob));
            ps.setString(idx++, gender);
            ps.setString(idx++, pref);
            ps.setInt   (idx++, height);
            ps.setString(idx++, mobile);
            ps.setString(idx++, email);
            ps.setString(idx++, city);
            ps.setString(idx++, state);
            ps.setString(idx++, qual);
            ps.setString(idx++, diet);
            ps.setString(idx++, bio);
            ps.setBlob  (idx++, pic);
            ps.setString(idx++, username);
            ps.setString(idx,   pass);
            ps.executeUpdate();
        }

        System.out.println("Registration successful!");
        System.out.println("Your username: " + username);
    }

    public boolean Login() throws LoginCancelledException, SQLException {
        while (true) {
            // prompt for username (or B to cancel)
            String enteredUsername = InputUtils.promptUntilValid(
                    "Enter username: ",
                    s -> !s.isEmpty(),
                    () -> new LoginCancelledException("Login cancelled by user.")
            );

            // prompt for password (or B to cancel)
            String enteredPassword = InputUtils.promptUntilValid(
                    "Enter password: ",
                    s -> !s.isEmpty(),
                    () -> new LoginCancelledException("Login cancelled by user.")
            );

            // try authenticating
            String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
            try (Connection con = DatabaseConnector.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, enteredUsername);
                ps.setString(2, enteredPassword);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // successful login
                        System.out.println("Login Successful");
                        Session.setCurrentUsername(enteredUsername);
                        Session.setCurrentUserObject(Session.getUserObject(enteredUsername));
                        return true;
                    }
                }
            }

            // wrong credentials — ask whether to retry or back out
            String retry = InputUtils.promptUntilValid(
                    "Incorrect username or password. [C]ontinue / [B]ack: ",
                    s -> s.equalsIgnoreCase("C") || s.equalsIgnoreCase("B"),
                    () -> new LoginCancelledException("Login cancelled by user.")
            );
            if (retry.equalsIgnoreCase("B")) {
                throw new LoginCancelledException("Login cancelled by user.");
            }
            // else loop and prompt again
        }
    }

    public static boolean verifyPassword(String pass)
    {
        if(pass.length()<8)
        {
            System.out.println("Password must be 8 characters long! ");
            return false;
        }
        else
        {
            boolean hasSpecialChar=false;
            boolean hasSpace=false;
            for(int i=0;i<pass.length();i++)
            {
                if(!Character.isLetter(pass.charAt(i))&&!Character.isDigit(pass.charAt(i)))
                {
                    hasSpecialChar=true;
                }
                if(pass.charAt(i)==' ')
                {
                    hasSpace=true;
                }
            }
            if(hasSpecialChar&&!hasSpace)
            {
                return true;
            }
            else
            {
                System.out.println("There should be atleast one special character & Space not allowed!");
                return false;
            }
        }
    }
    static boolean verifyBirthDate(String bd)
    {

        if(bd.length()!=10)
        {
            System.out.println("Please Enter Birthdate in YYYY-MM-DD format!");
            return false;
        }
        else
        {
            if(bd.charAt(4)!='-'||bd.charAt(7)!='-')
            {
                System.out.println("Please Enter Birthdate in YYYY-MM-DD format!");
                return false;
            }
            else
            {
                for(int i=0;i<bd.length();i++)
                {
                    if(Character.isDigit(bd.charAt(i))||(bd.charAt(i)=='-'&&(i==4||i==7)))
                    {

                    }
                    else
                    {
                        System.out.println("Invalid birth-date format");
                        return false;
                    }
                }

                int age=User.getUserAge(bd);
                if(age>=21 && age<122)
                {
                    return true;
                }
                else
                {
                    System.out.println("Invalid birth-date/You must be 21 years old or older");
                    return false;
                }
            }
        }
    }
    public static boolean verifyMobileNumber(String mo)
    {
        if(mo.length()!=10)
        {
            return false;
        }
        else if(mo.charAt(0)=='9'||mo.charAt(0)=='8'||mo.charAt(0)=='7'||mo.charAt(0)=='6')
        {
            for(int i=0;i<mo.length();i++)
            {
                if(!Character.isDigit(mo.charAt(i)))
                {
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkEmailExists(String email)
    {
        try {
            Connection conn= DatabaseConnector.getConnection();
            String q="SELECT * FROM Users where email=?";
            PreparedStatement pst=conn.prepareStatement(q);
            pst.setString(1,email);
            ResultSet rs=pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public static boolean verifyEmail(String email)
    {
        boolean isEmailFormatValid=false;
        for(int i=0;i<email.length();i++)
        {
            if(email.charAt(i)=='@')
            {
                isEmailFormatValid=true;
                break;
            }
        }
        if(isEmailFormatValid)
        {
            boolean emailExist=checkEmailExists(email);
            if(emailExist)
            {
                System.out.println("This E-mail already exists!");
                return false;
            }
            else
            {
                return true;
            }
        }
        else {
            System.out.println("Invalid E-mail format!");
            return false;
        }
    }
    public static boolean verifyGender(String gender)
    {
        if(gender.equalsIgnoreCase("male")||gender.equalsIgnoreCase("female")||gender.equalsIgnoreCase("other"))
        {
            return true;
        }
        else
        {
            System.out.println("Invalid gender!! Please Enter again");
            return false;
        }

    }

    static boolean verifyDietaryPreference(String dietaryPreference)
    {
        if(dietaryPreference.equalsIgnoreCase("veg")||dietaryPreference.equalsIgnoreCase("non-veg")||dietaryPreference.equalsIgnoreCase("vegan"))
        {
            return true;
        }
        else {
            System.out.println("Invalid choice!! Please Enter again");
            return false;
        }
}
    static  boolean verifyHeight(int height)
    {
        if(height>272||height<100)
        {
            System.out.println("Invalid height!");
            return false;
        }
        return true;
    }

    public static String generateUsername(String firstName, String lastName)  {
        String prefix = "MM" + firstName.toUpperCase().charAt(0) + lastName.toUpperCase().charAt(0);
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String query = "SELECT COUNT(*) FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            // Increment count to create the new unique username
            int nextNumber = count + 1;
            return prefix+"00"+nextNumber;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
    public void deleteAccount() throws SQLException
    {
        String username=Session.getCurrentUsername();
        String query="DELETE from users where username=?";
        PreparedStatement pst=DatabaseConnector.getConnection().prepareStatement(query);
        pst.setString(1,username);
        pst.executeUpdate();
        Session.setCurrentUserObject(null);
        Session.setCurrentUsername(null);
    }
}
