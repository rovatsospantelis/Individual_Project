import java.util.Date;

public class Student {

    private int id;
    private String fName;
    private String lName;
    private Date dOfBirth;
    private double tuitionFees;
    private String userName;


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getdOfBirth() {
        return dOfBirth;
    }

    public void setdOfBirth(Date dOfBirth) {
        this.dOfBirth = dOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", dOfBirth=" + dOfBirth +
                ", tuitionFees=" + tuitionFees +
                '}';
    }
}
