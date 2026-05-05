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
import java.io.Serializable;

public class Descrip implements Serializable {

    private int superId;
    private String description;

    public Descrip() {
    }

    public Descrip(int superId, String description) {
        this.superId = superId;
        this.description = description;
    }

    public int getSuperId() {
        return superId;
    }

    public String getDescription() {
        return description;
    }
}
