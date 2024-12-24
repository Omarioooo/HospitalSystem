-- Create the database
CREATE DATABASE Hospital_System;

-- Use the created database
USE Hospital_System;

-- Create the Department table
CREATE TABLE Department (
    DepID INT PRIMARY KEY,
    DepName VARCHAR(100) NOT NULL,
    MangerID INT NOT NULL
);

-- Create the Doctor table
CREATE TABLE Doctor (
    DocID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    SecondName VARCHAR(50),
    ThirdName VARCHAR(50),
    Salary DECIMAL(10, 2) NOT NULL,
    SupervisorID INT
);

-- Create the Clinic table
CREATE TABLE Clinic (
    ClinicID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Zcode VARCHAR(5),
    Street VARCHAR(100),
    City VARCHAR(25),
    DocID INT NOT NULL
);

-- Create the Employee table
CREATE TABLE Employee (
    empID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    SecondName VARCHAR(50),
    ThirdName VARCHAR(50),
    Salary DECIMAL(10, 2) NOT NULL,
    Emp_Rule VARCHAR(50) NOT NULL
);

-- Create the Emp_Dep table
CREATE TABLE Emp_Dep (
    empID INT,
    DepID INT,
    StartingDate DATE,
    PRIMARY KEY (empID, DepID)
);

-- Create the Doc_Dep table
CREATE TABLE Doc_Dep (
    DocID INT,
    DepID INT,
    StartingDate DATE,
    PRIMARY KEY (DocID, DepID)
);

-- Create the Patient table
CREATE TABLE Patient (
    patientID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    SecondName VARCHAR(50),
    ThirdName VARCHAR(50),
    Gender CHAR(1) CHECK ( Gender IN ('M', 'F') ),
    Phone VARCHAR(20),
    City VARCHAR(25)
);

-- Create the Room table
CREATE TABLE Room (
    RoomID INT PRIMARY KEY,
    Capacity INT CHECK (Capacity BETWEEN 1 AND 4),
    Type VARCHAR(50),
    Availability VARCHAR(20) Check (Availability in ('Available', 'Not Available')) DEFAULT 'Available',
    DepID INT  NOT NULL
);

-- Create the Patient_Room table
CREATE TABLE Patient_Room (
    patientID INT,
    RoomID INT,
    StartingDate DATE,
    stayingTime INT,
    PRIMARY KEY (patientID, RoomID)
);

-- Create the Nurse table
CREATE TABLE Nurse (
    NurseID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    SecondName VARCHAR(50),
    ThirdName VARCHAR(50),
    Salary DECIMAL(10, 2) NOT NULL
);

-- Create the Room_Nurse table
CREATE TABLE Room_Nurse (
    RoomID INT,
	NurseID INT
	PRIMARY KEY (RoomID, NurseID)
);


-- Create the Appointment table
CREATE TABLE Appointment (
    AppoID INT PRIMARY KEY,
    Status VARCHAR(50),
    ClinicID INT NOT NULL
);

-- Create the Appointment_Patient_Doc table
CREATE TABLE Appointment_Patient_Doc (
    AppoID INT,
    patientID INT,
    DocID INT,
    Date DATE,
    Cost DECIMAL(10, 2),
    PRIMARY KEY (AppoID, patientID, DocID)
);

-- Alter to add foreign key constraints

-- Add foreign key constraints for Department table
ALTER TABLE Department
ADD FOREIGN KEY (MangerID) REFERENCES Doctor(DocID);

-- Add foreign key constraints for Doctor table
ALTER TABLE Doctor
ADD FOREIGN KEY (SupervisorID) REFERENCES Doctor(DocID);

-- Add foreign key constraints for Clinic table
ALTER TABLE Clinic
ADD FOREIGN KEY (DocID) REFERENCES Doctor(DocID);

-- Add foreign key constraints for Emp_Dep table
ALTER TABLE Emp_Dep
ADD FOREIGN KEY (empID) REFERENCES Employee(empID),
    FOREIGN KEY (DepID) REFERENCES Department(DepID);

-- Add foreign key constraints for Doc_Dep table
ALTER TABLE Doc_Dep
ADD FOREIGN KEY (DocID) REFERENCES Doctor(DocID),
    FOREIGN KEY (DepID) REFERENCES Department(DepID);

-- Add foreign key constraints for Patient_Room table
ALTER TABLE Patient_Room
ADD FOREIGN KEY (patientID) REFERENCES Patient(patientID),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID);

-- Add foreign key constraints for Room_Dep table
ALTER TABLE Room
ADD FOREIGN KEY (DepID) REFERENCES Department(DepID);

-- Add foreign key constraints for Appointment table
ALTER TABLE Appointment
ADD FOREIGN KEY (ClinicID) REFERENCES Clinic(ClinicID);

-- Add foreign key constraints for Appointment_Patient_Doc table
ALTER TABLE Appointment_Patient_Doc
ADD FOREIGN KEY (AppoID) REFERENCES Appointment(AppoID),
    FOREIGN KEY (patientID) REFERENCES Patient(patientID),
    FOREIGN KEY (DocID) REFERENCES Doctor(DocID)
    FOREIGN KEY (Clinic_ID) REFERENCES Clinic(ClinicID);

-- Add foreign key constraints for Room_Nurse table
ALTER TABLE Room_Nurse
ADD FOREIGN KEY (RoomID) REFERENCES Room(RoomID),
    FOREIGN KEY (NurseID) REFERENCES Nurse(NurseID);

