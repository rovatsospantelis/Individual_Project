import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OpenCloseConnection {

    private Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/hogwarts?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public Connection open() {                                                  //NO NEED TO IMPLEMENT A close() METHOD BECAUSE OF THE TRY
        try {                                                                   //SYNTAX I USE WHEN I NEED TO OPEN THE CONNECTION
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);        //MORE INFO IN THE UserLogin CLASS!

        }catch (SQLException e) {
            System.out.println("Connection failed: "+e.getMessage());
        }
        return conn;
    }


}
