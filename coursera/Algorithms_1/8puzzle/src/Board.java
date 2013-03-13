
public class Board {
    
	private int N;            // N-by-N board
    private int[][] tiles;    // The tiles of this board    
    private int zeroi, zeroj; // Hole's position in board
    
    /**
     * Construct a board from an N-by-N array of blocks
     * @param blocks blocks[i][j] = block in row i, column j
     */
    public Board(int[][] blocks) {
    	
        N = blocks.length;
        
        tiles = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
                if (tiles[i][j] == 0) {
                	zeroi = i;
                	zeroj = j;
                }
            }
        }
    } 
    
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
        
        for (int i = 0; i < N; i++) {
        	for (int j = 0; j < N; j++) {
        		if (tiles[i][j] != 0 && tiles[i][j] != i*N+j+1) ++count;
        	}
        }
        
        return count;
    } 
    
    /**
     * Sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
    	
    	int distance = 0;
    	int expected_i;
    	int expected_j;
    	
    	for (int i = 0; i < N; i++) {
        	for (int j = 0; j < N; j++) {
        		if (tiles[i][j] != 0) {
        			expected_i = tiles[i][j] / N;
        			expected_j = tiles[i][j] % N;
        			if (i != expected_i) distance += Math.abs(i - expected_i);
        			if (j != expected_j) distance += Math.abs(j - expected_j);
        		}
        	}
        }
    	
        return distance;
    } 
    
    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
    	boolean ans = true;
    	
    	for (int i = 0; i < N && ans; i++) {
    		for (int j = 0; j < N && ans; j++) {
    			if (i < N-1 || j < N-1) ans = ans && (tiles[i][j] == i * N + j + 1);
    		}
    	}
    	
        return ans;
    }
    
    /**
     * A board obtained by exchanging two adjacent blocks in the same row.
     */
    public Board twin() {
    	
    	int[][] copy = new int[N][N];
    	int ii = 0, jj = 0;
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			copy[i][j] = tiles[i][j];
    			if (j < N-1 && tiles[i][j] != 0 && tiles[i][j+1] != 0) {
    				ii = i;
    				jj = j;
    			}
    		}
    	}
    	
    	swap(copy, ii, jj, ii, jj+1);
    	
    	return new Board(copy);
    } 
    
    
    
    /**
     * Does this board equal (board) y?
     */
    public boolean equals(Object y) {
    	
    	 if (y == this) return true;
         if (y == null) return false;
         if (y.getClass() != this.getClass()) return false;
         
         Board that = (Board) y;
         
         if (that.dimension() != this.dimension()) return false;
         
         return that.toString() == this.toString(); // TODO: argh!! :o
    } 
    
    /**
     * All neighboring boards.
     */
    public Iterable<Board> neighbors() {
    	
    	Queue<Board> q = new Queue<Board>();
    	
    	if (zeroi > 0  ) q.enqueue(createNeighbor(+1, 0));
    	if (zeroi < N-1) q.enqueue(createNeighbor(-1, 0));    	
    	if (zeroj > 0  ) q.enqueue(createNeighbor(0, +1));
    	if (zeroj < N-1) q.enqueue(createNeighbor(0, -1));
    	
    	return q;
    }
    
    /**
     * string representation of the board.
     */
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append(N + "\n");
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			s.append(String.format("%2d ", tiles[i][j]));
    		}
    		s.append("\n");
    	}
    	return s.toString();    	
    }
    
    private Board createNeighbor(int di, int dj) {
    	int[][] neighbor = copy(tiles);
    	swap(neighbor, zeroi, zeroj, zeroi+di, zeroj+dj);
    	return new Board(neighbor);
    }
    
    private int[][] copy(int[][] original) {
    	int[][] ans = new int[N][N];
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; i < N; i++) {
    			ans[i][j] = original[i][j];
    		}
    	}
    	
    	return ans;
    }
    
    private void swap(int[][] array, int i1, int j1, int i2, int j2) {
    	int tmp = array[i1][j1];
    	array[i1][j1] = array[i2][j2];
    	array[i2][j2] = tmp;
    }
    
}