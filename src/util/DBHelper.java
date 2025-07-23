package util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import user.*;

public class DBHelper
{
    Scanner sc= new Scanner(System.in);
    void updateName()
    {
        while(true)
        {
            try
            {
                Connection con=DatabaseConnector.getConnection();
                String username=Session.getCurrentUsername();
                System.out.println("Do you want to update First Name or Last Name ");
                String choice=sc.nextLine().trim();
                if(choice.equalsIgnoreCase("First Name"))
                {
                    String firstName= sc.nextLine().trim();
                    if(firstName.equalsIgnoreCase("B"))
                    {
                        System.out.println("Exited Update Name Process");
                        break;
                    }
                    String q= "update users set first_name=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,firstName);
                    pst.setString(2,username);
                    pst.executeUpdate();
                }
                else if(choice.equalsIgnoreCase("Last Name"))
                {
                    String lastName= sc.nextLine().trim();
                    if(lastName.equalsIgnoreCase("B"))
                    {
                        System.out.println("Exited Update Name Process");
                        break;
                    }
                    String q= "update users set first_name=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,lastName);
                    pst.setString(2,username);
                    pst.executeUpdate();
                    Session.getCurrentUserObject().setLast_name(lastName);
                }
                else if(choice.equalsIgnoreCase("B"))
                {
                    System.out.println("Exited Update Name Process");
                    break;
                }
                else
                {
                    System.out.println("Please Enter valid input!");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    void updatePassword()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter password:");
                String password= sc.nextLine();
                boolean flag=UserManager.verifyPassword(password);
                if(flag)
                {
                    String q= "update users set password=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,password);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void updateContact()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter contact:");
                String contact= sc.nextLine();
                boolean flag=UserManager.verifyMobileNumber(contact);
                if(flag)
                {
                    String q= "update users set phone=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,contact);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void updateAddress()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter address:");
                String address= sc.nextLine();
                if(!address.isEmpty())
                {
                    String q= "update users set address=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,address);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void updateAge()
    {
//        try
//        {
//            Connection con=DatabaseConnector.getConnection();
//            String un=Session.getCurrentUsername();
//            while(true)
//            {
//                System.out.println("Enter Age:");
//                int address= sc.nextInt();
//
//                boolean flag=UserManager.verify;
//                if(flag)
//                {
//                    String q= "update users set phone=? where username= ?";
//                    PreparedStatement pst= con.prepareStatement(q);
//                    pst.setString(1,contact);
//                    pst.setString(2,un);
//                    pst.executeUpdate();
//                    break;
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }

    }
    void updateGenderPreference()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter gender preference:");
                String gender= sc.nextLine();
                boolean flag=UserManager.verifyGender(gender);
                if(flag)
                {
                    String q= "update users set gender_preference=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,gender);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void updateGender()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter gender preference:");
                String gender= sc.nextLine();
                boolean flag=UserManager.verifyGender(gender);
                if(flag)
                {
                    String q= "update users set gender=? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,gender);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void updatePicture()
    {

    }
    void updateQualification()
    {
        try
        {
            Connection con=DatabaseConnector.getConnection();
            String un=Session.getCurrentUsername();
            while(true)
            {
                System.out.println("Enter gender preference:");
                String qualification= sc.nextLine();
                if(!qualification.isEmpty())
                {
                    String q= "update users set qualification =? where username= ?";
                    PreparedStatement pst= con.prepareStatement(q);
                    pst.setString(1,qualification);
                    pst.setString(2,un);
                    pst.executeUpdate();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}