package ida1.datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * HashSet is a set that uses HashMap to store the data.
 *
 * @author maartenhus
 */
public class HashSet<E> implements Set
{
	private final Object PRESENT = new Object();
	private HashMap<E, Object> hashMap = new HashMap<E, Object>();

	public boolean add(Object e)
	{
		if (!hashMap.containsKey((E)e))
		{
			return hashMap.put((E)e, PRESENT) == null;
		}

		return false;
	}

	public boolean addAll(Collection c)
	{
		boolean allSuccess = true;

		for (Object e : c)
		{
			boolean success = add(e);

			if (!success)
			{
				allSuccess = false;
			}
		}

		return allSuccess;
	}

	public void clear()
	{
		hashMap.clear();
	}

	public boolean contains(Object o)
	{
		return hashMap.containsKey((E)o);
	}

	public boolean containsAll(Collection c)
	{
		boolean allSuccess = true;

		for (Object e : c)
		{
			boolean success = hashMap.containsKey((E)e);

			if (!success)
			{
				allSuccess = false;
			}
		}

		return allSuccess;
	}

	public boolean isEmpty()
	{
		return hashMap.isEmpty();
	}

	public Iterator iterator()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean remove(Object o)
	{
		return hashMap.remove((E)o) == PRESENT;
	}

	public boolean removeAll(Collection c)
	{
		boolean allSuccess = true;

		for (Object e : c)
		{
			boolean success = remove(e);

			if (!success)
			{
				allSuccess = false;
			}
		}

		return allSuccess;
	}

	public boolean retainAll(Collection c)
	{
		return true;
	}

	public int size()
	{
		return hashMap.size();
	}

	public Object[] toArray()
	{
		Object[] ret = new Object[hashMap.size()];

		int i = 0;
		for (Map.Entry entry : hashMap)
		{
			ret[i] = entry.getKey();
			i += 1;
		}

		return ret;
	}

	public Object[] toArray(Object[] a)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
