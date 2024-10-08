package ejemplo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL_tienda_bd {
    
    public static Connection obtainConection(){
        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "12345678";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
        }
        return connection;
    }
    
}
