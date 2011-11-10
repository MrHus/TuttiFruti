package stack.linkedlist;

import java.util.*;

public class Lijst 
{
	private Entry header;
	private int size;

	// Constructor voor een lege lijst
	public Lijst() 
	{
 		clear();
	}

	// Object vooraan opvragen
	public Object getFirst() 
	{
	  	return header.next.element;
	}
	
	public Object getLast()
	{
		return header.previous.element;
	}
	
	// Aantal objecten in de lijst afleveren
	public int size() 
	{
	  	return size;
	}

	// Object vooraan toevoegen
	public void addFirst( Object o ) 
	{	
		addBefore( o, header.next );	
	}
	
	public void addLast(Object o)
	{
		addBefore(o, header);
	}

	// Object vooraan verwijderen
	public Object removeFirst() 
	{
		Object tmp = header.next.element;
		
		try
		{
			remove(header.next);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return tmp;
	}
	
	public Object removeLast()
	{
		Object tmp = header.previous.element;
		
		try
		{
			remove(header.previous);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return tmp;
	}

	// De lijst leegmaken
	public void clear() 
	{
		// Ik weet niet zeker of de garbage collector de entries dan vrijgeeft.
		// Opzich is er geen pointer meer naar de rest, maar de entries wijzen wel
		// naar elkaar. Dus: zal het gecollect worden omdat niets meer
		// naar de entries wijst. Of zal het niet gecollect worden omdat er entries
		// zijn die naar elkaar wijzen.
		// Java's garbage collector is legendarisch inmiddels het lijkt mij dan 
		// dat hij begrijpt dat hij het mag collecten. -- Maarten
		
		size = 0;
		header = new Entry( null, null, null );
	    header.next = header;
	    header.previous = header;
	}
	
	// private methoden
	private void addBefore( Object o, Entry e ) 
	{
		Entry newEntry = new Entry(o, e, e.previous);
		
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;
		
		size++;
	}
	
	private void remove( Entry e ) 
	{
	  	if( e == header )
	    	throw new NoSuchElementException("Kan Entry niet verwijderen: is header");

	  	e.previous.next = e.next;
	  	e.next.previous = e.previous;
	  	size--;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	// Lever een iterator voor de lijst
	public Iterator iterator() 
	{
		return new LItr();
	}

	// Inwendige klasse LItr
	private class LItr implements Iterator 
	{
	  	private Entry pos = header;

	  	public boolean hasNext() 
	  	{
	    	return pos.next != header;
	  	}

	  	public Object next() 
	  	{	
	    	pos = pos.next;
	    	return pos.element;
	  	}

	  	public boolean hasPrevious() 
	  	{
	    	return pos.previous != header;
	  	}	

	  	public Object previous() 
	  	{
	    	pos = pos.previous;
	    	return pos.element;
	  	}
	} // Einde inwendige klasse LItr

	// Inwendige klasse Entry
	private class Entry 
	{
	  	Object element;
	  	Entry next;
	  	Entry previous;

	  	Entry( Object element, Entry next, Entry previous ) 
	  	{
			//System.out.println("Toegevoegd:" + element);
		
	    	this.element = element;
	    	this.next = next;
	    	this.previous = previous;
	  	}
		
		public String toString()
		{
			return "previous: " + previous.element + " this: " + element + " next: " + next.element;
		}
	
	} // Einde inwendige klasse Entry

} // Einde klasse Lijst
