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
    	Point p0 = new Point( 0,  0);
        Point p1 = new Point( 1,  0);
        Point p2 = new Point( 0,  1);
        Point p3 = new Point(-1,  0);
        Point p4 = new Point( 0, -1);
        
        // Then 
        Assert.assertEquals(-1, p0.SLOPE_ORDER.compare(p1, p2));
        Assert.assertEquals(-1, p0.SLOPE_ORDER.compare(p2, p3));
        Assert.assertEquals(-1, p0.SLOPE_ORDER.compare(p3, p4));
    }
    
    @Test
    public void slope_ordering_2() {
        // Given
    	
    	int N = (int) (10 + 100 * Math.random());
    	double R = 1000;
    	double dAngle = 2 * Math.PI / N;
    	
    	Point[] points = new Point[N];
    	for(int i = 0; i < N; i++) {
    		int x = (int) (R * Math.cos(i * dAngle));
    		int y = (int) (R * Math.sin(i * dAngle));
    		points[i] = new Point(x, y);
    	}
    	
    	Point ZERO = new Point(0, 0);
    	for (int i = 1; i < N; i++) {
    		Assert.assertEquals(+1, ZERO.SLOPE_ORDER.compare(points[i], points[i-1]));
    	}    	
    }
    
    @Test
    public void slope_ordering_3() {
    	
        // Given a shuffled array of Points  	
    	int N = (int) (10 + 100 * Math.random());
    	double R = 1000;
    	double dAngle = 2 * Math.PI / N;
    	Point ZERO = new Point(0, 0);
    	
    	Point[] ordered = new Point[N];
    	Point[] shuffled = new Point[N];
    	for(int i = 0; i < N; i++) {
    		int x = (int) (R * Math.cos(i * dAngle));
    		int y = (int) (R * Math.sin(i * dAngle));
    		ordered[i] = shuffled[i] = new Point(x, y);
    	}
    	
    	StdRandom.shuffle(shuffled);
    	
    	// When sorted
    	Arrays.sort(shuffled, ZERO.SLOPE_ORDER);
    	
    	// Then it is sorted
    	for (int i = 1; i < N; i++) {
    		Assert.assertEquals(ordered[i], shuffled[i]);
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
    
    /*@Test
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
        
    }*/

}

