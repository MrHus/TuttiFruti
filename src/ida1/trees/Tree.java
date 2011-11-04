package ida1.trees;

/**
 *	Contains  operations used by all trees.
 * 
 *  rightRotation and leftRotation.
 * 
 *  Wikipedia: http://en.wikipedia.org/wiki/Tree_rotation
 *
 * @author maartenhus
 */
public class Tree <E>
{
	protected BKnoop<E> root;

	/**
	 * Rotate rt to the right
	 *
	 *
	 * @param rt the node that gets rotated
	 */
	protected void rightRotation(BKnoop<E> rt)
	{
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
	 *
	 * @param rt the node that gets rotated
	 */
	protected void leftRotation(BKnoop<E> rt)
	{
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

	public BKnoop<E> getRoot()
	{
		return root;
	}
}
