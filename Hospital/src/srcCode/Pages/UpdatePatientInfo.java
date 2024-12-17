package srcCode.Pages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.ExtractingDataPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UpdatePatientInfo extends ExtractingDataPage {
    private JButton search;
    private JTextField info;
    private List<String> list;
    private DBOperations dataBaseOperations;


    UpdatePatientInfo(DBConnection connection) {
        this.connection = connection;

        // create the title panel
        setFrameTitle("Update patient info");

        createLabel("Information :", 420, 230);
        info = createTextField(info, 590, 230);
        info.setFocusable(false);

        whiteFields();
        openFields();

        pageJob = createButton(pageJob, 565, "UPDATE");
        pageJob.addActionListener(new ButtonHandler());

        search = createButton(search, 425, "SEARCH");
        search.addActionListener(new ButtonHandler());

        clear = createButton(clear, 285, "CLEAR");
        clear.addActionListener(new ButtonHandler());

        back = createButton(back, 145, "BACK");
        back.addActionListener(new ButtonHandler());


        setVisible(true);
    }

    private void updatePatient() {
        dataBaseOperations = new DBOperations(connection);
        connection.Connect();

        int patient_ID;
        String firstNameUpdated = "";
        String secondNameUpdated = "";
        String thirdNameUpdated = "";
        int phoneUpdated;
        String cityUpdated = "";
        String genderUpdated = "";
        String resultMessage = "";

        if (patientID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for a patient", "error", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            try {
                patient_ID = Integer.parseInt(patientID.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input a valid patient id", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        firstNameUpdated = firstName.getText();
        secondNameUpdated = secondName.getText();
        thirdNameUpdated = thirdName.getText();
        phoneUpdated = Integer.parseInt(phoneField.getText());
        cityUpdated = city.getText();
        if (male.isSelected()) {
            genderUpdated = "M";
        } else {
            genderUpdated = "F";
        }

        try {
            resultMessage = dataBaseOperations.updatePatientInfo(patient_ID, firstNameUpdated, secondNameUpdated, thirdNameUpdated, phoneUpdated, cityUpdated, genderUpdated);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }

        if (resultMessage.equalsIgnoreCase("Patient data updated successfully.")) {
            JOptionPane.showMessageDialog(this, resultMessage, "Notification", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, resultMessage, "error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void searchPatient() {
        dataBaseOperations = new DBOperations(connection);
        connection.Connect();

        // create the new data field
        int patient_ID;

        if (patientID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please put the patient id", "error", JOptionPane.ERROR_MESSAGE);
            return;

        } else {
            try {
                patient_ID = Integer.parseInt(patientID.getText());

            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Input a valid patient id", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            String inPlace = dataBaseOperations.searchForPatientsPlace(patient_ID);

            System.out.println(inPlace);
            if (inPlace.equalsIgnoreCase("The patient not found")) {
                JOptionPane.showMessageDialog(this, "The patient not found", "error", JOptionPane.ERROR_MESSAGE);

            } else if (inPlace.equalsIgnoreCase("Room")) {
                list = dataBaseOperations.searchIntoRoom(patient_ID);
                fillRoomFields();

            } else if (inPlace.equalsIgnoreCase("Clinic")) {
                list = dataBaseOperations.searchIntoClinic(patient_ID);
                fillClinicFields();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // A method fill the data if the patient assigned to a  room
    private void fillRoomFields() {
        firstName.setText(list.get(0));
        secondName.setText(list.get(1));
        thirdName.setText(list.get(2));
        phoneField.setText(list.get(3));
        city.setText(list.get(4));
        getGender(list.get(5));
        department.setText(list.get(6));
        placeData.setText(list.get(7));
        startingDate.setText(list.get(8));
    }

    // A method fill the data if the patient assigned to a clinic and appointment
    private void fillClinicFields() {
        firstName.setText(list.get(0));
        secondName.setText(list.get(1));
        thirdName.setText(list.get(2));
        phoneField.setText(list.get(3));
        city.setText(list.get(4));
        getGender(list.get(5));
        department.setText(list.get(6));
        placeData.setText(list.get(7));
        info.setText(list.get(8));
        startingDate.setText(list.get(9));
    }

    // A method to extract the patient gender coming from the dataBase
    private void getGender(String gender) {
        if (gender.equalsIgnoreCase("M")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }

    private void openFields() {
        this.firstName.setFocusable(true);
        this.secondName.setFocusable(true);
        this.thirdName.setFocusable(true);
        this.phoneField.setFocusable(true);
        this.city.setFocusable(true);
        this.male.setEnabled(true);
        this.female.setEnabled(true);
    }

    private void whiteFields() {

        this.firstName.setBackground(Color.white);
        this.secondName.setBackground(Color.white);
        this.thirdName.setBackground(Color.white);
        this.phoneField.setBackground(Color.white);
        this.startingDate.setBackground(Color.white);
        this.city.setBackground(Color.white);
        this.department.setBackground(Color.white);
        this.placeData.setBackground(Color.white);
    }


    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pageJob) {
                updatePatient();

            } else if (e.getSource() == search) {
                searchPatient();

            } else if (e.getSource() == clear) {
                clearFields();

            } else {
                dispose();
            }
        }
    }

}
