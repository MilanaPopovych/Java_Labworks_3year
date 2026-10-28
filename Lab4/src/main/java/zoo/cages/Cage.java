package zoo.cages;

import zoo.animaltypes.Animal;
import zoo.exceptions.*;

import java.util.ArrayList;
import java.util.List;

// Базовий клас Вольєр (Generic з обмеженням типу)
public abstract class Cage<T extends Animal> {
    private final int maxCapacity;
    private final List<T> animals;

    public Cage(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.animals = new ArrayList<>();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getOccupiedCapacity() {
        return animals.size();
    }

    public void addAnimal(T animal) throws CageFullException {
        if (animals.size() >= maxCapacity) {
            throw new CageFullException("Вольєр заповнений. Неможливо додати: " + animal.getName());
        }
        animals.add(animal);
    }

    public void removeAnimal(T animal) throws AnimalNotFoundException {
        if (!animals.remove(animal)) {
            throw new AnimalNotFoundException("Тварину " + animal.getName() + " не знайдено у вольєрі.");
        }
    }
}
