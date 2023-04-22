package BL;

import DAL.DB_Connect;
import com.sun.jdi.connect.spi.Connection;
import newpackage.Sql_Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed_Qahtan
 */
public class Login {

    DB_Connect conn = new DB_Connect();
    Sql_Constant sql = new Sql_Constant();
    PreparedStatement statement;
    //Connection conn;
//==============register=======================
    public void RegisterUser(String username, String email, String password) {
        
        try {
            statement = conn.getmConnection().prepareStatement(sql.getRegister_sql());
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setBoolean(4, true);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//=============================================
    //========================login============================

    public boolean Login(String username, String email, String password) {
        boolean status = false;
        try {
            //String sql="SELECT * FROM users WHERE user_name=? AND email=? AND password=?";
            Statement stmnt = conn.getmConnection().createStatement();
            ResultSet result = stmnt.executeQuery("SELECT * FROM users WHERE name='" + username + "'");
            int count = 0;

            while (result.next()) {
                status = true;
                String Uemail = result.getString(3);
                String Upass = result.getString(4);
                if (email.equalsIgnoreCase(Uemail) && Upass.equals(password)) {
                    status = true;
                }
                ChangeStatus(true, username);
                break;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    //=========================================================

    //===========================check user===========================
    public boolean CheckUser(String username) {
        boolean status = false;
        try {
            Statement stmnt = conn.getmConnection().createStatement();
            ResultSet result = stmnt.executeQuery("SELECT * FROM users WHERE name='" + username + "'");
            int count = 0;

            while (result.next()) {
                status = result.getBoolean("login");
                break;
            }
            //System.out.println(status);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    //=============================================================
    //======================change stste=====================

    public void ChangeStatus(boolean status, String username) {

        try {
            statement = conn.getmConnection().prepareStatement(sql.getUpdate_status_sql());
            statement.setBoolean(1, status);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //==============================================================
}
