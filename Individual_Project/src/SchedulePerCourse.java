public class SchedulePerCourse {

    private int id;
    private String courseTitle;
    private String day;
    private String startEndTime;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        this.startEndTime = startEndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Course Title: " + courseTitle + " ----> " +
                " Day: " + day + " ----> " +
                " Start-End Time: " + startEndTime+"\n" ;
    }
}
