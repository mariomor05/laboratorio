
package avventur;


import avventur.game.KillZombie;
import java.io.PrintStream;
import static java.lang.System.out;
import avventur.parser.Parser;
import avventur.parser.ParserOutput;
import avventur.type.CommandType;
import java.util.Scanner;
import avventur.type.Zombie;
import gui.LogIn;
import interfaccia.InterfaceLogIn;
import java.io.IOException;
import interfaccia.DBMSInterface;
import DBMSconnection.Connectiondb;
import java.sql.SQLException;
import avventur.type.Pg;
import avventur.type.ObjectGame;
import avventur.type.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Engine {

    private final GameDescription game;

    private final Parser parser;

    private static String name = null;

    private static String pssw = null;

    public Engine(GameDescription game) {
        this.game = game;
        try {

        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = new Parser();

    }

    public void run(String name, String pssw, int health, int position, List<Zombie> zombie, List<ObjectGame> oggetto) throws ClassNotFoundException, Exception {
        this.game.init(health, position, zombie, oggetto);
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");

        AssignDescription o = new AssignDescription();
        System.out.println(o.managerDescription(game.getCurrentRoom().getId()));//stampa descrizione

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory());
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.SAVE) {
                DBMSInterface dbms = new Connectiondb();
                try {
                    dbms.insertGameUtente(name, pssw, game.getPg().getHp(), game.getCurrentRoom().getId());

                } catch (SQLException ex) {
                    Logger.getLogger(KillZombie.class.getName()).log(Level.SEVERE, null, ex);
                }

                dbms.eliminaZombie(name);

                try {
                    for (Room l : game.getRooms()) {
                        if (l.getZombie().isEmpty() == false) {
                            dbms.insertGameZombie(l.getZombie().get(0).getId(), l.getZombie().get(0).getHp(), name);
                        } 
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(KillZombie.class.getName()).log(Level.SEVERE, null, ex);
                }

                dbms.eliminaObject(name);

                try {
                    for (ObjectGame c : game.getInventory()) {

                        dbms.insertGameInventory(c.getId(), name, c.getCapienza());

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(KillZombie.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("salvataggio effettuato");
                break;
            } else if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {

            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        Engine engine = new Engine(new KillZombie());
        boolean checkUserExists = false;//Variabile booleana utile per verificare che l'utente è già presente nel database
        List<Zombie> zombie = new ArrayList<>();
        List<ObjectGame> oggetto = new ArrayList<>();
        int[] arrayDataUser = new int[2];
        InterfaceLogIn login = new LogIn();
        DBMSInterface dbms = new Connectiondb();
        String parola = null;//Memorizza la stringa composta da username e password

        parola = login.Presentazione();
        login.Chiudifinestra();
        estraiParole(parola);

        if (parola != null) {
            dbms.connDB();
            //a.creaDatabase(); Metodo per creare il database
            checkUserExists = dbms.verifyUtente(name, pssw);
            if (checkUserExists == true) {
                arrayDataUser = dbms.caricaGame(name, pssw);
                zombie = dbms.caricaZombie(name);
                oggetto = dbms.caricaInventario(name);
            } else {
                dbms.insertUtenteFT(name, pssw);
            }
        }

        System.out.println(parola);

        engine.run(name, pssw, arrayDataUser[0], arrayDataUser[1], zombie, oggetto);

    }

    public static void estraiParole(String parola) {
        int separatore = 0;
        int fine = 0;

        separatore = parola.indexOf(":");
        fine = parola.length();
        name = parola.substring(0, separatore);
        pssw = parola.substring(separatore + 1, fine);
    }

}
