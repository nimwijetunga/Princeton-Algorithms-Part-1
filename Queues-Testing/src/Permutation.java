import java.util.Arrays;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	
	
	public static void merge_sort(int [] a) {
		
		int size = a.length;
		if(size == 1) return;
		int mid = size/2;
		int [] left = Arrays.copyOfRange(a, 0, mid);
		int[] right = Arrays.copyOfRange(a, mid, size);
		
		merge_sort(left);
		merge_sort(right);
		merge(left,right,a);		
	}
	
	public static void merge(int [] l, int [] r, int []a) {
		int i = 0, j = 0, k = 0;
		while(i < l.length && j < r.length) {
			if(l[i] < r[j]) {
				a[k++] = l[i++];
			}else
				a[k++] = r[j++];
		}
		
		while(i < l.length) {
			a[k++] = l[i++];
		}
		
		while(j < r.length) {
			a[k++] = r[j++];
		}
	}

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		 while (!StdIn.isEmpty()) {
		      rq.enqueue(StdIn.readString());
		  }
		Iterator<String> it = rq.iterator();
		for(int i = 0; i < k; i++) {
			System.out.println(it.next());
		}
		/*int []a = {9,18,9,3,4,1,34,5,6};
		merge_sort(a);
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}*/
	}
	
	

}
