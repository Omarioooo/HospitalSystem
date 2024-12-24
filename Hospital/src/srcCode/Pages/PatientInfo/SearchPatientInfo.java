package srcCode.Pages.PatientInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.ExtractingDataPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class SearchPatientInfo extends ExtractingDataPage {
    private JTextField info;
    private List<String> list;
    protected DBOperations dataBaseOperations;

    public SearchPatientInfo(DBConnection connection) {
        this.connection = connection;

        // create the title panel
        setFrameTitle("Search for patients");

        createLabel("Information :", 420, 230);
        info = createTextField(590, 230);
        info.setFocusable(false);
        info.setBackground(COMPONENTS_COLOR);


        pageJob = createButton(500, "SEARCH");
        pageJob.addActionListener(new ButtonHandler());

        clear = createButton(360, "CLEAR");
        clear.addActionListener(new ButtonHandler());

        back = createButton(220, "BACK");
        back.addActionListener(new ButtonHandler());

        setVisible(true);
    }

    // The method for searching logic
    protected void searchPatient() {
        dataBaseOperations = new DBOperations(connection);

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

        closeFields();
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
        info.setText(list.get(9));
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

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pageJob) {
                searchPatient();

            } else if (e.getSource() == clear) {
                clearFields();
                info.setText("");

            } else {
                dispose();
            }
        }
    }
}