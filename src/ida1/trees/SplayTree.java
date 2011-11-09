package ida1.trees;

/**
 * Wiki: http://en.wikipedia.org/wiki/Splay_tree
 *
 * Hurray for variation, there are multiple ways a delete can be implemented.
 * Use this applet to visualize the current implementation:
 *  http://techunix.technion.ac.il/~itai/
 * Use this to see the difference: 
 *  http://www.cse.yorku.ca/~aaw/Sotirios/SplayTree.html
 *
 * Follow the testcase and notice the big difference when deleting 4/6.
 * The key difference lies in the node to splay after a node with 2 childs is
 * to be deleted. The current implementation uses the LargestValueInLeftSubtree.
 * The other uses the LowestValueInRightSubtree (not implemented).
 *
 * @author Cornel Alders, Maarten Hus
 */
public class SplayTree<E extends Comparable<E>> extends Tree<E>
{
    public void splay(BKnoop<E> node)
    {
        BKnoop<E> parent = node.getParent();

//        System.out.println(node);
//        System.out.println("Splaying: " + node.toString());
//        System.out.println("Parent: " + node.getParent());

        if(node == root)
        {
            return;
        }
        else if(parent == root) //Perform a zig operation
        {
            if(node == parent.getLeftChild())
            {
                rightRotation(parent);
            }
            else
            {
                leftRotation(parent);
            }
        }
        else //Perform a zig-zig or zig-zag
        {
            BKnoop<E> grandParent = parent.getParent();
            /*Performs the Zig-zig step when node is left and parent is left*/
            if(node == parent.getLeftChild() && parent == grandParent.getLeftChild())
            {
                rightRotation(grandParent);
                rightRotation(parent);
            }
            /*Performs the Zig-zig step when node is right and parent is right*/
            else if(node == parent.getRightChild() && parent == grandParent.getRightChild())
            {
                leftRotation(grandParent);
                leftRotation(parent);
            }
            /*Performs the Zig-zag step when node is right and parent is left*/
            else if(node == parent.getRightChild() && parent == grandParent.getLeftChild())
            {
                leftRotation(parent);
                rightRotation(grandParent);
            }
            /*Performs the Zig-zag step when node is left and parent is right*/
            else if(node == parent.getLeftChild() && parent == grandParent.getRightChild())
            {
                rightRotation(parent);
                leftRotation(grandParent);
            }
        }
        splay(node);
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

		if (comp <= -1)
		{
			//System.out.println("Comp -1");

			if(node.getLeftChild() == null)
			{
//				System.out.println("Insert left");
                BKnoop<E> newNode = new BKnoop<E>(element);
				node.insert(newNode, BKnoop.LEFT);
                splay(newNode);
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
//				System.out.println("Insert right");
                BKnoop<E> newNode = new BKnoop<E>(element);
				node.insert(newNode, BKnoop.RIGHT);
                splay(newNode);
			}
			else
			{
				insert(element, node.getRightChild());
			}
		}
        else
        {
            splay(node);
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

		if (comp <= -1)
		{
			//System.out.println("Comp -1");
            if(node.getLeftChild() == null)
            {
//                System.out.println("Left child is null");
                splay(node);
                return;
            }
            else
            {
                delete(element, node.getLeftChild());
            }
		}
		else if (comp >= 1)
		{
			//System.out.println("Comp 1");
            if(node.getRightChild() == null)
            {
//                System.out.println("Right child is null");
                splay(node);
                return;
            }
            else
            {
                delete(element, node.getRightChild());
            }
		}
		else // Element is found
		{
			//System.out.println("Node found: " + node.get());

			if (node.getLeftChild() == null && node.getRightChild() == null) // node is leaf
			{
//                System.out.println("It is leaf.");
                BKnoop<E> parent = node.getParent();
				if (parent != null)
				{
					parent.remove(node);
				}
                splay(parent);
			}
			else if (node.getLeftChild() != null && node.getRightChild() != null) // Two children
			{
//                System.out.println("It has 2 children.");
				BKnoop<E> replacement = getLargestInLeftSubtree(node);

//				System.out.println("replacement: " + replacement);

				BKnoop<E> parent		= node.getParent();
				BKnoop<E> leftChild		= node.getLeftChild();
				BKnoop<E> rightChild	= node.getRightChild();
                //We will splay on the parent of the replacement.
                BKnoop<E> replacementParent = replacement.getParent();

//				System.out.println("Parent: " + parent);
//				System.out.println("LeftChild: " + leftChild);
//				System.out.println("RightChild: " + rightChild);
//                System.out.println("ReplacementParent: " + replacementParent);

				node.remove(leftChild);
				node.remove(rightChild);

				if(replacementParent != null)
				{
					replacementParent.remove(replacement);
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
                splay(replacementParent);
			}
			else // one child, find out which
			{
//                System.out.println("Got a child");

				if (node.getLeftChild() != null)
				{
                    BKnoop<E> child = node.getLeftChild();
//                    System.out.println("WithOneLeftChild");
					switchNodeForDeleteWithOneChild(node, BKnoop.LEFT);
                    splay(child);
				}
				else
				{
//                    System.out.println("WithOneRightChild");
					switchNodeForDeleteWithOneChild(node, BKnoop.RIGHT);
				}
			}
		}
	}

    @Override
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

		if (comp <= -1)
		{
			if(node.getLeftChild() == null)
			{
                splay(node);
				return false;
			}
			else
			{
				return contains(element, node.getLeftChild());
			}
		}
		else if (comp >= 1)
		{
			if(node.getRightChild() == null)
			{
                splay(node);
				return false;
			}
			else
			{
				return contains(element, node.getRightChild());
			}
		}
		else // Element is found
		{
            splay(node);
			return true;
		}
	}

	public static void test()
	{
		SplayTree<Integer> tree = new SplayTree<Integer>();

        System.out.println("Filling Tree.");
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
		System.out.println("10 0 9 6 4 1 3 5 ");

        System.out.println("Find 4");
        tree.contains(4);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("4 0 1 3 10 6 5 9 ");

        System.out.println("insert 7");
        tree.insert(7);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("7 4 0 1 3 6 5 10 9 ");

        System.out.println("Find 8");
        tree.contains(8);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("9 7 4 0 1 3 6 5 10 ");

        System.out.println("Delete 4"); // <---
        tree.delete(4);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("1 0 7 3 6 5 9 10 ");

        System.out.println("Delete 6");
        tree.delete(6);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("5 1 0 3 7 9 10 ");

        System.out.println("Delete 2");
        tree.delete(2);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("3 1 0 5 7 9 10 ");

        System.out.println("Delete 5");
        tree.delete(5);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("3 1 0 7 9 10 ");

        System.out.println("Find 10");
        tree.contains(10);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("10 3 1 0 9 7 ");

        System.out.println("Delete 1");
        tree.delete(1);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("0 3 10 9 7 ");

        System.out.println("Find 7");
        tree.contains(7);
        System.out.println("Following lines should be the same");
        System.out.println(tree.getRoot().preOrderToString());
        System.out.println("7 3 0 9 10 ");
	}

    public static void main(String[] args)
    {
        test();
    }
}
