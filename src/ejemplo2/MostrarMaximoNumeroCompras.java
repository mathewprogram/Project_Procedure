package ejemplo2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class MostrarMaximoNumeroCompras {

    public static void main(String[] args) {
        // Obtener la conexión
        Connection connection = ejemplo1.ConexionMySQL_tienda_bd.obtainConection();
        if (connection != null) {
            String query = "{ ? = CALL obtain_maximum_purchases_number() }";

            try {
                CallableStatement cs = connection.prepareCall(query);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                int cantidad = cs.getInt("cantidad");
                System.out.println("Cantidad: " + cantidad);
            } catch (SQLException e) {
                System.out.println("Error query." + e);
            }
        } else {
            System.out.println("Error connection.");
        }
    }

}
