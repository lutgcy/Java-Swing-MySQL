package MYSQL;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

class MyTable extends JTable {

    MyTable(String[][] result, String[] head) {
        super(result, head);
        this.setRowHeight(30);
        this.setEnabled(false);
        this.setFont(new Font("宋体", Font.ROMAN_BASELINE, 14));
        this.setForeground(Color.black);
        this.getTableHeader().setPreferredSize(new Dimension(1, 35));
        this.getTableHeader().setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 14));
        this.getTableHeader().setForeground(Color.BLACK);
    }
}
