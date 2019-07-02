package fr.epita.quiz.datamodel;

public class MCQChoice {
    private String mchoice; //选择项
    private boolean mvalid; //是否为正确答案

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
