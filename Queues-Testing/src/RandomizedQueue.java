import java.util.Iterator;


import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue = (Item[]) new Object[1];
	private int N = 1;
	
	private void resize(int size) {
		Item[] tmp = (Item[]) new Object[size];
		for(int i = 0; i < Math.min(tmp.length, queue.length); i++) {
			tmp[i] = queue[i];
		}
		queue = tmp;
	}
	
	public boolean isEmpty() {
		return N==1;
	}
	
	public int size() {
		return N - 1;
	}
	
	public void enqueue(Item item) {
		if(item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if(queue.length == N)resize(N * 2);
		queue[N - 1] = item;
		N++;
	}
	
	public Item dequeue() {
		if(isEmpty()) throw new java.util.NoSuchElementException();
		int index = StdRandom.uniform(N);
		Item i = queue[index];
		if(N > 0 && size() == queue.length/4)resize(queue.length/2);
		queue[index] = queue[size() - 1];
		queue[size() - 1] = null;
		N--;
		return i;
	}
	
	public Item sample() {
		if(isEmpty()) throw new java.util.NoSuchElementException();
		int index = StdRandom.uniform(N);
		return queue[index];
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new newRQIterator();
	}
	
private class newRQIterator implements Iterator<Item>{
	
		private int current;
		Item[] nq;
		
		private newRQIterator() {
			nq = (Item[]) new Object[size()];
			this.current = 0;
			for(int i = 0; i < size(); i++) {
				nq[i] = queue[i];
			}
		}
		
		@Override
		public boolean hasNext() {
			return (current != (size()));
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new java.util.NoSuchElementException();
			int index = StdRandom.uniform(size() - current);
			Item i = nq[index];
			nq[index] = nq[size() - current - 1];
			nq[size() - current - 1] = null;
			current++;
			return i;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>(); 
		rq.enqueue(5);
		rq.enqueue(6);
		rq.enqueue(8);
		rq.enqueue(9);
		Iterator<Integer> it = rq.iterator();
		while(it.hasNext()) {
			System.out.println(rq.dequeue());
		}
		System.out.println(rq.isEmpty() +" " + rq.size());
	}

}
