package srcCode.Pages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.TampletPages.AddingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class BookAppointment extends AddingPage {
    private JTextField appointmentID, cost;
    private JComboBox<String> statusBox;

    public BookAppointment(DBConnection connection) {
        this.connection = connection;
        // create the title panel
        setFrameTitle("Book an appointment");

        //Create the components
        setPlaceLbl("Clinic Name : ");
        setStartingDateLbl("Booking Date : ");

        createLabel("Appointment ID : ", 420, 230);
        appointmentID = createTextField(appointmentID, 590, 230);


        createLabel("Booking cost : ", 420, 290);
        cost = createTextField(cost, 590, 290);
        int randomCost = 400 + (int) (Math.random() * 901);
        String costRandomly = Integer.toString(randomCost);
        cost.setText(costRandomly);
        cost.setEditable(false);

        createLabel("Status : ", 420, 350);
        statusBox = statusBox(590, 350);


        pageJob = createButton(pageJob, 500, "Book");
        pageJob.addActionListener(new ButtonHandler());
        clear = createButton(clear, 360, "CLEAR");
        clear.addActionListener(new ButtonHandler());
        back = createButton(back, 220, "BACK");
        back.addActionListener(new ButtonHandler());

        setVisible(true);
    }

    private JComboBox<String> createStatusBox(JComboBox<String> comboBox, int x_axis, int y_axis, String[] items) {
        comboBox = new JComboBox<>(items);
        comboBox.setBounds(x_axis, y_axis, 200, 40);
        comboBox.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        framePanel.add(comboBox);

        return comboBox;
    }

    protected JComboBox<String> statusBox(int x_axis, int y_axis) {
        String[] statues = {"New", "Canceled"};

        statusBox = createStatusBox(statusBox, x_axis, y_axis, statues);
        statusBox.setSelectedItem(0);
        statusBox.setEnabled(false);

        return statusBox;
    }

    @Override
    protected JComboBox<String> placeBox(int x_axis, int y_axis) {
        String[] clinics = {
                "Heart Clinic A",
                "Heart Clinic B",
                "Heart Clinic C",
                "Oncology Clinic A",
                "Oncology Clinic B",
                "Oncology Clinic C",
                "Pediatrics Clinic A",
                "Pediatrics Clinic B",
                "Pediatrics Clinic C",
                "Cardiology Clinic A",
                "Cardiology Clinic B",
                "Neurology Clinic A",
                "Neurology Clinic B",
                "General Surgery Clinic A",
                "General Surgery Clinic B",
                "Radiology Clinic A",
                "Radiology Clinic B",
                "Orthopedics Clinic",
                "Dermatology Clinic",
                "Anesthesiology Clinic",
                "Psychiatry Clinic"
        };


        placeData = createCompoBox(placeData, x_axis, y_axis, clinics);

        return placeData;

    }

    @Override
    protected JComboBox<String> departmentsBox(int x_axis, int y_axis) {
        String[] hospitalDepartments = {
                "Heart",
                "Neurology",
                "Orthopedics",
                "Pediatrics",
                "General Surgery",
                "Radiology",
                "Anesthesiology",
                "Oncology",
                "Dermatology",
                "Psychiatry"
        };

        departments = createCompoBox(departments, x_axis, y_axis, hospitalDepartments);

        placeMapping();
        departments.addActionListener(e -> updatePlaceComboBox());

        return departments;

    }

    @Override
    protected void placeMapping() {
        mapDepartmentsToPlace.put("Heart", Arrays.asList("Heart Clinic A", "Heart Clinic B", "Heart Clinic C"));
        mapDepartmentsToPlace.put("Neurology", Arrays.asList("Neurology Clinic A", "Neurology Clinic B"));
        mapDepartmentsToPlace.put("Orthopedics", Arrays.asList("Orthopedics Clinic A", "Orthopedics Clinic B", "Orthopedics Clinic C"));
        mapDepartmentsToPlace.put("Pediatrics", Arrays.asList("Pediatrics Clinic A", "Pediatrics Clinic B", "Pediatrics Clinic C"));
        mapDepartmentsToPlace.put("General Surgery", Arrays.asList("General Surgery Clinic A", "General Surgery Clinic B"));
        mapDepartmentsToPlace.put("Radiology", Arrays.asList("Radiology Clinic A", "Radiology Clinic B"));
        mapDepartmentsToPlace.put("Anesthesiology", Arrays.asList("Anesthesiology Clinic"));
        mapDepartmentsToPlace.put("Oncology", Arrays.asList("Oncology Clinic A", "Oncology Clinic B", "Oncology Clinic C"));
        mapDepartmentsToPlace.put("Dermatology", Arrays.asList("Dermatology Clinic"));
        mapDepartmentsToPlace.put("Psychiatry", Arrays.asList("Psychiatry Clinic"));
    }

    @Override
    protected void updatePlaceComboBox() {
        String selectedDepartment = (String) departments.getSelectedItem();
        if (selectedDepartment != null) {
            List<String> clinics = mapDepartmentsToPlace.get(selectedDepartment);
            cost.setText("");
            if (clinics != null) {
                for (String clinic : clinics) {
                    if (clinic != null) placeData.addItem(clinic);
                }
            }
        }
    }

    @Override
    protected void clearFields() {
        patientID.setText("");
        firstName.setText("");
        secondName.setText("");
        thirdName.setText("");
        phoneField.setText("");
        appointmentID.setText("");
        male.setSelected(false);
        female.setSelected(false);
    }

    // A method that extract the patient info from the components and send it to the database
    private void bookAppointment() {
        try {
            // extracting the needed data
            int patientId = Integer.parseInt(patientID.getText());
            String fName = firstName.getText();
            String sName = secondName.getText();
            String tName = thirdName.getText();
            int phone = extractPhoneNumber();
            String gender = male.isSelected() ? "M" : "F";
            String citySelected = (String) city.getSelectedItem();
            String clinicName = (String) placeData.getSelectedItem();
            int Appointment = Integer.parseInt(appointmentID.getText());
            int appointmentCost = Integer.parseInt(cost.getText());
            String status = (String) statusBox.getSelectedItem();

            System.out.println("how are u");
            connection.Connect();
            if(connection.isConnect()){
                System.out.println("Iam sassdd");
            }
            DBOperations operations = new DBOperations(connection);
            operations.bookAppointment(patientId, fName, sName, tName, phone, citySelected, gender, clinicName, Appointment, appointmentCost, status);

            // inserted successfully
            JOptionPane.showMessageDialog(this, "Appointment booked successfully.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter Correct number", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pageJob) {
                bookAppointment();

            } else if (e.getSource() == clear) {
                clearFields();

            } else {
                dispose();
            }
        }
    }

}
