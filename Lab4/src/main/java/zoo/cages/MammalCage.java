package zoo.cages;

import zoo.animaltypes.Mammal;

public abstract class MammalCage<T extends Mammal> extends Cage<T> {
    public MammalCage(int maxCapacity) {
        super(maxCapacity);
    }
}
