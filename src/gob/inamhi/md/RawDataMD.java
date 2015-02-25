/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.md;

import decodificador.Visitador;
import gob.inamhi.util.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Diego
 */
public class RawDataMD {

    private final String base = "bandahm";
    private final String usuario = "postgres";
    private final String password = "inamhidb";
    private final String driver = "org.postgresql.Driver";
    private final String host = "192.168.1.226";
    private final String puerto = "5432";
//    private final String base = "bandahm";
//    private final String usuario = "postgres";
//    private final String password = "postgres";
//    private final String driver = "org.postgresql.Driver";
//    private final String host = "localhost";
//    private final String puerto = "5433";
    private String queryInsercion = "";
    final static Logger logger = Logger.getLogger(Visitador.class);

    public Integer getEstacionId(String codigoEstacion, String medioTransmision, String idDatalogger) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        Integer esta__id = 0;
        try {
            // TODO code application logic here
            con.conectar();
            String query = "select esta__id from administrative.vta__estaciones where puobcodi='" + codigoEstacion + "' and trco__id='" + medioTransmision + "' and transdtid='" + idDatalogger + "' ";
            ResultSet rs = con.getSentencia().executeQuery(query);
            rs.next();
            esta__id = rs.getInt("esta__id");

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return esta__id;
    }

    public List<Integer> getParametrosId(List<String> nemonico) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        List<Integer> copa__id = new ArrayList<Integer>();
        String aux = "";
        try {
            // TODO code application logic here
            con.conectar();
            Iterator it = nemonico.iterator();

            while (it.hasNext()) {
                aux = it.next().toString();
                String query = "select copa__id from administrative.copa where copanemo='" + aux + "'";
                ResultSet rs = con.getSentencia().executeQuery(query);
                boolean respuesta = rs.next();
                if (respuesta) {
                    
                    copa__id.add(rs.getInt("copa__id"));
                } else {
                    copa__id.add(-1);
                    logger.error("No se encontro nemonico: " + aux+". Ignorando dato");
                }
            }

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error:" + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return copa__id;
    }

    public List<Integer> getConfiguracionesId(Integer idEstacion, List<Integer> sensores, String idDatalogger) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        List<Integer> cfges__id = new ArrayList<Integer>();
        try {
            // TODO code application logic here
            con.conectar();
            for (Integer aux : sensores) {
                String query = "select max(cfges__id) as cfges__id from administrative.cfges where esta__id=" + idEstacion + " and cfgesords=" + aux + " and cfgesordd='" + idDatalogger + "' ";
                ResultSet rs = con.getSentencia().executeQuery(query);
                boolean respuesta = rs.next();
                if (respuesta) {
                    cfges__id.add(rs.getInt("cfges__id"));
                } else {
                    cfges__id.add(1);
                    logger.error("No se encontro configuración, ingresando con configuración desconocida");
                }
            }
        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return cfges__id;
    }

    public Integer getFormatoId(String formato) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        Integer frmt__id = 0;
        try {
            // TODO code application logic here
            con.conectar();
            String query = "select frmt__id from raw_data.frmt where frmtcodi='" + formato + "'";
            ResultSet rs = con.getSentencia().executeQuery(query);
            rs.next();
            frmt__id = rs.getInt("frmt__id");

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return frmt__id;
    }

    public boolean insertarRegistroData(String data__id, Integer esta__id, Integer copa__id, String cadt__id, Integer cali1__id, Integer cfges__id, String dataf__id, String trco__id, String inda__id, String datafetd, Double datavalo, Boolean datactrl, Integer datavers, String datauing, String datafing) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        boolean respuesta = false;
        try {
            // TODO code application logic here
            con.conectar();
            String query = "INSERT INTO storage.data(data__id,esta__id,copa__id,cadt__id,cali1__id,cfges__id,dataf__id,trco__id,inda__id,datafetd,datavalo,datactrl,datavers,datauing,datafing) "
                    + "values('" + data__id + "'," + esta__id + "," + copa__id + ",'" + cadt__id + "'," + cali1__id + "," + cfges__id + ",'" + dataf__id + "','" + trco__id + "','" + inda__id + "','" + datafetd + "'," + datavalo + "," + datactrl + "," + datavers + ",'" + datauing + "','" + datafing + "')";
            respuesta = con.getSentencia().execute(query);
            con.commit();
            logger.info("Informacion: DATO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);
//            System.out.println("DATO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();
            logger.info("Informacion: DATO NO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return respuesta;
    }

    public void acumulacionRegistroData(String data__id, Integer esta__id, Integer copa__id, String cadt__id, Integer cali1__id, Integer cfges__id, String dataf__id, String trco__id, String inda__id, String datafetd, Double datavalo, Boolean datactrl, Integer datavers, String datauing, String datafing) {

        queryInsercion += "INSERT INTO storage.data(data__id,esta__id,copa__id,cadt__id,cali1__id,cfges__id,dataf__id,trco__id,inda__id,datafetd,datavalo,datactrl,datavers,datauing,datafing) "
                + "values('" + data__id + "'," + esta__id + "," + copa__id + ",'" + cadt__id + "'," + cali1__id + "," + cfges__id + ",'" + dataf__id + "','" + trco__id + "','" + inda__id + "','" + datafetd + "'," + datavalo + "," + datactrl + "," + datavers + ",'" + datauing + "','" + datafing + "');";

    }

    public boolean insertarRegistroData() {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        boolean respuesta = true;
//        String querys[] = queryInsercion.split(";");
//        for (int i = 0; i < querys.length; i++) {
        try {
            // TODO code application logic here
            con.conectar();
//                con.getSentencia().execute(querys[i]);
            con.getSentencia().execute(queryInsercion);
            con.commit();
            respuesta = true;
//            System.out.println("Insercion Exitosa");
            logger.info("Informacion: Insercion Exitosa");

        } catch (SQLException ex) {
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();
            respuesta = false;
//            System.out.println("DATO NO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);
//            System.out.println(ex.getMessage());
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
//        }
        return respuesta;
    }

    public boolean insertarRegistroDataFuente(Integer frmt__id, String inda__id, String datafcdes, String datafnoar, String dataffear, String datafdato, String datafuing, String dataffing) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        boolean respuesta = false;
        try {
            // TODO code application logic here
            con.conectar();
            String query = "INSERT INTO raw_data.dataf(frmt__id,inda__id,datafcdes,datafnoar,dataffear,datafdato,datafuing,dataffing) "
                    + "values(" + frmt__id + ",'" + inda__id + "','" + datafcdes + "','" + datafnoar + "','" + dataffear + "','" + datafdato + "','" + datafuing + "','" + dataffing + "')";

            respuesta = con.getSentencia().execute(query);
            con.commit();
//            System.out.println("DATO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);

        } catch (SQLException ex) {
            con.rollback();
//            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("DATO NO INGRESADO: " + data__id + " Fecha: " + datafetd + " Valor: " + datavalo + " " + "cdt" + ": " + cadt__id);
            logger.error("Error: " + ex.getMessage());
        } finally {
            con.desconectar();
        }
        return respuesta;
    }
}
