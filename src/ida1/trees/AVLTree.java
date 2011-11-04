package ida1.trees;

/**
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
	 * @param node		  The node that must get balanced (or not)
	 * @param child		  Which child (side) was added.
	 * @param childHeight The height of the child
	 */
	private void balance(BKnoop<E> node, int mode)
	{
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
	 * @param element
	 * @param node
	 */
	private void insert(E element, BKnoop<E> node)
	{
		//System.out.println("Node :" + node.get());
		//System.out.println("Element :" + element);
		
		int comp = element.compareTo(node.get());

		//System.out.println("comp: " + comp);

		if (comp == -1)
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
		else if (comp == 1)
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
		int comp = element.compareTo(node.get());

		//System.out.println("comp: " + comp);

		if (comp == -1)
		{
			//System.out.println("Comp -1");
			delete(element, node.getLeftChild());
		}
		else if (comp == 1)
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

	// Deletes a BKnoop (node) that has one child. Attaches that child to the parent of node
	// on the previous location of node in parent.
	private void switchNodeForDeleteWithOneChild(BKnoop<E> node, int leftOrRight)
	{
		BKnoop<E> parent = node.getParent();
		BKnoop<E> child;

		if (leftOrRight == BKnoop.LEFT)
		{
			child = node.getLeftChild();
		}
		else
		{
			child = node.getRightChild();
		}

		if (parent != null)
		{
			node.remove(child);

			if (parent.getLeftChild() == node)
			{
				parent.remove(node);
				parent.insert(child, BKnoop.LEFT);
			}
			else
			{
				parent.remove(node);
				parent.insert(child, BKnoop.RIGHT);
			}
		}
		else
		{
			if (node == root)
			{
				root = null;
			}
		}
	}

	// This is used when deleting a node. This node gets switched with the
	// node its deleting.
	private BKnoop <E> getLargestInLeftSubtree(BKnoop <E> node)
	{
		BKnoop <E> tree = node.getLeftChild();
		if (tree == null)
		{
			return node;
		}
		else
		{
			while (true)
			{
				BKnoop<E> rightChildOfLeftTree = tree.getRightChild();

				if (rightChildOfLeftTree == null)
				{
					return tree;
				}

				tree = rightChildOfLeftTree;
			}
		}
	}

	public boolean contains(E element)
	{
		if (root == null)
		{
			return false;
		}

		return contains(element, root);
	}

	private boolean contains(E element, BKnoop<E> node)
	{
		int comp = element.compareTo(node.get());

		if (comp == -1)
		{
			if(node.getLeftChild() == null)
			{
				return false;
			}
			else
			{
				return contains(element, node.getLeftChild());
			}
		}
		else if (comp == 1)
		{
			if(node.getRightChild() == null)
			{
				return false;
			}
			else
			{
				return contains(element, node.getRightChild());
			}
		}
		else // Element is found
		{
			return true;
		}
	}
}
