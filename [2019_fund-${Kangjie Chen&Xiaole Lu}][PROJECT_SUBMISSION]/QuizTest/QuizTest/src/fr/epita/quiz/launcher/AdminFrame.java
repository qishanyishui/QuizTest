package fr.epita.quiz.launcher;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

import fr.epita.quiz.services.data.*;
import fr.epita.quiz.datamodel.*;
/**
 * 管理员
 */
public class AdminFrame extends JFrame {
	JTabbedPane jtabbedpane;
	JPanel jp_user, jp_stu, jp_test, jp_user_top, jp_user_bottom, jp_bottom_info, jp_test_top, jp_test_top1,
			jp_test_top2, jp_test_bottom, jp_stu_top, jp_stu_bottom;
	JLabel jl_bottom_info, jl_name, jl_t_id, jl_add_tid, jl_add_tcontent, jl_add_tA, jl_add_tB, jl_add_tC, jl_add_tD,
			jl_add_tTrue, jl_stu_sea;
	JButton jbtn_search, jbtn_del, jbtn_t_search, jbtn_t_del, addTest, jbtn_stu_search, jbtn_stu_del;
	JTextField jtxf_search, jtxf_t_search, jtx_add_tid, jtx_add_tcontent, jtx_add_tA, jtx_add_tB, jtx_add_tC,
			jtx_add_tD, jtx_add_tTrue, jtx_stu_search;
	DefaultTableModel dtm, dtm_t, dtm_stu;
	JTable jtable_user, jtable_test, jtable_stu;
	Object[][] userinfo = null, testinfo = null, stuinfo = null;
	String[] table_user_head = { "ID", "User name", "Password", "Level" }, table_test_head = { "Topic", "Content", "Option A", "Option B", "Option C",
			"Option D", "Analyze" }, table_stu_head = { "Topic", "Content", "Answer" };
	String admin_name;
	JScrollPane jscroll_user_list, jscroll_test_list, jscroll_stu_list;

	public AdminFrame(String tempname) {
		this.admin_name = tempname;
		this.setTitle("Quiz Management System---"+tempname);
		this.setBounds(0, 0, 1024, 768);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	public void goAdmin() {
		jtabbedpane = new JTabbedPane();
		/**
		 * 用户区界面
		 */
		jp_user = new JPanel();
		jp_user.setLayout(null);
		jp_user_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_user_top.setBounds(5, 5, 1005, 250);
		jp_user_top.setBorder(BorderFactory.createTitledBorder(null, "Operation area", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		jl_name = new JLabel("SearchByName");
		jtxf_search = new JTextField(12);
		jbtn_search = new JButton("Search");
		jbtn_search.addActionListener(new actionButton());
		jbtn_del = new JButton("Delete");
		jbtn_del.addActionListener(new actionButton1());
		jp_user_top.add(jl_name);
		jp_user_top.add(jtxf_search);
		jp_user_top.add(jbtn_search);
		jp_user_top.add(jbtn_del);

		UserInfo userInfo = new UserInfo();
		userInfo.setBounds(5,50,1005, 200);
		jp_user.add(userInfo);

		jp_user_bottom = new JPanel(new BorderLayout());
		jp_user_bottom.setBounds(5, 260, 1005, 420);
		jp_user_bottom.setBorder(BorderFactory.createTitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		/**
		 * 用户表信息面板
		 */
		dtm = new DefaultTableModel(userinfo, table_user_head);
		jtable_user = new JTable(dtm);
		jtable_user.getSelectionModel().setSelectionMode(JTable.AUTO_RESIZE_OFF);
		jtable_user.setAutoCreateRowSorter(true);
		jscroll_user_list = new JScrollPane(jtable_user);
		jp_user_bottom.add(jscroll_user_list);
		jp_bottom_info = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp_bottom_info.setBounds(5, 680, 1005, 30);
		jl_bottom_info = new JLabel();
		jl_bottom_info.setText("Current user:" + admin_name);
		jp_bottom_info.add(jl_bottom_info);
		jp_user.add(jp_user_top);
		jp_user.add(jp_user_bottom);
		jp_user.add(jp_bottom_info);
		/**
		 * 多选题库区界面
		 */
		jp_test = new JPanel();
		jp_test.setLayout(null);
		jp_test_top = new JPanel();
		jp_test_top.setLayout(null);
		jp_test_top.setBounds(5, 5, 1005, 300);
		jp_test_top.setBorder(BorderFactory.createTitledBorder(null, "Operation area", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		jp_test_top1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_test_top1.setBounds(10, 15, 980, 40);
		jl_t_id = new JLabel("SearchByTopic");
		jtxf_t_search = new JTextField(10);
		jbtn_t_search = new JButton("Search");
		jbtn_t_search.addActionListener(new actionButton3());
		jbtn_t_del = new JButton("Delete");
		jbtn_t_del.addActionListener(new acctionButton2());
		//jp_test_top2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//jp_test_top2.setBounds(20, 50, 980, 250);

		MCQQuestionInfo mcqQuestionInfo = new MCQQuestionInfo();
		mcqQuestionInfo.setBounds(20, 50, 980,300);
		jp_test.add(mcqQuestionInfo);
		/*addTest = new JButton("提交");
		//jl_add_tid = new JLabel("题目编号");
		jl_add_tcontent = new JLabel("题目内容");
		jl_add_tA = new JLabel("A      选项");
		jl_add_tB = new JLabel("B      选项");
		jl_add_tC = new JLabel("C      选项");
		jl_add_tD = new JLabel("D      选项");
		jl_add_tTrue = new JLabel("答案解析");
		jtx_add_tid = new JTextField(40);
		jtx_add_tcontent = new JTextField(40);
		jtx_add_tA = new JTextField(40);
		jtx_add_tB = new JTextField(40);
		jtx_add_tC = new JTextField(40);
		jtx_add_tD = new JTextField(40);
		jtx_add_tTrue = new JTextField(40);

		// 创建复选框
		JCheckBox checkBox01 = new JCheckBox("A");
		JCheckBox checkBox02 = new JCheckBox("B");
		JCheckBox checkBox03 = new JCheckBox("C");
		JCheckBox checkBox04 = new JCheckBox("D");

		//jp_test_top2.add(jl_add_tid);
		jp_test_top2.add(jtx_add_tid);
		jp_test_top2.add(jl_add_tcontent);
		jp_test_top2.add(jtx_add_tcontent);
		jp_test_top2.add(jl_add_tA);
		jp_test_top2.add(jtx_add_tA);
		jp_test_top2.add(jl_add_tB);
		jp_test_top2.add(jtx_add_tB);
		jp_test_top2.add(jl_add_tC);
		jp_test_top2.add(jtx_add_tC);
		jp_test_top2.add(jl_add_tD);
		jp_test_top2.add(jtx_add_tD);

		jp_test_top2.add(jl_add_tTrue);

		jp_test_top2.add(jtx_add_tTrue);

		jp_test_top2.add(checkBox01);
		jp_test_top2.add(checkBox02);
		jp_test_top2.add(checkBox03);
		jp_test_top2.add(checkBox04);
		jp_test_top2.add(addTest);
		addTest.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//insertTest();
			}
		});
		jp_test_top.add(jp_test_top2);
		*/

		jp_test_top1.add(jl_t_id);
		jp_test_top1.add(jtxf_t_search);
		jp_test_top1.add(jbtn_t_search);
		jp_test_top1.add(jbtn_t_del);
		jp_test_top.add(jp_test_top1);
		jp_test_bottom = new JPanel(new BorderLayout());
		jp_test_bottom.setBounds(5, 350, 1005, 310);
		jp_test_bottom.setBorder(BorderFactory.createTitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		dtm_t = new DefaultTableModel(testinfo, table_test_head);
		jtable_test = new JTable(dtm_t);
		jtable_test.getSelectionModel().setSelectionMode(JTable.AUTO_RESIZE_OFF);
		jtable_test.setAutoCreateRowSorter(true);
		jscroll_test_list = new JScrollPane(jtable_test);
		jp_test_bottom.add(jscroll_test_list);
		jp_test.add(jp_test_bottom);
		jp_test.add(jp_test_top);
		/**
		 *开放问题区界面
		 */
		jp_stu = new JPanel();
		jp_stu.setLayout(null);
		jp_stu_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_stu_top.setBounds(5, 5, 1005, 250);
		jp_stu_top.setBorder(BorderFactory.createTitledBorder(null, "Operation area"));
		jp_stu_bottom = new JPanel(new BorderLayout());
		jp_stu_bottom.setBorder(BorderFactory.createTitledBorder(null, "Information"));
		jp_stu_bottom.setBounds(5, 260, 1005, 420);
		jl_stu_sea = new JLabel("SearchByTopic");
		jtx_stu_search = new JTextField(10);
		jbtn_stu_search = new JButton("Search");
		jbtn_stu_search.addActionListener(new actionButton4());
		jbtn_stu_del = new JButton("Delete");
		jbtn_stu_del.addActionListener(new acctionButton5());

		jp_stu_top.add(jl_stu_sea);
		jp_stu_top.add(jtx_stu_search);
		jp_stu_top.add(jbtn_stu_search);
		jp_stu_top.add(jbtn_stu_del);

		OpenQuestionInfo openQuestionInfo = new OpenQuestionInfo();
		openQuestionInfo.setBounds(20, 50, 980, 200);
		jp_stu.add(openQuestionInfo);

		dtm_stu = new DefaultTableModel(stuinfo, table_stu_head);
		jtable_stu = new JTable(dtm_stu);
		jscroll_stu_list = new JScrollPane(jtable_stu);
		jp_stu_bottom.add(jscroll_stu_list);
		jp_stu.add(jp_stu_top);
		jp_stu.add(jp_stu_bottom);
		jtabbedpane.add("User Information", jp_user);
		jtabbedpane.addTab("MCQQuestion", jp_test);
		jtabbedpane.addTab("OpenQuestion", jp_stu);
		this.add(jtabbedpane);
		this.setVisible(true);
	}

	/**
	 * 用户区按钮监听事件(查询)
	 */
	class actionButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int temprow = dtm.getRowCount();
			clear_Student_List(temprow);
			selectStudent(jtxf_search.getText());
		}
	}

	/**
	 * 多项选择题区按钮监听事件(查询)
	 */
	class actionButton3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int temprow = dtm_t.getRowCount();
			clear_MCQQuestion_List(temprow);
			selectMCQQuestion(jtxf_t_search.getText());
		}
	}

	/**
	 * 开放题目区按钮监听事件(查询)
	 */

	class actionButton4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int temprow = dtm_stu.getRowCount();
			clear_OpenQuestion_List(temprow);
			selectOpenQuestion(jtx_stu_search.getText());
		}
	}

	/**
	 * 用户区按钮监听事件(删除)
	 */
	class actionButton1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (jtable_user.getSelectedRow() != -1) {
				int tempcho = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "please choose", JOptionPane.YES_NO_CANCEL_OPTION);
				if (tempcho == 0) {
					Object o = jtable_user.getValueAt(jtable_user.getSelectedRow(), jtable_user
							.getSelectedColumnCount());
					Student user = new Student();
					user.setUname((String)o);
					delStudent(user);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a record to delete!", "prompt", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 多项选择题库区按钮监听事件(删除)
	 */
	class acctionButton2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (jtable_test.getSelectedRow() != -1) {
				int tempcho = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "please choose", JOptionPane.YES_NO_CANCEL_OPTION);
				if (tempcho == 0) {
					Object o = jtable_test.getValueAt(jtable_test.getSelectedRow(), jtable_test
							.getSelectedColumnCount());
					Question question = new Question();
					question.setContent((String)o);
					deleteMCQQuestion(question);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a record to delete!", "prompt", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 * 开放题库区按钮监听事件(删除)
	 */
	class acctionButton5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (jtable_stu.getSelectedRow() != -1) {
				int tempcho = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "please choose", JOptionPane.YES_NO_CANCEL_OPTION);
				if (tempcho == 0) {
					Object o = jtable_stu.getValueAt(jtable_stu.getSelectedRow(), jtable_stu
							.getSelectedColumnCount());
					Question question = new Question();
					question.setContent((String)o);
					deleteOpenQuestion(question);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a record to delete!", "prompt", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 用户查找方法
	 */
	public void selectStudent(String name) {
		if (jtxf_search.getText().trim().length() != 0) {
			try {
				StudentJDBCDAO userJDBCDAO = new StudentJDBCDAO();
				Student user = new Student();
				user.setUname(name);
				List<Student> userList = userJDBCDAO.queryStudentByName(user);
				if (null!=userList && userList.size()>0) {
					for(Student u : userList){
						String tempid = u.getUnumber();
						String tempname = u.getUname();
						String temppwd = u.getUpassword();
						String templevel = String.valueOf(u.getUlevel());
						addStudentInfo(tempid, tempname, temppwd, templevel);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Check no such person!", "Message", JOptionPane.NO_OPTION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try{
				StudentJDBCDAO userJDBCDAO = new StudentJDBCDAO();
				List<Student> userList = userJDBCDAO.queryStudents();
				for(Student u : userList){
					String tempid = u.getUnumber();
					String tempname = u.getUname();
					String temppwd = u.getUpassword();
					String templevel = String.valueOf(u.getUlevel());
					addStudentInfo(tempid, tempname, temppwd, templevel);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 多项选择题库区查找方法
	 */
	public void selectMCQQuestion(String name) {
		if (jtxf_t_search.getText().trim().length() != 0) {
			try {
				MCQQuestionJDBCDAO mcqQuestionJDBCDAO = new MCQQuestionJDBCDAO();
				Question question = new Question();
				question.setContent(name);

				List<MCQQuestionAll> mcqQuestionAlls = mcqQuestionJDBCDAO.queryMCQQuestionAllByName(question);
				if (null!=mcqQuestionAlls && mcqQuestionAlls.size()>0) {
					for (MCQQuestionAll mcqQuestionAll : mcqQuestionAlls) {
						String topic = mcqQuestionAll.getQtopics();
						String content = mcqQuestionAll.getQcontent();
						String choiceA = mcqQuestionAll.getChoiceA();
						String choiceB = mcqQuestionAll.getChoiceB();
						String choiceC = mcqQuestionAll.getChoiceC();
						String choiceD = mcqQuestionAll.getChoiceD();
						String answer = mcqQuestionAll.getAnswer();
						addMCQQuestionInfo(topic, content, choiceA, choiceB, choiceC, choiceD, answer);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Check for issues related to this topic!", "Message", JOptionPane.NO_OPTION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try{
				MCQQuestionJDBCDAO mcqQuestionJDBCDAO = new MCQQuestionJDBCDAO();
				List<MCQQuestionAll> mcqQuestionAlls = mcqQuestionJDBCDAO.queryMCQQuestionAlls();
				for (MCQQuestionAll mcqQuestionAll : mcqQuestionAlls){
					String topic = mcqQuestionAll.getQtopics();
					String content = mcqQuestionAll.getQcontent();
					String choiceA = mcqQuestionAll.getChoiceA();
					String choiceB = mcqQuestionAll.getChoiceB();
					String choiceC = mcqQuestionAll.getChoiceC();
					String choiceD = mcqQuestionAll.getChoiceD();
					String answer = mcqQuestionAll.getAnswer();
					addMCQQuestionInfo(topic,content,choiceA,choiceB,choiceC,choiceD,answer);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 开放题目区查找方法
	 */
	public void selectOpenQuestion(String name) {
		if (jtx_stu_search.getText().trim().length() != 0) {
			try {
				OpenQuestionJDBCDAO openQuestionJDBCDAO = new OpenQuestionJDBCDAO();
				Question question = new Question();
				question.setContent(name);
				List<OpenQuestionAll> openQuestionAlls = openQuestionJDBCDAO.queryOpenQuestionAllByName(question);
				if (null!=openQuestionAlls && openQuestionAlls.size()>0) {
					for (OpenQuestionAll openQuestionAll : openQuestionAlls) {
						String qcontent = openQuestionAll.getQcontent();
						String qtopics = openQuestionAll.getQtopics();
						String answer = openQuestionAll.getAnswer();
						addOpenQuestionInfo(qcontent,qtopics,answer);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Check for issues related to this topic!", "Message", JOptionPane.NO_OPTION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try{
				OpenQuestionJDBCDAO openQuestionJDBCDAO = new OpenQuestionJDBCDAO();
				List<OpenQuestionAll> openQuestionAllList = openQuestionJDBCDAO.queryOpenQuestionAlls();
				for (OpenQuestionAll openQuestionAll : openQuestionAllList){
					String qcontent = openQuestionAll.getQcontent();
					String qtopics = openQuestionAll.getQtopics();
					String answer = openQuestionAll.getAnswer();
					addOpenQuestionInfo(qcontent,qtopics,answer);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用户添加查询结果
	 */

	public void addStudentInfo(String u_id, String u_name, String u_pwd, String u_level) {
		Vector<String> v = new Vector<String>();
		v.add(u_id);
		v.add(u_name);
		v.add(u_pwd);
		v.add(u_level);
		dtm.addRow(v);
	}

	/**
	 * 多项选择题添加查询结果
	 */
	public void addMCQQuestionInfo(String T_topic, String T_content, String T_A, String T_B, String T_C, String T_D, String T_answer) {
		Vector<String> v = new Vector<String>();
		v.add(T_topic);
		v.add(T_content);
		v.add(T_A);
		v.add(T_B);
		v.add(T_C);
		v.add(T_D);
		v.add(T_answer);
		dtm_t.addRow(v);
	}

	/**
	 * 开放题目添加查询结果
	 */
	public void addOpenQuestionInfo(String qcontent,String qtopics,String answer) {
		Vector<String> v = new Vector<String>();
		v.add(qtopics);
		v.add(qcontent);
		v.add(answer);
		dtm_stu.addRow(v);
	}

	/**
	 * 用户区清空当前显示内容
	 */
	public void clear_Student_List(int temp) {
		if (temp != 0) {
			for (int i = 0; i < temp; i++) {
				dtm.removeRow(i - i);
			}
		}
	}

	/**
	 * 多项选择题库区清空当前显示内容
	 */
	public void clear_MCQQuestion_List(int temp) {
		if (temp != 0) {
			for (int i = 0; i < temp; i++) {
				dtm_t.removeRow(i - i);
			}
		}
	}

	/**
	 * 开放题目区清空当前显示内容
	 */
	public void clear_OpenQuestion_List(int temp) {
		if (temp != 0) {
			for (int i = 0; i < temp; i++) {
				dtm_stu.removeRow(i - i);
			}
		}
	}

	/**
	 * 删除记录
	 */
	public void delStudent(Student user) {
		try {
			StudentJDBCDAO userJDBCDAO = new StudentJDBCDAO();
			boolean isDeleted = userJDBCDAO.delete(user);
			if (isDeleted) {
				JOptionPane.showMessageDialog(null, "successfully deleted", "Message", JOptionPane.OK_OPTION);
                try{
                    //清空原来的数据
                    int temprow = dtm.getRowCount();
                    clear_Student_List(temprow);
                    //删除一条记录后的数据
                    List<Student> userList = userJDBCDAO.queryStudents();
                    for(Student u : userList){
						String tempid = u.getUnumber();
                        String tempname = u.getUname();
                        String temppwd = u.getUpassword();
                        String templevel = String.valueOf(u.getUlevel());
                        addStudentInfo(tempid, tempname, temppwd, templevel);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMCQQuestion(Question question){
		try {
			MCQQuestionJDBCDAO mcqQuestionJDBCDAO = new MCQQuestionJDBCDAO();
			boolean isDeleted = mcqQuestionJDBCDAO.delete(question);
			if (isDeleted) {
				JOptionPane.showMessageDialog(null, "successfully deleted", "Message", JOptionPane.OK_OPTION);
				try{
					//清空原来的数据
					int temprow = dtm_t.getRowCount();
					clear_MCQQuestion_List(temprow);
					//删除一条记录后的数据
					List<MCQQuestionAll> mcqQuestionAlls = mcqQuestionJDBCDAO.queryMCQQuestionAlls();
					for (MCQQuestionAll mcqQuestionAll : mcqQuestionAlls){
						String topic = mcqQuestionAll.getQtopics();
						String content = mcqQuestionAll.getQcontent();
						String choiceA = mcqQuestionAll.getChoiceA();
						String choiceB = mcqQuestionAll.getChoiceB();
						String choiceC = mcqQuestionAll.getChoiceC();
						String choiceD = mcqQuestionAll.getChoiceD();
						String answer = mcqQuestionAll.getAnswer();
						addMCQQuestionInfo(topic,content,choiceA,choiceB,choiceC,choiceD,answer);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteOpenQuestion(Question question){
		try {
			OpenQuestionJDBCDAO openQuestionJDBCDAO = new OpenQuestionJDBCDAO();
			boolean isDeleted = openQuestionJDBCDAO.delete(question);
			if (isDeleted) {
				JOptionPane.showMessageDialog(null, "successfully deleted", "Message", JOptionPane.OK_OPTION);
				try{
					//清空原来的数据
					int temprow = dtm_stu.getRowCount();
					clear_OpenQuestion_List(temprow);
					//删除一条记录后的数据
					try{
						List<OpenQuestionAll> openQuestionAllList = openQuestionJDBCDAO.queryOpenQuestionAlls();
						for (OpenQuestionAll openQuestionAll : openQuestionAllList){
							String qcontent = openQuestionAll.getQcontent();
							String qtopics = openQuestionAll.getQtopics();
							String answer = openQuestionAll.getAnswer();
							addOpenQuestionInfo(qcontent,qtopics,answer);
						}
					}catch (Exception e){
						e.printStackTrace();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
