package ejemplo1;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class Principal {

    public static void main(String[] args) {
        Connection connection = ConexionMySQL.obtainConection();
        if(connection != null){
            String query = "Call sumar_dos_numeros_1(?,?,?)";
            try{
            CallableStatement cs = connection.prepareCall(query);
            cs.setInt("n1_i", 3);
            cs.setInt("n2_i", 6);
            cs.registerOutParameter("suma_o", java.sql.Types.INTEGER);
            cs.execute();
            int suma = cs.getInt("suma_o");
            System.out.println("Suma: " + suma);
            }catch(SQLException e){
                System.out.println("Query error." + e);
            }
        }else {
            System.out.println("Error connection.");
        }
    }
    
}
