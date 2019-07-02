package fr.epita.quiz.datamodel;
import java.util.List;

public class MCQQuestion {
    private int mid;

    private List<MCQChoice> mcqChoice;

    private Question question;

    private Answer answer;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<MCQChoice> getMcqChoice() {
        return mcqChoice;
    }

    public void setMcqChoice(List<MCQChoice> mcqChoice) {
        this.mcqChoice = mcqChoice;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MCQQuestion{" +
                "mid=" + mid +
                ", mcqChoice=" + mcqChoice +
                ", question=" + question +
                ", answer=" + answer +
                '}';
    }
}

