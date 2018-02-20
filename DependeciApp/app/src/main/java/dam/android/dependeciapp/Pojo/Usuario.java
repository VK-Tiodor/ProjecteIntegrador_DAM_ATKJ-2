package dam.android.dependeciapp.Pojo;

import android.database.Cursor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by adria on 09/02/2018.
 */

public class Usuario implements Serializable {

    private int idPersona;
    private String DNI;
    private String nombre;
    private String apellidos;
    private Date fNacimiento;
    private String genero;
    private String tipoDeDependiente;
    private Date fAlta;
    private String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Usuario(ResultSet rs) throws SQLException {
        rs.first();
        idPersona = rs.getInt("idPersona");
        DNI = rs.getString("DNI");
        nombre = rs.getString("Nombre");
        apellidos = rs.getString("Apellidos");
        fNacimiento = rs.getDate("Nacimiento");
        genero = rs.getString("Genero");
        tipoDeDependiente = rs.getString("Tipo de Dependiente");
        fAlta = rs.getDate("FechaAlta");
    }

    public Usuario(Cursor cursor) {
        try {
            idPersona = cursor.getInt(0);
            DNI = cursor.getString(1);
            nombre = cursor.getString(2);
            apellidos = cursor.getString(3);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dt = df.parse(cursor.getString(4));
            fNacimiento = new Date(dt.getTime());
            genero = cursor.getString(5);
            tipoDeDependiente = cursor.getString(6);
            dt = df.parse(cursor.getString(7));
            fAlta = new Date(dt.getTime());
            pass = cursor.getString(8);
            cursor.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public int getIdPersona() {
        return idPersona;
    }
    public String getDNI() {
        return DNI;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public Date getfNacimiento() {
        return fNacimiento;
    }
    public String getGenero() {
        return genero;
    }
    public String getTipoDeDependiente() {
        return tipoDeDependiente;
    }
    public Date getfAlta() {
        return fAlta;
    }

}
