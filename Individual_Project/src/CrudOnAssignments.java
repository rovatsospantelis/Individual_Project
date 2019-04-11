import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnAssignments extends Validation {

    private static AssignmentsDatasource assignmentsDatasource = new AssignmentsDatasource();
    private static Scanner scanner = new Scanner(System.in);
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();


    public void printAssignments() {
        System.out.println("These are the assignments of the school");
        System.out.println("=====================================");
        for (Assignment element : assignmentsDatasource.queryAssignments()) {
            System.out.println(element.getTitle());
        }
    }

    public void createAssignment() {
        String query = "INSERT INTO assignments(title, description, oralMark, totalMark, submissionDate) VALUES( ?, ?, ?, ?, ? )";
        System.out.println("You have selected to create a new assignment...");
        System.out.println("Please set the Title:");
        String title = scanner.nextLine();
        System.out.println("Please set the Description:");
        String description = scanner.nextLine();
        System.out.println("Please describe the functionality of the Oral Mark:");
        String oralMark = scanner.nextLine();
        System.out.println("Please describe the functionality of the Total Mark:");
        String totalMark = scanner.nextLine();
        System.out.println("Please set the Submission Date of the assignment:");
        String submissionDate = scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1,title);
            pst.setString(2,description);
            pst.setString(3,oralMark);
            pst.setString(4,totalMark);
            pst.setString(5,submissionDate);

            if (pst.executeUpdate() > 0) {
                System.out.println("Assignment creation completed!");
            } else {
                System.out.println("Assignment creation failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------------------------------------
//            AFTER ASSIGNMENT CREATION PROCEDURE, APPOINT THE NEW ASSIGNMENT TO COURSES AND SET UP THE RELATED TABLES
        int thatAssignmentId = assignmentsDatasource.getAssignmentsIdByTitle(title);

        System.out.println("Now you have some extra work to do!\nYou have to appoint the new assignment " +
                "to some courses\nLets have a quick look at the courses of the school:");
        System.out.println();
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
        }
        System.out.println();

        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println("Do you want to appoint the new assignment to " + element.getTitle() + " ?\nPress -1- to enroll otherwise " +
                    "any other number to skip.");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println();
                    assignmentsDatasource.enrollToCourse(thatAssignmentId, element.getTitle());
                    System.out.println();
                    break;
                default:
                    break;
            }
        }

        afterAppointmentProcedure(title);
//----------------------------------------------------------------------------------------------------------------------
        
    }

    public void updateAssignmentsByAttribute(String attribute, String value, int id) {
        String query = "UPDATE assignments SET " + attribute + " = ? WHERE id = ? ";


        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, value);
            pst.setInt(2, id);

            if (pst.executeUpdate() > 0) {
                System.out.println("Assignment update completed!");
            } else {
                System.out.println("Assignment update failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteAssignment() {
        String query = "DELETE FROM assignments WHERE id = ? ";
        System.out.println("Before deleting any assignment, take a look at the list of the details of " +
                "the existing assignments");
        for (Assignment element : assignmentsDatasource.queryAssignments()) {
            System.out.println("ID: "+element.getId()+"  TITLE: " + element.getTitle() + "  DESCRIPTION: " + element.getDescription()
                    + "  ORAL MARK: " + element.getOralMark() + "  TOTAL MARK: " + element.getTotalMark() + "  " +
                    "SUBMISSION DATE: " + element.getSubmissionDate());
        }
        System.out.println();
        System.out.println("Since you are more aware of the details of the assignment, select carefully the id of the assignment" +
                "that you want to delete");
        System.out.println();
        int id = intValidation(scanner);
        scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);

            String deletedAssignment = "";
            for (Assignment element : assignmentsDatasource.queryAssignments()) {
                if (element.getId() == id ) {
                    deletedAssignment = element.getTitle();
                }
            }

            if (pst.executeUpdate() > 0) {
                System.out.println("Assignment: "+deletedAssignment+" has been successfully deleted!");
                System.out.println("All the records of the student at the related tables have also been deleted!");
            } else {
                System.out.println("Something went wrong, student: "+deletedAssignment+" has not been deleted!");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void afterAppointmentProcedure(String assTitle) {
        //---------  ASSIGNMENTS PER STUDENT MODIFICATIONS ------------------------------------------------------------
        String query = "SELECT id, fk_C_id FROM assignmentspercourse WHERE fk_Ass_id = (SELECT id FROM assignments" +
                " WHERE title = '" + assTitle + "')";
        ArrayList<AssPerCourIds> list = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {


            while (results.next()) {
                AssPerCourIds assPerCourIds = new AssPerCourIds();
                assPerCourIds.setApcId(results.getInt(1));
                assPerCourIds.setFk_C_id(results.getInt(2));
                list.add(assPerCourIds);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        String query2 = "select id, fk_C_id from studentspercourse";
        ArrayList<StuPerCourIds> list2 = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query2)) {

            while (results.next()) {
                StuPerCourIds stuPerCourIds = new StuPerCourIds();
                stuPerCourIds.setSpcId(results.getInt(1));
                stuPerCourIds.setFk_C_id(results.getInt(2));
                list2.add(stuPerCourIds);
            }


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        for (AssPerCourIds element : list) {
            for (StuPerCourIds element2 : list2) {
                if (element.getFk_C_id() == element2.getFk_C_id()) {
                    String query3 = "insert into assignmentsperstudent(fk_apc_id, fk_spc_id) VALUES( ? , ? )";

                    try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query3)) {

                        pst.setInt(1, element.getApcId());
                        pst.setInt(2, element2.getSpcId());

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
            }
        }
// --------------------------------------------------------------------------------------------------------------

        //------MARKS OF THE ASSIGNMENTS MODIFICATIONS---------------------------------------------------------

        for (AssPerCourIds element : list) {
            for (StuPerCourIds element2 : list2) {
                if (element.getFk_C_id() == element2.getFk_C_id()) {
                    String query4 = "insert into marksoftheassignments(fk_apc_id, fk_spc_id, fk_tr_id)" +
                            " VALUES( ? , ? , (select fk_Tr_id from trainerspercourse where " +
                            "fk_C_id = "+element.getFk_C_id()+" LIMIT 1))";

                    try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query4)) {

                        pst.setInt(1, element.getApcId());
                        pst.setInt(2, element2.getSpcId());

                        if (pst.executeUpdate() > 0) {
                            System.out.println("Table Marks of the Assignments modification executed!");
                        } else {
                            System.out.println("Modification was not executed...");
                        }
                    } catch (SQLException e) {
                        System.out.println("Query failed: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }

//-----------------------------------------------------------------------------------------------------------------------
    }


}
