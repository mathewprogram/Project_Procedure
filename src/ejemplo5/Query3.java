package ejemplo5;

import java.sql.*;

public class Query3 {

    public static void main(String[] args) {
        // Obtain Connection
        Connection connection = ConnectionMySQLInstituto.obtainConnection();
        if (connection != null) {
            String query = "CALL ObtenerProfesoresYModulos()";

            try {
                CallableStatement cs = connection.prepareCall(query);
                boolean hasResults = cs.execute();
                
                if(hasResults){
                    ResultSet rs = cs.getResultSet();
                    
                    System.out.println("Profesor\t\tModulo");
                    System.out.println("-----------------------------------");
                    while(rs.next()){
                        String profesor = rs.getString("profesor");
                        String modulo = rs.getString("modulo");
                        System.out.println(profesor + "\t\t" + modulo);
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Consult error." + e.getMessage());
            }
        } else {
            System.out.println("Connection error.");
        }
    }
}
