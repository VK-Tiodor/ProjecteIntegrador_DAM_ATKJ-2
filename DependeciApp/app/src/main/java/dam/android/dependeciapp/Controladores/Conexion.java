package dam.android.dependeciapp.Controladores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CreaConexion;

/**
 * Created by adria on 06/02/2018.
 */

public class Conexion {

    private Connection con;

    public Conexion() {
        CreaConexion();
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }



    private void CreaConexion() {
        try {
            CreaConexion cc = new CreaConexion();
            cc.execute();
            con = cc.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public ResultSet IniciaSesion(String DNI, String pass) {
        try {
            String sql = "call inicia_sesion(?, ?)";
            PreparedStatement login = con.prepareStatement(sql);
            login.setString(1, DNI);
            login.setString(2, pass);
            ResultSet rs = login.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }

    }

    public ResultSet getRecordatorios(int id){

        try {
            String sql = "call get_medicinas(?)";
            PreparedStatement login = con.prepareStatement(sql);
            login.setInt(1, id);
            ResultSet rs = login.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }
   public static boolean isNetDisponible(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
               context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

}
