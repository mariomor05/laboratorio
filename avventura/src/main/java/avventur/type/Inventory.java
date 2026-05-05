package avventur.type;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<ObjectGame> list = new ArrayList<>();

    public List<ObjectGame> getList() {
        return list;
    }

    public void setList(List<ObjectGame> list) {
        this.list = list;
    }

    public void add(ObjectGame o) {
        list.add(o);
    }

    public void remove(ObjectGame o) {
        list.remove(o);
    }

    public int getNumberOfObject() {
        return list.size();
    }

}
