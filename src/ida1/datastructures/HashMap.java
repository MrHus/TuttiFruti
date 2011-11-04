package ida1.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Stores key value pairs. 
 * 
 * put ("A", 10)
 * put ("B", 12)
 * 
 * get ("A") -> 10
 * get ("B") -> 12
 *
 * containsKey("D") -> false
 * containsValue(12) -> true
 *
 * put ("A", 12) -> overwrites the previous value.
 *
 * remove("B") -> 12 returns the value that was "B"
 *
 * @author Cornel Alders, Maarten Hus
 */
public class HashMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>
{
    private HashEntry<K, V> table[];
    private int tableSize = 16;
    private int currentSize = 0;

    public HashMap()
    {
        this.tableSize = 16;
        clear();
    }

	/**
	 * Constructor that allows a variable sized hashtable.
	 */
    public HashMap(int size)
    {
        this.tableSize = size;
        clear();
    }

    public void clear()
    {
        table = new HashEntry[tableSize];
    }

    public boolean containsKey(Object key)
    {
		int hash = key.hashCode() % tableSize;
        HashEntry<K, V> tmpObj = table[hash];

		if (tmpObj != null)
		{	
			if (tmpObj.next == null)
			{
				return tmpObj.key == key;
			}
			else
			{
				return containsKeyRecur(tmpObj, key);
			}
		}

		return false;
    }

	private boolean containsKeyRecur(HashEntry<K, V> entry, Object key)
	{
		if (entry == null)
		{
			return false;
		}
		else
		{
			if (entry.key == key)
			{
				return true;
			}

			return containsKeyRecur(entry.next, key);
		}
	}

    public boolean containsValue(Object value)
    {
        for (HashEntry<K, V> entry : table)
		{
			if(entry.value == value)
			{
				return true;
			}

			while(entry.next != null)
			{
				if (entry.next.value == value)
				{
					return true;
				}
				
				entry = entry.next;
			}
		}

		return false;
    }

    public Set entrySet()
    {
		HashSet<K> set = new HashSet<K>();

		for (HashEntry<K, V> entry : table)
		{
			if (entry != null)
			{
				set.add(entry);

				while (entry.next != null)
				{
					set.add(entry.next);
					entry = entry.next;
				}
			}
		}

		return set;
    }

    public V get(Object key)
    {
        int hash = key.hashCode() % tableSize;
        HashEntry<K, V> tmpObj = table[hash];

        if(tmpObj == null)
        {
            return null;
        }
        if(tmpObj.key.equals(key))
        {
            return tmpObj.value;
        }
        else 
        {
            return getRecur(tmpObj, key);
        }
    }

    private V getRecur(HashEntry<K, V> entry, Object key)
    {
        if(entry == null || entry.value == null)
        {
            return null;
        }
        else if(entry.key == key)
        {
            return entry.value;
        }
		
        return getRecur(entry.next, key);
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public Set keySet()
    {
		HashSet<K> set = new HashSet<K>();

		for (HashEntry<K, V> entry : table)
		{
			if (entry != null)
			{
				set.add(entry.key);

				while (entry.next != null)
				{
					set.add(entry.next.key);
					entry = entry.next;
				}
			}
		}

		return set;
    }
	
    public V put(K key, V value)
    {
        int hash = key.hashCode() % tableSize;
        
        if(table[hash] == null)
        {
			HashEntry<K, V> entry = new HashEntry<K, V>(key, value, null);
            table[hash] = entry;
			currentSize += 1;
			return entry.value;
        }
        else
        {
			if(table[hash].key == key)
			{
				V ret = table[hash].value;
				table[hash].value = value;
				return ret;
			}
			else
			{
				HashEntry<K, V> newEntry = new HashEntry<K, V>(key, value, null);

				HashEntry<K, V> entry = table[hash];
				while(entry.next != null)
				{
					entry = entry.next;
				}

				entry.next = newEntry;
				currentSize += 1;
				return newEntry.value;
			}
		}
    }

    public void putAll(Map m)
    {
        Set set = m.entrySet();

		for(Object o: set.toArray())
		{
			Map.Entry<K, V> entry = (Map.Entry<K, V>)o;

			put(entry.getKey(), entry.getValue());
		}
    }

	public V remove(Object key)
	{
		int hash = key.hashCode() % tableSize;
        HashEntry<K, V> tmpObj = table[hash];

		if (tmpObj == null)
		{
			return null;
		}
		else if (table[hash].next == null)
		{
			V ret = tmpObj.value;

			table[hash] = null;

			currentSize -= 1;

			return ret;
		}
		else
		{
			return removeRecur(hash, key);
		}
	}

	private V removeRecur(int hash, Object key)
	{
		if (table[hash].next == null)
		{
			return null;
		}
		else
		{
			if (table[hash].key == key)
			{
				table[hash] = table[hash].next;
				currentSize -= 1;
			}

			return removeRecur(hash, key);
		}
	}

    public int size()
    {
        return currentSize;
    }

    public Collection values()
    {
		ArrayList <V> values = new ArrayList<V>();

		for (HashEntry<K, V> entry : table)
		{
			if (entry != null)
			{
				values.add(entry.value);

				while (entry.next != null)
				{
					values.add(entry.next.value);
					entry = entry.next;
				}
			}
		}

		return values;
    }

    public class HashEntry<K, V> implements Map.Entry<K, V>
    {
        private K key;
        private V value;
        private HashEntry<K, V> next;

        public HashEntry(K key, V value, HashEntry<K, V> next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

		public K getKey()
		{
			return key;
		}

		public V getValue()
		{
			return value;
		}

		public V setValue(V value)
		{
			V ret = this.value;
			this.value = value;
			return ret;
		}
    }

	public Iterator<Map.Entry<K, V>> iterator()
	{
		return new HashMapIterator(table, size());
	}

	private class HashMapIterator implements Iterator<Map.Entry<K, V>>
	{
		private HashEntry<K, V> table[];
		private HashEntry<K, V> current;
		private int size;
		private int currentIndex = 0;
		private int iteratedNoEntries = 0;

		public HashMapIterator(HashEntry<K, V>[] table, int size)
		{
			this.table = table;
			this.size = size;
		}

		public boolean hasNext()
		{
			if (iteratedNoEntries == size)
			{
				return false;
			}

			return true;
		}

		public Entry<K, V> next()
		{
			//System.out.println(current);
			while (current == null)
			{
				//System.out.println("here");
				current = table[currentIndex];
				currentIndex += 1;

				if (current != null)
				{
					Entry<K, V> ret = current;
					current = current.next;
					iteratedNoEntries += 1;
					return ret;
				}
			}

			Entry<K, V> ret = current;
			current = current.next;
			iteratedNoEntries += 1;
			return ret;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
