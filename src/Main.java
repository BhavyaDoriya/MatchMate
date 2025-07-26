

import ExceptionHandling.LoginCancelledException;
import ExceptionHandling.RegistrationCancelledException;
import match.MatchDisplay;
import match.MatchEngine;
import user.Session;
import user.UpdateUser;
import user.UserManager;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
//        Connection con=DatabaseConnector.getConnection();
//        if(con!=null)
//        {
//            System.out.println("Connected");
//        }

        authAndExitHandler();
    }
    static void authAndExitHandler()
    {
        System.out.println("💘 Welcome to MatchMate 💘");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Enter choice:");
        int choice= sc.nextInt();
        switch(choice)
        {
            case 1:
                try {
                    new UserManager().Register();
                }
                catch (RegistrationCancelledException e)
                {
                    System.out.println(e.getMessage());
                }
                catch(SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                authAndExitHandler();
                break;
            case 2:
                boolean loginSuccessful= false;
                try {
                    loginSuccessful = new UserManager().Login();
                }catch (LoginCancelledException e)
                {
                    System.out.println(e.getMessage());
                    authAndExitHandler();
                }
                catch (SQLException e) {
                    System.out.println("Connection lost!");
                    System.out.println("Try again!");
                }
                if(loginSuccessful)
                {
                    HomePage();
                }
                break;
            case 3:
                break;
        }


    }
    static void HomePage()
    {

        boolean condition= true;
        while(condition)
        {
            System.out.println();
            System.out.println("1. Edit Profile");
            System.out.println("2. Find Matches");
            System.out.println("3. View Shortlisted Profiles");
            System.out.println("4. View Mutual Likes (Liked You Back)");
            System.out.println("5. View People Who Liked You");
            System.out.println("6. Delete account");
            System.out.println("7. Log out");

            int choice= sc.nextInt();
            switch(choice)
            {
                case 1:
                    UpdateUser.editProfile();
                    HomePage();
                    break;
                case 2:
                    new MatchDisplay().displayMatches();
                    HomePage();
                    break;
                case 3:
                    new MatchDisplay().displayShortlisted();
                    HomePage();
                    break;
                case 4:
                    new MatchDisplay().displayMutualLikes();
                    HomePage();
                    break;
                case 5:
                    new MatchDisplay().displayWhoLikedMe();
                    HomePage();
                    break;
                case 6:
                    sc.nextLine();
                    System.out.println("Are you sure that you want to delete your account?(Yes/No) ");
                    String choiceYesNo= sc.nextLine();
                    if(choiceYesNo.equalsIgnoreCase("yes"))
                    {
                        try {
                            new UserManager().deleteAccount();
                            System.out.println("Account deleted successfully!");
                            System.out.println("User logged out of account!");
                            System.out.println("Back to Login/Register page : ");
                            authAndExitHandler();
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Deletion failed!");
                            HomePage();
                        }
                    }
                    else if(choiceYesNo.equalsIgnoreCase("No"))
                    {
                        System.out.println("Deletion cancelled by user!");
                        HomePage();
                    }
                    break;
                case 7:
                    condition= false;
                    Session.setCurrentUsername(null);
                    Session.getUserObject(null);
                    authAndExitHandler();
                    break;

            }
        }
    }




}
//C:\Users\BHAVYA\Downloads\Anupammittal.jpg