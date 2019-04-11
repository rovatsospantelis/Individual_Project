import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOnSchedulePerCourse extends Validation {

    private static CoursesDatasource coursesDatasource = new CoursesDatasource();
    private static StudentsDatasource studentsDatasource = new StudentsDatasource();
    private static Scanner scanner = new Scanner(System.in);
    private static CrudOnCourses crudOnCourses = new CrudOnCourses();
    private static OpenCloseConnection openCloseConnection = new OpenCloseConnection();

    public void printSchedulePerCourse() {
        System.out.println("Schedule per Course:");
        System.out.println();
        for (Course element : coursesDatasource.queryCourse()) {
            System.out.println(element.getTitle());
            System.out.println("============================");
            for (SchedulePerCourse element2 : studentsDatasource.getSchedulePerCourse(element.getTitle())) {
                System.out.println(element2.getDay() + " ----> " + element2.getStartEndTime());
            }
            System.out.println();
        }
    }

    public void createSchedulePerCourseRecord() {
        System.out.println("Take a look at the schedule before you put some extra class hours!");
        System.out.println();
        printSchedulePerCourse();
        System.out.println();
        String day = "";
        System.out.println("Please type a Day from Monday - Friday,use capital letters when needed:");
        boolean test = true;

        while (test) {
            day = stringValidation(scanner);
            if (day.equalsIgnoreCase("Monday") || day.equalsIgnoreCase("Tuesday") || day.equalsIgnoreCase("Wednesday")
                    || day.equalsIgnoreCase("Thursday") || day.equalsIgnoreCase("Friday")) {
                test = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }
        System.out.println();
        ArrayList<String> allTheTitlesOfTheCourses = new ArrayList<>();
        for (Course element : coursesDatasource.queryCourse()) {
            allTheTitlesOfTheCourses.add(element.getTitle());
        }
        System.out.println();
        crudOnCourses.printCourses();
        System.out.println();
        System.out.println("Please type the title of the course that you want to put on the schedule, use capital" +
                " letters when needed. Be careful! The spelling must be correct!");
        String title = "";
        boolean test1 = true;
        while (test1) {
            title = stringValidation(scanner);
            if (allTheTitlesOfTheCourses.contains(title)) {
                test1 = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }
        boolean test2 = true;
        String startEndTime = "";
        System.out.println("Please select one of the existing Class Hours");
        System.out.println();
        for (String element : getClassHours()) {
            System.out.println(element);
        }
        System.out.println();
        System.out.println("Please type one of the Class Hours, exactly as they are presented above");
        while (test2) {
            startEndTime = stringValidation(scanner);
            if (getClassHours().contains(startEndTime)) {
                test2 = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }

        String query = "SELECT id FROM weekdays WHERE day = '" + day + "'";
        int dayId = 0;

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            result.next();
            dayId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        int courseId = 0;
        String query2 = "SELECT id FROM courses WHERE title = '"+title+"'";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query2)) {

            result.next();
            courseId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        int chId = 0;
        String query3 = "Select id FROM classhours WHERE startEndTime = '"+startEndTime+"'";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query3)) {

            result.next();
            chId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        String query4 = "INSERT INTO schedulepercourse( fk_Day_id, fk_C_id, fk_Ch_id) VALUES ( ? , ? , ? )";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query4)) {

            pst.setInt(1, dayId);
            pst.setInt(2, courseId);
            pst.setInt(3, chId);

            if (pst.executeUpdate() > 0) {
                System.out.println("A new record is created at the schedule!");
                System.out.println();
            } else {
                System.out.println("Something went wrong, the record was not created...");
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteSchedulePerCourseRecord() {
        System.out.println("You can take a look at the schedule before you delete any record");
        System.out.println();
        printSchedulePerCourse();
        System.out.println();
        String day = "";
        System.out.println("In order to delete a record from the schedule, you have to insert the Day it is instructed, " +
                "the title of the Course and the time (Class Hours)");
        System.out.println();
        System.out.println("Please type the Day from Monday - Friday, use capital letters when needed:");
        boolean test = true;

        while (test) {
            day = stringValidation(scanner);
            if (day.equalsIgnoreCase("Monday") || day.equalsIgnoreCase("Tuesday") || day.equalsIgnoreCase("Wednesday")
                    || day.equalsIgnoreCase("Thursday") || day.equalsIgnoreCase("Friday")) {
                test = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }
        System.out.println();
        ArrayList<String> allTheTitlesOfTheCourses = new ArrayList<>();
        for (Course element : coursesDatasource.queryCourse()) {
            allTheTitlesOfTheCourses.add(element.getTitle());
        }
        System.out.println();
        crudOnCourses.printCourses();
        System.out.println();
        System.out.println("Please type the title of the course that you want to remove from the schedule, use capital " +
                "letters when needed. Be careful! The spelling must be correct!");
        String title = "";
        boolean test1 = true;
        while (test1) {
            title = stringValidation(scanner);
            if (allTheTitlesOfTheCourses.contains(title)) {
                test1 = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }
        boolean test2 = true;
        String startEndTime = "";
        System.out.println("Please select one of the existing Class Hours");
        System.out.println();
        for (String element : getClassHours()) {
            System.out.println(element);
        }
        System.out.println();
        System.out.println("Please type the Class Hours of the course that you want to delete from the schedule," +
                " exactly as it is presented above");
        while (test2) {
            startEndTime = stringValidation(scanner);
            if (getClassHours().contains(startEndTime)) {
                test2 = false;
            } else {
                System.out.println("Invalid entry, please check your spelling...");
            }
        }

        String query = "SELECT id FROM weekdays WHERE day = '" + day + "'";
        int dayId = 0;

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            result.next();
            dayId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        int courseId = 0;
        String query2 = "SELECT id FROM courses WHERE title = '"+title+"'";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query2)) {

            result.next();
            courseId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        int chId = 0;
        String query3 = "Select id FROM classhours WHERE startEndTime = '"+startEndTime+"'";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query3)) {

            result.next();
            chId = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        String query4 = "SELECT id FROM schedulepercourse WHERE fk_Day_id = "+dayId+" AND fk_C_id = "+courseId+" AND fk_Ch_id = "+chId;

        int id = 0;

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query4)) {

            result.next();
            id = result.getInt(1);


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        String query5 = "DELETE FROM schedulepercourse WHERE id = ? ";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query5)) {

            pst.setInt(1, id);
            if (pst.executeUpdate() > 0) {
                System.out.println("The selected record was successfully deleted from the schedule!");
                System.out.println();
            } else {
                System.out.println("Something went wrong, the record was not deleted from the schedule.");
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

    }



    public ArrayList<String> getClassHours() {
        String query = "SELECT startEndTime FROM classhours";
        ArrayList<String> list = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                list.add(result.getString(1));
            }

        }catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    public void updateSchedulePerCourse() {
        ArrayList<String> allTheTitlesOfTheCourses = new ArrayList<>();
        for (Course element : coursesDatasource.queryCourse()) {
            allTheTitlesOfTheCourses.add(element.getTitle());
        }

        String query = "select spc.id, day, title, startEndTime from weekdays w, classhours cl, courses c," +
                " schedulepercourse spc where w.id = spc.fk_day_id and c.id = spc.fk_C_id and cl.id = spc.fk_CH_id ORDER BY id";
        ArrayList<SchedulePerCourse> list = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {


            while (results.next()) {
                SchedulePerCourse schedulePerCourse = new SchedulePerCourse();
                schedulePerCourse.setId(results.getInt(1));
                schedulePerCourse.setDay(results.getString(2));
                schedulePerCourse.setCourseTitle(results.getString(3));
                schedulePerCourse.setStartEndTime(results.getString(4));
                list.add(schedulePerCourse);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Take a look at the full data of the schedule before the update");
        System.out.println();
        for (SchedulePerCourse element : list) {
            System.out.println("ID: "+element.getId()+"  DAY: "+element.getDay()+"  COURSE: "+element.getCourseTitle()
            +"  START END TIME: "+ element.getStartEndTime());
        }
        System.out.println();
        System.out.println("Now that you are more aware of the Schedule, select the number of the id of the record that " +
                "you want to update, the field that needs update and the new value of the field\nNote that you have to type the selected " +
                "field exactly as it is presented above.");
        System.out.println();

        System.out.println("Please type the number of the id of the record that you want to update:");
        int id = intValidation(scanner);

        String courseValue;
        String dayValue;
        String startEndTimeValue;
        int value = 0;
        String attribute = "";
        boolean test = true;
        boolean test1 = true;
        System.out.println("Please type the field that needs update:");

        while (test) {
            attribute = stringValidation(scanner);
            if (attribute.equalsIgnoreCase("day")) {
                attribute = "fk_Day_id";
                while (test1) {
                    System.out.println("Please choose the new value for the day:");
                    dayValue = stringValidation(scanner);
                    if (dayValue.equalsIgnoreCase("Monday")) {
                        value = 1;
                        test1 = false;
                        test = false;
                    } else if (dayValue.equalsIgnoreCase("Tuesday")) {
                        value = 2;
                        test1 = false;
                        test = false;
                    } else if (dayValue.equalsIgnoreCase("Wednesday")) {
                        value = 3;
                        test1 = false;
                        test = false;
                    } else if (dayValue.equalsIgnoreCase("Thursday")) {
                        value = 4;
                        test1 = false;
                        test = false;
                    } else if (dayValue.equalsIgnoreCase("Friday")) {
                        value = 5;
                        test1 = false;
                        test = false;
                    } else {
                        System.out.println("Invalid entry, check your spelling...");
                        System.out.println("Waiting for your new entry:");
                    }
                }
            } else if (attribute.equalsIgnoreCase("course")) {
                attribute = "fk_C_id";
                while (test1) {
                    System.out.println("Please type the title of the course that you want to insert at the record" +
                            " of the schedule that you chose to update, be careful with the spelling: ");
                    courseValue = stringValidation(scanner);
                    if (allTheTitlesOfTheCourses.contains(courseValue)) {
                        String query2 = "SELECT id FROM courses WHERE title = '" + courseValue + "'";

                        try (Statement statement = openCloseConnection.open().createStatement();
                             ResultSet result = statement.executeQuery(query2)) {

                            result.next();
                            value = result.getInt(1);

                        } catch (SQLException e) {
                            System.out.println("Query failed: " + e.getMessage());
                            e.printStackTrace();
                        }
                        test1 = false;
                        test = false;

                    } else {
                        System.out.println("Invalid entry, check your spelling...");
                        System.out.println("Waiting for your new entry:");
                    }
                }
            } else if (attribute.equalsIgnoreCase("start end time")) {
                attribute = "fk_Ch_id";
                while (test1) {
                    System.out.println("Please type the new class hours, be careful with the typing! You have to " +
                            "choose one of the class hours that are given above");
                    startEndTimeValue = stringValidation(scanner);
                    if (getClassHours().contains(startEndTimeValue)) {
                        if (startEndTimeValue.equalsIgnoreCase(getClassHours().get(0))) {
                            value = 1;
                            test1 =false;
                            test = false;
                        } else if (startEndTimeValue.equalsIgnoreCase(getClassHours().get(1))) {
                            value = 2;
                            test1 = false;
                            test = false;
                        } else if (startEndTimeValue.equalsIgnoreCase(getClassHours().get(2))) {
                            value = 3;
                            test1 = false;
                            test = false;
                        }
                    } else {
                        System.out.println("Invalid entry, check your spelling...");
                        System.out.println("Waiting for your new entry:");
                    }
                }
            }
        }


        String query2 = "UPDATE schedulepercourse set "+attribute+" = '"+value+"' WHERE id = ? ";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query2)) {

            pst.setInt(1, id);


            if (pst.executeUpdate() > 0) {
                System.out.println("The schedule has been updated!");
            } else {
                System.out.println("Something went wrong with the update...");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

    }


}
