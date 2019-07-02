package fr.epita.quiz.datamodel;

// 为了在GUI界面展示出数据
public class OpenQuestionAll {
    private int qid;
    private String qcontent; //问题内容
    private String qtopics;  //标题
    private int qdifficulty; //复杂度
    private String answer; //正确答案解析

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
