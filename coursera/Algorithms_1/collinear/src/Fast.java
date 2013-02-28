/*
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * For a given set of 2D-points, scans for 4-aligned
 * subsets of points and draws lines connecting them. Differently from
 * Brute.java, this class uses a more efficient algorithm to do this.
 */
public class Fast {
    
    /**
     * Given an input file with 2D-points, seeks for aligned points
     * @param args Name of the input file
     */
    public static void main(String[] args) {
        
        Stopwatch sw = new Stopwatch();
        
        In input = new In(args[0]);
        
        int N = input.readInt();
        
        Point[] points = new Point[N];
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        int i = 0;
        while (!input.isEmpty()) {
            
            int x = input.readInt();
            int y = input.readInt();
            
            if (x < min) min = x;
            else if (x > max) max = x;
            
            if (y < min) min = y;
            else if (y > max) max = y;
            
            points[i++] = new Point(x, y);
        }
        
        //StdDraw.setXscale(min-1, max+1);
        //StdDraw.setYscale(min-1, max+1);
        StdDraw.setXscale(0, 32768); // Requested by the assignment
        StdDraw.setYscale(0, 32768); // Requested by the assignment
        
        LinkedList<Point[]> segments = new LinkedList<Point[]>();
        HashMap<Integer,Object> segments2 = new HashMap<Integer,Object>();
        
        seek(points, segments, segments2);
        draw(points, segments, segments2);
    }
    
    /**
     * @private
     * Seeks for the line-segments composed of 4 or more points.
     */
    private static void seek(Point[] points, LinkedList<Point[]> segments, HashMap<Integer,Object> segments2) {
        
        if (points.length < 4) return;
        
        Point[] copy = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++) {
            
            Arrays.sort(copy, points[i].SLOPE_ORDER);
            
            int jmin = 0;
            int j = 1;
            
            do {
                
                if (!aligned(points[i], copy[j], copy[j-1])) {
                    
                    if (j - jmin >= 3)
                        addSegment(copy, jmin, j-1, points[i], segments, segments2);
                    
                    jmin = j;
                }
                
            } while (++j < points.length);
            
            if (j - jmin >= 3)
                addSegment(copy, jmin, j-1, points[i], segments, segments2);
        }
    }
    
    /**
     * @private
     * Draws the line-segments found by seek()
     * @see seek
     */
    private static void draw(Point[] points, LinkedList<Point[]> segments, HashMap<Integer,Object> segments2) {
                
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
        
        StdDraw.setPenRadius(0.01);
        for (Point p : points) p.draw();
    }
    
    /**
     * @private
     * Inserts a new segment to the segments list, if it is not there yet.
     * @param original Sub-set of points to copy from
     * @param imin Lower index (starts copy here)
     * @param imax Upper index (stops copy here)
     * @param pivot The point used to find this segment
     * @param segments List of segments
     */
    private static void addSegment(Point[] original, int imin, int imax,
            Point pivot, LinkedList<Point[]> segments, HashMap<Integer,Object> segments2) {

        Point[] segment = new Point[imax - imin + 2];
        int j = 0;
        for (int i = imin; i <= imax; i++)
            segment[j++] = original[i];
        
        segment[j] = pivot;
        Arrays.sort(segment);
        
        int hash = (segment[0].toString() + segment[segment.length-1]).hashCode();
        
        Point[] s = {segment[0], segment[segment.length-1]};
                
        if (!segments2.containsKey(hash)) {
            segments2.put(hash, null);            
            segments.push(s);
        }
        /*
        boolean insert = true;
        Iterator<Point[]> it = segments.iterator();
        while (it.hasNext() && insert) {
            if (Arrays.equals(it.next(), segment)) insert = false;
        }
        
        if (insert) segments.push(segment);*/
    }
    
    /**
     * @private
     * Checks whether p1, p2 and p3 are aligned.
     */
    private static boolean aligned(Point p1, Point p2, Point p3) {
        return equals(p1.slopeTo(p2), p1.slopeTo(p3));
    }
    
    /**
     * @private
     * Checkes for Double equality
     */
    private static boolean equals(double a, double b) {
        boolean ans = a == b;
        if (!ans) ans = Math.abs(a-b) < Double.MIN_VALUE;
        return ans;
    }
}
