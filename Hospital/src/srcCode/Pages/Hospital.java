package srcCode.Pages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowClinics;
import srcCode.HospitalInfoPages.ShowDepartments;
import srcCode.HospitalInfoPages.ShowRooms;
import srcCode.HospitalInfoPages.ShowTablesPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hospital extends JFrame {

    private JButton back, departments, rooms, clinics;
    private JPanel framePanel, titlePanel;
    private JLabel titleLbl;
    private ShowTablesPage showDepartments, showRooms, showClinics;
    protected DBConnection connection;

    Hospital(DBConnection connection) {
        this.connection = connection;

        setSize(820, 700);
        setLocation(630, 90);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // set a title for the frame
        setFrameTitle("Display Departments");

        // create the frame panel
        framePanel = createPanel(framePanel, 80, 615);

        showDepartments = new ShowDepartments(connection);
        showDepartments.setBounds(30, 100, 760, 370);
        framePanel.add(showDepartments);

        showRooms = new ShowRooms(connection);
        showRooms.setBounds(30, 100, 760, 370);

        showClinics = new ShowClinics(connection);
        showClinics.setBounds(30, 100, 760, 370);

        departments = createButton(departments, 550, "Departments");
        rooms = createButton(rooms, 400, "Rooms");
        clinics = createButton(clinics, 250, "Clinics");
        back = createButton(back, 100, "BACK");

        setVisible(true);
    }

    // Creating the panel that will contain the title
    private void setFrameTitle(String pageTitle) {
        titlePanel = createPanel(titlePanel, 5, 70);
        titleLbl = new JLabel(pageTitle, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Serif", Font.BOLD, 35));
        titleLbl.setForeground(Color.GRAY);
        titleLbl.setBounds(0, 0, titlePanel.getWidth(), titlePanel.getHeight());
        titlePanel.add(titleLbl);
    }


    // A method to initializing the buttons
    private JButton createButton(JButton btn, int x_axis, String text) {
        btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(x_axis, 520, 140, 70);
        btn.setBackground(new Color(120, 120, 123, 255));
        btn.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
        btn.addActionListener(new ButtonHandler());
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

