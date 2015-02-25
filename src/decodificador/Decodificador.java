/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decodificador;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author dhinojosa
 */
public class Decodificador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        String content = new Scanner(new File("C:\\Users\\Diego\\Documents\\pruebas\\M0002_D1_F10_GPFT_R_141105120504.rep")).useDelimiter("\\n").next();
//        String[] result = content.split(",");
//        for (int i = 0; i < result.length; i++) {
//           System.out.println(result[i]); 
//        }

//        String ruta = "W:\\ESTACIONES_AUTOMATICAS\\M0188\\D1\\F10\\";             //SE DEFINE LA RUTA DE LECTURA DE LOS ARCHIVOS
         String ruta = "C:\\Users\\Patricio\\Documents\\DATOS\\M5103\\";             //SE DEFINE LA RUTA DE LECTURA DE LOS ARCHIVOS
//        String ruta = args[0];
//        File carpeta = new File(ruta);
//        File[] listaDeArchivos = carpeta.listFiles();                      //SE GUARDA EN UN ARRAY LA LISTA DE LOS ARCHIVOS


        Path path = FileSystems.getDefault().getPath(ruta);
        Visitador vi = new Visitador(ruta);
        System.out.println(Files.walkFileTree(path, vi));
    }
}