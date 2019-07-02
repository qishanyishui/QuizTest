package fr.epita.quiz.launcher;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.services.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MCQQuestionInfo extends JPanel
{
	JTextField inputOptionA,inputOptionB,inputOptionC,inputOptionD,comValue,inputTopic,inputContent,inputAnswerAna;
	JCheckBox optionA,optionB,optionC,optionD;
	JButton submit;

	public MCQQuestionInfo() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		submit=new JButton("submit");
		inputContent=new JTextField(50);
		inputOptionA=new JTextField(50);
		inputOptionB=new JTextField(50);
		inputOptionC=new JTextField(50);
		inputOptionD=new JTextField(50);
		comValue=new JTextField(50);
		inputTopic=new JTextField(50);
		inputAnswerAna=new JTextField(50);
		optionA=new JCheckBox("A");
		optionB=new JCheckBox("B");
		optionC=new JCheckBox("C");
		optionD=new JCheckBox("D");
		JLabel qusContent=new JLabel("Content");
		add(inputContent);
		add(qusContent);
		JLabel opA=new JLabel("Option A");
		add(inputOptionA);
		add(opA);
		JLabel opB=new JLabel("Option B");
		add(inputOptionB);
		add(opB);
		JLabel opC=new JLabel("Option C");
		add(inputOptionC);
		add(opC);
		JLabel opD=new JLabel("Option D");
		add(inputOptionD);
		add(opD);
		JLabel complex=new JLabel("Complexity");
		add(comValue);
		add(complex);
		JLabel top=new JLabel("Topic");
		add(inputTopic);
		add(top);
		JLabel ana=new JLabel("Analyze");
		add(inputAnswerAna);
		add(ana);
		add(optionA);
		add(optionB);
		add(optionC);
		add(optionD);
		JLabel rightAnswer=new JLabel("Right answer");
		add(rightAnswer);
		add(submit);
		inputContent.setBounds(5,35,400,35);
		qusContent.setBounds(415,35,150,35);
		inputOptionA.setBounds(5,75,400,35);
		opA.setBounds(415,75,150,35);
		inputOptionB.setBounds(5,115,400,35);
		opB.setBounds(415,115,150,35);
		inputOptionC.setBounds(5,155,400,35);
		opC.setBounds(415,155,150,35);
		inputOptionD.setBounds(5,195,400,35);
		opD.setBounds(415,195,150,35);
		comValue.setBounds(5,235,400,35);
		complex.setBounds(415,235,150,35);
		inputTopic.setBounds(5,275,400,35);
		top.setBounds(415,275,150,35);
		inputAnswerAna.setBounds(5,315,400,35);
		ana.setBounds(415,315,150,35);
		optionA.setBounds(55,355,35,35);
		optionB.setBounds(115,355,35,35);
		optionC.setBounds(175,355,35,35);
		optionD.setBounds(235,355,35,35);
		rightAnswer.setBounds(555,355,150,35);
		submit.setBounds(250,395,150,35);

		submit.addActionListener(new actionSubmitButton());
	}
	//Ìí¼Ó
	class actionSubmitButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MCQQuestion mcqQuestion = new MCQQuestion();
			Question question = new Question();
			question.setContent(inputContent.getText());

			int comNumber = 0;
			boolean flag = true;
			try{
				comNumber = Integer.valueOf(comValue.getText());
			}catch (Exception ex){
				flag = false;
				JOptionPane.showMessageDialog(null, "Please enter the number 1~6, which represents the complexity of the problem.!", "Message", JOptionPane.NO_OPTION);
			}

			if(flag){
				question.setDifficulty(comNumber);
				String qtopics[] = new String[1];
				qtopics[0] = inputTopic.getText();
				question.setTopics(qtopics);
				mcqQuestion.setQuestion(question);

				Answer answer = new Answer();
				answer.setText(inputAnswerAna.getText());
				mcqQuestion.setAnswer(answer);

				List<MCQChoice> mcqChoiceList = new ArrayList<>();
				MCQChoice mcqChoiceA = new MCQChoice();
				mcqChoiceA.setMchoice(inputOptionA.getText());
				if (optionA.getText().equals("A")&&optionA.isSelected()){
					mcqChoiceA.setMvalid(true);
				}
				mcqChoiceList.add(mcqChoiceA);

				MCQChoice mcqChoiceB = new MCQChoice();
				mcqChoiceB.setMchoice(inputOptionB.getText());
				if (optionB.getText().equals("B")&&optionB.isSelected()){
					mcqChoiceB.setMvalid(true);
				}
				mcqChoiceList.add(mcqChoiceB);

				MCQChoice mcqChoiceC = new MCQChoice();
				mcqChoiceC.setMchoice(inputOptionC.getText());
				if (optionC.getText().equals("C")&&optionC.isSelected()){
					mcqChoiceC.setMvalid(true);
				}
				mcqChoiceList.add(mcqChoiceC);

				MCQChoice mcqChoiceD = new MCQChoice();
				mcqChoiceD.setMchoice(inputOptionD.getText());
				if (optionD.getText().equals("D")&&optionD.isSelected()){
					mcqChoiceD.setMvalid(true);
				}
				mcqChoiceList.add(mcqChoiceD);

				mcqQuestion.setMcqChoice(mcqChoiceList);
				try{
					MCQQuestionJDBCDAO mcqQuestionJDBCDAO = new MCQQuestionJDBCDAO();
					mcqQuestionJDBCDAO.insert(mcqQuestion);

					inputContent.setText("");
					comValue.setText("");
					inputTopic.setText("");
					inputAnswerAna.setText("");
					inputOptionA.setText("");
					inputOptionB.setText("");
					inputOptionC.setText("");
					inputOptionD.setText("");

					JOptionPane.showMessageDialog(null, "Add record successfully!", "Prompt", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e0){
					JOptionPane.showMessageDialog(null, "Add record failed!", "Prompt", JOptionPane.ERROR_MESSAGE);
					e0.printStackTrace();
				}
			}
		}
	}
}
