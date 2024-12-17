package srcCode.TampletPages;

import javax.swing.*;
import java.awt.*;

public abstract class ExtractingDataPage extends OperationPage {

    protected JTextField city, department, placeData;

    public ExtractingDataPage() {

        city = createTextField(city, 190, 360);

        department = createTextField(department, 590, 50);

        setPlaceLbl("Patient place :");
        placeData = createTextField(placeData, 590, 110);

        createLabel("Starting Date :", 420, 170);

        grayFields();
        closeFields();
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

        this.firstName.setBackground(new Color(120, 120, 123, 255));
        this.secondName.setBackground(new Color(120, 120, 123, 255));
        this.thirdName.setBackground(new Color(120, 120, 123, 255));
        this.phoneField.setBackground(new Color(120, 120, 123, 255));
        this.startingDate.setBackground(new Color(120, 120, 123, 255));
        this.city.setBackground(new Color(120, 120, 123, 255));
        this.department.setBackground(new Color(120, 120, 123, 255));
        this.placeData.setBackground(new Color(120, 120, 123, 255));
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


