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
    // CERCA RISTORANTI TABELLA UTENTE
    // ============================================================
    public static List<Ristorante> cerca(
            String query,
            String tipoCucina,
            String localita,
            int prezzoMin,
            int prezzoMax,
            boolean delivery,
            boolean prenotazione,
            int stelleMin
    ) {
        List<Ristorante> lista = new ArrayList<>();

        try (Connection conn = DBConnectionPool.get()) {

            StringBuilder sql = new StringBuilder(
                "SELECT r.* " +
                "FROM RistorantiUtente r " +
                "LEFT JOIN Recensioni rec ON rec.id_ristorante = r.id " +
                "WHERE LOWER(r.nome) LIKE LOWER(?) "
            );

            List<Object> params = new ArrayList<>();
            params.add("%" + query + "%");

            // ============================
            // FILTRO TIPO CUCINA
            // ============================
            if (!tipoCucina.equalsIgnoreCase("Tutte")) {
                sql.append(" AND LOWER(r.tipo_cucina) LIKE LOWER(?) ");
                params.add("%" + tipoCucina + "%");
            }

            // ============================
            // FILTRO LOCALITÀ
            // ============================
            if (!localita.equalsIgnoreCase("Tutte")) {
                sql.append(" AND (LOWER(r.citta) = LOWER(?) OR LOWER(r.nazione) = LOWER(?)) ");
                params.add(localita.toLowerCase());
                params.add(localita.toLowerCase());
            }

            // ============================
            // FILTRO PREZZO
            // ============================
            sql.append(" AND r.fascia_prezzo BETWEEN ? AND ? ");
            params.add(prezzoMin);
            params.add(prezzoMax);

            // ============================
            // FILTRO DELIVERY
            // ============================
            if (delivery) {
                sql.append(" AND r.delivery = TRUE ");
            }

            // ============================
            // FILTRO PRENOTAZIONE
            // ============================
            if (prenotazione) {
                sql.append(" AND r.prenotazione = TRUE ");
            }

            // ============================
            // GROUP BY PER MEDIA STELLE
            // ============================
            sql.append(" GROUP BY r.id ");

            // ============================
            // FILTRO STELLE (SOLO PER FILTRARE)
            // ============================
            if (stelleMin > 0) {
                sql.append(" HAVING COALESCE(AVG(rec.voto), 0) >= ? ");
                params.add(stelleMin);
            }

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            // Bind parametri
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

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
                        "UTENTE"
                ));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    return lista;
}

        public static boolean isOwnedBy(int idRistorante, int idGestore) {

            String sql = "SELECT COUNT(*) FROM RistorantiUtente WHERE id = ? AND id_Gestore = ?";

            try (Connection conn = DBConnectionPool.get();
                PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idRistorante);
                ps.setInt(2, idGestore);

                

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);

                    return count > 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }



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
