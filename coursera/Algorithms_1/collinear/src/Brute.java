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

    /**
     * Given an input file with 2D-points, seeks for aligned points
     * @param args Name of the input file
     */
    public static void main(String[] args) {
        
        In input = new In(args[0]);
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        int N = input.readInt();
        Point[] points = new Point[N];
        
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
        
        seekAndDraw(points);
    }
 
    /**
     * @private
     * Seeks for the line-segments composed of 4 or more points,
     * draws and prints them.
     */
    private static void seekAndDraw(Point[] points) {
        
        if (points.length >= 4) {
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
        }
        
        StdDraw.setPenRadius(0.01);
        for (Point p : points) p.draw();
    }
 
    /**
     * @private
     * Checks whether p1, p2, p3 and p4 are aligned.
     */
    private static boolean aligned(Point p1, Point p2, Point p3, Point p4) {
        return equals(p1.slopeTo(p2), p1.slopeTo(p3))
            && equals(p1.slopeTo(p3), p1.slopeTo(p4));
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