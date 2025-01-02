package srcCode.Log_In;

import javax.swing.*;
import java.awt.*;

public class Slash extends JFrame {

    ImageIcon originalIcon, scaledIcon, frameIcon;
    Image image;
    JLabel imageLabel;


    public Slash() {
        // Create JFrame
        setTitle("Loading: Employees System");
        frameIcon = new ImageIcon(ClassLoader.getSystemResource("icons/frameIcon.png"));
        setIconImage(frameIcon.getImage());
        setSize(1170, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Scaling the GIF
        originalIcon = new ImageIcon(ClassLoader.getSystemResource("icons/HOSPITAL_gif.gif"));
        image = originalIcon.getImage().getScaledInstance(1170, 650, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(image);
        imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 1170, 650);
        add(imageLabel);

        // Set the frame visible
        setVisible(true);

        // Sleep the thread for 3_Sec before close the page
        try {
            Thread.sleep(3000);
            setVisible(false);
            dispose();
            new Login();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
