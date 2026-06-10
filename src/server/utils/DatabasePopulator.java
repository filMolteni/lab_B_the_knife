package server.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DatabasePopulator {

    // Percorso RELATIVO come risorsa interna al progetto/JAR
    private static final String CSV_PATH = "/csv/michelin_my_maps.csv";

    public static void main(String[] args) {
        importCSV();
    }

    public static void importCSV() {

        String sql = """
            INSERT INTO ristorantitheknife
            (nome, nazione, citta, indirizzo, latitudine, longitudine,
             fascia_prezzo, delivery, prenotazione, tipo_cucina)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        int count = 0;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/theknife?useSSL=false&serverTimezone=UTC",
                "root",
                "");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            // Carica il CSV come RISORSA (funziona nel JAR e nel progetto)
            InputStream is = DatabasePopulator.class.getResourceAsStream(CSV_PATH);
            if (is == null) {
                throw new RuntimeException("❌ File CSV non trovato: " + CSV_PATH);
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));

            br.readLine(); // salta header

            String line;
            while ((line = br.readLine()) != null) {

                String[] campi = parseCSV(line);
                if (campi.length < 7) continue;

                String nome = campi[0];
                String indirizzo = campi[1];

                String[] loc = campi[2].split(",");
                String citta = loc[0].trim();
                String nazione = loc.length > 1 ? loc[1].trim() : "";

                int fasciaPrezzo = campi[3].length();
                String tipoCucina = campi[4];

                double longitudine = Double.parseDouble(campi[5]);
                double latitudine = Double.parseDouble(campi[6]);

                ps.setString(1, nome);
                ps.setString(2, nazione);
                ps.setString(3, citta);
                ps.setString(4, indirizzo);
                ps.setDouble(5, latitudine);
                ps.setDouble(6, longitudine);
                ps.setInt(7, fasciaPrezzo);
                ps.setBoolean(8, false);
                ps.setBoolean(9, false);
                ps.setString(10, tipoCucina);

                ps.addBatch();
                count++;
            }

            ps.executeBatch();
            conn.commit();

            System.out.println("✔ Importazione completata. Ristoranti inseriti: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] parseCSV(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else sb.append(c);
        }
        result.add(sb.toString());

        return result.toArray(new String[0]);
    }
}
