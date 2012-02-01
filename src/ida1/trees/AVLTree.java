package ida1.trees;

/**
 * Wikipedia: http://en.wikipedia.org/wiki/AVL_tree
 *
 * Animation: http://www.site.uottawa.ca/~stan/csi2514/applets/avl/BT.html
 *
 * @author maartenhus
 */
public class AVLTree<E extends Comparable<E>> extends Tree<E>
{
	private static final int INSERT = 0;
	private static final int DELETE = 1;

	public AVLTree()
	{
		
	}

	/**
	 * Balance the tree.
	 *
	 * bumps Counter
	 *
	 * @param node		  The node that must get balanced (or not)
	 * @param child		  Which child (side) was added.
	 * @param childHeight The height of the child
	 */
	private void balance(BKnoop<E> node, int mode)
	{
		bumpCounter();

		//System.out.println("Begin balance");
		
		if (node == null) // roots parent
		{
			return; // We are done balancing.
		}
		
		//System.out.println("Element: " + node.get());

		int balanceFactor = balanceFactor(node);

		if (balanceFactor == -2) // right subtree outweighs the left
		{
			//System.out.println("Right tree outweighs");

			int rightBalance = balanceFactor(node.getRightChild());
			//System.out.println("rightBalance: " + rightBalance);

			if (rightBalance == -1)
			{
				leftRotation(node);

				if (mode == INSERT)
				{
					return;
				}
			}
			else if (rightBalance == 1)
			{
				//System.out.println("Right double rotation");
				rightRotation(node.getRightChild());
				leftRotation(node);

				if (mode == INSERT)
				{
					return;
				}
			}

			// Edge case for deleteing. If the left node from root is deleted.
			// But there is a right side with 2 children. The balance = 0.
			// Then nothing will happen. This is fixed by doing a rotation
			if (mode == DELETE && rightBalance == 0)
			{
				leftRotation(node);
			}
		}
		else if (balanceFactor == 2) // left subtree outweighs the right
		{
			//System.out.println("Left tree outweighs");

			int leftBalance = balanceFactor(node.getLeftChild());
			//System.out.println("leftBalance: " + leftBalance);

			if (leftBalance == 1)
			{
				rightRotation(node);

				if (mode == INSERT)
				{
					return;
				}
			}
			else if (leftBalance == -1)
			{
				//System.out.println("Left double rotation");
				leftRotation(node.getLeftChild());
				rightRotation(node);

				if (mode == INSERT)
				{
					return;
				}
			}

			// Edge case for deleteing. If the left node from root is deleted.
			// But there is a right side with 2 children. The balance = 0.
			// Then nothing will happen. This is fixed by doing a rotation
			if (mode == DELETE && leftBalance == 0)
			{
				rightRotation(node);
			}
		}

		balance(node.getParent(), mode);
	}

	private int balanceFactor(BKnoop<E> node)
	{
		bumpCounter();

		// defaults, cant ask null object anything!
		int leftHeight = 0;
		int rightHeight = 0;

		if (node.getLeftChild() != null)
		{
			//System.out.println("left not null");
			leftHeight = node.getLeftChild().diepte();
		}

		if (node.getRightChild() != null)
		{
			//System.out.println("right not null");
			rightHeight = node.getRightChild().diepte();
		}

		//System.out.println("leftHeight: " + leftHeight + " rightHeight: " + rightHeight);

		int balanceFactor = leftHeight - rightHeight;
		
		//System.out.println("Node: " + node.get() +" Balance Factor: " + balanceFactor);
		return balanceFactor;
	}

	public void insert(E element)
	{
		// Edge case, this is the first insert.
		if (root == null)
		{
			root = new BKnoop<E>(element);
		}
		else
		{
			insert(element, root);
		}
	}

	/**
	 * Compare the element to the node.userObject.
	 * If its smaller add to the leftChild. If bigger
	 * add to the rightChild. This is done via recursion.
	 *
	 * If the element already exists do nothing. Its a search
	 * tree so duplicates are not allowed.
	 *
	 * Bumps counter
	 *
	 * @param element
	 * @param node
	 */
	private void insert(E element, BKnoop<E> node)
	{
		bumpCounter();

		//System.out.println("Node :" + node.get());
		//System.out.println("Element :" + element);
		
		int comp = element.compareTo(node.get());

		//System.out.println("comp: " + comp);

		if (comp <= -1)
		{
			//System.out.println("Comp -1");

			if(node.getLeftChild() == null)
			{
				//System.out.println("Insert left");
				node.insert(new BKnoop<E>(element), BKnoop.LEFT);
				balance(node.getLeftChild(), INSERT);
			}
			else
			{
				insert(element, node.getLeftChild());
			}
		}
		else if (comp >= 1)
		{
			//System.out.println("Comp 1");

			if(node.getRightChild() == null)
			{
				//System.out.println("Insert right");
				node.insert(new BKnoop<E>(element), BKnoop.RIGHT);
				balance(node.getRightChild(), INSERT);
			}
			else
			{
				insert(element, node.getRightChild());
			}
		}	
	}

	/**
	 * Bumps counter
	 *
	 * @param element
	 */
	public void delete(E element)
	{
		if (root == null)
		{
			return;
		}
		else
		{
			delete(element, root);
		}
	}

	private void delete(E element, BKnoop<E> node)
	{
		bumpCounter();

        if(node == null)
        {
            return;
        }

		int comp = element.compareTo(node.get());

		//System.out.println("comp: " + comp);

		if (comp <= -1)
		{
			//System.out.println("Comp -1");
			delete(element, node.getLeftChild());
		}
		else if (comp >= 1)
		{
			//System.out.println("Comp 1");
			delete(element, node.getRightChild());
		}
		else // Element is found
		{
			//System.out.println("Node found: " + node.get());

			if (node.getLeftChild() == null && node.getRightChild() == null) // node is leaf
			{
				BKnoop<E> parent = node.getParent();
				if (parent != null)
				{
					parent.remove(node);
				}

				balance(parent, DELETE);
			}
			else if (node.getLeftChild() != null && node.getRightChild() != null) // Two children
			{
				BKnoop<E> replacement = getLargestInLeftSubtree(node);

				//System.out.println("replacement: " + replacement);

				BKnoop<E> parent		= node.getParent();
				BKnoop<E> leftChild		= node.getLeftChild();
				BKnoop<E> rightChild	= node.getRightChild();

				//System.out.println("LeftChild: " + leftChild);
				//System.out.println("RightChild: " + rightChild);

				node.remove(leftChild);
				node.remove(rightChild);

				if(replacement.getParent() != null)
				{
					replacement.getParent().remove(replacement);
				}

				if (leftChild != replacement)
				{
					replacement.insert(leftChild,  BKnoop.LEFT);
				}

				replacement.insert(rightChild, BKnoop.RIGHT);

				if (node == root)
				{
					root = replacement;
				}
				else
				{
					if (parent.getLeftChild() == node)
					{
						parent.remove(node);
						parent.insert(replacement, BKnoop.LEFT);
					}
					else
					{
						parent.remove(node);
						parent.insert(replacement, BKnoop.RIGHT);
					}
				}

				balance(replacement, DELETE);
			}
			else // one child, find out which
			{
				if (node.getLeftChild() != null)
				{
					switchNodeForDeleteWithOneChild(node, BKnoop.LEFT);
				}
				else
				{
					switchNodeForDeleteWithOneChild(node, BKnoop.RIGHT);
				}
			}
		}
	}

	public String toString()
	{
		return "AVLTree";
	}

	public static void test()
	{
		AVLTree tree = new AVLTree();
	
		tree.insert(3);
		System.out.println("Counter = " + tree.getCounter());
		tree.resetCounter();
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("3");

		tree.insert(2);
		System.out.println("Counter = " + tree.getCounter());
		tree.resetCounter();
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("3 2");

		tree.insert(1);
		System.out.println("Counter = " + tree.getCounter());
		tree.resetCounter();
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 3");

		tree.insert(4);
		System.out.println("Counter = " + tree.getCounter());
		tree.resetCounter();
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 3 4");

		tree.insert(5);
		System.out.println("Counter = " + tree.getCounter());
		tree.resetCounter();

		System.out.println("Following lines should be the same");
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 4 3 5");

		System.out.println("\nStart testing delete");

		System.out.println("\ndelete 2");
		tree.delete(2);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("4 1 3 5");

		System.out.println("\nReinsert 2");
		tree.insert(2);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("4 2 1 3 5");

		System.out.println("\ndelete 5");
		tree.delete(5);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 4 3");

		System.out.println("\ndelete 4");
		tree.delete(4);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 3");

		System.out.println("\nFollowing lines should be the same");

		tree.insert(6);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 3 6");	
		
		tree.insert(7);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("2 1 6 3 7");
		
		tree.insert(16);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("6 2 1 3 7 16");
		
		tree.insert(15);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("6 2 1 3 15 7 16");

		tree.insert(14);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("6 2 1 3 15 7 14 16");

		System.out.println("\nTesting the contains");
		System.out.println("Contains: 15 - " + tree.contains(15));
		System.out.println("Contains: 16 - " + tree.contains(16));
		System.out.println("Contains: 6  - " + tree.contains(6));
		System.out.println("Contains: 2  - " + tree.contains(2));
		System.out.println("Contains: 0  - " + tree.contains(0));
		System.out.println("Contains: 55 - " + tree.contains(55));
	}

	public static void main (String [] args)
	{
        test();
	}
}
