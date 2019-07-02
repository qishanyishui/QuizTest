package fr.epita.quiz.datamodel;

// Ϊ����GUI����չʾ������
public class MCQQuestionAll {
    private int qid;
    private String qcontent; //��������
    private String qtopics;  //����
    private int qdifficulty; //���Ӷ�
    private String choiceA; //ѡ��������
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private char answerChoice; //��ȷ��ѡ����
    private String answer; //��ȷ�𰸽���

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQcontent() {
        return qcontent;
    }

    public void setQcontent(String qcontent) {
        this.qcontent = qcontent;
    }

    public String getQtopics() {
        return qtopics;
    }

    public void setQtopics(String qtopics) {
        this.qtopics = qtopics;
    }

    public int getQdifficulty() {
        return qdifficulty;
    }

    public void setQdifficulty(int qdifficulty) {
        this.qdifficulty = qdifficulty;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public char getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(char answerChoice) {
        this.answerChoice = answerChoice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MCQQuestionAll{" +
                "qid=" + qid +
                ", qcontent='" + qcontent + '\'' +
                ", qtopics='" + qtopics + '\'' +
                ", qdifficulty=" + qdifficulty +
                ", choiceA='" + choiceA + '\'' +
                ", choiceB='" + choiceB + '\'' +
                ", choiceC='" + choiceC + '\'' +
                ", choiceD='" + choiceD + '\'' +
                ", answerChoice=" + answerChoice +
                ", answer='" + answer + '\'' +
                '}';
    }
}
