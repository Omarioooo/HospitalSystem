package srcCode.InterfacesRepo;

import javax.swing.*;

public interface TextFieldFactory extends FieldsFactory {

    // A method to create the frame text fields
    JTextField createTextField(int x_axis, int y_axis);
}
