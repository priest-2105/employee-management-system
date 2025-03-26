package com.companyz.controllers;

import com.companyz.models.Employee;
import com.companyz.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminController {

    @FXML private TableView<Employee> tableEmployees;
    @FXML private TableColumn<Employee, String> colEmpId;
    @FXML private TableColumn<Employee, String> colName;
    @FXML private TableColumn<Employee, Double> colSalary;

    
   
    @FXML private TextField txtSearch;
    @FXML private TextField txtEmpId;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;

    
    @FXML
    public void initialize() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    
    }

    @FXML
    private void loadEmployees() {
        List<Employee> employees = EmployeeService.getAllEmployees();
        tableEmployees.getItems().setAll(employees);
    }

    @FXML
    private void searchEmployee() {
        String keyword = txtSearch.getText();
        List<Employee> results = EmployeeService.searchEmployees(keyword);
        tableEmployees.getItems().setAll(results);
    }

    @FXML
    private void createEmployee() {
     
        String empId = txtEmpId.getText();
        String fName = txtFirstName.getText();
        String lName = txtLastName.getText();
       
        
        Employee emp = new Employee(empId, fName, lName, /*...*/ null, 0.0, null, null, null);
        
        EmployeeService.createEmployee(emp);
        
        loadEmployees();
    }

    @FXML
    private void updateSalary() {
      
        String empId = "E123";
        double newSalary = 75000.0;
        EmployeeService.updateEmployeeSalary(empId, newSalary);
        loadEmployees();
    }

    @FXML
    private void generateReports() {
    
    
    }
}
