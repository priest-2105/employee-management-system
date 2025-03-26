package com.companyz.models;

import java.util.Date;

public class PayrollHistory {
    private String payrollId;
    private String empId;
    private Date payDate;
    private double amount;
    private String payPeriod;
    private double deductions;
    private double netPay;

    public PayrollHistory(String payrollId, String empId, Date payDate, 
                         double amount, String payPeriod, double deductions) {
        this.payrollId = payrollId;
        this.empId = empId;
        this.payDate = payDate;
        this.amount = amount;
        this.payPeriod = payPeriod;
        this.deductions = deductions;
        this.netPay = amount - deductions;
    }

    // Getters and Setters
    public String getPayrollId() { return payrollId; }
    public String getEmpId() { return empId; }
    public Date getPayDate() { return payDate; }
    public double getAmount() { return amount; }
    public String getPayPeriod() { return payPeriod; }
    public double getDeductions() { return deductions; }
    public double getNetPay() { return netPay; }
}
