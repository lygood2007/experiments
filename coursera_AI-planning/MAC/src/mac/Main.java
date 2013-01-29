/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mac;

/**
 *
 * @author irpagnossin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            State s1 = new State(3, 3, 1);
            Action a1 = new LRAction(1,1);
            State s2 = s1.evolve(a1);
            Action a2 = new RLAction(1, 0);
            State s3 = s2.evolve(a2);
            
            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s3);
        }
        catch (Exception e) {}
    }
    
    private static final Action [] ACTIONS = {
        new LRAction(1,1),
        new LRAction(2,0),
        new LRAction(0,2),
        new RLAction(1,1),
        new RLAction(2,0),
        new RLAction(0,2)             
    };
}
