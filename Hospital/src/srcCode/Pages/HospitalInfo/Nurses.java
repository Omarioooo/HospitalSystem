package srcCode.Pages.HospitalInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowNurses;
import srcCode.InterfacesRepo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nurses extends JFrame implements ComponentsAttributes, FrameLocation,
        PagesButtonsFactory, PanelFactory, TableFactory {

    protected JButton back;
    protected JPanel framePanel, titlePanel;
    protected JLabel titleLbl;
    protected DBConnection connection;

    public Nurses(DBConnection connection) {
        this.connection = connection;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOCATION_IN_X_AXIS, LOCATION_IN_Y_AXIS);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // set a title for the frame
        setFrameTitle("Nurses on hospital");

        // create the frame panel
        framePanel = createPanel(FRAME_PANEL_IN_Y_AXIS, FRAME_PANEL_HEIGHT);

        // Add the ShowDepartments panel
        ShowNurses showNurses = new ShowNurses(connection);
        showNurses.setBounds(TABLE_IN_X_AXIS, TABLE_IN_Y_AXIS, TABLE_WIDTH, TABLE_HEIGHT);
        framePanel.add(showNurses);

        back = createButton(310, "BACK");

        setVisible(true);
    }

    // Creating the panel that will contain the title
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
        btn.setBounds(x_axis, BUTTON_IN_Y_AXIS-10, BUTTON_WIDTH+60, BUTTON_HEIGHT+15);
        btn.setBackground(COMPONENTS_COLOR);
        btn.setFont(COMPONENTS_FONT);
        btn.addActionListener(new ButtonHandler());
        framePanel.add(btn);

        return btn;
    }

    // A method to create the frame panels
    public JPanel createPanel(int y_axis, int height) {
        JPanel pnl = new JPanel();
        pnl.setBounds(PANEL_IN_X_AXIS, y_axis, PANEL_WIDTH, height);
        pnl.setBackground(PANEL_BACKGROUND_COLOR);
        pnl.setLayout(null);
        add(pnl);

        return pnl;
    }

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == back) {
                dispose();
            }
        }
    }

}

