package srcCode.Pages.PatientInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.ManipulatePatientInfoPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdatePatientInfo extends ManipulatePatientInfoPage {

    public UpdatePatientInfo(DBConnection connection) {
        this.connection = connection;

        // create the title panel
        setFrameTitle("Update patient info");

        pageJob = createButton(565, "UPDATE");
        pageJob.addActionListener(new ButtonHandler());

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

        clearFields();

    }


    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pageJob) {
                updatePatient();
            }
        }

    }
}