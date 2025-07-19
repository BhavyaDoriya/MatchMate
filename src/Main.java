import java.sql.Connection;

import user.User;
import user.UserManager;
import util.*;

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
//        int age=User.getUserAge("2006-07-29");
//        System.out.println(age);

        UserManager.Register();
    }
}
//C:\Users\BHAVYA\Downloads\Anupammittal.jpg