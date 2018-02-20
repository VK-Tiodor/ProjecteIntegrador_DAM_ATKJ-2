package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 16/02/2018.
 */

public class CreaRecordatorios extends AsyncTask<Integer, Void, Boolean> implements Comparator<Recordatorio> {
    //Constante que controla los dias de los que obtenemos los recordatorios
    //72 significa que se sacaran los recordatorios desde hoy hasta dentro de 3 dias
    private static final int HORAS = 24;
    private List<Recordatorio> recordatorioList;
    private Context context;
    private Conexion con;

    public CreaRecordatorios(List<Recordatorio> lista, Context context,Conexion con) {
        recordatorioList = lista;
        this.context = context;
        this.con=con;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Boolean doInBackground(Integer... integers) {
        if (con != null) {
            ResultSet rs = con.getRecordatorios(integers[0]);
            try {
                DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(context);
                db.restartAutonum();
                int id = 1;
                while (rs.next()) {
                    Date fechaActual = new Date();
                    String nombreMedicina = rs.getString("Nombre");
                    int toma = rs.getInt("Toma");
                    double cantidad = rs.getDouble("Cantidad");

                    int tomasEn3Dias = HORAS / toma;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaActual);
                    for (int i = 0; i < tomasEn3Dias; i++) {
                        String titulo = context.getString(R.string.tomar) + " " + nombreMedicina;
                        String contenido = context.getString(R.string.tomar) + " " + Recordatorio.cantidadATexto(context, cantidad) + context.getString(R.string.de) + " " + nombreMedicina;

                        //A la fecha actual le añadimos las horas de la toma
                        calendar.add(Calendar.HOUR, toma);

                        //Obtenemos la fecha y la hora de la toma
                        String fechaHora = calendar.getTime().toString();
                        Date fechaDeLaToma = calendar.getTime();

                        //Partimos el String para trabjar con el
                        String[] fechaHoraArray = fechaHora.split(" ");
                        String[] horaArray = fechaHoraArray[3].split(":");

                        String hora = horaArray[0] + ":" + horaArray[1];
                        //creamos el recordatorio
                        Recordatorio r = new Recordatorio(id, titulo, contenido,  hora,calendar.getTime());

                        //Establecemos una fecha para poder compararlos luego para ordenar la lista
                        db.insert(r.titulo, r.content, fechaHora, r.hora);
                        recordatorioList.add(r);
                        id++;
                    }
                }
                Collections.sort(recordatorioList,this);
                con = null;
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, R.string.no_carga_recordatorios, Toast.LENGTH_LONG).show();
        }
        con=null;
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
