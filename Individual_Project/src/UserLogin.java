import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin extends Validation {

    private static Scanner scanner = new Scanner(System.in);
    private OpenCloseConnection openCloseConnection = new OpenCloseConnection();

    public void login(String tableType) {
        LoggedUser loggedUser = new LoggedUser();
        PasswordHashing passwordHashing = new PasswordHashing();


        System.out.println("Please enter your Username:");                                    //ASKS FORM THE USER TO ENTER USERNAME AND PASSWORD
        String userName = stringValidation(scanner);
        System.out.println("Please enter your Password:");
        String password = stringValidation(scanner);
        String query = "SELECT * FROM "+tableType+" WHERE userName = ? AND password = ?";      //CHECKS THE DATABASE

        try (PreparedStatement pst = openCloseConnection.open().prepareStatement(query)) {     //USING THIS TRY SYNTAX, THE CONNECTION AND THE RESOURCES ARE CLOSED AUTOMATICALLY!
            pst.setString(1, userName);
            pst.setString(2, passwordHashing.password(password));

            ResultSet results = pst.executeQuery();
            results.next();
            loggedUser.setUserName(results.getString(2));
            loggedUser.setfName(results.getString(4));
            loggedUser.setlName(results.getString(5));
            loggedUser.setProfileId(results.getInt(6));
            loggedUser.setType(results.getString(7));
            System.out.println("You successfully logged in!");

            if (tableType.equalsIgnoreCase("studentusers")) {
                UserStudent userStudent = new UserStudent();                           //THE CORRESPONDING METHOD IS BEING CALLED (menuStudent/menuTrainer/menuHeadMaster)
                userStudent.menuStudent(loggedUser);
            }
            if (tableType.equalsIgnoreCase("trainerusers")) {
                if (loggedUser.getType().equalsIgnoreCase("headmaster")) {
                    UserHeadMaster userHeadMaster = new UserHeadMaster();
                    userHeadMaster.menuHeadMaster(loggedUser);
                } else {
                    UserTrainer userTrainer = new UserTrainer();
                    userTrainer.menuTrainer(loggedUser);
                }
            }

        } catch (SQLException e) {
            System.out.println("Wrong Username or Password!");
        }


    }

    public class PasswordHashing {

        private String generatedPassword = null;                            //CREATION OF AN INNER CLASS FOR THE PASSWORDS
                                                                            //ADDING SOME EXTRA SECURITY AND PRIVACY
        public String password(String passwordToHash) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(passwordToHash.getBytes());
                byte[] bytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    }


}
