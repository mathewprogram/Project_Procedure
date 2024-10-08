package ejemplo6;

import java.io.*;

public class MetodoArchivo {

    public static String readQueryTrigger() {
        File f;
        FileReader fr;
        BufferedReader br;

        String nra = "data/querys.sql";
        StringBuilder cadena = new StringBuilder(); // StringBuilder para rendimiento
        try {
            f = new File(nra);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String fila;

            while ((fila = br.readLine()) != null) {
                fila = fila.trim(); // Elimina espacios al inicio y al final
                if (!fila.isEmpty()) { // Solo añade líneas no vacías
                    cadena.append(fila).append("\n");
                }
            }
            br.close(); // Cierra el BufferedReader
            fr.close(); // Cierra el FileReader
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return cadena.toString().trim(); // Devuelve la consulta
    }

}
