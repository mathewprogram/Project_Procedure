package ejemplo1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarClientesMaxCompra {

    public static void main(String[] args) {
          // Obtener la conexión
        Connection connection = ConexionMySQL_tienda_bd.obtainConection();
        // Consulta para ejecutar el procedimiento
        String query = "CALL show_clients_max_purchases()";

        try {
            // Preparar la llamada al procedimiento almacenado
            CallableStatement cs = connection.prepareCall(query);

            // Ejecutar el procedimiento
             boolean hasResultSet = cs.execute();
            
            //RECUPERAR LO DEL SELECT
            if (hasResultSet) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int cantidad = rs.getInt("cantidad");
                   
                    
                    System.out.format("%-20s %5d\n",nombre, cantidad);
                }
            } else {
                System.out.println("Error. No results.");
            }

        } catch (SQLException e) {
            System.out.println("Error in query: " + e.getMessage());
        } finally {
            try {
                // Cerrar la conexión
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
