import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnAssignmentsPerCourse extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private static AssignmentsDatasource assignmentsDatasource = new AssignmentsDatasource();
    private static  OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static CrudOnAssignments crudOnAssignments = new CrudOnAssignments();


    public void printAssignmentsPerCourse() {

        String query = "SELECT a.title Assignment, c.title Course FROM assignments a INNER JOIN" +
                " assignmentspercourse apc ON a.id = apc.fk_Ass_id INNER JOIN courses c ON c.id = apc.fk_C_id;";
        ArrayList<AssignmentsPerCourse> allTheAssignmentsPerCourse = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                AssignmentsPerCourse assignmentsPerCourse = new AssignmentsPerCourse();
                assignmentsPerCourse.setAssTitle(results.getString(1));
                assignmentsPerCourse.setCourseTitle(results.getString(2));
                allTheAssignmentsPerCourse.add(assignmentsPerCourse);
            }

        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
            System.out.println("=================================");
            for (AssignmentsPerCourse element2 : allTheAssignmentsPerCourse) {
                if (element.getTitle().equalsIgnoreCase(element2.getCourseTitle())) {
                    System.out.println(element2.getAssTitle());
                }
            }
            System.out.println();
        }
    }


    public void createApcrecord() {
        System.out.println("First let's have a look at the list of the Assignments per Course");
        System.out.println();
        printAssignmentsPerCourse();
        System.out.println();
        System.out.println("Select the Assignment that you want to appoint by its title as it is written above");
        String title = stringValidation(scanner);
        ArrayList<String> allTheAssTitles = new ArrayList<>();
        for (Assignment element : assignmentsDatasource.queryAssignments()) {
            allTheAssTitles.add(element.getTitle());
        }

        if (allTheAssTitles.contains(title)) {
            int thatAssId = assignmentsDatasource.getAssignmentsIdByTitle(title);
            System.out.println("Shall we check the courses?");
            ArrayList<String> availableCourses = new ArrayList<>();
            int counter = 0;
            ArrayList<Course> courses = coursesDatasource.queryCourse();
            ArrayList<String> allTheExistingCourses = new ArrayList<>();
            ArrayList<String> list = assignmentsDatasource.getCoursesPerAssignment(title);

            for (Course element : courses) {
                allTheExistingCourses.add(element.getTitle());
            }

            for (String element : allTheExistingCourses) {
                if (list.contains(element)) {
                    System.out.println("The assignment is already appointed to the course: " + element);
                } else {
                    System.out.println("The assignment is NOT appointed to the course: " + element);
                    counter++;
                    availableCourses.add(element);
                }
            }
            System.out.println();

            if (counter > 0) {
                for (String element : availableCourses) {
                    System.out.println("Do you want to appoint the Assignment: "+title+" in " + element + " ?\nPress" +
                            " -1- to appoint the assignment otherwise any other number to skip.");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice1) {
                        case 1:
                            System.out.println();
                            assignmentsDatasource.enrollToCourse(thatAssId, element);
                            System.out.println();
                            System.out.println("Processing the new data to the database...this may take some " +
                                    "time, please wait...");
                            crudOnAssignments.afterAppointmentProcedure(title);
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();

            } else {
                System.out.println("The Assignments is appointed to every existing Course.");
            }
        } else {
            System.out.println("Invalid input, please try again later...");
        }
    }

    public void deleteApCrecord() {
        String query = "DELETE FROM assignmentspercourse where id = ? ";
        System.out.println("Before you remove an Assignment from a Course, let's have a look at the list of the Assignments per Course");
        System.out.println();
        printAssignmentsPerCourse();
        System.out.println();
        System.out.println("Since you are more aware of the details, select carefully the title of the Assignment \n" +
                "that you want to remove from the Course and the Course's title");
        System.out.println();
        System.out.println("Enter the Assignment's title:");
        String assTitle = stringValidation(scanner);
        System.out.println("Enter the Course's title:");
        String courseTitle = stringValidation(scanner);

        String query2 = "select id from assignmentspercourse where fk_Ass_id = (select id from assignments where" +
                " title = '"+assTitle+"') and fk_C_id = (select id from courses where title = '"+courseTitle+"')";
        int id = 0;
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query2)) {

            result.next();
            id = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);


            if (pst.executeUpdate() > 0) {
                System.out.println("The Assignment: "+assTitle+" has been released from the course: "+courseTitle+"!");
                System.out.println("All the records at the related tables have also been deleted!");
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
