package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.Pojo.Usuario;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 16/02/2018.
 */

public class CreaRecordatorios extends AsyncTask<Integer, Void, List<Recordatorio>> {
    //Constante que controla los dias de los que obtenemos los recordatorios
    //72 significa que se sacaran los recordatorios desde hoy hasta dentro de 3 dias
    private static final int HORAS = 72;
    private Conexion con;
    private List<Recordatorio> recordatorioList;
    private Context context;

    public CreaRecordatorios(Conexion con, List<Recordatorio> lista, Context context) {
        this.con = con;
        recordatorioList = lista;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<Recordatorio> doInBackground(Integer... integers) {
        ResultSet rs = con.getRecordatorios(integers[0]);
        try {

            while (rs.next()) {
                Date fechaActual = new Date();
                String nombreMedicina = rs.getString("Nombre");
                int toma = rs.getInt("Toma");
                double cantidad = rs.getDouble("Cantidad");

                int tomasEn3Dias = HORAS / toma;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaActual);
                DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(context);
                for (int i = 0; i < tomasEn3Dias; i++) {
                    String titulo = context.getString(R.string.tomar) + " " + nombreMedicina;
                    String contenido = context.getString(R.string.tomar) + " " + Recordatorio.cantidadATexto(context,cantidad) + context.getString(R.string.de) + " " + nombreMedicina;

                    //A la fecha actual le añadimos las horas de la toma
                    calendar.add(Calendar.HOUR, toma);

                    //Obtenemos la fecha y la hora de la toma
                    String fechaHora = calendar.getTime().toString();
                    Date fechaDeLaToma = calendar.getTime();

                    //Partimos el String para trabjar con el
                    String[] fechaHoraArray = fechaHora.split(" ");
                    String[] horaArray = fechaHoraArray[3].split(":");

                    //A partir de la fecha de la toma, obtenemos si es Hoy o Mañana
                    String cuando = Recordatorio.obtenHoyoMañana(context,fechaDeLaToma);

                    //Si no fuera ni hoy ni mañana, se pone el nombre del dia de la semana
                    if(cuando==null)
                           cuando= Recordatorio.obtenDiaTexto(context,fechaHoraArray[0]);
                    //Establecemos la hora con los datos del array
                    String hora = horaArray[0] + ":" + horaArray[1];
                    //creamos el recordatorio
                    //TODO HACER AQUI EL REINICIO DE CUENTA
                    Recordatorio r = new Recordatorio(0, titulo, contenido, cuando, hora);

                    //Establecemos una fecha para poder compararlos luego para ordenar la lista
                    r.setFecha(calendar.getTime());

                   db.insert(r.titulo,r.content,fechaHora,r.hora);
                    recordatorioList.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recordatorioList;
    }


}
