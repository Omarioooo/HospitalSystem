package srcCode.HospitalInfoPages;


import srcCode.DB_Interaction.DBConnection;

public class ShowNurses extends ShowTablesPage {

    public ShowNurses(DBConnection connection) {
        super(connection, "Nurse");
    }

}
