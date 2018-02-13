package dam.android.dependeciapp.SQLite;

/**
 * Created by Tiodor on 22/01/2018.
 */

public final class DependenciaDBContract {

    public static final String DB_NAME = "DEPENDECIA.DB";

    public static final int DB_VERSION = 1;

    public static class RecordatoriosDBContract {

        public static final String TABLE_NAME = "RECORDATORIOS";

        public static final String _ID = "_id";
        public static final String MEDICAMENTO = "medicamento";
        public static final String DOSIS = "dosis";
        public static final String HORA = "hora";

        public static final String CREATE_TABLE = "CREATE TABLE " + RecordatoriosDBContract.TABLE_NAME
                + " ("
                + RecordatoriosDBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecordatoriosDBContract.MEDICAMENTO + " TEXT NOT NULL, "
                + RecordatoriosDBContract.DOSIS + " REAL NOT NULL, "
                + RecordatoriosDBContract.HORA + " TEXT NOT NULL, "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + RecordatoriosDBContract.TABLE_NAME;
    }

    public static class UbicacionesDBContract {

        public static final String TABLE_NAME = "UBICACIONES";

        public static final String _ID = "_id";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String DIRECCION = "direccion";


        public static final String CREATE_TABLE = "CREATE TABLE " + UbicacionesDBContract.TABLE_NAME
                + " ("
                + UbicacionesDBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UbicacionesDBContract.LATITUD + " REAL NOT NULL, "
                + UbicacionesDBContract.LONGITUD + " REAL NOT NULL, "
                + UbicacionesDBContract.DIRECCION + " TEXT, "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + UbicacionesDBContract.TABLE_NAME;
    }
}
