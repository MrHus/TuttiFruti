package ida1.trees;

// Klasse voor een knoop voor een binaire boom
// Met boomwandelingen pre-order en level-order
import java.util.*;

public class BKnoop<E>
{
	private BKnoop<E> parent, leftChild, rightChild;
	private E userObject;
	private static StringBuffer buffer;
	private Queue<BKnoop<E>> q; // queue voor level-order wandeling
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

	// Methoden
	// preOrderToString() levert het resultaat van
	// een preorder wandeling af in een string
	public String preOrderToString()
	{
		buffer = new StringBuffer();
		preOrder();            // roep recursieve methode aan
		return buffer.toString();
	}

	private void preOrder()
	{
		buffer.append(this);
		buffer.append(" ");
		if(leftChild != null)
		{
			leftChild.preOrder();
		}
		if(rightChild != null)
		{
			rightChild.preOrder();
		}
	}

	/*
	\/\/ Added content \/\/
	 */

	public String inOrderToString()
	{
		buffer = new StringBuffer();
		inOrder();
		return buffer.toString();
	}

	private void inOrder()
	{
		if(leftChild != null)
		{
			leftChild.inOrder();
		}
		buffer.append(this);
		buffer.append(" ");
		if(rightChild != null)
		{
			rightChild.inOrder();
		}
	}

	public String postOrderToString()
	{
		buffer = new StringBuffer();
		postOrder();
		return buffer.toString();
	}

	private void postOrder()
	{
		if(leftChild != null)
		{
			leftChild.postOrder();
		}
		if(rightChild != null)
		{
			rightChild.postOrder();
		}
		buffer.append(this);
		buffer.append(" ");
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
			System.out.println("userobject null");
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

	/*
		^^ Added content ^^
	 */

	// levelOrderToString() levert het resultaat van
	// een level-order wandeling af in een string
	public String levelOrderToString()
	{
		buffer = new StringBuffer();
		//q = new ArrayDeque< BKnoop<E> >();
		q = new LinkedList< BKnoop<E>>();
		q.add(this);
		levelOrder();
		return buffer.toString();
	}

	private void levelOrder()
	{
		while(!q.isEmpty())
		{
			BKnoop<E> knoop = q.remove();
			buffer.append(knoop.userObject.toString());
			if(knoop.leftChild != null)
			{
				q.add(knoop.leftChild);
			}
			if(knoop.rightChild != null)
			{
				q.add(knoop.rightChild);
			}
		}
	}

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

	public String toString()
	{
		return userObject.toString();
	}
}
