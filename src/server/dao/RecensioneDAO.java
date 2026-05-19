package server.dao;

import server.model.Recensione;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    // ============================
    // VERIFICA SE L’UTENTE HA GIÀ RECENSITO IL RISTORANTE
    // ============================
    public static boolean recensioneEsiste(int idUtente, int idRistorante) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT 1 FROM recensioni WHERE id_utente = ? AND id_ristorante = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            ps.setInt(2, idRistorante);

            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();

            rs.close();
            ps.close();
            DBConnectionPool.release(conn);

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // AGGIUNGI RECENSIONE (CON FONTE)
    // ============================
    public static boolean aggiungi(int idUtente, int idRistorante, int voto, String testo, String fonte) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                INSERT INTO recensioni(id_utente, id_ristorante, voto, testo, data, fonte)
                VALUES (?, ?, ?, ?, NOW(), ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            ps.setInt(2, idRistorante);
            ps.setInt(3, voto);
            ps.setString(4, testo);
            ps.setString(5, fonte); // ⭐ NUOVO

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
    // MODIFICA RECENSIONE
    // ============================
    public static boolean modifica(int idRecensione, int voto, String testo) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "UPDATE recensioni SET voto = ?, testo = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, voto);
            ps.setString(2, testo);
            ps.setInt(3, idRecensione);

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
    // ELIMINA RECENSIONE
    // ============================
    public static boolean elimina(int idRecensione) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "DELETE FROM recensioni WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRecensione);

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
    // RECENSIONI DI UN RISTORANTE
    // ============================
    public static List<Recensione> getByRistorante(int idRistorante) {
        List<Recensione> lista = new ArrayList<>();

        try {
            Connection conn = DBConnectionPool.get();

            String sql = """
                SELECT id, id_utente, id_ristorante, voto, testo, data, fonte
                FROM recensioni
                WHERE id_ristorante = ?
                ORDER BY data DESC
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idRistorante);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Recensione(
                        rs.getInt("id"),
                        rs.getInt("id_utente"),
                        rs.getInt("id_ristorante"),
                        rs.getInt("voto"),
                        rs.getString("testo"),
                        rs.getString("data"),
                        rs.getString("fonte") // ⭐ NUOVO
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
    // RECENSIONI DEI RISTORANTI DI UN GESTORE
    // ============================
    public static List<Recensione> getByGestore(int idGestore) {
        List<Recensione> lista = new ArrayList<>();

        try (Connection conn = DBConnectionPool.get()) {

            String sqlR = "SELECT id FROM ristorantiutente WHERE id_gestore = ?";
            PreparedStatement psR = conn.prepareStatement(sqlR);
            psR.setInt(1, idGestore);

            ResultSet rsR = psR.executeQuery();

            while (rsR.next()) {
                int idRistorante = rsR.getInt("id");

                String sqlRec = """
                    SELECT id, id_utente, id_ristorante, voto, testo, data, fonte
                    FROM recensioni
                    WHERE id_ristorante = ? AND fonte = 'UTENTE'
                    ORDER BY data DESC
                """;

                PreparedStatement psRec = conn.prepareStatement(sqlRec);
                psRec.setInt(1, idRistorante);

                ResultSet rsRec = psRec.executeQuery();

                while (rsRec.next()) {
                    lista.add(new Recensione(
                            rsRec.getInt("id"),
                            rsRec.getInt("id_utente"),
                            rsRec.getInt("id_ristorante"),
                            rsRec.getInt("voto"),
                            rsRec.getString("testo"),
                            rsRec.getString("data"),
                            rsRec.getString("fonte")
                    ));
                }

                rsRec.close();
                psRec.close();
            }

            rsR.close();
            psR.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================
    // RISPONDI A RECENSIONE
    // ============================
    public static boolean rispondi(int idRecensione, String risposta) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "UPDATE recensioni SET risposta = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, risposta);
            ps.setInt(2, idRecensione);

            int rows = ps.executeUpdate();

            ps.close();
            DBConnectionPool.release(conn);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
