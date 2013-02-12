/*
 * @author Ivan Ramos Pagnossin
 * Email: ivan.pagnossin@gmail.com
 * Date: 2013.02.11
 * Description: solution to exercise 1 of programming assignment,
 * about the percolation model of a N-by-N grid.
 */
public class Percolation {

    private boolean [] openGrid;
    private boolean [] fullGrid;
    private WeightedQuickUnionUF uf;
    private int n;
    
    /**
     * create N-by-N grid, with all sites blocked.
     * TODO: what to do when N = 1? And when N < 1?
     */
    public Percolation(int N) {
        
        int size = N * N;
        
        openGrid = new boolean[size];
        fullGrid = new boolean[size];
        
        for (int x = 0; x < size; ++x) {
            openGrid[x] = false;
            fullGrid[x] = false;
        }
        
        uf = new WeightedQuickUnionUF(size);
        
        n = N;        
    }
    
    /*
     * Open site (row,column) = (i,j) if it is not already opened.
     */
    public void open(int i, int j) {
        
        // Internal one-dimentional coordinate x
        int x = toX(i, j);
        
        // It opens (i,j)
        openGrid[x] = true;
        requestFill(i, j);
        
        // Connect to left neighbour, if it is opened
        if (i > 1 && isOpen(i-1, j)) {
            int neighbour = toX(i-1, j);
            uf.union(x, neighbour);
            requestFill(i-1, j);
        }
        
        // Connect to right neighbour, if it is opened
        if (i < n && isOpen(i+1, j)) {
            int neighbour = toX(i+1, j);
            uf.union(x, neighbour);
            requestFill(i+1, j);
        }
        
        // Connect to up neighbour, if it is opened
        if (j > 1 && isOpen(i, j-1)) {
            int neighbour = toX(i, j-1);
            uf.union(x, neighbour);
            requestFill(i, j-1);
        }
        
        // Connect to bottom neighbour, if it is opened
        if (j < n && isOpen(i, j+1)) {
            int neighbour = toX(i, j+1);
            uf.union(x, neighbour);
            requestFill(i, j+1);
        }
    }
    
    /**
     * Is site (row, column) = (i, j) open?
     */
    public boolean isOpen(int i, int j) {
        return openGrid[toX(i, j)];
    }
    
    /**
     * Is site (row, column) = (i, j) full?
     */
    public boolean isFull(int i, int j) {
        return fullGrid[toX(i, j)];
    }
    
    /**
     * Does the system percolate? 
     */
    public boolean percolates() {
        
        boolean percolates = false;
        
        for (int j1 = 1; j1 <= n && !percolates; ++j1) {
            if (isOpen(n, j1)) {
                int x1 = toX(n, j1);
                for (int j2 = 1; j2 <= n && !percolates; ++j2) {
                    int x2 = toX(1, j2);
                    if (uf.connected(x1, x2)) percolates = true;
                }
            }
        }
        
        return percolates;
    }
    
    /**
     * @private
     * i,j \in [1..N] -> x \in 0..(N^2-1) 
     */
    private int toX(int i, int j) {
        return (i - 1) * n + j - 1;
    }
    
    /**
     * @private
     * Fills site (row, column) = (i,j) if it is opened and
     * connected to any site at the top.
     */
    private boolean requestFill(int i, int j) {
              
        boolean ans = false;
       
        if (isOpen(i, j) && !isFull(i, j)) {
            if (i == 1) {
                ans = true;
            }
            else {
                int x = toX(i, j);
                for (int c = 1; c <= n && !ans; ++c) {
                    if (uf.connected(x, toX(1, c))) ans = true;
                }
            }
        }    
        
        if (ans) fullGrid[toX(i, j)] = true;
        
        return ans;
    }
}
