package com.companyz.controllers;

import com.companyz.services.UserAuth;
import com.companyz.security.SecurityContext;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LoginController {

    @FXML private TextField txtEmpId;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        String empId = txtEmpId.getText();
        String password = txtPassword.getText();
        String role = cbRole.getValue();

        // Call UserAuth service to validate
        boolean isValid = UserAuth.login(empId, password, role);
        
        if (isValid) {
            System.out.println("Login success for role: " + role);
            loadDashboard(role, empId);
        } else {
            lblError.setText("Invalid credentials!");
            lblError.setVisible(true);
        }
    }

    private void loadDashboard(String role, String empId) {
        try {
            Stage stage = (Stage) txtEmpId.getScene().getWindow();
            FXMLLoader loader;

            if (role.equalsIgnoreCase("admin")) {
                loader = new FXMLLoader(getClass().getResource("/com/companyz/views/AdminDashboard.fxml"));
                stage.setTitle("Admin Dashboard");
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/companyz/views/EmployeeDashboard.fxml"));
                stage.setTitle("Employee Dashboard");
            }
            Scene scene = new Scene(loader.load());

         
            if (role.equalsIgnoreCase("admin")) {
                AdminController adminCtrl = loader.getController();

            } else {
                EmployeeController empCtrl = loader.getController();
                empCtrl.initData(empId); 
            }

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
