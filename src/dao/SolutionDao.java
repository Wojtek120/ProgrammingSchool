package dao;

import models.Solution;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionDao {
    private static final String CREATE_SOLUTION_QUERY = "INSERT INTO solution (created, updated, description, exercise_id, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_SOLUTION_QUERY = "SELECT * FROM solution WHERE id = ?";
    private static final String UPDATE_SOLUTION_QUERY = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, user_id=? WHERE id=?";
    private static final String DELETE_SOLUTION_QUERY = "DELETE FROM solution WHERE id=?";
    private static final String FIND_ALL_SOLUTIONS_QUERY = "SELECT * FROM solution";
    private static final String FIND_ALL_SORTED_SOLUTION_BY_EXERCISE_QUERY = "SELECT * FROM solution WHERE exercise_id = ? ORDER BY created";
    private static final String FIND_ALL_BY_USER_ID_SOLUTION_QUERY = "SELECT * FROM solution WHERE user_id = ? ORDER BY created";

    public Solution create(Solution solution) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);

//            statement.setTimestamp(1, solution.getCreated());
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(2,solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExerciseId());
            statement.setInt(5, solution.getUserId());

            statement.executeUpdate();

            // Pobieranie zestawu wygenerowanych kluczy
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int generatedKey = generatedKeys.getInt(1);
                solution.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return solution;
    }

    public Solution read(int id) {

        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return putDataFromResultSetIntoSolution(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Solution solution) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setTimestamp(1, solution.getCreated());
            statement.setTimestamp(2,solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExerciseId());
            statement.setInt(5, solution.getUserId());
            statement.setInt(6, solution.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = DBUtil.connect()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Solution> findAll() {
        return getSolutions(-1, FIND_ALL_SOLUTIONS_QUERY);
    }

    private List<Solution> findAllByExerciseId(int id){
        return getSolutions(id, FIND_ALL_SORTED_SOLUTION_BY_EXERCISE_QUERY);
    }

    public List<Solution> findAllByUserId(int userId){
        return getSolutions(userId, FIND_ALL_BY_USER_ID_SOLUTION_QUERY);
    }

    private List<Solution> getSolutions(int id, String query) {
        try(Connection connection = DBUtil.connect()) {

            List<Solution> solutions = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);

            if (id != -1){
                statement.setInt(1, id);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Solution solution = putDataFromResultSetIntoSolution(resultSet);
                solutions.add(solution);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution putDataFromResultSetIntoSolution(ResultSet resultSet){
        Solution solution = new Solution();
        try {
            solution.setId(resultSet.getInt("id"));
            solution.setCreated(resultSet.getTimestamp("created"));
            solution.setUpdated(resultSet.getTimestamp("updated"));
            solution.setDescription(resultSet.getString("description"));
            solution.setExerciseId(resultSet.getInt("exercise_id"));
            solution.setUserId(resultSet.getInt("user_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solution;
    }
}
