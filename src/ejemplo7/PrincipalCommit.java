package ejemplo7;

import java.sql.*;

public class PrincipalCommit {

    public static void main(String[] args) {
        Connection connection = ConnectionMySQLBDTRANSACCIONES.obtainConnection();
        PreparedStatement ps = null;
        if (connection != null) {
            System.out.println("Connection ok.");
            //Transaction insert
            String query = "INSERT INTO Cliente(nombre, email) VALUES ('Mathew', 'mathew@gmail.com')";
            String query1 = "INSERT INTO Cliente(nombre, email) VALUES ('Lavinia', 'lavinia@gmail.com')";
            try {
                connection.setAutoCommit(false); // START TRANSACTION
                ps = connection.prepareStatement(query);
                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    System.out.println("Query inserted.");
                } else {
                    System.out.println("Error query.");
                }

                ps = connection.prepareStatement(query1);
                int affectedRow1 = ps.executeUpdate();
                if (affectedRow1 > 0) {
                    System.out.println("Query 1 inserted.");
                } else {
                    System.out.println("Error query 1.");
                }
                connection.commit();
                System.out.println("Changes confirmed: Commit");
            } catch (SQLException e) {
                System.out.println("Error " + e);
            }

        } else {
            System.out.println("Connection error.");
        }
    }

}
