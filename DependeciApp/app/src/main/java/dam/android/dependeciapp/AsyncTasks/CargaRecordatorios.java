package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 16/02/2018.
 */

public class CargaRecordatorios extends AsyncTask<Integer, Void, Boolean> implements Comparator<Recordatorio> {

    private List<Recordatorio> recordatorioList;
    private Context context;
    private Conexion con;
    private int idUsuario;

    public CargaRecordatorios(List<Recordatorio> lista, Context context, Conexion con, int idUsuario) {
        recordatorioList = lista;
        this.context = context;
        this.con = con;
        this.idUsuario = idUsuario;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Boolean doInBackground(Integer... integers) {
        if (con != null) {
            ResultSet rs = con.getRecordatorios(integers[0]);
            try {
                if (rs != null) {
                    DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(context);
                    db.vaciaTabla();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String titulo = rs.getString(3);
                        String contenido = rs.getString(4);
                        Date fecha = rs.getTimestamp(2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fecha);

                        String fechaHora = calendar.getTime().toString();

                        //Partimos el String para trabjar con el
                        String[] fechaHoraArray = fechaHora.split(" ");
                        String[] horaArray = fechaHoraArray[3].split(":");

                        String hora = horaArray[0] + ":" + horaArray[1];
                        //creamos el recordatorio
                        Recordatorio r = new Recordatorio(id, titulo, contenido, hora, calendar.getTime());

                        //Establecemos una fecha para poder compararlos luego para ordenar la lista
                        db.insert(String.valueOf(r.id), r.titulo, r.content, fechaHora, r.hora);

                        recordatorioList.add(r);
                    }

                }else{
                    return false;
                }
                //Lo hice con Collections en vez de con recordatorioList.sort(this);
                //Porque esta ultima, no esta disponible en versiones menorea a la API 24
                Collections.sort(recordatorioList, this);
                con = null;
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, R.string.no_carga_recordatorios, Toast.LENGTH_LONG).show();
        }
        con = null;
        return false;
    }

    @Override
    public int compare(Recordatorio o1, Recordatorio o2) {
        if (o1.getFecha().before(o2.getFecha())) {
            return -1;
        } else if (o1.getFecha().after(o2.getFecha())) {
            return 1;
        } else {
            return 0;
        }
    }
}
