package util;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

public class EmailOTP {

    public static boolean sendOTP(String recipientEmail, String otp) {
        final String senderEmail = "matchmatePlatformHelp@gmail.com"; // your Gmail
        final String senderPassword = "myswjlfzzdfmiora"; // your App password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        jakarta.mail.Session mailSession = jakarta.mail.Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            Transport.send(message);
            System.out.println("OTP sent successfully!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void forgotUsername() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your registered email: ");
        String email = sc.nextLine();

        try {
            PreparedStatement ps = DatabaseConnector.getConnection()
                    .prepareStatement("SELECT username, first_name FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String first = rs.getString("first_name");

                // Generate OTP
                String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
                String body = "Hi " + first + ",\n\nYour OTP to recover your username is: " + otp;
                EmailUtil.sendMail(email, "Forgot Username - MatchMate", body);

                // Ask for OTP
                System.out.print("Enter the OTP sent to your email: ");
                String enteredOtp = sc.nextLine();

                if (enteredOtp.equals(otp)) {
                    // Send username to email
                    String msg = "Hi " + first + ",\n\nYour MatchMate username is: " + username;
                    EmailUtil.sendMail(email, "Your MatchMate Username", msg);
                    System.out.println("Your username has been sent to your email!");
                } else {
                    System.out.println("Invalid OTP.");
                }
            } else {
                System.out.println(" No account found with that email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void forgotPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your registered email: ");
        String email = sc.nextLine();

        try {
            PreparedStatement ps = DatabaseConnector.getConnection()
                    .prepareStatement("SELECT first_name FROM users WHERE username = ? AND email = ?");
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String first = rs.getString("first_name");

                // Generate OTP
                String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
                String body = "Hi " + first + ",\n\nYour OTP to reset your password is: " + otp;
                EmailUtil.sendMail(email, "Forgot Password - MatchMate", body);

                // Ask for OTP
                System.out.print("Enter the OTP sent to your email: ");
                String enteredOtp = sc.nextLine();

                if (enteredOtp.equals(otp)) {
                    System.out.print("Enter your new password: ");
                    String newPass = sc.nextLine();

                    PreparedStatement ps2 = DatabaseConnector.getConnection()
                            .prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                    ps2.setString(1, newPass);
                    ps2.setString(2, username);
                    ps2.executeUpdate();

                    System.out.println("Password reset successful!");
                } else {
                    System.out.println("Invalid OTP.");
                }
            } else {
                System.out.println("No matching account found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
