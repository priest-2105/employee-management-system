package com.companyz.services;

import com.companyz.database.DatabaseConnector;
import com.companyz.models.*;
import com.companyz.security.SecurityContext;
import java.sql.*;
import java.util.*;

public class EmployeeService {

    public static List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT e.empid, e.first_name, e.last_name, e.hire_date, " +
                       "e.salary, e.gender, e.dob, e.email, d.name AS division " +
                       "FROM employees e " +
                       "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                       "LEFT JOIN division d ON ed.div_id = d.div_id";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("empid"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("hire_date"),
                        rs.getDouble("salary"),
                        rs.getString("gender"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("division")
                );
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Employee getEmployee(String empId) {
        if (!SecurityContext.isAdmin() && 
            !empId.equals(SecurityContext.getCurrentEmpId())) {
            throw new SecurityException("Access denied");
        }
        String query = "SELECT e.*, d.name AS division " +
                       "FROM employees e " +
                       "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                       "LEFT JOIN division d ON ed.div_id = d.div_id " +
                       "WHERE e.empid = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getString("empid"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("hire_date"),
                        rs.getDouble("salary"),
                        rs.getString("gender"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("division")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Address getAddressForEmployee(String empId) {
        String query = "SELECT a.street, a.city, s.name as state, a.zipcode, a.country " +
                      "FROM employee_address ea " +
                      "JOIN address a ON ea.address_id = a.id " +
                      "JOIN state s ON a.state_id = s.id " +
                      "WHERE ea.empid = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Address(
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zipcode"),
                    rs.getString("country")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PayrollHistory> getPayrollHistory(String empId) {
        List<PayrollHistory> history = new ArrayList<>();
        String query = "SELECT * FROM payroll WHERE empid = ? ORDER BY pay_date DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                history.add(new PayrollHistory(
                    rs.getString("payroll_id"),
                    rs.getString("empid"),
                    rs.getDate("pay_date"),
                    rs.getDouble("amount"),
                    rs.getString("pay_period"),
                    rs.getDouble("deductions")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public static boolean createEmployee(Employee emp, String divisionId) {
        if (!SecurityContext.isAdmin()) {
            throw new SecurityException("Only admins can create employees");
        }
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);
            
            // Insert into employees table
            String empQuery = "INSERT INTO employees (empid, name, salary) VALUES (?, ?, ?)";
            PreparedStatement empStmt = conn.prepareStatement(empQuery);
            empStmt.setString(1, emp.getEmpid());
            empStmt.setString(2, emp.getName());
            empStmt.setDouble(3, emp.getSalary());
            empStmt.executeUpdate();
            
            // Insert into employee_division table
            String divQuery = "INSERT INTO employee_division (empid, div_id) VALUES (?, ?)";
            PreparedStatement divStmt = conn.prepareStatement(divQuery);
            divStmt.setString(1, emp.getEmpid());
            divStmt.setString(2, divisionId);
            divStmt.executeUpdate();
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateEmployeeSalary(String empId, double newSalary) {
        if (!SecurityContext.isAdmin()) {
            throw new SecurityException("Only admins can update salaries");
        }
        String query = "UPDATE employees SET salary = ? WHERE empid = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newSalary);
            stmt.setString(2, empId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Employee> searchEmployees(String keyword) {
        List<Employee> results = new ArrayList<>();
        String query = "SELECT e.empid, e.name, d.name AS division, e.salary " +
                      "FROM employees e " +
                      "JOIN employee_division ed ON e.empid = ed.empid " +
                      "JOIN division d ON ed.div_id = d.ID " +
                      "WHERE e.empid LIKE ? OR e.name LIKE ? OR d.name LIKE ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Employee(
                    rs.getString("empid"),
                    rs.getString("name"),
                    rs.getString("division"),
                    rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
