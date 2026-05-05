package avventur.type;

public class Pg {

    private boolean isLife;
    private int hp;
    private int id;
    private String name;

    public Pg(int id, String name, int hp, boolean isLife) {
        this.id = id;
        this.hp = hp;
        this.name = name;
        this.isLife = isLife;
    }

    public void setIsLife(boolean isLife) {
        this.isLife = isLife;
    }

    public boolean getIsLife() {
        return isLife;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean ceckIsLife(int lifePoint) {
        if (lifePoint < 0) {
            return false;
        } else {
            return true;
        }
    }

}
