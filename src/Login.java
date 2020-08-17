package MYSQL;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    Login father = this;
    JTextField user = null;
    JPasswordField password = null;
    JButton button1 = null;
    String flag = "teacher";

    Login() {
        this.setSize(500, 420);
        this.setLocationRelativeTo(null);
        this.setTitle("学生成绩管理系统");
        this.setResizable(false);
        init();
        this.setVisible(true);
        password.requestFocus();//让密码输入框获得焦点
    }

    public void login() {//进入主菜单事件
        String password_true = null;
        String where = " where T_ID = ";
        if (flag.equals("teacher")) {
            where = " where T_ID = ";
        } else {
            where = " where S_ID = ";
        }
        try {
            String password_sql = "select passcode from ssms." + flag + where + Integer.parseInt(user.getText()) + ";";
            Connection conn = Main.sendConnection();
            PreparedStatement stmt = conn.prepareStatement(password_sql);
            ResultSet result = stmt.executeQuery();
            result.next();
            password_true = result.getString("passcode");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        char[] pswd = password.getPassword();
        if (String.valueOf(pswd).equals(password_true)) {//密码
            JOptionPane.showMessageDialog(father, "登陆成功！");
            if (flag.equals("teacher")) {
                TeacherMenu teacherMenu = new TeacherMenu();//创建主菜单界面
                teacherMenu.sendObject(father);//传递主窗口引用
                teacherMenu.sendID(user.getText());//传递输入的账号ID用于数据库的操作等
                teacherMenu.init();
            } else {
                StudentMenu studentMenu = new StudentMenu();
                studentMenu.sendObject(father);
                studentMenu.sendID(user.getText());
                studentMenu.init();
            }
            father.setVisible(false);//进入主菜单界面时隐藏登录界面
        } else {
            JOptionPane.showMessageDialog(father, "账号或密码错误！", "错误", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void init() {

        setLayout(new GridLayout(2, 1, 0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel() {//登录界面上半部分
            private static final long serialVersionUID = 1L;
            ImageIcon image = new ImageIcon("Image\\up.png");//登录界面上部图片

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        panel1.setLayout(null);
        JPanel head = new JPanel() {//头像
            private static final long serialVersionUID = 1L;
            ImageIcon icon = new ImageIcon("Image\\flower.jpg");

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        head.setBounds(200, 70, 100, 100);
        panel1.add(head);
        JPanel panel2 = new JPanel(new GridLayout(2, 1, 0, 0));//登录界面下半部分
        add(panel1);
        add(panel2);
        JLabel label1 = new JLabel("账    号：");
        JLabel label2 = new JLabel("密    码：");
        user = new JTextField("10001", 15);//账号输入框
        password = new JPasswordField(15);//密码输入框
        password.addActionListener(e -> login());//密码框添加回车登录事件，进入主菜单界面
        JPanel panel3 = new JPanel(new GridLayout(2, 1, 0, 0));//放置用户名和密码及其输入框
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));//放置用户名及其输入框
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));//放置密码及其输入框
        panel3.add(panel4);
        panel3.add(panel5);
        panel4.add(label1);
        panel4.add(user);//添加用户名输入框
        panel5.add(label2);
        panel5.add(password);//添加密码输入框
        panel2.add(panel3);
        button1 = new JButton("登    录");
        JButton button2 = new JButton("退    出");
        button1.setFocusPainted(false);//不绘制焦点
        button2.setFocusPainted(false);

        try {//改变成win10风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        button1.addActionListener(e -> login());
        button2.addActionListener(e -> System.exit(0));//lambda表达式
        JPanel panel_choice = new JPanel();
        JRadioButton radioButton_1 = new JRadioButton("教师", true);//默认选择教师登录
        JRadioButton radioButton_2 = new JRadioButton("学生");
        radioButton_1.setFocusPainted(false);
        radioButton_2.setFocusPainted(false);
        radioButton_1.addActionListener(e -> flag = "teacher");
        radioButton_2.addActionListener(e -> flag = "student");
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton_1);
        group.add(radioButton_2);
        panel_choice.add(radioButton_1);
        panel_choice.add(radioButton_2);
        JPanel panel6 = new JPanel(new GridLayout(3, 1, 0, 0));
        panel2.add(panel6);
        panel6.add(panel_choice);
        JPanel panel_button = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        panel_button.add(button1);
        panel_button.add(button2);
        panel6.add(panel_button);
        panel6.add(new JPanel());
    }
}

