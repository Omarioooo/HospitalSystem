package srcCode.Pages.HospitalInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowClinics;
import srcCode.HospitalInfoPages.ShowDepartments;
import srcCode.HospitalInfoPages.ShowRooms;
import srcCode.HospitalInfoPages.ShowTablesPage;
import srcCode.InterfacesRepo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hospital extends JFrame implements FrameLocation, ButtonsFactory,
        PanelFactory, ComponentsAttributes, TableFactory {

    private JButton back, departments, rooms, clinics;
    private JPanel framePanel, titlePanel;
    private JLabel titleLbl;
    private ShowTablesPage showDepartments, showRooms, showClinics;
    protected DBConnection connection;

    public Hospital(DBConnection connection) {
        this.connection = connection;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOCATION_IN_X_AXIS, LOCATION_IN_Y_AXIS);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // set a title for the frame
        setFrameTitle("Display Departments");

        // create the frame panel
        framePanel = createPanel(FRAME_PANEL_IN_Y_AXIS, FRAME_PANEL_HEIGHT);

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
    public void setFrameTitle(String pageTitle) {
        titlePanel = createPanel(TITLE_PANEL_IN_Y_AXIS, TITLE_PANEL_HEIGHT);
        titleLbl = new JLabel(pageTitle, SwingConstants.CENTER);
        titleLbl.setFont(TITLE_FONT);
        titleLbl.setForeground(Color.GRAY);
        titleLbl.setBounds(0, 0, PANEL_WIDTH, TITLE_PANEL_HEIGHT);
        titlePanel.add(titleLbl);
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

    @Override
    public JPanel createPanel(int y_axis, int height) {
        JPanel pnl = new JPanel();
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

