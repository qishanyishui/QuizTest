package fr.epita.quiz.datamodel;

public class StudentGrade {
    private int sid;
    private Student student;
    private int sgrade; //成绩
    private int total; //当次考试的成绩

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getSgrade() {
        return sgrade;
    }

    public void setSgrade(int sgrade) {
        this.sgrade = sgrade;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "sid=" + sid +
                ", student=" + student +
                ", sgrade=" + sgrade +
                ", total=" + total +
                '}';
    }
}
