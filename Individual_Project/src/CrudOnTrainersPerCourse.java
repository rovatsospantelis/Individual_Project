import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnTrainersPerCourse extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private static TrainersDatasource trainersDatasource = new TrainersDatasource();
    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();
    private static TrainersPerCourse trainersPerCourse = new TrainersPerCourse();


    public void printTrainersPerCourse() {

        String query = "SELECT CONCAT(fname, ' ', lname) fullName, title course FROM trainers t INNER JOIN " +
                "trainerspercourse tpc ON t.id = tpc.fk_Tr_id INNER JOIN courses c ON c.id = tpc.fk_C_id";
        ArrayList<TrainersPerCourse> allTheTrainersPerCourse = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                TrainersPerCourse trainersPerCourse = new TrainersPerCourse();
                trainersPerCourse.setName(results.getString(1));
                trainersPerCourse.setCourse(results.getString(2));
                allTheTrainersPerCourse.add(trainersPerCourse);
            }

        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
            System.out.println("=================================");
            for (TrainersPerCourse element2 : allTheTrainersPerCourse) {
                if (element.getTitle().equalsIgnoreCase(element2.getCourse())) {
                    System.out.println(element2.getName());
                }
            }
            System.out.println();
        }

    }

    public void createTpCrecord() {
        System.out.println("First let's have a look at the list of the Trainers per Course list");
        printTrainersPerCourse();
        System.out.println();
        System.out.println("Select the Trainer that you want to enroll by his Last Name as it is written above:");
        String lName = stringValidation(scanner);
        ArrayList<String> allTheTrainersLName = new ArrayList<>();
        for (Trainer element : trainersDatasource.queryTrainers()) {
            allTheTrainersLName.add(element.getlName());
        }

        if (allTheTrainersLName.contains(lName)) {
            int thatTrainerId = trainersDatasource.getTrainersIdByLastName(lName);
            System.out.println("Shall we check the courses?");
            ArrayList<String> availableCourses = new ArrayList<>();
            int counter = 0;
            ArrayList<Course> courses = coursesDatasource.queryCourse();
            ArrayList<String> allTheExistingCourses = new ArrayList<>();
            ArrayList<String> list = trainersDatasource.getCoursesPerTrainer(lName);

            for (Course element : courses) {
                allTheExistingCourses.add(element.getTitle());
            }

            for (String element : allTheExistingCourses) {
                if (list.contains(element)) {
                    System.out.println("You are already appointed to the course: " + element);
                } else {
                    System.out.println("You are NOT appointed to the course: " + element);
                    counter++;
                    availableCourses.add(element);
                }
            }
            System.out.println();

            if (counter > 0) {
                for (String element : availableCourses) {
                    System.out.println("Do you want to appoint the Trainer: "+lName+" in " + element + " ?\nPress -1- to appoint him/her otherwise " +
                            "any other number to skip.");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice1) {
                        case 1:
                            System.out.println();
                            trainersDatasource.enrollToCourse(thatTrainerId, element);
                            System.out.println();
                            System.out.println("Processing the new data to the database...");
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();

            } else {
                System.out.println("This Trainer is appointed to every existing Course! You should better give him a break!");
            }
        } else {
            System.out.println("Invalid input, please try again later...");
        }
    }


    public void deleteTpCrecord() {
        String query = "DELETE FROM trainerspercourse where id = ? ";
        System.out.println("Before you unenroll any Trainer in any course, take a look at the list of the Trainers per Course");
        System.out.println();
        printTrainersPerCourse();
        System.out.println();
        System.out.println("Since you are more aware of the details, select carefully the Last Name of the Trainer \n" +
                "that you want to release from the Course and the Course's title");
        System.out.println("Enter the Last Name:");
        String lName = stringValidation(scanner);
        System.out.println("Enter the course's title:");
        String title = stringValidation(scanner);
        String query2 = "select id from trainerspercourse where fk_Tr_id = (select id from trainers where" +
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
                System.out.println("The Trainer: "+lName+" has been released from the course: "+title+"!");
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
