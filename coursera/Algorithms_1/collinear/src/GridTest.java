
public class GridTest {

    public static void main(String[] args) {
        
        Out file = new Out("my_grid.txt");
        
        int M = 4;
        int N = 100;
        
        file.println(N*M);
        for (int x = 0; x < M; x++) {
            for (int y = 0; y < N; y++) {
                file.println(x + "\t" + y);
            }
        }
        
        file.close();
    }
}
