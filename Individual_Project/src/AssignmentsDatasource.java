import java.sql.*;
import java.util.ArrayList;

public class AssignmentsDatasource {

    private static final String TABLE_ASSIGNMENTS = "assignments";
    private static final String COLUMN_ASSIGNMENT_ID = "id";
    private static final String COLUMN_ASSIGNMENT_TITLE = "title";
    private static final String COLUMN_ASSIGNMENT_DESCRIPTION = "description";
    private static final String COLUMN_ASSIGNMENT_ORAL_MARK = "oralMark";
    private static final String COLUMN_ASSIGNMENT_TOTAL_MARK = "totalMark";
    private static final String COLUMN_ASSIGNMENT_SUBMISSION_DATE = "submissionDate";
    public static final String QUERY_ASSIGNMENTS = "SELECT * FROM assignments";
    OpenCloseConnection openCloseConnection = new OpenCloseConnection();


    public ArrayList<Assignment> queryAssignments() {
        try (Statement st = openCloseConnection.open().createStatement();
             ResultSet results = st.executeQuery(QUERY_ASSIGNMENTS)){

            ArrayList<Assignment> assignments = new ArrayList<>();
            while (results.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(results.getInt(COLUMN_ASSIGNMENT_ID));
                assignment.setTitle(results.getString(COLUMN_ASSIGNMENT_TITLE));
                assignment.setDescription(results.getString(COLUMN_ASSIGNMENT_DESCRIPTION));
                assignment.setOralMark(results.getString(COLUMN_ASSIGNMENT_ORAL_MARK));
                assignment.setTotalMark(results.getString(COLUMN_ASSIGNMENT_TOTAL_MARK));
                assignment.setSubmissionDate(results.getDate(COLUMN_ASSIGNMENT_SUBMISSION_DATE));
                assignments.add(assignment);
            }
            return assignments;

        } catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage()) ;
            return null;
        }

    }

    public void enrollToCourse(int id, String courseTitle) {
        String query = "INSERT INTO assignmentspercourse(fk_Ass_id, fk_C_id) VALUES ( ? , (SELECT id FROM courses" +
                " WHERE title = ?))";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setString(2, courseTitle);

            if (pst.executeUpdate() > 0) {
                System.out.println("Assignment is successfully appointed to the course: " + courseTitle);
            } else {
                System.out.println("Something went wrong, assignment is not appointed to the course: " + courseTitle);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong, you are not enrolled to the course: " + courseTitle);
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public int getAssignmentsIdByTitle(String title) {
        String query = "SELECT id FROM assignments WHERE title = '"+title+"'";
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

    public ArrayList<String> getCoursesPerAssignment(String title) {
        String query = "SELECT a.title Assignment, c.title Course FROM assignments a INNER JOIN assignmentspercourse " +
                "apc ON a.id = apc.fk_Ass_id INNER JOIN courses c ON c.id = apc.fk_C_id;";
        ArrayList<String> list = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            ArrayList<AssignmentsPerCourse> allTheAssignmentsPerCourse = new ArrayList<>();
            while (results.next()) {
               AssignmentsPerCourse assignmentsPerCourse = new AssignmentsPerCourse();
                assignmentsPerCourse.setAssTitle(results.getString(1));
                assignmentsPerCourse.setCourseTitle(results.getString(2));
                allTheAssignmentsPerCourse.add(assignmentsPerCourse);
            }

            for (AssignmentsPerCourse element : allTheAssignmentsPerCourse) {
                if ( title.equalsIgnoreCase(element.getAssTitle())) {
                    list.add(element.getCourseTitle());
                }
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return list;
    }


}
