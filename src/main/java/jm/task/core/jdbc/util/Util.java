package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/mydb?user=admin&password=root";

    private Util() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }
}

