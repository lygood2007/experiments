/*
 * @author Ivan Ramos Pagnossin
 * @email: ivan.pagnossin@gmail.com
 * @version: 2013.02.11
 * @description: percolation model of a N-by-N grid.
 */
public class Percolation {

    private boolean [] open; // Open sites
    private WeightedQuickUnionUF uf; // Union-find associated to open
    private int N; // N-by-N grid
    
    /**
     * Create an N-by-N grid, with all sites blocked.
     */
    public Percolation(int N) {
        
        if (N < 1) throw new IllegalArgumentException("N must be 1 or greater.");
        
        int M = N * N + 2;
        
        open = new boolean[M];        
        for (int x = 0; x < M; x++) open[x] = false;
                
        uf = new WeightedQuickUnionUF(M);
        
        this.N = N;
    }
    
    /*
     * Open site (row,column) = (i,j) if it is not already opened.
     */
    public void open(int i, int j) {
        if (i < 1 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || i > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        
        int x = toX(i, j); // Internal one-dimentional coordinate x
        
        open[x] = true;
        
        if (i > 1 && isOpen(i-1, j)) uf.union(x, toX(i-1, j));
        if (i < N && isOpen(i+1, j)) uf.union(x, toX(i+1, j));
        if (j > 1 && isOpen(i, j-1)) uf.union(x, toX(i, j-1));
        if (j < N && isOpen(i, j+1)) uf.union(x, toX(i, j+1));        
        
        if (i == 1) uf.union(x, 0);
        else if (i == N) uf.union(x, N*N+1);
    }    
    
    /**
     * Is site (row, column) = (i, j) open?
     */
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || i > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        
        return open[toX(i, j)];
    }
    
    /**
     * Is site (row, column) = (i, j) full?
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || i > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        
        return uf.connected(0, toX(i, j));
    }
    
    /**
     * Does the system percolate? 
     */
    public boolean percolates() {
        return uf.connected(0, N*N+1);
    }
    
    /**
     * @private
     * i,j \in [1..N] -> x \in 0..(N^2-1) 
     */
    private int toX(int i, int j) {
        return (i - 1) * N + j;
    }    
}