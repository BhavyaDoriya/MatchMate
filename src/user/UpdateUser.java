package user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import ExceptionHandling.UpdateCancelledException;
import util.DatabaseConnector;

public class UpdateUser
{
    Scanner sc= new Scanner(System.in);
    public void updateName ()throws UpdateCancelledException, SQLException
    {
        Connection con= DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {

            System.out.println("Do you want to update First Name or Last Name ");
            String choice= sc.nextLine().trim();
            if(choice.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by user");
            }
            else if(choice.equalsIgnoreCase("First Name"))
            {
                while (true)
                {
                    String firstName= sc.nextLine().trim();
                    if(firstName.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by user");
                    }
                    else if (firstName.isEmpty())
                    {
                        System.out.println("First name must have some value!");
                    }
                    else
                    {
                        String q= "update users set first_name=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,firstName);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        System.out.println("First name updated successfully");
                        Session.getCurrentUserObject().setFirst_name(firstName);
                        return;
                    }
                }

            }
            else if(choice.equalsIgnoreCase("Last Name"))
            {
                while (true)
                {
                    String lastName= sc.nextLine().trim();
                    if(lastName.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by user");
                    } else if (lastName.isEmpty()) {
                        System.out.println("Last Name must have some value!");
                    } else
                    {
                        String q= "update users set last_name=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,lastName);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        System.out.println("Last name updated successfully");
                        Session.getCurrentUserObject().setLast_name(lastName);
                        return;
                    }
                }

            }
            else
            {
                System.out.println("Invalid input,Try again!");
            }
        }
    }
    public void updatePassword() throws UpdateCancelledException,SQLException
    {
        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Enter your current password:");
            String currentPassword= sc.nextLine().trim();
            if(currentPassword.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else if(currentPassword.equals(Session.getCurrentUserObject().getPassword()))
            {
                while (true)
                {
                    System.out.println("Enter your new password: ");
                    String newPassword=sc.nextLine().trim();
                    if(newPassword.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    boolean isNewPassWordValid=UserManager.verifyPassword(newPassword);
                    if(isNewPassWordValid)
                    {
                        String q= "update users set password=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,newPassword);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        Session.getCurrentUserObject().setPassword(newPassword);
                        System.out.println("Password updated successfully!");
                        return;
                    }
                }

            }
            else
            {
                System.out.println("Incorrect current password,Try again");
            }
        }


    }
    void updateContact() throws UpdateCancelledException,SQLException
    {

        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Would you like to update E-mail or Mobile Number ?");
            String choice= sc.nextLine().trim();
            if(choice.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else if(choice.equalsIgnoreCase("E-mail")||choice.equalsIgnoreCase("Email"))
            {
                while (true)
                {
                    System.out.println("Enter your new email address: ");
                    String email=sc.nextLine().trim();
                    if(email.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    boolean isEmailValid=UserManager.verifyEmail(email);
                    if(isEmailValid)
                    {
                        String q= "update users set email=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,email);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        Session.getCurrentUserObject().setEmail(email);
                        System.out.println("E-Mail Updated successfully!");
                        return;
                    }
                    else
                    {
                        System.out.println("Try again!");
                    }
                }
            }
            else if(choice.equalsIgnoreCase("Mobile Number"))
            {
                while (true)
                {
                    System.out.println("Enter your new Mobile Number : ");
                    String newMobile_Number=sc.nextLine().trim();
                    if(newMobile_Number.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    boolean isMobileNumberValid=UserManager.verifyMobileNumber(newMobile_Number);
                    if(isMobileNumberValid)
                    {
                        String q= "update users set mobile_number=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,newMobile_Number);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        Session.getCurrentUserObject().setMobile_number(newMobile_Number);
                        System.out.println("Mobile Number Updated successfully!");
                        return;
                    }
                    else {
                        System.out.println("Invalid Mobile Number");
                        System.out.println("Note : Mobile Number should start with any of the following 6,7,8,9 digits,it should only contain 10 digits,No alphabets,No special character!");
                        System.out.println("Try again!");
                    }
                }
            }
            else
            {
                System.out.println("Invalid choice, Try again!");
            }
        }

    }
    void updateAddress() throws UpdateCancelledException,SQLException
    {
        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Would you like to update City or State & City?");
            String choice= sc.nextLine().trim();
            if(choice.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else if(choice.equalsIgnoreCase("City"))
            {
                while (true)
                {
                    System.out.println("Enter your new city: ");
                    String newCity=sc.nextLine().trim();
                    if(newCity.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    if(!newCity.isEmpty())
                    {
                        String q= "update users set city=? where username= ?";
                        PreparedStatement pst= con.prepareStatement(q);
                        pst.setString(1,newCity);
                        pst.setString(2,username);
                        pst.executeUpdate();
                        Session.getCurrentUserObject().setCity(newCity);
                        System.out.println("City Updated successfully!");
                        return;
                    }
                    else
                    {
                        System.out.println("City must have some value!");
                        System.out.println("Try again!");
                    }
                }
            }
            else if(choice.equalsIgnoreCase("State & City")||choice.equalsIgnoreCase("State and City"))
            {
                String newCity;
                while (true)
                {
                    System.out.println("Enter your new city : ");
                    newCity=sc.nextLine().trim();
                    if(newCity.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    else if(!newCity.isEmpty())
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("City must have some value");
                    }
                }
                String newState;
                while (true)
                {
                    System.out.println("Enter your new State : ");
                    newState=sc.nextLine().trim();
                    if(newState.equalsIgnoreCase("B"))
                    {
                        throw new UpdateCancelledException("Updation cancelled by User");
                    }
                    else if(!newState.isEmpty())
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("State must have some value");
                    }
                }

                String q= "update users set city=?,state=? where username= ?";
                PreparedStatement pst= con.prepareStatement(q);
                pst.setString(1,newCity);
                pst.setString(2,newState);
                pst.setString(3,username);
                pst.executeUpdate();
                Session.getCurrentUserObject().setCity(newCity);
                Session.getCurrentUserObject().setState(newState);
                System.out.println("Address Updated successfully!");
                return;
            }
            else
            {
                System.out.println("Invalid choice, Try again!");
            }
        }
    }
    void updateAge()
    {
        User currentObj=Session.getCurrentUserObject();
        currentObj.setAge(User.getUserAge(currentObj.getBirth_date()));
    }
    void updateGenderPreference() throws UpdateCancelledException,SQLException
    {

        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Enter gender preference(male/female/other):");
            String gender_pref= sc.nextLine().trim();
            boolean isGenderValid=UserManager.verifyGender(gender_pref);
            if(gender_pref.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else if(isGenderValid)
            {
                String q= "update users set gender_preference=? where username= ?";
                PreparedStatement pst= con.prepareStatement(q);
                pst.setString(1,gender_pref);
                pst.setString(2,username);
                pst.executeUpdate();
                Session.getCurrentUserObject().setGender_preference(gender_pref);
                return;
            }
        }
    }
    void updateGender() throws UpdateCancelledException,SQLException
    {

        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Enter gender (male/female/other):");
            String gender= sc.nextLine().trim();
            boolean isGenderValid=UserManager.verifyGender(gender);
            if(gender.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else if(isGenderValid)
            {
                String q= "update users set gender=? where username= ?";
                PreparedStatement pst= con.prepareStatement(q);
                pst.setString(1,gender);
                pst.setString(2,username);
                pst.executeUpdate();
                Session.getCurrentUserObject().setGender(gender);
                return;
            }
        }
    }
    void updateProfilePicture() throws UpdateCancelledException,SQLException
    {
        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while(true)
        {
            System.out.println("Enter your new image path: ");
            String newPath=sc.nextLine().trim();
            if(newPath.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User");
            }
            else
            {
                try
                {
                    InputStream fis=new FileInputStream(newPath);
                    String query="Update users set profile_picture=? where username=?";
                    PreparedStatement pst=con.prepareStatement(query);
                    pst.setBlob(1,fis);
                    pst.setString(2,username);
                    Session.getCurrentUserObject().setImage_stream(fis);
                    System.out.println("Profile Picture updated successfully!");
                    return;
                } catch (FileNotFoundException e) {
                    System.out.println("File not Found!");
                    System.out.println("Please enter valid image path!");
                }
            }
        }
    }
    void updateQualification() throws UpdateCancelledException,SQLException
    {
        Connection con=DatabaseConnector.getConnection();
        String username=Session.getCurrentUsername();
        while (true)
        {
            System.out.println("Enter your qualification :");
            String qualification=sc.nextLine().trim();
            if(qualification.equalsIgnoreCase("B"))
            {
                throw new UpdateCancelledException("Updation cancelled by User!");
            }
            if(!qualification.isEmpty())
            {
                String query="Update users set qualification=? where username=?";
                PreparedStatement pst=con.prepareStatement(query);
                pst.setString(1,qualification);
                pst.setString(2,username);
                Session.getCurrentUserObject().setQualification(qualification);
                System.out.println("Qualification updated successfully!");
                return;
            }
            else
            {
                System.out.println("Qualification must have some value !");
                System.out.println("Try again!");
            }
        }
    }

}
