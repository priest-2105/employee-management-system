package com.companyz.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserAuth {

 
    public static boolean login(String empId, String password, String role) {
        try (Connection conn = DatabaseConnector.getConnection()) {
    
            String query = "SELECT * FROM employees WHERE empid = ? AND password = ? AND role = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, empId);
            stmt.setString(2, password);
            stmt.setString(3, role);

            // Execute query
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (Exception e) {
            e.printStackTrace();
            return false;  
        }
    }
}
