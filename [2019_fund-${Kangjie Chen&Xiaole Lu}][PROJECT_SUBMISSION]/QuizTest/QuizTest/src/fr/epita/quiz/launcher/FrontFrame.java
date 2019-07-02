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

    // ���ò˵�
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem item;

    /*
     * ������ע��·��
     */
    //private String path1 = "data/Java.txt";
    //private String path2 = "data/C++.txt";// ��������·��
    private String path[] = new String[100]; // ��������·��

    private int sum = 7200; // ��ʱ��
    private Timer timer;
    // ��ȡ�ļ����
    File file = null;
    FileReader reader = null;
    BufferedReader rBufferedReader = null;
    int score = 0;
    String result = null;
    boolean firstRead = true;  //����stopOrStart��ʱ��ȡ�ļ�
    int number = 1; // Ĭ�������1
    int correct = 0;
    StringBuffer grade = new StringBuffer(); // �ɼ�����ַ���
    boolean hascommitted = false;

    private JLabel label = new JLabel("Welcome, please select the type of questions first!");
    private JLabel label_select = new JLabel("Select question type");
    private JLabel label_content = new JLabel("Test content��");

    private JTextArea[] areas = { // �����ı���
            new JTextArea(), new JTextArea()};

    // jsp �Ƿ����ı����� jsp1�������ı���
    private JScrollPane jsp = new JScrollPane(areas[0]); // ��areas[0]��ӵ���������
    private JScrollPane jsp1 = new JScrollPane(areas[1]);

    String[] str = {"Java", "C++"}; //Ĭ��ֵ
    private JComboBox comB = new JComboBox(str); // ���������б���

    private JButton[] btn = { // ���尴ť����
            new JButton("Submit"), new JButton("The next"), new JButton("Start"),
            new JButton("View Score"),new JButton("Export test paper")};

    private JPanel panel = new JPanel(); // ������Ÿ�ѡ��
    private JCheckBox[] mCheckBoxs = {new JCheckBox("A"), new JCheckBox("B"),
            new JCheckBox("C"), new JCheckBox("D")};

    private JLabel jLabel2 = new JLabel(); // �����ʱ������JLabel
    /*
     * ��ʱ���ؼ�������
     */
    private JPanel panel_timer = new JPanel();
    private JLabel timer_hour = new JLabel();
    private JLabel timer_mintue = new JLabel();
    private JLabel timer_second = new JLabel();

    private String tempContent= ""; //  ÿ����Ŀ������

    public FrontFrame() {
        try {
            //�����Ծ�
            new ExamQuestionJDBCDAO().buildExamFile();
            //�����Ծ������˵�
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
                    //��ʼ��
                    str[i++] = qname;
                    System.out.println(qname);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.setLayout(null); // ���ò���Ϊ�ղ���
        /*
         * JLabel������
         */
        label.setForeground(Color.BLUE); // �����������ɫ
        label.setBounds(200, 10, 500, 30); // ����Jlabel����ʼλ�úʹ�С
        label.setFont(new Font("Courier", Font.PLAIN, 20)); // ���������С
        this.add(label);

        /*
         * JcomboBox��ѡ�������
         */
        label_select.setBounds(130, 50, 150, 15);
        this.add(label_select);
        comB.setBounds(5, 80, 290, 20); // ���õ�ѡ��Ĵ�Сλ��
        this.add(comB);
        comB.addActionListener(this); // �Ե�ѡ����Ӽ����¼�

        label_content.setBounds(500, 50, 100, 15);
        this.add(label_content);

        /*
         * ���ı������������
         */
        for (int i = 0; i < 2; i++) {
            areas[i].setLineWrap(true); // �������Զ�����
            // areas[i].append(""); //�������ı�׷�ӵ��ĵ���β
        }

        /*
         * �Թ�������Ĵ�С��λ�ý�������
         */
        jsp.setBounds(5, 120, 290, 180);
        this.add(jsp);
        jsp1.setBounds(300, 70, 490, 300);
        this.add(jsp1);

        /*
         * �����𰸽���
         */
        btn[4].setBounds(10, 305, 150, 25);
        this.add(btn[4]);
        btn[4].addActionListener(this);
        /*
         * �鿴������ť������
         */
        btn[3].setBounds(165, 305, 100, 25);
        btn[3].setForeground(Color.BLUE); // ���ð�ť���������ɫ
        this.add(btn[3]);
        btn[3].addActionListener(this);

        /*
         * �ύ�𰸺Ͷ�ȡ��һ�ⰴť������
         */
        for (int i = 0; i < 2; i++) {
            btn[i].setBounds(540 + i * 130, 400, 120, 25);
            btn[i].setForeground(Color.BLUE);
            this.add(btn[i]);
            btn[i].setEnabled(false); // ���ð�ť���β���
            btn[i].addActionListener(this);
        }

        /*
         * ��ʼ�������ͣ��ʱ��ť������
         */
        btn[2].setBounds(100, 430, 100, 25);
        this.add(btn[2]);
        btn[2].setBorderPainted(false); // ȥ����ť�߿�
        btn[2].addActionListener(this);
        btn[2].setEnabled(false);

        /*
         * ��ʱ��JLabel������
         */
        jLabel2.setBounds(150, 380, 100, 30);
        this.add(jLabel2);
        /*
         * ��ѡ�������
         */
        this.add(panel);
        mCheckBoxs[0].addItemListener(this); // ���ü���ʱ��
        mCheckBoxs[1].addItemListener(this);
        mCheckBoxs[2].addItemListener(this);
        mCheckBoxs[3].addItemListener(this);
        panel.add(mCheckBoxs[0]);
        panel.add(mCheckBoxs[1]);
        panel.add(mCheckBoxs[2]);
        panel.add(mCheckBoxs[3]);
        // ����������ʼ״̬Ϊ���ɲ���
        for (int i = 0; i < mCheckBoxs.length; i++) {
            mCheckBoxs[i].setEnabled(false);
        }
        panel.setBounds(280, 395, 300, 80);
        // panel.setBackground(Color.BLUE);

        /*
         * ���������
         */
        this.setResizable(false);// ���ô����С���ɸı�
        this.setBounds(250, 150, 800, 530); // ���ô���Ĵ�С��λ��
        this.setTitle("Standardized mock exam");
        this.setVisible(true); // ���ô���Ŀɼ���
        this.validate();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �����˳�
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        

        /*
         * �����б�ļ����¼�����
         */
        Object source = e.getSource();
        if (source == comB) {
            btn[2].setEnabled(true);
            String s = "Please click to start, the question you selected is��";
            int m;
            m = comB.getSelectedIndex(); // �����ѡ�����б���ĽǱ꣬���ݽǱ�õ�����
            System.out.println(m);
            s = s + str[m];
            /*if (m == 0) {
                file = new File(path1);
            } else if (m == 1) {
                file = new File(path2);
            }*/
            file = new File(path[m]);
            System.out.println(s);
            label.setText(s); // ��������JLabel
        }

        /*
         * �ύ�𰸰�ť�Ͷ�ȡ��һ�ⰴť������
         */
        /**
         * ��ʼ��ʱ��ť����ͣ��ť����ʼ��ʱ��ȡ�ļ�
         */
        // btn[0].setEnabled(true);
        // this.getTime(); // ���ü�ʱ��
        if (e.getSource() == btn[2] && btn[2].getText().equals("Start")) {
            System.out.println("Start");
            timeThread(); // �����ʼ��ʱ��ť����������ʱ
            // ��ѡ��ť��ʾ�ɼ�
            for (int i = 0; i < mCheckBoxs.length; i++) {
                mCheckBoxs[i].setEnabled(true);
            }
            // ��ȡ�ļ�
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
                btn[0].setEnabled(true);// �ύ�𰸰�ť��ʾ�ɼ�
            }
            btn[2].setText("Pause");
            requestFocus();
        } else if (e.getSource() == btn[2] && btn[2].getText().equals("Pause")) {
            System.out.println("Pause");
            if (timer != null) {
                timer.cancel(); // ��ͣ��ʱ
                timer = null;
            }
            for (int i = 0; i < mCheckBoxs.length; i++) {
                mCheckBoxs[i].setEnabled(false);
            }
            btn[0].setEnabled(false);
            btn[1].setEnabled(false);
            btn[2].setText("Start");
        }
        if (e.getSource() == btn[0]) { // �ύ�𰸰�ť������
            btn[0].setEnabled(false);
            btn[1].setEnabled(true);
            hascommitted = true;
            if(number<5) {  // ǰ��4����ĿΪ����ѡ����  ���1����ĿΪ������Ŀ
                // ����ѡ����
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
                    JOptionPane.showMessageDialog(this, "You have chosen C", "Prompt messageϢ",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (mCheckBoxs[3].isSelected()) {
                    checkBoxsResult(3);
                    JOptionPane.showMessageDialog(this, "You have chosen D", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    btn[0].setEnabled(true);
                    btn[1].setEnabled(false);
                    JOptionPane.showMessageDialog(this, "You have not chosen an answer yet�� ", "Prompt message",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                //����������
                checkOpenResult();
                JOptionPane.showMessageDialog(this, "Open topic answer submitted successfully�� ", "Prompt message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (e.getSource() == btn[1]) { // ��ȡ��һ�ⰴť������
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
        if (number==5&&e.getSource() == btn[3]) {// �鿴������ť
            areas[0].setText(grade.toString());
        }
        if (number==5&&e.getSource() == btn[4]) {// �����𰸽�����ť
            try{
                String data = grade.toString();
                PDFReport.writeSimplePdf(data); //�����Ծ�𰸽���
                JOptionPane.showMessageDialog(this, "Successfully export test paper answer analysis", "Prompt message",
                        JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * �����ȡ��text�ļ�����
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
                    JOptionPane.showMessageDialog(this, "You have completed all the questions", "Congratulations��",
                            JOptionPane.INFORMATION_MESSAGE);
                    areas[1].setText("You should have completed all the questions, please click on the score to view the test results.");
                    areas[1].setFont(new Font("Courier", Font.CENTER_BASELINE,
                            16));
                    grade.append("You're all right " + correct + " problem\n" + "finish score is��" + correct);
                    btn[0].setEnabled(false);
                    btn[1].setEnabled(false);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //��������Ŀ  Ϊ�ͷ���  ����Ŀ���з���
    private void checkOpenResult() {
        String answerStr = result;
        grade.append("The " + number + " problem\n");
        grade.append( areas[1].getText());

        grade.append( "\nanswer is:" + answerStr);

        score += 1;
        correct += 1;
        grade.append("\nyou are right!\n");
        grade.append("score is��" + score + "\n\n");
    }

    private void checkBoxsResult(int j) {
        // �ύ�𰸺󣬸�ѡ����ʱ���ܲ���
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
            grade.append("score is��" + score + "\n\n");
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
                    jLabel2.setText(hour + "Сʱ" + fen + "��" + t + "��");
                }
            }
        }, 0, 1000);
    }

}

