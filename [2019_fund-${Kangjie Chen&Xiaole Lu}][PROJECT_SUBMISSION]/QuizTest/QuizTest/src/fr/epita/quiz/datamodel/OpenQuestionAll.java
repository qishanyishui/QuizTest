package fr.epita.quiz.datamodel;

// Ϊ����GUI����չʾ������
public class OpenQuestionAll {
    private int qid;
    private String qcontent; //��������
    private String qtopics;  //����
    private int qdifficulty; //���Ӷ�
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "OpenQuestionAll{" +
                "qid=" + qid +
                ", qcontent='" + qcontent + '\'' +
                ", qtopics='" + qtopics + '\'' +
                ", qdifficulty=" + qdifficulty +
                ", answer='" + answer + '\'' +
                '}';
    }
}
