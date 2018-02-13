package dam.android.u4t8database.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tiodor on 22/01/2018.
 */

public class DependenciaDBManager {

    private TodoListDBHelper todoListDBHelper;

    public DependenciaDBManager(Context context){
        todoListDBHelper = TodoListDBHelper.getInstance(context);
    }

    public void insert(String todo, String when, String priority, String status, String description){
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase();

        if(sqLiteDatabase != null){
            ContentValues contentValues = new ContentValues();

            contentValues.put(TodoListDBContract.Tasks.TODO, todo);
            contentValues.put(TodoListDBContract.Tasks.TO_ACCOMPLISH, when);
            contentValues.put(TodoListDBContract.Tasks.PRIORITY, priority);
            contentValues.put(TodoListDBContract.Tasks.STATUS, status);
            contentValues.put(TodoListDBContract.Tasks.DESCRIPTION, description);

            sqLiteDatabase.insert(TodoListDBContract.Tasks.TABLE_NAME, null, contentValues);

            sqLiteDatabase.close();
        }
    }

    public void update(String id, String todo, String when, String priority, String status, String description){
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase();

        if(sqLiteDatabase != null){
            sqLiteDatabase.execSQL("UPDATE " + TodoListDBContract.Tasks.TABLE_NAME + " SET "
                    + TodoListDBContract.Tasks.TODO + " = '" + todo + "', "
                    + TodoListDBContract.Tasks.TO_ACCOMPLISH + " = '" + when + "', "
                    + TodoListDBContract.Tasks.PRIORITY + " = '" + priority + "', "
                    + TodoListDBContract.Tasks.STATUS + " = '" + status + "', "
                    + TodoListDBContract.Tasks.DESCRIPTION + " = '" + description + "'"
                    + " WHERE " + TodoListDBContract.Tasks._ID + " = " + id);

            sqLiteDatabase.close();
        }
    }

    public void delete(String id){
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase();

        if(sqLiteDatabase != null){
            sqLiteDatabase.execSQL("DELETE FROM " + TodoListDBContract.Tasks.TABLE_NAME + " WHERE "
                    + TodoListDBContract.Tasks._ID + " = '" + id + "'");

            sqLiteDatabase.close();
        }
    }

    public Cursor getRows(){
        Cursor cursor = null;

        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getReadableDatabase();

        if(sqLiteDatabase != null){
            String[] projection = new String[]{TodoListDBContract.Tasks._ID,
                                                TodoListDBContract.Tasks.TODO,
                                                TodoListDBContract.Tasks.TO_ACCOMPLISH,
                                                TodoListDBContract.Tasks.PRIORITY,
                                                TodoListDBContract.Tasks.STATUS,
                                                TodoListDBContract.Tasks.DESCRIPTION};

            cursor = sqLiteDatabase.query(TodoListDBContract.Tasks.TABLE_NAME,
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
