package server.dao;

import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PreferitiDAO {

    public static boolean aggiungi(int idUtente, int idRistorante) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "INSERT INTO preferiti(id_utente, id_ristorante) VALUES (?, ?)";
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

    public static List<Integer> getPreferiti(int idUtente) {
        List<Integer> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT id_ristorante FROM preferiti WHERE id_utente = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getInt("id_ristorante"));
            }

            DBConnectionPool.release(conn);
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
    }
}
