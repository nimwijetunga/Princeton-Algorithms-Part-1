import edu.princeton.cs.algs4.Quick;

public class BruteCollinearPoints {
	
	 private LineSegment[] segments;
	
	 public BruteCollinearPoints(Point[] points) {
		 if(points == null) throw new java.lang.IllegalArgumentException();
		 this.checkNull(points);
		 duplicates(points);
		 LineSegment[] tmp = new LineSegment[points.length];
		 int index = 0;
		 Quick.sort(points);
		 for(int p = 0; p < points.length; p++) {
			 for(int q = p + 1; q < points.length; q++) {
				 for(int r = q + 1; r < points.length; r++) {
					 for(int s = r + 1; s < points.length; s++) {
						 if(points[p] == null || points[q] == null || 
								 points[r] == null || points[s] == null) throw new java.lang.IllegalArgumentException();
						 double p_q = points[p].slopeTo(points[q]), p_r = points[p].slopeTo(points[r]), p_s = points[p].slopeTo(points[s]);
						 if(p_q == p_r && p_q == p_s) {
							 tmp[index] = new LineSegment(points[p], points[s]);
							 index++;
						 }
					 }
				 }
			 }
		 }
		 segments = new LineSegment[index];
		 for(int i = 0; i < index; i++) {
			 segments[i] = tmp[i];
		 }
	 }
	 
	 private void checkNull(Point [] points) {
		 for(int i = 0; i < points.length; i++) {
			 if(points[i] == null)throw new java.lang.IllegalArgumentException();
		 }		
	 }
	 
	 private void duplicates(Point[] points) {
		 for(int i = 0; i < points.length; i++) {
				for(int j = i + 1; j < points.length; j++) {
					if(points[i].compareTo(points[j]) == 0)throw new java.lang.IllegalArgumentException();
				}
		}
	 }
	 
	 public int numberOfSegments() {
		 return segments.length;
	 }
	 
	 public LineSegment[] segments() {
		 return this.segments;
	 }
	 
	 public static void main(String[] args) {
		 Point [] points = {new Point(10000,0), new Point(0,10000), new Point(3000, 7000), new Point(7000, 3000),
					new Point( 20000,21000), new Point(3000,4000)
					, new Point(14000, 15000), new Point(6000,7000)};
		 BruteCollinearPoints bcp = new BruteCollinearPoints(points);
		 LineSegment[] segments = bcp.segments();
		 for(LineSegment i : segments) {
			System.out.println(i);
		 }
	 }
	

}
