package dao;

import models.Exercise;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseDao {
    private static final String CREATE_EXERCISE_QUERY = "INSERT INTO exercise (title, description) VALUES (?, ?)";
    private static final String READ_EXERCISE_QUERY = "SELECT * FROM exercise WHERE id = ?";
    private static final String UPDATE_EXERCISE_QUERY = "UPDATE exercise SET title=?, description=? WHERE id=?";
    private static final String DELETE_EXERCISE_QUERY = "DELETE FROM exercise WHERE id=?";
    private static final String FIND_ALL_EXERCISES_QUERY = "SELECT * FROM exercise";
    private static final String FIND_ALL_EXERCISES_THAT_ARE_NOT_SOLVED_BY_USER_QUERY = "SELECT * FROM exercise WHERE NOT id IN (SELECT exercise_id FROM solution WHERE user_id = ?);";

    public Exercise create(Exercise exercise) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());

            statement.executeUpdate();

            // Pobieranie zestawu wygenerowanych kluczy
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int generatedKey = generatedKeys.getInt(1);
                exercise.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercise;
    }

    public Exercise read(int id) {

        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Exercise exercise) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exercise[] findAll() {
        try (Connection connection = DBUtil.connect()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Exercise[] addToArray(Exercise u, Exercise[] exercise) {
        Exercise[] tmpUsers = Arrays.copyOf(exercise, exercise.length + 1);
        tmpUsers[exercise.length] = u;
        return tmpUsers;
    }

    public List<Exercise> findAllThatAreNotSolved(int userId) {
        try (Connection connection = DBUtil.connect()) {
            List<Exercise> exercises = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_EXERCISES_THAT_ARE_NOT_SOLVED_BY_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
