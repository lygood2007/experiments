/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mac;

/**
 *
 * @author irpagnossin
 */
class Action {
    public int m;
    public int b;
    public int c;
    public Action (int m, int c, int b) {
        if (inRange(m) && inRange(c) && inRange(m + c) && (b == -1 || b == +1)) {
            this.m = m;
            this.c = c;
            this.b = b;
        }
    }
    
    
    private boolean inRange (int value) {
        return inRange(value, -2, 2);
    }
    
    private boolean inRange (int value, int min, int M) {
        return value >= min && value <= M;
    }
}
