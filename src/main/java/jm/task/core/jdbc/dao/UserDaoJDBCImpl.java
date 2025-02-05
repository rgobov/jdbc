package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final String CREATE_USERS_TABLE_MySQL;
    public static final String DROP_USERS_TABLE_SQL;
    public static final String SAVE_USER_SQL;
    public static final String REMOVE_USER_BY_ID_SQL;
    public static final String GET_ALL_USERS_SQL;
    public static final String CLEAN_USERS_TABLE_SQL;
    private final Connection conn;
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

    {
        try {
            conn = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE_MySQL);
            System.out.println("Таблица создана");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement();) {
            statement.executeUpdate(DROP_USERS_TABLE_SQL);
            System.out.println("Таблица удалена");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = conn.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User удален");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL);) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE_SQL);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
