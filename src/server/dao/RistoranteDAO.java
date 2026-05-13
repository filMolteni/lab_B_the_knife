package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import server.model.Ristorante;
import server.utils.DBConnectionPool;

public class RistoranteDAO {

    public static List<Ristorante> cerca(String query, String categoria) {
        List<Ristorante> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE LOWER(nome) LIKE LOWER(?)";

            // Se la categoria NON è "Tutte", aggiungiamo il filtro
            if (!categoria.equalsIgnoreCase("Tutte")) {
                sql += " AND LOWER(categoria) = LOWER(?)";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");

            if (!categoria.equalsIgnoreCase("Tutte")) {
                ps.setString(2, categoria);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Ristorante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("indirizzo"),
                        rs.getString("categoria"),
                        rs.getString("descrizione")
                ));
            }

            rs.close();
            ps.close();
            DBConnectionPool.release(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static Ristorante getById(int id) {
        Ristorante r = null;

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Ristorante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("indirizzo"),
                        rs.getString("categoria"),
                        rs.getString("descrizione")
                );
            }

            rs.close();
            ps.close();
            DBConnectionPool.release(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }
}
