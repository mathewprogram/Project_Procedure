package ejemplo4;

import java.sql.*;

public class Ejemplo5BeforeInsert {


    public static void main(String[] args) {
        Connection connection = ConexionMysqlBDTRIGGER.obtenerConexion();
        
        if(connection != null){
            System.out.println("Connection ok.");
            if(launchTriggerInsert(connection)==true){
                System.out.println("Trigger ok.");
            }else{
                System.out.println("Error Before Insert.");
            }
        }else {
            System.out.println("Connection error.");
        }
    }
    
    public static boolean launchTriggerInsert(Connection connection){
        boolean flag = true;
        String nombre = "Pepita";
        String clave = "Pep!t@";
        String query = "INSERT INTO Usuario (nombre,clave) VALUES (?, ?);";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,nombre);
            ps.setString(2,clave);
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Insert ok.");
            }else{
                System.out.println("Error inserting.");
            }
        }catch(SQLException e){                
            System.out.println(e);
            flag = false;
        }
        
        return flag;
        
    }
    
}
