package ida1.trees;

// Klasse voor een knoop voor een binaire boom

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Met boomwandelingen pre-order en level-order


public class BKnoop<E>
{
	private BKnoop<E> parent, leftChild, rightChild;
	private E userObject;
	public static final int LEFT = 0;  // public constanten voor het
	public static final int RIGHT = 1; // toevoegen van linker- of rechterkind

	// Constructors
	public BKnoop()
	{
		this(null);
	}

	public BKnoop(E userObject)
	{
		parent = null;
		leftChild = null;
		rightChild = null;
		this.userObject = userObject;
	}

	/* =============== Walks =============== */

	public String preOrderToString()
	{
		StringBuilder buffer = new StringBuilder();

		for (E node : preOrder())
		{
			buffer.append(node);
			buffer.append(" ");
		}

		return buffer.toString();
	}

	public ArrayList<E> preOrder()
	{
		ArrayList<E> retList = new ArrayList<E>();
		preOrder(retList);
		return retList;
	}

	private ArrayList<E> preOrder(ArrayList<E> retList)
	{
		retList.add(userObject);

		if(leftChild != null)
		{
			leftChild.preOrder(retList);
		}
		if(rightChild != null)
		{
			rightChild.preOrder(retList);
		}

		return retList;
	}

	public String inOrderToString()
	{
		StringBuilder buffer = new StringBuilder();

		for (E node : inOrder())
		{
			buffer.append(node);
			buffer.append(" ");
		}

		return buffer.toString();
	}

	public ArrayList<E> inOrder()
	{
		ArrayList<E> retList = new ArrayList<E>();
		inOrder(retList);
		return retList;
	}

	private ArrayList<E> inOrder(ArrayList<E> retList)
	{
		if(leftChild != null)
		{
			leftChild.inOrder(retList);
		}

		retList.add(userObject);

		if(rightChild != null)
		{
			rightChild.inOrder(retList);
		}

		return retList;
	}

	public String postOrderToString()
	{
		StringBuilder buffer = new StringBuilder();

		for (E node : postOrder())
		{
			buffer.append(node);
			buffer.append(" ");
		}

		return buffer.toString();
	}

	public ArrayList<E> postOrder()
	{
		ArrayList<E> retList = new ArrayList<E>();
		postOrder(retList);
		return retList;
	}
	
	private ArrayList<E> postOrder(ArrayList<E> retList)
	{
		if(leftChild != null)
		{
			leftChild.postOrder();
		}

		if(rightChild != null)
		{
			rightChild.postOrder();
		}
		
		retList.add(userObject);

		return retList;
	}

	public String levelOrderToString()
	{
		StringBuffer buffer = new StringBuffer();
		
		for (E node : levelOrder())
		{
			buffer.append(node);
			buffer.append(" ");
		}

		return buffer.toString();
	}

	private ArrayList<E> levelOrder()
	{
		Queue<BKnoop<E>> queue = new LinkedList<BKnoop<E>>();
		queue.add(this);

		ArrayList<E> retList = new ArrayList<E>();
		levelOrder(retList, queue);
		return retList;
	}

	private ArrayList<E> levelOrder(ArrayList<E> retList, Queue<BKnoop<E>> queue)
	{
		while(!queue.isEmpty())
		{
			BKnoop<E> knoop = queue.remove();

			retList.add(knoop.get());

			if(knoop.leftChild != null)
			{
				queue.add(knoop.leftChild);
			}

			if(knoop.rightChild != null)
			{
				queue.add(knoop.rightChild);
			}
		}

		return retList;
	}

	/* =============== Questions =============== */

	public boolean isChild(BKnoop<E> node)
	{
		if (node == null || node.getParent() == null)
		{
			return false;
		}
		else
		{
			//System.out.println("Node parent: " + (node.getParent() == this));
			return node.getParent() == this;
		}
	}

	public boolean isAncestor(BKnoop<E> aNode)
	{
		BKnoop<E> ancestor = this;
		while(ancestor != null && ancestor != aNode)
		{
			ancestor = ancestor.getParent();
		}
		return ancestor != null;
	}

	public int aantalKnopen()
	{
		if(leftChild == null && rightChild == null)
		{
			return 1;
		}
		if(leftChild != null && rightChild == null)
		{
			return 1 + leftChild.aantalKnopen();
		}
		if(leftChild == null && rightChild != null)
		{
			return 1 + rightChild.aantalKnopen();
		}
		else
		{
			return 1 + leftChild.aantalKnopen() + rightChild.aantalKnopen();
		}
	}

	public int aantalBlad()
	{
		if(leftChild != null && rightChild != null)
		{
			return leftChild.aantalBlad() + rightChild.aantalBlad();
		}
		else
		{
			return 1;
		}
	}

	public int diepte()
	{
		if(userObject == null)
		{
//			System.out.println("userobject null");
			return -1; //lege boom
		}

		//System.out.println("element: " + userObject +" left: " + leftChild + " right:" + rightChild);

		if (leftChild == null && rightChild == null)
		{
			return 1;
		}

		if (leftChild == null)
		{
			//System.out.println("Left is null do right");
			return 1 + rightChild.diepte();
		}

		if (rightChild == null)
		{
			//System.out.println("Right is null do left");
			return 1 + leftChild.diepte();
		}

		//System.out.println("both");
		return Math.max(1 + leftChild.diepte(), 1 + rightChild.diepte());
	}

	/* =============== CRUD =============== */

	public void add(BKnoop<E> newChild)
	{
		if(leftChild == null)
		{
			insert(newChild, LEFT);
		}
		else if(rightChild == null)
		{
			insert(newChild, RIGHT);
		}
		else
		{
			throw new IllegalArgumentException("Meer dan 2 kinderen");
		}
	}

	public void insert(BKnoop<E> newChild, int childIndex)
	{
		if(isAncestor(newChild))
		{
			throw new IllegalArgumentException("Nieuw kind is voorouder");
		}
		if(childIndex != LEFT && childIndex != RIGHT)
		{
			throw new IllegalArgumentException("Index moet 0 of 1 zijn");
		}

		if(newChild != null)
		{
			BKnoop<E> oldParent = newChild.getParent();
			if(oldParent != null)
			{
				oldParent.remove(newChild);
			}
		}

		newChild.parent = this;
		if(childIndex == LEFT)
		{
			leftChild = newChild;
		}
		else
		{
			rightChild = newChild;
		}
	}

	public void remove(BKnoop<E> aChild)
	{
		if(aChild == null)
		{
			throw new IllegalArgumentException("Argument is null");
		}

		if(!isChild(aChild))
		{
			throw new IllegalArgumentException("Argument is geen kind");
		}

		if(aChild == leftChild)
		{
			leftChild.parent = null;
			leftChild = null;
		}
		else
		{
			rightChild.parent = null;
			rightChild = null;
		}
	}

	/* =============== Getters and setters =============== */

	public E get()
	{
		return userObject;
	}

	public void set(E element)
	{
		this.userObject = element;
	}

	public BKnoop<E> getLeftChild()
	{
		return leftChild;
	}

	public BKnoop<E> getRightChild()
	{
		return rightChild;
	}

	public BKnoop<E> getParent()
	{
		return parent;
	}

	public String toString()
	{
		return userObject.toString();
	}
}
