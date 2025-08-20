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

            System.out.println("\ud83d\udc98 Welcome to MatchMate \ud83d\udc98");
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
            System.out.println("4. View Mutual Likes");
            System.out.println("5. View People Who Liked You");
            System.out.println("6. Download profile document");
            System.out.println("7. Delete account");
            System.out.println("8. Log out");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        UpdateUser.editProfile();
                        break;
                    case 2:
                        do {
                            try {
                                new MatchDisplay().displayMatches();
                            } catch (GoBackException e) {

                            } catch (RuntimeException e) {
                                break;
                            }

                        }while(true);
                        break;
                    case 3:
                        do {
                            try {
                                new MatchDisplay().displayShortlisted();
                            } catch (GoBackException e) {

                            } catch (RuntimeException e) {
                                break;
                            }
                        }while (true);
                        break;
                    case 4:
                        do{
                        try {
                            new MatchDisplay().displayMutualLikes();
                        } catch (GoBackException e) {

                        } catch (RuntimeException e) {
                            break;
                        }
                        }
                        while (true);
                        break;
                    case 5:
                        do{
                        try {
                            new MatchDisplay().displayWhoLikedMe();
                        } catch (GoBackException e) {

                        } catch (RuntimeException e) {
                            break;
                        }
                        }
                        while (true);
                        break;

                    case 6:
                        //Download profile document
                        new UserManager().generateUserProfile(Session.getCurrentUserObject(),true);
                        break;
                    case 7:
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

                        } catch (SQLException e) {
                            System.out.println("Connection lost!");
                            System.out.println("Try again!");
                            e.printStackTrace();
                            System.out.println("Returning to Home Page!");
                            return;
                        }
                        break;
                    case 8:
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

//he OTP sent to your email/Press [B] to re-enter you email: com.sun.mail.smtp.SMTPSendFailedException: [EOF]
//	at com.sun.mail.smtp.SMTPTransport.issueSendCommand(SMTPTransport.java:2374)
//	at com.sun.mail.smtp.SMTPTransport.mailFrom(SMTPTransport.java:1808)
//	at com.sun.mail.smtp.SMTPTransport.sendMessage(SMTPTransport.java:1285)
//	at jakarta.mail.Transport.send0(Transport.java:231)
//	at jakarta.mail.Transport.send(Transport.java:100)
//	at util.EmailUtil.sendMail(EmailUtil.java:41)
//	at util.EmailUtil.forgotUsername(EmailUtil.java:66)
//	at user.UserManager.Login(UserManager.java:279)
//	at Main.authAndExitHandler(Main.java:50)
//	at Main.main(Main.java:18)