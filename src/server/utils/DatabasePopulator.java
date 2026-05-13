package server.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DatabasePopulator {

    private static final String CSV_PATH =
            "C:\\Users\\Fil\\Desktop\\UNI\\lab_B\\database\\michelin_my_maps.csv";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/theknife";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static void main(String[] args) {
        new DatabasePopulator().importCSV();
    }

    public void importCSV() {

        String sql = "INSERT INTO RistorantiTheKnife " +
                "(nome, nazione, citta, indirizzo, latitudine, longitudine, fascia_prezzo, delivery, prenotazione, tipo_cucina) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int count = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {

            String line;
            br.readLine(); // salta intestazione

            while ((line = br.readLine()) != null) {

                List<String> fields = parseCSVLine(line);

                if (fields.size() < 12)
                    continue;

                // --- CAMPI BASE ---
                String nome = fields.get(0);
                String indirizzo = fields.get(1);
                String citta = fields.get(2);
                String nazione = fields.get(4);

                // --- TROVA COORDINATE AUTOMATICAMENTE ---
                double lat = 0;
                double lon = 0;
                int found = 0;

                for (String f : fields) {
                    try {
                        double val = Double.parseDouble(f);
                        if (found == 0) {
                            lon = val; // primo numero = longitudine
                            found++;
                        } else if (found == 1) {
                            lat = val; // secondo numero = latitudine
                            found++;
                            break;
                        }
                    } catch (Exception ignored) {}
                }

                if (found < 2)
                    continue; // riga senza coordinate valide

                // --- TIPO CUCINA ---
                String tipoCucina = fields.get(8);

                // --- PREZZO ---
                int fasciaPrezzo = 40;
                try {
                    fasciaPrezzo = Integer.parseInt(fields.get(15)) * 20;
                } catch (Exception ignored) {}

                boolean delivery = false;
                boolean prenotazione = false;

                ps.setString(1, nome);
                ps.setString(2, nazione);
                ps.setString(3, citta);
                ps.setString(4, indirizzo);
                ps.setDouble(5, lat);
                ps.setDouble(6, lon);
                ps.setInt(7, fasciaPrezzo);
                ps.setBoolean(8, delivery);
                ps.setBoolean(9, prenotazione);
                ps.setString(10, tipoCucina);

                ps.executeUpdate();
                count++;
            }

            System.out.println("Importazione completata. Ristoranti inseriti: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- PARSER CSV ROBUSTO ---
    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString().trim());
        return result;
    }
}
