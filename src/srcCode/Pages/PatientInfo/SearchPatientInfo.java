package srcCode.Pages.PatientInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.TampletPages.ExtractingDataPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SearchPatientInfo extends ExtractingDataPage {

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