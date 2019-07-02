package fr.epita.quiz.services.data;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.SearchFailedException;
import fr.epita.quiz.services.util.ConfigEntry;
import fr.epita.quiz.services.util.ConfigurationService;
import fr.epita.quiz.services.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenQuestionJDBCDAO {

    //mcqQuestion : 问题内容、主题、复杂度、答案解析
    //关联了Question表，OpenQuestion表,Answer表
    public void insert(OpenQuestion openQuestion) throws CreateFailedException {
        String insertQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_INSERT, "");
        String queryQuestionIdStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERYID, "");
        String insertOpenQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_OPENQUESTION_INSERT, "");
        try{
            Connection connection = ConnectionUtil.getConnection();
            // 1
            PreparedStatement pstmt = connection.prepareStatement(insertQuestionStr);
            Question question = openQuestion.getQuestion();
            pstmt.setString(1, question.getContent());
            StringBuffer topics = new StringBuffer();
            for(String temp : question.getTopics()){
                topics.append(temp);
                //topics.append("-");
            }
            pstmt.setString(2, topics.toString());
            pstmt.setInt(3,openQuestion.getQuestion().getDifficulty());
            pstmt.setInt(4,2); //类型：开放题

            boolean questionBoolean = false;
            questionBoolean=pstmt.execute();

            if(!questionBoolean) {
                // 2
                pstmt = connection.prepareStatement(queryQuestionIdStr);
                ResultSet rs = pstmt.executeQuery();
                int qid = 0;
                while (rs.next()) {
                    qid = rs.getInt("qid");
                }
                // 3
                pstmt = connection.prepareStatement(insertOpenQuestionStr);
                pstmt.setString(1,openQuestion.getOqtext());
                pstmt.setInt(2,qid);

                pstmt.execute();
            }
        } catch (Exception sqle) {
            throw new CreateFailedException(openQuestion);
        }
    }

    public List<OpenQuestionAll> queryOpenQuestionAlls() throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY2, "");
        String queryOpenQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_OPENQUESTION_QUERY, "");
        List<OpenQuestionAll> openQuestionAllList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String qcontent = rs.getString("qcontent");
                String qtopics = rs.getString("qtopics");

                OpenQuestionAll openQuestionAll = new OpenQuestionAll();
                openQuestionAll.setQcontent(qcontent);
                openQuestionAll.setQtopics(qtopics);

                pstmt = connection.prepareStatement(queryOpenQuestionStr);
                pstmt.setInt(1,qid);
                ResultSet temprs = pstmt.executeQuery();
                while (temprs.next()){
                    String oqtext = temprs.getString("oqtext");
                    openQuestionAll.setAnswer(oqtext);
                }
                openQuestionAllList.add(openQuestionAll);
            }
            return openQuestionAllList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("抱歉,没有用户数据!");
        }
    }

    public List<OpenQuestionAll> queryOpenQuestionAllByName(Question question) throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY3, "");
        String queryOpenQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_OPENQUESTION_QUERY_BYQID, "");
        List<OpenQuestionAll> openQuestionAllList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            pstmt.setString(1,"%"+question.getContent()+"%"); //按照主题在查找
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String qcontent = rs.getString("qcontent");
                String qtopics = rs.getString("qtopics");

                OpenQuestionAll openQuestionAll = new OpenQuestionAll();
                openQuestionAll.setQcontent(qcontent);
                openQuestionAll.setQtopics(qtopics);

                pstmt = connection.prepareStatement(queryOpenQuestionStr);
                pstmt.setInt(1,qid);
                ResultSet temprs = pstmt.executeQuery();
                while (temprs.next()){
                    String oqtext = temprs.getString("oqtext");
                    openQuestionAll.setAnswer(oqtext);
                }
                openQuestionAllList.add(openQuestionAll);
            }
            return openQuestionAllList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("抱歉,没有用户数据!");
        }
    }

    public boolean delete(Question question) throws DeleteFailedException {
        String queryQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY_BYCONTENT2, "");
        String deleteQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_DELETE_BYQID, "");
        String deleteOpenQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_OPENQUESTION_DELETE, "");
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryQuestionStr);
            pstmt.setString(1, question.getContent());

            ResultSet rs = pstmt.executeQuery();
            int qid = 0;
            while (rs.next()) {
                qid = rs.getInt("qid");
            }
            pstmt = connection.prepareStatement(deleteQuestionStr);
            pstmt.setInt(1,qid);
            pstmt.execute();

            pstmt = connection.prepareStatement(deleteOpenQuestionStr);
            pstmt.setInt(1,qid);
            pstmt.execute();

            return true;
        } catch (SQLException sqle) {
            throw new DeleteFailedException(question);
        }
    }
}
