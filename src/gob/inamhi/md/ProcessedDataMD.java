/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.md;

import gob.inamhi.util.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Diego
 */
public class ProcessedDataMD {

//    private final String base = "bandahm";
//    private final String usuario = "postgres";
//    private final String password = "postgres";
//    private final String driver = "org.postgresql.Driver";
//    private final String host = "10.0.4.70";
//    private final String puerto = "5433";
    private final String base = "bandahm";
    private final String usuario = "postgres";
    private final String password = "postgres";
    private final String driver = "org.postgresql.Driver";
    private final String host = "localhost";
    private final String puerto = "5433";

    public int buscarRepetido(String tabla, int esta, int copa, int cfges, String fecha) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        int numeroValores = -1;
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "select " + ncolumna + "nval" + " from " + tabla + " where esta__id=" + esta + " and copa__id=" + copa + " and cfges__id=" + cfges + " and " + ncolumna + "fetd='" + fecha + "' and capto__id=2";
            System.out.println("buscando repetido: " + query);
            ResultSet rs = con.getSentencia().executeQuery(query);
            while (rs.next()) {
                numeroValores = rs.getInt(1);
            }
            return numeroValores;
        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            return numeroValores;
        } finally {
            con.desconectar();
        }
    }

    public void insertarDato(String tabla, String id, int esta, int copa, int cali, int cfges, String fecha, double valor, int nval, boolean estado, String uing) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "insert into " + tabla + " (" + ncolumna + "__id,esta__id,copa__id,cali__id,cfges__id,capto__id," + ncolumna + "fetd," + ncolumna + "valo," + ncolumna + "nval," + ncolumna + "esta," + ncolumna + "uing) values ('" + id + "'," + esta + "," + copa + "," + cali + "," + cfges + ",2,'" + fecha + "'," + valor + "," + nval + "," + estado + ",'" + uing + "')";
            System.out.println("insertando registro: " + query);
            con.getSentencia().execute(query);
            con.commit();

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();

        } finally {
            con.desconectar();
        }
    }

    public void actualizarDato(String tabla, int esta, int copa, int cali, int cfges, String fecha, double valor, int nval, String umod, String fmod) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "update " + tabla + " set cali__id=" + cali + "," + ncolumna + "valo=" + valor + "," + ncolumna + "nval=" + nval + "," + ncolumna + "umod='" + umod + "'," + ncolumna + "fmod='" + fmod + "' where esta__id=" + esta + " and copa__id=" + copa + " and " + ncolumna + "fetd='" + fecha + "' and cfges__id=" + cfges + " and capto__id=2";
            System.out.println("actualizando registro: " + query);
            con.getSentencia().executeUpdate(query);
            con.commit();

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();


        } finally {
            con.desconectar();

        }
    }
}
