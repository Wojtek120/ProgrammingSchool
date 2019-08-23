package dao;

import models.Solution;
import models.User;
import util.DBUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users (email, username, password, user_group_id) VALUES (?, ?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email=?, username=?, password=?, user_group_id=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id=?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String FIND_ALL_BY_GROUP_QUERY = "SELECT * FROM users WHERE group_id=?";

    public User create(User user) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());

            statement.executeUpdate();

            // Pobieranie zestawu wygenerowanych kluczy
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int generatedKey = generatedKeys.getInt(1);
                user.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User read(int id) {

        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return putDataFromResultSetIntoUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(User user) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        return getUsers(-1, FIND_ALL_USERS_QUERY);
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User[] findAllByGroupId(int groupId){
        return getUsers(groupId, FIND_ALL_BY_GROUP_QUERY);
    }

    private User[] getUsers(int id, String query){
        try (Connection conn = DBUtil.connect()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(query);

            if(id != -1){
                statement.setInt(1,id);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = putDataFromResultSetIntoUser(resultSet);
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User putDataFromResultSetIntoUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setUserGroupId(resultSet.getInt("user_group_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
