package server.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DatabasePopulator {

    // 🔥 METTI QUI IL PERCORSO DEL FILE CSV (NON DELLA CARTELLA!)
    private static final String CSV_PATH =
    "C:\\Users\\User\\OneDrive\\Desktop\\labb\\lab_B_the_knife\\database\\michelin_my_maps.csv";


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

            System.out.println("Leggo CSV da: " + CSV_PATH);

            String line = br.readLine(); // salta intestazione
            if (line == null) {
                System.out.println("❌ CSV vuoto o non leggibile");
                return;
            }

            while ((line = br.readLine()) != null) {

                List<String> fields = parseCSVLine(line);

                if (fields.size() < 12) {
                    System.out.println("⚠️ Riga ignorata (colonne insufficienti): " + fields.size());
                    continue;
                }

                // --- CAMPI BASE ---
                String nome = fields.get(0);
                String indirizzo = fields.get(1);
                String citta = fields.get(2);
                String nazione = fields.get(4);

                // --- COORDINATE ---
                double lat = 0;
                double lon = 0;
                int found = 0;

                for (String f : fields) {
                    try {
                        double val = Double.parseDouble(f);
                        if (found == 0) {
                            lon = val;
                            found++;
                        } else if (found == 1) {
                            lat = val;
                            found++;
                            break;
                        }
                    } catch (Exception ignored) {}
                }

                if (found < 2) {
                    System.out.println("⚠️ Riga ignorata (coordinate mancanti)");
                    continue;
                }

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

            System.out.println("✅ Importazione completata. Ristoranti inseriti: " + count);

        } catch (Exception e) {
            System.out.println("❌ ERRORE IMPORTAZIONE CSV");
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
