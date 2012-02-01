package ida1.datastructures.queue;

import ida1.datastructures.linkedlist.Lijst;

/**
 *
 * @author Maarten Hus
 */
public class ListQueue implements Queue
{
	private Lijst lijst = new Lijst();

	public Object dequeue()
	{
		return lijst.removeFirst();
	}

	public void enqueue(Object o)
	{
		lijst.addLast(o);
	}

	public Object front()
	{
		return lijst.getClass();
	}

	public boolean isEmpty()
	{
		return lijst.isEmpty();
	}

	public int size()
	{
		return lijst.size();
	}
}
