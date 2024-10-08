package ejemplo8;

import java.sql.*;

public class ConnectionMySQL {
    
    public static void main(String[] args){
        obtainConnection();
    }

    public static Connection obtainConnection(){
        String url = "jdbc:mysql://localhost:3306/BDTRANSACCIONES";
        String user = "root";
        String key = "12345678";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, key);
        }catch(SQLException e){
            connection = null;
        }
        return connection;
    }
    
}


