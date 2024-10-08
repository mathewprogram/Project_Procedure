package ejemplo6;

import java.sql.*;

public class Principal {

    public static void main(String[] args) {
        Connection connection = ejemplo4.ConexionMysqlBDTRIGGER.obtenerConexion();

        if (connection != null) {
            System.out.println("Connection ok.");
            if (createQueryTrigger(connection)) {
                System.out.println("Query ok.");
            } else {
                System.out.println("Query error.");
            }
        } else {
            System.out.println("Connection error.");
        }
    }

    public static boolean createQueryTrigger(Connection connection) {
        boolean flag = true;

        // Separar las consultas
        String[] queries = MetodoArchivo.readQueryTrigger().split(";");
        for (String query : queries) {
            query = query.trim(); // Eliminar espacios en blanco
            if (!query.isEmpty()) { // Verificar que no sea una cadena vacía
                try {
                    Statement stmt = connection.createStatement();
                    stmt.execute(query);
                } catch (SQLException e) {
                    flag = false;
                    System.err.println("SQL Error Code: " + e.getErrorCode());
                    System.err.println("SQL State: " + e.getSQLState());
                    System.err.println("Error Message: " + e.getMessage());
                    e.printStackTrace(); // Para obtener más detalles del error
                }
            }
        }

        return flag;
    }
}
