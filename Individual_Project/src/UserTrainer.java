import java.util.ArrayList;
import java.util.Scanner;

public class UserTrainer extends Validation implements PrintedMenus {

    private static Scanner scanner = new Scanner(System.in);
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();

    public void menuTrainer(LoggedUser loggedUser) {

        System.out.println("Welcome " + loggedUser.getfName() + " " + loggedUser.getlName() + "! Please choose an action from the menu... ");
        userTrainerMenu();

        String trainerName = loggedUser.getfName() + " " + loggedUser.getlName();
        String trainerLName = loggedUser.getlName();
        boolean test = true;
        int choice = intValidation(scanner);
        scanner.nextLine();

        while (test) {
            switch (choice) {
                case 1:
                    System.out.println();
                    trainersDatasource.printCoursesPerTrainer(trainerName);
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    studentsDatasource.printStudentsPerCourse();
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    trainersDatasource.printStudentsPerSpecificTrainer(trainerName);
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    trainersDatasource.printAssignmentsPerStudentPerCoursePerSpecificTrainer(trainerName);
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    ArrayList<AssignmentsPerCoursePerStudentPerTrainer> list =
                            trainersDatasource.getAssignmentsPerStudentPerCoursePerSpecificTrainer(loggedUser.getlName());
                    System.out.println("Mind that you can't mark any of the assignments, if none of the students has turned in! ");
                    System.out.println();
                    System.out.println("Set the mark for the following assignments");
                    System.out.println("---------------------------------------------");
                    for (AssignmentsPerCoursePerStudentPerTrainer element : list) {
                        if (element.isTurnedIn()) {
                            System.out.println("Course's title: "+element.getCourseTitle()+" ----> Assignment's title: "+element.getAssTitle()+" ----> "+
                                    " Student's name: "+element.getStudentName());
                            System.out.println();
                            System.out.println("Set the mark: ");
                            int mark = intValidation(scanner);
                            scanner.nextLine();
                            trainersDatasource.markAssignmentsPerSpecificTrainer(trainerLName,
                                    element.getCourseTitle(), element.getStudentName(), element.getAssTitle(),mark);
                            System.out.println();
                        } else {
                            System.out.println("The assignment with data: \nCourse's title: "+element.getCourseTitle()+
                                    " ----> Assignment's title: "+element.getAssTitle()+" ----> "+
                                    " Student's name: "+element.getStudentName()+"\nΗas not been turned in...");
                            System.out.println();
                        }
                    }
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 6:
                    System.out.println();
                    System.out.println("Farewell!!\nYou left the menu");
                    System.out.println();
                    test = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("Wrong entry");
                    System.out.println();
                    userTrainerMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
            }


        }
    }
}