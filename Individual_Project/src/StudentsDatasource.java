import java.sql.*;
import java.util.ArrayList;

public class StudentsDatasource {

    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_STUDENT_ID = "id";
    private static final String COLUMN_STUDENT_FNAME = "fName";
    private static final String COLUMN_STUDENT_LNAME = "lName";
    private static final String COLUMN_STUDENT_DOFBIRTH = "dOfBirth";
    private static final String COLUMN_STUDENT_TUITIONFEES = "tuitionFees";
    private static final String QUERY_STUDENTS = "SELECT * FROM " + TABLE_STUDENTS;
    OpenCloseConnection openCloseConnection = new OpenCloseConnection();


    public ArrayList<Student> queryStudents() {
        try (Statement st = openCloseConnection.open().createStatement();
             ResultSet results = st.executeQuery(QUERY_STUDENTS)) {

            ArrayList<Student> students = new ArrayList<>();
            while (results.next()) {
                Student student = new Student();
                student.setId(results.getInt(COLUMN_STUDENT_ID));
                student.setfName(results.getString(COLUMN_STUDENT_FNAME));
                student.setlName(results.getString(COLUMN_STUDENT_LNAME));
                student.setdOfBirth(results.getDate(COLUMN_STUDENT_DOFBIRTH));
                student.setTuitionFees(results.getDouble(COLUMN_STUDENT_TUITIONFEES));
                students.add(student);
            }
            return students;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }



    public void printStudentsPerCourse() {
        String query = "SELECT CONCAT(fname, ' ', lname) fullName,  title course FROM students s INNER JOIN " +
                "studentspercourse spc ON s.id = spc.fk_St_id INNER JOIN courses c ON c.id = spc.fk_C_id";
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            ArrayList<StudentsPerCourse> allTheStudentsPerCourse = new ArrayList<>();
            while (result.next()) {
                StudentsPerCourse studentsPerCourse = new StudentsPerCourse();
                studentsPerCourse.setName(result.getString(1));
                studentsPerCourse.setCourse(result.getString(2));
                allTheStudentsPerCourse.add(studentsPerCourse);
            }

            CoursesDatasource coursesDatasource = new CoursesDatasource();
            ArrayList<Course> courses = coursesDatasource.queryCourse();
            for (Course element : courses) {
                System.out.println();
                System.out.println("--" + element.getTitle() + "--");
                System.out.println("===============================");
                for (StudentsPerCourse element2 : allTheStudentsPerCourse) {
                    if (element2.getCourse().equalsIgnoreCase(element.getTitle())) {
                        System.out.println(element2.getName());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public ArrayList<StudentsPerCourse> getStudentsPerCourse() {
        String query = "SELECT CONCAT(fname, ' ', lname) fullName,  title course FROM students s INNER JOIN " +
                "studentspercourse spc ON s.id = spc.fk_St_id INNER JOIN courses c ON c.id = spc.fk_C_id";
        ArrayList<StudentsPerCourse> allTheStudentsPerCourse = new ArrayList<>();
        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                StudentsPerCourse studentsPerCourse = new StudentsPerCourse();
                studentsPerCourse.setName(result.getString(1));
                studentsPerCourse.setCourse(result.getString(2));
                allTheStudentsPerCourse.add(studentsPerCourse);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return allTheStudentsPerCourse;
    }

    public ArrayList<SchedulePerCourse> getSchedulePerCourse(String courseName) {
        String query = "SELECT title, day, startEndTime FROM courses c, classhours ch, weekdays w," +
                " schedulepercourse sch WHERE c.id = sch.fk_C_id AND ch.id = sch.fk_Ch_id \n" +
                "AND w.id = sch.fk_Day_id AND c.title = '" + courseName + "'";
        ArrayList<SchedulePerCourse> schedule = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                SchedulePerCourse schedulePerCourse = new SchedulePerCourse();
                schedulePerCourse.setCourseTitle(results.getString(1));
                schedulePerCourse.setDay(results.getString(2));
                schedulePerCourse.setStartEndTime(results.getString(3));
                schedule.add(schedulePerCourse);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return schedule;
    }

    public ArrayList<CoursesPerStudent> getCoursesPerStudent(String studentLname) {
        String query = "SELECT c.title, CONCAT(s.fname,' ', s.lname) fullname FROM courses c, students s," +
                " studentspercourse spc WHERE c.id = spc.fk_C_id AND s.id = spc.fk_St_id AND s.lname = '" + studentLname + "'";
        ArrayList<CoursesPerStudent> allTheCoursesPerStudent = new ArrayList<>();

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                CoursesPerStudent coursesPerStudent = new CoursesPerStudent();
                coursesPerStudent.setCourseTitle(results.getString(1));
                coursesPerStudent.setStudentName(results.getString(2));
                allTheCoursesPerStudent.add(coursesPerStudent);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return allTheCoursesPerStudent;
    }

    public void printDatesOfSubmissionOfAssignmentsPerCourses(String studentLastName) {
        String query = "SELECT a.title Assignment , c.title Course , CONCAT(fname, ' ', lname) fullName, " +
                "a.submissionDate FROM assignments a, courses c, students s, studentspercourse spc, " +
                "assignmentspercourse apc, assignmentsperstudent aps\n" +
                "WHERE a.id = apc.fk_Ass_id AND apc.id = aps.fk_apc_id AND spc.id = aps.fk_spc_id " +
                "AND s.id = spc.fk_St_id AND c.id = spc.fk_C_id AND s.lname = '" + studentLastName + "'";
        ArrayList<DatesOfSubmissionOfAssignments> allTheDatesOfSubOfAss = new ArrayList<>();
        ArrayList<CoursesPerStudent> allTheCoursesPerStudent = getCoursesPerStudent(studentLastName);

        try (Statement statement = openCloseConnection.open().createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                DatesOfSubmissionOfAssignments dsa = new DatesOfSubmissionOfAssignments();
                dsa.setAssTitle(results.getString(1));
                dsa.setCourseTitle(results.getString(2));
                dsa.setStudentName(results.getString(3));
                dsa.setSubDate(results.getString(4));
                allTheDatesOfSubOfAss.add(dsa);
            }

            for (CoursesPerStudent element : allTheCoursesPerStudent) {
                System.out.println("--" + element.getCourseTitle() + "--");
                System.out.println("===============================");
                for (DatesOfSubmissionOfAssignments element2 : allTheDatesOfSubOfAss) {
                    if (element.getCourseTitle().equalsIgnoreCase(element2.getCourseTitle())) {
                        System.out.println("Assignment's title: " + element2.getAssTitle() + " ----> " +
                                "Submission date: " + element2.getSubDate());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void submitAssignments(String trainerLastName, String courseTitle, String studentName, String assTitle, boolean turnedIn) {
        String query = "UPDATE marksoftheassignments SET turnedIn = ? WHERE fk_tr_id = (SELECT id FROM trainers WHERE lName = ?) \n" +
                "AND fk_apc_id = (SELECT id FROM assignmentspercourse WHERE fk_Ass_id = (SELECT id FROM assignments WHERE title = ?)\n" +
                "AND fk_C_id = (SELECT id FROM courses WHERE title = ?) AND fk_spc_id = (SELECT id FROM studentspercourse\n" +
                " WHERE fk_St_id = (SELECT id FROM students WHERE lName = ?) AND fk_C_id = (SELECT id FROM courses WHERE title = ? )))";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setBoolean(1, turnedIn);
            pst.setString(2, trainerLastName);
            pst.setString(3, assTitle);
            pst.setString(4, courseTitle);
            pst.setString(5, studentName);
            pst.setString(6, courseTitle);
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("The assignment was successfully delivered!");
            } else {
                System.out.println("Something went wrong, the assignment was not turned in...\nPlease check the spelling and the list above!");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            System.out.println("The assignment was not turned in...");
        }
    }

    public void enrollToCourse(int id, String courseTitle) {
        String query = "INSERT INTO studentspercourse(fk_St_id, fk_C_id) VALUES ( ? , (SELECT id FROM courses" +
                " WHERE title = ?))";

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setString(2, courseTitle);

            if (pst.executeUpdate() > 0) {
                System.out.println("Student successfully enrolled to the course: " + courseTitle);
            } else {
                System.out.println("Something went wrong, student not enrolled to the course: " + courseTitle);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong, you are not enrolled to the course: " + courseTitle);
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void afterEnrollmentProcedure(int id, String courseTitle) {
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
                }else {
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
            String query4 = "insert into marksoftheassignments(fk_apc_id, fk_spc_id, fk_tr_id)" +
                    " VALUES( ? , ? , (select fk_Tr_id from trainerspercourse where " +
                    "fk_C_id = (select id from courses where title = ? ) LIMIT 1))";

            try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query4)) {

                pst.setInt(1, element);
                pst.setInt(2, spcId);
                pst.setString(3, courseTitle);

                if (pst.executeUpdate() > 0) {
                    System.out.println("Table Marks of the Assignments modification executed!");
                }else {
                    System.out.println("Modification was not executed...");
                }
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

//-----------------------------------------------------------------------------------------------------------------------
    }

    public int getStudentsIdByLastName(String lName) {
        String query = "SELECT id FROM students WHERE lName = '"+lName+"'";
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
}









