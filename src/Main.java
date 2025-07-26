

import match.MatchDisplay;
import match.MatchEngine;
import user.Session;
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
                new UserManager().Register();
                authAndExitHandler();
                break;
            case 2:
                boolean loginSuccessful=new UserManager().Login();
                if(loginSuccessful)
                {
                    System.out.println(Session.getCurrentUserObject().getEmail());
                    HomePage();
                }
                else
                {
                    authAndExitHandler();
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
                    MatchEngine.editProfile();
                    break;
                case 2:
                    new MatchDisplay().displayMatches();
                    break;
                case 3:
                    new MatchDisplay().displayShortlisted();
                    break;
                case 4:
                    new MatchDisplay().displayMutualLikes();
                    break;
                case 5:
                    new MatchDisplay().displayWhoLikedMe();
                    break;
                case 6:
                    break;
                case 7:
                    condition= false;
                    Session.setCurrentUsername(null);
                    Session.getUserObject(null);
                    break;

            }
        }
    }
    static void ProfileEdit() {
        System.out.println("1. Update first Name-last Name");
        System.out.println("2. Update username");
        System.out.println("3. Update Password");
        System.out.println("4. Update contact details");
        System.out.println("5. Update address(city ,state)");
        System.out.println("6. Update age");
        System.out.println("7. Update gender preferences");
        System.out.println("8. Update Gender");
        System.out.println("9. Update profile picture");
        System.out.println("10. Update Education");
        System.out.println("11. Exit");
    }



}
//C:\Users\BHAVYA\Downloads\Anupammittal.jpg