package ejemplo8;

import java.sql.*;
import java.util.*;

public class MenuCRUDMySQL {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Connection connection = ConnectionMySQL.obtainConnection();

        if (connection != null) {
            menu(connection);
        } else {
            System.out.println("Connection error.");
        }
    }

    public static void menu(Connection connection) {
        while (true) {
            System.out.println(" 1. Show Users.");
            System.out.println(" 2. Create User.");
            System.out.println(" 3. Delete User.");
            System.out.println(" 4. Grant Privileges.");
            System.out.println(" 5. Revoke Privileges.");
            System.out.println(" 6. Select User.");
            System.out.println(" 7. Show User acces to Database(s).");
            System.out.println(" 8. Create Data Base.");
            System.out.println(" 9. Create Table(s).");
            System.out.println("10. Show Data Base information.");
            System.out.println("11. Exit.");
            System.out.print("Insert option: ");
            String option = sc.next();

            switch (option) {
                case "1":
                    showUsers(connection);
                    pause();
                    break;
                case "2":
                    createUser(connection);
                    pause();
                    break;
                case "3":
                    deleteUser(connection);
                    pause();
                    break;
                case "4":
                    grantPrivileges(connection);
                    pause();
                    break;
                case "5":
                    revokePrivileges(connection);
                    pause();
                    break;
                case "6":
                    selectUser(connection);
                    pause();
                    break;
                case "7":
                    String username = selectUser(connection); // Selección del usuario
                    if (username != null) {
                        showDatabases(connection, username); // Mostrar bases de datos del usuario seleccionado
                    }
                    pause();
                    break;
                case "8":
                    createDataBase(connection);
                    pause();
                    break;
                case "9":
                    createTable(connection);
                    pause();
                    break;
                case "10":
                    showDatabaseInfo(connection);
                    pause();
                    break;
                case "11":
                    exit(connection);
                    break;
            }
        }
    }

    // 1. showUsers
    public static void showUsers(Connection connection) {
        String query = "SELECT user, host FROM mysql.user";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.print("----------------------------");
            System.out.print("\n");
            System.out.printf("%-18s %-18s\n", "user", "host");
            System.out.print("----------------------------");
            System.out.print("\n");
            while (rs.next()) {
                String user = rs.getString("user");
                String host = rs.getString("host");
                System.out.printf("%-18s %-18s\n", user, host);
            }
        } catch (SQLException e) {
            System.out.println("Error." + e);
        }
    }

    // 2. createUser
    public static void createUser(Connection connection) {
        System.out.print("Enter new username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        String query = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("User " + username + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    // 3. deleteUser
    public static void deleteUser(Connection connection) {
        System.out.print("Enter username to delete: ");
        String username = sc.next();

        String query = "DROP USER ?@'localhost'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.executeUpdate();
            System.out.println("User " + username + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // 4. grantPrivileges
    public static void grantPrivileges(Connection connection) {
        System.out.print("Enter username: ");
        String username = sc.next();

        System.out.print("Grant privileges to all databases? (yes/no): ");
        String grantAll = sc.next().toLowerCase();

        String grantQuery;

        if (grantAll.equals("yes")) {
            // Conceder privilegios a todas las bases de datos
            grantQuery = "GRANT ALL PRIVILEGES ON *.* TO '" + username + "'@'localhost'";
        } else {
            // Conceder privilegios a una base de datos específica
            System.out.print("Enter database: ");
            String database = sc.next();
            grantQuery = "GRANT ALL PRIVILEGES ON " + database + ".* TO '" + username + "'@'localhost'";
        }

        String flushQuery = "FLUSH PRIVILEGES";

        try {
            // Conceder privilegios
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(grantQuery);

            // Ejecutar FLUSH PRIVILEGES
            stmt.executeUpdate(flushQuery);

            System.out.println("Privileges granted and flushed for user " + username);
        } catch (SQLException e) {
            System.out.println("Error granting privileges: " + e.getMessage());
        }
    }

    // 5. revokePrivileges
    public static void revokePrivileges(Connection connection) {
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter database: ");
        String database = sc.next();

        String query = "REVOKE ALL PRIVILEGES ON " + database + ".* FROM ?@'localhost'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.executeUpdate();
            System.out.println("Privileges revoked from user " + username + " on database " + database);
        } catch (SQLException e) {
            System.out.println("Error revoking privileges: " + e.getMessage());
        }
    }

    // 6. selectUser
    public static String selectUser(Connection connection) {
        String query = "SELECT user, host FROM mysql.user";
        List<String> users = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int i = 1;
            System.out.println("Select a user by number:");
            while (rs.next()) {
                String user = rs.getString("user");
                String host = rs.getString("host");
                users.add(user);
                System.out.printf("%d. %s@%s\n", i++, user, host);
            }

            // Pedir al usuario que elija un número
            System.out.print("Insert number: ");
            int userIndex = sc.nextInt();

            // Verificar si el índice es válido
            if (userIndex > 0 && userIndex <= users.size()) {
                return users.get(userIndex - 1);
            } else {
                System.out.println("Invalid selection.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
            return null;
        }
    }

    // 7. showDatabases
    public static void showDatabases(Connection connection, String username) {
        // Verifica si el usuario tiene privilegios globales
        String globalPrivilegesQuery = "SELECT * FROM mysql.user WHERE user = ? AND host = 'localhost' AND (Super_priv = 'Y' OR Select_priv = 'Y' OR Insert_priv = 'Y' OR Update_priv = 'Y' OR Delete_priv = 'Y')";

        try {
            PreparedStatement ps = connection.prepareStatement(globalPrivilegesQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Si el usuario tiene privilegios globales, mostrar todas las bases de datos
                System.out.println("User " + username + " has global privileges. Showing all databases:");
                showAllDatabases(connection);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking global privileges for user: " + e.getMessage());
        }

        // Si no tiene privilegios globales, mostrar las bases de datos específicas
        String specificPrivilegesQuery = "SELECT DISTINCT table_schema FROM information_schema.SCHEMA_PRIVILEGES WHERE grantee = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(specificPrivilegesQuery);
            ps.setString(1, "'" + username + "'@'localhost'");
            ResultSet rs = ps.executeQuery();

            System.out.println("Databases accessible by " + username + ":");
            System.out.print("-------------------------------\n");
            while (rs.next()) {
                String databaseName = rs.getString("table_schema");
                System.out.println(databaseName);
            }
            System.out.print("-------------------------------\n");
        } catch (SQLException e) {
            System.out.println("Error showing databases for user: " + e.getMessage());
        }
    }

    public static void showAllDatabases(Connection connection) {
        String query = "SHOW DATABASES";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("All Databases:");
            System.out.print("-------------------------------\n");
            while (rs.next()) {
                String databaseName = rs.getString("Database");
                System.out.println(databaseName);
            }
            System.out.print("-------------------------------\n");
        } catch (SQLException e) {
            System.out.println("Error showing all databases: " + e.getMessage());
        }
    }

    // 8. createDataBase
    public static void createDataBase(Connection connection) {
        System.out.print("Enter the name of the new database: ");
        String dbName = sc.next();

        // Consulta para crear una nueva base de datos
        String query = "CREATE DATABASE " + dbName;

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Database " + dbName + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    // 9. createTable
    public static void createTable(Connection connection) {
        System.out.print("Enter the name of the database to use: ");
        String dbName = sc.next();

        System.out.print("Enter the name of the new table: ");
        String tableName = sc.next();

        System.out.println("Define the columns for the table.");
        List<String> columns = new ArrayList<>();
        String addMore;
        do {
            System.out.print("Enter column name: ");
            String columnName = sc.next();
            System.out.print("Enter data type (e.g., VARCHAR(255), INT, etc.): ");
            String dataType = sc.next();
            columns.add(columnName + " " + dataType);

            System.out.print("Do you want to add another column? (yes/no): ");
            addMore = sc.next();
        } while (addMore.equalsIgnoreCase("yes"));

        // Unimos las definiciones de columnas
        String columnsDefinition = String.join(", ", columns);

        // SQL para crear una tabla
        String query = "CREATE TABLE " + dbName + "." + tableName + " (" + columnsDefinition + ")";

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Table " + tableName + " created successfully in database " + dbName + ".");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // 10. showDatabaseInfo
    public static void showDatabaseInfo(Connection connection) {
        System.out.print("Enter the name of the database to show information: ");
        String dbName = sc.next();

        try {
            // Use the database
            String useDbQuery = "USE " + dbName;
            Statement useDbStmt = connection.createStatement();
            useDbStmt.executeUpdate(useDbQuery);

            // Retrieve all tables from the database
            String tablesQuery = "SHOW TABLES";
            Statement stmt = connection.createStatement();
            ResultSet rsTables = stmt.executeQuery(tablesQuery);

            System.out.println("Tables in database '" + dbName + "':");
            System.out.print("----------------------------------------\n");

            List<String> tables = new ArrayList<>();
            while (rsTables.next()) {
                String tableName = rsTables.getString(1);
                System.out.println(tableName);
                tables.add(tableName);
            }
            System.out.print("----------------------------------------\n");

            // For each table, show columns and then display the data
            for (String table : tables) {
                // Show table structure
                String columnsQuery = "DESCRIBE " + table;
                ResultSet rsColumns = stmt.executeQuery(columnsQuery);

                System.out.println("\nTable: " + table);
                System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "Field", "Type", "Null", "Key", "Default");
                System.out.print("---------------------------------------------------------------\n");
                while (rsColumns.next()) {
                    String field = rsColumns.getString("Field");
                    String type = rsColumns.getString("Type");
                    String nullable = rsColumns.getString("Null");
                    String key = rsColumns.getString("Key");
                    String defaultValue = rsColumns.getString("Default");

                    System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", field, type, nullable, key, defaultValue);
                }
                System.out.print("\n-----------------------------------------------------------------------------------\n");

                // Show table data
                String dataQuery = "SELECT * FROM " + table;
                ResultSet rsData = stmt.executeQuery(dataQuery);

                ResultSetMetaData metaData = rsData.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Print column headers
                System.out.println("\nData in table: " + table);
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", metaData.getColumnName(i));
                }
                System.out.print("\n-----------------------------------------------------------------------------------\n");

                // Print each row of data
                while (rsData.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-20s", rsData.getString(i));
                    }
                    System.out.println();
                }
                System.out.print("\n-----------------------------------------------------------------------------------\n");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving database information: " + e.getMessage());
        }
    }

    // 11. Exit
    public static void exit(Connection connection) {
        System.exit(0);
        System.out.println();
    }

    // Pause
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
