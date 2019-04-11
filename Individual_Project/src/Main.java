import java.util.Scanner;

public class Main extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private static UserLogin userLogin = new UserLogin();

    public static void main(String[] args) {


        System.out.println("Welcome to HOGWARTS, the school of witchcraft and wizardry!\nPress -1- if you are a " +
                "STUDENT\nPress -2- if you are a TRAINER\nPress -3- if you are the HEAD MASTER\nPress -4- " +
                "if you want to exit the menu\n\nWaiting for your choice...");
        System.out.println();

        int initialChoice = intValidation(scanner);                  // THE USER DECLARES HIS IDENTITY (STUDENT/TRAINER/HEAD MASTER)
        scanner.nextLine();                                          // THE login() METHOD CHECKS THE INPUT
        boolean test = true;
        while (test) {
            switch (initialChoice) {
                case 1:
                    userLogin.login("studentusers");
                    test = false;
                    break;
                case 2:
                    userLogin.login("trainerusers");
                    test = false;
                    break;
                case 3:
                    userLogin.login("trainerusers");
                    test = false;
                    break;
                case 4:
                    System.out.println("Farewell!!\nYou left the menu");
                    test = false;
                    break;
                default:
                    System.out.println("Wrong number, please try again...");
                    System.out.println();
                    System.out.println("Press -1- if you are a STUDENT\nPress -2- if you are a TRAINER\nPress -3- if you " +
                            "are the HEAD MASTER\nPress -4- if you want to exit the menu\nWaiting for your choice...");
                    initialChoice = intValidation(scanner);
                    scanner.nextLine();
                    break;
            }
        }
    }


}