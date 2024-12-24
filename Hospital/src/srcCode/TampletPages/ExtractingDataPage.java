package srcCode.TampletPages;

import javax.swing.*;

public abstract class ExtractingDataPage extends MainTemplatePage {

    protected JTextField city, department, placeData;

    public ExtractingDataPage() {

        city = createTextField(190, 360);

        department = createTextField(590, 50);

        setPlaceLbl("Patient place :");
        placeData = createTextField(590, 110);

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


