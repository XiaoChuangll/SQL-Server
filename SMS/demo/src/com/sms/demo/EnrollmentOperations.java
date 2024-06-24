package com.sms.demo;

import java.sql.*;

public class EnrollmentOperations {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public void insertEnrollment(String studentID, String courseID, String classroomID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call InsertEnrollment(?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setString(3, classroomID);
            stmt.executeUpdate();
            System.out.println("选课信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("选课信息插入失败：" + e.getMessage());
        }
    }

    public void updateEnrollment(String studentID, String courseID, String classroomID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call UpdateEnrollment(?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setString(3, classroomID);
            stmt.executeUpdate();
            System.out.println("选课信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("选课信息更新失败：" + e.getMessage());
        }
    }

    public void deleteEnrollment(String studentID, String courseID, String classroomID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteEnrollment(?, ?, ?)}")) {
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setString(3, classroomID);
            stmt.executeUpdate();
            System.out.println("选课信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("选课信息删除失败：" + e.getMessage());
        }
    }

    public ResultSet getEnrollment(String studentID, String courseID, String classroomID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            CallableStatement stmt = conn.prepareCall("{call GetEnrollment(?, ?, ?)}");
            stmt.setString(1, studentID);
            stmt.setString(2, courseID);
            stmt.setString(3, classroomID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("选课信息查询失败：" + e.getMessage());
            return null;
        }
    }
}
