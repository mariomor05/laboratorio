/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avventur.load;

import avventur.type.ObjectGame;
import avventur.type.Room;
import avventur.type.Zombie;
import interfaccia.LoadInterface;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author miche
 */
public class LoadGame implements LoadInterface{

    public LoadGame() {

    }

    public Zombie loadZombie(List<Zombie> z, Zombie zombie) {
        if (z.isEmpty() == false) {

            for (Zombie d : z) {
                if (d.getId() == zombie.getId()) {
                    zombie.setHp(d.getHp());
                    if (zombie.getHp() <= 0) {
                        zombie.setIsLife(false);
                    }
                }

            }
            return zombie;
        } else {
            return zombie;
        }

    }

    public Room loadPosition(int posizione, List<Room> l) {
        Room roomAppoggio = new Room(100);
        int ceck = 0;
        for (Room r : l) {
            if (r.getId() == posizione) {
                roomAppoggio = r;
                ceck = 1;
            }
        }
        if (ceck == 1) {
            return roomAppoggio;
        } else {
            for (Room r : l) {
                if (r.getId() == 5) {
                    roomAppoggio = r;

                }
            }
            return roomAppoggio;
        }
    }
}
