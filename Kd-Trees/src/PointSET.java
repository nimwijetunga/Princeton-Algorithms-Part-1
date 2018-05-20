import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	
	private SET<Point2D> point_set;
	
	public PointSET() {
		point_set = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return (point_set.size() == 0);
	}
	
	public int size() {
		return point_set.size();
	}
	
	private void err() {
		throw new java.lang.IllegalArgumentException();
	}
	
	public void insert(Point2D p) {
		if(p == null) err();
		point_set.add(p);
	}
	
	public boolean contains(Point2D p) {
		if(p == null) err();
		return point_set.contains(p);
	}
	
	public void draw() {
		StdDraw.setPenRadius(0);
		for(Point2D p: point_set) {
			StdDraw.point(p.x(), p.y());
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null)err();
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		for(Point2D p : point_set) {
			if(rect.contains(p)) points.add(p);
		}
		return points;
	}
	
	public Point2D nearest(Point2D p) {
		if(p == null)return null;
		Point2D closest = null;
		double dist = Double.POSITIVE_INFINITY;
		for(Point2D p_cur : point_set) {
			double cur_dist = p_cur.distanceTo(p);
			if(cur_dist < dist) {
				dist = cur_dist;
				closest = p_cur;
			}
		}
		return closest;
	}
	
}
