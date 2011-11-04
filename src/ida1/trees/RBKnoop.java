package ida1.trees;

/**
 * Minor color additions to the nodes for the red black tree.
 *
 * @author maartenhus
 */
public class RBKnoop<E> extends BKnoop<E>
{
	private int color;
	public static final int BLACK	= 0;
	public static final int RED		= 1;

	public RBKnoop(E userObject, int color)
	{
		super (userObject);
		this.color = color;

		add(new RBKnoop());
		add(new RBKnoop());
	}

	// RBKnoop has two children that are null nodes that are black.
	private RBKnoop()
	{
		super(null);
		this.color = BLACK;
	}
	
	public int getColor()
	{
		return color;
	}

	public void setColor (int color)
	{
		this.color = color;
	}

	public void makeBlack()
	{
		color = BLACK;
	}

	public void makeRed()
	{
		color = RED;
	}

	public boolean isBlack()
	{
		return color == BLACK;
	}

	public boolean isRed()
	{
		return color == RED;
	}

	public String toString()
	{
		// The null nodes that are children by default
		if (get() == null)
		{
			return "";
		}

		if (isBlack())
		{
			return "B:" + get().toString();
		}
		else
		{
			return "R:" + get().toString();
		}
	}
}
