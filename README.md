# 🏥 Hospital System Management

A simple desktop application built to manage the workflow within a hospital. This project demonstrates core concepts of object-oriented programming in Java and practical integration of databases and graphical user interfaces.

## 💻 Technologies Used

* **Java** – Core language used for the entire backend logic
* **Swing** – For building the graphical user interface (GUI)
* **JDBC (Java Database Connectivity)** – To connect and interact with the Microsoft SQL Server database
* **Microsoft SQL Server** – Used as the relational database for storing hospital data
* **File I/O** – Used to manage and load configurations from external files (e.g., database connection settings)

## 📌 Features

* Add, update, and delete patient records
* Manage hospital rooms and their availability
* Assign patients to rooms
* Track staying time and patient details
* Store and retrieve data using a secure SQL database
* Configurable setup using file-based configuration

## ⚙️ Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Omarioooo/HospitalSystem.git
   cd HospitalSystem
   ```

2. **Configure the Database:**

   * Set up your Microsoft SQL Server.
   * Run the provided SQL script (if available) to create necessary tables and stored procedures.
   * Update the configuration file `SQL_SERVER.txt` file with your connection details.

3. **Run the Application:**

   * Open the project in your favorite IDE (e.g., IntelliJ IDEA or Eclipse).
   * Make sure the MS SQL JDBC driver is included in the classpath.
   * Run the `Main.java` file.

## ✅ Future Improvements

* Add user authentication (login system for staff)
* Add role-based access (doctor, nurse, admin)
* Generate reports (daily admissions, discharge summaries, etc.)
* Improve UI design with modern Java UI libraries

## 🙌 Author

**Omar Mohamed**

📫 Email: [omarrmohammed86@gmail.com](mailto:omarrmohammed86@gmail.com)  
🔗 LinkedIn: [linkedin.com/in/your-profile](https://www.linkedin.com/in/your-profile)
---
