package ejemplo7;

import java.sql.*;

public class ConnectionMySQLBDTRANSACCIONES {
    
    public static void main(String[] args){
        obtainConnection();
    }

    public static Connection obtainConnection(){
        String url = "jdbc:mysql://localhost:3306/BDTRANSACCIONES";
        String user = "mathew";
        String key = "Passw0rd!";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, key);
        }catch(SQLException e){
            connection = null;
        }
        return connection;
    }
    
}


