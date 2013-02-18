/**
 * <p>Usage:
 * <pre>
 * java PercolationStats N T
 * </pre>
 * It performs T independent computational experiments on an N-by-N grid.
 * N > 0 and T > 0 are integers.
 * </p>
 * 
 * <p>Example:
 * <pre>
 * % java PercolationStats 300 100
 * </pre>
 * </p>
 * 
 * @author Ivan Ramos Pagnossin
 * @version 2013.02.12
 * @description statistical analysis of percolation problem (programming assignment).
 */
public class PercolationStats {

    private double [] threshold; // The threshold of the <n> experiments
    private int n; // The amount of experiments
    
    /**
     * Perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        
        if (N < 1) throw new IllegalArgumentException("N must be 1 or greater.");
        if (T < 1) throw new IllegalArgumentException("T must be 1 or greater.");
        
        n = T;
        threshold = new double[T];
        
        int x, i, j;
        int nopen;
        Percolation p;
            
        for (int t = 0; t < T; t++) {
            
            p = new Percolation(N);
            nopen = 0;
            
            while (!p.percolates()) {                
                x = (int) StdRandom.uniform(0, N * N);
                i = x / N + 1;
                j = x % N + 1;
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    ++nopen;
                }
            } 
            
            threshold[t] = (double) nopen / (N * N);
        }
    }
    
    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(threshold);
    }
        
    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /**
     * Returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(n);
    }
    
    /**
     * Returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(n);
    }
    
    /**
     * Test client, described below
     */
    public static void main(String[] args) {
        
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(N, T);
        
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = "
                               + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}