-- Core tables
CREATE TABLE employees (
    empid VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    hire_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    gender CHAR(1),
    dob DATE,
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE state (
    state_id INT PRIMARY KEY AUTO_INCREMENT,
    state_name VARCHAR(50) NOT NULL,
    state_code CHAR(2) NOT NULL UNIQUE
);

CREATE TABLE city (
    city_id INT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(50) NOT NULL,
    state_id INT,
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

CREATE TABLE address (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    empid VARCHAR(20),
    street VARCHAR(100),
    city_id INT,
    state_id INT,
    zipcode VARCHAR(10),
    mobile_phone VARCHAR(15),
    identified_race VARCHAR(50),
    FOREIGN KEY (empid) REFERENCES employees(empid),
    FOREIGN KEY (city_id) REFERENCES city(city_id),
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

CREATE TABLE division (
    div_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10),
    address_line1 VARCHAR(100),
    address_line2 VARCHAR(100),
    state_id INT,
    country VARCHAR(50),
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

CREATE TABLE employee_division (
    empid VARCHAR(20),
    div_id INT,
    PRIMARY KEY (empid, div_id),
    FOREIGN KEY (empid) REFERENCES employees(empid),
    FOREIGN KEY (div_id) REFERENCES division(div_id)
);

CREATE TABLE job_titles (
    job_title_id INT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE employee_job_titles (
    empid VARCHAR(20),
    job_title_id INT,
    start_date DATE NOT NULL,
    end_date DATE,
    PRIMARY KEY (empid, job_title_id, start_date),
    FOREIGN KEY (empid) REFERENCES employees(empid),
    FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id)
);

CREATE TABLE payroll (
    payroll_id INT PRIMARY KEY AUTO_INCREMENT,
    empid VARCHAR(20),
    pay_date DATE NOT NULL,
    gross_pay DECIMAL(10,2) NOT NULL,
    fed_tax DECIMAL(10,2),
    state_tax DECIMAL(10,2),
    fed_401k DECIMAL(10,2),
    health_care DECIMAL(10,2),
    other_deductions DECIMAL(10,2),
    net_pay DECIMAL(10,2),
    pay_period VARCHAR(20),
    FOREIGN KEY (empid) REFERENCES employees(empid)
);

-- Indexes for better performance
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_employee_name ON employees(last_name, first_name);
CREATE INDEX idx_address_zipcode ON address(zipcode);
CREATE INDEX idx_division_name ON division(name);
