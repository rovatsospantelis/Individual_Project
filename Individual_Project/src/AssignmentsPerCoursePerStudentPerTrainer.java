public class AssignmentsPerCoursePerStudentPerTrainer {

    private String assTitle;
    private String courseTitle;
    private String studentName;
    private String trainerName;
    private boolean turnedIn;

    public String getAssTitle() {
        return assTitle;
    }

    public void setAssTitle(String assTitle) {
        this.assTitle = assTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public boolean isTurnedIn() {
        return turnedIn;
    }

    public void setTurnedIn(boolean turnedIn) {
        this.turnedIn = turnedIn;
    }

    @Override
    public String toString() {
        return "AssignmentsPerCoursePerStudentPerTrainer{" +
                "assTitle='" + assTitle + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", studentName='" + studentName + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", turnedIn=" + turnedIn +
                '}';
    }
}
