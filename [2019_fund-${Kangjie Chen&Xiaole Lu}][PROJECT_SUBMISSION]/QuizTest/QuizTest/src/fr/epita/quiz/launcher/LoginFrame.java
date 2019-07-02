package fr.epita.quiz.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.data.*;


public class LoginFrame extends JFrame {
    JPanel mb;
    JLabel lb1, lb2;
    JButton bt;
    JTextField username;
    JPasswordField password;

    ButtonGroup btngroup;
    JRadioButton jrb_user, jrb_admin;
    public LoginFrame() {

    }

    public void buildLoginFrame(LoginFrame loginFrame) {
        mb = new JPanel();
        lb1 = new JLabel(new ImageIcon("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\image/loginbackgroud.jpeg"));//�ϲ���ͼƬ
        lb2 = new JLabel(new ImageIcon("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\image/backtouxiang.jpg"));//qqͷ�񲿷�
        username = new JTextField(20);
        password = new JPasswordField(20);

        bt = new JButton("Login");//���齨��ӵ���嵱��
        mb.add(lb2);

        mb.add(bt);
        mb.add(username);
        mb.add(password);
        mb.setSize(540, 190);//�����Ĳ�����Ϊnull��Ȼ���Զ��岼��
        mb.setLayout(null);
        mb.setBackground(Color.white);
        lb2.setBounds(43, 8, 100, 100);
        username.setBounds(160, 14, 250, 37);
        username.setFont(new Font("����", Font.PLAIN, 16));
        password.setBounds(160, 48, 250, 37);
        password.setFont(new Font("����", Font.PLAIN, 16));

        btngroup = new ButtonGroup();
        jrb_user = new JRadioButton("Student", true);
        jrb_user.setBounds(160, 90, 120, 20);
        jrb_admin = new JRadioButton("Admin");
        jrb_admin.setBounds(320, 90, 120, 20);
        btngroup.add(jrb_user);
        btngroup.add(jrb_admin);

        mb.add(jrb_admin);
        mb.add(jrb_user);

        bt.setBounds(160, 130, 250, 37);
        bt.setFont(new Font("����", Font.PLAIN, 16));
        bt.setBackground(new Color(0, 178, 238));
        bt.setForeground(Color.BLACK);

        bt.addActionListener(new LoginAction());

        loginFrame.add(lb1, BorderLayout.NORTH);
        loginFrame.add(mb, BorderLayout.CENTER);
        loginFrame.setSize(540, 440);
        loginFrame.setTitle("User Login");
        loginFrame.setLocation(620, 280);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
    }

    /**
     * ��½�¼� jrb_admin ����Աѡ�� jrb_user��ͨ�û�
     */
    class LoginAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            checkLogin();
        }
    }

    /**
     * �ͷŵ�ǰ���󷽷�
     */
    public void disPose() {
        this.dispose();
    }

    /**
     * �û���֤
     */
    public void checkLogin() {
        try {
            StudentJDBCDAO userJDBCDAO = new StudentJDBCDAO();
            Student user = new Student();
            user.setUname(username.getText());

            char[] values = password.getPassword();
            String password = new String(values);

            user.setUpassword(password);

            if (jrb_admin.isSelected()) {
                user.setUlevel(1);
            } else {
                user.setUlevel(2);
            }
            List<Student> userList = userJDBCDAO.queryStudentByNameAndPassword(user);

            if (null!=userList && userList.size()>0) {
                String level = String.valueOf(userList.get(0).getUlevel());
                String name = userList.get(0).getUname();
                verifyLogin(level,name);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong user name or password or match!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��½�ɹ�����
     */
    public void verifyLogin(String level,String name) {
        if (level.equals("1")) { //����Ա
            if (jrb_admin.isSelected()) {
                AdminFrame admin = new AdminFrame(name);
                admin.goAdmin();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "You are admin,cannot attend the exam!", "Warning",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else { //��ͨ�û�
            if (jrb_user.isSelected()) {
                FrontFrame frontFrame = new FrontFrame();
                disPose();
            } else {
                JOptionPane.showMessageDialog(null, "Permissions cross-border", "Warning",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        LoginFrame login = new LoginFrame();
        login.buildLoginFrame(login);
    }
}