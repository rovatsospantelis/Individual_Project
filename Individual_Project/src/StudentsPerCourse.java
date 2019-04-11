public class StudentsPerCourse {

    private String name;
    private String course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "StudentsPerCourse{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
