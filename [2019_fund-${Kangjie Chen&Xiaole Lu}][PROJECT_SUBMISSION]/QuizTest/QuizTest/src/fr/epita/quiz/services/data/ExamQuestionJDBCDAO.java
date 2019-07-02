package fr.epita.quiz.services.data;

import fr.epita.quiz.datamodel.*;
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
import java.util.*;
import java.io.*;

public class ExamQuestionJDBCDAO {

    /*public static void main(String[] args) throws Exception{
        new ExamQuestionJDBCDAO().buildExamFile();
    }*/

    public static int [] getRandomValue(int length){
        int list[] = new int[length];   //生成 [0-n) 个不重复的随机数 list 用来保存这些随机数
        int n = length;
        int j=0;
        Random rand = new Random();
        boolean[] bool = new boolean[n];
        int num =0;
            for (int i = 0; i<n; i++){
                 do{
                     num = rand.nextInt(n); //如果产生的数相同继续循环
                 }while(bool[num]);
                 bool[num] =true;
                 list[j++]=num;
             }
        return list;
    }

    //生成试卷  将数据库里面的多项选择题  开放题 读取出来  并入写入文件中
    //试卷生成算法策略
    public  void buildExamFile() throws Exception{
        List<String> qTopics = queryQuestionTopics();
        try {
            if(qTopics!=null && qTopics.size()>0){
                for (String qname : qTopics){
                    String fileName = "C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\data/";
                    fileName +=qname;
                    fileName +="1.txt";
                    System.out.println(fileName);
                    File writeName = new File(fileName); // 相对路径，如果没有则要建立一个新的output.txt文件
                    writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖

                    FileWriter writer = new FileWriter(writeName);
                    BufferedWriter out = new BufferedWriter(writer);

                    List<MCQQuestionAll> mcqQuestionAllList = queryMCQQuestionAllByTopics(qname);
                    int result[] = getRandomValue(mcqQuestionAllList.size());  //产生随机数
                    int j = 0;
                    for ( int i=0; i<result.length;i++ ){
                        if(j>=4){
                            break;
                        }
                        MCQQuestionAll mcqQuestionAll = mcqQuestionAllList.get(result[i]);
                        out.write(mcqQuestionAll.getQcontent()+"\r\n");
                        out.write(mcqQuestionAll.getChoiceA()+"\r\n");
                        out.write(mcqQuestionAll.getChoiceB()+"\r\n");
                        out.write(mcqQuestionAll.getChoiceC()+"\r\n");
                        out.write(mcqQuestionAll.getChoiceD()+"\r\n");
                        out.write("*****\r\n");
                        out.write("answer:"+mcqQuestionAll.getAnswerChoice()+"\r\n");
                        j++;
                    }
                    List<OpenQuestionAll> openQuestionAllList = queryOpenQuestionAllByTopics(qname);
                    int result2[] = getRandomValue(openQuestionAllList.size());  //产生随机数
                    int k = 0;
                    for (int i=0;i<result2.length;i++){
                        OpenQuestionAll openQuestionAll =  openQuestionAllList.get(result2[i]);
                        out.write(openQuestionAll.getQcontent()+"\r\n");
                        out.write("*****\r\n");
                        out.write(openQuestionAll.getAnswer()+"\r\n");
                        out.write("#####\r\n");
                    }
                //    out.write("#####");
                    out.flush(); // 把缓存区内容压入文件
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 查询问题主题
    public List<String> queryQuestionTopics() throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_EXAMQUESTION_QUERY_TOPICS, "");
        List<String> topicsList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String topic = rs.getString("qtopics");
                topicsList.add(topic);
            }
            return topicsList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("Not found question topics!");
        }
    }

    // 查询多项选择题
    public List<MCQQuestionAll> queryMCQQuestionAllByTopics(String topics) throws SearchFailedException {
        String queryQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY_BYNAME, "");  //按照主题在查找
        String queryMCQChoiceStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_MCQCHOICE_QUERY_BYQID, "");
        String queryAnswerStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_ANSWER_QUERY_BYAID, "");
        List<MCQQuestionAll> mcqQuestionList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryQuestionStr);
            pstmt.setString(1,"%"+topics+"%"); //按照主题在查找
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String qcontent = rs.getString("qcontent");
                String qtopics = rs.getString("qtopics");
                int qdifficuty = rs.getInt("qdifficulty");

                MCQQuestionAll u = new MCQQuestionAll();
                u.setQid(qid);
                u.setQcontent(qcontent);
                u.setQtopics(qtopics);
                u.setQdifficulty(qdifficuty);

                pstmt = connection.prepareStatement(queryMCQChoiceStr);
                pstmt.setInt(1,qid);
                ResultSet temprs = pstmt.executeQuery();
                int i=1;
                String  mvalidFlag = "";
                while (temprs.next()) {
                    if(i==1){
                        u.setChoiceA(temprs.getString("mchoice"));
                        if (temprs.getBoolean("mvalid")){
                            mvalidFlag = "A";
                        }
                    }
                    else if(i==2){
                        u.setChoiceB(temprs.getString("mchoice"));
                        if (temprs.getBoolean("mvalid")){
                            mvalidFlag = "B";
                        }
                    }
                    else if(i==3){
                        u.setChoiceC(temprs.getString("mchoice"));
                        if (temprs.getBoolean("mvalid")){
                            mvalidFlag = "C";
                        }
                    }
                    else{
                        u.setChoiceD(temprs.getString("mchoice"));
                        if (temprs.getBoolean("mvalid")){
                            mvalidFlag = "D";
                        }
                    }
                    i++;
                }

                u.setAnswerChoice(mvalidFlag.charAt(0));
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
            throw new SearchFailedException("Sorry, no user data!");
        }
    }

    // 查找开放试题
    public List<OpenQuestionAll> queryOpenQuestionAllByTopics(String topics) throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_QUESTION_QUERY3, "");
        String queryOpenQuestionStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_OPENQUESTION_QUERY_BYQID, "");
        List<OpenQuestionAll> openQuestionAllList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            pstmt.setString(1,"%"+topics+"%"); //按照主题在查找
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
            throw new SearchFailedException("Sorry, no user data!");
        }
    }


}