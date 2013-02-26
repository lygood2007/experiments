import junit.framework.Assert;
import org.junit.Test;

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
    public void slope_ordering() {
        Point p1 = new Point(0,  0);
        Point p2 = new Point(0,  1);
        
        System.out.println(p1.SLOPE_ORDER.compare(p1, p2));
        System.out.println(p1.SLOPE_ORDER.compare(p1, p1));
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
}

