package MYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class ReturnQueryResult {
    public static String[][] send(String sql) {
        String[][] result = null;
        Connection conn = null;
        Statement statement = null;
        try {
            conn = Main.sendConnection();
            statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();//获取列数
            rs.last();
            int row = rs.getRow();//获取行数
            rs.beforeFirst();
            result = new String[row][column];
            int count = 0;
            while (rs.next()) {
                for (int i = 1; i <= column; i++) {
                    result[count][i - 1] = rs.getString(i);
                }
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
