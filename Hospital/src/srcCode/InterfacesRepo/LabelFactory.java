package srcCode.InterfacesRepo;

import java.awt.*;

public interface LabelFactory {

    int LABEL_WIDTH = 180;
    int LABEL_HEIGHT = 45;

    // A method to create the frame labels
    void createLabel(String text, int x_axis, int y_axis);
}
