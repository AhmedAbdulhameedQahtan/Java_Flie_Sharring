
package newpackage;
import DAL.DB_Connect;
import PL.LoginForm;
import java.sql.Connection;
import java.sql.Statement;
public class Main {

    public static void main(String[] args) {
        LoginForm loginform = new LoginForm();
        loginform.show();
        
    }
}
