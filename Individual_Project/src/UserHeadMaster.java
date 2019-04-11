import java.util.Scanner;

public class UserHeadMaster extends Validation implements PrintedMenus {

    private static Scanner scanner = new Scanner(System.in);
    private static CrudOnCourses crudOnCourses = new CrudOnCourses();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static AssignmentsDatasource assignmentsDatasource = new AssignmentsDatasource();
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static CrudOnStudents crudOnStudents = new CrudOnStudents();
    private static CrudOnAssignments crudOnAssignments = new CrudOnAssignments();
    private static CrudOnTrainers crudOnTrainers = new CrudOnTrainers();
    private static CrudOnStudentsPerCourse crudOnStudentsPerCourse = new CrudOnStudentsPerCourse();
    private static CrudOnTrainersPerCourse crudOnTrainersPerCourse = new CrudOnTrainersPerCourse();
    private static CrudOnAssignmentsPerCourse crudOnAssignmentsPerCourse = new CrudOnAssignmentsPerCourse();
    private static CrudOnSchedulePerCourse crudOnSchedulePerCourse = new CrudOnSchedulePerCourse();

    public void menuHeadMaster(LoggedUser loggedUser) {

        System.out.println("Hail to the Head Master of Hogwarts!!! Welcome " + loggedUser.getfName() + " " + loggedUser.getlName() +
                "! Please choose an action from the menu... ");
        System.out.println();
        headMasterMenu();

        boolean test = true;
        int choice = intValidation(scanner);
        scanner.nextLine();

        while (test) {
            switch (choice) {
                case 1:
                    crudOnCoursesMenu();
                    System.out.println();
                    boolean test2 = true;
                    int choice2 = intValidation(scanner);
                    scanner.nextLine();
                    while (test2) {
                        switch (choice2) {
                            case 1:
                                crudOnCourses.printCourses();
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println();
                                System.out.println("The List with all the data of the Courses");
                                System.out.println("-------------------------------------------");
                                for (Course element : coursesDatasource.queryCourse()) {
                                    System.out.println(" TITLE: " + element.getTitle() +
                                            "  STREAM  : " + element.getStream() + "  " +
                                            "TYPE: " + element.getType() + "  START DATE: " + element.getStartDate() +
                                            "  END DATE: " + element.getEndDate());
                                }
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Before updating any course, take a look at the list of the details of " +
                                        "the existing courses");
                                for (Course element : coursesDatasource.queryCourse()) {
                                    System.out.println("ID: " + element.getId() + "  TITLE: " + element.getTitle() +
                                            "  STREAM  : " + element.getStream() + "  " +
                                            "TYPE: " + element.getType() + "  START DATE: " + element.getStartDate() +
                                            "  END DATE: " + element.getEndDate());
                                }
                                System.out.println();
                                System.out.println("Since you are more aware of the details of the courses, select the field " +
                                        "that you want to update, " +
                                        "set the new value and the course's id.");
                                System.out.println();
                                System.out.println("Please set the field as it is presented at the list above:");
                                System.out.println();
                                String attribute = stringValidation(scanner);
                                if (attribute.equalsIgnoreCase("title")) {
                                    attribute = "title";
                                } else if (attribute.equalsIgnoreCase("stream")) {
                                    attribute = "stream";
                                } else if (attribute.equalsIgnoreCase("type")) {
                                    attribute = "type";
                                } else if (attribute.equalsIgnoreCase("start date")) {
                                    attribute = "startDate";
                                } else if (attribute.equalsIgnoreCase("end date")) {
                                    attribute = "endDate";
                                } else {
                                    System.out.println("Invalid entry, please try again later");
                                    crudOnCoursesMenu();
                                    choice2 = intValidation(scanner);
                                    scanner.nextLine();
                                    System.out.println();
                                    break;
                                }

                                if (attribute.equalsIgnoreCase("startDate") || attribute.equalsIgnoreCase("endDate")) {
                                    System.out.println("Please set the date following the format: YYYY-MM-DD:");
                                } else {
                                    System.out.println("Please set the value:");
                                }
                                String value = stringValidation(scanner);
                                System.out.println("Please set the id of the course:");
                                int id = intValidation(scanner);
                                crudOnCourses.updateCoursesByAttribute(attribute, value, id);
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                crudOnCourses.createCourse();
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                crudOnCourses.deleteCourse();
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("You left the Course's menu");
                                System.out.println();
                                test2 = false;
                                headMasterMenu();
                                System.out.println();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnCoursesMenu();
                                choice2 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 2:
                    crudOnStudentsMenu();
                    boolean test3 = true;
                    int choice3 = intValidation(scanner);
                    scanner.nextLine();
                    while (test3) {
                        switch (choice3) {
                            case 1:
                                crudOnStudents.printStudents();
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println();
                                System.out.println("The List with all the data of the Students");
                                System.out.println("-------------------------------------------");
                                for (Student element : studentsDatasource.queryStudents()) {
                                    System.out.println("FIRST NAME: " + element.getfName() + "  LAST NAME: " + element.getlName()
                                            + "  DATE OF BIRTH: " + element.getdOfBirth() + "  TUITION FEES: " + element.getTuitionFees());
                                }
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Before updating any student, take a look at the list of the details of " +
                                        "the existing students");
                                for (Student element : studentsDatasource.queryStudents()) {
                                    System.out.println("ID: " + element.getId() + "  FIRST NAME: " + element.getfName()
                                            + "  LAST NAME: " + element.getlName()
                                            + "  DATE OF BIRTH: " + element.getdOfBirth() + "  TUITION FEES: " + element.getTuitionFees());
                                }
                                System.out.println();
                                System.out.println("Since you are more aware of the details of the students, select the field " +
                                        "that you want to update, " +
                                        "set the new value and the student's id.");
                                System.out.println();
                                System.out.println("Please set the field as it is presented at the list above:");
                                System.out.println();
                                String attribute = stringValidation(scanner);
                                if (attribute.equalsIgnoreCase("first name")) {
                                    attribute = "fName";
                                } else if (attribute.equalsIgnoreCase("last name")) {
                                    attribute = "lName";
                                } else if (attribute.equalsIgnoreCase("date of birth")) {
                                    attribute = "dOfBirth";
                                } else if (attribute.equalsIgnoreCase("tuition fees")) {
                                    attribute = "tuitionFees";
                                } else {
                                    System.out.println("Invalid entry, please try again later");
                                    crudOnStudentsMenu();
                                    choice3 = intValidation(scanner);
                                    scanner.nextLine();
                                    System.out.println();
                                    break;
                                }
                                System.out.println("Please set the value:");
                                String value = stringValidation(scanner);
                                System.out.println("Please set the id of the student:");
                                int id = intValidation(scanner);
                                crudOnStudents.updateStudentsByAttribute(attribute, value, id);
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                crudOnStudents.createStudent();
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                crudOnStudents.deleteStudent();
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("You left the Student's menu");
                                System.out.println();
                                test3 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnStudentsMenu();
                                choice3 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 3:
                    crudOnAssignmentsMenu();
                    boolean test4 = true;
                    int choice4 = intValidation(scanner);
                    scanner.nextLine();
                    while (test4) {
                        switch (choice4) {
                            case 1:
                                crudOnAssignments.printAssignments();
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println();
                                System.out.println("The List with all the data of the Assignments");
                                System.out.println("----------------------------------------------");
                                for (Assignment element : assignmentsDatasource.queryAssignments()) {
                                    System.out.println("TITLE: " + element.getTitle() + "  DESCRIPTION: " + element.getDescription()
                                            + "  ORAL MARK: " + element.getOralMark() + "  TOTAL MARK: " + element.getTotalMark() + "  " +
                                            "SUBMISSION DATE: " + element.getSubmissionDate());
                                }
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Before updating any assignment, take a look at the list of the details of " +
                                        "the existing assignments");
                                for (Assignment element : assignmentsDatasource.queryAssignments()) {
                                    System.out.println("ID: " + element.getId() + "  TITLE: " + element.getTitle() + "  DESCRIPTION: " + element.getDescription()
                                            + "  ORAL MARK: " + element.getOralMark() + "  TOTAL MARK: " + element.getTotalMark() + "  " +
                                            "SUBMISSION DATE: " + element.getSubmissionDate());
                                }
                                System.out.println();
                                System.out.println("Since you are more aware of the details of the assignments, select the field " +
                                        "that you want to update, " +
                                        "set the new value and the assignment's id.");
                                System.out.println();
                                System.out.println("Please set the field as it is presented at the list above:");
                                System.out.println();
                                String attribute = stringValidation(scanner);
                                if (attribute.equalsIgnoreCase("title")) {
                                    attribute = "title";
                                } else if (attribute.equalsIgnoreCase("description")) {
                                    attribute = "description";
                                } else if (attribute.equalsIgnoreCase("oral mark")) {
                                    attribute = "oralMark";
                                } else if (attribute.equalsIgnoreCase("total mark")) {
                                    attribute = "totalMark";
                                } else if (attribute.equalsIgnoreCase("submission date")) {
                                    attribute = "submissionDate";
                                } else {
                                    System.out.println("Invalid entry, please try again later");
                                    crudOnAssignmentsMenu();
                                    choice4 = intValidation(scanner);
                                    scanner.nextLine();
                                    System.out.println();
                                    break;
                                }
                                System.out.println("Please set the value:");
                                String value = stringValidation(scanner);
                                System.out.println("Please set the id of the assignment:");
                                int id = intValidation(scanner);
                                crudOnAssignments.updateAssignmentsByAttribute(attribute, value, id);
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                crudOnAssignments.createAssignment();
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                crudOnAssignments.deleteAssignment();
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("You left the Assignments' menu");
                                System.out.println();
                                test4 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnAssignmentsMenu();
                                choice4 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }

                    }
                    break;
                case 4:
                    crudOnTrainersMenu();
                    boolean test5 = true;
                    int choice5 = intValidation(scanner);
                    scanner.nextLine();
                    while (test5) {
                        switch (choice5) {
                            case 1:
                                crudOnTrainers.printTrainers();
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println();
                                System.out.println("The List with all the data of the Trainers");
                                System.out.println("----------------------------------------------");
                                for (Trainer element : trainersDatasource.queryTrainers()) {
                                    System.out.println("FIRST NAME: " + element.getfName() + "  LAST NAME: " + element.getlName()
                                            + "  SUBJECT: " + element.getSubject());
                                }
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Before updating any trainer, take a look at the list of the details of " +
                                        "the existing trainers");
                                for (Trainer element : trainersDatasource.queryTrainers()) {
                                    System.out.println("FIRST NAME: " + element.getfName() + "  LAST NAME: " + element.getlName()
                                            + "  SUBJECT: " + element.getSubject());
                                }
                                System.out.println();
                                System.out.println("Since you are more aware of the details of the trainers, select the field " +
                                        "that you want to update, " +
                                        "set the new value and the trainer's id.");
                                System.out.println();
                                System.out.println("Please set the field as it is presented at the list above:");
                                System.out.println();
                                String attribute = stringValidation(scanner);
                                if (attribute.equalsIgnoreCase("first name")) {
                                    attribute = "first name";
                                } else if (attribute.equalsIgnoreCase("last name")) {
                                    attribute = "last name";
                                } else if (attribute.equalsIgnoreCase("subject")) {
                                    attribute = "subject";
                                } else {
                                    System.out.println("Invalid entry, please try again later");
                                    crudOnTrainersMenu();
                                    choice5 = intValidation(scanner);
                                    scanner.nextLine();
                                    System.out.println();
                                    break;
                                }
                                System.out.println("Please set the value:");
                                String value = stringValidation(scanner);
                                System.out.println("Please set the id of the trainer:");
                                int id = intValidation(scanner);
                                crudOnTrainers.updateTrainersByAttribute(attribute, value, id);
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                crudOnTrainers.createTrainer();
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                crudOnTrainers.deleteTrainer();
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("You left the Trainer's menu");
                                System.out.println();
                                test5 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                System.out.println();
                                crudOnTrainersMenu();
                                choice5 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 5:
                    crudOnStudentsPerCourseMenu();
                    boolean test6 = true;
                    int choice6 = intValidation(scanner);
                    scanner.nextLine();
                    while (test6) {
                        switch (choice6) {
                            case 1:
                                studentsDatasource.printStudentsPerCourse();
                                crudOnStudentsPerCourseMenu();
                                choice6 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                crudOnStudentsPerCourse.createSpCrecord();
                                crudOnStudentsPerCourseMenu();
                                choice6 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                crudOnStudentsPerCourse.deleteSpCrecord();
                                crudOnStudentsPerCourseMenu();
                                choice6 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("You left the Students per Courses menu");
                                System.out.println();
                                test6 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnStudentsPerCourseMenu();
                                choice6 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 6:
                    crudOnTrainersPerCourseMenu();
                    boolean test7 = true;
                    int choice7 = intValidation(scanner);
                    scanner.nextLine();
                    while (test7) {
                        switch (choice7) {
                            case 1:
                                crudOnTrainersPerCourse.printTrainersPerCourse();
                                crudOnTrainersPerCourseMenu();
                                choice7 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                crudOnTrainersPerCourse.createTpCrecord();
                                crudOnTrainersPerCourseMenu();
                                choice7 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                crudOnTrainersPerCourse.deleteTpCrecord();
                                crudOnTrainersPerCourseMenu();
                                choice7 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("You left the Trainers per Courses menu");
                                System.out.println();
                                test7 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnTrainersPerCourseMenu();
                                choice7 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 7:
                    crudOnAssignmentsPerCourseMenu();
                    boolean test8 = true;
                    int choice8 = intValidation(scanner);
                    scanner.nextLine();
                    while (test8) {
                        switch (choice8) {
                            case 1:
                                crudOnAssignmentsPerCourse.printAssignmentsPerCourse();
                                crudOnAssignmentsPerCourseMenu();
                                choice8 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                crudOnAssignmentsPerCourse.createApcrecord();
                                crudOnAssignmentsPerCourseMenu();
                                choice8 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                crudOnAssignmentsPerCourse.deleteApCrecord();
                                crudOnAssignmentsPerCourseMenu();
                                choice8 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("You left the Assignments per Courses menu");
                                System.out.println();
                                test8 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                crudOnAssignmentsPerCourseMenu();
                                choice8 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 8:
                    crudOnSchedulePerCourseMenu();
                    boolean test9 = true;
                    int choice9 = intValidation(scanner);
                    scanner.nextLine();
                    while (test9) {
                        switch (choice9) {
                            case 1:
                                crudOnSchedulePerCourse.printSchedulePerCourse();
                                crudOnSchedulePerCourseMenu();
                                choice9 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                crudOnSchedulePerCourse.createSchedulePerCourseRecord();
                                crudOnSchedulePerCourseMenu();
                                choice9 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                crudOnSchedulePerCourse.deleteSchedulePerCourseRecord();
                                crudOnSchedulePerCourseMenu();
                                choice9 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                crudOnSchedulePerCourse.updateSchedulePerCourse();
                                crudOnSchedulePerCourseMenu();
                                choice9 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                System.out.println("You left the Schedule per Courses menu");
                                System.out.println();
                                test9 = false;
                                headMasterMenu();
                                choice = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                            default:
                                System.out.println("Wrong number, try again...");
                                System.out.println();
                                crudOnSchedulePerCourseMenu();
                                choice9 = intValidation(scanner);
                                scanner.nextLine();
                                System.out.println();
                                break;
                        }
                    }
                    break;
                case 9:
                    System.out.println("You left the Head Master menu");
                    test = false;
                    break;
                default:
                    System.out.println("Wrong number, try again...");
                    headMasterMenu();
                    choice = intValidation(scanner);
                    scanner.nextLine();
                    System.out.println();
                    break;
            }
        }
    }
}