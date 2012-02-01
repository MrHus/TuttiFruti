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
 * @author Maarten Hus
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
		int hash = getHashCode(key);
        HashEntry<K, V> tmpObj = table[hash];

		if (tmpObj != null)
		{
			if (tmpObj.next == null)
			{
				return tmpObj.key.equals(key);
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
			if (entry.key.equals(key))
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
			if(entry != null && entry.value.equals(value))
			{
				return true;
			}

			if (entry != null)
			{
				while(entry != null)
				{
					if (entry != null && entry.value.equals(value))
					{
						return true;
					}

					entry = entry.next;
				}
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
        int hash = getHashCode(key);
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
        else if(entry.key.equals(key))
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
        int hash = getHashCode(key);


        if(table[hash] == null)
        {
			HashEntry<K, V> entry = new HashEntry<K, V>(key, value, null);
            table[hash] = entry;
			currentSize += 1;
			return entry.value;
        }
        else
        {
			if(table[hash].key.equals(key))
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
		int hash = getHashCode(key);
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
			if (table[hash].key.equals(key))
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

    public int getHashCode(Object s)
    {
        int hash = s.hashCode() % tableSize;
        //Edgecase, sometimes the hash is negative. It is however never lower
        //then negative tableSize, therefore adding the tablesize to the hash
        //value gives us the correct? positive hash that stays within the
        //bounds of the array.
        if(hash < 0)
        {
//            System.out.println(s + " -> " + (hash + tableSize));
            return hash + tableSize;
        }
        else
        {
//            System.out.println(s + " -> " + hash);
            return hash;
        }
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

	public static void test()
	{
		HashMap<String, Integer> testMap = new HashMap<String, Integer>();

		System.out.println("\nChecking put");
		testMap.put("a", 10);
		testMap.put("b", 9);
		testMap.put("b", 2);
		testMap.put("c", 8);
		testMap.put("c", 7);

		for (Map.Entry<String, Integer> entry : testMap)
		{
			System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
		}

		System.out.println("Expecting (a, 10), (b, 2), (c, 7)");

		System.out.println("\nChecking size");
		System.out.println(testMap.size());
		System.out.println("expecting 3");

		System.out.println("\nChecking remove");
		System.out.println(testMap.remove("b"));
		System.out.println("Expect 2");
		System.out.println(testMap.remove("c"));
		System.out.println("Expect 7");

		System.out.println("\nChecking size");
		System.out.println(testMap.size());
		System.out.println("Expect 1");

		System.out.println("\nChecking containsKey");
		System.out.println(testMap.containsKey("a"));
		System.out.println(testMap.containsKey("b"));
		System.out.println(testMap.containsKey("d"));
		System.out.println("Expecting: true, false false");

		System.out.println("\nChecking remove which makes map nil");
		System.out.println(testMap.remove("a"));
		System.out.println(testMap.size());
		System.out.println("Expecting size to be 0");

		testMap.put("a", 10);
		testMap.put("ab", 12);
		testMap.put("abb", 14);

		System.out.println("Expect 10 got: " + testMap.get("a"));
		System.out.println("Expect 12 got: " + testMap.get("ab"));
		System.out.println("Expect 14 got: " + testMap.get("abb"));

		System.out.println("\nChecking contains value");
		System.out.println("Expect true  got: " + testMap.containsValue(10));
		System.out.println("Expect true  got: " + testMap.containsValue(12));
		System.out.println("Expect true  got: " + testMap.containsValue(14));
		System.out.println("Expect false got: " + testMap.containsValue(16));

		System.out.println("\nChecking size");
		System.out.println(testMap.size());
		System.out.println("Expecting size to be 3");

		System.out.println("\nPrinting all values");
		System.out.println(testMap.values());
		System.out.println("Expecting [10, 12, 14]");

		System.out.println("\nTesting loop through entries");
		for (Map.Entry<String, Integer> entry : testMap)
		{
			System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
		}

		System.out.println("Expecting (a, 10), (ab, 12), (abb, 14)");
	}

    public static void main(String[] args)
    {
        test();
    }
}
