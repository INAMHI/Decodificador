/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duke
 */
public class FechasUtiles {

    private String fecha1hora_anterior = null;
    private String fecha1dia_anterior = null;
    private String hora1hora_anterior = null;

    /**
     * @return the fecha1hora_nterior
     */
    public String getFecha1hora_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.HOUR, -1);
        int dia = HoraAnterior.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = HoraAnterior.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = HoraAnterior.get(Calendar.YEAR);  //año
        fecha1hora_anterior = anio + "-" + mes + "-" + dia;
        return fecha1hora_anterior;
    }

    public String getFecha1dia_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.DAY_OF_MONTH, -1);
        int dia = HoraAnterior.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = HoraAnterior.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = HoraAnterior.get(Calendar.YEAR);  //año
        fecha1dia_anterior = anio + "-" + mes + "-" + dia;
        return fecha1dia_anterior;
    }

    /**
     * @return the hora1hora_anterior
     */
    public String getHora1hora_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.HOUR, -1);
        int hora = HoraAnterior.get(Calendar.HOUR_OF_DAY);
        int minuto = HoraAnterior.get(Calendar.MINUTE);
        hora1hora_anterior = hora + ":" + minuto;
        return hora1hora_anterior;
    }

    /* ------------------------------------------------------------------------- */
    public String getFechaActual() { //Fecha actual del sistema
        Date fechaHoy = new Date();
        String fecha_actual;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual = formato.format(fechaHoy);
        return fecha_actual;
    }

    public String getHoraActual() { //Hora actual del sistema
        Date ahora = new Date();
        String hora_actual;
        SimpleDateFormat formato = new SimpleDateFormat("hh.mm.ss");
        hora_actual = formato.format(ahora);
        return hora_actual;
    }

    public static String getFechaYHoraActual() {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY);  //año
        int minuto = cal.get(Calendar.MINUTE);  //año
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
//        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + "00" + ":00";
        //Date aux=DateUtils.round(cal.getTime(),Calendar.SECOND);
//        System.out.println(strDate);
        return strDate;
    }

    public static String setFechaYHoraActual(Date fecha) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTime(fecha);
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public static String setFechaActual(String fecha) {
        String strDate = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(sdf.parse(fecha));
            strDate = sdf2.format(cal.getTime());
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }

    public String getFechaYHoraMenosUnaHoraAnterior(String fechaActual, String hora) {
        String[] aux = hora.split("_");
        String strDate = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fechaActual));
            int diferencia = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, -diferencia);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, -diferencia);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, -diferencia);
                    break;

                default:
                    cal.add(Calendar.HOUR, -diferencia);
                    break;
            }
            strDate = sdf.format(cal.getTime());
//            System.out.println(strDate);
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }
    /*-------------------------------------------------------------------------*/

    public static String getFechaYHoraMasUnaHoraPosterior(String fecha, String hora) {
        String strDate = "";
        try {
            String[] aux = hora.split("_");

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fecha));
            int sumando = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, sumando);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, sumando);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, sumando);
                    break;

                default:
                    cal.add(Calendar.HOUR, sumando);
                    break;
            }
            strDate = sdf.format(cal.getTime());
//            System.out.println(strDate);
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }
}
