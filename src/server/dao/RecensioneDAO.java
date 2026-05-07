package server.dao;

import server.model.Recensione;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecensioneDAO {

    public static boolean aggiungi(int idUtente, int idRistorante, int voto, String testo) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "INSERT INTO recensioni(id_utente, id_ristorante, voto, testo) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            ps.setInt(2, idRistorante);
            ps.setInt(3, voto);
            ps.setString(4, testo);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifica(int idRecensione, int voto, String testo) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "UPDATE recensioni SET voto = ?, testo = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, voto);
            ps.setString(2, testo);
            ps.setInt(3, idRecensione);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean elimina(int idRecensione) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "DELETE FROM recensioni WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRecensione);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
