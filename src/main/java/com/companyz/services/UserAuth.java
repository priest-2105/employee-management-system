package com.companyz.services;

import com.companyz.security.SecurityContext;
import java.sql.*;

public class UserAuth {
    public static boolean login(String empId, String password, String role) {
        String query = "SELECT empid, role FROM employees WHERE empid = ? AND password = ? AND role = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, empId);
            stmt.setString(2, hashPassword(password)); // TODO: Implement proper password hashing
            stmt.setString(3, role.toLowerCase());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                SecurityContext.setCurrentUser(rs.getString("empid"), rs.getString("role"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void logout() {
        SecurityContext.clearCurrentUser();
    }

    private static String hashPassword(String password) {
        // TODO: Implement proper password hashing
        return password;
    }
}
