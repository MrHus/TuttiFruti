package ida1.algorithms.search;

/**
 *
 * @author Cornel Alders, Maarten Hus
 */
public interface SearchAlgorithm<E extends Comparable<? super E>>
{
    public int search(E[] array, E target);
}
