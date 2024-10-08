package ejemplo1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Calculadora {

    public static void main(String[] args) {
        Connection connection = ConexionMySQL.obtainConection();
        if(connection != null){
            String query = "Call calculadora(?,?,?,?)";
            try{
            CallableStatement cs = connection.prepareCall(query);
            cs.setInt("n1", 2);
            cs.setInt("n2", 0);
            cs.setString("operacion", "/");
            cs.registerOutParameter("resultado", java.sql.Types.DOUBLE);
            cs.execute();
            double resultado = cs.getDouble("resultado");
            System.out.println("Result: " + resultado);
            }catch(SQLException e){
                System.out.println("Query error." + e);
            }
        }else {
            System.out.println("Error connection.");
        }
    }
    
}
