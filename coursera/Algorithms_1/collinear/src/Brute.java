/*
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 */

import java.util.Arrays;

/**
 * For a given set of 2D-points, draws the points, scans for 4-aligned
 * subsets of points, prints them and draws lines connecting them.
 */
public class Brute {

    private Point[] points; // the set of points to analyze

    /**
     * Constructor
     * @param points
     */
    public Brute(Point[] points) {
        this.points = points;
        draw();
    }
 
    /**
     * Client test
     * @param args
     */
    public static void main(String[] args) {
        
        In input = new In(args[0]);
        
        int xmin = Integer.MAX_VALUE;
        int xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;
        
        int N = input.readInt();
        Point[] points = new Point[N];
        
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
        
        StdDraw.setXscale(xmin-1, xmax+1);
        StdDraw.setYscale(ymin-1, ymax+1);
        
        Stopwatch sw = new Stopwatch();
        new Brute(points);
        System.out.println(String.format("Elapsed time: %3.3f s",
                                         sw.elapsedTime()/1000));
    }
 
    /**
     * @private
     * Draws the set of points given and the lines connecting aligned points.
     */
    private void draw() {
        
        for (int p = 0; p < points.length; p++) {
            for (int q = p+1; q < points.length; q++) {
                for (int r = q+1; r < points.length; r++) {
                    for (int s = r+1; s < points.length; s++) {
                        
                        if (p == q || p == r || p == s
                         || q == r || q == s || r == s)
                            continue;
                        
                        if (aligned(points[p],
                                    points[q],
                                    points[r],
                                    points[s])) {
                            
                            Point[] alignedPoints = {
                                    points[p],
                                    points[q],
                                    points[r],
                                    points[s]
                            };
                            Arrays.sort(alignedPoints);
                            
                            System.out.println(alignedPoints[0] + " -> "
                                             + alignedPoints[1] + " -> "
                                             + alignedPoints[2] + " -> "
                                             + alignedPoints[3]);
                            
                            alignedPoints[0].drawTo(alignedPoints[3]);
                        }
                    }
                }
            }
        }
        
        StdDraw.setPenRadius(0.01);
        for (Point p : points) p.draw();
    }
 
    /**
     * @private
     * Indicates whether four given points are aligned.
     * @param a First point
     * @param b Second point
     * @param c Third point
     * @param d Fourth point
     * @return true if they are aligned; false otherwise.
     */
    private boolean aligned(Point a, Point b, Point c, Point d) {
        return equals(a.slopeTo(b), a.slopeTo(c))
            && equals(a.slopeTo(c), a.slopeTo(d));
    }
 
    /**
     * @private
     * Double comparison
     * @param a First number
     * @param b Second number
     * @return true if a == b; false otherwise.
     */
    private boolean equals(double a, double b) {
        return Math.abs(a-b) < Double.MIN_VALUE;
    }
}
