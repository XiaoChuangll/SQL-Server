package com.sms.demo;

import java.sql.*;

public class StudentOperations {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public void insertStudent(String studentID, String name, boolean gender, Date birthDate, String major, int totalCredits, String remarks) {
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
            System.out.println("学生信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("学生信息插入失败：" + e.getMessage());
        }
    }

    public void updateStudent(String studentID, String name, boolean gender, Date birthDate, String major, int totalCredits, String remarks) {
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
            System.out.println("学生信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("学生信息更新失败：" + e.getMessage());
        }
    }

    public void deleteStudent(String studentID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteStudent(?)}")) {
            stmt.setString(1, studentID);
            stmt.executeUpdate();
            System.out.println("学生信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("学生信息删除失败：" + e.getMessage());
        }
    }

    public ResultSet getStudent(String studentID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            CallableStatement stmt = conn.prepareCall("{call GetStudent(?)}");
            stmt.setString(1, studentID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("学生信息查询失败：" + e.getMessage());
            return null;
        }
    }
}
