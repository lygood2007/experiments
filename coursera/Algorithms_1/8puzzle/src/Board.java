import java.util.Arrays;

public class Board {
    
    private int[] state; 
    private int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private int N;
    
    /**
     * Construct a board from an N-by-N array of blocks
     * @param blocks
     */
    public Board(int[][] blocks) {
        N = blocks.length;
        state = new int[N*N];
        goal = new int[N*N];
        
        int n = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                state[n] = blocks[i][j];
                if (n == N*N-1) goal[n] = 0;
                else goal[n] = n;
                ++n;
            }
        }
    } 
                                           // (where blocks[i][j] = block in row i, column j)
    
    /**
     * Board dimension N
     */
    public int dimension() {
        return N;
    }            
     
    /**
     * Number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        
        for (int n = 0; n < N*N; n++) {
            if (state[n] != 0 && state[n] != goal[n]) ++count;
        }
        
        return count;
    } 
    
    /**
     * Sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
        return 0;
    } 
    
    /**
     * Is this board the goal board?
     * @return
     */
    public boolean isGoal() {
        return Arrays.equals(state, goal);
    }
    
    public Board twin() {return null;}                   // a board obtained by exchanging two adjacent blocks in the same row
    public boolean equals(Object y) {return true;}       // does this board equal y?
    public Iterable<Board> neighbors() {return null;}     // all neighboring boards
    public String toString() {return "";}               // string representation of the board (in the output format specified below)
}