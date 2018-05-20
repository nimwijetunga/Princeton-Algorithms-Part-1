import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node{
		Item item;
		Node next;
		Node prev;
	}
	
	private Node first;
	private Node last;
	private int size;
	
	public Deque() {
		size = 0;
		first = null;
		last = null;
	}
	
	public boolean isEmpty() {
		return (size() == 0);
	}
	
	public int size() {
		return this.size;
	}
	
	public void addFirst(Item item) {
		if(item == null) {
			 throw new IllegalArgumentException();
		}
		if(isEmpty()) {
			first = new Node();
			last = new Node();
			first.item = item;
			first.next = null;
			last = first;
		}else {
			Node tmp = new Node();
			tmp.item = item;
			tmp.next = first;
			first.prev = tmp;
			first = tmp;
		}
		first.prev = null;
		this.size++;
	}
	
	public void addLast(Item item) {
		if(item == null) {
			 throw new IllegalArgumentException();
		}
		if(isEmpty()) {
			first = new Node();
			last = new Node();
			last.item = item;
			last.prev = null;
			first = last;
		}else {
			Node tmp = new Node();
			tmp.item = item;
			tmp.prev = last;
			last.next = tmp;
			last = tmp;
		}
		last.next = null;
		this.size++;
	}
	
	public Item removeFirst() {
		if(isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item i = first.item;
		if(first.equals(last)) {//Only one item in the list
			first = null;
			last = first;
		}else {
			first = first.next;
			first.prev = null;
		}
		this.size--;
		return i;
	}
	
	public Item removeLast() {
		if(isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item i = last.item;
		if(first.equals(last)) {//Only one item in the list
			first = null;
			last = first;
		}else {
			last= last.prev;
			last.next = null;
		}
		this.size--;
		return i;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new newDequeIterator();
	}
	
	private class newDequeIterator implements Iterator<Item>{
		
		private Node firstQ;
		private int count;
		
		private newDequeIterator() {
			firstQ = first;
			count = 0;
			
		}
		
		@Override
		public boolean hasNext() {
			return count != size();
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item i = firstQ.item;
			firstQ = firstQ.next;
			count++;
			return i;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		Deque<Integer> d = new Deque<Integer>();
		d.addFirst(5);
		d.addLast(6);
		d.addLast(7);
		d.addFirst(4);
		d.addFirst(18);

		Iterator<Integer> it = d.iterator();
		int count = 0;
		while(it.hasNext()) {
			int i = it.next();
			System.out.println(i);
		}
		//d.print(d);
	}

}
