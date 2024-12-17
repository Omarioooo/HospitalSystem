package srcCode.TampletPages;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AddingPage extends OperationPage {
    protected JComboBox<String> city, placeData, departments;
    protected final Map<String, List<String>> mapDepartmentsToPlace;

    protected AddingPage() {

        // A hash table that will map the room or the clinic to it's department
        mapDepartmentsToPlace = new HashMap<>();

        // Add the comboBox that contain the patient cities
        city = citiesBox(190, 360);

        // Add the departments compoBax that contain all departments
        departments = departmentsBox(590, 50);

        // Add the comboBox that contain the room id or clinic name
        placeData = placeBox(590, 110);

        // put the date of adding is the default date into database
        startingDate.setText(String.valueOf(LocalDate.now()));

    }


    // A method that create the compo box that needed for the cities, departments, rooms and clinics
    protected JComboBox<String> createCompoBox(JComboBox<String> comboBox, int x_axis, int y_axis, String[] items) {
        comboBox = new JComboBox<>(items);
        comboBox.setBounds(x_axis, y_axis, 200, 40);
        comboBox.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        framePanel.add(comboBox);

        return comboBox;
    }

    // A method that use to put the cities name to the cities comboBox
    protected JComboBox<String> citiesBox(int x_axis, int y_axis) {
        String[] egyptianCities = {
                "Alexandria",
                "Aswan",
                "Asyut",
                "Banha",
                "Beni Suef",
                "Cairo",
                "Damietta",
                "Faiyum",
                "Giza",
                "Hurghada",
                "Ismailia",
                "Kafr El Sheikh",
                "Luxor",
                "Mansoura",
                "Minya",
                "Port Said",
                "Qena",
                "Sharm El Sheikh",
                "Sohag",
                "Tanta",
                "Zagazig"
        };

        city = createCompoBox(city, x_axis, y_axis, egyptianCities);

        return city;
    }

    // A method that use to put the data items into the room for adding or clinic for booking appointment
    protected abstract JComboBox<String> placeBox(int x_axis, int y_axis);

    protected abstract JComboBox<String> departmentsBox(int x_axis, int y_axis);

    protected abstract void placeMapping();

    protected abstract void updatePlaceComboBox();

    // A method that extract the patient's phone number that will be inserted into database
    protected int extractPhoneNumber() {
        int phone;
        try {
            phone = Integer.parseInt(phoneField.getText());
            return phone;
        } catch (Exception e) {
            return -1;
        }

    }

    @Override
    protected abstract void clearFields();
}
