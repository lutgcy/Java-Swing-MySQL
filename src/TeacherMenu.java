package MYSQL;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class TeacherMenu extends JFrame {

    TeacherMenu father = this;
    Login send = null;
    JPanelRight flag = null;
    String input_ID = null;
    JPanel panel_top = null;
    JPanelLeft panel_left = null;
    JPanelRight panel_right = null;
    JPanelLeft showmessage = null;
    static final CardLayout cl = new CardLayout();

    public TeacherMenu() {
        this.setLayout(new GridBagLayout());//主菜单界面为网格包布局管理器
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //获取登录界面对象的引用
    void sendObject(Login o) {
        send = o;
    }

    //获取登录时用户输入的账号
    void sendID(String ID) {
        input_ID = ID;
    }

    public void init() {//在主菜单界面添加panel
        panel_top = new JPanel();
        JLabel label_title = new JLabel("欢迎使用学生成绩管理系统");
        label_title.setFont(new Font("宋体", Font.ROMAN_BASELINE, 40));
        panel_top.add(label_title);

        panel_top.setBackground(new Color(0xAFEEEE));
        this.add(panel_top, new MyGridBagConstraints(0, 0, 2, 1).
                setFill(GridBagConstraints.BOTH).setIpad(200, 50).setWeight(100, 0));

        panel_right = new JPanelRight();
        this.add(panel_right, new MyGridBagConstraints(1, 1, 1, 1).setFill(GridBagConstraints.BOTH));

        panel_left = new JPanelLeft();
        this.add(panel_left, new MyGridBagConstraints(0, 1, 1, 1).
                setFill(GridBagConstraints.BOTH).setWeight(0, 100).setIpad(100, 300));
        this.setVisible(true);
    }

    class JPanelLeft extends JPanel {//放置左侧按钮面板

        JPanelLeft dad = this;

        public JPanelLeft() {
            showmessage = this;
            this.setLayout(new GridBagLayout());
            init();
        }

        JPanel panel_head = new JPanel();
        JPanel panel_tail = new JPanel();
        JButton button_search = new JButton("主页");
        JButton button_add = new JButton("添加");
        JButton button_score = new JButton("成绩统计");
        JButton button_message = new JButton("个人信息");
        JButton button_quit = new JButton("退出系统");

        void init() {
            button_search.addActionListener(e -> cl.show(panel_right, "home"));
            button_add.addActionListener(e -> cl.show(panel_right, "insert"));
            button_score.addActionListener(e -> {
                flag.init();
                cl.show(panel_right, "score");});
            button_message.addActionListener(e -> cl.show(panel_right, "message"));
            add(panel_head, new MyGridBagConstraints(0, 0, 1, 2).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setWeight(10, 10).setIpad(1, 1));
            add(button_search, new MyGridBagConstraints(0, 2, 1, 1).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setIpad(20, 10));
            add(button_add, new MyGridBagConstraints(0, 3, 1, 1).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setIpad(20, 10));
            add(button_score, new MyGridBagConstraints(0, 4, 1, 1).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setIpad(20, 10));
            add(button_message, new MyGridBagConstraints(0, 6, 1, 1).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setIpad(20, 10));
            add(panel_tail, new MyGridBagConstraints(0, 7, 1, 2).setInset(new Insets(5, 0, 5, 0)).
                    setFill(GridBagConstraints.BOTH).setWeight(10, 30).setIpad(1, 1));
        }
    }

    class JPanelRight extends JPanel {//放置右侧 主页面板，个人信息面板等

        JPanelRight parent = this;

        public JPanelRight() {
            flag = this;
            this.setBackground(Color.MAGENTA);
            this.setLayout(cl);//CardLayout
            init();
        }

        void init() {
            JPanelHome panel_home = new JPanelHome();
            JPanelInsert panel_insert = new JPanelInsert();
            JPanelScore panel_score = new JPanelScore();
            PanelMessage panel_message = new PanelMessage();
            add(panel_home, "home");
            add(panel_insert, "insert");
            add(panel_score, "score");
            add(panel_message, "message");
        }

        //添加学生信息面板类
        class JPanelInsert extends JPanelHome {
            JPanelInsert() {
                this.setLayout(new GridLayout(7, 2));
            }

            void init() {
                JPanel[] panels = new JPanel[7];
                for (int i = 0; i < panels.length; i++) {
                    panels[i] = new JPanel();
                }
                JLabel title = new JLabel("添加学生信息：");
                panels[0].setLayout(new FlowLayout(FlowLayout.LEFT));
                panels[0].add(title);
                JLabel[] jLabels = new JLabel[5];
                JTextField[] jTextFields = new JTextField[5];
                String[] label_text = {"学号", "姓名", "性别", "年龄", "电话"};
                for (int i = 0; i < 5; i++) {
                    jLabels[i] = new JLabel(label_text[i]);
                    jTextFields[i] = new JTextField(20);
                    panels[i + 1].add(jLabels[i]);
                    panels[i + 1].add(jTextFields[i]);
                }
                JButton button_confirm = new JButton("添加");
                button_confirm.setPreferredSize(new Dimension(100, 30));
                button_confirm.addActionListener(e -> {
                    Connection conn = Main.sendConnection();
                    try {
                        String sql_student = "insert into ssms.student values (" + jTextFields[0].getText() + ", '"
                                + jTextFields[1].getText() + "', '" + jTextFields[2].getText() + "', "
                                + jTextFields[3].getText() + ", '" + jTextFields[4].getText() + "', '123456'); ";
                        PreparedStatement pstmt = conn.prepareStatement(sql_student);
                        pstmt.executeUpdate();
                        for (int i = 1; i <= 4; i++) {//在score表添加默认成绩0分
                            String sql_score = "insert into ssms.score values(" + jTextFields[0].getText() + ", 1000" + i + ", 0);";
                            pstmt = conn.prepareStatement(sql_score);
                            pstmt.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(this, "添加成功！",
                                "Succeed", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "格式有误，请重新输入！",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                });
                panels[6].add(button_confirm);
                for (int i = 0; i < panels.length; i++) {
                    this.add(panels[i]);
                }
            }
        }

        //统计学生信息面板类
        class JPanelScore extends JPanelHome {

            JPanelShow panel_show = new JPanelShow();
            CardLayout layout = (CardLayout)panel_show.getLayout();

            JPanelScore() {
                this.setLayout(new BorderLayout());
//                this.removeAll();
                JLabel title = new JLabel("成绩统计:");
                title.setFont(new Font("楷体", Font.BOLD, 30));
                this.add(title,BorderLayout.NORTH);

                this.add(panel_show, BorderLayout.CENTER);
                JPanelButton panel_button = new JPanelButton();
                panel_button.setPreferredSize(new Dimension(0, 100));
                this.add(panel_button,BorderLayout.SOUTH);
            }
            class JPanelButton extends JPanel {
                JPanelButton() {
                    this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 40));
                    String[] button = {"总成绩", "Java", "数据库原理", "算法与数据结构", "C++"};
                    JButton[] button_all = new JButton[5];
                    for(int i=0; i<button.length; i++) {
                        button_all[i] = new JButton(button[i]);
                        this.add(button_all[i]);
                    }
                    button_all[0].addActionListener(e->layout.show(panel_show, "0"));
                    button_all[1].addActionListener(e->layout.show(panel_show, "1"));
                    button_all[2].addActionListener(e->layout.show(panel_show, "2"));
                    button_all[3].addActionListener(e->layout.show(panel_show, "3"));
                    button_all[4].addActionListener(e->layout.show(panel_show, "4"));
                }
            }

            class JPanelShow extends JPanel {
                JPanel[] panels = new JPanel[5];
                JPanelShow() {
                    this.setLayout(new CardLayout());
                    for(int i=0; i<panels.length; i++) {
                        panels[i] = new JPanel(new BorderLayout());
                        this.add(panels[i], i + "");
                    }
                    show_all();
                    show_single(1, "10001");
                    show_single(2, "10002");
                    show_single(3, "10003");
                    show_single(4, "10004");
                }
                void show_single(int no, String t_no) {//统计每科成绩
                    String[] head = {"学号", "姓名", "成绩", "排名"};
                    String sql = "select r.*, row_number() over(order by 'subject') as row_rank\n" +
                            "from(\n" +
                            "select score.S_ID, student.name,\n" +
                            "sum(score.grade) as 'subject'\n" +
                            "from ssms.score, ssms.student\n" +
                            "where score.S_ID = student.S_ID and score.T_ID = " + t_no +
                            " group by score.S_ID\n" +
                            "order by score.grade desc) r";
                    String[][]  result = ReturnQueryResult.send(sql);
                    MyTable table = new MyTable(result, head);
                    JScrollPane jsp = new JScrollPane(table);
                    panels[no].add(jsp, BorderLayout.CENTER);
                    String[] head_1 = {"分数段", "优秀(90~100)", "良好(80~90)", "中等(70~80)","及格(60~70)", "不及格(0~60)"};
                    String[][] result_1 = new String[1][6];
                    result_1[0][0] = "人数";
                    String sql_1 = "select count(case when score.grade >= 90 then 1 end) as '优秀',\n" +
                            "count(case when score.grade < 90 and score.grade >= 80 then 1 end) as '良好',\n" +
                            "count(case when score.grade < 80 and score.grade >= 70 then 1 end) as '中等',\n" +
                            "count(case when score.grade < 70 and score.grade >= 60 then 1 end) as '及格',\n" +
                            "count(case when score.grade < 60 then 1 end) as '不及格'\n" +
                            "from ssms.score\n" +
                            "where score.T_ID = " + t_no;
                    String[][] resultSet = ReturnQueryResult.send(sql_1);
                    for(int i = 1; i<6; i++) {
                        result_1[0][i] = resultSet[0][i-1];
                    }
                    MyTable table_1 = new MyTable(result_1, head_1);
                    JScrollPane jsp_1 = new JScrollPane(table_1);
                    jsp_1.setPreferredSize(new Dimension(0, 100));
                    panels[no].add(jsp_1, BorderLayout.SOUTH);
                }

                void show_all() {//统计总成绩
                    String[] head = {"学号", "姓名", "Java", "数据库原理", "算法与数据结构", "C++", "总分", "平均分", "排名"};
                    String sql = "select r.*, row_number() over(order by '平均分') as row_rank\n" +
                            "from(\n" +
                            "select score.S_ID, student.name,\n" +
                            "sum(case when T_ID = 10001 then score.grade else 0 end) as 'java',\n" +
                            "sum(case when T_ID = 10002 then score.grade else 0 end) as '数据库',\n" +
                            "sum(case when T_ID = 10003 then score.grade else 0 end) as '算法',\n" +
                            "sum(case when T_ID = 10004 then score.grade else 0 end) as 'C++',\n" +
                            "sum(score.grade) as '总分',\n" +
                            "format(sum(score.grade)/count(*), 2) as '平均分'\n" +
                            "from ssms.score, ssms.student\n" +
                            "where score.S_ID = student.S_ID\n" +
                            "group by score.S_ID\n" +
                            "order by 平均分 desc) r";
                    String[][]  result = ReturnQueryResult.send(sql);
                    MyTable table = new MyTable(result, head);
                    JScrollPane jsp = new JScrollPane(table);
                    panels[0].add(jsp, BorderLayout.CENTER);
                    String[] head_1 = {"分数段", "优秀(90~100)", "良好(80~90)", "中等(70~80)","及格(60~70)", "不及格(0~60)"};
                    String[][] result_1 = new String[1][6];
                    result_1[0][0] = "人数";
                    String sql_1 = "select count(case when ave >= 90 then 1 end) as '优秀',\n" +
                            "count(case when ave < 90 and ave >= 80 then 1 end) as '良好',\n" +
                            "count(case when ave < 80 and ave >= 70 then 1 end) as '中等',\n" +
                            "count(case when ave < 70 and ave >= 60 then 1 end) as '及格',\n" +
                            "count(case when ave < 60 then 1 end) as '不及格'\n" +
                            "from(select score.S_ID, student.name,\n" +
                            "sum(case when T_ID = 10001 then score.grade else 0 end) as 'java',\n" +
                            "sum(case when T_ID = 10002 then score.grade else 0 end) as '数据库',\n" +
                            "sum(case when T_ID = 10003 then score.grade else 0 end) as '算法',\n" +
                            "sum(case when T_ID = 10004 then score.grade else 0 end) as 'C++',\n" +
                            "sum(score.grade) as '总分',\n" +
                            "format(sum(score.grade)/count(*), 2) as ave,\n" +
                            "format(@r := @r + 1, 0) as '排名' \n" +
                            "from ssms.score, ssms.student, (select @r := 0) alias\n" +
                            "where score.S_ID = student.S_ID\n" +
                            "group by score.S_ID) t;";
                    String[][] resultSet = ReturnQueryResult.send(sql_1);
                    for(int i = 1; i<6; i++) {
                        result_1[0][i] = resultSet[0][i-1];
                    }
                    MyTable table_1 = new MyTable(result_1, head_1);
                    JScrollPane jsp_1 = new JScrollPane(table_1);
                    jsp_1.setPreferredSize(new Dimension(0, 100));
                    panels[0].add(jsp_1, BorderLayout.SOUTH);
                }
            }
        }

        //个人信息面板类
        class PanelMessage extends JPanel {//个人信息面板

            PanelMessage() {
                this.setLayout(new GridBagLayout());
                init();
            }

            void init() {
                JPanel panel_top = new JPanel(new GridLayout(10, 1));
                JLabel l_message = new JLabel("个人信息:");
                JLabel[] labels = new JLabel[8];
                String[] label_name = {"教工号:", "姓名:", "性别:", "年龄:", "电话:", "课程号:", "密码:", "课程名:"};
                String sql_message = "SELECT teacher.*, course.Cname FROM ssms.teacher, ssms.course where T_ID = " + input_ID
                        + " and teacher.subject = course.Cno;";
                String[][] result = ReturnQueryResult.send(sql_message);
                for (int i = 0; i < labels.length; i++) {
                    labels[i] = new JLabel("        " + label_name[i] + "   " + result[0][i]);
                }
                panel_top.add(l_message);
                for (JLabel l : labels) {
                    if (l == labels[6])//不显示密码
                        continue;
                    panel_top.add(l);
                }
                TPanelBottom panel_bottom = new TPanelBottom();
                this.add(panel_top, new MyGridBagConstraints(0, 0, 10, 5).setFill(GridBagConstraints.BOTH)
                        .setWeight(10, 100));
                this.add(panel_bottom, new MyGridBagConstraints(0, 16, 10, 1).setFill(GridBagConstraints.BOTH)
                        .setWeight(10, 1));
            }

            class TPanelBottom extends JPanel {//放置两个按钮

                TPanelBottom() {
                    this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 30));
                    init();
                }

                public void init() {
                    JButton button_alter = new JButton("修改信息");
                    JButton button_password = new JButton("修改密码");
                    button_alter.setFocusPainted(false);
                    button_password.setFocusPainted(false);
                    button_alter.setPreferredSize(new Dimension(100, 30));
                    button_password.setPreferredSize(new Dimension(100, 30));
                    button_alter.addActionListener(e -> new AlterDialog(father));//弹出修改个人信息对话框
                    button_password.addActionListener(e -> new PasswordDialog(father));
                    this.add(button_alter);
                    this.add(button_password);
                }
            }

            class AlterDialog extends JDialog {//修改个人信息对话框

                AlterDialog(JFrame parents) {
                    super(parents);
                    this.setSize(350, 460);
                    this.setLocationRelativeTo(parents);
                    init();
                    this.setVisible(true);
                }

                void init() {
                    Container c = this.getContentPane();
                    c.setLayout(new GridLayout(10, 1, 0, 15));
                    JLabel label_age = new JLabel("请输入年龄:");
                    this.add(label_age);
                    JTextField text_age = new JTextField();
                    this.add(text_age);
                    JLabel label_phone = new JLabel("请输入电话:");
                    this.add(label_phone);
                    JTextField text_phone = new JTextField();
                    this.add(text_phone);
                    JButton bt_save = new JButton("保    存");
                    Connection conn = Main.sendConnection();
                    String sql = "update ssms.teacher set teacher.age=?, teacher.phone=? where teacher.T_ID=?;";
                    bt_save.addActionListener(e -> {
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, Integer.parseInt(text_age.getText()));
                            pstmt.setString(2, text_phone.getText());
                            pstmt.setInt(3, Integer.parseInt(input_ID));
                            pstmt.executeUpdate();
                            parent.init();
                            cl.show(panel_right, "message");
                            showmessage.button_message.requestFocus();
                            JOptionPane.showMessageDialog(this, "修改成功！",
                                    "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException | NumberFormatException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "格式有误，请重新输入！",
                                    "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                    this.add(bt_save);
                    this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                }
            }

            class PasswordDialog extends JDialog {//修改密码对话框

                PasswordDialog(JFrame parents) {
                    super(parents);
                    this.setSize(300, 260);
                    this.setLocationRelativeTo(parents);
                    init();
                    this.setVisible(true);
                }

                void compare(String s1, String s2) throws Exception {
                    if (!s1.equals(s2))
                        throw new Exception("两次输入的密码不同！");
                }

                void init() {
                    Container c = this.getContentPane();
                    c.setLayout(new GridLayout(5, 1, 0, 15));
                    JLabel label_1 = new JLabel("请输入你的新密码:");
                    this.add(label_1);
                    JTextField text_1 = new JTextField();
                    this.add(text_1);
                    JLabel label_2 = new JLabel("请再次输入你的密码:");
                    this.add(label_2);
                    JTextField text_2 = new JTextField();
                    this.add(text_2);
                    JButton bt_save = new JButton("保    存");
                    Connection conn = Main.sendConnection();
                    String sql = "update ssms.teacher set teacher.passcode =? where teacher.T_ID=?;";
                    bt_save.addActionListener(e -> {
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = conn.prepareStatement(sql);
                            try {
                                compare(text_1.getText(), text_2.getText());
                                pstmt.setString(1, text_1.getText());
                                pstmt.setInt(2, Integer.parseInt(input_ID));
                                pstmt.executeUpdate();
                                JOptionPane.showMessageDialog(this, "修改成功！", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "两次输入的密码不同！", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "格式有误，请重新输入！", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                    this.add(bt_save);
                    this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                }
            }
        }

        //主页面板类
        class JPanelHome extends JPanel {//主页面板

            JPanel panel_top = null;
            JPanelHome dd = this;
            String sign_id = "";
            DownPanel down = null;

            public JPanelHome() {
                this.setLayout(new BorderLayout());
                init();
            }

            void init() {
                panel_top = new JPanel(new GridLayout(1, 2));
                panel_top.setPreferredSize(new Dimension(0, 80));
                panel_top.setBackground(Color.pink);
                JPanel id = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
                JPanel name = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 30));

                JTextField input_id = new JTextField(20);
                id.add(input_id);
                JButton button_id = new JButton("按学号查询");
                id.add(button_id);
                JTextField input_name = new JTextField(20);
                name.add(input_name);
                JButton button_name = new JButton("按姓名查询");
                name.add(button_name);
                panel_top.add(id);
                panel_top.add(name);
                add(panel_top, BorderLayout.NORTH);

                button_id.addActionListener(e -> {
                    String sql = "SELECT student.*, course.Cname, score.grade, teacher.name FROM" +
                            " ssms.student, ssms.course, ssms.score, ssms.teacher" +
                            " where student.S_ID = " + input_id.getText() +
                            " and student.S_ID = score.S_ID and score.T_ID = teacher.T_ID and course.Cno = teacher.subject;";
                    try {
                        search(sql);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
                button_name.addActionListener(e -> {
                    String sql = "SELECT student.*, course.Cname, score.grade, teacher.name FROM" +
                            " ssms.student, ssms.course, ssms.score, ssms.teacher" +
                            " where student.name = '" + input_name.getText() +
                            "' and student.S_ID = score.S_ID and score.T_ID = teacher.T_ID and course.Cno = teacher.subject;";
                    try {
                        search(sql);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            }

            class DownPanel extends JPanel {//放置查询结果面板

                DownPanel() {
                    this.setLayout(new GridLayout(3, 1));
                }

                void init(String sql) {
                    String[][] result = null;
                    try {
                        result = ReturnQueryResult.send(sql);
                        sign_id = result[0][0];
                    } catch (Exception e) {
                        dd.removeAll();
                        dd.init();
                    }
                    String[][] result_1 = new String[1][5];
                    try {
                        for (int i = 0; i <= 4; i++) {
                            result_1[0][i] = result[0][i];
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dd.removeAll();
                        dd.init();
                    }

                    String[] head_1 = {"学号", "姓名", "性别", "年龄", "电话"};
                    MyTable table_1 = new MyTable(result_1, head_1);
                    JScrollPane sp_1 = new JScrollPane(table_1);
                    String[][] result_2 = null;
                    try {
                        result_2 = new String[result.length][3];
                        for (int i = 0; i < result_2.length; i++) {
                            for (int j = 0; j < result_2[i].length; j++) {
                                result_2[i][j] = result[i][j + 6];
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        result_2 = new String[0][0];
                    }

                    String[] head_2 = {"科目", "分数", "老师"};
                    MyTable table_2 = new MyTable(result_2, head_2);
                    JScrollPane sp_2 = new JScrollPane(table_2);
                    this.add(sp_1);
                    this.add(sp_2);
                    JPanel panel_bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
                    JButton button_alter = new JButton("修改信息");
                    button_alter.addActionListener(e -> {
                        JDialog alter = new JDialog(father);
                        alter.setSize(new Dimension(400, 500));
                        alter.setLocationRelativeTo(father);
                        Container c = alter.getContentPane();
                        c.setLayout(new GridLayout(10, 1, 0, 15));
                        JLabel label_age = new JLabel("请输入年龄:");
                        alter.add(label_age);
                        JTextField text_age = new JTextField();
                        alter.add(text_age);
                        JLabel label_phone = new JLabel("请输入电话:");
                        alter.add(label_phone);
                        JTextField text_phone = new JTextField();
                        alter.add(text_phone);
                        JButton bt_save = new JButton("保    存");
                        Connection conn = Main.sendConnection();
                        String sql_alter = "update ssms.student set student.age=?, student.phone=? where student.S_ID=?;";
                        bt_save.addActionListener(l -> {
                            PreparedStatement pstmt = null;
                            try {
                                pstmt = conn.prepareStatement(sql_alter);
                                pstmt.setInt(1, Integer.parseInt(text_age.getText()));
                                pstmt.setString(2, text_phone.getText());
                                pstmt.setInt(3, Integer.parseInt(sign_id));
                                pstmt.executeUpdate();
                                //修改信息后刷新界面,更新数据
                                update_page();
                                JOptionPane.showMessageDialog(alter, "修改成功！", "Succeed",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException | NumberFormatException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(alter, "格式有误，请重新输入！",
                                        "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        });
                        alter.add(bt_save);
                        alter.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                        alter.setVisible(true);
                    });

                    JButton button_alter_score = new JButton("修改成绩");
                    button_alter_score.addActionListener(e -> {
                        String sql_cname = "SELECT course.Cname FROM ssms.course where course.Cno = " +
                                "( select teacher.subject from ssms.teacher where teacher.T_ID = " + input_ID + " );";
                        String[][] Cname = ReturnQueryResult.send(sql_cname);
                        String flag = JOptionPane.showInputDialog(parent, "请输入" + Cname[0][0] + "成绩：",
                                "修改成绩", JOptionPane.PLAIN_MESSAGE);
                        if (flag != null && flag != "") {
                            String sql_write_score = "update ssms.score set score.grade = " + Integer.parseInt(flag)
                                    + " where score.S_Id = " + sign_id + " and score.T_ID = " + input_ID;
                            Connection conn = Main.sendConnection();
                            try {
                                PreparedStatement pstmt = conn.prepareStatement(sql_write_score);
                                pstmt.executeUpdate();
                                update_page();
                                JOptionPane.showMessageDialog(parent, "修改成功！",
                                        "Succeed", JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(parent, "修改失败！",
                                        "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    JButton button_delete = new JButton("彻底删除");
                    button_delete.addActionListener(e -> {//删除学生所有信息
                        Object[] options = {"确认", "取消"};
                        int flag = JOptionPane.showOptionDialog(parent, "确认删除该生所有信息吗？", "标题",
                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (flag == JOptionPane.YES_OPTION) {
                            Connection conn = Main.sendConnection();
                            String sql_score = "delete from ssms.score where S_ID = " + sign_id;
                            String sql_student = "delete from ssms.student where S_ID = " + sign_id;
                            try {
                                PreparedStatement pstmt = conn.prepareStatement(sql_score);
                                pstmt.executeUpdate();
                                pstmt = conn.prepareStatement(sql_student);
                                pstmt.executeUpdate();
                                update_page();
                                JOptionPane.showMessageDialog(parent, "删除成功！",
                                        "Succeed", JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(parent, "删除错误！",
                                        "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    panel_bottom.add(button_alter_score);
                    panel_bottom.add(button_delete);
                    panel_bottom.add(button_alter);
                    this.add(panel_bottom);
                }
            }

            void update_page() {
                //修改信息后刷新界面,更新数据
                String sql_updata = "SELECT student.*, course.Cname, score.grade, teacher.name FROM" +
                        " ssms.student, ssms.course, ssms.score, ssms.teacher" +
                        " where student.S_ID = " + sign_id + " and student.S_ID = score.S_ID " +
                        "and score.T_ID = teacher.T_ID and course.Cno = teacher.subject;";
                try {
                    search(sql_updata);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    dd.removeAll();
                    dd.init();
                }
            }

            void search(String sql) {//按学号查询事件
                this.removeAll();
                this.init();
                down = new DownPanel();
                try {
                    down.init(sql);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    this.removeAll();
                    this.init();
                }
                add(down, BorderLayout.CENTER);
                cl.show(panel_right, "message");//先跳转到其他面板，再调回来，起到刷新的作用
                cl.show(panel_right, "home");
            }
        }
    }
}
