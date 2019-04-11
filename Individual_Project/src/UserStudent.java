import java.util.ArrayList;
import java.util.Scanner;

public class UserStudent extends Validation implements PrintedMenus {

    private static Scanner scanner = new Scanner(System.in);
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();

    public void menuStudent(LoggedUser loggedUser) {

        System.out.println("Welcome "+loggedUser.getfName()+" "+loggedUser.getlName()+"! Please choose an action from the menu... ");
        userStudentMenu();

        String studentName = loggedUser.getfName()+" "+loggedUser.getlName();
        boolean test = true;
        int choice = intValidation(scanner);
        scanner.nextLine();


        while (test) {
            switch (choice) {
                case 1:
                    System.out.println("Shall we check the courses?");
                    ArrayList<String> availableCourses = new ArrayList<>();
                    int counter = 0;
                    ArrayList<String> allTheExistingCourses = new ArrayList<>();
                    ArrayList<String> enrolledCourses = new ArrayList<>();
                    ArrayList<Course> courses = coursesDatasource.queryCourse();
                    ArrayList<CoursesPerStudent> list1 = studentsDatasource.getCoursesPerStudent(loggedUser.getlName());
                    for (Course element : courses) {
                        allTheExistingCourses.add(element.getTitle());
                    }
                    for (CoursesPerStudent element : list1) {
                        enrolledCourses.add(element.getCourseTitle());
                    }
                    for (String element : allTheExistingCourses) {
                        if (enrolledCourses.contains(element)){
                            System.out.println("You are already enrolled to the course: "+element);
                        } else {
                            System.out.println("You are NOT enrolled to the course: "+element);
                            counter++;
                            availableCourses.add(element);
                        }

                    }
                    if (counter > 0) {
                        for (String element : availableCourses) {
                            System.out.println("Do you want to enroll in "+element+" ?\nPress -1- to enroll otherwise " +
                                    "any other number to skip.");
                            int choice1 = scanner.nextInt();
                            scanner.nextLine();
                            switch (choice1) {
                                case 1:
                                    System.out.println();
                                    studentsDatasource.enrollToCourse(loggedUser.getProfileId(), element);
                                    System.out.println();
                                    System.out.println("Processing the new data to the database...this may take some " +
                                            "time, please wait...");
                                    studentsDatasource.afterEnrollmentProcedure(loggedUser.getProfileId(), element);
                                    break;
                                default:
                                    break;
                            }

                        }
                        userStudentMenu();
                        choice = intValidation(scanner);
                        scanner.nextLine();
                        System.out.println();
                        break;
                    } else {
                        System.out.println("You are enrolled in every course! Take a break!");
                        System.out.println();
                        userStudentMenu();
                        choice = intValidation(scanner);
                        scanner.nextLine();
                        System.out.println();
                        break;
                    }

                case 2:
                    System.out.println("This is your Daily Personal Schedule based on the Courses you are enrolled ");
                    System.out.println();
                    System.out.println("You daily Schedule per Course : ");
                    System.out.println("===============================");
                    for (CoursesPerStudent element : studentsDatasource.getCoursesPerStudent(loggedUser.getlName())) {
                        for (SchedulePerCourse element2 : studentsDatasource.getSchedulePerCourse(element.getCourseTitle())) {
                            System.out.println("Course Title: " + element2.getCourseTitle()+" ----> " +
                                    " Day: " + element2.getDay() + " ----> " +
                                    " Start-End Time: " + element2.getStartEndTime());
                        }
                    }
                    userStudentMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("These are the Submission Dates of the assignments of the Courses you are enrolled in");
                    System.out.println();
                    studentsDatasource.printDatesOfSubmissionOfAssignmentsPerCourses(loggedUser.getlName());
                    userStudentMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 4:
                    System.out.println("These are the assignments according to the courses you are enrolled, you ca also check " +
                            "if you have already delivered each one of the assignments");
                    System.out.println();
                    ArrayList<AssignmentsPerCoursePerStudentPerTrainer> list = trainersDatasource.getAssignmentsPerStudentPerCoursePerTrainer();
                    for (AssignmentsPerCoursePerStudentPerTrainer element : list) {
                        if (element.getStudentName().equalsIgnoreCase(studentName)) {
                            System.out.println("Assignment's title: "+element.getAssTitle()+" ----> "+" Course's title: "+
                                    element.getCourseTitle()+" ----> "+ " Trainer's name: "+element.getTrainerName()
                            +" ----> "+"Turned in: "+element.isTurnedIn());
                        }
                    }
                    System.out.println();
                    System.out.println("If you want to submit any of your assignments please press -1- , otherwise press " +
                            "any other number to exit");
                    System.out.println();
                    boolean test1 = true;
                    int choice1 = intValidation(scanner);
                    scanner.nextLine();
                    while (test1) {
                        switch (choice1) {
                            case 1:
                                System.out.println("Give some information about the assignment you want to turn in. " +
                                        "Please be very careful with the spelling, the inputs must be correct.");
                                System.out.println("Please write the title of the course:");
                                String courseTitle = stringValidation(scanner);
                                System.out.println("Please write the title of the assignment:");
                                String assTitle = stringValidation(scanner);
                                System.out.println("Please write the trainer's last name:");
                                String trainerLastName = stringValidation(scanner);
                                System.out.println();
                                studentsDatasource.submitAssignments(trainerLastName, courseTitle, loggedUser.getlName(),
                                        assTitle, true);
                                System.out.println();

                                System.out.println("If you want to turn in another assignments press -1- , " +
                                        "otherwise press any other number to exit");
                                choice1 = intValidation(scanner);
                                scanner.nextLine();
                                break;
                            default:
                                test1 = false;
                                break;
                        }
                    }
                    userStudentMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Farewell!!\nYou left the menu");
                    test = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("Wrong entry");
                    userStudentMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
            }
        }

    }
}