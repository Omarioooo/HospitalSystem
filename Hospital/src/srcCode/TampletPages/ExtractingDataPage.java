package srcCode.TampletPages;

import srcCode.DB_Interaction.DBOperations;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public abstract class ExtractingDataPage extends MainTemplatePage {

    protected JTextField city, department, placeData, info;
    protected DBOperations dataBaseOperations;
    protected List<String> list;


    public ExtractingDataPage() {

        city = createTextField(190, 360);

        department = createTextField(590, 50);

        setPlaceLbl("Patient place :");
        placeData = createTextField(590, 110);

        createLabel("Starting Date :", 420, 170);

        grayFields();
        closeFields();
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
    protected void fillRoomFields() {
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
    protected void fillClinicFields() {
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
    protected void getGender(String gender) {
        if (gender.equalsIgnoreCase("M")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }

    protected void closeFields() {
        this.firstName.setFocusable(false);
        this.secondName.setFocusable(false);
        this.thirdName.setFocusable(false);
        this.phoneField.setFocusable(false);
        this.startingDate.setFocusable(false);
        this.city.setFocusable(false);
        this.department.setFocusable(false);
        this.placeData.setFocusable(false);
        this.male.setEnabled(false);
        this.female.setEnabled(false);
    }

    private void grayFields() {

        this.firstName.setBackground(COMPONENTS_COLOR);
        this.secondName.setBackground(COMPONENTS_COLOR);
        this.thirdName.setBackground(COMPONENTS_COLOR);
        this.phoneField.setBackground(COMPONENTS_COLOR);
        this.startingDate.setBackground(COMPONENTS_COLOR);
        this.city.setBackground(COMPONENTS_COLOR);
        this.department.setBackground(COMPONENTS_COLOR);
        this.placeData.setBackground(COMPONENTS_COLOR);
    }

    @Override
    protected void clearFields() {
        this.patientID.setText("");
        this.firstName.setText("");
        this.secondName.setText("");
        this.thirdName.setText("");
        this.phoneField.setText("");
        this.startingDate.setText("");
        this.city.setText("");
        this.department.setText("");
        this.placeData.setText("");
        this.male.setSelected(false);
        this.female.setSelected(false);
    }

}


