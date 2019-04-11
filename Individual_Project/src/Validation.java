import java.util.Scanner;

public class Validation {


    public static String stringValidation(Scanner scanner) {                   //STRING VALIDATION
        String string = null;
        boolean test = true;
        while (test) {
            if (scanner.hasNextInt()) {
                System.out.println("Invalid entry, try again");
                scanner.nextLine();
            } else {
                string = scanner.nextLine();
                test = false;
            }
        }return string;
    }

    public static int intValidation(Scanner scanner) {             //ΙΝΤ VALIDATION
        int anyInt = 0;
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid entry, please type a number:");
            scanner.next();
        }
        anyInt = scanner.nextInt();
        return anyInt;
    }
}
