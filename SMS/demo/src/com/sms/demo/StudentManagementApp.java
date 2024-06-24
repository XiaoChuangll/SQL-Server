package com.sms.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementApp extends JFrame {

    private StudentOperations studentOps = new StudentOperations();
    private CourseOperations courseOps = new CourseOperations();
    private ClassroomOperations classroomOps = new ClassroomOperations();
    private EnrollmentOperations enrollmentOps = new EnrollmentOperations();
    private GradeOperations gradeOps = new GradeOperations();

    private JTextField studentIDField, nameField, birthDateField, majorField, totalCreditsField, remarksField;
    private JTextField courseIDField, courseNameField, courseHoursField, courseCreditsField;
    private JTextField classroomIDField, classroomNameField, classroomCapacityField, classroomRemarksField;
    private JTextField enrollmentStudentIDField, enrollmentCourseIDField, enrollmentClassroomIDField;
    private JTextField gradeStudentIDField, gradeCourseIDField, gradeField;
    private JComboBox<String> genderField;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentManagementApp() {
        setTitle("学生管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("学生", createStudentPanel());
        tabbedPane.addTab("课程", createCoursePanel());
        tabbedPane.addTab("教室", createClassroomPanel());
        tabbedPane.addTab("选课", createEnrollmentPanel());
        tabbedPane.addTab("成绩", createGradePanel());

        add(tabbedPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        pack();
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("学号:"), gbc);
        gbc.gridx = 1; studentIDField = new JTextField(15); panel.add(studentIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1; nameField = new JTextField(15); panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1; genderField = new JComboBox<>(new String[]{"男", "女"}); panel.add(genderField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("出生日期:"), gbc);
        gbc.gridx = 1; birthDateField = new JTextField(15); panel.add(birthDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("专业:"), gbc);
        gbc.gridx = 1; majorField = new JTextField(15); panel.add(majorField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("总学分:"), gbc);
        gbc.gridx = 1; totalCreditsField = new JTextField(15); panel.add(totalCreditsField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("备注:"), gbc);
        gbc.gridx = 1; remarksField = new JTextField(15); panel.add(remarksField, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);
        panel.add(buttonPanel, gbc);

        insertButton.addActionListener(e -> insertStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        selectButton.addActionListener(e -> selectStudent());

        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("课程号:"), gbc);
        gbc.gridx = 1; courseIDField = new JTextField(15); panel.add(courseIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("课程名:"), gbc);
        gbc.gridx = 1; courseNameField = new JTextField(15); panel.add(courseNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("学时:"), gbc);
        gbc.gridx = 1; courseHoursField = new JTextField(15); panel.add(courseHoursField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("学分:"), gbc);
        gbc.gridx = 1; courseCreditsField = new JTextField(15); panel.add(courseCreditsField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);
        panel.add(buttonPanel, gbc);

        insertButton.addActionListener(e -> insertCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        selectButton.addActionListener(e -> selectCourse());

        return panel;
    }

    private JPanel createClassroomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("教室号:"), gbc);
        gbc.gridx = 1; classroomIDField = new JTextField(15); panel.add(classroomIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("教室名:"), gbc);
        gbc.gridx = 1; classroomNameField = new JTextField(15); panel.add(classroomNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("容量:"), gbc);
        gbc.gridx = 1; classroomCapacityField = new JTextField(15); panel.add(classroomCapacityField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("备注:"), gbc);
        gbc.gridx = 1; classroomRemarksField = new JTextField(15); panel.add(classroomRemarksField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);
        panel.add(buttonPanel, gbc);

        insertButton.addActionListener(e -> insertClassroom());
        updateButton.addActionListener(e -> updateClassroom());
        deleteButton.addActionListener(e -> deleteClassroom());
        selectButton.addActionListener(e -> selectClassroom());

        return panel;
    }

    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("学号:"), gbc);
        gbc.gridx = 1; enrollmentStudentIDField = new JTextField(15); panel.add(enrollmentStudentIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("课程号:"), gbc);
        gbc.gridx = 1; enrollmentCourseIDField = new JTextField(15); panel.add(enrollmentCourseIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("教室号:"), gbc);
        gbc.gridx = 1; enrollmentClassroomIDField = new JTextField(15); panel.add(enrollmentClassroomIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);
        panel.add(buttonPanel, gbc);

        insertButton.addActionListener(e -> insertEnrollment());
        updateButton.addActionListener(e -> updateEnrollment());
        deleteButton.addActionListener(e -> deleteEnrollment());
        selectButton.addActionListener(e -> selectEnrollment());

        return panel;
    }

    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("学号:"), gbc);
        gbc.gridx = 1; gradeStudentIDField = new JTextField(15); panel.add(gradeStudentIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("课程号:"), gbc);
        gbc.gridx = 1; gradeCourseIDField = new JTextField(15); panel.add(gradeCourseIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("成绩:"), gbc);
        gbc.gridx = 1; gradeField = new JTextField(15); panel.add(gradeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton insertButton = new JButton("插入");
        JButton updateButton = new JButton("更新");
        JButton deleteButton = new JButton("删除");
        JButton selectButton = new JButton("查询");
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);
        panel.add(buttonPanel, gbc);

        insertButton.addActionListener(e -> insertGrade());
        updateButton.addActionListener(e -> updateGrade());
        deleteButton.addActionListener(e -> deleteGrade());
        selectButton.addActionListener(e -> selectGrade());

        return panel;
    }

    private void insertStudent() {
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        boolean gender = genderField.getSelectedItem().equals("男");
        Date birthDate = Date.valueOf(birthDateField.getText());
        String major = majorField.getText();
        int totalCredits = Integer.parseInt(totalCreditsField.getText());
        String remarks = remarksField.getText();
        studentOps.insertStudent(studentID, name, gender, birthDate, major, totalCredits, remarks);
    }

    private void updateStudent() {
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        boolean gender = genderField.getSelectedItem().equals("男");
        Date birthDate = Date.valueOf(birthDateField.getText());
        String major = majorField.getText();
        int totalCredits = Integer.parseInt(totalCreditsField.getText());
        String remarks = remarksField.getText();
        studentOps.updateStudent(studentID, name, gender, birthDate, major, totalCredits, remarks);
    }

    private void deleteStudent() {
        String studentID = studentIDField.getText();
        studentOps.deleteStudent(studentID);
    }

    private void selectStudent() {
        String studentID = studentIDField.getText();
        ResultSet rs = studentOps.getStudent(studentID);
        displayResultSet(rs);
    }

    private void insertCourse() {
        String courseID = courseIDField.getText();
        String courseName = courseNameField.getText();
        int courseHours = Integer.parseInt(courseHoursField.getText());
        int courseCredits = Integer.parseInt(courseCreditsField.getText());
        courseOps.insertCourse(courseID, courseName, courseHours, courseCredits);
    }

    private void updateCourse() {
        String courseID = courseIDField.getText();
        String courseName = courseNameField.getText();
        int courseHours = Integer.parseInt(courseHoursField.getText());
        int courseCredits = Integer.parseInt(courseCreditsField.getText());
        courseOps.updateCourse(courseID, courseName, courseHours, courseCredits);
    }

    private void deleteCourse() {
        String courseID = courseIDField.getText();
        courseOps.deleteCourse(courseID);
    }

    private void selectCourse() {
        String courseID = courseIDField.getText();
        ResultSet rs = courseOps.getCourse(courseID);
        displayResultSet(rs);
    }

    private void insertClassroom() {
        String classroomID = classroomIDField.getText();
        String classroomName = classroomNameField.getText();
        int capacity = Integer.parseInt(classroomCapacityField.getText());
        String remarks = classroomRemarksField.getText();
        classroomOps.insertClassroom(classroomID, classroomName, capacity, remarks);
    }

    private void updateClassroom() {
        String classroomID = classroomIDField.getText();
        String classroomName = classroomNameField.getText();
        int capacity = Integer.parseInt(classroomCapacityField.getText());
        String remarks = classroomRemarksField.getText();
        classroomOps.updateClassroom(classroomID, classroomName, capacity, remarks);
    }

    private void deleteClassroom() {
        String classroomID = classroomIDField.getText();
        classroomOps.deleteClassroom(classroomID);
    }

    private void selectClassroom() {
        String classroomID = classroomIDField.getText();
        ResultSet rs = classroomOps.getClassroom(classroomID);
        displayResultSet(rs);
    }

    private void insertEnrollment() {
        String studentID = enrollmentStudentIDField.getText();
        String courseID = enrollmentCourseIDField.getText();
        String classroomID = enrollmentClassroomIDField.getText();
        enrollmentOps.insertEnrollment(studentID, courseID, classroomID);
    }

    private void updateEnrollment() {
        String studentID = enrollmentStudentIDField.getText();
        String courseID = enrollmentCourseIDField.getText();
        String classroomID = enrollmentClassroomIDField.getText();
        enrollmentOps.updateEnrollment(studentID, courseID, classroomID);
    }

    private void deleteEnrollment() {
        String studentID = enrollmentStudentIDField.getText();
        String courseID = enrollmentCourseIDField.getText();
        String classroomID = enrollmentClassroomIDField.getText();
        enrollmentOps.deleteEnrollment(studentID, courseID, classroomID);
    }

    private void selectEnrollment() {
        String studentID = enrollmentStudentIDField.getText();
        String courseID = enrollmentCourseIDField.getText();
        String classroomID = enrollmentClassroomIDField.getText();
        ResultSet rs = enrollmentOps.getEnrollment(studentID, courseID, classroomID);
        displayResultSet(rs);
    }

    private void insertGrade() {
        String studentID = gradeStudentIDField.getText();
        String courseID = gradeCourseIDField.getText();
        int grade = Integer.parseInt(gradeField.getText());
        gradeOps.insertGrade(studentID, courseID, grade);
    }

    private void updateGrade() {
        String studentID = gradeStudentIDField.getText();
        String courseID = gradeCourseIDField.getText();
        int grade = Integer.parseInt(gradeField.getText());
        gradeOps.updateGrade(studentID, courseID, grade);
    }

    private void deleteGrade() {
        String studentID = gradeStudentIDField.getText();
        String courseID = gradeCourseIDField.getText();
        gradeOps.deleteGrade(studentID, courseID);
    }

    private void selectGrade() {
        String studentID = gradeStudentIDField.getText();
        String courseID = gradeCourseIDField.getText();
        ResultSet rs = gradeOps.getGrade(studentID, courseID);
        displayResultSet(rs);
    }

    private void displayResultSet(ResultSet rs) {
        try {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(rs.getMetaData().getColumnName(i));
            }
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    if (rs.getMetaData().getColumnName(i + 1).equals("性别")) {
                        row[i] = rs.getBoolean(i + 1) ? "男" : "女";
                    } else {
                        row[i] = rs.getObject(i + 1);
                    }
                }
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("结果集显示失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            StudentManagementApp frame = new StudentManagementApp();
            frame.setVisible(true);
        });
    }
}
