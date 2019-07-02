package fr.epita.quiz.services.data;

import fr.epita.quiz.datamodel.MCQQuestion;
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

public class MCQQuestionJDBCDAO {

    //mcqQuestion : 问题内容、主题、复杂度、4个选择项内容、答案解析、正确答案选择项
    //关联了Question表，MCQQuestionChoice表,Answer表
    public void insert(MCQQuestion mcqQuestion) throws CreateFailedException {
        String insertQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_INSERT, "");
        String queryQuestionIdStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERYID, "");
        String insertMCQChoiceStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_MCQCHOICE_INSERT, "");
        String insertAnswerStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_ANSWER_INSERT, "");
        try{
            Connection connection = ConnectionUtil.getConnection();
            // 1
            PreparedStatement pstmt = connection.prepareStatement(insertQuestionStr);
            Question question = mcqQuestion.getQuestion();
            pstmt.setString(1, question.getContent());
            StringBuffer topics = new StringBuffer();
            for(String temp : question.getTopics()){
                topics.append(temp);
                //topics.append(",");
            }
            pstmt.setString(2, topics.toString());
            pstmt.setInt(3,mcqQuestion.getQuestion().getDifficulty());
            pstmt.setInt(4,1); //类型：多项选择题

            boolean questionBoolean=pstmt.execute();
            if(!questionBoolean){
                 // 2
                 pstmt = connection.prepareStatement(queryQuestionIdStr);
                 ResultSet rs = pstmt.executeQuery();
                 int qid = 0;
                 while (rs.next()) {
                    qid = rs.getInt("qid");
                 }
                 List<MCQChoice> mcqQuestionList=mcqQuestion.getMcqChoice();
                 for(MCQChoice mcqChoice : mcqQuestionList){
                     // 3
                     pstmt = connection.prepareStatement(insertMCQChoiceStr);
                     pstmt.setString(1,mcqChoice.getMchoice());
                     pstmt.setBoolean(2,mcqChoice.isMvalid());
                     pstmt.setInt(3,qid);
                     pstmt.execute();
                 }
                 // 4
                 pstmt = connection.prepareStatement(insertAnswerStr);
                 pstmt.setInt(1,qid);
                 pstmt.setString(2,mcqQuestion.getAnswer().getText());
                 pstmt.execute();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new CreateFailedException(mcqQuestion);
        }
    }

    public List<MCQQuestionAll> queryMCQQuestionAlls() throws SearchFailedException {
        String queryQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY, "");
        String queryMCQChoiceStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_MCQCHOICE_QUERY_BYQID, "");
        String queryAnswerStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_ANSWER_QUERY_BYAID, "");
        List<MCQQuestionAll> mcqQuestionList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryQuestionStr);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String qcontent = rs.getString("qcontent");
                String qtopics = rs.getString("qtopics");

                MCQQuestionAll u = new MCQQuestionAll();
                u.setQcontent(qcontent);
                u.setQtopics(qtopics);

                pstmt = connection.prepareStatement(queryMCQChoiceStr);
                pstmt.setInt(1,qid);
                ResultSet temprs = pstmt.executeQuery();
                int i=1;
                while (temprs.next()) {
                    if(i==1)
                        u.setChoiceA(temprs.getString("mchoice"));
                    else if(i==2)
                        u.setChoiceB(temprs.getString("mchoice"));
                    else if(i==3)
                        u.setChoiceC(temprs.getString("mchoice"));
                    else
                        u.setChoiceD(temprs.getString("mchoice"));
                    i++;
                }

                pstmt = connection.prepareStatement(queryAnswerStr);
                pstmt.setInt(1,qid);
                ResultSet trs = pstmt.executeQuery();
                while (trs.next()){
                    u.setAnswer(trs.getString("atext"));
                }

                mcqQuestionList.add(u);
            }
            return mcqQuestionList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("抱歉,没有用户数据!");
        }
    }

    public List<MCQQuestionAll> queryMCQQuestionAllByName(Question question) throws SearchFailedException {
        String queryQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY_BYNAME, "");
        String queryMCQChoiceStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_MCQCHOICE_QUERY_BYQID, "");
        String queryAnswerStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_ANSWER_QUERY_BYAID, "");
        List<MCQQuestionAll> mcqQuestionList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryQuestionStr);
            pstmt.setString(1,"%"+question.getContent()+"%"); //按照主题在查找
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String qcontent = rs.getString("qcontent");
                String qtopics = rs.getString("qtopics");

                MCQQuestionAll u = new MCQQuestionAll();
                u.setQcontent(qcontent);
                u.setQtopics(qtopics);

                pstmt = connection.prepareStatement(queryMCQChoiceStr);
                pstmt.setInt(1,qid);
                ResultSet temprs = pstmt.executeQuery();
                int i=1;
                while (temprs.next()) {
                    if(i==1)
                        u.setChoiceA(temprs.getString("mchoice"));
                    else if(i==2)
                        u.setChoiceB(temprs.getString("mchoice"));
                    else if(i==3)
                        u.setChoiceC(temprs.getString("mchoice"));
                    else
                        u.setChoiceD(temprs.getString("mchoice"));
                    i++;
                }

                pstmt = connection.prepareStatement(queryAnswerStr);
                pstmt.setInt(1,qid);
                ResultSet trs = pstmt.executeQuery();
                while (trs.next()){
                    u.setAnswer(trs.getString("atext"));
                }

                mcqQuestionList.add(u);
            }
            return mcqQuestionList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("抱歉,没有用户数据!");
        }
    }

    public boolean delete(Question question) throws DeleteFailedException {
        String queryQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY_BYCONTENT, "");
        String deleteQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_DELETE_BYQID, "");
        String deleteMcqchoiceStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_MCQCHOICE_DELETE_BYQID, "");
        String deleteAnswerStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_ANSWER_DELETE_BYQID, "");
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

            pstmt = connection.prepareStatement(deleteMcqchoiceStr);
            pstmt.setInt(1,qid);
            pstmt.execute();

            pstmt = connection.prepareStatement(deleteAnswerStr);
            pstmt.setInt(1,qid);
            pstmt.execute();

            return true;
        } catch (SQLException sqle) {
            throw new DeleteFailedException(question);
        }
    }
}
