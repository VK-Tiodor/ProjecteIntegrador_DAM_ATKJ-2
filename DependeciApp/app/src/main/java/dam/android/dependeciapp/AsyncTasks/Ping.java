package dam.android.dependeciapp.AsyncTasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by adria on 20/02/2018.
 */

public class Ping extends AsyncTask<Void,Void,Boolean> {



    protected Boolean doInBackground(Void... voids) {
        InetAddress ping;
        String ip = "149.202.8.230"; // Ip de la máquina remota
        try {
            ping = InetAddress.getByName(ip);
            if (ping.isReachable(1000)) {// Tiempo de espera
                ping=null;
                return true;
            } else {
                ping=null;
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return false;
    }
}
