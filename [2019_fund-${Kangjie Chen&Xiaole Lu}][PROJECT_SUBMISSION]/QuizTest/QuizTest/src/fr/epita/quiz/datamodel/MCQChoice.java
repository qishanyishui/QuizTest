package fr.epita.quiz.datamodel;

public class MCQChoice {
    private String mchoice; //ѡ����
    private boolean mvalid; //�Ƿ�Ϊ��ȷ��

    public String getMchoice() {
        return mchoice;
    }

    public void setMchoice(String mchoice) {
        this.mchoice = mchoice;
    }

    public boolean isMvalid() {
        return mvalid;
    }

    public void setMvalid(boolean mvalid) {
        this.mvalid = mvalid;
    }

    @Override
    public String toString() {
        return "MCQChoice{" +
                "mchoice='" + mchoice + '\'' +
                ", mvalid=" + mvalid +
                '}';
    }
}
