package server.dao;

import server.model.Utente;
import server.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtenteDAO {

    public static Utente login(String email, String password) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            Utente u = null;
            if (rs.next()) {
                u = new Utente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ruolo")
                );
            }

            DBConnectionPool.release(conn);
            return u;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean registra(String nome, String email, String password, String ruolo) {
        try {
            Connection conn = DBConnectionPool.get();

            String sql = "INSERT INTO utenti(nome, email, password, ruolo) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, ruolo);

            int rows = ps.executeUpdate();

            DBConnectionPool.release(conn);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
