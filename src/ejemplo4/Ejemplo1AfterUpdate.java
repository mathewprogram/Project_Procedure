package ejemplo4;

import java.sql.Connection;
import java.sql.*;


public class Ejemplo1AfterUpdate {

    public static void main(String[] args) {
        Connection connection = ConexionMysqlBDTRIGGER.obtenerConexion();
        if(connection != null){
            System.out.println("Ok connection.");
            if(launchTriggerUpdate(connection)==true){
                System.out.println("Trigger ok.");
            }else{
                System.out.println("Error After Update Trigger.");
            }
        }else {
            System.out.println("Error connection.");
        }
    }
    
    public static boolean launchTriggerUpdate(Connection connection){
        boolean flag = true;
        String nombre = "Mihai";
        String clave = "1234abcde";
        int idUsuario = 2;
        String query = "UPDATE usuario SET nombre = ?, clave = ? WHERE id_usuario = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,nombre);
            ps.setString(2,clave);
            ps.setInt(3,idUsuario);
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                System.out.println("User exists.");
            }else{
                System.out.println("User " + idUsuario + "missing.");
            }
        }catch(SQLException e){
            flag = false;
        }
        
        return flag;
    }
    
}
