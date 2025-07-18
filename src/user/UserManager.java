package user;

import util.DatabaseConnector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class UserManager {
//    private String first_name;      -->
//    private String last_name;       -->
//    private String birth_date;      -->
//    private String gender_preference;-->
//    private Blob profile_picture=null;  -->
//    private String bio;          -->
//    private String mobile_number; -->
//    private String email;         -->
//    private String user_name;
//    private String password;
//    private String gender;       -->
//    private String dietary_choice;-->
//    private int age;
//    private String height;
    //private String city            -->
    //Private String state          -->
    //private String qualification  -->
static Scanner sc=new Scanner(System.in);
    static public void Register() {
        System.out.println("Enter first name: ");
        String first_name = sc.nextLine();

        System.out.println("Enter last name: ");
        String last_name = sc.nextLine();
        String birth_date;
        while (true)
        {
            System.out.println("Enter Birth date(in (YYYY-MM-DD) format): ");
            birth_date= sc.nextLine();
            boolean check=verifyBirthDate(birth_date);
            if(check)
            {
                break;
            }
        }
        String gender;
        while (true)
        {
            System.out.println("Enter your gender: ");
            gender=sc.nextLine();
            boolean check=verifyGender(gender);
            if(check)
            {
                break;
            }
        }
        String gender_preferences;
        while (true)
        {
            System.out.println("Enter your gender preferences: ");
            gender_preferences=sc.nextLine();
            boolean check=verifyGender(gender_preferences);
            if(check)
            {
                break;
            }
        }
        int height;
        while(true)
        {
            System.out.println("Enter your height(in cm): ");
            height= sc.nextInt();
            if(verifyHeight(height))
            {
                break;
            }
        }

        String mo;
        while (true)
        {
            System.out.println("Enter your mobile number: ");
            mo=sc.nextLine();
            boolean check=verifyMobileNumber(mo);
            if(check)
            {
                break;
            }
        }
        String email;
       while (true)
        {
            System.out.println("Enter your email: ");
            email=sc.nextLine();
            boolean check=checkEmailExists(email);
            if(!check)
            {
                break;
            }
            else
            {
                System.out.println("Email already Exists");
            }
        }

            System.out.println("Enter your city: ");
            String city= sc.nextLine();
            System.out.println("Enter your state : ");
            String state= sc.nextLine();
            System.out.println("Enter your qualification: ");
            String qualification=sc.nextLine();

            String dietary_preferences;
       while (true)
       {
           System.out.println("Enter your Dietary preference/[S]kip : ");
           dietary_preferences=sc.nextLine();
           if(dietary_preferences.equalsIgnoreCase("s"))
           {
               dietary_preferences="Not Mentioned";
               break;
           } else if (verifyDietaryPreferences(dietary_preferences)) {
               break;
           }
       }
           System.out.println("Enter Bio/[S]kip : ");
           String bio=sc.nextLine();
           if(bio.equalsIgnoreCase("s"))
           {
               bio="Not mentioned";
           }
            System.out.println("Would you like to upload your profile picture(Yes/[S]kip): ");
           String choice=sc.nextLine();
          InputStream fis;
           if(choice.equalsIgnoreCase("YES"))
           {
               while(true)
               {
                   try {
                       System.out.println("Enter image path: ");
                       String image_path=sc.nextLine();
                        fis=new FileInputStream(image_path);
                       break;

                   } catch (FileNotFoundException e) {
                       System.out.println("Please enter valid image path");
                   }
               }

           }
           else
           {
               fis=null;
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
    static boolean verifyGender(String gender)
    {
        return true;
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
    static boolean verifyDietaryPreferences(String dietaryPreference)
    {
        return true;
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

}
