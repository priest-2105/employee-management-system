package com.companyz.security;

public class SecurityContext {
    private static ThreadLocal<String> currentEmpId = new ThreadLocal<>();
    private static ThreadLocal<String> currentRole = new ThreadLocal<>();

    public static void setCurrentUser(String empId, String role) {
        currentEmpId.set(empId);
        currentRole.set(role);
    }

    public static void clearCurrentUser() {
        currentEmpId.remove();
        currentRole.remove();
    }

    public static String getCurrentEmpId() {
        return currentEmpId.get();
    }

    public static String getCurrentRole() {
        return currentRole.get();
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase(getCurrentRole());
    }

    public static boolean isEmployee() {
        return "employee".equalsIgnoreCase(getCurrentRole());
    }
}
