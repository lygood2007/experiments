/**
 * A simple generic Deque implementation.
 * @param <Item> The type of data to be stored.
 * @author irpagnossin
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first; // First element on the deque
	private Node last; // Last element on the deque
	private int n; // Amount of elements on the deque
	
	/**
	 * Constructs an empty deque.
	 */
	public Deque() {
		first = null;
		last = null;
		n = 0;
	}
	
	/**
	 * Is the deque empty?
	 * @return true if empty; false otherwise.
	 */
	public boolean isEmpty() {
		return n == 0;
	}
	
	/**
	 * Number of items on the deque.
	 * @return The number of items on the queue.
	 */
	public int size() {
		return n;
	}
	
	/**
	 * Inserts the item at the front of the deque.
	 * @param item Element to insert.
	 * @throws NullPointerException if element is null.
	 */
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException("item cannot be null.");
		
		Node node = new Node();
		node.item = item;
		node.next = first;
		node.prev = null;
		
		if (isEmpty()) {
			first = node;
			last = node;
		}
		else {
			first.prev = node;
			first = node;
		}
		
		++n;
	}
	
	/**
	 * Inserts the item at the end of the deque.
	 * @param item Element to insert.
	 * @throws NullPointerException if element is null.
	 */
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException("item cannot be null.");
		
		Node node = new Node();
		node.item = item;
		node.next = null;
		node.prev = last;
		
		if (isEmpty()) {
			first = node;
			last = node;
		}
		else {
			last.next = node;
			last = node;
		}
		
		++n;
	}
	
	/**
	 * Remove the item at the front of the deque and return it.
	 * @return The item removed from the deque.
	 * @throws NoSuchElementException if deque is empty.
	 */
	public Item removeFirst() {		
		if (isEmpty())
			throw new NoSuchElementException("deque is empty.");
		
		Node node = first;
		
		--n;
		
		if (isEmpty()) {
			first = null;
			last = null;
		}
		else {
			first = first.next;
		}
		
		return node.item;
	}
	
	/**
	 * Remove the item at the end of the deque and return it.
	 * @return The item removed from the deque.
	 * @throws NoSuchElementException if deque is empty.
	 */
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException("deque is empty.");
		
		Node node = last;
		
		--n;
		
		if (isEmpty()) {
			first = null;
			last = null;
		}
		else {
			last = last.prev;
		}
		
		return node.item;
	}
	
	/**
	 * Return an iterator over items in order from front to end.
	 * @return The iterator.
	 */
	public Iterator<Item> iterator() {
		Iterator<Item> iterator = new DequeIterator();
		return iterator;
	}
	
	/**
	 * The iterator of the deque.
	 * @author irpagnossin
	 */
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		
		/**
		 * There is another item on the deque?
		 * @return true if yes; false otherwise.
		 */
		public boolean hasNext() {
			return current != null;
		}
		
		/**
		 * Iterators cannot remove items from the deque.
		 * Use Deque API instead.
		 */
		public void remove() {
			throw new UnsupportedOperationException("iterators "
					+ "cannot remove elements from the deque.");
		}
		
		/**
		 * Returns the next item on the deque.
		 */
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("deque is empty.");
			
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	/**
	 * A node in the linked list (deque)
	 * @author irpagnossin
	 * @param <Item> The type of data to be stored.
	 */
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
}