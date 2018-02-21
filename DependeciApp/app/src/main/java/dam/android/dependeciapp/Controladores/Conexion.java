package dam.android.dependeciapp.Controladores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CreaConexion;
import dam.android.dependeciapp.AsyncTasks.Ping;
import dam.android.dependeciapp.Pojo.Recordatorio;

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
            PreparedStatement login = con.prepareStatement(sql);
            login.setString(1, DNI);
            login.setString(2, pass);
            ResultSet rs = login.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }

    }

    public ResultSet getMedicinas(int id) {

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

    //Este Metodo es el usado en el Thread principal. Hace el ping de manera Asincrona para
    //evitar el colapso de la App
    public static boolean isNetDisponible(Context context, boolean async) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if (actNetInfo != null && actNetInfo.isConnected()) {
            Ping p = new Ping();
            p.execute();
            try {
                boolean hayConexion = p.get();
                p = null;
                return hayConexion;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Este es el usado en Async tasks, hace el ping aqui mismo
    public static boolean isNetDisponible(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if (actNetInfo != null && actNetInfo.isConnected()) {
            InetAddress ping;
            String ip = "149.202.8.230"; // Ip de la máquina remota
            try {
                ping = InetAddress.getByName(ip);
                if (ping.isReachable(1000)) {// Tiempo de espera
                    ping = null;
                    return true;
                } else {
                    ping = null;
                    return false;
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return false;
        }
        return false;
    }

    public void insertInTareasPendientes(Recordatorio r, int toma, int idUsuario) {
        String sql = "INSERT INTO `proyecto1`.`TareasPendientes` (`idTarea`, `idDependiente`, `Fecha`, `Encabezado`, `Descripcion`, `horasRepeticion`, `tareaAsistente`, `realizada`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement insert = con.prepareStatement(sql);
            insert.setInt(1, r.getId());
            insert.setInt(2, idUsuario);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            insert.setString(3, sdf.format(r.getFecha()));
            insert.setString(4, r.getTitulo());
            insert.setString(5, r.getContent());
            insert.setInt(6, toma);
            insert.setInt(7, 0);
            insert.setInt(8, 0);
            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setTareaTerminada(int id) {
        String sql = "UPDATE `proyecto1`.`TareasPendientes` SET `realizada`='1' WHERE `idTarea`=?";
        try {
            PreparedStatement update = con.prepareStatement(sql);
            update.setInt(1, id);
            update.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
