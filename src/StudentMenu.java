package MYSQL;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMenu extends JFrame {

    StudentMenu now = this;

    public StudentMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    Login send = null;

    void sendObject(Login o) {
        send = o;
    }

    String input_ID = null;

    void sendID(String ID) {
        input_ID = ID;
    }

    public void init() {
        JPanelUp panel_up = new JPanelUp();
        JPanelBottom panel_bottom = new JPanelBottom();
        this.add(panel_up, BorderLayout.NORTH);
        this.add(panel_bottom, BorderLayout.CENTER);
        this.setVisible(true);
    }

    class JPanelUp extends JPanel {
        JPanelUp() {
            this.setPreferredSize(new Dimension(800, 480));
            this.setLayout(new GridLayout(1, 2, 0, 0));
            init();
        }

        public void init() {
            String sql_message = "select * from ssms.student where S_ID = " + input_ID + ";";
            Connection conn = Main.sendConnection();
            PreparedStatement pstmt = null;
            ResultSet resultSet = null;
            String no = "", name = "", sex = "", age = "", phone = "";
            try {
                pstmt = conn.prepareStatement(sql_message);
                resultSet = pstmt.executeQuery();
                resultSet.next();
                no = resultSet.getString(1);
                name = resultSet.getString(2);
                sex = resultSet.getString(3);
                age = resultSet.getString(4);
                phone = resultSet.getString(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JLabel l_message = new JLabel("个人信息:");
            l_message.setFont(new Font("楷体", Font.BOLD, 20));
            JLabel l_no = new JLabel("学   号:   " + no);
            JLabel l_name = new JLabel("姓   名:   " + name);
            JLabel l_sex = new JLabel("性   别:   " + sex);
            JLabel l_age = new JLabel("年   龄:   " + age);
            JLabel l_phone = new JLabel("电   话:   " + phone);
            JPanel panel_left = new JPanel(new GridLayout(10, 1));
            panel_left.add(l_message);
            panel_left.add(l_no);
            panel_left.add(l_name);
            panel_left.add(l_sex);
            panel_left.add(l_age);
            panel_left.add(l_phone);
            this.add(panel_left);
            JLabel l_score = new JLabel("各科成绩:");
            l_score.setFont(new Font("楷体", Font.BOLD, 20));
            JPanel panel_right = new JPanel(new BorderLayout());
            String sql_score = "select Cname, grade, teacher.name from ssms.student," +
                    " ssms.teacher, ssms.course, ssms.score where student.S_ID = "
                    + input_ID + " and student.S_ID = score.S_ID and " +
                    "score.T_ID = teacher.T_ID and teacher.subject = course.Cno;";
            String[][] result = ReturnQueryResult.send(sql_score);
            String[] head = {"科目", "分数", "老师"};
            MyTable t_score = new MyTable(result, head);
            JScrollPane sp = new JScrollPane(t_score);
            panel_right.add(l_score, BorderLayout.NORTH);
            panel_right.add(sp, BorderLayout.CENTER);
            this.add(panel_right);
        }
    }

    class JPanelBottom extends JPanel {

        JPanelBottom() {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));
            init();
        }

        public void init() {
            JButton button_alter = new JButton("修改信息");
            JButton button_password = new JButton("修改密码");
            JButton button_quit = new JButton("退出系统");
            button_alter.setFocusPainted(false);
            button_password.setFocusPainted(false);
            button_quit.setFocusPainted(false);
            button_alter.setPreferredSize(new Dimension(100, 30));
            button_password.setPreferredSize(new Dimension(100, 30));
            button_quit.setPreferredSize(new Dimension(100, 30));
            button_alter.addActionListener(e -> new AlterDialog(now));//弹出修改个人信息对话框
            button_password.addActionListener(e -> new PasswordDialog(now));
            button_quit.addActionListener(e -> System.exit(0));
            this.add(button_alter);
            this.add(button_password);
            this.add(button_quit);
        }
    }

    class PasswordDialog extends JDialog {
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
            String sql = "update ssms.student set student.passcode =? where student.S_ID=?;";
            bt_save.addActionListener(e -> {
                PreparedStatement pstmt = null;
                try {
                    pstmt = conn.prepareStatement(sql);
                    try {
                        compare(text_1.getText(), text_2.getText());
                        pstmt.setString(1, text_1.getText());
                        pstmt.setInt(2, Integer.parseInt(input_ID));
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "修改成功！",
                                "Succeed", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "两次输入的密码不同！",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            this.add(bt_save);
            this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        }
    }

    class AlterDialog extends JDialog {
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
            String sql = "update ssms.student set student.age=?, student.phone=? where student.S_ID=?;";
            bt_save.addActionListener(e -> {
                PreparedStatement pstmt = null;
                try {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, Integer.parseInt(text_age.getText()));
                    pstmt.setString(2, text_phone.getText());
                    pstmt.setInt(3, Integer.parseInt(input_ID));
                    pstmt.executeUpdate();
                    now.init();
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
}



