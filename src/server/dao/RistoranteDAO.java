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
    // CERCA RISTORANTI
    // ============================
    public static List<Ristorante> cerca(String query, String categoria) {
        List<Ristorante> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE LOWER(nome) LIKE LOWER(?)";

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

    // ============================
    // GET BY ID
    // ============================
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

    // ============================
    // AGGIUNGI RISTORANTE
    // ============================
    public static boolean aggiungi(String nome, String indirizzo, String categoria,
                                   String descrizione, int idGestore) {

        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                INSERT INTO ristoranti (nome, indirizzo, categoria, descrizione, id_gestore)
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, indirizzo);
            ps.setString(3, categoria);
            ps.setString(4, descrizione);
            ps.setInt(5, idGestore);

            int rows = ps.executeUpdate();

            ps.close();
            DBConnectionPool.release(conn);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // MODIFICA RISTORANTE
    // ============================
    public static boolean modifica(int id, String nome, String indirizzo,
                                   String categoria, String descrizione) {

        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                UPDATE ristoranti
                SET nome = ?, indirizzo = ?, categoria = ?, descrizione = ?
                WHERE id = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, indirizzo);
            ps.setString(3, categoria);
            ps.setString(4, descrizione);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            ps.close();
            DBConnectionPool.release(conn);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // ELIMINA RISTORANTE
    // ============================
    public static boolean elimina(int id) {

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "DELETE FROM ristoranti WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            ps.close();
            DBConnectionPool.release(conn);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // RISTORANTI DI UN GESTORE
    // ============================
    public static List<Ristorante> getByGestore(int idGestore) {
        List<Ristorante> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM ristoranti WHERE id_gestore = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idGestore);

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
}
