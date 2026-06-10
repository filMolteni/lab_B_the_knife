package server.dao;

import server.model.Utente;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object (DAO) per la gestione degli utenti.
 *
 * Questa classe fornisce metodi per:
 * - autenticare un utente (login)
 * - registrare un nuovo utente
 * - recuperare un utente tramite email
 *
 * Utilizza connessioni fornite dal {@link DBConnectionPool}.
 */
public class UtenteDAO {

    // ============================
    // LOGIN
    // ============================

    /**
     * Effettua il login verificando email e password nella tabella "utenti".
     *
     * @param email email dell'utente
     * @param password password dell'utente
     * @return oggetto {@link Utente} se le credenziali sono corrette, altrimenti null
     */
    public static Utente login(String email, String password) {
        Utente u = null;

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Utente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ruolo")
                );
            }

            rs.close();
            ps.close();
            DBConnectionPool.release(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    // ============================
    // REGISTRAZIONE
    // ============================

    /**
     * Registra un nuovo utente nella tabella "utenti".
     *
     * @param username nome dell'utente (salvato nel campo "nome")
     * @param email email dell'utente
     * @param password password dell'utente
     * @param ruolo ruolo dell'utente (CLIENTE, GESTORE, ADMIN)
     * @return true se l'inserimento è avvenuto con successo
     */
    public static boolean registrati(String username, String email, String password, String ruolo) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "INSERT INTO utenti(nome, email, password, ruolo) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);   // username → nome
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, ruolo);

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
    // GET BY EMAIL
    // ============================

    /**
     * Recupera un utente tramite email.
     *
     * @param email email da cercare
     * @return oggetto {@link Utente} se trovato, altrimenti null
     */
    public static Utente getByEmail(String email) {
        Utente u = null;

        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM utenti WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Utente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ruolo")
                );
            }

            rs.close();
            ps.close();
            DBConnectionPool.release(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }
}
