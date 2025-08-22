import ExceptionHandling.DeletionCancelledException;
import ExceptionHandling.GoBackException;
import ExceptionHandling.LoginCancelledException;
import ExceptionHandling.RegistrationCancelledException;
import match.MatchDisplay;
import user.Session;
import user.UpdateUser;
import user.UserManager;
import util.ConsoleColors;
import util.InputUtils;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.out.println();
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "                                                                     💘 Welcome to MatchMate 💘" + ConsoleColors.RESET);

        authAndExitHandler();
    }

    static void authAndExitHandler() {
        while (true) {

            System.out.println();
            System.out.println(ConsoleColors.YELLOW+"NOTE: YOU CAN PRESS 'B' TO GO BACK TO PREVIOUS FEATURES/METHODS ANY TIME!"+ConsoleColors.RESET);
            System.out.println();
            System.out.println(ConsoleColors.CYAN_BOLD + "1. Register" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "2. Login" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "3. Exit" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD + "Enter choice: " + ConsoleColors.RESET);

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        try {
                            new UserManager().Register();
                        } catch (RegistrationCancelledException e) {
                            System.out.println();
                            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println();
                            System.out.println(ConsoleColors.RED + "Connection lost!" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW + "Try again!" + ConsoleColors.RESET);
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        boolean loginSuccessful = false;
                        try {
                            loginSuccessful = new UserManager().Login();
                        } catch (LoginCancelledException e) {
                            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW + "Returning to Login/Register page!" + ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED + "Connection lost!" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW + "Try again!" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW + "Returning to Login/Register page!" + ConsoleColors.RESET);
                        }
                        if (loginSuccessful) {
                            HomePage();
                        }
                        break;

                    case 3:
                        System.out.println(ConsoleColors.GREEN_BOLD + "Exiting MatchMate!" + ConsoleColors.RESET);
                        return;

                    default:
                        System.out.println(ConsoleColors.RED + "Invalid choice, try again!" + ConsoleColors.RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED + "Please Enter Integer Value only!" + ConsoleColors.RESET);
                sc.nextLine(); // clear buffer
            }
        }
    }

    static void HomePage() {
        while (true) {
            System.out.println();
            System.out.println(ConsoleColors.CYAN_BOLD + "1. Edit Profile" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "2. Find Matches" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "3. View Shortlisted Profiles" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "4. View Mutual Likes" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "5. View People Who Liked You" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "6. Download Profile Document" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "7. Delete Account" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD + "8. Log out" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD + "Enter choice: " + ConsoleColors.RESET);

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
                                // go back safely
                            } catch (RuntimeException e) {
                                break;
                            }
                        } while (true);
                        break;

                    case 3:
                        do {
                            try {
                                new MatchDisplay().displayShortlisted();
                            } catch (GoBackException e) {
                            } catch (RuntimeException e) {
                                break;
                            }
                        } while (true);
                        break;

                    case 4:
                        do {
                            try {
                                new MatchDisplay().displayMutualLikes();
                            } catch (GoBackException e) {
                            } catch (RuntimeException e) {
                                break;
                            }
                        } while (true);
                        break;

                    case 5:
                        do {
                            try {
                                new MatchDisplay().displayWhoLikedMe();
                            } catch (GoBackException e) {
                            } catch (RuntimeException e) {
                                break;
                            }
                        } while (true);
                        break;

                    case 6:
                        new UserManager().generateUserProfile(Session.getCurrentUserObject(), true);
                        break;

                    case 7:
                        sc.nextLine(); // clear buffer
                        try {
                            String choiceYesNo = InputUtils.promptUntilValid(
                                    ConsoleColors.RED+"Are you sure you want to delete your account? (Yes / B for No): "+ConsoleColors.RESET,
                                    s -> s.equalsIgnoreCase("yes"),
                                    () -> new DeletionCancelledException("Deletion Cancelled by User")
                            );
                            new UserManager().deleteAccount();
                            System.out.println(ConsoleColors.GREEN_BOLD + "Account deleted successfully!" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.GREEN + "User logged out. Back to Login/Register page." + ConsoleColors.RESET);
                            return;
                        } catch (DeletionCancelledException e) {
                            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
                        } catch (SQLException e) {
                            System.out.println(ConsoleColors.RED_BOLD + "Connection lost!" + ConsoleColors.RESET);
                            e.printStackTrace();
                            System.out.println(ConsoleColors.GREEN + "Returning to Home Page!" + ConsoleColors.RESET);
                            return;
                        }
                        break;

                    case 8:
                        Session.setCurrentUsername(null);
                        Session.setCurrentUserObject(null);
                        System.out.println(ConsoleColors.GREEN_BOLD + "Logged out successfully." + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.GREEN_BOLD + "Back to Login/Register page!" + ConsoleColors.RESET);
                        return;

                    default:
                        System.out.println(ConsoleColors.RED_BOLD + "Invalid choice! Try again!" + ConsoleColors.RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED_BOLD + "Please Enter Integer Value only!" + ConsoleColors.RESET);
                sc.nextLine();
            }
        }
    }
}
