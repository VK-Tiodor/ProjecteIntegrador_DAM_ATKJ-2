package dam.android.dependeciapp.Pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adria on 12/02/2018.
 */

public class Recordatorio implements Serializable {

    public final int id;
    public final String titulo;
    public final String content;
    public final String cuando;
    public final String hora;
    public final boolean terminado;
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
        this.terminado = false;
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

    public boolean isTerminado() {
        return terminado;
    }

    @Override
    public String toString() {
        return content;
    }


}