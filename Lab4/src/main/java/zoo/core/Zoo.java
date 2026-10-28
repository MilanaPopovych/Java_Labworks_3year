package zoo.core;

import zoo.animaltypes.Animal;
import zoo.cages.Cage;

import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    public int getCountOfAnimals() {
        int total = 0;
        for (Cage<? extends Animal> cage : cages) {
            total += cage.getOccupiedCapacity();
        }
        return total;
    }

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
    }
}
