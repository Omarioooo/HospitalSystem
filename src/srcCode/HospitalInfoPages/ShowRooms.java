package srcCode.HospitalInfoPages;


import srcCode.DB_Interaction.DBConnection;

public class ShowRooms extends ShowTablesPage {

    public ShowRooms(DBConnection connection){
        super(connection, "Room");
    }

}
