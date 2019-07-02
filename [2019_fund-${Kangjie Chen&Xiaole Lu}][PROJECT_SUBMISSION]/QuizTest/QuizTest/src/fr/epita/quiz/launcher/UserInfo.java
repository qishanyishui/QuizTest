package fr.epita.quiz.launcher;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfo extends JPanel
{
    JTextField inputId,inputUserName,inputPassWord,inputLevel;
    JButton submit;
	public UserInfo() {
		setLayout(null);
		submit=new JButton("submit");
		inputId=new JTextField(60);
		inputUserName=new JTextField(60);
		inputPassWord=new JTextField(60);
		inputLevel=new JTextField(60);
		JLabel id=new JLabel("ID");
		add(inputId);
		add(id);
		JLabel name=new JLabel("User Name");
		add(inputUserName);
		add(name);
		JLabel pass=new JLabel("Password");
		add(inputPassWord);
		add(pass);
		JLabel le=new JLabel("Level");
		add(inputLevel);
		add(le);
		add(submit);
		inputId.setBounds(10,20,300,25);
		id.setBounds(325,20,50,25);
		inputUserName.setBounds(10,50,300,25);
		name.setBounds(325,50,70,25);
		inputPassWord.setBounds(10,80,300,25);
		pass.setBounds(325,80,70,25);
		inputLevel.setBounds(10,110,300,25);
		le.setBounds(325,110,50,25);
		submit.setBounds(100,140,100,25);

		submit.addActionListener(new actionSubmitButton());
	}
	//添加
	class actionSubmitButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Student user = new Student();
			user.setUnumber(inputId.getText());
			user.setUname(inputUserName.getText());
			user.setUpassword(inputPassWord.getText());
			int level = 0;
			boolean flag = true;
			try{
				level = Integer.parseInt(inputLevel.getText());
			}catch (Exception ex){
				flag = false;
				JOptionPane.showMessageDialog(null, "请输入数字1或者2,代表老师和学生!", "消息", JOptionPane.NO_OPTION);
			}

			if(flag){
				user.setUlevel(level);
				try{
					StudentJDBCDAO studentJDBCDAO = new StudentJDBCDAO();
					studentJDBCDAO.insert(user);
					inputId.setText("");
					inputUserName.setText("");
					inputPassWord.setText("");
					inputLevel.setText("");
					JOptionPane.showMessageDialog(null, "添加记录成功!", "提示", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e0){
					JOptionPane.showMessageDialog(null, "添加记录失敗!", "提示", JOptionPane.ERROR_MESSAGE);
					e0.printStackTrace();
				}
			}
		}
	}
}
