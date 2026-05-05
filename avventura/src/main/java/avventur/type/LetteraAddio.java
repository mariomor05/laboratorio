package avventur.type;

import java.util.Set;

public class LetteraAddio extends ObjectGame {

    private String mittente;

    public LetteraAddio(int id, String name, String description, String mittente, String posizione) {
        super(id, name, description, posizione);
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
}
