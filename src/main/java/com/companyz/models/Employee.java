package com.companyz.models;

import java.time.LocalDate;

public class Employee {
    private String empid;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private double salary;
    private String gender;
    private LocalDate dob;
    private String email;
    private String role;
    private String password;

    // Constructors
    public Employee() { }
    
    public Employee(String empid, String firstName, String lastName, LocalDate hireDate,
                    double salary, String gender, LocalDate dob, String email,
                    String role, String password) {
        this.empid = empid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.salary = salary;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters/Setters
    public String getEmpid() { return empid; }
    public void setEmpid(String empid) { this.empid = empid; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
