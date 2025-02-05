package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String CREATE_USERS_TABLE_MySQL;
    public static final String DROP_USERS_TABLE_SQL;
    public static final String SAVE_USER_SQL;
    public static final String REMOVE_USER_BY_ID_SQL;
    public static final String GET_ALL_USERS_SQL;
    public static final String CLEAN_USERS_TABLE_SQL;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/mydb?user=admin&password=root";

   static  {

        // MySQL
        CREATE_USERS_TABLE_MySQL = """
                CREATE TABLE IF NOT EXISTS users
                (
                    id        INT PRIMARY KEY AUTO_INCREMENT,
                    name      VARCHAR(45) NOT NULL,
                    lastname VARCHAR(45) NOT NULL,
                    age       TINYINT     NOT NULL
                )
                """;


        // Generic SQL
        DROP_USERS_TABLE_SQL = """
                DROP TABLE IF EXISTS users
                """;

        SAVE_USER_SQL = """
                INSERT INTO users
                (
                    name, lastname, age
                ) VALUES (?,?,?)
                """;

        REMOVE_USER_BY_ID_SQL = """
                DELETE FROM users
                WHERE id =?
                """;

        GET_ALL_USERS_SQL = """
                SELECT
                    id,
                    name,
                    lastName,
                    age
                FROM users
                """;

        CLEAN_USERS_TABLE_SQL = """
                TRUNCATE TABLE users
                """;
    }



    private Util() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }
}

