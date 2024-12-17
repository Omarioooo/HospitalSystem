package srcCode.TampletPages;

import srcCode.DB_Interaction.DBConnection;

import javax.swing.*;
import java.awt.*;

public abstract class OperationPage extends JFrame {

    protected JRadioButton male, female;
    protected JButton pageJob, back, clear;
    protected JPanel framePanel, titlePanel;
    protected JLabel titleLbl, frameLbl;
    protected JTextField patientID, firstName, secondName, thirdName, phoneField,
            startingDate;
    protected DBConnection connection;


    protected OperationPage() {

        setSize(820, 700);
        setLocation(630, 90);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // create the frame panel
        framePanel = createPanel(framePanel, 80, 615);

        //Create the components
        createLabel("Patient ID :", 20, 50);
        patientID = createTextField(patientID, 190, 50);

        createLabel("First Name :", 20, 110);
        firstName = createTextField(firstName, 190, 110);

        createLabel("Second Name :", 20, 170);
        secondName = createTextField(secondName, 190, 170);

        createLabel("Third Name :", 20, 230);
        thirdName = createTextField(thirdName, 190, 230);

        createLabel("Phone Number : ", 20, 300);
        phoneField = createTextField(phoneField, 190, 300);

        createLabel("Patient city : ", 20, 360);

        male = createRadioButton(male, "Male", 80);
        female = createRadioButton(female, "Female", 280);

        createLabel("Department : ", 420, 50);

        startingDate = createTextField(startingDate, 590, 170);
        startingDate.setEditable(false);
    }

    // Creating the panel that will contain the title
    protected void setFrameTitle(String pageTitle) {

        titlePanel = createPanel(titlePanel, 5, 70);
        titleLbl = new JLabel(pageTitle, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Serif", Font.BOLD, 35));
        titleLbl.setForeground(Color.RED);
        titleLbl.setBounds(0, 0, titlePanel.getWidth(), titlePanel.getHeight());
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


    // A method to initializing the buttons
    protected JButton createButton(JButton btn, int x_axis, String text) {
        btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(x_axis, 530, 120, 60);
        btn.setBackground(new Color(120, 120, 123, 255));
        btn.setFont(new Font("Comic Sans MS", Font.ITALIC, 15));
        framePanel.add(btn);

        return btn;
    }

    // A method to create the frame panels
    protected JPanel createPanel(JPanel pnl, int y_axis, int height) {
        pnl = new JPanel();
        pnl.setBounds(5, y_axis, 810, height);
        pnl.setBackground(new Color(109, 164, 170));
        pnl.setLayout(null);
        add(pnl);

        return pnl;
    }

    // A method to create the frame labels
    protected void createLabel(String text, int x_axis, int y_axis) {
        frameLbl = new JLabel(text);
        frameLbl.setBounds(x_axis, y_axis, 180, 45);
        frameLbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        framePanel.add(frameLbl);
    }

    // A method to create the frame text fields
    protected JTextField createTextField(JTextField textField, int x_axis, int y_axis) {
        textField = new JTextField();
        textField.setBounds(x_axis, y_axis, 200, 40);
        textField.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        framePanel.add(textField);
        return textField;
    }

    // A method to create the frame radio buttons
    protected JRadioButton createRadioButton(JRadioButton rb, String text, int x_axis) {

        rb = new JRadioButton(text);
        rb.setBounds(x_axis, 430, 180, 45);
        rb.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        rb.setFocusPainted(false);
        rb.setOpaque(false);
        framePanel.add(rb);
        return rb;
    }

    // A method to clear all frame fields
    protected abstract void clearFields();

}
