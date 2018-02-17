package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 16/02/2018.
 */

public class CreaRecordatorios extends AsyncTask<Conexion, Void, List<Recordatorio>> {
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
    protected List<Recordatorio> doInBackground(Conexion... conexions) {
        ResultSet rs = con.getRecordatorios(1);
        try {

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
                    String contenido = context.getString(R.string.tomar) + " " + cantidadATexto(cantidad) + context.getString(R.string.de) + " " + nombreMedicina;

                    //A la fecha actual le añadimos las horas de la toma
                    calendar.add(Calendar.HOUR, toma);

                    //Obtenemos la fecha y la hora de la toma
                    String fechaHora = calendar.getTime().toString();
                    Date fechaDeLaToma = calendar.getTime();

                    //Partimos el String para trabjar con el
                    String[] fechaHoraArray = fechaHora.split(" ");
                    String[] horaArray = fechaHoraArray[3].split(":");

                    //A partir de la fecha de la toma, obtenemos si es Hoy o Mañana
                    String cuando = obtenHoyoMañana(fechaDeLaToma);

                    //Si no fuera ni hoy ni mañana, se pone el nombre del dia de la semana
                    if(cuando==null)
                           cuando= obtenDiaTexto(fechaHoraArray[0]);
                    //Establecemos la hora con los datos del array
                    String hora = horaArray[0] + ":" + horaArray[1];
                    //creamos el recordatorio
                    Recordatorio r = new Recordatorio(0, titulo, contenido, cuando, hora);

                    //Establecemos una fecha para poder compararlos luego para ordenar la lista
                    r.setFecha(calendar.getTime());
                    recordatorioList.add(r);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //ordenamos la lsita con un comparador de fechas
        recordatorioList.sort(new Comparator<Recordatorio>() {
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
        });

        return recordatorioList;
    }
    //Transforma el Double de la cantidad a texto
    private String cantidadATexto(double cantidad) {
        if (cantidad == 0.25) {
            return " " + context.getString(R.string.cuarto) + " ";
        }
        if (cantidad == 0.5) {
            return " " + context.getString(R.string.media) + " ";
        }
        if (cantidad == 1.0)
            return " " + context.getString(R.string.pastilla) + " ";

        return " " + (int) cantidad + context.getString(R.string.pastillas) + " ";
    }

    private String obtenDiaTexto(String day) {
        String[] dias = context.getResources().getStringArray(R.array.dias);
        switch (day) {
            case "Mon": return dias[0];
            case "Tue":return dias[1];
            case "Wed":return dias[2];
            case "Thu":return dias[3];
            case "Fry" :return dias[4];
            case "Sat" :return dias[5];
            case "Sun" :return dias[6];
        }
        return day;
    }
    //Introduces una fecha y te devuelve si es hoy o mañana. Si no lo es devuelve Null
    private String obtenHoyoMañana( Date toma){
        Date actual = new Date();
        String[] dias = context.getResources().getStringArray(R.array.dias);

        SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyy");
        String actualString = dt.format(actual);
        String tomaString = dt.format(toma);
        if (actualString.equalsIgnoreCase(tomaString))
            return dias[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        actual = calendar.getTime();
        actualString = dt.format(actual);
        if (actualString.equalsIgnoreCase(tomaString))
            return  dias[8];
        return null;
    }


}
