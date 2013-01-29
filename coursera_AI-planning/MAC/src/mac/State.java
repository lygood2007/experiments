/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mac;

/**
 *
 * @author irpagnossin
 */
public class State {
    private int m;
    private int c;
    private int b;

    
    public State (int m, int c, int b) throws Exception {
        if ((m >= 0 && m <= 3) && 
            (c >= 0 && c <= m) && 
            (b == 0 || b == 1)) {
        
            this.m = m;
            this.c = c;
            this.b = b;        
        }
    }
    
    public State evolve (Action action) throws Exception {
        State state = new State(this.m, this.c, this.b);
        
        state.m += action.m;
        state.c += action.c;
        state.b += action.b;
        
        if (valid(state)) {
            return state;
        }
        else {
            return null;
        }        
    }

    private boolean valid(State state) {
        return inRange(state.m) && inRange(state.c) && state.m >= state.c;
    }
    
    private boolean inRange(int x) {
        return x >= 0 && x <= 3;
    }
    
    @Override
    public String toString () {
        return "(" + this.m + "," + this.c + "," + this.b + ")";
    }
}
