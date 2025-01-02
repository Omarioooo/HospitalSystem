package srcCode.Pages.ControlPage;

import srcCode.DB_Interaction.DBConnection;
import srcCode.InterfacesRepo.*;
import srcCode.Pages.HospitalInfo.Hospital;
import srcCode.Pages.HospitalInfo.Nurses;
import srcCode.Pages.PatientInfo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard extends JFrame implements DashBoardFactory, ComponentsAttributes{

    private PagePanels displayPanel, buttonsPanel;
    private JLabel gifLabel;
    private ImageIcon frameIcon, originalImageGif, scaledImageGif;
    private JButton addPatient, updatePatient, patientInfo, removePatient,
            nursesInfo, hospitalInfo, exitBtn, bookAppointment;
    private DBConnection connection;

    public DashBoard(DBConnection connection) {
        this.connection = connection;

        setTitle("Hospital System");
        frameIcon = new ImageIcon(ClassLoader.getSystemResource("icons/frameIcon.png"));
        setIconImage(frameIcon.getImage());
        setSize(DASHBOARD_WIDTH, DASHBOARD_HEIGHT);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // The side to display the data from the database
        displayPanel = createPanel(440, 1040);

        // The side of the buttons
        buttonsPanel = createPanel(5, 430);

        // Add the ambulance_GIF
        originalImageGif = new ImageIcon(ClassLoader.getSystemResource("icons/ambulance-hospital.gif"));
        Image image = originalImageGif.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        scaledImageGif = new ImageIcon(image);

        gifLabel = new JLabel(scaledImageGif);
        gifLabel.setBounds(90, 10, 250, 250);
        buttonsPanel.add(gifLabel);

        // Creating the buttons
        addPatient = createButton(310, "Add new patient");
        bookAppointment = createButton(370, "Book an appointment");
        patientInfo = createButton(430, "Patient information");
        updatePatient = createButton(490, "Update patient information");
        removePatient = createButton(550, "Remove patient");
        nursesInfo = createButton(610, "Hospital's nurses");
        hospitalInfo = createButton(670, "Hospital info");
        exitBtn = createButton(730, "Exit");


        setVisible(true);
    }

    @Override
    public JButton createButton(int y_axis, String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(DASHBOARD_BUTTONS_IN_X_AXIS, y_axis, DASHBOARD_BUTTONS_WIDTH, DASHBOARD_BUTTONS_HEIGHT);  // Positioned below the previous button
        btn.setBackground(BUTTONS_COLOR);
        btn.setFont(COMPONENTS_FONT);
        btn.addActionListener(new ButtonHandler());
        buttonsPanel.add(btn);

        return btn;
    }

    // A method to initializing the panels
    private PagePanels createPanel(int x_axis, int width) {
        // The side to display the data from the database
        PagePanels pg = new PagePanels("icons/dboard.jpg");
        pg.setBounds(x_axis, 5, width, 803);
        pg.setLayout(null);
        pg.setOpaque(false);
        this.add(pg);

        return pg;
    }

    // Inner class to handle the custom panel with a background image
    private class PagePanels extends JPanel {
        private final Image backgroundImage;

        public PagePanels(String imagePath) {
            backgroundImage = new ImageIcon(ClassLoader.getSystemResource(imagePath)).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addPatient) {
                new AddPatientToRoom(connection);

            } else if (e.getSource() == bookAppointment) {
                new BookAppointment(connection);

            } else if (e.getSource() == patientInfo) {
                new SearchPatientInfo(connection);

            } else if (e.getSource() == updatePatient) {
                new UpdatePatientInfo(connection);

            } else if (e.getSource() == removePatient) {
                new RemovePatient(connection);

            } else if (e.getSource() == nursesInfo) {
                new Nurses(connection);

            } else if (e.getSource() == hospitalInfo) {
                new Hospital(connection);

            } else {
                System.exit(0);
            }
        }

    }
}
