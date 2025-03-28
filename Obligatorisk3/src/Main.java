import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Database-tilkoblingsdetaljer
        String url = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h573042";
        String user = "h573042";
        String password = "Kikerter3rdigg";


        try {
            // Etablerer tilkobling
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Tilkoblet til databasen!");

            // Kjører en spørring
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ansatt");

            while (rs.next()) {
                System.out.println(rs.getString(1)); // Skriver ut første kolonne
            }

            // Lukk
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
