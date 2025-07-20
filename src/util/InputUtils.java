package util;

import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import ExceptionHandling.*;

public class InputUtils{
    public static String promptUntilValid(String prompt, Predicate<String> validator) throws RegistrationCancelledException{
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("B")) {
                throw new RegistrationCancelledException("Registration cancelled by user.");
            }
            if (validator.test(input)) return input;
            System.out.println("Invalid input. Try again.");
        }
    }

    public static String promptOptional(String prompt, String defaultValue) throws  RegistrationCancelledException{
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("B")) {
            throw new RegistrationCancelledException("Registration cancelled by user.");
        }
        return (input.equalsIgnoreCase("s") || input.isEmpty()) ? defaultValue : input;
    }

    public static int promptInt(String prompt, IntPredicate validator) throws RegistrationCancelledException{
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("B")) {
                throw new RegistrationCancelledException("Registration cancelled by user.");
            }
            try {
                int value = Integer.parseInt(input);
                if (validator.test(value)) return value;
            } catch (NumberFormatException e) {}
            System.out.println("Invalid number. Try again.");
        }
    }
}

