import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private int moves;
	private boolean solvable;
	
	private class Node implements Comparable<Node>{
		Board current;
		int numMoves;
		Node prev;
		
		@Override
		public int compareTo(Node that) {
			int this_p = current.manhattan() + this.numMoves;
			int that_p = that.current.manhattan() + that.numMoves;
			if(this_p < that_p) return -1;
			else if(this_p > that_p) return 1;
			return 0;
		}
	}
	
	private MinPQ<Node> pq;
	private ArrayList<Board> soln;
	
	public Solver(Board initial) {
		if(initial == null) throw new java.lang.IllegalArgumentException();
		this.moves = -1;
		soln = new ArrayList<Board>();
		
		pq = new MinPQ<Node>();
		
		MinPQ<Node> pq_twin = new MinPQ<Node>();
		
		Node init = new Node();
		init.current = initial;
		init.numMoves = 0;
		init.prev = null;
		
		pq.insert(init);
		
		Node init_twin = new Node();
		init_twin.current = initial.twin();
		init_twin.numMoves = 0;
		init_twin.prev = null;
				
		pq_twin.insert(init_twin);
		
		Node deq = pq.delMin();
		Node deq_twin = pq_twin.delMin();
		
		while(!deq.current.isGoal() && !deq_twin.current.isGoal()) {
			for(Board b : deq.current.neighbors()) {
				if(should_add(deq,b)) {
					Node tmp = new Node();
					tmp.current = b;
					tmp.prev = deq;
					tmp.numMoves = deq.numMoves+1;
					pq.insert(tmp);
				}
			}			
			
			for(Board b : deq_twin.current.neighbors()) {
				if(should_add(deq_twin,b)) {
					Node tmp = new Node();
					tmp.current = b;
					tmp.prev = deq_twin;
					tmp.numMoves = deq_twin.numMoves+1;
					pq_twin.insert(tmp);
				}
			}
			deq = pq.delMin();
			deq_twin = pq_twin.delMin();
			
		}
		
		this.solvable = deq.current.isGoal();
		while(deq != null) {
			soln.add(deq.current);
			deq = deq.prev;
			this.moves++;
		}
		Collections.reverse(soln);
	}
	
	private boolean should_add(Node last_step, Board board) {
		Node cur = last_step;
		while(cur != null) {
			if(cur.current.equals(board)) {
				return false;
			}
			cur = cur.prev;
		}
		return true;
	}
	
	public boolean isSolvable() {
		return this.solvable;
	}
	
	public int moves() {
		if(!isSolvable())return -1;
		return this.moves;
	}
	
	public Iterable<Board> solution(){
		if(!isSolvable())return null;
		return this.soln;
		
	}
	
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In("puzzle31.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	           StdOut.println(board);
	    }
	}

}
