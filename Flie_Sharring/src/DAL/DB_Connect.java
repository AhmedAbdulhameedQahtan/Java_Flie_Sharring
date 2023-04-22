package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_Connect {

    private final String DB_URL = "jdbc:mysql://localhost/myjava";
    private final String USER_NAME = "root";
    private final String USER_PASS = "";

    public Connection getmConnection() {
        return mConnection;
    }
    private Connection mConnection;

//================CONNECTION================
    public DB_Connect() {
        try {

            mConnection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASS);

            if (mConnection != null) {
                System.out.println("DB_connect is Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//================read fanction============================
    public ResultSet read(String sql, Object[] values) {
        ResultSet result = null;
        try {
            PreparedStatement statement = mConnection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
            }
            result = statement.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

//======================write function===================================
    public boolean write(String sql, Object[] values) {
        boolean checkExecute = false;
        try {
            PreparedStatement statement = mConnection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
            }
            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                checkExecute = true;
            }

        } catch (Exception e) {
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, e);
        }
        return checkExecute;
    }

}
