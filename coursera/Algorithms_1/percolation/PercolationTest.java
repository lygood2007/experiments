import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class PercolationTest {
    public static void main (String [] args) {
        
        int x, i, j;
        int N = 20;
        Percolation p = new Percolation(N);
        
        StdRandom.setSeed(System.currentTimeMillis());
        
        try{
            Writer out = new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream("animation.txt"), "UTF-8"));
            
            out.write(N + "\n");
            while (!p.percolates()) {                
                x = (int) StdRandom.uniform(0, N * N);
                i = x / N + 1;
                j = x % N + 1;
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    out.write(i + "\t" + j + "\n");
                }
            }
            
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}