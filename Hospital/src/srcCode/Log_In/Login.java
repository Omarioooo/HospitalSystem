package srcCode.Log_In;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;
import srcCode.Pages.DashBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {

    // Declare components
    JLabel frameLbl, imageLbl, gifLbl;
    JTextField nameField;
    JPasswordField idField;
    JButton loginBtn, exitBtn;
    ImageIcon originalIcon, scaledIcon, originalEmp, scaledEmp, frameIcon;
    Image image, empImage;
    DBConnection connection;
    DBOperations checkOperation;

    public Login() {
        connection = new DBConnection();

        // Create JFrame
        setTitle("Hospital System");
        frameIcon = new ImageIcon(ClassLoader.getSystemResource("icons/frameIcon.png"));
        setIconImage(frameIcon.getImage());
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        // Create the components
        createLabel("Employee :", 40, 30);
        nameField = createTextField(nameField, 120, 38);

        createLabel("ID :", 83, 80);
        idField = createPasswordField(idField, 120, 88);

        loginBtn = createButton(loginBtn, 205, 150, "Login");
        exitBtn = createButton(exitBtn, 110, 150, "Exit");


        // Set the background
        originalEmp = new ImageIcon(ClassLoader.getSystemResource("icons/emp.jpg"));
        empImage = originalEmp.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        scaledEmp = new ImageIcon(empImage);
        gifLbl = new JLabel(scaledEmp);
        gifLbl.setBounds(370, 10, 600, 400);
        add(gifLbl);

        originalIcon = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpg"));
        image = originalIcon.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(image);
        imageLbl = new JLabel(scaledIcon);
        imageLbl.setBounds(0, 0, 600, 300);
        add(imageLbl);

        // Set the frame visible
        setVisible(true);
    }

    // A method to initializing the buttons
    private JButton createButton(JButton btn, int x_axis, int y_axis, String text) {
        btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBounds(x_axis, y_axis, 90, 25);
        btn.setFont(new Font("Comic Sans MS", Font.ITALIC, 15));
        btn.addActionListener(new ButtonHandler());
        this.add(btn);

        return btn;
    }

    // A method to create the text Fields
    private JTextField createTextField(JTextField textField, int x_axis, int y_axis) {
        textField = new JTextField();
        textField.setBounds(x_axis, y_axis, 160, 25);
        add(textField);
        return textField;
    }

    // A method to create the password Fields
    private JPasswordField createPasswordField(JPasswordField passwordField, int x_axis, int y_axis) {
        passwordField = new JPasswordField();
        passwordField.setBounds(x_axis, y_axis, 160, 25);
        add(passwordField);
        return passwordField;
    }

    // A method to create the frame labels
    private void createLabel(String text, int x_axis, int y_axis) {
        frameLbl = new JLabel(text);
        frameLbl.setBounds(x_axis, y_axis, 100, 30);
        add(frameLbl);
    }

    // A method to extract the id from the id field
    int extractID() {
        // Get the password as a char array & Convert the char array to a String
        char[] passwordChars = idField.getPassword();
        String idAsString = new String(passwordChars);
        int id;
        try {
            id = Integer.parseInt(idAsString);
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    // A method to clear the fields when errors
    void clearFields() {
        nameField.setText("");
        idField.setText("");
    }

    // An inner class to handel the events
    private final class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginBtn) {
                loginChecker();
            } else if (e.getSource() == exitBtn) {
                System.exit(0);
            }
        }


        // A method to handel the login button
        private void loginChecker() {
            // A flag to check if the employee exists
            boolean login;

            // The data put from the user
            int id = extractID();
            String name = nameField.getText();

            connection.Connect();
            checkOperation = new DBOperations(connection);

            try {
                login = checkOperation.logInCheck(name, id);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(loginBtn, ex.getMessage(),
                        "Connection Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            checkLogging(login);
        }

        // A method checking if the logins succeed
        private void checkLogging(boolean login) {
            if (login) {
                dispose();
                new DashBoard(connection);
            } else {
                JOptionPane.showMessageDialog(loginBtn, "Please check your data",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                clearFields();
            }
        }

    }

}
