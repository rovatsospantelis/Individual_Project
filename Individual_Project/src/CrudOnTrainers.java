import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CrudOnTrainers extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static UserLogin userLogin = new UserLogin();
    private static UserLogin.PasswordHashing passwordHashing = userLogin.new PasswordHashing();


    public void printTrainers() {
        System.out.println("These are the trainers of the school");
        System.out.println("=====================================");
        for (Trainer element : trainersDatasource.queryTrainers()) {
            System.out.println(element.getfName()+" "+element.getlName());
        }
    }

    public void createTrainer() {
        String query = "INSERT INTO trainers(fName, lName, subject) VALUES( ?, ?, ?)";
        System.out.println("You have selected to create a new trainer...");
        System.out.println("Please set the First Name:");
        String fName = scanner.nextLine();
        System.out.println("Please set the Last Name:");
        String lName = scanner.nextLine();
        System.out.println("Please set the Subject of Preference");
        String subject = scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, fName);
            pst.setString(2, lName);
            pst.setString(3, subject);

            if (pst.executeUpdate() > 0) {
                System.out.println("Trainer creation completed!");
            } else {
                System.out.println("Trainer creation failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------------------------------------
//            AFTER TRAINER CREATION PROCEDURE, ASSIGN COURSES TO THE NEW TRAINER
        int thatTrainerId = trainersDatasource.getTrainersIdByLastName(lName);

        System.out.println("Now you have some extra work to do!\nYou have to assign the trainer " +
                "to some courses\nLets have a quick look at the courses of the school:");
        System.out.println();
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
        }
        System.out.println();

        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println("Do you want to enroll the new trainer in " + element.getTitle() + " ?\nPress -1- to enroll otherwise " +
                    "any other number to skip.");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println();
                    trainersDatasource.enrollToCourse(thatTrainerId, element.getTitle());
                    System.out.println();
                    System.out.println("Processing the new data to the database...this may take some " +
                            "time, please wait...");
                    break;
                default:
                    break;
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        //          CREATE ACCESS TO THE PROGRAM FOR THE NEW USER

        String query2 = "INSERT INTO trainerusers(username, password, fName, lName, fk_Tr_id, type) VALUES" +
                "( ?, ?, ?, ?, ?, ? )";
        double cheat = Math.random() * 997 ;
        String username = lName+((int) cheat);
        System.out.println("Please deliver the Username and the Password to the new trainer!");
        System.out.println("Trainer's Username: "+username);
        String password = "newTrainer"+((int) cheat);
        System.out.println("Trainer's Password: "+password);
        String hashedPassword = passwordHashing.password(password);
        String type = "trainer";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query2)) {

            pst.setString(1, username);
            pst.setString(2, hashedPassword);
            pst.setString(3, fName);
            pst.setString(4, lName);
            pst.setInt(5, thatTrainerId);
            pst.setString(6, type);

            if (pst.executeUpdate() > 0) {
                System.out.println("The new trainer is successfully added to the Login System!");
            }else {
                System.out.println("Something went wrong, the trainer is not added to the Login System...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
//----------------------------------------------------------------------------------------------------------------------

    }





    public void updateTrainersByAttribute(String attribute, String value, int id) {
        String query = "UPDATE trainers SET " + attribute + " = ? WHERE id = ? ";


        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setString(1, value);
            pst.setInt(2, id);

            if (pst.executeUpdate() > 0) {
                System.out.println("Trainer update completed!");
            } else {
                System.out.println("Trainer update failed...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteTrainer() {
        String query = "DELETE FROM trainers WHERE id = ? ";
        System.out.println("Before deleting any trainer, take a look at the list of the details of " +
                "the existing trainers");
        for (Trainer element : trainersDatasource.queryTrainers()) {
            System.out.println("ID: "+element.getId()+"  FIRST NAME: " + element.getfName() + "  LAST NAME: " + element.getlName()
                    + "  SUBJECT: " + element.getSubject());
        }
        System.out.println();
        System.out.println("Since you are more aware of the details of the trainers, select carefully the id of the trainer " +
                "that you want to delete\n***Be careful! If you delete Albus Dumbledore with ID: 1, you will not be able " +
                "to login to the program with the Head Master role!***");
        System.out.println();
        int id = intValidation(scanner);
        scanner.nextLine();

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);

            String deletedTrainer = "";
            for (Trainer element : trainersDatasource.queryTrainers()) {
                if (element.getId() == id ) {
                    deletedTrainer = element.getfName()+" "+element.getlName();
                }
            }

            if (pst.executeUpdate() > 0) {
                System.out.println("Assignment: "+deletedTrainer+" has been successfully deleted!");
                System.out.println("All the records of the student at the related tables have also been deleted!");
            } else {
                System.out.println("Something went wrong, student: "+deletedTrainer+" has not been deleted!");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

