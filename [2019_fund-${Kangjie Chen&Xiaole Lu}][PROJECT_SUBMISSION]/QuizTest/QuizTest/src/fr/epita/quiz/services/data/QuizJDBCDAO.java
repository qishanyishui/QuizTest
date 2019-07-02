package fr.epita.quiz.services.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.*;
import fr.epita.quiz.services.util.*;

public class QuizJDBCDAO {

    /*private static final  String CREATE_TABLE = "CREATE table QUIZ (id bigint auto_increment, name varchar(255) )";
    private static final String INSERT_QUERY = "INSERT into QUIZ (name) values(?)";
    private static final String UPDATE_QUERY = "UPDATE QUIZ SET NAME=? WHERE ID = ?";
    private static final String DELETE_QUERY = "DELETE FROM QUIZ  WHERE ID = ?";*/


    public void create() throws CreateFailedException {
        String createStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUIZ_CREATE, "");
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(createStr);) {
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new CreateFailedException("create quize table fail!");
        }
    }

    /**
     * Insert a quiz in the database, if a problem occurs then it throws an
     * {@link CreateFailedException} usage example: QuizJDBCDAO dao = new ... try{
     * dao.insert(quizInstance); }catch(CreateFailed e){ //log exception }
     *
     * @param quiz
     * @throws CreateFailedException
     */
    public void insert(Quiz quiz) throws CreateFailedException {
        String insertStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUIZ_INSERT, "");
        try{
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(insertStr);
            pstmt.setString(1, quiz.getTitle());
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new CreateFailedException(quiz);
        }
    }

    public void update(Quiz quiz) throws UpdateFailedException{
        String updateStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUIZ_UPDATE, "");
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(updateStr);) {
            pstmt.setString(1, quiz.getTitle());
            pstmt.setInt(2, quiz.getId());
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new UpdateFailedException(quiz);
        }
    }

    public void delete(Quiz quiz) throws DeleteFailedException {
        String deleteStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUIZ_DELETE, "");
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(deleteStr);) {
            pstmt.setInt(1, quiz.getId());
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new DeleteFailedException(quiz);
        }
    }

    public Quiz getById(int id) {
        return null;
    }

    public List<Quiz> search(Quiz quizCriterion) throws SearchFailedException {
        String searchQuery = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUIZ_SEARCHQUERY_BYIDORNAME, "");
        List<Quiz> quizList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();

             PreparedStatement pstmt = connection.prepareStatement(searchQuery)) {

            pstmt.setInt(1, quizCriterion.getId());
            pstmt.setString(2, "%" + quizCriterion.getTitle() + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String topic = rs.getString("NAME");
                Quiz quiz = new Quiz(topic);
                quiz.setId(id);
                quizList.add(quiz);
            }

            rs.close();
        } catch (SQLException e) {
            throw new SearchFailedException(quizCriterion);
        }
        return quizList;
    }
}
