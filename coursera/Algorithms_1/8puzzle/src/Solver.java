public class Solver {
    
    private int N = 4;
    private int[][] AVAILABLE_ACTIONS = {
        {        +1, +N}, // In a given state, if the hole is in position 0, only actions +1 and +N can be applied
        {    -1, +1, +N}, // If hole's position is 1, only -1, +1 and +3 are applicable
        {    -1,     +N},
        {-N,     +1, +N},
        {-N, -1, +1, +N},
        {-N, -1,     +N},
        {-N,     +1    }, // Only action -N and +1 can be applied
        {-N, -1, +1    },
        {-N, -1        }
    }; 
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
               
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return 0;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return null;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        
    }
    
    private int[] getAvailableActions(int n) {
        int i = n / N; // (i,j) coordinate in a N-by-N grid
        int j = n % N;
        
        int a; // (a,b) coordinate in a 3-by-3 grid 
        int b;
        
        if (i == 0) a = 0;
        else if (i == N-1) a = 2;
        else a = 1;
        
        if (j == 0) b = 0;
        else if (j == N-1) b = 2;
        else b = 1;
        
        int m = a + 3 * b;
        
        return AVAILABLE_ACTIONS[m];
    }
    
    private final int[][] 
}