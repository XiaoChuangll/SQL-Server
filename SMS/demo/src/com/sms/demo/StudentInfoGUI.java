package com.sms.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentInfoGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public StudentInfoGUI() {
        // 设置窗口标题
        setTitle("学生信息");
        // 设置默认关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        setSize(800, 400);
        // 设置窗口居中
        setLocationRelativeTo(null);

        // 创建表格模型
        tableModel = new DefaultTableModel();
        // 设置表头
        tableModel.setColumnIdentifiers(new String[]{"学号", "姓名", "性别", "出生日期", "专业", "总学分", "备注"});

        // 创建表格
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 加载数据
        loadData();
    }

    private void loadData() {
        //定义加载驱动
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        //定义数据库用户
        String userName = "ddl2024";
        //定义数据库密码
        String userPwd = "Ddl2024@123";
        //定义数据库连接对象
        Connection dbConn = null;
        //定义命令对象
        Statement stmt = null;
        try {
            //1.加载及注册驱动
            Class.forName(driverName);
            //2.定义数据库连接字符串
            String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
            //3.数据库连接
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("连接数据库成功");
            //4.定义SQL语句
            String sql = "select * from xsb";
            //5.创建一个执行sql对象
            stmt = dbConn.createStatement();
            //6.执行SQL语句命令
            ResultSet resultSet = stmt.executeQuery(sql);
            //7.处理结果，并获取数据
            while (resultSet.next()) {
                String studentID = resultSet.getString("学号");
                String name = resultSet.getString("姓名");
                boolean gender = resultSet.getBoolean("性别");
                Date birthDate = resultSet.getDate("出生日期");
                String major = resultSet.getString("专业");
                int totalCredits = resultSet.getInt("总学分");
                String remarks = resultSet.getString("备注");
                // 将数据添加到表格模型
                tableModel.addRow(new Object[]{studentID, name, (gender ? "男" : "女"), birthDate, major, totalCredits, remarks});
            }
            //8.关闭连接
            resultSet.close();
            dbConn.close();
            stmt.close();
        } catch (SQLException e) {      //数据库的处理异常的方法
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // 显示GUI界面
        SwingUtilities.invokeLater(() -> {
            StudentInfoGUI frame = new StudentInfoGUI();
            frame.setVisible(true);
        });
    }
}
