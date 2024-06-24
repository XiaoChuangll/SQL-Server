package com.sms.demo;

import java.sql.*;

public class CourseOperations {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public void insertCourse(String courseID, String courseName, int courseHours, int courseCredits) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call InsertCourse(?, ?, ?, ?)}")) {
            stmt.setString(1, courseID);
            stmt.setString(2, courseName);
            stmt.setInt(3, courseHours);
            stmt.setInt(4, courseCredits);
            stmt.executeUpdate();
            System.out.println("课程信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("课程信息插入失败：" + e.getMessage());
        }
    }

    public void updateCourse(String courseID, String courseName, int courseHours, int courseCredits) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call UpdateCourse(?, ?, ?, ?)}")) {
            stmt.setString(1, courseID);
            stmt.setString(2, courseName);
            stmt.setInt(3, courseHours);
            stmt.setInt(4, courseCredits);
            stmt.executeUpdate();
            System.out.println("课程信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("课程信息更新失败：" + e.getMessage());
        }
    }

    public void deleteCourse(String courseID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteCourse(?)}")) {
            stmt.setString(1, courseID);
            stmt.executeUpdate();
            System.out.println("课程信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("课程信息删除失败：" + e.getMessage());
        }
    }

    public ResultSet getCourse(String courseID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            CallableStatement stmt = conn.prepareCall("{call GetCourse(?)}");
            stmt.setString(1, courseID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("课程信息查询失败：" + e.getMessage());
            return null;
        }
    }
}
