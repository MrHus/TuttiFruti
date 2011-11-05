package ida1.algorithms.sort;

/**
 *
 * @author Cornel Alders
 */
public interface SortAlgorithm<E extends Comparable<? super E>>
{
    public E[] sort(E[] array);
}
