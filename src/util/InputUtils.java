package util;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class InputUtils {

    private static final Scanner sc = new Scanner(System.in);

    public static String promptUntilValid(String prompt, Function<String, Boolean> validator) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine();
            if (validator.apply(input)) {
                return input;
            }
        }
    }

    public static int promptInt(String prompt, Predicate<Integer> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(sc.nextLine());
                if (validator.test(input)) {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }
    }

    public static String promptOptional(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = sc.nextLine();
        return input.equalsIgnoreCase("S") ? defaultValue : input;
    }
}

