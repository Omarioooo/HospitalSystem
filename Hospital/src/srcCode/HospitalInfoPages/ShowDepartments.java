package srcCode.HospitalInfoPages;


import srcCode.DB_Interaction.DBConnection;

public class ShowDepartments extends ShowTablesPage {

    public ShowDepartments(DBConnection connection){
        super(connection, "Department");
    }

}
