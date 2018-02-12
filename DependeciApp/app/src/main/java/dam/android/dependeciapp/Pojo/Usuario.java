package dam.android.dependeciapp.Pojo;

import com.google.android.gms.maps.MapView;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Usuario(ResultSet rs) throws SQLException {
       DNI= rs.getString("DNI");
       nombre=rs.getString("Nombre");
       apellidos=rs.getString("Apellido");
       fNacimiento=rs.getDate("Nacimiento");
       genero=rs.getString("Genero");
       tipoDeDependiente=rs.getString("Tipo de Dependiente");
       fAlta=rs.getDate("Fecha de Alta");

    }



    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoDeDependiente() {
        return tipoDeDependiente;
    }

    public void setTipoDeDependiente(String tipoDeDependiente) {
        this.tipoDeDependiente = tipoDeDependiente;
    }

    public Date getfAlta() {
        return fAlta;
    }

    public void setfAlta(Date fAlta) {
        this.fAlta = fAlta;
    }
}
