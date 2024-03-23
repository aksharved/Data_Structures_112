package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int rows = StdIn.readInt();
        int columns = StdIn.readInt();
        this.grid = new boolean [rows][columns];
        for (int i =0; i < rows; i++)
        {
            for (int j =0; j < columns; j++)
            {
                this.grid[i][j] = StdIn.readBoolean();
            }
        }
     

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

      
        // WRITE YOUR CODE HERE
        
        if (this.grid[row][col] == true )
            return true; // update this line, provided so that code compiles
        else   
            return false;
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        for (int i = 0; i< grid.length; i++)
        {
            for (int j =0; j<grid[0].length; j++)
            {
                if (getCellState(i, j) == true)
                {
                    return true;
                }
            }
        }
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE index - 1, add the total length of the column, modulus % row 
        int counter = 0;
         
        for (int i  = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                int num1 = row+i;
                int num2 = col+j;
                if (num1 < 0)
                {
                    num1 += grid.length;
                }
                if (num2 < 0)
                {
                    num2 += grid[0].length;
                }
                if (num1 >= grid.length)
                {
                    num1 -= grid.length;
                }
                if (num2 >= grid[0].length)
                {
                    num2 -= grid[0].length;
                }
                if (grid[num1][num2] && (i!=0 || j!= 0))
                {
                    counter++;
                }
            }
        }
        return counter; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        // WRITE YOUR CODE HERE
        boolean grid2 [][] = new boolean [grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j<grid[0].length;j++)
            {
                if (getCellState(i, j) == true)
                {
                    if (numOfAliveNeighbors(i, j) <= 1)
                    {
                        grid2[i][j] =  false;
                    }
                    else if ((numOfAliveNeighbors(i, j) <= 3) && (numOfAliveNeighbors(i, j) > 1))
                    {
                        grid2[i][j] = true;
                    }
                    else 
                    {
                        grid2[i][j] = false;
                    }

                }
                else
                {
                    if (numOfAliveNeighbors(i, j) == 3)
                    {
                        grid2[i][j] =  true;
                    }

                }

            }
        }
        return grid2;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () 
    {

        // WRITE YOUR CODE HERE
        boolean [][] newGrid = computeNewGrid();
        grid = newGrid;
        totalAliveCells = 0; 
        for (int i = 0; i < grid.length; i++)
        {
            for (int j =0; j < grid[0].length; j++)
            { 
                if (newGrid[i][j] == true)  
                {
                    totalAliveCells++;
                }
                
            }
        }
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for (int i = 0; i < n; i++)
        {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF obj = new WeightedQuickUnionUF(grid.length, grid[0].length);
        for (int s = 0; s < grid.length; s++)
        {
            for (int l = 0; l < grid[0].length; l++)
            {
                if(grid[s][l]){
                    for (int i  = -1; i <= 1; i++)
                    {
                        for (int j = -1; j <= 1; j++)
                        {
                            int num1 = s+i;
                            int num2 = l+j;
                            if (num1 < 0)
                            {
                                num1 += grid.length;
                            }
                            if (num2 < 0)
                            {
                                num2 += grid[0].length;
                            }
                            if (num1 >= grid.length)
                            {
                                num1 -= grid.length;
                            }
                            if (num2 >= grid[0].length)
                            {
                                num2 -= grid[0].length;
                            }
                            if (grid[num1][num2] && (i!=0 || j!= 0))
                            {
                                obj.union(s, l, num1, num2); 
                            }
                        }
                    }
                }

            }

        }
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < grid.length; k++)
        {
            for (int a = 0; a < grid[0].length; a++)
            { 
               
                if(grid[k][a] == true)  
                {
                    if (list.indexOf(obj.find(k,a))==-1)
                    {
                        list.add(obj.find(k, a));
                    } 
                    
                }    
               
            }
        
        }

        return list.size(); // update this line, provided so that code compiles
    }


}
