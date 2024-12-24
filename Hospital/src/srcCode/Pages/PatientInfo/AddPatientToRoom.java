package srcCode.Pages.PatientInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.InsertingDataPage;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class AddPatientToRoom extends InsertingDataPage {
    private JTextField numOfDaysToStay;

    public AddPatientToRoom(DBConnection connection) {
        this.connection = connection;

        // create the title panel
        setFrameTitle("Add new patient");


        setPlaceLbl("Room ID : ");
        setStartingDateLbl("Starting Date : ");

        createLabel("Number of days : ", 420, 230);
        numOfDaysToStay = createTextField(590, 230);


        pageJob = createButton(500, "Add");
        pageJob.addActionListener(new ButtonHandler());

        clear = createButton(360, "CLEAR");
        clear.addActionListener(new ButtonHandler());

        back = createButton(220, "BACK");
        clear.addActionListener(new ButtonHandler());


        setVisible(true);
    }


    @Override
    public JComboBox<String> placeBox(int x_axis, int y_axis) {
        String[] rooms = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
                "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
                "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
                "91", "92", "93", "94", "95", "96", "97", "98", "99", "100",
                "101", "102", "103", "104", "105", "106", "107", "108", "109", "110",
                "111", "112", "113", "114", "115", "116", "117", "118", "119", "120",
                "121", "122", "123", "124", "125", "126", "127", "128", "129", "130",
                "131", "132", "133", "134", "135"
        };

        placeData = createCompoBox(x_axis, y_axis, rooms);

        return placeData;
    }

    @Override
    public JComboBox<String> departmentsBox(int x_axis, int y_axis) {
        String[] hospitalDepartments = {
                "Heart",
                "Neurology",
                "Orthopedics",
                "Pediatrics",
                "General Surgery",
                "Radiology",
                "Anesthesiology",
                "Oncology",
                "Dermatology",
                "Psychiatry"
        };

        departments = createCompoBox(x_axis, y_axis, hospitalDepartments);
        placeMapping();
        departments.addActionListener(e -> updatePlaceComboBox());

        return departments;

    }

    @Override
    public void placeMapping() {
        mapDepartmentsToPlace.put("Heart", Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"));
        mapDepartmentsToPlace.put("Neurology", Arrays.asList("16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"));
        mapDepartmentsToPlace.put("Orthopedics", Arrays.asList("31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"));
        mapDepartmentsToPlace.put("Pediatrics", Arrays.asList("46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"));
        mapDepartmentsToPlace.put("General Surgery", Arrays.asList("61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75"));
        mapDepartmentsToPlace.put("Radiology", Arrays.asList("76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90"));
        mapDepartmentsToPlace.put("Anesthesiology", Arrays.asList("91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105"));
        mapDepartmentsToPlace.put("Oncology", Arrays.asList("106", "107", "108", "109", "110"));
        mapDepartmentsToPlace.put("Dermatology", Arrays.asList("116", "117", "118", "119", "120"));
        mapDepartmentsToPlace.put("Psychiatry", Arrays.asList("126", "127", "128", "129", "130"));
    }

    @Override
    public void updatePlaceComboBox() {
        String selectedDepartment = (String) departments.getSelectedItem();
        if (selectedDepartment != null) {
            List<String> rooms = mapDepartmentsToPlace.get(selectedDepartment);
            placeData.removeAllItems();
            if (rooms != null) {
                for (String room : rooms) {
                    placeData.addItem(room);
                }
            }
        }
    }

    protected int extractRoomID() {
        int room_ID;
        try {
            room_ID = Integer.parseInt(Objects.requireNonNull(placeData.getSelectedItem()).toString());
            return room_ID;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    protected void clearFields() {
        patientID.setText("");
        firstName.setText("");
        secondName.setText("");
        thirdName.setText("");
        phoneField.setText("");
        startingDate.setText("");
        numOfDaysToStay.setText("");
        male.setSelected(false);
        female.setSelected(false);
    }

    private int extractStayingTime() {
        int stayTime;
        try {
            stayTime = Integer.parseInt(numOfDaysToStay.getText());
            return stayTime;
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    // A method that extract the patient info from the components and send it to the database
    private void addPatient() {
        try {
            // extracting the needed data
            int patientId = Integer.parseInt(patientID.getText());
            String fName = firstName.getText();
            String sName = secondName.getText();
            String tName = thirdName.getText();
            String gender = male.isSelected() ? "Male" : "Female";
            int phone = extractPhoneNumber();
            String citySelected = (String) city.getSelectedItem();
            int room = this.extractRoomID();
            int stayTime = extractStayingTime();
            if (phone == -1 || stayTime == -1){
                JOptionPane.showMessageDialog(phoneField, "Input a valid number", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            connection.Connect();
            DBOperations operations = new DBOperations(connection);
            operations.insertPatient(patientId, fName, sName, tName, gender, phone, citySelected, room, stayTime);

            // inserted successfully
            JOptionPane.showMessageDialog(this, "Patient added successfully.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter Correct number", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Patient not added :( " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pageJob) {
                addPatient();

            } else if (e.getSource() == clear) {
                clearFields();

            } else {
                dispose();
            }
        }
    }

}