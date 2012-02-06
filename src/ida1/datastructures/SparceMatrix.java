package ida1.datastructures;

/**
 * This is a sparce matrix that uses a HashMap to save a two dimensional matrix.
 * The element that gets saved must be an instance of DefinesEmpty. Because
 * an empty element has no place in a sparce matrix :P
 *
 * @author maartenhus
 */
public class SparceMatrix<E extends DefinesEmpty>
{
	private HashMap<Coordinate, E> hashMap = new HashMap<Coordinate, E>();

	// They start counting at zero;
	final int columns;
	final int rows;

	public SparceMatrix(int columns, int rows)
	{
		this.columns = columns;
		this.rows = rows;
	}

	// array[columns][rows]
	public SparceMatrix (E[][] array)
	{
		columns = array.length - 1;
		rows	= array[0].length -1;

		System.out.println("columns: " + columns +" rows: " + rows);

		for (int x = 0; x < array.length; x++)
		{
			for (int y = 0; y < array[x].length; y++)
			{
				add(x, y, array[x][y]);
			}
		}
	}

	public void add (int x, int y, E element)
	{
		// Rows start counting at zero
		if (x < 0 || x > columns)
		{
			throw new IllegalArgumentException("x is smaller that zero or bigger that the amount of rows");
		}

		// Columns start counting at zero
		if (y < 0 || y > rows)
		{
			throw new IllegalArgumentException("y is smaller that zero or bigger that the amount of columns");
		}

		if (element.isEmpty() == false)
		{
			hashMap.put(new Coordinate(x, y), element);
		}
	}

	public E get (int x, int y)
	{
		if (x < 0 || x > columns)
		{
			throw new IllegalArgumentException("x is smaller that zero or bigger that the amount of rows");
		}

		if (y < 0 || y > rows)
		{
			throw new IllegalArgumentException("y is smaller that zero or bigger that the amount of columns");
		}

		return hashMap.get(new Coordinate(x, y));
	}

	public E remove(int x, int y)
	{
		if (x < 0 || x > columns)
		{
			throw new IllegalArgumentException("x is smaller that zero or bigger that the amount of rows");
		}

		if (y < 0 || y > rows)
		{
			throw new IllegalArgumentException("y is smaller that zero or bigger that the amount of columns");
		}

		return hashMap.remove(new Coordinate(x, y));
	}

	public String toString()
	{
		// Rows / columns start counting at zero so add one;
		
		int columnLength = columns + 1;
		int rowLength = rows + 1;

		//System.out.println("columns: " + columnLength +" rows: " + rowLength);

		StringBuffer buffer = new StringBuffer();
		
		for (int x = 0; x < columnLength; x++)
		{
			for (int y = 0; y < rowLength; y++)
			{
				E element = get(x, y);
				
				//System.out.println("x: " + x + " y: " + y + " element: " + element);

				if (element == null)
				{
					buffer.append(" null ");
				}
				else
				{
					buffer.append(element.toString());
				}
			}

			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * This internal class represents a coordinate (x,y)
	 * that is used as the key in the hashmap.
	 */
	private class Coordinate
	{
		private int x;
		private int y;

		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public int getX()
		{
			return x;
		}

		public void setX(int x)
		{
			this.x = x;
		}

		public int getY()
		{
			return y;
		}

		public void setY(int y)
		{
			this.y = y;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			if (this == null || getClass() != obj.getClass())
			{
				return false;
			}

			Coordinate other = (Coordinate) obj;
			return other.getX() == this.getX() && other.getY() == this.getY();
		}

		@Override
		public int hashCode()
		{
			int tmpX = (x == 0) ? 1 : x;
			int tmpY = (y == 0) ? 1 : y;

			return tmpX + tmpY;
		}

		public String toString()
		{
			return "(" + x + "," + y + ")";
		}
	}

	public static void test()
	{
		class IntWrapper implements DefinesEmpty
		{
			public final int value;

			public IntWrapper(int value)
			{
				this.value = value;
			}

			public boolean isEmpty()
			{
				return value == 0;
			}

			public int getValue()
			{
				return value;
			}

			public String toString()
			{
				return "" + value;
			}
		}

		SparceMatrix<IntWrapper> matrix = new SparceMatrix<IntWrapper>(2, 2);

		System.out.println("Testing matrix add");
		matrix.add(0, 0, new IntWrapper(1));
		matrix.add(1, 2, new IntWrapper(3));
		matrix.add(0, 0, new IntWrapper(2));

		System.out.println("(0,0) -> 2 got: " + matrix.get(0, 0).getValue());
		System.out.println("(1,2) -> 3 got: " + matrix.get(1, 2).getValue());
		//System.out.println("(3,3) -> illegal got: " + matrix.get(3, 3));


		System.out.println("\nTesting matrix add with array");
		IntWrapper[][] intMatrix = new IntWrapper[4][3];
		intMatrix[0][0] = new IntWrapper(0);
		intMatrix[0][1] = new IntWrapper(88);
		intMatrix[0][2] = new IntWrapper(0);

		intMatrix[1][0] = new IntWrapper(0);
		intMatrix[1][1] = new IntWrapper(0);
		intMatrix[1][2] = new IntWrapper(0);
		
		intMatrix[2][0] = new IntWrapper(27);
		intMatrix[2][1] = new IntWrapper(0);
		intMatrix[2][2] = new IntWrapper(0);
		
		intMatrix[3][0] = new IntWrapper(19);
		intMatrix[3][1] = new IntWrapper(0);
		intMatrix[3][2] = new IntWrapper(66);

		matrix = new SparceMatrix<IntWrapper>(intMatrix);		
		System.out.println(matrix);

		System.out.println("\nTesting remove");
		System.out.println("Expecting 19 got: " + matrix.remove(3, 0).getValue());

		System.out.println("\nTesting print again expect 19 to be removed");
		System.out.println(matrix);
	}

    public static void main(String[] args)
    {
        test();
    }
}
