import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CrudOnStudents extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static UserLogin userLogin = new UserLogin();
    private static UserLogin.PasswordHashing passwordHashing = userLogin.new PasswordHashing();


    public void printStudents() {
        System.out.println("These are the students of the school");
        System.out.println("=====================================");
        for (Student element : studentsDatasource.queryStudents()) {
            System.out.println(element.getfName()+" "+element.getlName());
        }
    }

    public void createStudent() {
        String query = "INSERT INTO students(fName, lName, dOfBirth, tuitionFees) VALUES( ?, ?, ?, ? )";
        System.out.println("You have selected to create a new student...");
        System.out.println();
        System.out.println("Please set the First Name:");
        String fName = stringValidation(scanner);
        System.out.println("Please set the Last Name:");
        String lName = stringValidation(scanner);
        System.out.println("Please set the Date Of Birth, follow this format YYYY-MM-DD:");
        String dOfBirth = scanner.nextLine();
        System.out.println("Please set the Tuition Fees:");
        double tuitionFees = scanner.nextDouble();
        scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, fName);
            pst.setString(2, lName);
            pst.setString(3, dOfBirth);
            pst.setDouble(4, tuitionFees);

            if (pst.executeUpdate() > 0) {
                System.out.println("Student creation completed!");
            } else {
                System.out.println("Student creation failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
//----------------------------------------------------------------------------------------------------------------------
//            AFTER STUDENT CREATION PROCEDURE, ASSIGN COURSES TO THE NEW STUDENT
        int thatStudentId = studentsDatasource.getStudentsIdByLastName(lName);

        System.out.println("Now you have some extra work to do!\nYou have to assign the student " +
                "to some courses\nLets have a quick look at the courses of the school:");
        System.out.println();
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
        }
        System.out.println();

        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println("Do you want to enroll in " + element.getTitle() + " ?\nPress -1- to enroll otherwise " +
                    "any other number to skip.");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println();
                    studentsDatasource.enrollToCourse(thatStudentId, element.getTitle());
                    System.out.println();
                    System.out.println("Processing the new data to the database...this may take some " +
                            "time, please wait...");
                    studentsDatasource.afterEnrollmentProcedure(thatStudentId, element.getTitle());
                    break;
                default:
                    break;
            }
        }
//----------------------------------------------------------------------------------------------------------------------
        //          CREATE ACCESS TO THE PROGRAM FOR THE NEW USER

        String query2 = "INSERT INTO studentusers(username, password, fName, lName, fk_St_id, type) VALUES" +
                "( ?, ?, ?, ?, ?, ? )";
        double cheat = Math.random() * 991 ;
        String username = lName+((int) cheat);
        System.out.println("Please deliver the Username and the Password to the new student!");
        System.out.println("Student's Username: "+username);
        String password = "newGuy"+((int) cheat);
        System.out.println("Student's Password: "+password);
        String hashedPassword = passwordHashing.password(password);
        String type = "student";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query2)) {

            pst.setString(1, username);
            pst.setString(2, hashedPassword);
            pst.setString(3,fName);
            pst.setString(4, lName);
            pst.setInt(5, thatStudentId);
            pst.setString(6, type);

            if (pst.executeUpdate() > 0) {
                System.out.println("The new student is successfully added to the Login System!");
            }else {
                System.out.println("Something went wrong, the student is not added to the Login System...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
//----------------------------------------------------------------------------------------------------------------------
    }

    public void updateStudentsByAttribute(String attribute, String value, int id) {
        String query = "UPDATE students SET " + attribute + " = ? WHERE id = ? ";


        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, value);
            pst.setInt(2, id);

            if (pst.executeUpdate() > 0) {
                System.out.println("Student update completed!");
            } else {
                System.out.println("Student update failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteStudent() {
        String query = "DELETE FROM students WHERE id = ? ";
        System.out.println("Before deleting any student, take a look at the list of the details of " +
                "the existing students");
        for (Student element : studentsDatasource.queryStudents()) {
            System.out.println("ID: " + element.getId() + "  FIRST NAME: "+element.getfName()
                    +"  LAST NAME: "+element.getlName()
                    +"  DATE OF BIRTH: "+element.getdOfBirth()+"  TUITION FEES: "+element.getTuitionFees());
        }
        System.out.println();
        System.out.println("Since you are more aware of the details of the students, select carefully the id of the student " +
                "that you want to delete");
        System.out.println();
        int id = intValidation(scanner);
        scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);

            String deletedStudent = "";
            for (Student element : studentsDatasource.queryStudents()) {
                if (element.getId() == id ) {
                    deletedStudent = element.getfName()+" "+element.getlName();
                }
            }

            if (pst.executeUpdate() > 0) {
                System.out.println("Student: "+deletedStudent+" has been successfully deleted!");
                System.out.println("All the records of the student at the related tables have also been deleted!");
            } else {
                System.out.println("Something went wrong, student: "+deletedStudent+" has not been deleted!");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
