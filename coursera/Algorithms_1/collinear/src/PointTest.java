import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import java.util.Arrays;

public class PointTest {

    @Test
    public void horizontal_line_slope_must_be_POSITIVE_zero() {
        // Given two horizontal aligned points
        Point p1 = new Point(3, 3);
        Point p2 = new Point(5, 3);
        
        // Then slope must be positive zero
        Assert.assertEquals(0.0, p1.slopeTo(p2));
    }
    
    @Test
    public void vertical_line_slope_must_be_POSITIVE_infinity() {
        // Given two vertical aligned points
        Point p1 = new Point(3, 3);
        Point p2 = new Point(3, 5);
        
        // Then slope must be positive infinity
        Assert.assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2));
    }
    
    @Test
    public void degenerate_line_slope_must_be_NEGATIVE_infinity() {
        // Given two equal points
        Point p1 = new Point(3, 3);
        Point p2 = new Point(3, 3);
        
        // Then slope must be negative infinity
        Assert.assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2));
    }
    
    @Test
    public void slope_ordering_1() {
        // Given
        Point p2 = new Point(2, 2);
        Point p1 = new Point(1, 3);
        
        // Then 
        Assert.assertEquals(1, p1.SLOPE_ORDER.compare(p1, p2));
        Assert.assertEquals(1, p2.SLOPE_ORDER.compare(p1, p2));
    }
    
    @Test
    public void slope_ordering_2() {
        // Given
        Point p1 = new Point( 1,  0);
        Point p2 = new Point( 0,  1);
        Point p3 = new Point(-1,  0);
        Point p4 = new Point( 0, -1);
        
        // Then 
        Assert.assertEquals(-1, p1.SLOPE_ORDER.compare(p1, p2));
        Assert.assertEquals(-1, p1.SLOPE_ORDER.compare(p2, p3));
        Assert.assertEquals(-1, p1.SLOPE_ORDER.compare(p3, p4));
    }
    
    @Test
    public void slope_ordering_3() {

        Point p1 = new Point( 1,  0);
        Point p2 = new Point( 0,  1);
        Point p3 = new Point(-1,  0);
        Point p4 = new Point( 0, -1);
                
        final Point[] ORDERED = {p1, p2, p3, p4}; // I know this is correctly ordered
        
        // Given a shuffled array of points
        Point[] shuffled = {p1, p2, p3, p4};
        StdRandom.shuffle(shuffled);
        
        // When sorted
        Arrays.sort(shuffled, p1.SLOPE_ORDER);
        
        // It equals to ORDERED
        for(int i = 0; i < shuffled.length; i++) {
            Assert.assertEquals(shuffled[i], ORDERED[i]);
        }
    }
    
    @Test
    public void xy_ordering() {
        // Given
        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(1, 2);
        
        // Then 
        Assert.assertTrue(p1.compareTo(p3) == -1);
        Assert.assertTrue(p1.compareTo(p4) == -1);
        Assert.assertTrue(p2.compareTo(p3) == -1);
        Assert.assertTrue(p2.compareTo(p4) == -1);
        Assert.assertTrue(p1.compareTo(p2) == -1);
        Assert.assertTrue(p4.compareTo(p3) == -1);
        Assert.assertTrue(p0.compareTo(p1) ==  0);
    }
    
    @Test
    public void xy_ordering_2() {
                
        Point[] ordered = {
            new Point( 3, 0),
            new Point( 3, 1),
            new Point( 2, 3),
            new Point( 1, 3),
            new Point( 0, 3),
            new Point(-1, 3),
            new Point(-2, 2),
            new Point(-3, 1),
            new Point(-3,-1),
            new Point(-2,-2),
            new Point(-1,-3),
            new Point( 0,-3),
            new Point( 1,-3),
            new Point( 2,-2),
            new Point( 3,-1)
        };
        
        Point[] shuffled = new Point[ordered.length];
        for (int i = 0; i < shuffled.length; i++) shuffled[i] = ordered[i];        
        StdRandom.shuffle(shuffled);
                
        Arrays.sort(shuffled, ordered[0].SLOPE_ORDER);
                
        for (int i = 0; i < shuffled.length; i++) Assert.assertEquals(ordered[i], shuffled[i]);
        
    }

}

