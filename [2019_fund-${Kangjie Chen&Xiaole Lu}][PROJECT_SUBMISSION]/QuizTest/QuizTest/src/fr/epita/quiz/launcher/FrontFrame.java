package fr.epita.quiz.launcher;

import fr.epita.quiz.services.util.PDFReport;
import fr.epita.quiz.services.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FrontFrame extends JFrame implements ActionListener, ItemListener {

    // 设置菜单
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem item;

    /*
     * 测试请注意路径
     */
    //private String path1 = "data/Java.txt";
    //private String path2 = "data/C++.txt";// 考试题存放路径
    private String path[] = new String[100]; // 考试题存放路径

    private int sum = 7200; // 总时间
    private Timer timer;
    // 读取文件相关
    File file = null;
    FileReader reader = null;
    BufferedReader rBufferedReader = null;
    int score = 0;
    String result = null;
    boolean firstRead = true;  //控制stopOrStart计时读取文件
    int number = 1; // 默认题号是1
    int correct = 0;
    StringBuffer grade = new StringBuffer(); // 成绩结果字符串
    boolean hascommitted = false;

    private JLabel label = new JLabel("Welcome, please select the type of questions first!");
    private JLabel label_select = new JLabel("Select question type");
    private JLabel label_content = new JLabel("Test content：");

    private JTextArea[] areas = { // 声明文本区
            new JTextArea(), new JTextArea()};

    // jsp 是分数文本区， jsp1是试题文本区
    private JScrollPane jsp = new JScrollPane(areas[0]); // 将areas[0]添加到滚动窗体
    private JScrollPane jsp1 = new JScrollPane(areas[1]);

    String[] str = {"Java", "C++"}; //默认值
    private JComboBox comB = new JComboBox(str); // 创建下拉列表项

    private JButton[] btn = { // 定义按钮数组
            new JButton("Submit"), new JButton("The next"), new JButton("Start"),
            new JButton("View Score"),new JButton("Export test paper")};

    private JPanel panel = new JPanel(); // 用来存放复选框
    private JCheckBox[] mCheckBoxs = {new JCheckBox("A"), new JCheckBox("B"),
            new JCheckBox("C"), new JCheckBox("D")};

    private JLabel jLabel2 = new JLabel(); // 定义计时器处的JLabel
    /*
     * 计时器控件的设置
     */
    private JPanel panel_timer = new JPanel();
    private JLabel timer_hour = new JLabel();
    private JLabel timer_mintue = new JLabel();
    private JLabel timer_second = new JLabel();

    private String tempContent= ""; //  每个题目的内容

    public FrontFrame() {
        try {
            //生成试卷
            new ExamQuestionJDBCDAO().buildExamFile();
            //生成试卷下拉菜单
            List<String> qTopics = new ExamQuestionJDBCDAO().queryQuestionTopics();
            int i=0;
            if (qTopics != null && qTopics.size() > 0) {
                for (String qname : qTopics) {
                    String fileName = "C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\data/";
                    fileName += qname;
                    fileName += "1.txt";
                    /*if(i==0){
                        path1 = fileName;
                    }
                    else if(i==1){
                        path2 = fileName;
                    }*/
                    path[i] = fileName;
                    //初始化
                    str[i++] = qname;
                    System.out.println(qname);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.setLayout(null); // 设置布局为空布局
        /*
         * JLabel的设置
         */
        label.setForeground(Color.BLUE); // 设置字体的颜色
        label.setBounds(200, 10, 500, 30); // 设置Jlabel的起始位置和大小
        label.setFont(new Font("Courier", Font.PLAIN, 20)); // 设置字体大小
        this.add(label);

        /*
         * JcomboBox单选框的设置
         */
        label_select.setBounds(130, 50, 150, 15);
        this.add(label_select);
        comB.setBounds(5, 80, 290, 20); // 设置单选框的大小位置
        this.add(comB);
        comB.addActionListener(this); // 对单选框添加监听事件

        label_content.setBounds(500, 50, 100, 15);
        this.add(label_content);

        /*
         * 对文本区域进行设置
         */
        for (int i = 0; i < 2; i++) {
            areas[i].setLineWrap(true); // 设置其自动换行
            // areas[i].append(""); //将给定文本追加到文档结尾
        }

        /*
         * 对滚动窗体的大小和位置进行设置
         */
        jsp.setBounds(5, 120, 290, 180);
        this.add(jsp);
        jsp1.setBounds(300, 70, 490, 300);
        this.add(jsp1);

        /*
         * 导出答案解析
         */
        btn[4].setBounds(10, 305, 150, 25);
        this.add(btn[4]);
        btn[4].addActionListener(this);
        /*
         * 查看分数按钮的设置
         */
        btn[3].setBounds(165, 305, 100, 25);
        btn[3].setForeground(Color.BLUE); // 设置按钮上字体的颜色
        this.add(btn[3]);
        btn[3].addActionListener(this);

        /*
         * 提交答案和读取下一题按钮的设置
         */
        for (int i = 0; i < 2; i++) {
            btn[i].setBounds(540 + i * 130, 400, 120, 25);
            btn[i].setForeground(Color.BLUE);
            this.add(btn[i]);
            btn[i].setEnabled(false); // 设置按钮补课操作
            btn[i].addActionListener(this);
        }

        /*
         * 开始答题和暂停计时按钮的设置
         */
        btn[2].setBounds(100, 430, 100, 25);
        this.add(btn[2]);
        btn[2].setBorderPainted(false); // 去掉按钮边框
        btn[2].addActionListener(this);
        btn[2].setEnabled(false);

        /*
         * 计时器JLabel的设置
         */
        jLabel2.setBounds(150, 380, 100, 30);
        this.add(jLabel2);
        /*
         * 复选框的设置
         */
        this.add(panel);
        mCheckBoxs[0].addItemListener(this); // 设置监听时间
        mCheckBoxs[1].addItemListener(this);
        mCheckBoxs[2].addItemListener(this);
        mCheckBoxs[3].addItemListener(this);
        panel.add(mCheckBoxs[0]);
        panel.add(mCheckBoxs[1]);
        panel.add(mCheckBoxs[2]);
        panel.add(mCheckBoxs[3]);
        // 并且设置起始状态为不可操作
        for (int i = 0; i < mCheckBoxs.length; i++) {
            mCheckBoxs[i].setEnabled(false);
        }
        panel.setBounds(280, 395, 300, 80);
        // panel.setBackground(Color.BLUE);

        /*
         * 窗体的设置
         */
        this.setResizable(false);// 设置窗体大小不可改变
        this.setBounds(250, 150, 800, 530); // 设置窗体的大小和位置
        this.setTitle("Standardized mock exam");
        this.setVisible(true); // 设置窗体的可见性
        this.validate();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置退出
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        

        /*
         * 下拉列表的监听事件设置
         */
        Object source = e.getSource();
        if (source == comB) {
            btn[2].setEnabled(true);
            String s = "Please click to start, the question you selected is：";
            int m;
            m = comB.getSelectedIndex(); // 获得所选下拉列表项的角标，根据角标得到内容
            System.out.println(m);
            s = s + str[m];
            /*if (m == 0) {
                file = new File(path1);
            } else if (m == 1) {
                file = new File(path2);
            }*/
            file = new File(path[m]);
            System.out.println(s);
            label.setText(s); // 重新设置JLabel
        }

        /*
         * 提交答案按钮和读取下一题按钮的设置
         */
        /**
         * 开始计时按钮和暂停按钮，开始计时读取文件
         */
        // btn[0].setEnabled(true);
        // this.getTime(); // 调用计时器
        if (e.getSource() == btn[2] && btn[2].getText().equals("Start")) {
            System.out.println("Start");
            timeThread(); // 点击开始计时按钮，启动倒计时
            // 复选框按钮显示可见
            for (int i = 0; i < mCheckBoxs.length; i++) {
                mCheckBoxs[i].setEnabled(true);
            }
            // 读取文件
            if (firstRead) {
                try {
                    reader = new FileReader(file);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                rBufferedReader = new BufferedReader(reader);
                dealtext();
                firstRead = false;
            }
            if (hascommitted) {
                btn[0].setEnabled(false);
                btn[1].setEnabled(true);
            } else {
                btn[0].setEnabled(true);// 提交答案按钮显示可见
            }
            btn[2].setText("Pause");
            requestFocus();
        } else if (e.getSource() == btn[2] && btn[2].getText().equals("Pause")) {
            System.out.println("Pause");
            if (timer != null) {
                timer.cancel(); // 暂停计时
                timer = null;
            }
            for (int i = 0; i < mCheckBoxs.length; i++) {
                mCheckBoxs[i].setEnabled(false);
            }
            btn[0].setEnabled(false);
            btn[1].setEnabled(false);
            btn[2].setText("Start");
        }
        if (e.getSource() == btn[0]) { // 提交答案按钮的设置
            btn[0].setEnabled(false);
            btn[1].setEnabled(true);
            hascommitted = true;
            if(number<5) {  // 前面4个题目为多项选择题  最后1个题目为开放题目
                // 多项选择题
                if (mCheckBoxs[0].isSelected()) {
                    checkBoxsResult(0);
                    JOptionPane.showMessageDialog(this, "You have chosen A", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (mCheckBoxs[1].isSelected()) {
                    checkBoxsResult(1);
                    JOptionPane.showMessageDialog(this, "You have chosen B", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (mCheckBoxs[2].isSelected()) {
                    checkBoxsResult(2);
                    JOptionPane.showMessageDialog(this, "You have chosen C", "Prompt message息",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (mCheckBoxs[3].isSelected()) {
                    checkBoxsResult(3);
                    JOptionPane.showMessageDialog(this, "You have chosen D", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    btn[0].setEnabled(true);
                    btn[1].setEnabled(false);
                    JOptionPane.showMessageDialog(this, "You have not chosen an answer yet！ ", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                //开放性试题
                checkOpenResult();
                JOptionPane.showMessageDialog(this, "Open topic answer submitted successfully！ ", "Prompt message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (e.getSource() == btn[1]) { // 读取下一题按钮的设置
            btn[1].setEnabled(false);
            btn[0].setEnabled(true);
            hascommitted = false;
            if (number <5){
                for (int i = 0; i < mCheckBoxs.length; i++) {
                    mCheckBoxs[i].setEnabled(true);
                    mCheckBoxs[i].setSelected(false);
                }
            }
            dealtext();
        }
        if (number==5&&e.getSource() == btn[3]) {// 查看分数按钮
            areas[0].setText(grade.toString());
        }
        if (number==5&&e.getSource() == btn[4]) {// 导出答案解析按钮
            try{
                String data = grade.toString();
                PDFReport.writeSimplePdf(data); //导出试卷答案解析
                JOptionPane.showMessageDialog(this, "Successfully export test paper answer analysis", "Prompt message",
                        JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 处理读取的text文件内容
     */
    private void dealtext() {
        String lineString = null;
        StringBuffer content = new StringBuffer();
        try {
            while ((lineString = rBufferedReader.readLine()) != null) {
                content.append(lineString + "\n");
                if (lineString.equals("*****")) {
                    content.delete(content.length() - 6, content.length());
                    areas[1].setText(content.toString());
                    result = rBufferedReader.readLine();
                    tempContent =content.toString();
                    return;
                }
                if (lineString.equals("#####")) {
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    btn[2].setEnabled(false);
                    JOptionPane.showMessageDialog(this, "You have completed all the questions", "Congratulations：",
                            JOptionPane.INFORMATION_MESSAGE);
                    areas[1].setText("You should have completed all the questions, please click on the score to view the test results.");
                    areas[1].setFont(new Font("Courier", Font.CENTER_BASELINE,
                            16));
                    grade.append("You're all right " + correct + " problem\n" + "finish score is：" + correct);
                    btn[0].setEnabled(false);
                    btn[1].setEnabled(false);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //开放性题目  为送分题  答题目后都有分数
    private void checkOpenResult() {
        String answerStr = result;
        grade.append("The " + number + " problem\n");
        grade.append( areas[1].getText());

        grade.append( "\nanswer is:" + answerStr);

        score += 1;
        correct += 1;
        grade.append("\nyou are right!\n");
        grade.append("score is：" + score + "\n\n");
    }

    private void checkBoxsResult(int j) {
        // 提交答案后，复选框暂时不能操作
        for (int i = 0; i < mCheckBoxs.length; i++) {
            mCheckBoxs[i].setEnabled(false);
        }
        String answer = null;
        switch (j) {
            case 0:
                answer = "A";
                break;
            case 1:
                answer = "B";
                break;
            case 2:
                answer = "C";
                break;
            case 3:
                answer = "D";
                break;

            default:
                break;
        }
        String answerStr = result.substring(7, 8);
        grade.append("The " + number  + " problem\n");
        grade.append(tempContent);

        grade.append( "your choice is:"
                + answer.toUpperCase() + "\nanswer is:" + answerStr);
        if (answerStr.equalsIgnoreCase(answer)) {
            score += 1;
            correct += 1;
            grade.append("\nyou are right!\n");
            grade.append("score is：" + score + "\n\n");
        } else {
            grade.append("\nwrong answer!\n");
            grade.append("score is: " + score + "\n\n");
        }
        number++;
    }

    public static void main(String[] args) {
        new FrontFrame();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void timeThread() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int hour = sum / 3600;
                int fen = (sum % 3600) / 60;
                int t = sum % 60;
                if (hour > 0 || fen > 0 || t > 0) {
                    if (0 == t && fen >= 0) {
                        t = 59;

                        if (0 == fen && hour > 0) {
                            fen = 59;
                            hour--;
                        } else {
                            fen--;
                        }
                    } else {
                        t--;
                    }
                    sum--;
                    jLabel2.setText(hour + "小时" + fen + "分" + t + "秒");
                }
            }
        }, 0, 1000);
    }

}

