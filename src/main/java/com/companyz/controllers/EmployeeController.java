package com.companyz.controllers;

import com.companyz.models.*;
import com.companyz.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

public class EmployeeController {
    @FXML private Label lblEmpId;
    @FXML private Label lblName;
    @FXML private Label lblHireDate;
    @FXML private Label lblDivision;
    @FXML private Label lblJobTitle;
    
    @FXML private Label lblStreet;
    @FXML private Label lblCity;
    @FXML private Label lblState;
    @FXML private Label lblZipCode;
    
    @FXML private TableView<PayrollHistory> tblPayroll;
    @FXML private TableColumn<PayrollHistory, Date> colPayDate;
    @FXML private TableColumn<PayrollHistory, Double> colGrossPay;
    @FXML private TableColumn<PayrollHistory, Double> colFedTax;
    @FXML private TableColumn<PayrollHistory, Double> colStateTax;
    @FXML private TableColumn<PayrollHistory, Double> colDeductions;
    @FXML private TableColumn<PayrollHistory, Double> colNetPay;

    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void initialize() {
        setupPayrollTable();
    }

    private void setupPayrollTable() {
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        colGrossPay.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colFedTax.setCellValueFactory(new PropertyValueFactory<>("fedTax"));
        colStateTax.setCellValueFactory(new PropertyValueFactory<>("stateTax"));
        colDeductions.setCellValueFactory(new PropertyValueFactory<>("deductions"));
        colNetPay.setCellValueFactory(new PropertyValueFactory<>("netPay"));

        // Format currency columns
        formatCurrencyColumn(colGrossPay);
        formatCurrencyColumn(colFedTax);
        formatCurrencyColumn(colStateTax);
        formatCurrencyColumn(colDeductions);
        formatCurrencyColumn(colNetPay);
    }

    private void formatCurrencyColumn(TableColumn<PayrollHistory, Double> column) {
        column.setCellFactory(tc -> new TableCell<PayrollHistory, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(currencyFormatter.format(value));
                }
            }
        });
    }

    public void initData(String empId) {
        Employee emp = EmployeeService.getEmployee(empId);
        if (emp != null) {
            // Set personal information
            lblEmpId.setText(emp.getEmpid());
            lblName.setText(emp.getFirstName() + " " + emp.getLastName());
            lblHireDate.setText(emp.getHireDate().format(dateFormatter));
            lblDivision.setText(emp.getDivision());

            // Load and set address
            Address addr = EmployeeService.getAddressForEmployee(empId);
            if (addr != null) {
                lblStreet.setText(addr.getStreet());
                lblCity.setText(addr.getCity());
                lblState.setText(addr.getState());
                lblZipCode.setText(addr.getZipCode());
            }

            // Load payroll history
            tblPayroll.getItems().setAll(EmployeeService.getPayrollHistory(empId));
        }
    }
}
