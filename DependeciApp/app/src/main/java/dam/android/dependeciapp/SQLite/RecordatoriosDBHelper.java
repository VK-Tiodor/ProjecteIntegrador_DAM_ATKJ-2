package dam.android.dependeciapp.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tiodor on 22/01/2018.
 */

public class RecordatoriosDBHelper extends SQLiteOpenHelper {

        private static RecordatoriosDBHelper instanceDBHelper;

        public static synchronized RecordatoriosDBHelper getInstance(Context context){
            if(instanceDBHelper == null){
                instanceDBHelper = new RecordatoriosDBHelper(context.getApplicationContext());
            }
            return instanceDBHelper;
        }

        private RecordatoriosDBHelper(Context context){
            super(context, DependeciaDBContract.DB_NAME, null, DependeciaDBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DependeciaDBContract.Recordatorios.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DependeciaDBContract.Recordatorios.DELETE_TABLE);

            onCreate(db);
        }

    }
