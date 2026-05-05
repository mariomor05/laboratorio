
package DBMSconnection;

import avventur.type.Zombie;
import interfaccia.DBMSInterface;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import avventur.type.ObjectGame;
import avventur.type.ObjectGame;

/**
 *
 * @author W-book
 */
public class Connectiondb implements DBMSInterface {

    private Connection conn;
    private Properties dbprops;
    private String us;
    private String password;

    public Connectiondb(String us, String password) {
        this.us = us;
        this.password = password;
    }

    public Connectiondb() {

    }

    public String getUsername() {
        return us;
    }

    public void setUsername(String username) {
        this.us = username;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void reconnect() throws SQLException {

        conn = (Connection) DriverManager.getConnection("jdbc:h2:./database/game");

    }

    public void connDB() {
        try {
            Properties dbprops = new Properties();

            java.sql.Connection conn = DriverManager.getConnection("jdbc:h2:./database/game");

        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    /*public void creaDatabase() throws SQLException {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS utente (username VARCHAR(1024) PRIMARY KEY, password VARCHAR(1024), stanza_corrente INT,hp INT)";
        String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS room (Id INT PRIMARY KEY)";
        String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS zombie (Id INT , hp INT,username VARCHAR(1024),PRIMARY KEY(Id,username))";
        String CREATE_TABLE3 = "CREATE TABLE IF NOT EXISTS oggetto (Id INT , proprietario VARCHAR(1024),id_posizione INT,capacita int,PRIMARY KEY(Id,proprietario))";

        try {
            Properties dbprops = new Properties();
            java.sql.Connection conn = DriverManager.getConnection("jdbc:h2:./database/game");
            Statement stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE);
            stm.close();
            stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE2);
            stm.close();
            stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE1);
            stm.close();
            stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE3);
            stm.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }

    }*/ //Creazione database locale

    public boolean verifyUtente(String user, String password) throws SQLException {
        boolean check = false;

        try {

            reconnect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM utente ");
            while (rs.next()) {
                if (user.compareTo(rs.getString(1)) == 0) {
                    check = true;
                }
            }
            rs.close();
            stm.close();
            if (check == true) {
                System.out.println("Bentornato vedi di finire il gioco questa volta");
            }
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
        return check;
    }

    public void insertUtenteFT(String name, String pssw) throws SQLException {
        reconnect();

        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO utente VALUES (?,?,?,?)");
            pstm.setString(1, name);
            pstm.setString(2, pssw);
            pstm.setInt(3, 0);
            pstm.setInt(4, 100);
            pstm.executeUpdate();
            pstm.close();
            System.out.println("Utente memorizzato per la prima volta");
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void insertGameUtente(String a, String b, int c, int d) throws SQLException {
        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("UPDATE utente SET stanza_corrente=?,hp=? WHERE username=?");
            pstm.setInt(1, c);
            pstm.setInt(2, d);
            pstm.setString(3, a);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void insertGameZombie(int idZ, int hpZ, String name) throws SQLException {

        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO zombie VALUES (?,?,?)");
            pstm.setInt(1, idZ);
            pstm.setInt(2, hpZ);
            pstm.setString(3, name);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void eliminaZombie(String name) throws SQLException {
        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM zombie WHERE username=?");
            pstm.setString(1, name);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void eliminaObject(String name) throws SQLException {
        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM oggetto WHERE proprietario=?");
            pstm.setString(1, name);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void insertGameObject(int id, int idRoom, int capacita) throws SQLException {
        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO oggetto VALUES (?,?,?,?)");
            pstm.setInt(1, id);
            pstm.setString(2, null);
            pstm.setInt(3, idRoom);
            pstm.setInt(4, capacita);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void insertGameInventory(int id, String username, int capacita) throws SQLException {
        reconnect();
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO oggetto VALUES (?,?,?,?)");
            pstm.setInt(1, id);
            pstm.setString(2, username);
            pstm.setInt(3, 0);
            pstm.setInt(4, capacita);
            pstm.executeUpdate();
            pstm.close();
            DatabaseMetaData metaData = conn.getMetaData();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }

    }

    public int[] caricaGame(String a, String b) throws SQLException {
        int[] arrayVal = new int[2];
        reconnect();
        try {
            ResultSet rs;
            PreparedStatement pstm = conn.prepareStatement("SELECT stanza_corrente,hp FROM utente WHERE username=? AND password=?");
            pstm.setString(1, a);
            pstm.setString(2, b);
            rs = pstm.executeQuery();
            while (rs.next()) {
                arrayVal[0] = rs.getInt(1);
                arrayVal[1] = rs.getInt(2);
            }
            rs.close();
            pstm.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
        return arrayVal;
    }

    public List<Zombie> caricaZombie(String a) throws SQLException {
        reconnect();
        List<Zombie> z = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement pstm = conn.prepareStatement("SELECT id,hp FROM zombie WHERE username=?");
            pstm.setString(1, a);

            rs = pstm.executeQuery();
            while (rs.next()) {
                Zombie s = new Zombie(rs.getInt(1), rs.getInt(2));
                z.add(s);
            }
            rs.close();
            pstm.close();

        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
        return z;
    }

    public List<ObjectGame> caricaInventario(String a) throws SQLException {
        reconnect();
        List<ObjectGame> g = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement pstm = conn.prepareStatement("SELECT id,capacita FROM oggetto WHERE proprietario=? AND id_posizione=0");
            pstm.setString(1, a);

            rs = pstm.executeQuery();
            while (rs.next()) {
                ObjectGame s = new ObjectGame(rs.getInt(1), rs.getInt(2));
                g.add(s);
            }
            rs.close();
            pstm.close();

        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
        return g;
    }

}
