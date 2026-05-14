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
    // CERCA RISTORANTI MICHELIN
    // ============================
    public static List<Ristorante> cerca(String query, String tipoCucina) {
        List<Ristorante> lista = new ArrayList<>();

        try (Connection conn = DBConnectionPool.get()) {

            String sql = "SELECT * FROM RistorantiTheKnife WHERE LOWER(nome) LIKE LOWER(?)";

            if (!tipoCucina.equalsIgnoreCase("Tutte")) {
                sql += " AND LOWER(tipo_cucina) LIKE LOWER(?)";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");

            if (!tipoCucina.equalsIgnoreCase("Tutte")) {
                ps.setString(2, "%" + tipoCucina + "%");
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
                        rs.getBoolean("prenotazione")
                ));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================
    // GET BY ID (MICHELIN)
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
                        rs.getBoolean("prenotazione")
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
