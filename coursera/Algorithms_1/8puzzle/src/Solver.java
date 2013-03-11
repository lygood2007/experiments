public class Solver {
    
    private int N = 3;
    
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
        int i = n / N;
        int j = n % N;
        int a, b;
        
        if (i == 0) a = 0;
        else if (i == N-1) a = 2;
        else a = 1;
        
        if (j == 0) b = 0;
        else if (j == N-1) b = 2;
        else b = 1;
        
        int nn = a + N * b;
        
        return AVAILABLE_ACTIONS[nn];
        
    }
    
    private int[][] AVAILABLE_ACTIONS = {
            {        +1, +N}, // In a given state, if the hole is in position 0, only actions +1 and +3 can be applied
            {    -1, +1, +N}, // If hole's position is 1, only -1, +1 and +3 are applicable
            {    -1,     +N},
            {-N,     +1, +N},
            {-N, -1, +1, +N},
            {-N, -1,     +N},
            {-N,     +1    }, // Only action -3 and +1 can be applied
            {-N, -1, +1    },
            {-N, -1        }
    };
}