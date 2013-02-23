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

    public final Comparator<Point> SLOPE_ORDER = new PointComparator(); // Compare points by slope

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
     * Slope between this point and that point.
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
            ans = (that.y - this.y) / (that.x - this.x);
        
        return ans;        
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
     * String representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Compares two 2D-points by slope.
     * @see java.util.Comparator
     */
    class PointComparator implements Comparator<Point> {
        
        /**
         * Compares two 2D-points by slope (invoking point is the reference)
         * @see java.util.Comparator
         */
        @Override
        public int compare(Point p1, Point p2) {
            
            double angleP1 = Math.atan2(p1.y - y, p1.x - x);
            if (angleP1 < 0) angleP1 += 2*Math.PI;
            
            double angleP2 = Math.atan2(p2.y - y, p2.x - x);
            if (angleP2 < 0) angleP2 += 2*Math.PI;
            
            int ans;
            if (angleP1 < angleP2) ans = -1;
            else if (angleP1 > angleP2) ans = +1;
            else ans = 0;
                        
            return ans;
        }        
    }
}