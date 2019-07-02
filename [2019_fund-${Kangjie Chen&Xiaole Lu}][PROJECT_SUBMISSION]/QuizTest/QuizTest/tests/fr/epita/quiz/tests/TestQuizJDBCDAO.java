package fr.epita.quiz.tests;

import java.sql.Statement;

import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.services.data.*;

public class TestQuizJDBCDAO {

    public static void main(String[] args) throws Exception {
        System.out.println("asfdfdsa");
        Statement stmt = null;
        try {

            QuizJDBCDAO dao = new QuizJDBCDAO();

            //insert
            dao.insert(new Quiz("Why is Java so cool2?"));

            System.out.println("456789");
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("12324");

        System.out.println("insert successfully!");

    }

}
