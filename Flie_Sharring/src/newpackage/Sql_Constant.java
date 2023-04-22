package newpackage;

public class Sql_Constant {

    private final String register_sql = "INSERT INTO users (name, email, password, login) VALUES (?, ?, ?, ?)";
    private final String update_status_sql = "UPDATE users SET login=? WHERE name=?";
    private final String check_status_sql = "SELECT status FROM users WHERE name=?";
    private final String login_sql = "SELECT * FROM users WHERE name=?, email=? , password=?";

    public String getRegister_sql() {
        return register_sql;
    }

    public String getUpdate_status_sql() {
        return update_status_sql;
    }

    public String getCheck_status_sql() {
        return check_status_sql;
    }

    public String getLogin_sql() {
        return login_sql;
    }

}
