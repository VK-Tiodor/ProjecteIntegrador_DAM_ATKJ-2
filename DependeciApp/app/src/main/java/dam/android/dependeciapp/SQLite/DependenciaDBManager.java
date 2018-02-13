package dam.android.dependeciapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tiodor on 22/01/2018.
 */

public class DependenciaDBManager {

    public class RecordatoriosDBManager {

        private DependenciaDBHelper.RecordatoriosDBHelper recordatoriosDBHelper;

        public RecordatoriosDBManager(Context context) {
            recordatoriosDBHelper = DependenciaDBHelper.RecordatoriosDBHelper.getInstance(context);
        }

        public void insert(String medicamento, String dosis, String hora) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.MEDICAMENTO, medicamento);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.DOSIS, dosis);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.FECHA_HORA, hora);

                sqLiteDatabase.insert(DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(String id, String medicamento, String dosis, String hora) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("UPDATE " + DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME + " SET "
                        + DependenciaDBContract.RecordatoriosDBContract.MEDICAMENTO + " = '" + medicamento + "', "
                        + DependenciaDBContract.RecordatoriosDBContract.DOSIS + " = '" + dosis + "', "
                        + DependenciaDBContract.RecordatoriosDBContract.FECHA_HORA + " = '" + hora + "', "
                        + " WHERE " + DependenciaDBContract.RecordatoriosDBContract._ID + " = " + id);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("DELETE FROM " + DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME + " WHERE "
                        + DependenciaDBContract.RecordatoriosDBContract._ID + " = '" + id + "'");

                sqLiteDatabase.close();
            }
        }

        public Cursor getRows() {
            Cursor cursor = null;

            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getReadableDatabase();

            if (sqLiteDatabase != null) {
                String[] projection = new String[]{DependenciaDBContract.RecordatoriosDBContract._ID,
                        DependenciaDBContract.RecordatoriosDBContract.MEDICAMENTO,
                        DependenciaDBContract.RecordatoriosDBContract.DOSIS,
                        DependenciaDBContract.RecordatoriosDBContract.FECHA_HORA};

                cursor = sqLiteDatabase.query(DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }

            return cursor;
        }
    }

    public class UbicacionesDBManager {

        private DependenciaDBHelper.UbicacionesDBHelper ubicacionesDBHelper;

        public UbicacionesDBManager(Context context) {
            ubicacionesDBHelper = DependenciaDBHelper.UbicacionesDBHelper.getInstance(context);
        }

        public void insert(String latitud, String longitud, String direccion) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LATITUD, latitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LONGITUD, longitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.DIRECCION, direccion);

                sqLiteDatabase.insert(DependenciaDBContract.UbicacionesDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(String id, String latitud, String longitud, String direccion) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("UPDATE " + DependenciaDBContract.UbicacionesDBContract.TABLE_NAME + " SET "
                        + DependenciaDBContract.UbicacionesDBContract.LATITUD + " = '" + latitud + "', "
                        + DependenciaDBContract.UbicacionesDBContract.LONGITUD + " = '" + longitud + "', "
                        + DependenciaDBContract.UbicacionesDBContract.DIRECCION + " = '" + direccion + "', "
                        + " WHERE " + DependenciaDBContract.UbicacionesDBContract._ID + " = " + id);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("DELETE FROM " + DependenciaDBContract.UbicacionesDBContract.TABLE_NAME + " WHERE "
                        + DependenciaDBContract.UbicacionesDBContract._ID + " = '" + id + "'");

                sqLiteDatabase.close();
            }
        }

        public Cursor getRows() {
            Cursor cursor = null;

            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getReadableDatabase();

            if (sqLiteDatabase != null) {
                String[] projection = new String[]{DependenciaDBContract.UbicacionesDBContract._ID,
                        DependenciaDBContract.UbicacionesDBContract.LATITUD,
                        DependenciaDBContract.UbicacionesDBContract.LONGITUD,
                        DependenciaDBContract.UbicacionesDBContract.DIRECCION};

                cursor = sqLiteDatabase.query(DependenciaDBContract.UbicacionesDBContract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }

            return cursor;
        }
    }

    public class UsuarioDBManager {

        private DependenciaDBHelper.UsuarioDBHelper usuarioDBHelper;

        public UsuarioDBManager(Context context) {
            usuarioDBHelper = DependenciaDBHelper.UsuarioDBHelper.getInstance(context);
        }

        public void insert(String dni, String nombre, String apellidos, String fecha_nacimiento, String genero, String tipo_dependiente, String fecha_alta) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(DependenciaDBContract.UsuarioDBContract.DNI, dni);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.NOMBRE, nombre);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.APELLIDOS, apellidos);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO, fecha_nacimiento);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.GENERO, genero);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE, tipo_dependiente);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_ALTA, fecha_alta);

                sqLiteDatabase.insert(DependenciaDBContract.UsuarioDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(String id, String dni, String nombre, String apellidos, String fecha_nacimiento, String genero, String tipo_dependiente, String fecha_alta) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("UPDATE " + DependenciaDBContract.UsuarioDBContract.TABLE_NAME + " SET "
                        + DependenciaDBContract.UsuarioDBContract.DNI + " = '" + dni + "', "
                        + DependenciaDBContract.UsuarioDBContract.NOMBRE + " = '" + nombre + "', "
                        + DependenciaDBContract.UsuarioDBContract.APELLIDOS + " = '" + apellidos + "', "
                        + DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO + " = '" + fecha_nacimiento + "', "
                        + DependenciaDBContract.UsuarioDBContract.GENERO + " = '" + genero + "', "
                        + DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE + " = '" + tipo_dependiente + "', "
                        + DependenciaDBContract.UsuarioDBContract.FECHA_ALTA + " = '" + fecha_alta + "', "
                        + " WHERE " + DependenciaDBContract.UsuarioDBContract._ID + " = " + id);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                sqLiteDatabase.execSQL("DELETE FROM " + DependenciaDBContract.UsuarioDBContract.TABLE_NAME + " WHERE "
                        + DependenciaDBContract.UsuarioDBContract._ID + " = '" + id + "'");

                sqLiteDatabase.close();
            }
        }

        public Cursor getRows() {
            Cursor cursor = null;

            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getReadableDatabase();

            if (sqLiteDatabase != null) {
                String[] projection = new String[]{DependenciaDBContract.UsuarioDBContract._ID,
                        DependenciaDBContract.UsuarioDBContract.DNI,
                        DependenciaDBContract.UsuarioDBContract.NOMBRE,
                        DependenciaDBContract.UsuarioDBContract.APELLIDOS,
                        DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO,
                        DependenciaDBContract.UsuarioDBContract.GENERO,
                        DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE,
                        DependenciaDBContract.UsuarioDBContract.FECHA_ALTA};

                cursor = sqLiteDatabase.query(DependenciaDBContract.UsuarioDBContract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }

            return cursor;
        }
    }
}

