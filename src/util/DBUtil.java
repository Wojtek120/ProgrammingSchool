package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private  static final String DB_URL="jdbc:mysql://localhost:3306/programming_school";
    private static final  String DB_PROPERTIES = "?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD="coderslab";

    public  static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL+DB_PROPERTIES, DB_USER, DB_PASSWORD);
    }
}
