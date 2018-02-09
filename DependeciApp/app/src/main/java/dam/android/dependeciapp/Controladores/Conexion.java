package dam.android.dependeciapp.Controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Connection getConnection() {
        return con;

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

}
