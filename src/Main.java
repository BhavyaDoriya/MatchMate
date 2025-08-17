

import ExceptionHandling.DeletionCancelledException;
import ExceptionHandling.GoBackException;
import ExceptionHandling.LoginCancelledException;
import ExceptionHandling.RegistrationCancelledException;
import match.MatchDisplay;
import user.Session;
import user.UpdateUser;
import user.UserManager;
import util.InputUtils;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        authAndExitHandler();
    }
    static void authAndExitHandler()
    {
        System.out.println("💘 Welcome to MatchMate 💘");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Enter choice:");
        try {
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
                        e.printStackTrace();
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
                        System.out.println("Returning to Login/Register page!");
                        authAndExitHandler();
                    }
                    if(loginSuccessful)
                    {
                        HomePage();
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice try again!");
                    authAndExitHandler();
                    break;
            }
        }catch (NumberFormatException e)
        {
            System.out.println("Please Enter Integer Value only!");
            authAndExitHandler();
        }



    }
    static void HomePage()
    {

            System.out.println();
            System.out.println("1. Edit Profile");
            System.out.println("2. Find Matches");
            System.out.println("3. View Shortlisted Profiles");
            System.out.println("4. View Mutual Likes (Liked You Back)");
            System.out.println("5. View People Who Liked You");
            System.out.println("6. Delete account");
            System.out.println("7. Log out");
            try {
                int choice= sc.nextInt();
                switch(choice)
                {
                    case 1:
                        UpdateUser.editProfile();
                        HomePage();
                        break;
                    case 2:
                        try {
                            new MatchDisplay().displayMatches();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        HomePage();
                        break;
                    case 3:
                        try {
                            new MatchDisplay().displayShortlisted();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        HomePage();
                        break;
                    case 4:
                        try {
                            new MatchDisplay().displayMutualLikes();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }                    HomePage();
                        break;
                    case 5:
                        try {
                            new MatchDisplay().displayWhoLikedMe();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }                    HomePage();
                        break;
                    case 6:
                        sc.nextLine();
                        try
                        {
                            String choiceYesNo= InputUtils.promptUntilValid("Are you sure that you want to delete your account?(Yes/B(for No)) ",
                                    s->s.equalsIgnoreCase("yes"),
                                    ()->new DeletionCancelledException("Deletion Cancelled by User"));
                            new UserManager().deleteAccount();
                            System.out.println("Account deleted successfully!");
                            System.out.println("User logged out of account!");
                            System.out.println("Back to Login/Register page : ");
                            authAndExitHandler();
                        }
                        catch (DeletionCancelledException e)
                        {
                            System.out.println(e.getMessage());
                            System.out.println("Returning to Home Page!");
                            HomePage();
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            e.printStackTrace();
                            System.out.println("Returning to Home Page!");
                            HomePage();
                        }
                        break;
                    case 7:
                        Session.setCurrentUsername(null);
                        Session.setCurrentUserObject(null);
                        System.out.println("Logged out successfully");
                        System.out.println("Back to Login/Register page !");
                        authAndExitHandler();
                        break;
                    default:
                        System.out.println("Invalid choice! Try again!");
                        HomePage();
                        break;
                }
            }catch (NumberFormatException e)
            {
                System.out.println("Please Enter Integer Value only!");
                HomePage();
            }
            }

    }





//C:\Users\BHAVYA\Downloads\Anupammittal.jpg