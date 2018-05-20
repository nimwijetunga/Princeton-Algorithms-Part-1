import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class FastCollinearPoints {
	
	private LineSegment[] segments;
	private ArrayList<LineSegment> tmp;
	private ArrayList<Point> startP;
	private ArrayList<Point> endP;
	
	public FastCollinearPoints(Point[] points) {
		tmp = new ArrayList<LineSegment>();
		endP = new ArrayList<Point>();
		startP = new ArrayList<Point>();
		if(points == null) throw new java.lang.IllegalArgumentException();
		this.checkNull(points);
		duplicates(points);
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
		
		for(int i = 0; i < points.length; i++) {		
			ArrayList<Point> sameSlope = new ArrayList<Point>();
			Arrays.sort(pointsCopy, points[i].slopeOrder());
			
			double curSlope = 0;
			double prevSlope = Double.NEGATIVE_INFINITY;
			
			for(int j = 1; j < pointsCopy.length; j++) {
				curSlope = points[i].slopeTo(pointsCopy[j]);
				if(curSlope == prevSlope) {
					sameSlope.add(pointsCopy[j]);
				}else {
					if(sameSlope.size() >= 3) {
						sameSlope.add(points[i]);
						addSegments(sameSlope, prevSlope);
					}
					sameSlope.clear();
					sameSlope.add(pointsCopy[j]);
				}
				prevSlope = curSlope;
			}
			if(sameSlope.size() >= 3) {
				sameSlope.add(points[i]);
				addSegments(sameSlope, curSlope);
			}
		}
		segments = new LineSegment[tmp.size()];
		int index = 0;
		for(LineSegment value: tmp) {
			segments[index] = value;
			index++;
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
	
	private void addSegments(ArrayList<Point> sameSlope, double slope) {
		Collections.sort(sameSlope);
		
		Point start = sameSlope.get(0);
		Point end = sameSlope.get(sameSlope.size() - 1);
		
		for(int i = 0; i <tmp.size(); i++) {
			Point s_2 = startP.get(i);
			Point e_2 =  endP.get(i);
			if(slope == s_2.slopeTo(e_2)) {
				if(e_2.compareTo(end) == 0) {
					if(start.compareTo(s_2) < 0) {
						startP.set(i, start);
						LineSegment  ls = new LineSegment(start, e_2);
						tmp.set(i, ls);
					}
					return;
				} 
				if(s_2.compareTo(start) == 0) {
					if(end.compareTo(e_2) > 0) {
						endP.set(i, end); 
						LineSegment ls = new LineSegment(s_2, end);
						tmp.set(i, ls);
					}
					return;
				}
			}
		}
		LineSegment ls = new LineSegment(start,end);
		tmp.add(ls);
		startP.add(start);
		endP.add(end);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In("input56.txt");
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
