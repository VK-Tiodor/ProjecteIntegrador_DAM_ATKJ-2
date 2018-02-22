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

    public Conexion(boolean noAsync) {
        try {
            String driver = "com.mysql.jdbc.Driver";

            Class.forName(driver).newInstance();

            String jdbcUrl = "jdbc:mysql://149.202.8.230:3306/proyecto1";
            //Conectando
            Properties pc = new Properties();
            pc.put("user", "grupo1");
            pc.put("password", "jatk1");
            con = DriverManager.getConnection(jdbcUrl, pc);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet IniciaSesion(String DNI, String pass) {
        try {
            String sql = "call inicia_sesion(?, ?)";
            if (con == null)
                return null;
            PreparedStatement login = con.prepareStatement(sql);
            login.setString(1, DNI);
            login.setString(2, pass);
            ResultSet rs = login.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }

    }


    public int getLastId() {
        String sql = "select idTarea from TareasPendientes\n" +
                "order by idTarea desc limit 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.first();
            return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static String toMD5(String md5) {
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
        if (actNetInfo != null && actNetInfo.isConnected())
            return true;
        else
            return false;
    }


    public void setTareaTerminada(int id) {
        String sql = "CALL estableceLaTareaRealizada(?)";
        try {
            PreparedStatement update = con.prepareStatement(sql);
            update.setInt(1, id);
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRecordatorios(int id) {
        try {
            if (con != null) {
                String sql = "call get_recordatorios(?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);

                return ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
