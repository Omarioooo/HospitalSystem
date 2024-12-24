package srcCode.TampletPages;

import srcCode.InterfacesRepo.ComboBoxFactory;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InsertingDataPage extends MainTemplatePage implements ComboBoxFactory {
    protected JComboBox<String> city, placeData, departments;
    protected final Map<String, List<String>> mapDepartmentsToPlace;

    protected InsertingDataPage() {

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


    @Override
    public JComboBox<String> createCompoBox(int x_axis, int y_axis, String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(x_axis, y_axis, FIELDS_WIDTH, FIELDS_HEIGHT);
        comboBox.setFont(COMPONENTS_FONT);
        framePanel.add(comboBox);

        return comboBox;
    }

    // A method that extract the patient's phone number that will be inserted into database
    protected int extractPhoneNumber() {
        int phone;
        try {
            phone = Integer.parseInt(phoneField.getText());
            return phone;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public JComboBox<String> citiesBox(int x_axis, int y_axis) {
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

        city = createCompoBox(x_axis, y_axis, egyptianCities);

        return city;
    }

    @Override
    public abstract JComboBox<String> placeBox(int x_axis, int y_axis);

    @Override
    public abstract JComboBox<String> departmentsBox(int x_axis, int y_axis);

    @Override
    public abstract void placeMapping();

    @Override
    public abstract void updatePlaceComboBox();

    @Override
    protected abstract void clearFields();
}
