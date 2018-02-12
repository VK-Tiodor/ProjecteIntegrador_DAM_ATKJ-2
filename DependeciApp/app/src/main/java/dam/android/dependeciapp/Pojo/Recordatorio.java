package dam.android.dependeciapp.Pojo;

/**
 * Created by adria on 12/02/2018.
 */

public class Recordatorio {
    public final String titulo;
    public final String content;
    public final String cuando;
    public final String hora;

    public Recordatorio(String titulo, String content, String cuando,String hora) {
        this.titulo=titulo;
        this.content = content;
        this.cuando = cuando;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return content;
    }
}