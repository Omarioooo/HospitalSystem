package srcCode.HospitalInfoPages;

import srcCode.DB_Interaction.DBConnection;
import srcCode.DB_Interaction.DBOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public abstract class ShowTablesPage extends JPanel {

    private JTable depInfoTable;
    private final DBConnection connection;
    private ResultSet resultSet;
    private DBOperations showInfoOperation;

    public ShowTablesPage(DBConnection connection, String page) {
        this.connection = connection;
        connection.Connect();
        setLayout(new BorderLayout());

        try {
            depInfoTable = getDepInfoTable(page);
            JScrollPane scrollPane = new JScrollPane(depInfoTable);
            add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // A method to return the view(Table)
    protected JTable getDepInfoTable(String page) throws SQLException {
        showInfoOperation = new DBOperations(connection);
        resultSet = getPage(page);

        if (resultSet == null) {
            JOptionPane.showMessageDialog(this, "No data found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return new JTable();
        }

        DefaultTableModel tableModel = buildTableModel(resultSet);

        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBackground(Color.GRAY);
        header.setForeground(Color.WHITE);

        // Close the ResultSet when done
        resultSet.close();

        return table;
    }

    // A method to convert the ResultSet to DefaultTableModel
    protected DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Collect column names
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }

        // Collect rows data
        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row.add(resultSet.getObject(columnIndex));
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }

    // A method to help decide which page needed to select its data
    private ResultSet getPage(String page) {
        switch (page) {
            case "Department":
                resultSet = showInfoOperation.showDepartmentInfo();
                break;
            case "Room":
                resultSet = showInfoOperation.showRoomInfo();
                break;
            case "Clinic":
                resultSet = showInfoOperation.showClinicInfo();
                break;
            case "Nurse":
                resultSet = showInfoOperation.showNurseInfo();
                break;
            default:
                resultSet = null;
        }
        return resultSet;
    }

}
