package srcCode.Pages.HospitalInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowClinics;
import srcCode.HospitalInfoPages.ShowDepartments;
import srcCode.HospitalInfoPages.ShowRooms;
import srcCode.HospitalInfoPages.ShowTablesPage;
import srcCode.InterfacesRepo.*;
import srcCode.TampletPages.InfoPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hospital extends InfoPage implements ButtonsFactory {

    private JButton departments, rooms, clinics;
    private ShowTablesPage showDepartments, showRooms, showClinics;

    public Hospital(DBConnection connection) {
        this.connection = connection;

        // set a title for the frame
        setFrameTitle("Display Departments");

        showDepartments = new ShowDepartments(connection);
        showDepartments.setBounds(TABLE_IN_X_AXIS, TABLE_IN_Y_AXIS, TABLE_WIDTH, TABLE_HEIGHT);
        framePanel.add(showDepartments);

        showRooms = new ShowRooms(connection);
        showRooms.setBounds(TABLE_IN_X_AXIS, TABLE_IN_Y_AXIS, TABLE_WIDTH, TABLE_HEIGHT);

        showClinics = new ShowClinics(connection);
        showClinics.setBounds(TABLE_IN_X_AXIS, TABLE_IN_Y_AXIS, TABLE_WIDTH, TABLE_HEIGHT);

        departments = createButton(550, "Departments");
        rooms = createButton(400, "Rooms");
        clinics = createButton(250, "Clinics");
        back = createButton(100, "BACK");

        setVisible(true);
    }


    @Override
    public JButton createButton(int x_axis, String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(x_axis, 520, 140, 70);
        btn.setBackground(COMPONENTS_COLOR);
        btn.setFont(COMPONENTS_FONT);
        btn.addActionListener(new ButtonHandler());
        framePanel.add(btn);

        return btn;
    }

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            framePanel.remove(showDepartments);
            framePanel.remove(showRooms);
            framePanel.remove(showClinics);


            if (e.getSource() == departments) {
                framePanel.add(showDepartments);

            } else if (e.getSource() == rooms) {
                framePanel.add(showRooms);

            } else if (e.getSource() == clinics) {
                framePanel.add(showClinics);

            } else {
                dispose();
                return;
            }

            framePanel.revalidate();
            framePanel.repaint();
        }
    }

}

