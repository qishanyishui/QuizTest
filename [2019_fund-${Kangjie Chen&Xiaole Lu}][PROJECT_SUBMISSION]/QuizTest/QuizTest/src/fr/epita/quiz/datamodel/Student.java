package fr.epita.quiz.datamodel;

public class Student {
    private int uid;
    private String uname; //�û���
    private String upassword; //����
    private int ulevel; //�����Ƿ�Ϊ����Ա
    private String unumber; //ѧ��

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public int getUlevel() {
        return ulevel;
    }

    public void setUlevel(int ulevel) {
        this.ulevel = ulevel;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upassword='" + upassword + '\'' +
                ", ulevel=" + ulevel +
                ", unumber='" + unumber + '\'' +
                '}';
    }
}
