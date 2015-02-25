/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public final class Conexion {

    private Connection conexion;
    private Statement sentencia;
    private String conector; //url del conector a la base de datos
    private String usuario; // usuario de la base de datos
    private String clave; // password de la base de datos
    private String driver; //Driver de conexión
    private String host;
    private String puerto;
    private String base;

    public Conexion() {
    }

    public Conexion(String usuario, String clave, String driver, String host, String puerto, String base) {
        setConector("jdbc:postgresql://" + host + ":" + puerto + "/" + base);
        this.usuario = usuario;
        this.clave = clave;
        this.driver = driver;
        this.host = host;
        this.puerto = puerto;
        this.base = base;
    }

    public Conexion(String url, String usuario, String clave, String driver) {
        setConector(url);
        this.usuario = usuario;
        this.clave = clave;
        this.driver = driver;
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Statement getSentencia() {
        return sentencia;
    }

    public void setSentencia(Statement sentencia) {
        this.sentencia = sentencia;
    }

    public String getConector() {
        return conector;
    }

    public void setConector(String conector) {
        this.conector = conector;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void conectar() {
        try {
            Class.forName(driver); //Ubicar libreria del manejador 
            this.conexion = DriverManager.getConnection(conector, usuario, clave); //conectar a la base de datos
            this.conexion.setAutoCommit(false);//Quitar auto commit de las transacciones a la base
            this.sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
//            System.out.println("Conexión a base exitosa");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public void desconectar() {
        try {
            this.conexion.close();
            this.sentencia.close();
//            System.out.println("Conexión a base cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public void commit() {
        try {
            this.conexion.commit();
//            System.out.println("Transacción existosa");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public void rollback() {
        try {
            this.conexion.rollback();
//            System.out.println("Transacción anulada");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
}
