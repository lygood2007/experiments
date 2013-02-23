
public class Brute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Point[] points = {
				new Point( 1, 1), // A
				new Point( 0, 3), // B
				new Point( 1, 2), // C
				new Point( 2, 1), // D
				new Point( 3, 0), // E
				new Point( 0, 1), // F
				new Point( 3, 1), // G
				new Point( 1, 0), // H
				new Point( 5, 2), // I
				new Point(-1,-1)  // J
		};
		
		String[] names = {
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
		};
		
		
		// Aligned points
		// BCDE: 1 2 3 4
		// ADFG: 0 3 5 6 
		// GHIJ: 6 7 8 9
		
		for (int p = 0; p < points.length; p++) {
			for (int q = p+1; q < points.length; q++) {
				for (int r = q+1; r < points.length; r++) {
					for (int s = r+1; s < points.length; s++) {
						
						if (p == q || p == r || p == s || q == r || q == s || r == s) continue;
						
						if (aligned(points[p], points[q], points[r], points[s])) {
							double slopePQ = points[p].slopeTo(points[q]);
							double slopePR = points[p].slopeTo(points[r]);
							double slopePS = points[p].slopeTo(points[s]);
							System.out.println(names[p] + "\t" + names[q] + "\t" + names[r] + "\t" + names[s] + "\t" + slopePQ + "\t" + slopePR + "\t" + slopePS);
						}
					}
				}
			}
		}
	}
	
	public static boolean aligned(Point a, Point b, Point c, Point d) {
		return equals(a.slopeTo(b),a.slopeTo(c)) && equals(a.slopeTo(c),a.slopeTo(d));
	}
	
	public static boolean equals(double a, double b) {
		return Math.abs(a-b) < Double.MIN_VALUE;
	}
}
