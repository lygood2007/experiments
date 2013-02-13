import java.io.FileWriter;
import java.io.File;
public class PercolationTest {
    public static void main (String [] args) {
        /*Percolation p = new Percolation(4);
        
        p.open(1,4);
        p.open(2,4);
        p.open(2,3);
        p.open(3,3);
        p.open(4,3);
        p.open(4,1);
        
        System.out.println(p.percolates()); // true
        System.out.println(p.isFull(4,1)); // false
        
        
          */  
        
        int x, i, j;
        int N = 20;
        Percolation p = new Percolation(N);
        
        StdRandom.setSeed(System.currentTimeMillis());
        
        try{
            FileWriter file = new FileWriter(new File("animation.txt"));
            
            file.write(N + "\n");
            while (!p.percolates()) {                
                x = (int) StdRandom.uniform(0, N * N);
                i = x / N + 1;
                j = x % N + 1;
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    file.write(i + "\t" + j + "\n");
                }
            }
            
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}