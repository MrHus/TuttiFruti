package ida1.trees;

/**
 * http://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 *
 * Good animation: http://www.cse.yorku.ca/~aaw/Sotirios/RedBlackTree.html
 *
 * @author maartenhus
 */
public class RBTree<E extends Comparable<E>> extends Tree<E>
{
	public void insert(E element)
	{
		// Edge case, this is the first insert.
		if (root == null)
		{
			root = new RBKnoop<E>(element, RBKnoop.BLACK);
		}
		else
		{
			insert(element, (RBKnoop)root);
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
	private void insert(E element, RBKnoop<E> node)
	{
		//System.out.println("Node :" + node.get());
		//System.out.println("Element :" + element);
		
		int comp = 1;
		if (node.get() != null)
		{
			comp = element.compareTo(node.get());
		}

		if (comp == -1)
		{
			//System.out.println("Comp -1");

			if(node.getLeftChild().get() == null)
			{
				//System.out.println("Insert left");
				node.remove(node.getLeftChild());
				RBKnoop<E> newNode = new RBKnoop<E>(element, RBKnoop.RED);
				node.insert(newNode, RBKnoop.LEFT);
				insertValidator(newNode);
			}
			else
			{
				insert(element, (RBKnoop)node.getLeftChild());
			}
		}
		else if (comp == 1)
		{
			//System.out.println("Comp 1");

			if(node.getRightChild().get() == null)
			{
				node.remove(node.getRightChild());
				RBKnoop<E> newNode = new RBKnoop<E>(element, RBKnoop.RED);
				node.insert(newNode, RBKnoop.RIGHT);
				insertValidator(newNode);
			}
			else
			{
				insert(element, (RBKnoop)node.getRightChild());
			}
		}
	}

	/**
	 * This insertValidator handles the various cases for inserting in red black trees.
	 *
	 * See wikipedia for cases: http://en.wikipedia.org/wiki/Red%E2%80%93black_tree
	 *
	 * @param node The node that must be validated.
	 */
	private void insertValidator(RBKnoop<E> node)
	{
		// Case one: node's parent is null;
		RBKnoop<E> parent = (RBKnoop)node.getParent();
		if (parent == null)
		{
			node.makeBlack();
		}
		else
		{
			// Case two: node's parent is black. Node is red. So just add
			if (parent.isBlack())
			{
				return;
			}
			else
			{
				// Case three: Both parent and uncle are red. Turn them black and grandparent red.

				RBKnoop<E> uncle		= getUncle(node);
				RBKnoop<E> grandParent  = getGrandParent(node);

				if (uncle != null && uncle.isRed())
				{
					parent.makeBlack();
					uncle.makeBlack();
					grandParent.makeRed();
					insertValidator(grandParent);
				}
				else
				{
					// Case four: parent is red but uncle is black. A rotation is needed
					if (node == parent.getRightChild() && parent == grandParent.getLeftChild())
					{
						leftRotation(parent);
						node = (RBKnoop)node.getLeftChild();
					}
					else if (node == parent.getLeftChild() && parent == grandParent.getRightChild())
					{
						rightRotation(parent);
						node = (RBKnoop)node.getRightChild();
					}

					// Case five: Parent is red but the uncle is black, node is the left child, parent is the left child of the grandparent

					// Could have changed in case four so rebind.
					parent = (RBKnoop)node.getParent();
					grandParent  = getGrandParent(node);

					parent.makeBlack();
					grandParent.makeRed();

					if (node == parent.getLeftChild() && parent == grandParent.getLeftChild())
					{
						rightRotation(grandParent);
					}
					else if (node == parent.getRightChild() && parent == grandParent.getRightChild())
					{
						leftRotation(grandParent);
					}
				}
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
			delete(element, (RBKnoop)root);
		}
	}

	private void delete(E element, RBKnoop<E> node)
	{
		int comp = 1;
		if (node.get() != null)
		{
			comp = element.compareTo(node.get());
		}

		//System.out.println("comp: " + comp);

		if (comp == -1)
		{
			//System.out.println("Comp -1");
			delete(element, (RBKnoop)node.getLeftChild());
		}
		else if (comp == 1)
		{
			//System.out.println("Comp 1");
			delete(element, (RBKnoop)node.getRightChild());
		}
		else // Element is found
		{
			//System.out.println("Node found: " + node.get());

			if (node.getLeftChild().get() != null && node.getRightChild().get() != null) // Two real children
			{
				System.out.println("Two real children");

				RBKnoop<E> replacement;
				if (node == root)
				{
					replacement = getSmallestInRightSubtree((RBKnoop)root);
				}
				else
				{
					replacement = getLargestInLeftSubtree(node);
				}

				System.out.println("node	: " + node);
				System.out.println("replacement: " + replacement);
				

				// Switch them, remember that this is possible because everyone has null children.
				node.set(replacement.get());
				replacement.set(null);

				if (node == root)
				{
					deleteValidator(replacement);
				}
				else
				{
					deleteValidator(replacement);
				}
			}
			else // one child, find out which and swap with node.
			{
				System.out.println("One child");
				deleteNodeWithOneChild(node);
			}
		}
	}

	// This is used when deleting a node. This node gets switched with the
	// node its deleting.
	private RBKnoop<E> getLargestInLeftSubtree(RBKnoop <E> node)
	{
		RBKnoop <E> tree = (RBKnoop)node.getLeftChild();
		if (tree == null)
		{
			return node;
		}
		else
		{
			while (true)
			{
				//System.out.println(tree.get());

				RBKnoop<E> rightChildOfTree = (RBKnoop)tree.getRightChild();
				RBKnoop<E> leftChildOfTree  = (RBKnoop)tree.getLeftChild();

				if(rightChildOfTree.get() == null && leftChildOfTree.get() == null)
				{
					return tree;
				}

				// One is not null
				if (rightChildOfTree.get() != null)
				{
					tree = rightChildOfTree;
				}
				else
				{
					tree = leftChildOfTree;
				}
			}
		}
	}

	private RBKnoop<E> getSmallestInRightSubtree(RBKnoop <E> node)
	{
		RBKnoop <E> tree = (RBKnoop)node.getRightChild();
		if (tree == null)
		{
			return node;
		}
		else
		{
			while (true)
			{
				System.out.println(tree.get());

				RBKnoop<E> rightChildOfLeftTree = (RBKnoop)tree.getRightChild();
				RBKnoop<E> leftChildOfLeftTree  = (RBKnoop)tree.getLeftChild();

				if(rightChildOfLeftTree.get() == null && leftChildOfLeftTree.get() == null)
				{
					return tree;
				}

				// One is not null
				if (leftChildOfLeftTree.get() != null)
				{
					tree = leftChildOfLeftTree;
				}
				else
				{
					tree = rightChildOfLeftTree;
				}
			}
		}
	}

	private void deleteNodeWithOneChild(RBKnoop<E> node)
	{
		RBKnoop<E> child;
		if (node.getLeftChild().get() != null)
		{
			child = (RBKnoop)node.getLeftChild();
		}
		else
		{
			child = (RBKnoop)node.getRightChild();
		}

		RBKnoop<E> parent = (RBKnoop)node.getParent();

		if (node == parent.getLeftChild())
		{
			node.remove(child);
			parent.remove(node);
			parent.insert(child, BKnoop.LEFT);
		}
		else
		{
			node.remove(child);
			parent.remove(node);
			parent.insert(child, BKnoop.RIGHT);
		}

		if (node.isBlack())
		{
			if (child.isRed())
			{
				child.makeBlack();
			}
			else
			{
				deleteValidator(child);
			}
		}
	}

	private void deleteValidator(RBKnoop<E> node)
	{
		System.out.println("validating node: " + node);

		//Case one: node is the new root, in that case we are done.

		RBKnoop<E> parent = (RBKnoop)node.getParent();
		if (parent != null)
		{
			System.out.println("case one");

			// Case two: sibling is red. Reverse the colors of parent and sibling, and then rotate parent left.
			// Turning sibling into node's grandparent.

			RBKnoop<E> sibling = getSibling(node);

			if (sibling.isRed())
			{
				System.out.println("case two");
				parent.makeRed();
				sibling.makeBlack();

				if (node == parent.getLeftChild())
				{
					leftRotation(parent);
				}
				else
				{
					rightRotation(parent);
				}
			}

			// Case three: parent, sibling, and siblings's children are black. Make sibling red.
			
			// rebind rotation could have changed it.
			parent  = (RBKnoop)node.getParent();
			sibling = getSibling(node);
			
			RBKnoop<E> siblingLeftChild		= (RBKnoop)sibling.getLeftChild();
			RBKnoop<E> siblingRightChild	= (RBKnoop)sibling.getRightChild();

			if(parent.isBlack() && sibling.isBlack() && siblingLeftChild.isBlack() && siblingLeftChild.isBlack())
			{
				System.out.println("case three");
				sibling.makeRed();
				deleteValidator(parent);
			}
			else
			{
				// Case four: sibling and children are black, but parent is red.
				// In this case, we simply exchange the colors of sibling and parent

				// Rebind deleteValidator could have changed it
				parent				= (RBKnoop)node.getParent();
				sibling				= getSibling(node);
				siblingLeftChild	= (RBKnoop)sibling.getLeftChild();
				siblingRightChild	= (RBKnoop)sibling.getRightChild();

				if (parent.isRed() && sibling.isBlack() && siblingLeftChild.isBlack() && siblingLeftChild.isBlack())
				{
					System.out.println("case four");
					sibling.makeRed();
					parent.makeBlack();
				}
				else
				{
					
					// Case five: sibling is black, sibling's left child is red and right child is black.
					// Node is the left child of its parent. In this case we rotate sibling right.
					// So that siblings left child becomes siblings parent and nodes new sibling.
					// We then exchange the colors of S and its new parent.

					if (sibling.isBlack())
					{
						System.out.println("case five");
						if (node == parent.getLeftChild() && siblingLeftChild.isRed() && siblingRightChild.isBlack())
						{
							System.out.println("case five red black");
							sibling.makeRed();
							siblingLeftChild.makeBlack();
							rightRotation(sibling);
						}
						else if (node == parent.getRightChild() && siblingLeftChild.isBlack() && siblingRightChild.isRed())
						{
							System.out.println("case five black red");
							sibling.makeRed();
							siblingRightChild.makeBlack();
							leftRotation(sibling);
						}
					}

					System.out.println("case six");
					// Case six: sibling is black, siblings right child is red.
					// Node is the left child of its parent. In this case we left rotate parent.
					// So that sibling becomes the parent of parent and siblings right child.
					// We then exchange the colors of parent and sibling, and make sibling's right child black.

					// Rebind rotation occurred
					parent = (RBKnoop)node.getParent();
					sibling = getSibling(node);
					siblingLeftChild		= (RBKnoop)sibling.getLeftChild();
					siblingRightChild	= (RBKnoop)sibling.getRightChild();

					sibling.setColor(parent.getColor());
					parent.makeBlack();

					if (node == parent.getLeftChild())
					{
						System.out.println("case six left");
						siblingRightChild.makeBlack();
						leftRotation(parent);
					}
					else
					{
						System.out.println("case six right");
						siblingLeftChild.makeBlack();
						rightRotation(parent);
					}
				}
			}
		}
	}

	private RBKnoop<E> getGrandParent(RBKnoop<E> node)
	{
		RBKnoop<E> parent = (RBKnoop)node.getParent();
		
		if (parent != null)
		{
			return (RBKnoop)parent.getParent();
		}
		
		return null;
	}
	
	private RBKnoop<E> getUncle(RBKnoop<E> node)
	{
		RBKnoop<E> parent		= (RBKnoop)node.getParent();
		RBKnoop<E> grandParent  = getGrandParent(node);

		if(grandParent != null)
		{
			if (grandParent.getLeftChild() == parent)
			{
				return (RBKnoop)grandParent.getRightChild();
			}
			else
			{
				return (RBKnoop)grandParent.getLeftChild();
			}
		}

		return null;
	}

	private RBKnoop<E> getSibling(RBKnoop<E> node)
	{
		RBKnoop<E> parent = (RBKnoop)node.getParent();

		if (node == parent.getLeftChild())
		{
			return (RBKnoop)parent.getRightChild();
		}
		else
		{
			return (RBKnoop)parent.getLeftChild();
		}
	}
}
