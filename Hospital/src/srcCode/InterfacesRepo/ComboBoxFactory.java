package srcCode.InterfacesRepo;

import javax.swing.*;

public interface ComboBoxFactory extends FieldsFactory{

    // A method that create the compo box that needed for the cities, departments, rooms and clinics
    JComboBox<String> createCompoBox(int x_axis, int y_axis, String[] items);

    // A method to create the departments compoBox
    JComboBox<String> departmentsBox(int x_axis, int y_axis);

    // A method that use to put the data items into the room for adding or clinic for booking appointment
    JComboBox<String> placeBox(int x_axis, int y_axis);

    // A method that use to put the cities name to the cities comboBox
    JComboBox<String> citiesBox(int x_axis, int y_axis);

    // A method to map each Room or Clinic to its department
    void placeMapping();

    // A method to handel which room or clinic will appear onto the place compBox depending on the place mapping
    void updatePlaceComboBox();

}
