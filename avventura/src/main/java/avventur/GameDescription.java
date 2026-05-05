/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avventur;

import DBMSconnection.Connectiondb;
import avventur.parser.ParserOutput;
import avventur.type.ObjectGame;
import avventur.type.Siringa;
import avventur.type.Command;
import avventur.type.Pg;
import avventur.type.Room;
import avventur.type.Zombie;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class GameDescription {

    private final List<Room> rooms = new ArrayList<>();

    private final List<Command> commands = new ArrayList<>();

    private final List<ObjectGame> inventory = new ArrayList<>();

    private Room currentRoom;

    private Siringa siringaRoom1;

    private Connectiondb currentConnectiondb;

    private final int changeHealth =10;
    
    
    private Pg pgs;

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Pg getPg() {
        return pgs;
    }

    public void setPg(Pg pgs) {
        this.pgs = pgs;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<ObjectGame> getInventory() {
        return inventory;
    }

    public Siringa getSiringRoom() {
        return siringaRoom1;
    }
    public int getChangeHealth(){
       return changeHealth;
    } 

    public Connectiondb getConnectiondb() {
        return currentConnectiondb;
    }

    public abstract void init(int a, int b, List<Zombie> z, List<ObjectGame> g) throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);
  

}
