package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `usersdb`.`users` (\n" +
                "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` SMALLINT(20) NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        try (Connection conn = Util.getConnection(); PreparedStatement statement = conn.prepareStatement(sql);) {
            statement.executeUpdate();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS `usersdb`.`users`";

        try (Connection conn = Util.getConnection(); PreparedStatement statement = conn.prepareStatement(sql);) {
            statement.executeUpdate();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES ('"+ name + "', '" + lastName + "', " + age + ");";

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement();) {
            statement.execute(sql);
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM usersdb.users WHERE id=" + id;
        try (Connection conn = Util.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM 'usersdb'.'users'";
        List<User> userList = new ArrayList<>();

        try (Connection conn = Util.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                User user = new User(result.getString(2),
                        result.getString(3),
                        result.getByte(4));
                user.setId(result.getLong(1));
                userList.add(user);
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE `usersdb`.`users`";

        try (Connection conn = Util.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
