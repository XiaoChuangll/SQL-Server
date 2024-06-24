package com.sms.demo;

import java.sql.*;

public class ClassroomOperations {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=SMS";
    private final String DB_USER = "ddl2024";
    private final String DB_PASSWORD = "Ddl2024@123";

    public void insertClassroom(String classroomID, String classroomName, int capacity, String remarks) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call InsertClassroom(?, ?, ?, ?)}")) {
            stmt.setString(1, classroomID);
            stmt.setString(2, classroomName);
            stmt.setInt(3, capacity);
            stmt.setString(4, remarks);
            stmt.executeUpdate();
            System.out.println("教室信息插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("教室信息插入失败：" + e.getMessage());
        }
    }

    public void updateClassroom(String classroomID, String classroomName, int capacity, String remarks) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call UpdateClassroom(?, ?, ?, ?)}")) {
            stmt.setString(1, classroomID);
            stmt.setString(2, classroomName);
            stmt.setInt(3, capacity);
            stmt.setString(4, remarks);
            stmt.executeUpdate();
            System.out.println("教室信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("教室信息更新失败：" + e.getMessage());
        }
    }

    public void deleteClassroom(String classroomID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{call DeleteClassroom(?)}")) {
            stmt.setString(1, classroomID);
            stmt.executeUpdate();
            System.out.println("教室信息删除成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("教室信息删除失败：" + e.getMessage());
        }
    }

    public ResultSet getClassroom(String classroomID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            CallableStatement stmt = conn.prepareCall("{call GetClassroom(?)}");
            stmt.setString(1, classroomID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("教室信息查询失败：" + e.getMessage());
            return null;
        }
    }
}
