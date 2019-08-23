package dao;

import models.UserGroup;
import util.DBUtil;

import java.sql.*;
import java.util.Arrays;

public class UserGroupDao {
    private static final String CREATE_USERGROUP_QUERY = "INSERT INTO user_group (name) VALUES (?)";
    private static final String READ_USERGROUP_QUERY = "SELECT * FROM user_group WHERE id = ?";
    private static final String UPDATE_USERGROUP_QUERY = "UPDATE user_group SET name=? WHERE id=?";
    private static final String DELETE_USERGROUP_QUERY = "DELETE FROM user_group WHERE id=?";
    private static final String FIND_ALL_USERGROUPS_QUERY = "SELECT * FROM user_group";
    private static final String CHECK_IF_EXISTS_USERGROUPS_QUERY = "SELECT COUNT(1) AS number_of_groups FROM user_group WHERE id = ?;";

    public UserGroup create(UserGroup userGroup) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_USERGROUP_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, userGroup.getName());

            statement.executeUpdate();

            // Pobieranie zestawu wygenerowanych kluczy
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int generatedKey = generatedKeys.getInt(1);
                userGroup.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userGroup;
    }

    public UserGroup read(int id) {

        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(READ_USERGROUP_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(resultSet.getInt("id"));
                userGroup.setName(resultSet.getString("name"));

                return userGroup;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(UserGroup user) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USERGROUP_QUERY);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USERGROUP_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserGroup[] findAll() {
        try (Connection connection = DBUtil.connect()) {
            UserGroup[] userGroups = new UserGroup[0];
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERGROUPS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(resultSet.getInt("id"));
                userGroup.setName(resultSet.getString("name"));
                userGroups = addToArray(userGroup, userGroups);
            }
            return userGroups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private UserGroup[] addToArray(UserGroup u, UserGroup[] userGroups) {
        UserGroup[] tmpUsers = Arrays.copyOf(userGroups, userGroups.length + 1);
        tmpUsers[userGroups.length] = u;
        return tmpUsers;
    }

    public boolean checkIfExists(int userGroupId){
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_EXISTS_USERGROUPS_QUERY);
            statement.setInt(1, userGroupId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("number_of_groups") != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
