package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int [][] map;
	private WeightedQuickUnionUF uf;
	private int n, numOpen;
	
	//0 indicates blocked, 1 indicates open
	public Percolation(int n) {
		if(n <= 0) throw new IllegalArgumentException(n + " is <= 0"); 
		this.numOpen = 0;
		this.n = n;
		uf = new WeightedQuickUnionUF(n*n);
		map = new int[n][n];
		for(int i = 0; i < n; i++) {
			//Connect all top row to map[0][0];
			uf.union(0,i);
			//Connect all bottom row to map[n-1][0];
			uf.union(n*(n-1), (n-1)*n + i);
			for(int j = 0; j < n; j++) {
				map[i][j] = 0;
			}
		}
	}
	
	public void open(int row, int col) {
		if(isOpen(row,col))return;
		row-=1;col-=1;
		map[row][col] = 1;
		numOpen++;
		
		//Make union connections
		
		//Left Node
		int trow = row, tcol = col - 1;
		if(isValid(trow,tcol)) uf.union(n*row + col, n*trow + tcol);
		
		//Right Node
		trow = row; tcol = col + 1;
		if(isValid(trow,tcol)) uf.union(n*row + col, n*trow + tcol);
		
		//Up Node
		trow = row - 1; tcol = col;
		if(isValid(trow,tcol)) uf.union(n*row + col, n*trow + tcol);
		
		//Down Node
		trow = row + 1; tcol = col;
		if(isValid(trow,tcol)) uf.union(n*row + col, n*trow + tcol);
	}
	
	public boolean isOpen(int row, int col) {
		row-=1;col-=1;
		validate(row);
		validate(col);
		return map[row][col] == 1;
	}
	
	public boolean isFull(int row, int col) {
		if(!isOpen(row, col)) return false;
		row-=1;col-=1;
		return uf.connected(0, row*n + col);
	}
	
	public int numberOfOpenSites() {
		return this.numOpen;
	}
	
    public boolean percolates() {
    	//Check to see if map[0][0] is connected to map[n][0]
    	return uf.connected(0, n*(n-1));
    }
	
	private void validate(int p) {
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
	}
	
	private boolean isValid(int p) {
        if (p < 0 || p >= n) {
            return false;
        }
        return true;
	}
	
	private boolean isValid(int row, int col) {
		return (isValid(row) && isValid(col) && map[row][col] == 1);
	}
		
	/*public static void main(String[] args) {
		Percolation p = new Percolation(2);
		p.open(1, 2);
		p.open(1, 1);
		//System.out.println(p.percolates() + " " + p.numberOfOpenSites() + " " + p.isFull(1,2));
	}*/

}
