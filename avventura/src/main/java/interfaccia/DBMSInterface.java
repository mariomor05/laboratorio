package interfaccia;

import avventur.type.ObjectGame;
import avventur.type.Zombie;
import java.sql.SQLException;
import java.util.List;

public interface DBMSInterface {

    public void connDB();

    public void reconnect() throws SQLException;

    //public void creaDatabase() throws SQLException; Metodo interfaccia 

    public boolean verifyUtente(String user, String password) throws SQLException;

    public void insertGameUtente(String a, String b, int c, int d) throws SQLException;

    public void eliminaZombie(String name) throws SQLException;

    public void eliminaObject(String name) throws SQLException;

    public void insertGameZombie(int idZ, int hpZ, String name) throws SQLException;

    public void insertGameObject(int id, int idRoom, int capacita) throws SQLException;

    public void setUsername(String username);

    public void setPassword(String password);

    public int[] caricaGame(String a, String b) throws SQLException;

    public List<Zombie> caricaZombie(String a) throws SQLException;

    public void insertUtenteFT(String name, String pssw) throws SQLException;

    public void insertGameInventory(int id, String username, int capacita) throws SQLException;

    public List<ObjectGame> caricaInventario(String a) throws SQLException;
}
