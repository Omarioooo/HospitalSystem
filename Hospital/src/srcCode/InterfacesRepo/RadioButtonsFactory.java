package srcCode.InterfacesRepo;

import javax.swing.*;

public interface RadioButtonsFactory {

    int RADIO_BUTTON_WIDTH = 180;
    int RADIO_BUTTON_HEIGHT = 45;
    int RADIO_BUTTON_IN_Y_AXIS = 430;

    // A method to create the frame radio buttons
    JRadioButton createRadioButton(String text, int x_axis);

}
