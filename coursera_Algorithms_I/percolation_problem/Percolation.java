/*
 * @author Ivan Ramos Pagnossin
 * @email: ivan.pagnossin@gmail.com
 * @version: 2013.02.11
 * @description: percolation model of a N-by-N grid.
 */
public class Percolation {

    private boolean [] openGrid; // Open sites
    private boolean [] fullGrid; // Full sites
    private WeightedQuickUnionUF uf; // Union-find associated to openGrid
    //private WeightedQuickUnionUF uf_full;
    private int n; // n-by-n grid
    private boolean percolates; // The grid percolates or not
    
    /**
     * Create an N-by-N grid, with all sites blocked.
     */
    public Percolation(int N) {
        
        if (N < 1) throw new IllegalArgumentException("N must be 1 or greater.");
        
        int size = N * N;
        
        openGrid = new boolean[size];
        fullGrid = new boolean[size];
        
        for (int x = 0; x < size; ++x) {
            openGrid[x] = false;
            fullGrid[x] = false;
        }
        
        uf = new WeightedQuickUnionUF(size);
        //uf_full = new WeightedQuickUnionUF(size);
        
        n = N;     
        
        percolates = false;
    }
    
    /*
     * Open site (row,column) = (i,j) if it is not already opened.
     */
    public void open(int i, int j) {
        
        int x = toX(i, j); // Internal one-dimentional coordinate x
        
        openGrid[x] = true;
        if (i == 1) fullGrid[x] = true;
        
        if (n == 1) {
            percolates = true;
        }
        else {
            /*
             * In the next four lines the current just opened site is connected to
             * any open neighbour. If any of these neighbours is also full, fill
             * the current site.
             */
            if (i > 1 && isOpen(i-1, j)) openAndMaybeFill(x, toX(i-1, j));
            if (i < n && isOpen(i+1, j)) openAndMaybeFill(x, toX(i+1, j));
            if (j > 1 && isOpen(i, j-1)) openAndMaybeFill(x, toX(i, j-1));
            if (j < n && isOpen(i, j+1)) openAndMaybeFill(x, toX(i, j+1));
            
            /*
             * In the next if-block all open neighbours are filled, iff current
             * site is just filled too (lines above).
             */
            if (isFull(i, j)) {
                if (i > 1 && isOpen(i-1, j)) propagateFull(toX(i-1, j), x);
                if (i < n && isOpen(i+1, j)) propagateFull(toX(i+1, j), x);
                if (j > 1 && isOpen(i, j-1)) propagateFull(toX(i, j-1), x);
                if (j < n && isOpen(i, j+1)) propagateFull(toX(i, j+1), x);
            }
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
        
        int x = toX(i, j);
        boolean ans = fullGrid[x];
            
        if (!ans) {
            ans = ((i > 1 && fullGrid[toX(i-1, j)] && uf.connected(x, toX(i-1, j)))
                || (i < n && fullGrid[toX(i+1, j)] && uf.connected(x, toX(i+1, j)))
                || (j > 1 && fullGrid[toX(i, j-1)] && uf.connected(x, toX(i, j-1)))
                || (j < n && fullGrid[toX(i, j+1)] && uf.connected(x, toX(i, j+1))));
            
            if (ans) {
                fullGrid[x] = true;
            }
        }
        
        return ans;
    }
    
    /**
     * Does the system percolate? 
     */
    public boolean percolates() {
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
     * Connect the just opened site to its (open) neighbour.
     * Also fill site if neighbour is full.
     */
    private void openAndMaybeFill(int site, int neighbour) {
        uf.union(site, neighbour);
        if (fullGrid[neighbour]) propagateFull(site, neighbour);
    }
    
    /**
     * @private
     * Given that neighbour is full, fill site and checks if grid percolates
     */
    private void propagateFull(int site, int neighbour) {
        fullGrid[site] = true;
        //uf_full.union(site, neighbour);
        int j = site % n + 1;
        if (j == n) percolates = true;
    }
    
    public static void main (String [] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        System.out.println("true: " + p.isFull(1, 1)); // true
        System.out.println("Percolates > false: " + p.percolates()); // false
        
        p.open(3, 3);
        System.out.println("false: " + p.isFull(3, 3)); // false
        System.out.println("Percolates > false: " + p.percolates()); // false
        
        p.open(2, 3);
        System.out.println("false: " + p.isFull(2, 3)); // false
        System.out.println("Percolates > false: " + p.percolates()); // false
        
        p.open(1, 2);
        System.out.println("false: " + p.isFull(1, 2)); // true
        System.out.println("Percolates > false: " + p.percolates()); // false
        
        p.open(1, 3);
        System.out.println("true: " + p.isFull(1, 3)); // true
        System.out.println("true: " + p.isFull(2, 3)); // true
        System.out.println("true: " + p.isFull(3, 3)); // true
        System.out.println("Percolates > false: " + p.percolates()); // false
        
        p.open(2, 4);
        System.out.println("true: " + p.isFull(2, 4)); // true
        System.out.println("Percolates > true: " + p.percolates()); // true
    }
}