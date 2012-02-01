package ida1.algorithms.sort;

/**
 *
 * @author Maarten Hus
 */
public interface SortAlgorithm<E extends Comparable<? super E>>
{
    public E[] sort(E[] array);
}
