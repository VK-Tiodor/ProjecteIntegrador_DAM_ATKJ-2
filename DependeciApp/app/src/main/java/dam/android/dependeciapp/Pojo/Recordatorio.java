package dam.android.dependeciapp.Pojo;

/**
 * Created by adria on 12/02/2018.
 */

public class Recordatorio {

    public final int id;
    public final String titulo;
    public final String content;
    public final String cuando;
    public final String hora;
    public final boolean terminado;

    public Recordatorio(int id, String titulo, String content, String cuando, String hora, boolean terminado) {
        this.id = id;
        this.titulo=titulo;
        this.content = content;
        this.cuando = cuando;
        this.hora = hora;
        this.terminado = terminado;
    }

    @Override
    public String toString() {
        return content;
    }
}