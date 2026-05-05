package avventur.type;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int id;

    private String name;

    private String description;

    private String look;

    private String indovinello;

    private String risposta;

    private Room south = null;

    private Room north = null;

    private Room east = null;

    private Room west = null;

    private Room nextStep = null;

    private boolean aperta = false;

    private int idporta;

    private String LastCommand = null;

    private boolean visible;

    private boolean portUsb;

    private boolean checkZombie;

    private final List<ObjectGame> objects = new ArrayList<>();

    private final List<Zombie> arrayZombie = new ArrayList<>();

    public boolean GetSiringa() {
        class Siringa extends ObjectGame {

            private boolean full;

            public Siringa(int id, String name, String description, String combinedDescription, boolean full, boolean combined, String posizione) {
                super(id, name, description, posizione);
                this.full = full;
            }

        }
        Siringa siringa1 = new Siringa(5, "siringa", "è vuota ti consiglio di riempirla con del sangue di zombie", "E' piena di liquido nero...", false, false, "room1");
        if (siringa1.getFull()) {

            return true;

        } else {
            siringa1.isEmpty();

            return false;
        }

    }

    public void setLastCommand(String s) {
        this.LastCommand = s;
    }

    public String getLastCommad() {
        return LastCommand;
    }

    public void setIsAperta(boolean aperta) {
        this.aperta = aperta;
    }

    public boolean getIsAperta() {
        return aperta;
    }

    public void setIdPorta(int porta) {
        this.idporta = idporta;
    }

    public boolean getCheckZombie() {
        return checkZombie;
    }

    public void setCheckZombie(boolean checkZombie) {
        this.checkZombie = checkZombie;
    }

    public int getIdPorta() {
        return idporta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsbPort(boolean usbPort) {
        this.portUsb = usbPort;
    }

    public boolean getUsbPort() {
        return portUsb;
    }

    public Room(int id) {
        this.id = id;
    }

    public Room(int id, String name, int idporta, boolean visible, boolean checkZombie) {
        this.id = id;
        this.name = name;
        //this.description = description;
        this.idporta = idporta;
        this.visible = visible;
        this.checkZombie = checkZombie;
    }

    public Room(int id, String name, String description, int idporta, boolean visible, boolean portUsb, boolean checkZombie) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idporta = idporta;
        this.visible = visible;
        this.portUsb = portUsb;
        this.checkZombie = checkZombie;
    }

    public Room(int id, String name, String description, int idporta, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idporta = idporta;
        this.visible = visible;
    }

    public Room(int id, String name, int idporta, String indovinello, String risposta, boolean visible, boolean checkZombie) {
        this.id = id;
        this.name = name;
        //this.description = description;
        this.idporta = idporta;
        this.indovinello = indovinello;
        this.risposta = risposta;
        this.visible = visible;
        this.checkZombie = checkZombie;
    }

    public int checkIndovinello(String risp) {

        return risposta.compareTo(risp);

    }

    public String getIndovinello() {
        return indovinello;
    }

    public String getRisposta() {
        return risposta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getNorth() {
        return north;
    }

    public Room getNextStep() {
        return nextStep;
    }

    public void setNextStep(Room nextStep) {
        this.nextStep = nextStep;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public List<ObjectGame> getObjects() {
        return objects;
    }

    public List<Zombie> getZombie() {
        return arrayZombie;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible() {
        this.visible = true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;

    }

}
