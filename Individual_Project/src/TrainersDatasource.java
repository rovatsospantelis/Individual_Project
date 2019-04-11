import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainersDatasource {

    private static final String TABLE_TRAINERS = "trainers";
    private static final String COLUMN_TRAINERS_ID = "id";
    private static final String COLUMN_TRAINERS_FNAME = "fName";
    private static final String COLUMN_TRAINERS_LNAME = "lName";
    public static final String COLUMN_TRAINERS_SUBJECT = "subject";

    private static final String QUERY_TRAINERS = "SELECT * FROM "+TABLE_TRAINERS;
    OpenCloseConnection openCloseConnection = new OpenCloseConnection();


    public ArrayList<Trainer> queryTrainers() {
        try (Statement st = openCloseConnection.open().createStatement();
             ResultSet results = st.executeQuery(QUERY_TRAINERS)){

            ArrayList<Trainer> trainers = new ArrayList<>();
            while (results.next()) {
                Trainer trainer = new Trainer();
                trainer.setId(results.getInt(COLUMN_TRAINERS_ID));
                trainer.setfName(results.getString(COLUMN_TRAINERS_FNAME));
                trainer.setlName(results.getString(COLUMN_TRAINERS_LNAME));
                trainer.setSubject(results.getString(COLUMN_TRAINERS_SUBJECT));

                trainers.add(trainer);
            }
            return trainers;

        } catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage()) ;
            return null;
        }

    }

    public Trainer getTrainerByAttribute(String attribute, String value) {
        String query = "select * from trainers where "+attribute+" = \""+value+"\"";
        Trainer trainer = new Trainer();
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)){
            result.next();
            trainer.setId(result.getInt(COLUMN_TRAINERS_ID));
            trainer.setfName(result.getString(COLUMN_TRAINERS_FNAME));
            trainer.setlName(result.getString(COLUMN_TRAINERS_LNAME));
            trainer.setSubject(result.getString(COLUMN_TRAINERS_SUBJECT));

        } catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage()) ;
        }
        return  trainer;
    }

    public void printCoursesPerTrainer(String trainerName) {
        String query = "SELECT CONCAT(fname, ' ', lname) fullName, title course FROM trainers t INNER JOIN " +
                "trainerspercourse tpc ON t.id = tpc.fk_Tr_id INNER JOIN courses c ON c.id = tpc.fk_C_id";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            ArrayList<TrainersPerCourse> allTheTrainersPerCourse = new ArrayList<>();
            while (results.next()) {
                TrainersPerCourse trainersPerCourse = new TrainersPerCourse();
                trainersPerCourse.setName(results.getString(1));
                trainersPerCourse.setCourse(results.getString(2));
                allTheTrainersPerCourse.add(trainersPerCourse);
            }
            int counter = 0;
            System.out.println("--"+trainerName+"--");
            System.out.println("===================================");
            for (TrainersPerCourse element : allTheTrainersPerCourse) {
                if ( trainerName.equalsIgnoreCase(element.getName())) {
                    System.out.println(element.getCourse());
                    counter++;
                }
            }
            if (counter == 0) {
                System.out.println(trainerName+" doesn't teach any course. Please check the spelling.");
            }

        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());

        }
    }

    public void printStudentsPerSpecificTrainer(String trainerName) {
        String query = "SELECT CONCAT(fname, ' ', lname) fullName, title course FROM trainers t INNER JOIN " +
                "trainerspercourse tpc ON t.id = tpc.fk_Tr_id INNER JOIN courses c ON c.id = tpc.fk_C_id";

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {
            StudentsDatasource studentsDatasource = new StudentsDatasource();
            ArrayList<StudentsPerCourse> allTheStudentsPerCourse = studentsDatasource.getStudentsPerCourse();
            ArrayList<TrainersPerCourse> allTheTrainersPerCourse = new ArrayList<>();
            while (results.next()) {
                TrainersPerCourse trainersPerCourse = new TrainersPerCourse();
                trainersPerCourse.setName(results.getString(1));
                trainersPerCourse.setCourse(results.getString(2));
                allTheTrainersPerCourse.add(trainersPerCourse);
            }
            System.out.println("--"+trainerName+"--");
            System.out.println("===================================");
            for (TrainersPerCourse element : allTheTrainersPerCourse) {
                if (trainerName.equalsIgnoreCase(element.getName())) {
                    for (StudentsPerCourse element2 : allTheStudentsPerCourse) {
                        if (element.getCourse().equalsIgnoreCase(element2.getCourse())) {
                            System.out.println("Student's name: "+element2.getName()+" ----> Course's title: "+element.getCourse());
                        }
                    }
                }

            }
        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());

        }
    }

    public ArrayList<AssignmentsPerCoursePerStudentPerTrainer> getAssignmentsPerStudentPerCoursePerTrainer() {           //ΤΗ ΧΡΗΣΙΜΟΠΟΙΩ ΑΠΟΚΑΤΩ
        String query = "SELECT a.title Assignment , c.title Course , CONCAT(s.fname, ' ', s.lname) St_fullName, " +
                "CONCAT(t.fName,' ', t.lName) Tr_fullName, moa.turnedIn FROM assignments a, courses c, students s, trainers t," +
                " studentspercourse spc, assignmentspercourse apc, marksoftheassignments moa, trainerspercourse tpc\n" +
                "WHERE a.id = apc.fk_Ass_id AND apc.id = moa.fk_apc_id AND spc.id = moa.fk_spc_id AND" +
                " s.id = spc.fk_St_id AND c.id = spc.fk_C_id AND t.id = tpc.fk_Tr_id AND tpc.fk_C_id = c.id AND t.id = moa.fk_tr_id;";

        ArrayList<AssignmentsPerCoursePerStudentPerTrainer> fullList = new ArrayList<>();
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                AssignmentsPerCoursePerStudentPerTrainer item = new AssignmentsPerCoursePerStudentPerTrainer();
                item.setAssTitle(results.getString(1));
                item.setCourseTitle(results.getString(2));
                item.setStudentName(results.getString(3));
                item.setTrainerName(results.getString(4));
                item.setTurnedIn(results.getBoolean(5));
                fullList.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return fullList;
    }

    public void printAssignmentsPerStudentPerCoursePerSpecificTrainer (String trainerName) {
        ArrayList<AssignmentsPerCoursePerStudentPerTrainer> fullList = getAssignmentsPerStudentPerCoursePerTrainer();

        System.out.println("--"+trainerName+"--");
        System.out.println("===================================");
        for (AssignmentsPerCoursePerStudentPerTrainer element : fullList) {
            if (element.getTrainerName().equalsIgnoreCase(trainerName)) {
                System.out.println("Student's name: "+element.getStudentName()+" ----> Course's title:" +
                        " "+element.getCourseTitle()+" ----> Assignment's title: "+element.getAssTitle());
                System.out.println();
            }
        }
    }

    public void markAssignmentsPerSpecificTrainer(String trainerLastName, String courseTitle, String studentLName, String assTitle, int mark) {
        String query = "UPDATE marksoftheassignments SET mark = ? WHERE fk_tr_id = (SELECT id FROM trainers WHERE lName = ?) \n" +
                "AND fk_apc_id = (SELECT id FROM assignmentspercourse WHERE fk_Ass_id = (SELECT id FROM assignments WHERE title = ?)\n" +
                "AND fk_C_id = (SELECT id FROM courses WHERE title = ?) AND fk_spc_id = (SELECT id FROM studentspercourse\n" +
                " WHERE fk_St_id = (SELECT id FROM students WHERE lName = ? ) AND fk_C_id = (SELECT id FROM courses WHERE title = ? )))";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, mark);
            pst.setString(2, trainerLastName);
            pst.setString(3, assTitle);
            pst.setString(4, courseTitle);
            pst.setString(5, studentLName);
            pst.setString(6, courseTitle);

            if (pst.executeUpdate() > 0) {
                System.out.println("The mark was successfully updated!");
            } else {
                System.out.println("The mark of the assignment was not updated...");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<AssignmentsPerCoursePerStudentPerTrainer> getAssignmentsPerStudentPerCoursePerSpecificTrainer(String trainerName) {
        String query = "SELECT a.title Assignment , c.title Course , s.lname lastname, " +
                "CONCAT(t.fName,' ', t.lName) Tr_fullName, moa.turnedIn FROM assignments a, courses c, students s," +
                " trainers t, studentspercourse spc, assignmentspercourse apc, marksoftheassignments moa, trainerspercourse tpc\n" +
                "WHERE a.id = apc.fk_Ass_id AND apc.id = moa.fk_apc_id AND spc.id = moa.fk_spc_id AND s.id = spc.fk_St_id " +
                "AND c.id = spc.fk_C_id AND t.id = tpc.fk_Tr_id AND tpc.fk_C_id = c.id AND t.id = moa.fk_tr_id AND t.lName = '"+trainerName+"'";

        ArrayList<AssignmentsPerCoursePerStudentPerTrainer> list = new ArrayList<>();
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                AssignmentsPerCoursePerStudentPerTrainer item = new AssignmentsPerCoursePerStudentPerTrainer();
                item.setAssTitle(results.getString(1));
                item.setCourseTitle(results.getString(2));
                item.setStudentName(results.getString(3));
                item.setTrainerName(results.getString(4));
                item.setTurnedIn(results.getBoolean(5));
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return list;
    }

    public int getTrainersIdByLastName(String lName) {
        String query = "SELECT id FROM trainers WHERE lName = '"+lName+"'";
        int id = 0;
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            result.next();
            id = result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return id;
    }

    public void enrollToCourse(int id, String courseTitle) {
        String query = "INSERT INTO trainerspercourse(fk_Tr_id, fk_C_id) VALUES ( ? , (SELECT id FROM courses" +
                " WHERE title = ?))";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setString(2, courseTitle);

            if (pst.executeUpdate() > 0) {
                System.out.println("Trainer is successfully appointed to the course: " + courseTitle);
            } else {
                System.out.println("Something went wrong, trainer is not appointed to the course: " + courseTitle);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong, you are not enrolled to the course: " + courseTitle);
            System.out.println("Query failed: " + e.getMessage());
        }
    }


    public ArrayList<TrainersPerCourse> getTrainersPerCourse() {
        String query = "SELECT lName, title course FROM trainers t INNER JOIN " +
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
        return allTheTrainersPerCourse;
    }


    public ArrayList<String> getCoursesPerTrainer(String trainerLname) {
        String query = "SELECT lName, title course FROM trainers t INNER JOIN " +
                "trainerspercourse tpc ON t.id = tpc.fk_Tr_id INNER JOIN courses c ON c.id = tpc.fk_C_id";
        ArrayList<String> list = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            ArrayList<TrainersPerCourse> allTheTrainersPerCourse = new ArrayList<>();
            while (results.next()) {
                TrainersPerCourse trainersPerCourse = new TrainersPerCourse();
                trainersPerCourse.setName(results.getString(1));
                trainersPerCourse.setCourse(results.getString(2));
                allTheTrainersPerCourse.add(trainersPerCourse);
            }

            for (TrainersPerCourse element : allTheTrainersPerCourse) {
                if ( trainerLname.equalsIgnoreCase(element.getName())) {
                    list.add(element.getCourse());
                }
            }


        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return list;
    }


}


















