package com.companyz.controllers;

import com.companyz.models.Employee;
import com.companyz.models.Address;
import com.companyz.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeController {
    @FXML private Label lblWelcome;
    @FXML private Label lblPersonalInfo;
    @FXML private Label lblAddress;
    @FXML private Label lblPayrollHistory;

    private String empId; // The ID of the logged-in employee

    public void initData(String empId) {
        this.empId = empId;
        loadEmployeeData(empId);
    }

    private void loadEmployeeData(String empId) {
        Employee emp = EmployeeService.getEmployeeById(empId);
        if (emp != null) {
            lblWelcome.setText("Welcome, " + emp.getFirstName());
            lblPersonalInfo.setText(
                "Name: " + emp.getFirstName() + " " + emp.getLastName() +
                "\nGender: " + emp.getGender() +
                "\nDOB: " + emp.getDob() +
                "\nHire Date: " + emp.getHireDate() +
                "\nSalary: " + emp.getSalary()
            );

            // Load address
            Address addr = EmployeeService.getAddressForEmployee(empId);
            if (addr != null) {
                lblAddress.setText("Address: " + addr.getStreet() + ", " +
                                   addr.getCityName() + ", " +
                                   addr.getStateName() + " " + addr.getZip());
            }

           }
    }
}
