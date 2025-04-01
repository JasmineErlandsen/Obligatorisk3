package no.hvl.dat107;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class runSQL {
    // Update these with your database details
    private static final String URL = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h573042";
    private static final String USER = "h573042";
    private static final String PASSWORD = "Kikerter3rdigg";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database successfully!");
            System.out.println("Enter your SQL query:");
            String sqlQuery = scanner.nextLine();

            try (Statement stmt = conn.createStatement()) {
                boolean hasResultSet = stmt.execute(sqlQuery);
                
                if (hasResultSet) {
                    // Handle SELECT queries
                    try (ResultSet rs = stmt.getResultSet()) {
                        while (rs.next()) {
                            // Print each column
                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                System.out.print(rs.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    }
                } else {
                    // Handle UPDATE, INSERT, DELETE, ALTER, etc.
                    int updateCount = stmt.getUpdateCount();
                    System.out.println("Query executed successfully. Rows affected: " + updateCount);
                }
            }

        } catch (Exception e) {
            System.out.println("Error executing query: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}