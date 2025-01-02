package srcCode.HospitalInfoPages;


import srcCode.DB_Interaction.DBConnection;

public class ShowClinics extends ShowTablesPage {

    public ShowClinics(DBConnection connection) {
        super(connection, "Clinic");
    }

}
