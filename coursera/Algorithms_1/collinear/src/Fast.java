/*
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * For a given set of 2D-points, draws the points, scans for 4-aligned
 * subsets of points and draws lines connecting them. Differently from
 * Brute.java, this class uses a more efficient algorithm to do this.
 */
public class Fast {

    private Point[] points; // the set of points to analyze
    private LinkedList<Point[]> segments;
    
    /**
     * Constructor
     * @param points
     */
    public Fast(Point[] points) {
        this.points = points;
        segments = new LinkedList<Point[]>();
        draw();
        
    }
    
    /**
     * Client test
     * @param args Name of the input file
     * @throws Exception 
     */
    public static void main(String[] args) {
        
        In input = new In(args[0]);
        
        int N = input.readInt();
        
        Point[] points = new Point[N];
        
        int xmin = Integer.MAX_VALUE;
        int xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;
        
        int i = 0;
        while (!input.isEmpty()) {
            
            int x = input.readInt();
            int y = input.readInt();
            
            if (x < xmin) xmin = x;
            else if (x > xmax) xmax = x;
            
            if (y < ymin) ymin = y;
            else if (y > ymax) ymax = y;
            
            points[i++] = new Point(x, y);
        }
        
        xmin = Math.min(xmin, ymin);
        ymin = xmin;
        xmax = Math.max(xmax, ymax);
        ymax = xmax;
        
        StdDraw.setXscale(xmin-1, xmax+1);
        StdDraw.setYscale(ymin-1, ymax+1);
        
        Stopwatch sw = new Stopwatch();
        new Fast(points); 
        System.out.println(String.format("Elapsed time: %3.3f s",
                                         sw.elapsedTime()/1000));
    }
    
    /**
     * @private
     * Draws the set of points given and the lines connecting aligned points.
     */
    private void draw() {
        
        Point[] copy = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++) {
            
            Arrays.sort(copy, points[i].SLOPE_ORDER);
            
            int jmin = 0;
            int j = 1;
            
            do {
                
                /*if (points[i] == copy[j] || points[i] == copy[j-1]) {
                    jmin = j;
                    continue;
                }*/
                
                if (!aligned(points[i], copy[j], copy[j-1])) {
                    
                    if (j - jmin >= 3) {
                        addSegment(copy, jmin, j-1, points[i]);
                    }
                    
                    jmin = j;
                }
                
            } while (++j < points.length);
            
            if (j - jmin >= 3) {
                addSegment(copy, jmin, j-1, points[i]);
            }
        }
        
        String output = "";
        
        Iterator<Point[]> it = segments.iterator();
        while (it.hasNext()) {
            Point[] pts = it.next();
            
            pts[0].drawTo(pts[pts.length-1]);
            
            for (int i = 0; i < pts.length; i++) {
                output += pts[i];
                if (i < pts.length - 1) output += " -> ";
            }
            output += "\n";         
        }
        
        StdDraw.setPenRadius(0.01);
        for (Point p : points) p.draw();
        
        System.out.println(output);
    }
    
    private void addSegment(Point[] original, int imin, int imax, Point pivot) {

        Point[] segment = new Point[imax - imin + 2];
        int j = 0;
        for (int i = imin; i <= imax; i++) {
            segment[j++] = original[i];
        }        
        //segment = Arrays.copyOfRange(original, imin, imax);
        segment[j] = pivot;
        Arrays.sort(segment);
                
        // TODO: otimizar com hash?
        boolean insert = true;
        Iterator<Point[]> it = segments.iterator();
        while (it.hasNext() && insert) {
            if (Arrays.equals(it.next(), segment)) insert = false;
        }               
        if (insert) segments.push(segment);
    }
    
    private boolean aligned(Point pivot, Point p1, Point p2) {
        return equals(pivot.slopeTo(p1), pivot.slopeTo(p2));
    }
    
    /**
     * @private
     * Double comparison
     * @param a First number
     * @param b Second number
     * @return true if a == b; false otherwise.
     */
    private boolean equals(double a, double b) {
        boolean ans = a == b;
        if (!ans) ans = Math.abs(a-b) < Double.MIN_VALUE;
        return ans;
    }
}
