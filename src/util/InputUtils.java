package util;

import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Prompts until valid, or if user enters “B”, throws whatever
 * exception your supplier provides.
 */
public class InputUtils {

    private static final Scanner sc = new Scanner(System.in);

    public static <E extends Exception> String promptUntilValid(
            String prompt,
            Predicate<String> validator,
            Supplier<E> cancelException
    ) throws E {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("B")) {
                throw cancelException.get();
            }
            if (validator.test(input)) {
                return input;
            }
            System.out.println("Invalid input. Try again or 'B' to go back.");
        }
    }

    public static <E extends Exception> String promptOptional(
            String prompt,
            String defaultValue,
            Supplier<E> cancelException
    ) throws E {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("B")) {
            throw cancelException.get();
        }
        return (input.equalsIgnoreCase("S") || input.isEmpty())
                ? defaultValue
                : input;
    }

    public static <E extends Exception> int promptInt(
            String prompt,
            IntPredicate validator,
            Supplier<E> cancelException
    ) throws E {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("B")) {
                throw cancelException.get();
            }
            try {
                int v = Integer.parseInt(line);
                if (validator.test(v)) {
                    return v;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid number. Try again or 'B' to go back.");
        }
    }
}
