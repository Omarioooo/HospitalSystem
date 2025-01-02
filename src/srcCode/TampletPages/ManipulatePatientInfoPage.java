package srcCode.TampletPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ManipulatePatientInfoPage extends ExtractingDataPage {

    protected JButton search;

    protected ManipulatePatientInfoPage() {

        createLabel("Information :", 420, 230);
        info = createTextField(590, 230);
        info.setFocusable(false);

        whiteFields();
        openFields();


        search = createButton(425, "SEARCH");
        search.addActionListener(new ButtonHandler());

        clear = createButton(285, "CLEAR");
        clear.addActionListener(new ButtonHandler());

        back = createButton(145, "BACK");
        back.addActionListener(new ButtonHandler());

    }

    protected void openFields() {
        this.firstName.setFocusable(true);
        this.secondName.setFocusable(true);
        this.thirdName.setFocusable(true);
        this.phoneField.setFocusable(true);
        this.city.setFocusable(true);
        this.male.setEnabled(true);
        this.female.setEnabled(true);
    }

    protected void whiteFields() {

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
            if (e.getSource() == search) {
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
