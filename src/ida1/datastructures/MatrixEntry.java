package ida1.datastructures;

/**
 *
 * @author Cornel Alders
 */
public class MatrixEntry<E>
{
    private int col;
    private int row;
    private E data;
    private MatrixEntry<E> nextCol;
    private MatrixEntry<E> previousCol;
    private MatrixEntry<E> nextRow;
    private MatrixEntry<E> previousRow;

    public MatrixEntry(int col, int row, E data)
    {
        this.col = col;
        this.row = row;
        this.data = data;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }
    
    public E getData()
    {
        return data;
    }

    public void setData(E data)
    {
        this.data = data;
    }

    public MatrixEntry<E> getNextColumn()
    {
        return this.nextCol;
    }

    public void setNextColumn(MatrixEntry<E> e)
    {
        this.nextCol = e;
    }

    public MatrixEntry<E> getPreviousColumn()
    {
        return this.previousCol;
    }

    public void setPreviousColumn(MatrixEntry<E> e)
    {
        this.previousCol = e;
    }

    public MatrixEntry<E> getNextRow()
    {
        return this.nextRow;
    }

    public void setNextRow(MatrixEntry<E> e)
    {
        this.nextRow = e;
    }

    public MatrixEntry<E> getPreviousRow()
    {
        return this.previousRow;
    }

    public void setPreviousRow(MatrixEntry<E> e)
    {
        this.previousRow = e;
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

        MatrixEntry<E> other = (MatrixEntry<E>) obj;
        return  other.getCol() == this.getCol() && other.getRow() == this.getRow();
    }

    @Override
    public int hashCode()
    {
        int tmpX = (col == 0) ? 1 : col;
        int tmpY = (row == 0) ? 1 : row;

        return tmpX + tmpY;
    }

    public String toString()
    {
        return "(" + col + "," + row + ")";
    }
}
