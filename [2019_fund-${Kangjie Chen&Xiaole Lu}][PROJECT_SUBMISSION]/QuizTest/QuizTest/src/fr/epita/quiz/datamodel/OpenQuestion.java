package fr.epita.quiz.datamodel;

public class OpenQuestion {
    private int oqid;
    private String oqtext; //答案解析信息
    private Question question;

    public int getOqid() {
        return oqid;
    }

    public void setOqid(int oqid) {
        this.oqid = oqid;
    }

    public String getOqtext() {
        return oqtext;
    }

    public void setOqtext(String oqtext) {
        this.oqtext = oqtext;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "OpenQuestion{" +
                "oqid=" + oqid +
                ", oqtext='" + oqtext + '\'' +
                ", question=" + question +
                '}';
    }
}
