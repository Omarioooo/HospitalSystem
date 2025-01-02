package srcCode.InterfacesRepo;

import javax.swing.*;
import java.awt.*;

public interface PanelFactory {

    // The font for the text written on title panels
    Font TITLE_FONT = new Font("Serif", Font.BOLD, 35);
    Color PANEL_BACKGROUND_COLOR = new Color(109, 164, 170);

    int PANEL_WIDTH = 810;
    int PANEL_IN_X_AXIS = 5;
    int TITLE_PANEL_HEIGHT = 70;
    int TITLE_PANEL_IN_Y_AXIS = 5;
    int FRAME_PANEL_HEIGHT = 615;
    int FRAME_PANEL_IN_Y_AXIS = 80;


    // A method to create the frame panels
    JPanel createPanel(int y_axis, int height);

    // Creating the panel that will contain the title
    void setFrameTitle(String pageTitle);


}
