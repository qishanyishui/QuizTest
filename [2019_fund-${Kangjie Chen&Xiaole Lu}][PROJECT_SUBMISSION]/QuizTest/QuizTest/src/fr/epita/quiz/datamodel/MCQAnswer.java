package fr.epita.quiz.datamodel;

import java.util.Arrays;

//学生自己做的答案
public class MCQAnswer {
    private MCQQuestion mcqQuestion;
    private char[] answer; //答案值为A,B,C,D

    public MCQQuestion getMcqQuestion() {
        return mcqQuestion;
    }

    public void setMcqQuestion(MCQQuestion mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }

    public char[] getAnswer() {
        return answer;
    }

    public void setAnswer(char[] answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MCQAnswer{" +
                "mcqQuestion=" + mcqQuestion +
                ", answer=" + Arrays.toString(answer) +
                '}';
    }
}
