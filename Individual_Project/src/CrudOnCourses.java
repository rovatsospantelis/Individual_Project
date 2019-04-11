import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnCourses extends Validation {

    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static AssignmentsDatasource assignmentsDatasource = new AssignmentsDatasource();
    private static Scanner scanner = new Scanner(System.in);
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static int thisTrainerId;


    public void printCourses() {
        System.out.println("These are the courses of the school");
        System.out.println("=====================================");
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
        }
    }

    public void createCourse() {
        String query = "INSERT INTO courses(title, stream, type, startDate, endDate) VALUES( ?, ?, ?, ?, ? )";
        System.out.println("You have selected to create a new course...");
        System.out.println();
        System.out.println("Please set the Title:");
        String title = stringValidation(scanner);
        System.out.println("Please set the Stream:");
        String stream = stringValidation(scanner);
        System.out.println("Please set a Type:");
        String type = stringValidation(scanner);
        System.out.println("Please set a Start Date and Time, follow this format YYYY-MM-DD HH:MM:SS :");
        String startDate = stringValidation(scanner);
        System.out.println("Please set a Start Date and Time, follow this format YYYY-MM-DD HH:MM:SS :");
        String endDate = stringValidation(scanner);

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, title);
            pst.setString(2, stream);
            pst.setString(3, type);
            pst.setString(4, startDate);
            pst.setString(5, endDate);

            if (pst.executeUpdate() > 0) {
                System.out.println("Course creation completed!");
                System.out.println("TITLE: " + title +
                        "  STREAM  : " + stream + "  " +
                        "TYPE: " + type + "  START DATE: " + startDate +
                        "  END DATE: " + endDate);
            } else {
                System.out.println("Course creation failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
//-----------------------------------------------------------------------------------------------------------------------
        // AFTER CREATION PROCEDURE, APPOINT TRAINERS TO THE NEW COURSE
        int trainerId = 0;
        ArrayList<Integer> allTheTrainerIds = new ArrayList<>();
        System.out.println();
        System.out.println("Now you have some extra work to do!\nYou have to appoint the " +
                "trainer(s) and the students under the course and also appoint assignments");
        System.out.println();
        System.out.println("Let's have a quick look at the list of the trainers");
        ArrayList<Trainer> trainers = trainersDatasource.queryTrainers();
        for (Trainer element : trainers) {
            System.out.println(element.getfName() + " " + element.getlName());
        }
        System.out.println();
        for (Trainer element : trainers) {
            System.out.println("Press -1- if you want to appoint " + element.getfName() + " " + element.getlName() + " to the new" +
                    " course, otherwise press any other number");
            int choice = intValidation(scanner);
            scanner.nextLine();
            switch (choice) {
                case 1:
                    trainerId = trainersDatasource.getTrainersIdByLastName(element.getlName());
                    allTheTrainerIds.add(trainerId);
                    thisTrainerId = trainerId;
                    break;
                default:
                    break;
            }
        }

        for (Integer element : allTheTrainerIds) {
            trainersDatasource.enrollToCourse(element, title);
        }
//----------------------------------------------------------------------------------------------------------------------
        //REFORM SCHEDULE

        String query2 = "INSERT INTO schedulepercourse(fk_Day_id, fk_C_id, fk_Ch_id) VALUES( ? , (SELECT id FROM courses WHERE title = ? ), ? )";
        double randomDay = 1 + Math.random() * 5;
        double randomClassHour = 1 + Math.random() * 3;

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query2)) {

            pst.setInt(1, (int) randomDay);
            pst.setString(2, title);
            pst.setInt(3, (int) randomClassHour);

            if (pst.executeUpdate() > 0) {
                System.out.println("The schedule was successfully updated!");
            } else {
                System.out.println("Something went wrong, the schedule was not updated...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------------------------------------

        //APPOINT STUDENTS TO THE COURSE
        int studentId = 0;
        ArrayList<Integer> allTheStudentIds = new ArrayList<>();
        System.out.println("Let's have a quick look at the list of the Students");
        for (Student element : studentsDatasource.queryStudents()) {
            System.out.println(element.getfName() + " " + element.getlName());
        }
        System.out.println();
        for (Student element : studentsDatasource.queryStudents()) {
            System.out.println("Press -1- if you want to appoint " + element.getfName() + " " + element.getlName() + " to the new" +
                    " course, otherwise press any other number");
            int choice = intValidation(scanner);
            scanner.nextLine();
            switch (choice) {
                case 1:
                    studentId = studentsDatasource.getStudentsIdByLastName(element.getlName());
                    allTheStudentIds.add(studentId);
                    break;
                default:
                    break;
            }
        }
        for (Integer element : allTheStudentIds) {
            studentsDatasource.enrollToCourse(element, title);
        }
//--------------------------------------------------------------------------------------------------------------------
//            APPOINT ASSIGNMENTS TO THE COURSE

        int assignmentId = 0;
        ArrayList<Integer> allTheAssignmentIds = new ArrayList<>();
        System.out.println("Let's have a quick look at the list of the available Assignments");
        for (Assignment element : assignmentsDatasource.queryAssignments()) {
            System.out.println(element.getTitle() + " ---> " + element.getDescription());
        }
        System.out.println();
        for (Assignment element : assignmentsDatasource.queryAssignments()) {
            System.out.println("Press -1- if you want to appoint " + element.getTitle() + " to the new" +
                    " course, otherwise press any other number");
            int choice = intValidation(scanner);
            scanner.nextLine();
            switch (choice) {
                case 1:
                    assignmentId = assignmentsDatasource.getAssignmentsIdByTitle(element.getTitle());
                    allTheAssignmentIds.add(assignmentId);
                    break;
                default:
                    break;
            }
        }
        for (Integer element : allTheAssignmentIds) {
            assignmentsDatasource.enrollToCourse(element, title);
        }

        for (Integer element : allTheStudentIds) {
            afterAppointmentProcedure(element, title);
        }

    }

    public void updateCoursesByAttribute(String attribute, String value, int id) {
        String query = "UPDATE courses SET " + attribute + " = ? WHERE id = ? ";


        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, value);
            pst.setInt(2, id);

            if (pst.executeUpdate() > 0) {
                System.out.println("Course update completed!");
            } else {
                System.out.println("Course update failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void afterAppointmentProcedure(int id, String courseTitle) {
        //---------  ASSIGNMENTS PER STUDENT MODIFICATIONS ------------------------------------------------------------
        String query = "SELECT id FROM assignmentspercourse WHERE fk_C_id = (SELECT id FROM courses" +
                " WHERE title = '" + courseTitle + "')";
        ArrayList<Integer> apcIds = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                apcIds.add(results.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        String query2 = "select id from studentspercourse where fk_St_id = " + id + " and " +
                "fk_C_id = (SELECT id FROM courses WHERE title = '" + courseTitle + "')";
        int spcId = 0;

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query2)) {

            result.next();
            spcId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        for (Integer element : apcIds) {
            String query3 = "insert into assignmentsperstudent(fk_apc_id, fk_spc_id) VALUES( ? , ? )";

            try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query3)) {

                pst.setInt(1, element);
                pst.setInt(2, spcId);

                if (pst.executeUpdate() > 0) {
                    System.out.println("Table Assignments per Student modification executed!");
                } else {
                    System.out.println("Modification was not executed...");
                }

            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
// --------------------------------------------------------------------------------------------------------------

        //------MARKS OF THE ASSIGNMENTS MODIFICATIONS---------------------------------------------------------

        for (Integer element : apcIds) {
            String query4 = "insert into marksoftheassignments(fk_apc_id, fk_spc_id, fk_tr_id) VALUES( ? , ? , ?)";

            try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query4)) {

                pst.setInt(1, element);
                pst.setInt(2, spcId);
                pst.setInt(3, thisTrainerId);

                if (pst.executeUpdate() > 0) {
                    System.out.println("Table Marks of the Assignments modification executed!");
                    System.out.println();
                } else {
                    System.out.println("Modification was not executed...");
                }
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

//-----------------------------------------------------------------------------------------------------------------------
    }

    public void deleteCourse() {
        String query = "DELETE FROM courses where id = ? ";
        System.out.println("Before deleting any course, take a look at the list of the details of " +
                "the existing courses");
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println("ID: " + element.getId() + "  TITLE: " + element.getTitle() +
                    "  STREAM  : " + element.getStream() + "  " +
                    "TYPE: " + element.getType() + "  START DATE: " + element.getStartDate() +
                    "  END DATE: " + element.getEndDate());
        }
        System.out.println("Since you are more aware of the details of the courses, select carefully the id of the course " +
                "that you want to delete");
        System.out.println();
        int id = intValidation(scanner);
        scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);

            String deletedCourse = "";
             for (Course element : coursesDatasource.queryCourse()) {
                 if (element.getId() == id ) {
                     deletedCourse = element.getTitle();
                 }
             }

            if (pst.executeUpdate() > 0) {
                System.out.println("Course: "+deletedCourse+" has been successfully deleted!");
                System.out.println("All the records of the course at the related tables have also been deleted!");
            } else {
                System.out.println("Something went wrong, course: "+deletedCourse+" has not been deleted!");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}