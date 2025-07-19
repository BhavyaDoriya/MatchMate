package user;

import util.DatabaseConnector;
import util.InputUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class UserManager {
static Scanner sc=new Scanner(System.in);
    static public void Register() {
        String first_name = InputUtils.promptUntilValid("Enter first name: ", input -> !input.trim().isEmpty());
        String last_name = InputUtils.promptUntilValid("Enter last name: ", input -> !input.trim().isEmpty());
        String pass = InputUtils.promptUntilValid("Enter password: ", UserManager::verifyPassword);
        String birth_date = InputUtils.promptUntilValid("Enter birth date (YYYY-MM-DD): ", UserManager::verifyBirthDate);
        String gender = InputUtils.promptUntilValid("Enter gender: ", UserManager::verifyGender);
        String gender_preferences = InputUtils.promptUntilValid("Enter gender preference: ", UserManager::verifyGender);
        int height = InputUtils.promptInt("Enter height (in cm): ", UserManager::verifyHeight);
        String mo = InputUtils.promptUntilValid("Enter mobile number: ", UserManager::verifyMobileNumber);
        String email = InputUtils.promptUntilValid("Enter email: ", input -> !checkEmailExists(input));
        String city = InputUtils.promptUntilValid("Enter your city: ", input -> !input.trim().isEmpty());
        String state = InputUtils.promptUntilValid("Enter your state: ", input -> !input.trim().isEmpty());
        String qualification = InputUtils.promptUntilValid("Enter your qualification: ", input -> !input.trim().isEmpty());
        String dietary_preferences = InputUtils.promptOptional("Enter your Dietary preference/[S]kip: ", "Not Mentioned");
        while (!dietary_preferences.equals("Not Mentioned") && !verifyDietaryPreference(dietary_preferences)) {
            System.out.println("Invalid input. Try again or enter 'S' to skip.");
            dietary_preferences = InputUtils.promptOptional("Enter your Dietary preference/[S]kip: ", "Not Mentioned");
        }

        String bio = InputUtils.promptOptional("Enter Bio/[S]kip: ", "Not mentioned");

        int age = User.getUserAge(birth_date);


        InputStream image_stream = null;

        while (true) {
            System.out.print("Upload profile picture (Yes/S to skip): ");
            String choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("Yes")) {
                while (true) {
                    System.out.print("Enter image path: ");
                    String path = sc.nextLine().trim();
                    try {
                        image_stream = new FileInputStream(path);
                        break;
                    } catch (FileNotFoundException e) {
                        System.out.println("Invalid file path. Try again.");
                    }
                }
                break; // exit outer loop after successful image selection
            } else if (choice.equalsIgnoreCase("S")) {
                image_stream = null;
                break; // skip profile picture
            } else {
                System.out.println("Invalid input. Please type 'Yes' to upload or 'S' to skip.");
            }
        }


        String username;
        do {
            username = generateUsername(first_name, last_name);
        } while (username == null);

        User u=new User(first_name,last_name,birth_date,age,gender,gender_preferences,
                    height,mo,email,city,state,qualification,dietary_preferences,bio,
                    image_stream,username,pass);
        String sql = "INSERT INTO users (first_name, last_name, birth_date, age, gender, gender_preference, " +
                "height, mobile_number, email, city, state, qualification, dietary_choice, " +
                "bio, profile_picture, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn=DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,first_name);
            pstmt.setString(2,last_name);
            pstmt.setString(3,birth_date);
            pstmt.setInt(4,age);
            pstmt.setString(5,gender);
            pstmt.setString(6,gender_preferences);
            pstmt.setInt(7,height);
            pstmt.setString(8,mo);
            pstmt.setString(9,email);
            pstmt.setString(10,city);
            pstmt.setString(11,state);
            pstmt.setString(12,qualification);
            pstmt.setString(13,dietary_preferences);
            pstmt.setString(14,bio);
            pstmt.setBlob(15,image_stream);
            pstmt.setString(16,username);
            pstmt.setString(17,pass);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Registration successfully!");
    }
    public static void login()
    {
        try
        {
            boolean flag= true;
            while(flag)
            {
                System.out.println("Enter username:");
                String enteredUsername= sc.next();
                System.out.println("Enter password:");
                String enteredPassword= sc.next();
                PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                ps.setString(1, enteredUsername);
                ps.setString(2, enteredPassword);
                ResultSet rs = ps.executeQuery();

                if(rs.next()) {

                } else {
                    System.out.println("Please enter username or password again!!");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    static boolean verifyPassword(String pass)
    {
        if(pass.length()<8)
        {
            System.out.println("Password must be 8 characters long! ");
            return false;
        }
        else
        {
            boolean hasSpecialChar=false;
            for(int i=0;i<pass.length();i++)
            {
                if(!Character.isLetter(pass.charAt(i))&&!Character.isDigit(pass.charAt(i)))
                {
                    hasSpecialChar=true;
                }
            }
            if(hasSpecialChar)
            {
                return true;
            }
            else
            {
                System.out.println("There should be atleast one special character!");
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
                    if(Character.isDigit(bd.charAt(i))||bd.charAt(i)=='-')
                    {

                    }
                    else
                    {
                        return false;
                    }
                }

                int age=User.getUserAge(bd);
                if(age!=-1&&age<122)
                {
                    return true;
                }
                else
                {
                    System.out.println("Invalid birth-date");
                    return false;
                }
            }
        }
    }
    static boolean verifyMobileNumber(String mo)
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

    static boolean checkEmailExists(String email)
    {
        try {
            Connection conn= DatabaseConnector.getConnection();
            String q="SELECT * FROM Users where email=?";
            PreparedStatement pst=conn.prepareStatement(q);
            pst.setString(1,email);
            ResultSet rs=pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }

    }
    static boolean verifyGender(String gender)
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
        String prefix = "MM" + firstName.charAt(0) + lastName.charAt(0);
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
            String username = prefix+"00"+nextNumber;
            return username;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
