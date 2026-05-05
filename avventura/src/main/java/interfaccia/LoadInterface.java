
package interfaccia;

import avventur.type.Room;
import avventur.type.Zombie;
import java.util.List;


public interface LoadInterface {
    public Zombie loadZombie(List<Zombie> z, Zombie zombie); 
    public Room loadPosition(int posizione, List<Room> l);
}
