package dam.android.dependeciapp.AsyncTasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by swido on 06/02/2018.
 */

public class LanzaLlamada extends AsyncTask<String, Void, LanzaLlamada> {

    private final String MACHINE = "172.16.226.22";
    private final int PORT = 9090;
    private final String cadenaComprobacionAlertaCliente = "eb8d3f1b179bfca7a3d31880b4d66778";
    private final String cadenaComprobacionAlertaServidor = "3779ba59f06f7ae68c14527375ff4654";


    @Override
    protected LanzaLlamada doInBackground(String... strings)  {
        Boolean result = false;
        try {

            // Se crea la conexión con el servidor
            Socket clientSocket = new Socket();
            InetSocketAddress sockAddr = new InetSocketAddress(MACHINE, PORT);

            clientSocket.connect(sockAddr);

            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            try {

                // Se escribe primero el mensaje clave para que el servidor reconozca la alerta
                bw.write(cadenaComprobacionAlertaCliente);
                bw.newLine();
                bw.flush();

                // Se espera a que el servidor conteste que ha recibido la alerta
                String message = br.readLine();


                // Se envia al servidor el id del usuario
                if (message.equals(this.cadenaComprobacionAlertaServidor)) {
                    bw.write(strings[0]);
                    bw.newLine();
                    bw.flush();
                } else {
                    throw new IOException();
                }

                // Se cierran las conexiones
            } finally {
                br.close();
                bw.close();
                clientSocket.close();
            }
        } catch (IOException e){
            System.out.println(e);
        }
        return null;
    }
}
