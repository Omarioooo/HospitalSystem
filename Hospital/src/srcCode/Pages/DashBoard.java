package srcCode.Pages;

import srcCode.DB_Interaction.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard extends JFrame{

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
        setSize(1500, 850);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // The side to display the data from the database
        displayPanel = createPanel(displayPanel, 440, 1040);

        // The side of the buttons
        buttonsPanel = createPanel(buttonsPanel, 5, 430);


        // Add the ambulance_GIF
        originalImageGif = new ImageIcon(ClassLoader.getSystemResource("icons/ambulance-hospital.gif"));
        Image image = originalImageGif.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        scaledImageGif = new ImageIcon(image);

        gifLabel = new JLabel(scaledImageGif);
        gifLabel.setBounds(90, 10, 250, 250);
        buttonsPanel.add(gifLabel);

        // Creating the buttons
        addPatient = createButton(addPatient, 310, "Add new patient");
        bookAppointment = createButton(removePatient, 370, "Book an appointment");
        patientInfo = createButton(patientInfo, 430, "Patient information");
        updatePatient = createButton(updatePatient, 490, "Update patient information");
        removePatient = createButton(removePatient, 550, "Remove patient");
        nursesInfo = createButton(nursesInfo, 610, "Hospital's nurses");
        hospitalInfo = createButton(hospitalInfo, 670, "Hospital info");
        exitBtn = createButton(exitBtn, 730, "Exit");


        setVisible(true);
    }


    // A method to initializing the buttons
    private JButton createButton(JButton btn, int y_axis, String text) {
        btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(80, y_axis, 250, 50);  // Positioned below the previous button
        btn.setBackground(new Color(109, 164, 170));
        btn.setFont(new Font("Comic Sans MS", Font.ITALIC, 15));
        btn.addActionListener(new ButtonHandler());
        buttonsPanel.add(btn);

        return btn;
    }

    // A method to initializing the panels
    private PagePanels createPanel(PagePanels pg, int x_axis, int width) {
        // The side to display the data from the database
        pg = new PagePanels("icons/dboard.jpg");
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
                new AddPatient(connection);

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
