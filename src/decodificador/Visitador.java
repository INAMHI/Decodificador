/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decodificador;

import gob.inamhi.md.RawDataMD;
import gob.inamhi.util.FechasUtiles;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author dhinojosa
 */
public class Visitador implements FileVisitor<Path> {

    private String ruta;
    private String cabecera[] = new String[0];
    private String encabezado;
    private String añoCreacion;
    private String mesCreacion;
    private String nombreArchivo;
    private boolean respuestaInsert;
    private int contador;
    final static Logger logger = Logger.getLogger(Visitador.class);

    public Visitador(String ruta) {
        this.ruta = ruta;
        this.respuestaInsert = false;
        this.contador = 0;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (!attrs.isDirectory()) {
            return FileVisitResult.CONTINUE;
        } else {
            if (contador++ == 0) {
                return FileVisitResult.CONTINUE;
            } else {
                return FileVisitResult.SKIP_SUBTREE;
            }
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        File archivo = file.toFile();
//        System.out.println(file);
        logger.info("Informacion: Archivo: " + file);
        try {
            Scanner escaner = new Scanner(archivo).useDelimiter("\\n"); //SE DEFINE UN OBJETO DE TIPO SCANNER PARA PODER LEER LAS LINEAS DEL ARCHIVO
            /**
             * ***** GUARADO EN VARIABLES DE DATOS RELVANTES DEL NOMBRE DEL
             * ARCHIVO********
             */
            nombreArchivo = archivo.getName().trim();
            String auxNombreArchivo = nombreArchivo.split("\\.")[0];
            String[] contenidoNombreArchivo = auxNombreArchivo.split("_");
            String estacion = contenidoNombreArchivo[0];
            String idDatalogger = contenidoNombreArchivo[1];
            String formato = contenidoNombreArchivo[2];
            String medioTransmision = contenidoNombreArchivo[3];
            String idTransmision = contenidoNombreArchivo[4];
            String fechaArchivo = contenidoNombreArchivo[5];
            SimpleDateFormat formatoFechaArchivo = new SimpleDateFormat("yyMMddHHmmss");
            Date fechaCreacionArchivo = new Date();
            try {
                fechaCreacionArchivo = formatoFechaArchivo.parse(fechaArchivo);
            } catch (ParseException e) {
            }
            String fechaCreacionArchivoFormateada = FechasUtiles.setFechaYHoraActual(fechaCreacionArchivo);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaCreacionArchivo);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            añoCreacion = "" + year;
            mesCreacion = "" + month;
            String fechaActual = FechasUtiles.getFechaYHoraActual();

//            System.out.println("Estacion: " + estacion);
//            System.out.println("Datalogger: " + idDatalogger);
//            System.out.println("Formato: " + formato);
//            System.out.println("Medio: " + medioTransmision);
//            System.out.println("Transmision: " + idTransmision);
//            System.out.println("Fecha de Creacion: " + fechaCreacionArchivo.toString());

            logger.info("Informacion: Estacion: " + estacion + " Datalogger: " + idDatalogger + " Formato: " + formato + " Medio: " + medioTransmision + " Transmision: " + idTransmision + " Fecha de Creacion: " + fechaCreacionArchivo.toString());
            /**
             * ***************FIN DE GUARDADO DE DATOS
             * RELEVANTES*******************
             */
            List<String> contenidoNemonico = new ArrayList<String>();
            List<Integer> sensores = new ArrayList<Integer>();

            if (escaner.hasNext()) {
                encabezado = escaner.next();
                cabecera = encabezado.split(",");

                for (int i = 1; i < cabecera.length; i += 2) {
                    String[] aux = cabecera[i].trim().split("_");
                    sensores.add(Integer.parseInt(aux[0]));
                    contenidoNemonico.add(aux[1]);
                }
            }

            String contenidoArchivo = encabezado;

            RawDataMD rawData = new RawDataMD();
            List<Integer> copa__id = rawData.getParametrosId(contenidoNemonico);
            Integer esta__id = rawData.getEstacionId(estacion, medioTransmision, idDatalogger);
            Integer frmt__id = rawData.getFormatoId(formato);
            List<Integer> cfges__id = rawData.getConfiguracionesId(esta__id, sensores, idDatalogger);
            /**
             * ***************** INICIO DE LA LECTURA DE LAS LINEAS DEL ARCHIVO
             * EN PARES POR CADA COLUMNA E INSERCIÓN EN LA BASE DE
             * DATOS***********
             */
            boolean fechaMala = false;

            while (escaner.hasNext()) {
                String content = escaner.next();
                contenidoArchivo = contenidoArchivo + "\n" + content;
                String[] result = content.split(",");
                Iterator itCopa = copa__id.iterator();
                Iterator itCfges = cfges__id.iterator();

                for (int i = 1; i < result.length; i += 2) {
                    if (i + 1 < result.length) {
                        Integer auxCopa = (Integer) itCopa.next();
                        if (auxCopa == -1) {
                            break;
                        }
                        Integer auxCfges = (Integer) itCfges.next();
                        String fecha = result[0].trim();
                        String calidad = result[i + 1].trim();
                        Integer cali1__id = 5;
                        Double valor = null;

                        try {
                            valor = Double.parseDouble(result[i].trim());
                        } catch (Exception e) {
//                            logger.error("Error: " + e.getMessage());
                        }

                        SimpleDateFormat formatoFechaTomaDato = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date fechaTomaDato = new Date();
                        try {
                            fechaTomaDato = formatoFechaTomaDato.parse(fecha);
                        } catch (ParseException e) {
                            logger.error("Error: " + e.getMessage());
                            break;
                        }


                        String fechaId = FechasUtiles.setFechaYHoraActual(fechaTomaDato);
//                        String fechaActualFecha = FechasUtiles.setFechaActual(fechaActual);
                        String fechaActualFecha = FechasUtiles.getFechaYHoraMasUnaHoraPosterior(fechaActual, "5_h");
                        String fechaTomaDatoFecha = FechasUtiles.setFechaActual(fechaId);

                        if (fechaActualFecha.compareTo(fechaTomaDatoFecha) >= 0) {
                            String id_dato = esta__id + "_" + auxCopa + "_" + auxCfges + "_" + medioTransmision + "_" + fechaId.replaceAll(" ", "_");
//                                rawData.insertarRegistroData(id_dato, esta__id, auxCopa, calidad, cali1__id, auxCfges, nombreArchivo, medioTransmision, idTransmision, fechaId, valor, Boolean.TRUE, 0, "ADMIN", fechaActual);
                            rawData.acumulacionRegistroData(id_dato, esta__id, auxCopa, calidad, cali1__id, auxCfges, nombreArchivo, medioTransmision, idTransmision, fechaId, valor, Boolean.TRUE, 0, "ADMIN", fechaActual);
//                                System.out.println("ID Dato: " + id_dato + " Fecha: " + fechaTomaDato.toString() + " Valor: " + valor + " " + "cdt" + ": " + calidad);
                        } else {
                            fechaMala = true;
                            break;
                        }

                    }
                }
            }
            escaner.close();


            if (!fechaMala) {
                respuestaInsert = rawData.insertarRegistroData();
            } else {
                respuestaInsert = false;
            }

            if (respuestaInsert) {
                rawData.insertarRegistroDataFuente(frmt__id, idTransmision, estacion, nombreArchivo, fechaCreacionArchivoFormateada, contenidoArchivo, "ADMIN", fechaActual);
            }
            /**
             * ****************** FIN DE LECTURA DEL
             * ARCHIVO*******************************
             */
            /*INICIO DE CREACION DE RUTA POR AÑO Y MES PARA MOVER EL ARCHIVO PROCESADO*/
//                    String rutaAño = ruta + añoCreacion + "\\";
            /*FIN DE CREACION DE RUTA POR AÑO Y MES PARA MOVER EL ARCHIVO PROCESADO*/
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            logger.error("Error: " + e.getMessage());
        }
        String rutaAño = ruta + añoCreacion + "/";
//                    String rutaMes = rutaAño + mesCreacion + "\\";
        String rutaMes = rutaAño + mesCreacion + "/";
//                    String rutaErroneos = rutaAño + mesCreacion + "no_processed\\";
        String rutaErroneos = rutaAño + mesCreacion + "/noprocessed/";
        File directorioAño = new File(rutaAño);
//        System.out.println("Ruta año: " + rutaAño);
        File directorioMes = new File(rutaMes);
//        System.out.println("Ruta mes: " + rutaMes);
        File directorioErroneos = new File(rutaErroneos);
//        System.out.println("Ruta Errores: " + rutaErroneos);

        Path source = Paths.get(ruta + nombreArchivo);
        Path target = Paths.get(directorioMes + "/" + nombreArchivo);
        Path erroneos = Paths.get(directorioErroneos + "/" + nombreArchivo);

        if (!directorioAño.exists()) {
//            System.out.println("creating directory: " + rutaAño);
            logger.info("Informacion: Creating directory: " + rutaAño);
            try {
                directorioAño.mkdir();
                directorioMes.mkdir();
                if (respuestaInsert) {
//                                Files.copy(source, target, REPLACE_EXISTING, COPY_ATTRIBUTES);
//                    System.out.println("Respuesta positiva: " + source + " " + target);
                    Files.copy(source, target);
                    Files.delete(source);
                } else {
                    if (!directorioErroneos.exists()) {
                        directorioErroneos.mkdir();
                    }

//                                Files.copy(source, target);
//                    System.out.println("Respuesta negativa: " + source.getFileName() + " " + erroneos);
                    Files.copy(source, erroneos, REPLACE_EXISTING, COPY_ATTRIBUTES);
                    Files.delete(source);
                }
            } catch (Exception se) {
                //handle it
//                System.out.println(se.getMessage());
                logger.error("Error: " + se.getMessage());
            }
        } else {
            if (!directorioMes.exists()) {
//                System.out.println("creating directory: " + rutaMes);
                logger.info("Informacion: Creating directory: " + rutaMes);
                try {
                    directorioMes.mkdir();
                    if (respuestaInsert) {
//                        System.out.println("Respuesta positiva: " + source + " " + target);
                        Files.copy(source, target, REPLACE_EXISTING, COPY_ATTRIBUTES);
//                                    Files.copy(source, target);
                        Files.delete(source);
                    } else {
                        if (!directorioErroneos.exists()) {
                            directorioErroneos.mkdir();
                        }
//                        System.out.println("Respuesta negativa: " + source.getFileName() + " " + erroneos);
                        //                                    Files.copy(source, target);
                        Files.copy(source, erroneos, REPLACE_EXISTING, COPY_ATTRIBUTES);
                        Files.delete(source);
                    }
                } catch (Exception se) {
                    //handle it
//                    System.out.println(se.getMessage());
                    logger.error("Error: " + se.getMessage());
                }
            } else {
                try {
                    if (respuestaInsert) {
//                        System.out.println("Respuesta positiva: " + source + " " + target);
                        Files.copy(source, target, REPLACE_EXISTING, COPY_ATTRIBUTES);
//                                    Files.copy(source, target);
                        Files.delete(source);
                    } else {
                        if (!directorioErroneos.exists()) {
                            directorioErroneos.mkdir();
                        }
//                        System.out.println("Respuesta negativa: " + source.getFileName() + " " + erroneos);
                        Files.copy(source, erroneos, REPLACE_EXISTING, COPY_ATTRIBUTES);
//                                    Files.copy(source, target);
                        Files.delete(source);
                    }
                } catch (Exception se) {
//                    System.out.println(se.getMessage());
                    logger.error("Error: " + se.getMessage());
                }
            }
        }
//        System.out.println(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }
}
