package ida1.datastructures.queue;

/**
 *
 * @author Maarten Hus
 */
public interface Queue
{
     public void enqueue( Object o);
     public Object dequeue();
     public Object front();
     public int size();
     public boolean isEmpty();
}
