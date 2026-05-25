package server.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility per popolare il database con i ristoranti provenienti
 * dal file CSV esportato da Google My Maps (michelin_my_maps.csv).
 *
 * Funzionalità principali:
 * - lettura del CSV riga per riga
 * - parsing sicuro dei campi (gestione virgolette)
 * - inserimento batch nella tabella `ristorantitheknife`
 * - commit unico per massime prestazioni
 *
 * Questo tool viene eseguito una tantum per inizializzare il database.
 */
public class DatabasePopulator {

    /** Percorso locale del file CSV da importare. */
    private static final String CSV_PATH =
            "C:\\Users\\Fil\\Desktop\\UNI\\lab_B\\database\\michelin_my_maps.csv";

    /**
     * Entry point dell'utility.
     * Esegue direttamente l'importazione del CSV.
     */
    public static void main(String[] args) {
        importCSV();
    }

    /**
     * Importa il file CSV e inserisce i ristoranti nella tabella `ristorantitheknife`.
     *
     * Logica:
     * 1. Apre connessione JDBC
     * 2. Prepara un INSERT parametrico
     * 3. Legge il CSV riga per riga
     * 4. Effettua parsing robusto tramite {@link #parseCSV(String)}
     * 5. Inserisce i record in batch
     * 6. Esegue commit finale
     *
     * In caso di errore, stampa lo stack trace.
     */
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
             PreparedStatement ps = conn.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {

            conn.setAutoCommit(false);

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

    /**
     * Parser CSV che gestisce correttamente:
     * - campi con virgole tra virgolette
     * - campi normali separati da virgole
     *
     * Implementazione manuale per evitare problemi con CSV complessi.
     *
     * @param line riga del CSV
     * @return array di campi estratti
     */
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
