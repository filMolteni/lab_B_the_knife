package server.dao;

import server.model.Ristorante;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) per la gestione dei ristoranti preferiti.
 *
 * Questa classe permette di:
 * - aggiungere un ristorante ai preferiti dell'utente
 * - rimuoverlo
 * - recuperare la lista completa dei preferiti (sia THEKNIFE che UTENTE)
 *
 * La tabella "preferiti" contiene:
 * - id_utente
 * - id_ristorante
 * - fonte (THEKNIFE / UTENTE)
 *
 * In base alla fonte, i dettagli del ristorante vengono caricati
 * dalla tabella corretta.
 */
public class PreferitiDAO {

    // ============================================================
    // AGGIUNGI PREFERITO (con fonte)
    // ============================================================

    /**
     * Aggiunge un ristorante ai preferiti dell'utente.
     *
     * @param idUtente id dell'utente
     * @param idRistorante id del ristorante
     * @param fonte THEKNIFE o UTENTE
     * @return true se l'inserimento è avvenuto con successo
     */
    public static boolean aggiungi(int idUtente, int idRistorante, String fonte) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "INSERT INTO preferiti(id_utente, id_ristorante, fonte) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            ps.setInt(2, idRistorante);
            ps.setString(3, fonte);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // RIMUOVI PREFERITO
    // ============================================================

    /**
     * Rimuove un ristorante dai preferiti dell'utente.
     *
     * @param idUtente id dell'utente
     * @param idRistorante id del ristorante
     * @return true se la rimozione è avvenuta con successo
     */
    public static boolean rimuovi(int idUtente, int idRistorante) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "DELETE FROM preferiti WHERE id_utente = ? AND id_ristorante = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            ps.setInt(2, idRistorante);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // OTTIENI LISTA COMPLETA DEI PREFERITI (THEKNIFE + UTENTE)
    // ============================================================

    /**
     * Restituisce la lista completa dei ristoranti preferiti dell'utente.
     * Per ogni ristorante:
     * - legge la fonte dalla tabella preferiti
     * - carica i dettagli dalla tabella corretta
     * - imposta la fonte nel model
     *
     * @param idUtente id dell'utente
     * @return lista di ristoranti preferiti
     */
    public static List<Ristorante> getRistorantiPreferiti(int idUtente) {
        List<Ristorante> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT id_ristorante, fonte FROM preferiti WHERE id_utente = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idR = rs.getInt("id_ristorante");
                String fonte = rs.getString("fonte");

                Ristorante r;

                if ("THEKNIFE".equalsIgnoreCase(fonte)) {
                    r = getByIdTheKnife(idR);
                } else {
                    r = getByIdUtente(idR);
                }

                if (r != null) {
                    r.setFonte(fonte); // ⭐ IMPOSTIAMO LA FONTE NEL MODEL
                    lista.add(r);
                }
            }

            DBConnectionPool.release(conn);
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
    }

    // ============================================================
    // CARICA RISTORANTE DA THEKNIFE
    // ============================================================

    /**
     * Carica un ristorante dalla tabella THEKNIFE.
     *
     * @param id id del ristorante
     * @return oggetto Ristorante oppure null se non trovato
     */
    private static Ristorante getByIdTheKnife(int id) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                SELECT id, nome, indirizzo, citta, nazione,
                       latitudine, longitudine, fascia_prezzo,
                       tipo_cucina, delivery, prenotazione
                FROM ristorantitheknife
                WHERE id = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ristorante r = new Ristorante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("indirizzo"),
                    rs.getString("tipo_cucina"),
                    rs.getInt("fascia_prezzo"),
                    rs.getDouble("latitudine"),
                    rs.getDouble("longitudine"),
                    rs.getString("citta"),
                    rs.getString("nazione"),
                    rs.getBoolean("delivery"),
                    rs.getBoolean("prenotazione"),
                    "THEKNIFE"
                );

                DBConnectionPool.release(conn);
                return r;
            }

            DBConnectionPool.release(conn);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================================================
    // CARICA RISTORANTE DA RISTORANTIUTENTE
    // ============================================================

    /**
     * Carica un ristorante dalla tabella RISTORANTIUTENTE.
     *
     * @param id id del ristorante
     * @return oggetto Ristorante oppure null se non trovato
     */
    private static Ristorante getByIdUtente(int id) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                SELECT id, nome, indirizzo, citta, nazione,
                       latitudine, longitudine, fascia_prezzo,
                       tipo_cucina, delivery, prenotazione
                FROM ristorantiutente
                WHERE id = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ristorante r = new Ristorante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("indirizzo"),
                    rs.getString("tipo_cucina"),
                    rs.getInt("fascia_prezzo"),
                    rs.getDouble("latitudine"),
                    rs.getDouble("longitudine"),
                    rs.getString("citta"),
                    rs.getString("nazione"),
                    rs.getBoolean("delivery"),
                    rs.getBoolean("prenotazione"),
                    "UTENTE"
                );

                DBConnectionPool.release(conn);
                return r;
            }

            DBConnectionPool.release(conn);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
