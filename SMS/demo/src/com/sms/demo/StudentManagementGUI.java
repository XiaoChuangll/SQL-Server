package com.sms.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentManagementGUI extends JFrame {

    private JTextField studentIDField;
    private JTextField nameField;
    private JComboBox<String> genderField;
    private JTextField birthDateField;
    private JTextField majorField;
    private JTextField totalCreditsField;
    private JTextArea remarksField;
    private JTable table;
    private DefaultTableModel tableModel;

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public StudentManagementGUI() {
        setTitle("学生管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("学号:"), gbc);
        gbc.gridx = 1;
        studentIDField = new JTextField(20);
        inputPanel.add(studentIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        genderField = new JComboBox<>(new String[]{"男", "女"});
        inputPanel.add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("出生日期:"), gbc);
        gbc.gridx = 1;
        birthDateField = new JTextField(20);
        inputPanel.add(birthDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("专业:"), gbc);
        gbc.gridx = 1;
        majorField = new JTextField(20);
        inputPanel.add(majorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("总学分:"), gbc);
        gbc.gridx = 1;
        totalCreditsField = new JTextField(20);
        inputPanel.add(totalCreditsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("备注:"), gbc);
        gbc.gridx = 1;
        remarksField = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(remarksField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        add(inputPanel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");

        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"学号", "姓名", "性别", "出生日期", "专业", "总学分", "备注"});
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, gbc);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectStudent();
            }
        });

        pack(); // 自动调整窗口大小以适应内容
    }

    private void insertStudent() {
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        boolean gender = genderField.getSelectedItem().equals("男");
        Date birthDate = Date.valueOf(birthDateField.getText());
        String major = majorField.getText();
        int totalCredits = Integer.parseInt(totalCreditsField.getText());
        String remarks = remarksField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call InsertStudent(?, ?, ?, ?, ?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, name);
            stmt.setBoolean(3, gender);
            stmt.setDate(4, birthDate);
            stmt.setString(5, major);
            stmt.setInt(6, totalCredits);
            stmt.setString(7, remarks);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "学生信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "学生信息插入失败：" + e.getMessage());
        }
    }

    private void updateStudent() {
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        boolean gender = genderField.getSelectedItem().equals("男");
        Date birthDate = Date.valueOf(birthDateField.getText());
        String major = majorField.getText();
        int totalCredits = Integer.parseInt(totalCreditsField.getText());
        String remarks = remarksField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call UpdateStudent(?, ?, ?, ?, ?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, name);
            stmt.setBoolean(3, gender);
            stmt.setDate(4, birthDate);
            stmt.setString(5, major);
            stmt.setInt(6, totalCredits);
            stmt.setString(7, remarks);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "学生信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "学生信息更新失败：" + e.getMessage());
        }
    }

    private void deleteStudent() {
        String studentID = studentIDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteStudent(?)}")) {
            stmt.setString(1, studentID);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "学生信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "学生信息删除失败：" + e.getMessage());
        }
    }

    private void selectStudent() {
        String studentID = studentIDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call GetStudent(?)}")) {
            stmt.setString(1, studentID);
            ResultSet resultSet = stmt.executeQuery();
            tableModel.setRowCount(0); // 清空表格
            while (resultSet.next()) {
                String id = resultSet.getString("学号");
                String name = resultSet.getString("姓名");
                boolean gender = resultSet.getBoolean("性别");
                Date birthDate = resultSet.getDate("出生日期");
                String major = resultSet.getString("专业");
                int totalCredits = resultSet.getInt("总学分");
                String remarks = resultSet.getString("备注");
                tableModel.addRow(new Object[]{id, name, (gender ? "男" : "女"), birthDate, major, totalCredits, remarks});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "学生信息查询失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI frame = new StudentManagementGUI();
            frame.setVisible(true);
        });
    }
}
