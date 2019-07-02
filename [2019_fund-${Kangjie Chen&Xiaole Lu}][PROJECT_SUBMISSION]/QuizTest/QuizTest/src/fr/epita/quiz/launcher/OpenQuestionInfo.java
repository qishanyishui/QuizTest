package fr.epita.quiz.launcher;

import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.data.OpenQuestionJDBCDAO;
import fr.epita.quiz.services.data.StudentJDBCDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenQuestionInfo extends JPanel
{
    JTextField complex,topc,answerAna,inputCentent;
    JButton submit;
	public OpenQuestionInfo() {
		setLayout(null);
		complex = new JTextField(30);
		topc = new JTextField(30);
		answerAna = new JTextField(30);
		inputCentent = new JTextField(30);

		submit=new JButton("submit");
		JLabel con=new JLabel("Content");
		add(inputCentent);
		add(con);
		JLabel comp=new JLabel("Complexity");
		add(complex);
		add(comp);
		JLabel topi=new JLabel("Topic");
		add(topc);
		add(topi);
		JLabel ana=new JLabel("Analyze");
		add(answerAna);
		add(ana);
		add(submit);
		inputCentent.setBounds(10,10,300,25);
		con.setBounds(330,10,100,25);
		complex.setBounds(10,40,300,25);
		comp.setBounds(330,40,100,25);
		topc.setBounds(10,70,300,25);
		topi.setBounds(330,70,100,25);
		answerAna.setBounds(10,100,300,25);
		ana.setBounds(330,100,100,25);
		submit.setBounds(100,140,80,25);
		submit.addActionListener(new actionSubmitButton());
	}
	//添加
	class actionSubmitButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OpenQuestion openQuestion = new OpenQuestion();
			openQuestion.setOqtext(answerAna.getText());

			Question question = new Question();
			question.setContent(inputCentent.getText());
			String qtopics[] = new String[1];
			qtopics[0] = topc.getText();
			question.setTopics(qtopics);

			int complexNumber = 0;
			boolean flag = true;
			try{
				complexNumber = Integer.valueOf(complex.getText());
			}catch (Exception ex){
				flag = false;
				JOptionPane.showMessageDialog(null, "请输入数字1~6,代表问题复杂度!", "消息", JOptionPane.NO_OPTION);
			}

			if(flag){
				question.setDifficulty(complexNumber);
				openQuestion.setQuestion(question);
				try{
					OpenQuestionJDBCDAO openQuestionJDBCDAO = new OpenQuestionJDBCDAO();
					openQuestionJDBCDAO.insert(openQuestion);
					answerAna.setText("");
					inputCentent.setText("");
					topc.setText("");
					complex.setText("");
					JOptionPane.showMessageDialog(null, "添加记录成功!", "提示", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e0){
					JOptionPane.showMessageDialog(null, "添加记录失敗!", "提示", JOptionPane.ERROR_MESSAGE);
					e0.printStackTrace();
				}
			}
		}
	}

}
