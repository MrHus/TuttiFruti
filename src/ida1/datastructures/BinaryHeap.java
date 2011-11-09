package ida1.datastructures;

import java.util.ArrayList;

/**
 *
 * Simulation http://people.ksp.sk/~kuko/bak/index.html
 *
 * @author Cornel Alders
 */
public class BinaryHeap<E extends Comparable<E>>
{
    ArrayList<E> list = new ArrayList<E>();
    Type heapType;


    public BinaryHeap(Type t)
    {
        heapType = t;
    }

    public BinaryHeap(Type t, ArrayList<E> a)
    {
        heapType = t;
        
        for(E e : a)
        {
            list.add(e);
            heapify();
        }
    }

	public enum Type
    {
        MINHEAP, MAXHEAP
    }

    public void heapify()
    {
        //System.out.println("Heapify");
        heap(list.size() - 1);
    }

    private void heap(int index)
    {
        //System.out.println("Current list: " + list);
        //System.out.println("Current index: " + index);

        int parentIndex = getParentIndex(index);

        if(index == 0 || parentIndex < 0)
        {
            return;
        }

		E indexValue = list.get(index);
        E parentValue = list.get(getParentIndex(index));

        //System.out.println("list.get(index): " + indexValue);
        //System.out.println("parentValue: " + parentValue);

		int comp = indexValue.compareTo(parentValue);

        if(heapType == Type.MINHEAP)
        {
            if(comp < 0)
            {
                //System.out.println("Swap it");
                this.swap(parentIndex, index);
                heap(parentIndex);
            }
        }
        else
        {
            if(comp > 0)
            {
                //System.out.println("Swap it");
                this.swap(parentIndex, index);
                heap(parentIndex);
            }
        }
    }

    public void add(E node)
    {
        //System.out.println("Adding node: " + node);
        list.add(node);
        //Add to end of heap
        heapify();
    }

    public void delete()
    {
        //System.out.println("Delete first node");
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);

		checkHeapAfterDelete(0);
    }

    private void checkHeapAfterDelete(int index)
    {
        int listsize = list.size();

		// Compare only leftChild with parent. In this edgecase.
		if (listsize == 2)
		{
			if (heapType == Type.MINHEAP)
			{
				if(list.get(0).compareTo(list.get(1)) > 0)
				{
					this.swap(0, 1);
				}
			}
			else
			{
				if(list.get(0).compareTo(list.get(1)) < 0)
				{
					this.swap(0, 1);
				}
			}

			return;
		}

		int leftChild = (2 * index) + 1;
        int rightChild = (2 * index) + 2;

		//System.out.println("leftChild: " + leftChild + " rightChild: " + rightChild);
		//System.out.println("size: " + listsize);

        boolean leftChildInBounds  = leftChild < listsize;
        boolean rightChildInBounds = rightChild < listsize;

		//System.out.println("leftChild in bounds " + leftChildInBounds);
		//System.out.println("rightChild in bounds " + rightChildInBounds);

        if(!leftChildInBounds && !rightChildInBounds)
        {
            return;
        }	

		E parentValue	= list.get(getParentIndex(index));
		E leftValue		= list.get(leftChild);

        // If the leftChild is not in bounds and the rightchild is definately not in bounds.
		// Happens only on the last check when array is bigger than 2.
        if (leftChildInBounds && !rightChildInBounds)
        {
			if (heapType == Type.MINHEAP)
			{
				if(leftValue.compareTo(parentValue) > 0)
				{
					this.swap(index, leftChild);
					return;
				}
			}
			else
			{
				if(leftValue.compareTo(parentValue) < 0)
				{
					this.swap(index, leftChild);
					return;
				}
			}
        }
        else if (rightChildInBounds)
        {
			E rightValue = list.get(rightChild);
			
			// If one one child is smaller than the parent (either left or right)
			if(leftValue.compareTo(parentValue) < 0 || rightValue.compareTo(parentValue) > 0)
            {
				// Pick the biggest of the two and swap with parent
                int compLeftRight = leftValue.compareTo(rightValue);

				//System.out.println("leftValue: " + leftValue + " rightValue: " + rightValue);
				//System.out.println("compLeftWithRight: " + compLeftRight);

				if(compLeftRight < 0)
				{
					if (heapType == Type.MINHEAP)
					{
						this.swap(index, leftChild);
						//System.out.println(list);
						checkHeapAfterDelete(leftChild);
					}
					else
					{
						this.swap(index, rightChild);
						//System.out.println(list);
						checkHeapAfterDelete(rightChild);
					}
				}
				else
				{
					if (heapType == Type.MINHEAP)
					{
						this.swap(index, rightChild);
						//System.out.println(list);
						checkHeapAfterDelete(rightChild);
					}
					else
					{
						this.swap(index, leftChild);
						//System.out.println(list);
						checkHeapAfterDelete(leftChild);
					}
				}
            }
        }
    }

    private void swap(int parent, int child)
    {
        //System.out.println("Swap Parent index: " + parent + "; Child index: " + child);
        E oldParent = list.get(parent);
        E oldChild = list.get(child);

        list.set(parent, oldChild);
        list.set(child, oldParent);
    }

    public int getParentIndex(int i)
    {
        return (int)Math.floor((i-1)/2);
    }

    public ArrayList<E> getList()
    {
        return list;
    }

	public static void test()
	{
		int[] ints = {7, 8, 3, 5, 1, 9};
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (int index = 0; index < ints.length; index++)
        {
            intList.add(ints[index]);
        }

		System.out.println("Checking minheap");
        System.out.println("IN: " + intList);
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Type.MINHEAP, intList);
        System.out.println("OUT: " + heap.getList());
		System.out.println("Expected: 1 3 7 8 5 9");

		System.out.println("\nChecking delete\n");
        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 3 5 7 8 9");

        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 5 8 7 9");
		
        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 7 8 9");
		
		heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 8 9");
        
		heap.delete();
		System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 9");

		heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: []");


		System.out.println("\nChecking maxheap");
        System.out.println("IN: " + intList);
        heap = new BinaryHeap<Integer>(Type.MAXHEAP, intList);
        System.out.println("OUT: " + heap.getList());
		System.out.println("Expected: 9 7 8 5 1 3");

		System.out.println("\nChecking delete\n");
        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 8 7 3 5 1");

        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 7 5 3 1");

        heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 5 1 3");

		heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 3 1");

		heap.delete();
		System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: 1");

		heap.delete();
        System.out.println("After delete: " + heap.getList());
		System.out.println("Expected: []");
	}

	public static void main (String [] args)
	{
        test();
	}
}
