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
    public static String getRispostaByRecensione(int idRecensione) {

    String sql = "SELECT testo FROM risposte WHERE id_recensione = ? LIMIT 1";

    try (Connection conn = DBConnectionPool.get();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idRecensione);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("testo");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null; // Nessuna risposta trovata
}


    // ============================
    // AGGIUNGI RECENSIONE
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
            ps.setString(5, fonte);

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
    // RECENSIONI SCRITTE DA UN UTENTE
    // ============================
    public static List<Recensione> getByUtente(int idUtente) {
        List<Recensione> lista = new ArrayList<>();

        try (Connection conn = DBConnectionPool.get()) {

            String sql = """
                SELECT r.id, r.id_utente, r.id_ristorante, r.voto, r.testo, r.data, r.fonte,
                COALESCE(ru.nome, rt.nome) AS nome_ristorante
                FROM recensioni r
                LEFT JOIN ristorantiutente ru ON r.id_ristorante = ru.id AND r.fonte = 'UTENTE'
                LEFT JOIN ristorantitheknife rt ON r.id_ristorante = rt.id AND r.fonte = 'THEKNIFE'
                WHERE r.id_utente = ?
                ORDER BY r.data DESC

            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUtente);
            
            System.out.println("Eseguo query getByUtente con idUtente = " + idUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Recensione(
                        rs.getInt("id"),
                        rs.getInt("id_utente"),
                        rs.getInt("id_ristorante"),
                        rs.getInt("voto"),
                        rs.getString("testo"),
                        rs.getString("data"),
                        rs.getString("fonte"),
                        rs.getString("nome_ristorante")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================
    // RECENSIONI DI UN RISTORANTE
    // ============================
    public static List<Recensione> getByRistorante(int idRistorante) {
        List<Recensione> lista = new ArrayList<>();

        try (Connection conn = DBConnectionPool.get()) {

            String sql = """
                SELECT r.id, r.id_utente, r.id_ristorante, r.voto, r.testo, r.data, r.fonte,
                       COALESCE(ru.nome, rt.nome) AS nome_ristorante
                FROM recensioni r
                LEFT JOIN ristorantiutente ru ON r.id_ristorante = ru.id AND r.fonte = 'UTENTE'
                LEFT JOIN ristorantitheknife rt ON r.id_ristorante = rt.id AND r.fonte = 'THEKNIFE'
                WHERE r.id_ristorante = ?
                ORDER BY r.data DESC
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
                        rs.getString("fonte"),
                        rs.getString("nome_ristorante")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<Recensione> getNonAnonimeByRistorante(int idRistorante) {
    List<Recensione> lista = new ArrayList<>();

    try (Connection conn = DBConnectionPool.get()) {

        String sql = """
            SELECT r.id, r.id_utente, r.id_ristorante, r.voto, r.testo, r.data, r.fonte,
                   COALESCE(ru.nome, rt.nome) AS nome_ristorante,
                   u.nome AS nome_utente
            FROM recensioni r
            JOIN utenti u ON r.id_utente = u.id
            LEFT JOIN ristorantiutente ru 
                   ON r.id_ristorante = ru.id AND r.fonte = 'UTENTE'
            LEFT JOIN ristorantitheknife rt 
                   ON r.id_ristorante = rt.id AND r.fonte = 'THEKNIFE'
            WHERE r.id_ristorante = ?
            ORDER BY r.data DESC
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idRistorante);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Recensione rec = new Recensione(
                    rs.getInt("id"),
                    rs.getInt("id_utente"),
                    rs.getInt("id_ristorante"),
                    rs.getInt("voto"),
                    rs.getString("testo"),
                    rs.getString("data"),
                    rs.getString("fonte"),
                    rs.getString("nome_ristorante")
            );

            // ⭐ aggiungiamo il nome utente
            rec.setNomeUtente(rs.getString("nome_utente"));

            lista.add(rec);
        }

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

            String sql = """
                SELECT r.id, r.id_utente, r.id_ristorante, r.voto, r.testo, r.data, r.fonte,
                       ru.nome AS nome_ristorante
                FROM recensioni r
                JOIN ristorantiutente ru ON r.id_ristorante = ru.id
                WHERE ru.id_gestore = ? AND r.fonte = 'UTENTE'
                ORDER BY r.data DESC
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idGestore);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Recensione(
                        rs.getInt("id"),
                        rs.getInt("id_utente"),
                        rs.getInt("id_ristorante"),
                        rs.getInt("voto"),
                        rs.getString("testo"),
                        rs.getString("data"),
                        rs.getString("fonte"),
                        rs.getString("nome_ristorante")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static boolean rispondi(int idRecensione, String testo) {

        String sql = "INSERT INTO risposte (id_recensione, testo) VALUES (?, ?)";

        try (Connection conn = DBConnectionPool.get();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRecensione);
            ps.setString(2, testo);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
