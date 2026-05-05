package avventur.type;

public class FlashLight extends ObjectGame {

    private boolean accesa;

    public FlashLight(int id, String name, String description, boolean accesa, String posizione) {
        super(id, name, description, posizione);
        this.accesa = accesa;

    }

    public boolean getAccesa() {
        return accesa;
    }

    public void setAccesa(boolean accesa) {
        this.accesa = accesa;
    }

}
