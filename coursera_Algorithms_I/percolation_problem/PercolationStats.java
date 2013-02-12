/**
 * @author Ivan Ramos Pagnossin
 * Email: ivan.pagnossin@gmail.com
 * Date: 2013.02.12
 * Description: statistical analysis of percolation problem (programming assignment).
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
        
        int x;
        int[] coord;
        int opened;
        Percolation p;
            
        for (int t = 0; t < T; t++) {
            
            StdRandom.setSeed(System.currentTimeMillis());
            
            p = new Percolation(N);
            opened = 0;
            
            while (!p.percolates()) {                
                x = (int) StdRandom.uniform(0, N * N);
                coord = fromX(x, N);
                
                if (!p.isOpen(coord[0], coord[1])) {
                    p.open(coord[0], coord[1]);
                    ++opened;
                }
            } 
            
            threshold[t] = (double) opened / (N * N);          
            System.out.println(t + "\t" + threshold[t]);
        }
        
        System.out.println(mean() + "\t" + stddev() + "\t" + confidenceLo() + "\t" + confidenceHi());
    }
    
    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        double mean = 0;
        
        for (int i = 0; i < n; i++) {
            mean += threshold[i];
        }
        
        return mean / n;
    }
        
    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        
        double mean = mean();
        double diff = 0;
        
        double stddev = 0;
        for (int i = 0; i < n; i++) {
            diff = threshold[i] - mean;
            stddev += diff * diff;
        }
        
        return Math.sqrt(stddev / (n - 1));
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
     * x \in [0..N^2) -> (i,j) \in [0..N)
     */
    private int [] fromX(int x, int size) {
        int [] ans = {x / size + 1, x % size + 1};
        return ans;
    }
    
    /**
     * Test client, described below
     */
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(100, 30);
    }
}