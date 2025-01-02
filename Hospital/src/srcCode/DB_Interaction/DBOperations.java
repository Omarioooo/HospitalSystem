package srcCode.DB_Interaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBOperations {

    private DBConnection connection;

    public DBOperations(DBConnection connection) {
        this.connection = connection;
        connection.Connect();
    }

    public DBOperations() {
    }

    // A method to insert patients
    public void insertPatient(int patientID, String fName, String secName, String trdName,
                              String gender, int phone, String city, int roomID, int stayingTime) throws SQLException {

        // Define the stored procedure call
        String insertQuery = "EXEC insertPatientIntoRoom ?, ?, ?, ?, ?, ?, ?, ?, ?";

        PreparedStatement stmt = connection.getConnection().prepareStatement(insertQuery);

        // Set the parameters for the stored procedure
        stmt.setInt(1, patientID);
        stmt.setString(2, fName);
        stmt.setString(3, secName);
        stmt.setString(4, trdName);
        stmt.setString(5, gender);
        stmt.setInt(6, phone);
        stmt.setString(7, city);
        stmt.setInt(8, roomID);
        stmt.setInt(9, stayingTime);

        stmt.executeUpdate();

        // Close the statement
        stmt.close();
    }

    // A method to add patient and clinic to an appointment
    public void bookAppointment(int patientID, String fName, String secName, String trdName,
                                int phone, String city, String gender, String clinicName, int appointmentID, int cost, String status) throws SQLException {

        // Define the stored procedure call
        String query = "EXEC bookAppointment ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement stmt = connection.getConnection().prepareStatement(query);

        stmt.setInt(1, patientID);
        stmt.setString(2, fName);
        stmt.setString(3, secName);
        stmt.setString(4, trdName);
        stmt.setInt(5, phone);
        stmt.setString(6, city);
        stmt.setString(7, gender);
        stmt.setString(8, clinicName);
        stmt.setInt(9, appointmentID);
        stmt.setInt(10, cost);
        stmt.setString(11, status);

        stmt.executeUpdate();

        // Close the statement
        stmt.close();
    }

    // A method to search for a patient
    public String searchForPatientsPlace(int patientID) throws SQLException {

        // the needed data for handling the query
        String searchQuery = "EXEC search_patients ?, ?, ?";
        String resultMessage;
        String placeFoundIn;
        CallableStatement stmt;

        // prepare the statement
        stmt = connection.getConnection().prepareCall(searchQuery);

        // input variables
        stmt.setInt(1, patientID);

        // output variables
        stmt.registerOutParameter(2, Types.VARCHAR);
        stmt.registerOutParameter(3, Types.VARCHAR);

        // execute the stored procedure
        stmt.execute();

        // retrieve output parameters
        resultMessage = stmt.getString(2);
        placeFoundIn = stmt.getString(3);

        if (resultMessage.equalsIgnoreCase("The patient is found")) {
            return placeFoundIn;
        }
        return resultMessage;
    }

    public List<String> searchIntoRoom(int patientID) throws SQLException {
        List<String> list = new ArrayList<>();

        // the needed data for handling the query
        String searchQuery = "EXEC search_on_rooms ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        CallableStatement stmt;

        // prepare the statement
        stmt = connection.getConnection().prepareCall(searchQuery);

        // input variable
        stmt.setInt(1, patientID);  // PatientID

        // output Variable
        prepareDataForRoom(stmt);

        // execute the stored procedure
        stmt.execute();

        // adding to list
        fillListWithRoomData(list, stmt);

        return list;
    }

    public List<String> searchIntoClinic(int patientID) throws SQLException {
        List<String> list = new ArrayList<>();

        // the needed data for handling the query
        String searchQuery = "EXEC search_on_clinics ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        CallableStatement stmt;

        // prepare the statement
        stmt = connection.getConnection().prepareCall(searchQuery);

        // input variable
        stmt.setInt(1, patientID);  // PatientID

        // output Variable
        prepareDataForClinic(stmt);

        // execute the stored procedure
        stmt.execute();

        // adding to list
        fillListWithClinicData(list, stmt);

        return list;
    }

    public String updatePatientInfo(int patientID, String firstName, String secondName, String thirdName, int phone, String city, String gender) throws SQLException {
        // The needed data for handling the query
        String updateQuery = "EXEC update_patient_data ?, ?, ?, ?, ?, ?, ?, ?";
        String resultMessage;
        CallableStatement stmt;

        stmt = connection.getConnection().prepareCall(updateQuery);

        // input variable
        stmt.setInt(1, patientID);
        stmt.setString(2, firstName);
        stmt.setString(3, secondName);
        stmt.setString(4, thirdName);
        stmt.setInt(5, phone);
        stmt.setString(6, city);
        stmt.setString(7, gender);

        // output variable
        stmt.registerOutParameter(8, Types.VARCHAR);

        stmt.execute();

        resultMessage = stmt.getString(8);

        return resultMessage;
    }

    public String removePatient(int patientID) throws SQLException {

        // the needed data for handling the query
        String removeQuery = "EXEC delete_patient ?,?";
        String resultMessage;
        CallableStatement stmt;

        // prepare execution the query
        stmt = connection.getConnection().prepareCall(removeQuery);

        // input variable
        stmt.setInt(1, patientID);

        // output variable
        stmt.registerOutParameter(2, Types.VARCHAR);

        // execute the query
        stmt.execute();

        resultMessage = stmt.getString(2);

        return resultMessage;
    }


    // A method to show the departments info
    public ResultSet showDepartmentInfo() {
        String queryForDepInfo = "EXEC select_Dep_info";
        ResultSet rs;
        PreparedStatement stmt;

        try {
            // Prepare the query
            stmt = connection.getConnection().prepareStatement(queryForDepInfo);

            // Execute the query and get the ResultSet
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching department info: " + e.getMessage(), e);
        }

        return rs;
    }

    // A method to show the rooms info
    public ResultSet showRoomInfo() {
        String queryForDepInfo = "EXEC room_info";
        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            // Prepare the query
            stmt = connection.getConnection().prepareStatement(queryForDepInfo);

            // Execute the query and get the ResultSet
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching room info: " + e.getMessage(), e);
        }

        return rs;
    }

    // A method to show the clinics info
    public ResultSet showClinicInfo() {
        String queryForDepInfo = "EXEC clinic_info";

        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            // Prepare the query
            stmt = connection.getConnection().prepareStatement(queryForDepInfo);

            // Execute the query and get the ResultSet
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching clinic info: " + e.getMessage(), e);
        }

        return rs;
    }

    // A method to show nurse info
    public ResultSet showNurseInfo() {
        String queryForDepInfo = "EXEC nurse_info";
        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            // Prepare the query
            stmt = connection.getConnection().prepareStatement(queryForDepInfo);

            // Execute the query and get the ResultSet
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching clinic info: " + e.getMessage(), e);
        }

        return rs;
    }

    private void prepareDataForRoom(CallableStatement stmt) throws SQLException {
        // Register output parameters for the Room procedure
        stmt.registerOutParameter(2, Types.VARCHAR); // First Name
        stmt.registerOutParameter(3, Types.VARCHAR); // Second Name
        stmt.registerOutParameter(4, Types.VARCHAR); // Third Name
        stmt.registerOutParameter(5, Types.VARCHAR); // Phone
        stmt.registerOutParameter(6, Types.VARCHAR); // City
        stmt.registerOutParameter(7, Types.VARCHAR); // Gender
        stmt.registerOutParameter(8, Types.VARCHAR); // Department
        stmt.registerOutParameter(9, Types.INTEGER); // RoomID
        stmt.registerOutParameter(10, Types.DATE); // Starting Date
        stmt.registerOutParameter(11, Types.INTEGER); // Remaining Time

    }


    private void prepareDataForClinic(CallableStatement stmt) throws SQLException {
        // Register output parameters for the Clinic procedure
        stmt.registerOutParameter(2, Types.VARCHAR); // First Name
        stmt.registerOutParameter(3, Types.VARCHAR); // Second Name
        stmt.registerOutParameter(4, Types.VARCHAR); // Third Name
        stmt.registerOutParameter(5, Types.VARCHAR); // Phone
        stmt.registerOutParameter(6, Types.VARCHAR); // City
        stmt.registerOutParameter(7, Types.VARCHAR); // Gender
        stmt.registerOutParameter(8, Types.VARCHAR); // Department
        stmt.registerOutParameter(9, Types.VARCHAR); // Clinic
        stmt.registerOutParameter(10, Types.INTEGER); // Appointment ID
        stmt.registerOutParameter(11, Types.DATE); // Starting Date
    }

    private void fillListWithRoomData(List<String> list, CallableStatement stmt) throws SQLException {
        list.add(stmt.getString(2)); // First Name
        list.add(stmt.getString(3)); // Second Name
        list.add(stmt.getString(4)); // Third Name
        list.add(stmt.getString(5)); // Phone
        list.add(stmt.getString(6)); // City
        list.add(stmt.getString(7)); // Gender
        list.add(stmt.getString(8)); // Department
        list.add("Room " + stmt.getInt(9)); //RoomID
        list.add("" + stmt.getDate(10)); //Starting Date
        list.add("Remaining " + stmt.getInt(11) + " days"); // Remaining Time
    }

    private void fillListWithClinicData(List<String> list, CallableStatement stmt) throws SQLException {
        list.add(stmt.getString(2)); // First Name
        list.add(stmt.getString(3)); // Second Name
        list.add(stmt.getString(4)); // Third Name
        list.add(stmt.getString(5)); // Phone
        list.add(stmt.getString(6)); // City
        list.add(stmt.getString(7)); // Gender
        list.add(stmt.getString(8)); // Department
        list.add(stmt.getString(9)); // Clinic
        list.add("Appointment " + stmt.getInt(10)); // Appointment ID
        list.add("" + stmt.getDate(11)); // Starting Date
    }


    // A method to check the login info
    public boolean logInCheck(String name, int id) throws SQLException {
        String queryForCheck = "SELECT * FROM employee WHERE empId = ? AND FirstName+' '+SecondName = ? AND Emp_rule LIKE '%Officer'";

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(queryForCheck)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if a row is found
            }

        } catch (SQLException e) {
            e.getStackTrace();
            return false;
        }
    }

}
