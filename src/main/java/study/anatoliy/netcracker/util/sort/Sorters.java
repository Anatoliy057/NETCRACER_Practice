package study.anatoliy.netcracker.util.sort;

import java.util.EnumMap;
import java.util.Objects;

/**
 * A class intended for caching objects that implements ISorter
 *
 * @see ISorter
 *
 * @author Udarczev Anatoliy
 */
public class Sorters {

    /** Cache of objects that implements ISorter */
    private final static EnumMap<TypeSorter, ISorter> cache = new EnumMap<>(TypeSorter.class);

    // Init cache
    static {
        ISorter s;

        s = new BubbleSorter();
        cache.put(s.getType(), s);
        s = new QuickSorter();
        cache.put(s.getType(), s);

        assert cache.size() == TypeSorter.values().length;
    }

    private Sorters() {}

    /**
     * @param t sorting type
     * @return sorter of the specified type
     *
     * @throws NullPointerException if type is null
     */
    public static ISorter get(TypeSorter t) {
        Objects.requireNonNull(t);
        return cache.get(t);
    }
}
