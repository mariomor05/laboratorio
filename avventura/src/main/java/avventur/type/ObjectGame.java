package avventur.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ObjectGame {

    private final int id;

    private String name;

    private String description;

    private Set<String> alias;

    private boolean openable = false;

    private boolean pickupable = false;

    private boolean pushable = false;

    private boolean open = false;

    private boolean push = false;

    private boolean combined = false;

    private String combinedDescription;

    private String posizione;

    private boolean check = false;

    private int capienza;

    private boolean full;

    public ObjectGame(int id) {
        this.id = id;
    }

    public ObjectGame(int id, int capienza) {
        this.id = id;
        this.capienza = capienza;
    }

    public ObjectGame(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectGame(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ObjectGame(int id, String name, String description, String posizione) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posizione = posizione;
    }

    public ObjectGame(int id, String name, String description, int capienza, String posizione) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posizione = posizione;
        this.capienza = capienza;
    }

    public ObjectGame(int id, String name, String description, String posizione, boolean check) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posizione = posizione;
        this.check = check;
    }

    public ObjectGame(int id, String name, String description, String posizione, int capienza) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posizione = posizione;
        this.capienza = capienza;
    }

    public ObjectGame(int id, String name, String description, String combinedDescription, String posizione) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.combinedDescription = combinedDescription;
        this.posizione = posizione;
    }

    public ObjectGame(int id, String name, String description, Set<String> alias, String combinedDescription, String posizione) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.combinedDescription = combinedDescription;
        this.posizione = posizione;

    }

    public boolean isEmpty() {
        full = true;
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean getFull() {
        return full;
    }

    public boolean getCheckUsb() {
        return check;
    }

    public void setCheckUsb(boolean check) {
        this.check = check;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int a) {
        this.capienza = a;
    }

    public String getPosizione() {
        return posizione;
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

    public void setCombinedDescription(String combinedDescription) {
        this.combinedDescription = combinedDescription;
    }

    public String getCombinedDescription() {
        return combinedDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpenable() {
        return openable;
    }

    public void setOpenable(boolean openable) {
        this.openable = openable;
    }

    public boolean isPickupable() {
        return pickupable;
    }

    public void setPickupable(boolean pickupable) {
        this.pickupable = pickupable;
    }

    public boolean isPushable() {
        return pushable;
    }

    public void setPushable(boolean pushable) {
        this.pushable = pushable;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    public int getId() {
        return id;
    }

    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    public boolean equals(ObjectGame obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectGame other = (ObjectGame) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
