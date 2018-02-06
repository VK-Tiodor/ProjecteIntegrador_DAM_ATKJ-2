package dam.android.dependeciapp.Controladores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by adria on 06/02/2018.
 */

public class Conexion {

    private Connection con;

    public Conexion() {
            CreaConexion();
    }

    public void CreaConexion() {
        try {
            //Registrando el Driver
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";
            //Conectando
            Properties pc = new Properties();
            pc.put("user", "root");
            pc.put("password", "1234");
            this.con = DriverManager.getConnection(jdbcUrl, pc);

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
        String sql = "call inicia_sesion(?, ?)";
        try {
            PreparedStatement login = this.con.prepareStatement(sql);
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
