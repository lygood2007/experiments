/*
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 */
import java.util.Comparator;

/**
 * Represents a 2D point in rectangular coordinate system.
 */
public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER =
            new PointComparator(); // Compare points by slope to this point

    private final int x; // x coordinate
    private final int y; // y coordinate
    
    /**
     * create the point (x, y)
     * @param x coordinate
     * @param y coordinate
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Plot this point to standard drawing.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draw line between this point and that point to standard drawing.
     * @param that
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    /**
     * String representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Is this point lexicographically smaller than that one?
     */
    public int compareTo(Point that) {
        
        int ans;
        
        if      (this.y < that.y) ans = -1;
        else if (this.y > that.y) ans = +1;
        else if (this.x < that.x) ans = -1;
        else if (this.x > that.x) ans = +1;
        else                      ans =  0;
        
        return ans;
    }
    
    /**
     * Slope from this point to that point.
     * @param that The other point.
     * @return The slope, a real number.
     */
    public double slopeTo(Point that) {
        
        double ans;
        
        if (that.x == this.x && that.y == this.y)
            ans = Double.NEGATIVE_INFINITY; // Degenerate
        else if (that.x == this.x && that.y != this.y)
            ans = Double.POSITIVE_INFINITY; // Vertical line
        else if (that.x != this.x && that.y == this.y)
            ans = +0; // Horizontal line
        else
            ans = (double) (that.y - this.y) / (that.x - this.x);
        
        return ans;        
    }
    
    /**
     * Compares two 2D-points by slope.
     * @see java.util.Comparator
     */
    private class PointComparator implements Comparator<Point> {
        
        /**
         * Compares two 2D-points by slope (invoking point is the reference)
         * @see java.util.Comparator
         */
        @Override
        public int compare(Point p1, Point p2) {
            
            double slopeP1 = slopeTo(p1);
            double slopeP2 = slopeTo(p2);
            
            int ans;
            if (slopeP1 < slopeP2) ans = -1;
            else if (slopeP1 > slopeP2) ans = +1;
            else ans = 0;
                        
            return ans;
        }        
    }
}