package dam.android.dependeciapp.SQLite;

/**
 * Created by Tiodor on 22/01/2018.
 */

public final class DependeciaDBContract {

    public static final String DB_NAME = "DEPENDECIA.DB";

    public static final int DB_VERSION = 1;

    public static class Recordatorios {

        public static final String TABLE_NAME = "RECORDATORIOS";

        public static final String _ID = "_id";
        public static final String MEDICAMENTO = "medicamento";
        public static final String DOSIS = "dosis";
        public static final String HORA = "hora";

        public static final String CREATE_TABLE = "CREATE TABLE " + Recordatorios.TABLE_NAME
                + " ("
                + Recordatorios._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Recordatorios.MEDICAMENTO + " TEXT NOT NULL, "
                + Recordatorios.DOSIS + " REAL NOT NULL, "
                + Recordatorios.HORA + " TEXT NOT NULL, "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + Recordatorios.TABLE_NAME;
    }

    public static class Ubicaciones {

        public static final String TABLE_NAME = "UBICACIONES";

        public static final String _ID = "_id";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String DIRECCION = "direccion";


        public static final String CREATE_TABLE = "CREATE TABLE " + Ubicaciones.TABLE_NAME
                + " ("
                + Ubicaciones._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ubicaciones.LATITUD + " REAL NOT NULL, "
                + Ubicaciones.LONGITUD + " REAL NOT NULL, "
                + Ubicaciones.DIRECCION + " TEXT, "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + Ubicaciones.TABLE_NAME;
    }
}
