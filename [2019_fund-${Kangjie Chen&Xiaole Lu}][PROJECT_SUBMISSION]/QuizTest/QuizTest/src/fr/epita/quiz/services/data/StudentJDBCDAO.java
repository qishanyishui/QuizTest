package fr.epita.quiz.services.data;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.SearchFailedException;
import fr.epita.quiz.exception.UpdateFailedException;
import fr.epita.quiz.services.util.ConfigEntry;
import fr.epita.quiz.services.util.ConfigurationService;
import fr.epita.quiz.services.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCDAO {

    public void insert(Student student) throws CreateFailedException {
        String insertStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_INSERT, "");
        try{
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(insertStr);
            pstmt.setString(1, student.getUname());
            pstmt.setString(2, student.getUpassword());
            pstmt.setInt(3, student.getUlevel());
            pstmt.setString(4,student.getUnumber());
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new CreateFailedException(student);
        }
    }

    public void update(Student student) throws UpdateFailedException {
        String updateStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_UPDATE, "");
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(updateStr);) {
            pstmt.setString(1, student.getUname());
            pstmt.setString(2, student.getUpassword());
            pstmt.setInt(3, student.getUlevel());
            pstmt.setInt(4,student.getUid());
            pstmt.execute();
        } catch (SQLException sqle) {
            throw new UpdateFailedException(student);
        }
    }

    public boolean delete(Student student) throws DeleteFailedException {
        String deleteStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_DELETE, "");
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(deleteStr);) {
            pstmt.setString(1, student.getUname());
            pstmt.execute();
            return true;
        } catch (SQLException sqle) {
            throw new DeleteFailedException(student);
        }
    }

    public List<Student> queryStudents() throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_QUERY, "");
        List<Student> userList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String number = rs.getString("unumber");
                String uname = rs.getString("UNAME");
                String upassword = rs.getString("UPASSWORD");
                int ulevel = rs.getInt("ULEVEL");

                Student u = new Student();
                u.setUnumber(number);
                u.setUname(uname);
                u.setUpassword(upassword);
                u.setUlevel(ulevel);

                userList.add(u);
            }
            return userList;
        } catch (SQLException sqle) {
            throw new SearchFailedException("抱歉,没有用户数据!");
        }
    }

    public List<Student> queryStudentByName(Student student) throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_QUERY_BYNAME, "");
        List<Student> userList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            pstmt.setString(1, "%"+student.getUname()+"%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String number = rs.getString("unumber");
                String uname = rs.getString("UNAME");
                String upassword = rs.getString("UPASSWORD");
                int ulevel = rs.getInt("ULEVEL");

                Student u = new Student();
                u.setUnumber(number);
                u.setUname(uname);
                u.setUpassword(upassword);
                u.setUlevel(ulevel);

                userList.add(u);
            }
            return userList;
        } catch (SQLException sqle) {
            throw new SearchFailedException(student);
        }
    }

    public List<Student> queryStudentByNameAndPassword(Student student) throws SearchFailedException {
        String queryStr = ConfigurationService.getInstance()
                .getConfigurationValue(ConfigEntry.DB_USER_QUERY_BYNAMEANDPASSWORD, "");
        List<Student> userList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(queryStr);
            pstmt.setString(1, student.getUname());
            pstmt.setString(2, student.getUpassword());
            pstmt.setInt(3,student.getUlevel());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("UID");
                String uname = rs.getString("UNAME");
                String upassword = rs.getString("UPASSWORD");
                int ulevel = rs.getInt("ULEVEL");

                Student u = new Student();
                u.setUid(id);
                u.setUname(uname);
                u.setUpassword(upassword);
                u.setUlevel(ulevel);

                userList.add(u);
            }
            return userList;
        } catch (SQLException sqle) {
            throw new SearchFailedException(student);
        }
    }
}
