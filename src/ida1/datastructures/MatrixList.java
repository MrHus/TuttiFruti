package ida1.datastructures;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Cornel Alders
 */
public class MatrixList<E> implements Iterable<E>
{
    private ArrayList<MatrixEntry<E>> rowMatrix;
    private ArrayList<MatrixEntry<E>> colMatrix; //Not used yet
    private MatrixEntry<E> rowHeader;
	private MatrixEntry<E> colHeader;
	private int rowSize;
    private int colSize;

    public MatrixList(int numCols, int numRows)
    {
//        clear();
        colSize = numCols;
        rowSize = numRows;
        colMatrix = new ArrayList<MatrixEntry<E>>();
        for(int i = 0; i <= colSize; i++)
        {
            colMatrix.add(i, new MatrixEntry<E>());
        }

        rowMatrix = new ArrayList<MatrixEntry<E>>();
        for(int i = 0; i <= rowSize; i++)
        {
            rowMatrix.add(i, new MatrixEntry<E>());
        }
    }

    public MatrixList(int numCols, int numRows, String file)
    {
        //Read matrix out of file.
    }

    public void add(int col, int row, E data)
    {
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, data);
        rowHeader = rowMatrix.get(row);
        colHeader = colMatrix.get(col);
        MatrixEntry<E> currentColumn = rowHeader;
        MatrixEntry<E> currentRow = colHeader;

        //Add new column entry to the row
            System.out.println("------------------- Add to row ---------------------");
        if(currentColumn.getNextColumn().equals(rowHeader))
        {
//            System.out.println("add after head " + el.getData() + " on pos " + el);
            el.setNextColumn(currentColumn);
            el.setPreviousColumn(currentColumn);
            currentColumn.setNextColumn(el);
            currentColumn.setPreviousColumn(el);
        }
        else
        {
//            System.out.println("------------------- " + el + " ---------------------");
            do
            {
//                System.out.println(el.getCol() + " > " + currentColumn.getCol() + " = " + (el.getCol() > currentColumn.getCol()));
//                System.out.println(el.getCol() + " < " + currentColumn.getNextColumn().getCol() + " = " + (el.getCol() < currentColumn.getNextColumn().getCol()));
//                System.out.println("end of list? " + currentColumn.getNextColumn().equals(rowHeader));
                if(currentColumn.equals(el))
                {
//                    System.out.println(el + " equals " + currentColumn + " = " + currentColumn.equals(el));
//                    System.out.println("replace " + currentColumn.getData() + " with " + data + " on pos " + currentColumn);
                    currentColumn.setData(data);
                    break;
                }
                else if(el.getCol() > currentColumn.getCol() && el.getCol() < currentColumn.getNextColumn().getCol() ||
                        currentColumn.getNextColumn().equals(rowHeader))
                {
//                    System.out.println("add " + el.getData() + " after pos " + currentColumn + " on " + el);
                    el.setNextColumn(currentColumn.getNextColumn());
                    el.setPreviousColumn(currentColumn);
                    currentColumn.setNextColumn(el);
                    break;
                }
//                System.out.println("---");
                currentColumn = currentColumn.getNextColumn();
            }
            while(currentColumn != rowHeader);
        }

            System.out.println("------------------- Add to column ---------------------");
        //Add a new row entry to the column
        if(currentRow.getNextRow().equals(colHeader))
        {
            System.out.println("add after head " + el.getData() + " on pos " + el);
            el.setNextRow(currentRow);
            el.setPreviousRow(currentRow);
            currentRow.setNextRow(el);
            currentRow.setPreviousRow(el);
        }
        else
        {
            System.out.println("------------------- " + el + " ---------------------");
            do
            {
                System.out.println(el.getRow() + " > " + currentRow.getRow() + " = " + (el.getRow() > currentRow.getRow()));
                System.out.println(el.getRow() + " < " + currentRow.getNextColumn().getRow() + " = " + (el.getRow() < currentRow.getNextRow().getRow()));
                System.out.println("end of list? " + currentRow.getNextRow().equals(colHeader));
                if(currentRow.equals(el))
                {
                    System.out.println(el + " equals " + currentRow + " = " + currentRow.equals(el));
                    System.out.println("replace " + currentRow.getData() + " with " + data + " on pos " + currentRow);
                    currentRow.setData(data);
                    break;
                }
                else if(el.getRow() > currentRow.getRow() && el.getRow() < currentRow.getNextRow().getRow() ||
                        currentRow.getNextRow().equals(colHeader))
                {
                    System.out.println("add " + el.getData() + " after pos " + currentRow + " on " + el);
                    el.setNextRow(currentRow.getNextRow());
                    el.setPreviousRow(currentRow);
                    currentRow.setNextRow(el);
                    break;
                }
                System.out.println("---");
                currentRow = currentRow.getNextRow();
            }
            while(currentRow != colHeader);
        }
    }

    //Not used yet, old and incorrect impl.
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
        rowHeader = rowMatrix.get(row);
        MatrixEntry<E> currentColumn = rowHeader;

//        System.out.println("rowHeader " + rowHeader);
		do
        {
//            System.out.println("currentRow " + currentColumn);
            if(currentColumn.equals(el))
            {
//                System.out.println("Found data " + currentColumn.getData());
                return currentColumn.getData();
            }
            currentColumn = currentColumn.getNextColumn();
        }
        while(currentColumn != rowHeader);
        return null;
    }

    public E getFromColumn(int col, int row)
    {
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, null);
        colHeader = colMatrix.get(col);
        MatrixEntry<E> currentRow = colHeader;

//        System.out.println("colHeader " + colHeader);
		do
        {
//            System.out.println("currentRow " + currentRow);
            if(currentRow.equals(el))
            {
//                System.out.println("Found data " + currentRow.getData());
                return currentRow.getData();
            }
            currentRow = currentRow.getNextRow();
        }
        while(currentRow != colHeader);
        return null;
    }

    public E remove(int col, int row)
    {
        validateInput(col, row);
        MatrixEntry<E> el = new MatrixEntry<E>(col, row, null);
        rowHeader = rowMatrix.get(row);
        MatrixEntry<E> currentColumn = rowHeader.getNextColumn();

		do
        {
//            System.out.println("Remove " + currentColumn + " if " + el);
            if(currentColumn.equals(el) && !currentColumn.equals(rowHeader))
            {
//                System.out.println("Remove " + currentColumn.getData());
//                currentRow.getPreviousRow().setNextRow(currentRow.getNextRow());
                currentColumn.getPreviousColumn().setNextColumn(currentColumn.getNextColumn());
//                currentRow.getNextRow().setPreviousRow(currentRow.getPreviousRow());
                currentColumn.getNextColumn().setPreviousColumn(currentColumn.getPreviousColumn());
                return currentColumn.getData();
            }
            currentColumn = currentColumn.getNextColumn();
        }
        while(currentColumn != rowHeader);
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
		rowHeader = new MatrixEntry<E>();
	    rowHeader.setNextRow(null);
	    rowHeader.setPreviousRow(null);
	    rowHeader.setNextColumn(null);
	    rowHeader.setPreviousColumn(null);

		colHeader = new MatrixEntry<E>();
	    colHeader.setNextRow(null);
	    colHeader.setPreviousRow(null);
	    colHeader.setNextColumn(null);
	    colHeader.setPreviousColumn(null);
	}

    //prints only first entries of each row for now.
    public void printMatrixByRows()
    {
        System.out.println("Sparse Matrix by Rows:");
        for(MatrixEntry<E> row : rowMatrix)
        {
            rowHeader = row;
            MatrixEntry<E> currentColumn = rowHeader.getNextColumn();

            do
            {
                System.out.print(currentColumn.getData() + " ");
                currentColumn = currentColumn.getNextColumn();
            }
            while(currentColumn.getNextColumn() != rowHeader);
            System.out.println("");
        }
//        for(int i = 0; i < numRows; i++)
//        {
//            for(int j = 0; j < numColumns; j++)
//            {
//                System.out.print(m.getByRow(i, j) + "\t");
//            }
//            System.out.println("[" + m.getNumElementsInRow(i) + "]");
//        }
//        for(int j = 0; j < numColumns; j++)
//        {
//            System.out.print("[" + m.getNumElementsInColumn(j) + "]\t");
//        }
//        System.out.println();
    }

    public void printMatrixByColumns()
    {
//        System.out.println("Sparse Matrix by Columns:");
//        int numRows = m.getNumRows();
//        int numColumns = m.getNumColumns();
//        for(int i = 0; i < numRows; i++)
//        {
//            for(int j = 0; j < numColumns; j++)
//            {
//                System.out.print(m.getByColumn(i, j) + "\t");
//            }
//            System.out.println("[" + m.getNumElementsInRow(i) + "]");
//        }
//        for(int j = 0; j < numColumns; j++)
//        {
//            System.out.print("[" + m.getNumElementsInColumn(j) + "]\t");
//        }
//        System.out.println();
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
