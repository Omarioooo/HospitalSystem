package srcCode.Pages.HospitalInfo;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowNurses;
import srcCode.HospitalInfoPages.ShowTablesPage;
import srcCode.InterfacesRepo.*;
import srcCode.TampletPages.InfoPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nurses extends InfoPage implements PagesButtonsFactory {

    private ShowTablesPage showNurses;

    public Nurses(DBConnection connection) {
        this.connection = connection;

        // set a title for the frame
        setFrameTitle("Nurses on hospital");

        // Add the ShowNurses panel
        showNurses = new ShowNurses(connection);
        showNurses.setBounds(TABLE_IN_X_AXIS, TABLE_IN_Y_AXIS, TABLE_WIDTH, TABLE_HEIGHT);
        framePanel.add(showNurses);

        back = createButton(310, "BACK");

        setVisible(true);
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

