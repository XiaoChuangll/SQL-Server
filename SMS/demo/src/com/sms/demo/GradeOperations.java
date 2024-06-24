package com.sms.demo;

import java.sql.*;

public class GradeOperations {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public void insertGrade(String studentID, String courseID, int grade) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call InsertGrade(?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setInt(3, grade);
            stmt.executeUpdate();
            System.out.println("成绩信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("成绩信息插入失败：" + e.getMessage());
        }
    }

    public void updateGrade(String studentID, String courseID, int grade) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call UpdateGrade(?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setInt(3, grade);
            stmt.executeUpdate();
            System.out.println("成绩信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("成绩信息更新失败：" + e.getMessage());
        }
    }

    public void deleteGrade(String studentID, String courseID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteGrade(?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.executeUpdate();
            System.out.println("成绩信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("成绩信息删除失败：" + e.getMessage());
        }
    }

    public ResultSet getGrade(String studentID, String courseID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            CallableStatement stmt = conn.prepareCall("{call GetGrade(?, ?)}");
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("成绩信息查询失败：" + e.getMessage());
            return null;
        }
    }
}
