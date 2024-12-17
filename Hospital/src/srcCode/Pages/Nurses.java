package srcCode.Pages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.HospitalInfoPages.ShowClinics;
import srcCode.HospitalInfoPages.ShowDepartments;
import srcCode.HospitalInfoPages.ShowNurses;
import srcCode.HospitalInfoPages.ShowRooms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nurses extends JFrame {

    protected JButton back;
    protected JPanel framePanel, titlePanel;
    protected JLabel titleLbl;
    protected DBConnection connection;

    public Nurses(DBConnection connection) {
        this.connection = connection;
        connection.Connect();

        setSize(820, 700);
        setLocation(630, 90);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        // set a title for the frame
        setFrameTitle("Nurses on hospital");

        // create the frame panel
        framePanel = createPanel(framePanel, 80, 615);

        // Add the ShowDepartments panel
        ShowNurses showNurses = new ShowNurses(connection);
        showNurses.setBounds(30, 100, 760, 370);
        framePanel.add(showNurses);

        back = createButton(back, 310, "BACK");

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
        btn.setBounds(x_axis, 520, 180, 75);
        btn.setBackground(new Color(120, 120, 123, 255));
        btn.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
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
            if (e.getSource() == back) {
                dispose();
            }
        }
    }
}

