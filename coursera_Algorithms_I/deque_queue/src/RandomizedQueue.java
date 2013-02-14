import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item [] list; // The list of items
	private int n; // Amount of items on the queue
	private int current; // Index of the next item
	
	/**
	 * construct an empty randomized queue
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		list = (Item[]) new Object[1];
		n = 0;
	}
	
	/**
	 * is the queue empty?
	 * @return
	 */
	public boolean isEmpty() {
		return n == 0;
	}
	
	/**
	 * return the number of items on the queue
	 * @return
	 */
	public int size() {
		return n;
	}
	
	// TODO: REMOVER ESTE MÉTODO
	public int capacity() {
		return list.length;
	}
	
	/**
	 * add the item
	 * @param item
	 */
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException("item cannot be null.");
		
		if (current == list.length) resize();
		list[current++] = item;
		++n;
	}
	
	/**
	 * delete and return a random item
	 * @return
	 */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("deque is empty.");
				
		int idx;
		Item item;
		
		for (int i = 0; i < list.length; i++) System.out.print(list[i] + " ");
		System.out.println();
		
		do {			
			idx = (int) StdRandom.uniform(0, list.length);
			item = list[idx];
			System.out.println(idx);
		} while (item == null);
		
		--n;		
		list[idx] = null;
				
		if (n > 0 && n == list.length / 4) resize();
				
		return item;
	}
	
	/**
	 * return (but do not delete) a random item
	 * @return
	 */
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException("deque is empty.");
		
		Item item;
		
		do {
			item = list[(int) StdRandom.uniform(0, list.length)];
		} while (item == null);
				
		return item;
	}
	
	/**
	 * return an independent iterator over items in random order
	 */
	public Iterator<Item> iterator() {
		Iterator<Item> iterator = new RandomizedQueueIterator();
		return iterator;
	}
	
	@SuppressWarnings("unchecked")
	private void resize () {
		
		Item[] copy = (Item[]) new Object[2 * n];
			
		int j = 0;
		for (int i = 0; i < n; i++) {			
			Item item = list[i];
			if (item != null) copy[j++] = item;
		}
		
		current = j;
		list = copy;
	}
	
	/**
	 * The iterator of the deque.
	 * @author irpagnossin
	 */
	private class RandomizedQueueIterator implements Iterator<Item> {
		
		private Item [] l;
		private int nn;
		
		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator () {
			l = (Item[]) new Object[size()];			
			for (int i = 0; i < size(); i++) l[i] = list[i];
			nn = size();
		}
		
		/**
		 * There is another item on the deque?
		 * @return true if yes; false otherwise.
		 */
		public boolean hasNext() {
			return nn > 0;
		}
		
		/**
		 * Iterators cannot remove items from the deque.
		 * Use Deque API instead.
		 */
		public void remove() {
			throw new UnsupportedOperationException("iterators cannot remove elements from the deque.");
		}
		
		/**
		 * Returns the next item on the deque.
		 */
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException("deque is empty.");
			
			int idx;
			Item item;
			
			do {
				idx = (int) StdRandom.uniform(0, list.length);
				item = list[idx];
			} while (item == null);
			
			list[idx] = null;
			--nn;
			
			return item;
		}
	}
}
