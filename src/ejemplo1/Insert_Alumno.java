package ejemplo1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

public class Insert_Alumno {

    public static void main(String[] args) {
        version1();
    }

    public static void version1() {
        String[][] registros = {
            {"Lavinia","Filip","dam","1983-12-28"},
            {"Maria","Navarro","daw","1999-12-20"},
            {"Lorena","Anca","dam","1996-06-12"}
        };
        
        Connection connection = ejemplo1.ConexionMySQL.obtainConection();
        String query = "Call insertar_alumno(?,?,?,?)";
        try {
            for (int i = 0; i < registros.length; i++) {
                CallableStatement cs = connection.prepareCall(query);
                cs.setString("nombre_i", registros[i][0]);
                cs.setString("apellidos_i", registros[i][1]);
                cs.setString("grupo_i", registros[i][2]);
                cs.setDate("fecha_nacimiento_i", Date.valueOf(registros[i][3]));
                cs.execute();
            }
        } catch (SQLException e) {

        }
    }

    public static void version2() {
        Connection connection = ejemplo1.ConexionMySQL.obtainConection();
        if (connection != null) {
            String query = "Call insertar_alumno(?,?,?,?)";
            try {
                CallableStatement cs = connection.prepareCall(query);
                cs.setString("nombre_i", "Mihai");
                cs.setString("apellidos_i", "Matei");
                cs.setString("grupo_i", "dam");
                cs.setDate("fecha_nacimiento_i", Date.valueOf("1991-01-17"));
                cs.execute();
                System.out.println("Inserted.");
            } catch (SQLException e) {
                System.out.println("Query error." + e);
            }
        } else {
            System.out.println("Error connection.");
        }
    }
}
