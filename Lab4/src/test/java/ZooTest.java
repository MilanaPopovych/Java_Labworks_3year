import org.junit.jupiter.api.Test;
import zoo.animals.*;
import zoo.cages.*;
import zoo.core.Zoo;
import zoo.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

public class ZooTest {
    @Test
    public void testAddAndRemoveAnimal() throws Exception {
        LionCage lionCage = new LionCage(2);
        Lion simba = new Lion("Сімба");

        lionCage.addAnimal(simba);
        assertEquals(1, lionCage.getOccupiedCapacity());

        lionCage.removeAnimal(simba);
        assertEquals(0, lionCage.getOccupiedCapacity());
    }

    @Test
    public void testCageFullException() throws Exception {
        LionCage lionCage = new LionCage(1);
        lionCage.addAnimal(new Lion("Сімба"));

        // перевіряємо, чи генерується виключення при переповненні
        assertThrows(CageFullException.class, () -> {
            lionCage.addAnimal(new Lion("Муфаса"));
        });
    }

    @Test
    public void testAnimalNotFoundException() {
        LionCage lionCage = new LionCage(2);
        Lion simba = new Lion("Сімба");

        // перевіряємо, чи генерується виключення при видаленні відсутньої тварини
        assertThrows(AnimalNotFoundException.class, () -> {
            lionCage.removeAnimal(simba);
        });
    }

    @Test
    public void testUngulateCageAcceptsZebrasAndGiraffes() throws Exception {
        UngulateCage ungulateCage = new UngulateCage(5);
        Zebra zebra = new Zebra("Марті");
        Giraffe giraffe = new Giraffe("Мелман");

        ungulateCage.addAnimal(zebra);
        ungulateCage.addAnimal(giraffe);

        assertEquals(2, ungulateCage.getOccupiedCapacity());


//        Помилка компіляції для перевірки роботи генериків
//        ungulateCage.addAnimal(new Lion("Алекс"));

    }

    @Test
    public void testZooTotalAnimalsCount() throws Exception {
        Zoo zoo = new Zoo();

        LionCage lionCage = new LionCage(3);
        lionCage.addAnimal(new Lion("Лев 1"));
        lionCage.addAnimal(new Lion("Лев 2"));

        UngulateCage ungulateCage = new UngulateCage(4);
        ungulateCage.addAnimal(new Zebra("Зебра 1"));
        ungulateCage.addAnimal(new Giraffe("Жираф 1"));

        BirdCage birdCage = new BirdCage(5);
        birdCage.addAnimal(new Eagle("Орел 1"));

        zoo.addCage(lionCage);
        zoo.addCage(ungulateCage);
        zoo.addCage(birdCage);
        // Перевірка загальної кількості тварин
        assertEquals(5, zoo.getCountOfAnimals());
    }
}
