/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avventur;

/**
 *
 * @author W-book
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AssignDescription {

    public AssignDescription() {

    }

    public String managerDescription(int a) throws ClassNotFoundException {

        int o = 0;
        int i = 0;
        Descrip p = new Descrip();
        FileInputStream fileInput;
        ObjectInputStream sI;

        try {
            fileInput = new FileInputStream("descrizioneRooms.txt");
            sI = new ObjectInputStream(fileInput);
            while (i != 1) {
                p = (Descrip) sI.readObject();
                if (p.getSuperId() == a) {

                    o = 1;
                    i = 1;
                }
            }
            sI.close();

        } catch (IOException ex) {
        }

        if (o == 1) {
            return p.getDescription();
        } else {
            return null;
        }
    }

}
