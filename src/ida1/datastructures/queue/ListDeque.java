package ida1.datastructures.queue;

import ida1.datastructures.linkedlist.Lijst;

/**
 *
 * @author Cornel Alders
 */
public class ListDeque implements Deque
{
	private Lijst lijst = new Lijst();

	public void addFirst(Object o)
	{
		lijst.addFirst(o);
	}

	public void addLast(Object o)
	{
		lijst.addLast(o);
	}

	public Object getFirst()
	{
		return lijst.getFirst();
	}

	public Object getLast()
	{
		return lijst.getLast();
	}

	public Object removeFirst()
	{
		return lijst.removeFirst();
	}

	public Object removeLast()
	{
		return lijst.removeLast();
	}

	public int size()
	{
		return lijst.size();
	}

	public boolean isEmpty()
	{
		return lijst.isEmpty();
	}
}
