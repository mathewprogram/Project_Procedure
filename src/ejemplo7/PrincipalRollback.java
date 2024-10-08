package ejemplo7;

import java.sql.*;

public class PrincipalRollback {

    public static void main(String[] args) {
        Connection connection = ConnectionMySQLBDTRANSACCIONES.obtainConnection();
        PreparedStatement ps = null;
        if (connection != null) {
            System.out.println("Connection ok.");
            //Transaction insert
            String query = "DELETE FROM Cliente WHERE id_cliente = 1";
            
            try {
                connection.setAutoCommit(false); // START TRANSACTION
                ps = connection.prepareStatement(query);
                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    System.out.println("Query inserted.");
                } else {
                    System.out.println("Error query.");
                }
                connection.rollback();
                System.out.println("Rollback confirmed.");
                
            } catch (SQLException e) {
                System.out.println("Error " + e);
            }

        } else {
            System.out.println("Connection error.");
        }
    }

}
