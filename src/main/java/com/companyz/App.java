package com.companyz;

import java.util.Scanner;
import com.companyz.services.UserAuth;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Employee ID: ");
        String empId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Role (admin/employee): ");
        String role = scanner.nextLine();

        if (UserAuth.login(empId, password, role)) {
            System.out.println("Login successful as " + role);
            if ("admin".equalsIgnoreCase(role)) {
                AdminDashboard.start();
            } else {
                EmployeeDashboard.start(empId);
            }
        } else {
            System.out.println("Invalid credentials!");
        }

      scanner.close();
    }
}
