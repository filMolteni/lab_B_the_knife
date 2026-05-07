package server.dao;

import server.model.Ristorante;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDAO {

    public static List<Ristorante> cerca(String query) {
        List<Ristorante> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE LOWER(nome) LIKE LOWER(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");

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

            DBConnectionPool.release(conn);
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
    }

    public static Ristorante getById(int id) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Ristorante r = null;
            if (rs.next()) {
                r = new Ristorante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("indirizzo"),
                        rs.getString("categoria"),
                        rs.getString("descrizione")
                );
            }

            DBConnectionPool.release(conn);
            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
