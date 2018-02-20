package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;

/**
 * Created by Tiodor on 20/02/2018.
 */

public class GuardarUbicacionSQLite extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        Context applicationContext = (Context) objects[0];
        LatLng latLngLocation = (LatLng) objects[1];
        String address = (String) objects[2];

        saveCurrentLocationInSQLite(applicationContext, latLngLocation, address);

        return null;
    }

    private void saveCurrentLocationInSQLite(Context context, LatLng latLng, String address) {
        DependenciaDBManager.UbicacionesDBManager db = new DependenciaDBManager.UbicacionesDBManager(context);
        Cursor cursor = db.getRows();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                db.update(1, latLng.latitude, latLng.longitude, address);
            } else {
                db.insert(latLng.latitude, latLng.longitude, address);
            }
        } else {
            Log.e("NULL_LOCATION_CURSOR","El cursor del DB Manager de ubicaciones es nulo, la aplicación no consigue acceso a la base de datos interna");
        }
    }
}
