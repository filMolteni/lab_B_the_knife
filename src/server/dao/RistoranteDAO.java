package server.dao;

import server.model.Ristorante;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

    public class RistoranteDAO {

        // ============================
        // CERCA RISTORANTI THEKNIFE
        // ============================
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
                "SELECT r.*, COALESCE(AVG(rec.voto), 0) AS stelle " +
                "FROM RistorantiTheKnife r " +
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
            // FILTRO LOCALITÀ (città o nazione)
            // ============================
            if (!localita.equalsIgnoreCase("Tutte")) {
                sql.append(" AND (LOWER(r.citta) = LOWER(?) OR LOWER(r.nazione) = LOWER(?)) ");
                params.add(localita.toLowerCase());
                params.add(localita.toLowerCase());
            }

            // ============================
            // FILTRO PREZZO (1–5)
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
            // FILTRO STELLE (HAVING)
            // ============================
            if (stelleMin > 0) {
                sql.append(" HAVING stelle >= ? ");
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
                        "THEKNIFE"
                ));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }




    public static boolean esisteUtente(int idRistorante) {
    try (Connection conn = DBConnectionPool.get()) {
        String sql = "SELECT 1 FROM ristorantiutente WHERE id = ? LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idRistorante);

        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


    // ============================
    // GET BY ID (THEKNIFE)
    // ============================
    public static Ristorante getById(int id) {
        Ristorante r = null;

        try (Connection conn = DBConnectionPool.get()) {

            String sql = "SELECT * FROM RistorantiTheKnife WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Ristorante(
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
                        "THEKNIFE"   // ⭐ FONTE AGGIUNTA
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }
}
