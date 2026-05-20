package server.dao;

import server.model.Ristorante;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RistoranteUtenteDAO {

    // ============================================================
    // AGGIUNGI RISTORANTE UTENTE
    // ============================================================
    public static boolean aggiungi(int idGestore, String nome, String indirizzo,
                                   String tipoCucina, int fasciaPrezzo,
                                   String citta, String nazione,
                                   double lat, double lon,
                                   boolean delivery, boolean prenotazione) {

        String sql = """
            INSERT INTO RistorantiUtente
            (id_gestore, nome, indirizzo, tipo_cucina, fascia_prezzo,
             citta, nazione, latitudine, longitudine, delivery, prenotazione)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionPool.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGestore);
            ps.setString(2, nome);
            ps.setString(3, indirizzo);
            ps.setString(4, tipoCucina);
            ps.setInt(5, fasciaPrezzo);
            ps.setString(6, citta);
            ps.setString(7, nazione);
            ps.setDouble(8, lat);
            ps.setDouble(9, lon);
            ps.setBoolean(10, delivery);
            ps.setBoolean(11, prenotazione);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // MODIFICA RISTORANTE UTENTE
    // ============================================================
   public static boolean modifica(int id, String nome, String indirizzo,
                               String tipoCucina, int fasciaPrezzo,
                               String citta, String nazione,
                               double lat, double lon,
                               boolean delivery, boolean prenotazione) {

    String sql = """
        UPDATE RistorantiUtente
        SET nome = ?, indirizzo = ?, tipo_cucina = ?, fascia_prezzo = ?,
            citta = ?, nazione = ?, latitudine = ?, longitudine = ?,
            delivery = ?, prenotazione = ?
        WHERE id = ?
    """;

    try (Connection conn = DBConnectionPool.get();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, nome);
        ps.setString(2, indirizzo);
        ps.setString(3, tipoCucina);
        ps.setInt(4, fasciaPrezzo);
        ps.setString(5, citta);
        ps.setString(6, nazione);
        ps.setDouble(7, lat);
        ps.setDouble(8, lon);
        ps.setBoolean(9, delivery);
        ps.setBoolean(10, prenotazione);
        ps.setInt(11, id);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


    // ============================================================
    // ELIMINA RISTORANTE UTENTE
    // ============================================================
    public static boolean elimina(int id) {

        String sql = "DELETE FROM RistorantiUtente WHERE id = ?";

        try (Connection conn = DBConnectionPool.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // GET RISTORANTI DI UN GESTORE
    // ============================================================
    public static List<Ristorante> getByGestore(int idGestore) {

        List<Ristorante> lista = new ArrayList<>();

        String sql = "SELECT * FROM RistorantiUtente WHERE id_gestore = ?";

        try (Connection conn = DBConnectionPool.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGestore);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Ristorante(
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
                        "UTENTE"   // ⭐ FONTE
                ));
            }

            rs.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    public static Ristorante getById(int id) {

        String sql = "SELECT * FROM RistorantiUtente WHERE id = ?";

        try (Connection conn = DBConnectionPool.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Ristorante(
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
            }

            rs.close();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
