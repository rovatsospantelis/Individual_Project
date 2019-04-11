import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CoursesDatasource {

    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_TITLE = "title";
    private static final String COLUMN_COURSE_STREAM = "stream";
    private static final String COLUMN_COURSE_TYPE = "type";
    private static final String COLUMN_COURSE_START_DATE = "startDate";
    private static final String COLUMN_COURSE_END_DATE = "endDate";
    private static final String QUERY_COURSES = "SELECT * FROM courses";
    private OpenCloseConnection openCloseConnection = new OpenCloseConnection();


    public ArrayList<Course> queryCourse() {
        try (Statement st = openCloseConnection.open().createStatement();
             ResultSet results = st.executeQuery(QUERY_COURSES)){

            ArrayList<Course> courses = new ArrayList<>();
            while (results.next()) {
                Course course = new Course();
                course.setId(results.getInt(COLUMN_COURSE_ID));
                course.setTitle(results.getString(COLUMN_COURSE_TITLE));
                course.setStream(results.getString(COLUMN_COURSE_STREAM));
                course.setType(results.getString(COLUMN_COURSE_TYPE));
                course.setStartDate(results.getDate(COLUMN_COURSE_START_DATE));
                course.setEndDate(results.getDate(COLUMN_COURSE_END_DATE));
                courses.add(course);
            }
            return courses;

        } catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage()) ;
            return null;
        }

    }

    public Course getCoursesByAttribute(String attribute, String value) {
        String query = "SELECT * FROM courses WHERE "+attribute+" = \""+value+"\"";
        Course course = new Course();
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)){
            result.next();
            course.setId(result.getInt(COLUMN_COURSE_ID));
            course.setTitle(result.getString(COLUMN_COURSE_TITLE));
            course.setStream(result.getString(COLUMN_COURSE_STREAM));
            course.setType(result.getString(COLUMN_COURSE_TYPE));
            course.setStartDate(result.getDate(COLUMN_COURSE_START_DATE));
            course.setEndDate(result.getDate(COLUMN_COURSE_END_DATE));

        } catch (SQLException e) {
            System.out.println("Query failed: "+e.getMessage());
            e.printStackTrace();
        }
        return course;
    }
}
