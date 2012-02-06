package ida1.datastructures;

import java.util.Iterator;

/**
 *
 * @author Cornel Alders
 */
public class MatrixList<E> implements Iterable<E>
{
    private MatrixEntry<E> rowHeader;
	private MatrixEntry<E> colHeader;
	private int rowSize;
    private int colSize;

    public MatrixList(int numCols, int numRows)
    {
        clear();
        colSize = numCols;
        rowSize = numRows;
    }

    public MatrixList(int numCols, int numRows, String file)
    {
        //Read matrix out of file.
    }

    public void add(int col, int row, E data)
    {
        boolean replaced = false;
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, data);
        MatrixEntry<E> currentRow = rowHeader;

		while(currentRow != null)
        {
            if(currentRow.equals(el))
            {
//                System.out.println("replace " + currentRow.getData() + " with " + data + " on pos " + currentRow);
                currentRow.setData(data);
                replaced = true;
                break;
            }
            currentRow = currentRow.getNextRow();
        }

        if(!replaced)
        {
//            System.out.println("add " + el.getData() + " on pos " + el);
            rowHeader.setNextRow(el);
            el.setPreviousRow(rowHeader);
        }
    }

    public boolean contains(MatrixEntry<E> el)
    {
        MatrixEntry<E> currentRow = rowHeader;
		while(currentRow != null)
        {
            if(currentRow.equals(el))
            {
                return true;
            }
            currentRow = currentRow.getNextRow();
        }
        return false;
    }

    public E get(int col, int row)
    {
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, null);
        MatrixEntry<E> currentRow = rowHeader;

		while(currentRow != null)
        {
            if(currentRow.equals(el))
            {
                return currentRow.getData();
            }
            currentRow = currentRow.getNextRow();
        }
        return null;
    }

    public E remove(int col, int row)
    {
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, null);
        MatrixEntry<E> currentRow = rowHeader;
		while(currentRow != null)
        {
            if(currentRow.equals(el))
            {
                currentRow.getPreviousRow().setNextRow(currentRow.getNextRow());
                currentRow.getPreviousColumn().setNextColumn(currentRow.getNextColumn());
                currentRow.getNextRow().setNextRow(currentRow.getPreviousRow());
                currentRow.getNextColumn().setNextColumn(currentRow.getPreviousColumn());
                return currentRow.getData();
            }
            currentRow = currentRow.getNextRow();
        }
        return null;
    }

    public int getColSize()
    {
        return colSize;
    }

    public int getRowSize()
    {
        return rowSize;
    }

    public boolean validateInput(int col, int row)
    {
		if (col < 0 || col > colSize)
		{
			throw new IllegalArgumentException("x is smaller that zero or bigger that the amount of columns");
		}

		if (row < 0 || row > rowSize)
		{
			throw new IllegalArgumentException("y is smaller that zero or bigger that the amount of row");
		}
        return true;
    }

	public void clear()
	{
		rowHeader = new MatrixEntry<E>(0, 0, null);
	    rowHeader.setNextRow(null);
	    rowHeader.setPreviousRow(null);
	    rowHeader.setNextColumn(null);
	    rowHeader.setPreviousColumn(null);

		colHeader = new MatrixEntry<E>(0, 0, null);
	    colHeader.setNextRow(null);
	    colHeader.setPreviousRow(null);
	    colHeader.setNextColumn(null);
	    colHeader.setPreviousColumn(null);
	}

    //Implementation probably incorrect!
	public Iterator<E> iterator()
	{
		return new MItr<E>();
	}

    //Should be updated to iterate over all col and row entries.
	private class MItr<E> implements Iterator<E>
	{
	  	private MatrixEntry<E> rowPos = (MatrixEntry<E>) rowHeader;
        private MatrixEntry<E> colPos = (MatrixEntry<E>) colHeader;

	  	public boolean hasNext()
	  	{
	    	return rowPos.getNextRow() != null;
	  	}

	  	public E next()
	  	{
	    	rowPos = rowPos.getNextRow();
	    	return rowPos.getData();
	  	}

	  	public boolean hasPrevious()
	  	{
	    	return rowPos.getPreviousRow() != null;
	  	}

	  	public E previous()
	  	{
	    	rowPos = rowPos.getPreviousRow();
	    	return rowPos.getData();
	  	}

		public void remove()
		{
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
