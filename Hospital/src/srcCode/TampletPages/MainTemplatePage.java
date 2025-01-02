package srcCode.TampletPages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.InterfacesRepo.*;

import javax.swing.*;
import java.awt.*;

public abstract class MainTemplatePage extends JFrame implements FrameLocation, ComponentsAttributes,
        LabelFactory, PagesButtonsFactory, RadioButtonsFactory, PanelFactory, TextFieldFactory {

    protected JRadioButton male, female;
    protected JButton pageJob, back, clear;
    protected JPanel framePanel, titlePanel;
    protected JLabel titleLbl, frameLbl;
    protected JTextField patientID, firstName, secondName, thirdName, phoneField, startingDate;
    protected DBConnection connection;


    protected MainTemplatePage() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOCATION_IN_X_AXIS, LOCATION_IN_Y_AXIS);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // create the frame panel
        framePanel = createPanel(FRAME_PANEL_IN_Y_AXIS, FRAME_PANEL_HEIGHT);

        //Create the components that common between all pages
        createLabel("Patient ID :", 20, 50);
        patientID = createTextField(190, 50);

        createLabel("First Name :", 20, 110);
        firstName = createTextField(190, 110);

        createLabel("Second Name :", 20, 170);
        secondName = createTextField(190, 170);

        createLabel("Third Name :", 20, 230);
        thirdName = createTextField(190, 230);

        createLabel("Phone Number : ", 20, 300);
        phoneField = createTextField(190, 300);

        createLabel("Patient city : ", 20, 360);

        male = createRadioButton("Male", 80);
        female = createRadioButton("Female", 280);

        createLabel("Department : ", 420, 50);

        startingDate = createTextField(590, 170);
        startingDate.setEditable(false);
    }

    @Override
    public void setFrameTitle(String pageTitle) {

        titlePanel = createPanel(TITLE_PANEL_IN_Y_AXIS, TITLE_PANEL_HEIGHT);
        titleLbl = new JLabel(pageTitle, SwingConstants.CENTER);
        titleLbl.setFont(TITLE_FONT);
        titleLbl.setForeground(Color.GRAY);
        titleLbl.setBounds(0, 0, PANEL_WIDTH, TITLE_PANEL_HEIGHT);
        titlePanel.add(titleLbl);

    }

    // A label that will point to the roomID or clinicID when adding new patient
    protected void setPlaceLbl(String place) {
        createLabel(place, 420, 110);
    }

    // create the label that will contain the starting date label
    protected void setStartingDateLbl(String text) {
        createLabel(text, 420, 170);
    }

    @Override
    public JButton createButton(int x_axis, String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(x_axis, BUTTON_IN_Y_AXIS, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setBackground(COMPONENTS_COLOR);
        btn.setFont(COMPONENTS_FONT);
        framePanel.add(btn);

        return btn;
    }

    @Override
    public JPanel createPanel(int y_axis, int height) {
        JPanel pnl = new JPanel();
        pnl.setBounds(PANEL_IN_X_AXIS, y_axis, PANEL_WIDTH, height);
        pnl.setBackground(PANEL_BACKGROUND_COLOR);
        pnl.setLayout(null);
        add(pnl);

        return pnl;
    }

    @Override
    public void createLabel(String text, int x_axis, int y_axis) {
        frameLbl = new JLabel(text);
        frameLbl.setBounds(x_axis, y_axis, LABEL_WIDTH, LABEL_HEIGHT);
        frameLbl.setFont(COMPONENTS_FONT);
        framePanel.add(frameLbl);
    }

    @Override
    public JTextField createTextField(int x_axis, int y_axis) {
        JTextField textField = new JTextField();
        textField.setBounds(x_axis, y_axis, FIELDS_WIDTH, FIELDS_HEIGHT);
        textField.setFont(COMPONENTS_FONT);
        framePanel.add(textField);
        return textField;
    }

    @Override
    public JRadioButton createRadioButton(String text, int x_axis) {

        JRadioButton rb = new JRadioButton(text);
        rb.setBounds(x_axis, RADIO_BUTTON_IN_Y_AXIS, RADIO_BUTTON_WIDTH, RADIO_BUTTON_HEIGHT);
        rb.setFont(COMPONENTS_FONT);
        rb.setFocusPainted(false);
        rb.setOpaque(false);
        framePanel.add(rb);
        return rb;
    }

    // A method to clear all frame fields
    protected abstract void clearFields();

}
