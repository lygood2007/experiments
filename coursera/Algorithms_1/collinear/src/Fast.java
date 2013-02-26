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
    private LinkedList<Point[]> segments; // List of the line-segments found
    
    /**
     * Constructor
     * @param points
     */
    public Fast(Point[] ps) {
        points = Arrays.copyOf(ps, ps.length);
        segments = new LinkedList<Point[]>();
        
        seek();
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
     * Seeks for the line-segments composed of 4 or more points.
     */
    private void seek() {
        Point[] copy = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++) {
            
            Arrays.sort(copy, points[i].SLOPE_ORDER);
            
            int jmin = 0;
            int j = 1;
            
            do {
                
                if (!aligned(points[i], copy[j], copy[j-1])) {
                    
                    if (j - jmin >= 3)
                        addSegment(copy, jmin, j-1, points[i]);
                    
                    jmin = j;
                }
                
            } while (++j < points.length);
            
            if (j - jmin >= 3)
                addSegment(copy, jmin, j-1, points[i]);
        }
    }
    
    /**
     * @private
     * Draws the line-segments found by seek()
     * @see seek
     */
    private void draw() {
        
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
        
        System.out.println(output);
    }
    
    /**
     * @private
     * Inserts a new segment to the segments list, if it is not there yet.
     * @param original Sub-set of points to copy from
     * @param imin Lower index (starts copy here)
     * @param imax Upper index (stops copy here)
     * @param pivot The point used to find this segment
     */
    private void addSegment(Point[] original, int imin, int imax, Point pivot) {

        Point[] segment = new Point[imax - imin + 2];
        int j = 0;
        for (int i = imin; i <= imax; i++)
            segment[j++] = original[i];
        
        segment[j] = pivot;
        Arrays.sort(segment);
                
        boolean insert = true;
        Iterator<Point[]> it = segments.iterator();
        while (it.hasNext() && insert) {
            if (Arrays.equals(it.next(), segment)) insert = false;
        }
        
        if (insert) segments.push(segment);
    }
    
    private boolean aligned(Point p1, Point p2, Point p3) {
        return equals(p1.slopeTo(p2), p1.slopeTo(p3));
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
