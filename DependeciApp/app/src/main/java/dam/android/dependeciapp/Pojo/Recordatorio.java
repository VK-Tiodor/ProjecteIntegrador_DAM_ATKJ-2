package dam.android.dependeciapp.Pojo;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

public class Recordatorio implements Serializable {

    public final int id;
    public final String titulo;
    public final String content;
    public final String cuando;
    public final String hora;
    public  Date fecha=null;

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha){
        this.fecha=fecha;

    }

    public Recordatorio(int id, String titulo, String content, String cuando, String hora) {
        this.id = id;
        this.titulo=titulo;
        this.content = content;
        this.cuando = cuando;
        this.hora = hora;
    }


    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContent() {
        return content;
    }

    public String getCuando() {
        return cuando;
    }

    public String getHora() {
        return hora;
    }

    @Override
    public String toString() {
        return content;
    }

    //Transforma el Double de la cantidad a texto
    public static String cantidadATexto(Context context,double cantidad) {
        if (cantidad == 0.25) {
            return  context.getString(R.string.cuarto) + " ";
        }
        if (cantidad == 0.5) {
            return  context.getString(R.string.media) + " ";
        }
        if (cantidad == 1.0)
            return  context.getString(R.string.pastilla) + " ";

        return (int) cantidad + context.getString(R.string.pastillas) + " ";
    }

    public static String obtenDiaTexto(Context context,String day) {
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
    public static String obtenHoyoMañana(Context context, Date toma){
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