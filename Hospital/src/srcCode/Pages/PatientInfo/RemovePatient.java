package srcCode.Pages.PatientInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.ManipulatePatientInfoPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RemovePatient extends ManipulatePatientInfoPage {

    public RemovePatient(DBConnection connection) {
        this.connection = connection;

        // create the title panel
        setFrameTitle("Remove existed patient");

        pageJob = createButton(565, "DELETE");
        pageJob.addActionListener(new ButtonHandler());

        setVisible(true);
    }

    private void removePatient() {
        dataBaseOperations = new DBOperations(connection);

        int patient_ID;
        String resultMessage = "";

        if (patientID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input patient id", "error", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            try {
                patient_ID = Integer.parseInt(patientID.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input a valid patient id", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            resultMessage = dataBaseOperations.removePatient(patient_ID);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }

        if (resultMessage.equalsIgnoreCase("Patient and related records deleted successfully.")) {
            JOptionPane.showMessageDialog(this, "Patient deleted successfully", "Notification", JOptionPane.INFORMATION_MESSAGE);

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
                removePatient();
            }
        }
    }

}
