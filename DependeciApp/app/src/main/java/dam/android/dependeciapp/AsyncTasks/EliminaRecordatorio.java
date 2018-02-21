package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import dam.android.dependeciapp.Controladores.Conexion;

/**
 * Created by adria on 21/02/2018.
 */

public class EliminaRecordatorio extends AsyncTask<Integer,Void,Void>  {
    private Context context;
    private Conexion con;
    public EliminaRecordatorio(Context context,Conexion con){
        this.context=context;
        this.con=con;
    }
    @Override
    protected Void doInBackground(Integer... integers) {
        if(Conexion.isNetDisponible(context))
            if(con!=null)
                con.eliminaTarea(integers[0]);

        return null;
    }
}
