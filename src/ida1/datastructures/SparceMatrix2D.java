package ida1.datastructures;

/**
 *
 * @author maartenhus
 */
public class SparceMatrix2D<E>
{
    private MatrixList<E> matrix;

	// They start counting at zero;
	final int columns;
	final int rows;

	public SparceMatrix2D(int columns, int rows)
	{
		this.columns = columns;
		this.rows = rows;
        buildMatrix();
	}

	// array[columns][rows]
	public SparceMatrix2D (E[][] array)
	{
		columns = array.length - 1;
		rows	= array[0].length -1;

		System.out.println("columns: " + columns +" rows: " + rows);

        buildMatrix();
		for (int x = 0; x < array.length; x++)
		{
			for (int y = 0; y < array[x].length; y++)
			{
				matrix.add(x, y, array[x][y]);
			}
		}
	}

    private void buildMatrix()
    {
        matrix = new MatrixList<E>(rows, columns);
    }

	public void add (int x, int y, E element)
	{
		validateInput(x, y);

		if (element != null)
		{
            matrix.add(x, y, element);
//			hashMap.put(new Coordinate(x, y), element);
		}
	}

	public E get (int x, int y)
	{
		validateInput(x, y);

        return matrix.get(x, y);
//		return hashMap.get(new Coordinate(x, y));
	}

	public E remove(int x, int y)
	{
		validateInput(x, y);
        return matrix.remove(x, y);
//		return hashMap.remove(new Coordinate(x, y));
	}

    public boolean validateInput(int x, int y)
    {
		// Columns start counting at zero
		if (x < 0 || x > columns)
		{
			throw new IllegalArgumentException("x is smaller that zero or bigger that the amount of columns");
		}

		// Rows start counting at zero
		if (y < 0 || y > rows)
		{
			throw new IllegalArgumentException("y is smaller that zero or bigger that the amount of rows");
		}
        return true;
    }

//	public String toString()
//	{
//		// Rows / columns start counting at zero so add one;
//
//		int columnLength = columns + 1;
//		int rowLength = rows + 1;
//
//		//System.out.println("columns: " + columnLength +" rows: " + rowLength);
//
//		StringBuffer buffer = new StringBuffer();
//
//		for (int x = 0; x < columnLength; x++)
//		{
//			for (int y = 0; y < rowLength; y++)
//			{
//				E element = get(x, y);
//
//				//System.out.println("x: " + x + " y: " + y + " element: " + element);
//
//				if (element == null)
//				{
//					buffer.append(" null ");
//				}
//				else
//				{
//					buffer.append(element.toString());
//				}
//			}
//
//			buffer.append("\n");
//		}
//
//		return buffer.toString();
//	}

    public void printRows()
    {
        matrix.printMatrixByRows();
    }


    public E getFromColumn(int col, int row)
    {
        return matrix.getFromColumn(col, row);
    }

	public static void testWithInteger()
	{
		SparceMatrix2D<Integer> matrix = new SparceMatrix2D<Integer>(2, 2);

		System.out.println("Testing matrix add with Integer");
		matrix.add(0, 0, 1);
		System.out.println("(0,0) -> 1 got: " + matrix.get(0, 0));
		matrix.add(0, 1, 3);
		System.out.println("(0,1) -> 3 got: " + matrix.get(0, 1));
		System.out.println("--column(0,1) -> 3 got: " + matrix.getFromColumn(0, 1));
		matrix.add(1, 2, 3);
		System.out.println("(1,2) -> 3 got: " + matrix.get(1, 2));
		matrix.add(0, 2, 1);
		System.out.println("(0,2) -> 1 got: " + matrix.get(0, 2));
		matrix.add(1, 0, 4);
		System.out.println("(1,0) -> 4 got: " + matrix.get(1, 0));
		matrix.add(1, 1, 6);
		System.out.println("(1,1) -> 6 got: " + matrix.get(1, 1));
		matrix.add(0, 0, 2);
		System.out.println("(0,0) -> 2 got: " + matrix.get(0, 0));
		System.out.println("(0,1) -> 3 got: " + matrix.get(0, 1));
		System.out.println("(0,2) -> 1 got: " + matrix.get(0, 2));
		System.out.println("(1,1) -> 6 got: " + matrix.get(1, 1));
//		System.out.println("(3,3) -> illegal got: " + matrix.get(3, 3));

		System.out.println("\nTesting remove");
		System.out.println("Expecting 4 got: " + matrix.remove(1, 0));
		System.out.println("Expecting null got: " + matrix.remove(2, 2));
		matrix.add(1, 0, 4);
		System.out.println("(1,0) -> null got: " + matrix.get(1, 0));

//        matrix.printRows();
	}

    public static void testWithString()
    {
		SparceMatrix2D<String> matrix = new SparceMatrix2D<String>(2, 2);

		System.out.println("Testing matrix add with String");
		matrix.add(0, 0, "test");
		System.out.println("(0,0) -> test got: " + matrix.get(0, 0));
		matrix.add(1, 0, "Hello");
		System.out.println("(1,0) -> Hello got: " + matrix.get(1, 0));
		matrix.add(1, 2, "Wurd");
		System.out.println("(1,2) -> Wurd got: " + matrix.get(1, 2));
		matrix.add(0, 0, "World");
		System.out.println("(0,0) -> World got: " + matrix.get(0, 0));
    }

    public static void main(String[] args)
    {
        testWithInteger();
        testWithString();
    }
}