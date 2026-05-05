package avventur.type;

public class Zombie extends ObjectGame {

    private boolean isLife;
    private int hp;

    public Zombie(int id, String description, String name, boolean isLife, int hp) {
        super(id, name, description);
        this.hp = hp;
        this.isLife = isLife;
    }

    public Zombie(int id, int hp) {
        super(id);
        this.hp = hp;
    }

    public void isDead() {
        this.setIsLife(false);
    }

    public boolean checkLife() {
        return this.getIsLife();
    }

    public void setDannoZombieHp() {
        int damage = (int) (Math.random() * 51);
        if (damage <= 40 && damage >= 10) {
            damage = 25;
        }

        if (damage < 10) {
            damage = 0;
        }
        if (damage > 51) {
            damage = 50;
        }
        this.setHp(this.getHp() - damage);
    }

    public void setDannoZombieManiHp() {

        this.setHp(this.getHp() - 5);
    }

    public int getHpZombie() {
        return this.getHp();
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

}
