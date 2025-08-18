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
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        authAndExitHandler();
    }
    static void authAndExitHandler()
    {
        while (true)
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
                        break;
                    case 2:
                        boolean loginSuccessful= false;
                        try {
                            loginSuccessful = new UserManager().Login();
                        }catch (LoginCancelledException e)
                        {
                            System.out.println(e.getMessage());
                            System.out.println("Returning to Login/Register page!");
                        }
                        catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            System.out.println("Returning to Login/Register page!");
                        }
                        if(loginSuccessful)
                        {
                            HomePage();
                        }
                        break;
                    case 3:
                        System.out.println("Exiting MatchMate!");
                        return;
                    default:
                        System.out.println("Invalid choice try again!");
                        break;
                }
            }catch (InputMismatchException e)
            {
                System.out.println("Please Enter Integer Value only!");
                sc.nextLine();
            }
        }

    }
    static void HomePage() {
        while (true) {
            System.out.println();
            System.out.println("1. Edit Profile");
            System.out.println("2. Find Matches");
            System.out.println("3. View Shortlisted Profiles");
            System.out.println("4. View Mutual Likes (Liked You Back)");
            System.out.println("5. View People Who Liked You");
            System.out.println("6. Delete account");
            System.out.println("7. Log out");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        UpdateUser.editProfile();
                        break;
                    case 2:
                        try {
                            new MatchDisplay().displayMatches();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        break;
                    case 3:
                        try {
                            new MatchDisplay().displayShortlisted();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        break;
                    case 4:
                        try {
                            new MatchDisplay().displayMutualLikes();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        break;
                    case 5:
                        try {
                            new MatchDisplay().displayWhoLikedMe();
                        } catch (GoBackException e) {
                            new MatchDisplay().displayMatches();
                        } catch (RuntimeException e) {

                        }
                        break;
                    case 6:
                        sc.nextLine();
                        try {
                            String choiceYesNo = InputUtils.promptUntilValid("Are you sure that you want to delete your account?(Yes/B(for No)) ",
                                    s -> s.equalsIgnoreCase("yes"),
                                    () -> new DeletionCancelledException("Deletion Cancelled by User"));
                            new UserManager().deleteAccount();
                            System.out.println("Account deleted successfully!");
                            System.out.println("User logged out of account!");
                            System.out.println("Back to Login/Register page : ");
                            return;
                        } catch (DeletionCancelledException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Returning to Home Page!");
                            return;
                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            e.printStackTrace();
                            System.out.println("Returning to Home Page!");
                            return;
                        }
                    case 7:
                        Session.setCurrentUsername(null);
                        Session.setCurrentUserObject(null);
                        System.out.println("Logged out successfully");
                        System.out.println("Back to Login/Register page !");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Integer Value only!");
                sc.nextLine();
                }
            }
        }
    }





//C:\Users\BHAVYA\Downloads\Anupammittal.jpg