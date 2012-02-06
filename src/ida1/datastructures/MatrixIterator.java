package ida1.datastructures;

/**
 *
 * @author Cornel Alders
 */
public interface MatrixIterator<E>
{
	public boolean hasNext();

	public E next();

	public boolean hasPrevious();

	public E previous();
}
