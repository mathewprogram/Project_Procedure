package ejemplo7;

import java.sql.*;
import java.util.*;

public class PrincipalCommitRollback {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection connection = ConnectionMySQLBDTRANSACCIONES.obtainConnection();

        if (connection != null) {
            menu(connection);
        } else {
            System.out.println("Connection error.");
        }

    }

    public static void menu(Connection connection) {
        while (true) {
            System.out.println("1. Activate restauration point");
            System.out.println("2. Update");
            System.out.println("3. Confirm transaction - Commit");
            System.out.println("4. Discard transaction - Rollback");
            System.out.println("5. SHow table Client");
            System.out.println("6. Exit");
            System.out.print("Insert option: ");
            String option = sc.next();

            switch (option) {
                case "1":
                    activateRestaurationPoint(connection);
                    pause();
                    break;
                case "2":
                    doUpdateTransaction(connection);
                    pause();
                    break;
                case "3":
                    confirmTransaction(connection);
                    pause();
                    break;
                case "4":
                    rollbackTransaction(connection);
                    pause();
                    break;
                case "5":
                    showTable(connection);
                    pause();
                    break;
                case "6":
                    exit(connection);
                    break;
            }
        }
    }

    // 1. Activate restauration point
    public static void activateRestaurationPoint(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    // 2. Update
    public static void doUpdateTransaction(Connection connection) {
        String query = "UPDATE Cliente SET nombre=?, email=? WHERE id_cliente=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nombre());
            ps.setString(2, email());
            ps.setInt(3, idCliente());

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                System.out.println("Updated.");
            } else {
                System.out.println("Update error.");
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public static String nombre() {
        System.out.print("Insert new name: ");
        String nombre = sc.next();
        return nombre;
    }

    public static String email() {
        System.out.print("Insert new email: ");
        String email = sc.next();
        return email;
    }

    public static int idCliente() {
        System.out.print("Choose id: ");
        int idCliente = sc.nextInt();
        return idCliente;
    }

    // 3. Confirm transaction
    public static void confirmTransaction(Connection connection) {
        try {
            connection.commit();
            System.out.println("Changes confirmed - commit.");
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    // 4. Rollback
    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
            System.out.println("Changes discarded - rollback.");
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    // 5. Show table Client
    public static void showTable(Connection connection) {
        String query = "SELECT * FROM Cliente";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.print("\n");
            System.out.print("""
                                         Cliente:
                             """);
            System.out.print("""
                             -------------------------------
                             """);
            System.out.printf("%-4s %10s %15s\n", "id", "Nombre", "Email");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int idCliente = rs.getInt("id_cliente");
                System.out.printf("%-4d %10s %15s\n", idCliente, nombre, email);
            }
        } catch (SQLException e) {
            System.out.println("Error." + e);
        }
    }

    // 6. Exit
    public static void exit(Connection connection) {
        System.exit(0);
        System.out.println();
    }

    public static void pause() {
        System.out.println("\nPress Enter to return to the menu...");
        try {
            sc.nextLine(); // Captura la línea que quedó pendiente
            sc.nextLine(); // Espera a que el usuario presione Enter
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
