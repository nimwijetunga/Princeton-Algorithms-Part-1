import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	
	private int[][] board;
	
	public Board(int [][] blocks) {
		this.board = blocks;
	}
	
	public int dimension() {
		return board.length;
	}
	
	public int hamming() {
		int count = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == 0)continue;
				if(board[i][j] != (this.dimension()*i + j + 1))count++;
			}
		}
		return count;
	}
	
	public int manhattan() {
		int count = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == (this.dimension()*i + j + 1) || board[i][j] == 0)continue;
				int [] cur = {i,j};
				int val = board[i][j] - 1;
				int [] goal = new int[] {val/dimension(), val%dimension()};;
				count += Math.abs(cur[0] - goal[0]) + Math.abs(cur[1] - goal[1]);
			}
		}
		return count;
	}
	
	
	public String toString() {
		String s = this.dimension() + "\n";
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				s += (" " + board[i][j]);
			}
			s += "\n";
		}
		return s;
	}
	
	public Board twin() {
		if(dimension() <= 1) return null;
		int [][] twin = this.get_copy(board);
		int x1,y1,x2,y2;
		if(twin[0][0] != 0) {
			x1 = 0;
			y1 = 0;
			if(twin[0][1] != 0) {
				x2 = 0;
				y2 = 1;
			}else {
				x2 = 1;
				y2 = 0;
			}
		}else{
			x1 = 0;
			y1 = 1;
			x2 = 1;
			y2 = 0;
		}
		
		int val = twin[x1][y1];
		twin[x1][y1] = twin[x2][y2];
		twin[x2][y2] = val;
		return new Board(twin);
	}
	
	@Override
	 public boolean equals(Object y) {
		 if(y == null)return false;
		 if(y == board)return true;
		 if(y.getClass() != this.getClass())return false;
		 final Board obj = (Board) y;
		 return Arrays.deepEquals(obj.board, this.board);
	 }
	 
	 public Iterable<Board> neighbors(){
		 return get_boards();
	 }
	 
	 private ArrayList<Board> get_boards(){
		 ArrayList<Board> board_list = new ArrayList<Board>();
		 
		 int x = 0, y = 0;
		 
		 outer:
		 for(int i = 0; i < board.length; i++) {
			 for(int j = 0; j < board.length; j++) {
				 if(board[i][j] == 0) {
					 x = i;
					 y = j;
					 break outer;
				 }
			 }
		 }
		 //Left Board
		 if(y - 1 >= 0) {
			 //System.out.println("Left");
			 int [][] tmp = get_copy(board);
			 tmp[x][y] = tmp[x][y-1];
			 tmp[x][y-1] = 0;
			 board_list.add(new Board(tmp));
		 }
		 
		 //Right Board
		 if(y + 1 < dimension()) {
			 //System.out.println("Right");
			 int [][] tmp = get_copy(board);
			 tmp[x][y] = tmp[x][y+1];
			 tmp[x][y+1] = 0;
			 board_list.add(new Board(tmp));
		 }
		 
		 //Up Board
		 if(x - 1 >= 0) {
			 //System.out.println("Up");
			 int [][] tmp = get_copy(board);
			 tmp[x][y] = tmp[x-1][y];
			 tmp[x-1][y] = 0;
			 board_list.add(new Board(tmp));
		 }
		 
		 //Down Board
		 if(x + 1 < dimension()) {
			// System.out.println("Down");
			 int [][] tmp = get_copy(board);
			 tmp[x][y] = tmp[x+1][y];
			 tmp[x+1][y] = 0;
			 board_list.add(new Board(tmp));
		 }
		 
		 return board_list;
	 }
	 
	 private int[][] get_copy(int [][] a){
		 int [][] cpy = new int[dimension()][dimension()];
		 for(int i = 0; i < a.length; i++) {
			 for(int j = 0; j < a.length; j++) {
				 cpy[i][j] = a[i][j];
			 }
		 }
		 return cpy;
	 }
	
	public boolean isGoal() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == 0 && (this.dimension()*i + j + 1) == 
						this.dimension()*(this.dimension() - 1) + this.dimension())continue;
				if(board[i][j] != (this.dimension()*i + j + 1))return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{8,1,3},{4,0,2},{7,6,5}});
		System.out.println(board.manhattan());
		Iterable<Board> it = board.neighbors();
		for(Board b : it) {
			//System.out.println(b);
		}
		
		//System.out.println(board.equals(board));
		//System.out.print(board.twin());
	}

}
