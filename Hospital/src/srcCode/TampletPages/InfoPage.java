package srcCode.TampletPages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowNurses;
import srcCode.InterfacesRepo.*;

import javax.swing.*;
import java.awt.*;

public abstract class InfoPage extends JFrame implements FrameLocation, ComponentsAttributes,
        PanelFactory, TableFactory {

    protected JButton back;
    protected JPanel framePanel, titlePanel;
    protected JLabel titleLbl;
    protected DBConnection connection;

    public InfoPage(){

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOCATION_IN_X_AXIS, LOCATION_IN_Y_AXIS);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // create the frame panel
        framePanel = createPanel(FRAME_PANEL_IN_Y_AXIS, FRAME_PANEL_HEIGHT);

    }

    @Override
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
    public JPanel createPanel(int y_axis, int height) {
        JPanel pnl = new JPanel();
        pnl.setBounds(PANEL_IN_X_AXIS, y_axis, PANEL_WIDTH, height);
        pnl.setBackground(PANEL_BACKGROUND_COLOR);
        pnl.setLayout(null);
        add(pnl);

        return pnl;
    }

}


