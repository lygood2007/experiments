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
    
    /**
     * Constructor
     * @param points
     */
    public Fast(Point[] points) {
        this.points = points;
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
        
        LinkedList<Point[]> allSegments = new LinkedList<Point[]>();
        LinkedList<Point> oneSegment = new LinkedList<Point>();
        
        Point[] copy = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++) {
            
            Arrays.sort(copy, points[i].SLOPE_ORDER);
            //print(points[i], copy);
            oneSegment = new LinkedList<Point>();
            oneSegment.push(points[i]);
            
            for (int j = 1; j < points.length; j++) {
                //System.out.println(j);
                if (points[i] == copy[j] || points[i] == copy[j-1]) continue;
                
                if (equals(points[i].slopeTo(copy[j]),
                           points[i].slopeTo(copy[j-1]))) {
                    
                    if (oneSegment.size() == 1) oneSegment.push(copy[j-1]);
                    oneSegment.push(copy[j]);
                }
                else {
                    //printList(oneSegment);
                    if (oneSegment.size() > 1) {
                        if (oneSegment.size() >= 4) insert(oneSegment, allSegments);
                        //else System.out.println("no add");
                        oneSegment = new LinkedList<Point>();
                        oneSegment.push(points[i]);
                    }
                    else {
                        //System.out.println("no add");
                    }
                }
            }
            
            if (oneSegment.size() >= 4) insert(oneSegment, allSegments);
        }
        
        String output = "";
        
        Iterator<Point[]> it = allSegments.iterator();
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
    
    private void printList(LinkedList<Point> l) {
        Iterator<Point> it = l.iterator();
        while(it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.print("... ");
    }
    
    private void print(Point p, Point[] ps) {
        String output = p + "\t";
        for (Point pt : ps) output += pt;
        System.out.println(output);
    }
    
    private void insert(LinkedList<Point> segment, LinkedList<Point[]> segments) {
        Point[] sorted = segment.toArray(new Point[segment.size()]);
        Arrays.sort(sorted);
        
        boolean insert = true;
        Iterator<Point[]> it = segments.iterator();
        while (it.hasNext() && insert) {
            if (Arrays.equals(it.next(), sorted)) insert = false;
        }               
        if (insert) {
            segments.push(sorted);
            System.out.println("add");
        }
        else System.out.println("no add");
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
