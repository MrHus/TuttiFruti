package ida1.trees;

/**
 *
 * @author maartenhus
 */
public class BinaryTree <E extends Comparable<E>> extends Tree<E>
{
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

	public static void test()
	{
		BinaryTree<Integer> tree = new BinaryTree<Integer>();

		tree.insert(1);
		tree.insert(9);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(0);
		tree.insert(10);

		System.out.println("Following lines should be the same");
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("1 0 9 3 4 5 6 10");

		System.out.println("\ndelete 6");
		tree.delete(6);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("1 0 9 3 4 5 10");

		System.out.println("\ndelete 0");
		tree.delete(0);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("1 9 3 4 5 10");

		System.out.println("\ndelete 9");
		tree.delete(9);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("1 5 3 4 10");

		System.out.println("\ndelete 1");
		tree.delete(1);
		System.out.println(tree.getRoot().preOrderToString());
		System.out.println("5 3 4 10");

		System.out.println("\ncontains tester");
		System.out.println("Contains: 15 - " + tree.contains(15));
		System.out.println("Contains: 5  - " + tree.contains(5));
		System.out.println("Contains: 6  - " + tree.contains(6));
		System.out.println("Contains: 2  - " + tree.contains(2));
		System.out.println("Contains: 3  - " + tree.contains(3));
		System.out.println("Contains: 10 - " + tree.contains(10));


		System.out.println("\nshow in order");
		System.out.println(tree.getRoot().inOrderToString());

	}
}
