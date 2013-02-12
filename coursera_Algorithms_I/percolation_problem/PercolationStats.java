public class PercolationStats {
    
    //private int [] openedSites;
   // private int [] closedSites;
    private float [] threshold;
    
    /**
     * Perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        for (int t = 1; t <= T; ++t) {
            //int size = N * N;
            //openedSites = new int[size];
            //closedSites = new int[size];
            Percolation percolation = new Percolation(N);
            
            do {
                boolean percolates = false;
                
                int x;
                do {
                    x = random(0,size-1);
                } while (isOpened(x))
                    
                p.open(x);
                    
                    
            } while (!percolates);
            
            
            
        }
    }
    
    /**
     * sample mean of percolation threshold
     */
    public double mean() {
    }
        
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
    }

    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
    }
    
    /**
     * returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
    }
    
    /**
     * test client, described below
     */
    public static void main(String[] args) {
    }
}