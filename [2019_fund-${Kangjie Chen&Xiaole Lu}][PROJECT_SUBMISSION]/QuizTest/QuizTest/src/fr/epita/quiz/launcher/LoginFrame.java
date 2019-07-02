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
        lb1 = new JLabel(new ImageIcon("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\image/loginbackgroud.jpeg"));//上部分图片
        lb2 = new JLabel(new ImageIcon("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\image/backtouxiang.jpg"));//qq头像部分
        username = new JTextField(20);
        password = new JPasswordField(20);

        bt = new JButton("Login");//将组建添加到面板当中
        mb.add(lb2);

        mb.add(bt);
        mb.add(username);
        mb.add(password);
        mb.setSize(540, 190);//将面板的布局设为null，然后自定义布局
        mb.setLayout(null);
        mb.setBackground(Color.white);
        lb2.setBounds(43, 8, 100, 100);
        username.setBounds(160, 14, 250, 37);
        username.setFont(new Font("宋体", Font.PLAIN, 16));
        password.setBounds(160, 48, 250, 37);
        password.setFont(new Font("宋体", Font.PLAIN, 16));

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
        bt.setFont(new Font("宋体", Font.PLAIN, 16));
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
     * 登陆事件 jrb_admin 管理员选择 jrb_user普通用户
     */
    class LoginAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            checkLogin();
        }
    }

    /**
     * 释放当前对象方法
     */
    public void disPose() {
        this.dispose();
    }

    /**
     * 用户验证
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
     * 登陆成功方法
     */
    public void verifyLogin(String level,String name) {
        if (level.equals("1")) { //管理员
            if (jrb_admin.isSelected()) {
                AdminFrame admin = new AdminFrame(name);
                admin.goAdmin();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "You are admin,cannot attend the exam!", "Warning",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else { //普通用户
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