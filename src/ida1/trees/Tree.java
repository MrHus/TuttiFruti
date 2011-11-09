package ida1.trees;

/**
 *	Contains  operations used by all trees.
 * 
 *  rightRotation and leftRotation.
 * 
 *  Wikipedia: http://en.wikipedia.org/wiki/Tree_rotation
 *
 *	Has a delete for when node is leaf, and when node has one child
 *
 *  Also the method contains(element) to check if its in the tree.
 *
 * @author maartenhus
 */
public abstract class Tree <E extends Comparable<E>>
{
	protected BKnoop<E> root;

	// Counts the actions must be cleared manually.
	protected int counter;

	/**
	 * Rotate rt to the right.
	 *
	 * Bumps counter
	 *
	 * @param rt the node that gets rotated
	 */
	protected void rightRotation(BKnoop<E> rt)
	{
		bumpCounter();

		//System.out.println("RightRotation " + rt.get());

		// take the pivot, the one that will take rt place
		BKnoop<E> pivot = rt.getLeftChild();

		if (pivot == null) // No pivot means no rotation (can happen in double rotate)
		{
			//System.out.println("Right Rotation not neccessary no child");
			return;
		}

		// Pivot can have children these must be added to the rt' left to prevent orphanage
		BKnoop<E> rightChildOfPivot = pivot.getRightChild();

		boolean pivotRemoved = false; // Keep tabs so we don't remove twice (causes error)
		if (rightChildOfPivot != null)
		{
			// Remember KNoop will whine if we add a child that is already the ancestor
			// That's why a remove must be done first

			pivot.remove(rightChildOfPivot);
			rt.remove(pivot);
			pivotRemoved = true;
			rt.insert(rightChildOfPivot, BKnoop.LEFT);
		}

		// Check if rt has a parent. The parent must link to the pivot to keep the tree
		BKnoop<E> parentOfRt = rt.getParent();

		if (parentOfRt != null) // If the rt has a parent it must now link to the pivot.
		{
			//System.out.println("parentValue: " + parentOfRt.get());

			// Set pivot to be the child of the parent(of rt) on the old location of rt
			// This keeps the tree intact. Otherwise the tree gets broken.
			if (parentOfRt.getLeftChild() == rt)
			{
				parentOfRt.remove(rt);
				parentOfRt.insert(pivot, BKnoop.LEFT);
			}
			else
			{
				parentOfRt.remove(rt);
				parentOfRt.insert(pivot, BKnoop.RIGHT);
			}
		}
		else if (!pivotRemoved) // Don't remove a second time, will cause error
		{
			rt.remove(pivot);
		}

		// Finally do the switch
		pivot.insert(rt, BKnoop.RIGHT);

		// I want a good reference to the root
		if (rt == root)
		{
			root = pivot;
		}
	}

	/**
	 * Rotate rt to the left
	 *
	 * Bumps counter
	 *
	 * @param rt the node that gets rotated
	 */
	protected void leftRotation(BKnoop<E> rt)
	{
		bumpCounter();

		//System.out.println("LeftRotation " + rt.get());

		// take the pivot, the one that will take rt place
		BKnoop<E> pivot = rt.getRightChild();

		if (pivot == null) // No pivot means no rotation (can happen in double rotate)
		{
			//System.out.println("Left Rotation not neccessary no child");
			return;
		}

		// Pivot can have children these must be added to the rt' right to prevent orphanage
		BKnoop<E> leftChildOfPivot = pivot.getLeftChild();

		boolean pivotRemoved = false; // Keep tabs so we won't remove twice (causes error)
		if (leftChildOfPivot != null)
		{
			// Remember KNoop will whine if we add a child that is already the ancestor
			// That's why a remove must be done first

			pivot.remove(leftChildOfPivot);
			rt.remove(pivot);
			pivotRemoved = true;
			rt.insert(leftChildOfPivot, BKnoop.RIGHT);
		}

		// Check if rt has a parent. The parent must link to the pivot to keep the tree
		BKnoop<E> parentOfRt = rt.getParent();

		if (parentOfRt != null)
		{
			//System.out.println("parentValue: " + parentOfRt.get());

			// Set pivot to be the child of the parent(of rt) on the old location of rt
			// This keeps the tree intact. Otherwise the tree gets broken.
			if (parentOfRt.getLeftChild() == rt)
			{
				parentOfRt.remove(rt);
				parentOfRt.insert(pivot, BKnoop.LEFT);
			}
			else
			{
				parentOfRt.remove(rt);
				parentOfRt.insert(pivot, BKnoop.RIGHT);
			}
		}
		else if (!pivotRemoved)
		{
			rt.remove(pivot);
		}

		// Finally do the switch
		pivot.insert(rt, BKnoop.LEFT);

		// I want a good reference to the root
		if (rt == root)
		{
			root = pivot;
		}
	}

	/**
	 * Check if element exists in tree.
	 *
	 * bumps counter
	 *
	 * @param element
	 * @return
	 */
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
		bumpCounter();

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

	// Deletes a BKnoop (node) that has one child. Attaches that child to the parent of node
	// on the previous location of node in parent.
	protected void switchNodeForDeleteWithOneChild(BKnoop<E> node, int leftOrRight)
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
				root = child;
			}
		}
	}

	// This is used when deleting a node. This node gets switched with the
	// node its deleting.
	protected BKnoop <E> getLargestInLeftSubtree(BKnoop <E> node)
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

	public BKnoop<E> getRoot()
	{
		return root;
	}

	public int getCounter()
	{
		return counter;
	}

	public void resetCounter()
	{
		counter = 0;
	}

	protected void bumpCounter()
	{
		counter += 1;
	}

    public abstract void insert(E element);
    public abstract void delete(E element);
}
