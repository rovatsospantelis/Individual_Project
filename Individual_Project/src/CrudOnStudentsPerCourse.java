import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnStudentsPerCourse extends Validation {

    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static Scanner scanner = new Scanner(System.in);
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();


    public void createSpCrecord() {
        System.out.println("First let's have a look at the list of the Students per Course");
        System.out.println();
        studentsDatasource.printStudentsPerCourse();
        System.out.println();
        System.out.println("Select the Student that you want to enroll by his Last Name as it is written above");
        String lName = stringValidation(scanner);
        ArrayList<String> allTheStudentsLName = new ArrayList<>();
        for (Student element : studentsDatasource.queryStudents()) {
            allTheStudentsLName.add(element.getlName());
        }

        if (allTheStudentsLName.contains(lName)) {
            int thatStudentId = studentsDatasource.getStudentsIdByLastName(lName);
            System.out.println("Shall we check the courses?");
            ArrayList<String> availableCourses = new ArrayList<>();
            int counter = 0;
            ArrayList<String> enrolledCourses = new ArrayList<>();
            ArrayList<Course> courses = coursesDatasource.queryCourse();
            ArrayList<CoursesPerStudent> list1 = studentsDatasource.getCoursesPerStudent(lName);

            for (CoursesPerStudent element : list1) {
                enrolledCourses.add(element.getCourseTitle());
            }
            for (Course element : courses) {
                if (enrolledCourses.contains(element.getTitle())) {
                    System.out.println("You are already enrolled to the course: " + element.getTitle());
                } else {
                    System.out.println("You are NOT enrolled to the course: " + element.getTitle());
                    counter++;
                    availableCourses.add(element.getTitle());
                }
            }
            if (counter > 0) {
                for (String element : availableCourses) {
                    System.out.println("Do you want to enroll in " + element + " ?\nPress -1- to enroll otherwise " +
                            "any other number to skip.");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice1) {
                        case 1:
                            System.out.println();
                            studentsDatasource.enrollToCourse(thatStudentId, element);
                            System.out.println();
                            System.out.println("Processing the new data to the database...this may take some " +
                                    "time, please wait...");
                            studentsDatasource.afterEnrollmentProcedure(thatStudentId, element);
                            break;
                        default:
                            break;
                    }

                }
                System.out.println();

            } else {
                System.out.println("This Student is enrolled in every course! He/she should take a break!");
            }
        } else {
            System.out.println("Invalid input, please try again later...");
        }
    }

    public void deleteSpCrecord() {
        String query = "DELETE FROM studentspercourse where id = ? ";
        System.out.println("Before you unenroll any student in any course, take a look at the list of the students per course");
        System.out.println();
        studentsDatasource.printStudentsPerCourse();
        System.out.println();
        System.out.println("Since you are more aware of the details, select carefully the Last Name of the Student \n" +
                "that you want to unenroll and the Course's title");
        System.out.println("Enter the Last Name:");
        String lName = stringValidation(scanner);
        System.out.println("Enter the course's title:");
        String title = stringValidation(scanner);
        String query2 = "select id from studentspercourse where fk_St_id = (select id from students where" +
                " lName = '"+lName+"') and fk_C_id = (select id from courses where title = '"+title+"')";
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
                System.out.println("The student: "+lName+" has been unenrolled from the course: "+title+"!");
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
