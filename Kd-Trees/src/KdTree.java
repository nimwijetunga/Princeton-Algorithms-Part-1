import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private final boolean VERT = true, HORIZ = false;
	private int size;
	
	private class Node{
		Point2D point;
		Node left, right;
		boolean orientation;
		RectHV rect;
		public Node(Point2D point, Node left, Node right,
				boolean orientation, RectHV rect) {
			this.point = point;
			this.left = left;
			this.right = right;
			this.orientation = orientation;
			if(rect == null)this.rect = new RectHV(0,0,1,1);
			else this.rect = rect;
		}
	}
	
	private Node first;
	
	public KdTree() {
		first = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return (first == null || size == 0);
	}
	
	public int size() {
		return this.size;
	}
	
	private void err() {
		throw new java.lang.IllegalArgumentException();
	}
	
	public void insert(Point2D p) {
		if(p == null) err();
		if(isEmpty())
			first = rec_insert(first,p,null,0);
		else
			first = rec_insert(first,p,first.rect, 0);
			
	}
	
	private RectHV left_rect(Node root, RectHV rect) {
		if(root.orientation == VERT && root.left == null) {
			return new RectHV(rect.xmin(), rect.ymin(), root.point.x(), rect.ymax());
		}else if(root.orientation == HORIZ && root.left == null) {
			return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), root.point.y());
		}
		return root.left.rect;
	}
	
	private RectHV right_rect(Node root, RectHV rect) {
		if(root.orientation == VERT && root.right == null) {
			return new RectHV(root.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
		}else if(root.orientation == HORIZ && root.right == null) {
			return new RectHV(rect.xmin(), root.point.y(), rect.xmax(), rect.ymax());
		}
		return root.right.rect;
	}
	
	private Node rec_insert(Node root, Point2D p, RectHV rect, int depth) {
		if(root == null) {
			boolean or;
			if(depth % 2 == 0)or = VERT;
			else or = HORIZ;
			root = new Node(p, null, null, or, rect);
			this.size++;
			return root;
		}else if(root.point.equals(p))return root;
		RectHV l = left_rect(root,rect);
		RectHV r = right_rect(root,rect);
		if(depth % 2 == 0) {//even use x
			if(p.x() <= root.point.x()) root.left =  rec_insert(root.left,p,l,depth+1);
			else root.right = rec_insert(root.right,p,r,depth+1);
		}else {//odd use y
			if(p.y() <= root.point.y()) root.left = rec_insert(root.left,p,l,depth+1);
			else root.right = rec_insert(root.right,p,r,depth+1);
		}
		return root;
	}
	
	public boolean contains(Point2D p) {
		if(p == null) err();
		return rec_contains(first,p);
	}
	
	private boolean rec_contains(Node root, Point2D p) {
		if(root == null) return false;
		else if(root.point.equals(p)) return true;
		
		if(root.orientation == VERT) {
			if(p.x() <= root.point.x()) return rec_contains(root.left,p);
			else return rec_contains(root.right,p);
		}else {//odd use y
			if(p.y() <= root.point.y()) return rec_contains(root.left,p);
			else return rec_contains(root.right,p);
		}
	}
	
	public void draw() {
		StdDraw.setPenRadius(0);
		rec_draw(first);
	}
	
	private void rec_draw(Node root) {
		if(root == null) return;
		rec_draw(root.left);
		StdDraw.point(root.point.x(), root.point.y());
		rec_draw(root.right);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null)err();
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		rec_range(first, rect, points);
		return points;
	}
	
	private boolean[] search_vert(Node root, RectHV r) {
		boolean [] s = new boolean[2];//0-left, 1-right
		if(root.point.x() > r.xmax()) {
			s[0] = true;
			s[1] = false;
		}else if(root.point.x() < r.xmin()) {
			s[0] = false;
			s[1] = true;
		}else {
			s[0] = true;
			s[1] = true;
		}
		return s;
	}
	
	private boolean[] search_horiz(Node root, RectHV r) {
		boolean [] s = new boolean[2];//0-below, 1-above
		if(root.point.y() > r.ymax()) {
			s[0] = true;
			s[1] = false;
		}else if(root.point.y() < r.ymin()) {
			s[0] = false;
			s[1] = true;
		}else {
			s[0] = true;
			s[1] = true;
		}
		return s;
	}
	
	private void rec_range(Node root, RectHV rect, ArrayList<Point2D> points) {
		if(root == null) return;
		if(rect.contains(root.point)) {
			points.add(root.point);
		}	
		boolean [] search;
		if(root.orientation == this.HORIZ) {
			search = search_horiz(root, rect);
		}else {
			search = search_vert(root,rect);
		}
		if(search[0] && search[1]) {
			rec_range(root.left, rect,points);
			rec_range(root.right, rect,points);
		}else if(search[0]) {
			rec_range(root.left, rect,points);
		}else if(search[1]) {
			rec_range(root.right, rect,points);
		}
		
	}
	
	public Point2D nearest(Point2D p) {
		if(p == null)return null;
		double inf = Double.MAX_VALUE;
		Point2D closest = new Point2D(inf, inf);
		return rec_nearest(first,p,closest);
	}
	
	private boolean should_search_right(Node root, Point2D p, double dist) {
		if(root.right == null) return false;
		if(root.right.rect.distanceSquaredTo(p) < dist)return true;
		return false;
	}
	
	private boolean should_search_left(Node root, Point2D p, double dist) {
		if(root.left == null) return false;
		if(root.left.rect.distanceSquaredTo(p) < dist)return true;
		return false;
	}
	
	private Point2D rec_nearest(Node root, Point2D p, Point2D closest) {
		if(root == null) return closest;
		if(root.point.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
			closest = root.point;
		
		Point2D cur = root.point;
		
		if((root.orientation == this.VERT &&
				p.x() <= cur.x()) || (root.orientation == this.HORIZ
				&& p.y() <= cur.y())) {
			closest = rec_nearest(root.left, p, closest);
			double dist = closest.distanceSquaredTo(p);
			if(should_search_right(root, p, dist)) closest = rec_nearest(root.right, p, closest);
		}else if((root.orientation == this.VERT &&
				p.x() > cur.x()) || (root.orientation == this.HORIZ
				&& p.y() > cur.y())) {
			closest = rec_nearest(root.right, p, closest);
			double dist = closest.distanceSquaredTo(p);
			if(should_search_left(root, p, dist)) closest = rec_nearest(root.left, p, closest);
		}
		return closest;
	}
	
	public static void main(String [] args) {
		KdTree t = new KdTree();
		Point2D [] points = new Point2D[5];
		points[0] = new Point2D(0.0,0.625);
		points[1] = new Point2D(0.125,0.375);
		points[2] = new Point2D(0.875,1.0);
		points[3] = new Point2D(0.75,0.875);
		points[4] = new Point2D(0.375,0.0);
		for(Point2D p : points) {
			t.insert(p);
		}
		t.draw();
		System.out.println(t.contains(new Point2D(0.125,0.375)));
	}
}
