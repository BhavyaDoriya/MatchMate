package util;

import ExceptionHandling.GoBackException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import user.UserManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class EmailUtil {
    private static final String senderEmail = "matchmatePlatformHelp@gmail.com";
    private static final String senderPassword = "myswjlfzzdfmiora";
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public static boolean sendMail(String recipient, String subject, String body) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to " + recipient);
            return true;
        } catch (MessagingException e) {
            System.out.println("Failed to send OTP to email. Try again later.");

            System.out.println("Please enter valid email!");
            return false;
        }
    }
    public void forgotUsername() throws GoBackException {
        while (true) {
            String email = InputUtils.promptUntilValid("Enter your registered email: ",
                    s->s.contains("@"), () -> new GoBackException("User chose to go back from forgot username!"));

            try {
                PreparedStatement ps = DatabaseConnector.getConnection()
                        .prepareStatement("SELECT username, first_name FROM users WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String username = rs.getString("username");
                    String first = rs.getString("first_name");

                    String otp = OTPGenerator.generateOTP(6);
                    String body = "Hi " + first + ",\n\nYour OTP to recover your username is: " + otp;
                    System.out.println("Sending OTP to your email...");
                    EmailUtil.sendMail(email, "Forgot Username - MatchMate", body);
                    System.out.println("Sent!");
                    try {
                        String enteredOtp = InputUtils.promptUntilValid(
                                "Enter the OTP sent to your email/Press [B] to re-enter you email: ",
                                s -> s.equals(otp),
                                () -> new GoBackException("User chose to re enter his email!")
                        );
                    }catch (GoBackException e)
                    {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    System.out.println("Sending username to your email...");
                    String msg = "Hi " + first + ",\n\nYour MatchMate username is: " + username;
                    EmailUtil.sendMail(email, "Your MatchMate Username", msg);
                    System.out.println("Your username has been sent to your email!");
                    return;

                } else {
                    System.out.println(" No account found with that email.");
                    System.out.println("Try again! or Enter B to go back!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void forgotPassword() throws  GoBackException{
        while (true) {
            String username = InputUtils.promptUntilValid("Enter your username: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back from forget password option"));
            String email="";
            do {
                try {
                    email = InputUtils.promptUntilValid("Enter your registered email/Press [B] to re-enter username: ",
                            s -> !s.isEmpty(), () -> new GoBackException("User chose to re-enter username!"));
                } catch (GoBackException e) {
                    System.out.println(e.getMessage());
                    break;
                }

                try {
                    PreparedStatement ps = DatabaseConnector.getConnection()
                            .prepareStatement("SELECT first_name FROM users WHERE username = ? AND email = ?");
                    ps.setString(1, username);
                    ps.setString(2, email);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        System.out.println("Sending OTP to your email...");
                        String first = rs.getString("first_name");
                        String otp = OTPGenerator.generateOTP(6);
                        String body = "Hi " + first + ",\n\nYour OTP to reset your password is: " + otp;
                        EmailUtil.sendMail(email, "Forgot Password - MatchMate", body);
                        System.out.println("Sent!");
                        try {
                            String enteredOtp = InputUtils.promptUntilValid("EnterOTP sent to  the your email/Press [B] to re-enter registered email: ",
                                    s -> s.equalsIgnoreCase(otp),
                                    () -> new GoBackException("User chose to re-enter Email!"));
                        } catch (GoBackException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }

                        String newPass = InputUtils.promptUntilValid("Enter your new password: ", UserManager::verifyPassword, () -> new GoBackException("User chose to go back from forgot password option!"));

                        PreparedStatement ps2 = DatabaseConnector.getConnection()
                                .prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                        ps2.setString(1, newPass);
                        ps2.setString(2, username);
                        ps2.executeUpdate();

                        System.out.println("Password reset successful!");
                        return;
                    } else {
                        System.out.println("No matching account found.");
                        System.out.println("Try again! or Enter B to go back!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
}
