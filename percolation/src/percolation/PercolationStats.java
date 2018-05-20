package percolation;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;

public class PercolationStats {
	
	private double [] a;
	public PercolationStats(int n, int trials) {
		if(n <= 0 || trials <= 0)  throw new IllegalArgumentException("Invalid");
		a = new double[trials];
		for(int i = 0; i < trials; i++) {
			a[i] = getPerc(n);
		}
	}
	
	
	public double mean() {
		return StdStats.mean(a);
	}
	
	public double stddev() {
		return StdStats.stddev(a);
	}
	
	public double confidenceLo() {
		return mean() - (1.96*stddev()/Math.sqrt(a.length));
	}
	
	public double confidenceHi() {
		return mean() + (1.96*stddev()/Math.sqrt(a.length));
	}
	
	//For a single trial return the percolation threshold
	private double getPerc(int n) {
		double total = n*n;
		Percolation p = new Percolation(n);
		while(!p.percolates()) {
			int row = StdRandom.uniform(n)+ 1;
			int col = StdRandom.uniform(n) + 1;
			p.open(row, col);
		}
		double openSites = (double)(p.numberOfOpenSites());
		return openSites/total;
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n,T);
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
	}

}
