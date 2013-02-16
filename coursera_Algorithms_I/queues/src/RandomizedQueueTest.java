import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import junit.framework.Assert;
import org.junit.Test;

public class RandomizedQueueTest {

	@Test
	public void test1() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Assert.assertEquals(0, rq.size());		
		Assert.assertTrue(rq.isEmpty());
	}
	
	@Test
	public void test2() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Assert.assertEquals(0, rq.size());
		rq.enqueue("a");
		Assert.assertEquals("a", rq.dequeue());
		Assert.assertEquals(0, rq.size());		
		Assert.assertTrue(rq.isEmpty());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test3() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.dequeue(); // Exception
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test4() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("a");
		rq.dequeue();
		rq.dequeue(); // Exception
	}
	
	@Test(expected=NullPointerException.class)
	public void test5() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue(null); // Exception
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test6() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Iterator<String> iterator = rq.iterator();
		iterator.next(); // Exception
	}
	
	@Test
	public void test7() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Iterator<String> iterator = rq.iterator();
		Assert.assertFalse(iterator.hasNext());
	}
	
	@Test
	public void test8() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("1");		
		Iterator<String> iterator = rq.iterator();		
		Assert.assertTrue(iterator.hasNext());
	}
	
	@Test
	public void test9() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		for (int i = 1; i <= 10; i++) rq.enqueue("1");
		Iterator<String> iterator = rq.iterator();
		
		for (int i = 1; i <= 9; i++) {
			Assert.assertNotNull(iterator.next());
			Assert.assertTrue(iterator.hasNext());
		}
		
		Assert.assertNotNull(iterator.next());
		Assert.assertFalse(iterator.hasNext());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void test10() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Iterator<String> iterator = rq.iterator();
		iterator.remove();
	}
	
	@Test
	public void test11() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int N = 10;
		
		for (int i = 1; i <= N; i++) rq.enqueue("1");
		
		for (int i = 1; i <= N; i++) {
			rq.sample();
			Assert.assertFalse(rq.isEmpty());
			Assert.assertEquals(N, rq.size());
		}
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test12() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.sample(); // Exception
	}
	
	@Test
	public void test13() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		for (int i = 0; i <= 9; i++) rq.enqueue(((Integer) i).toString());
		Iterator<String> iterator1 = rq.iterator();
		Iterator<String> iterator2 = rq.iterator();
		
		String str1 = "";
		String str2 = "";
		
		while(iterator1.hasNext()) str1 += iterator1.next();
		while(iterator2.hasNext()) str2 += iterator2.next();
		
		Assert.assertFalse(str1 == str2); // obs.: eventually this can be equal. Run the test again.
	}
	
	@Test(expected=java.util.ConcurrentModificationException.class)
	public void test14 () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		rq.enqueue("1");
		rq.enqueue("2");
		
		// Given
		Iterator<String> it = rq.iterator();
		
		// When queue is changed
		rq.enqueue("3");
		
		// Then throws ConcurrentModificationException
		it.hasNext();		
	}	
	
	@Test(expected=java.util.ConcurrentModificationException.class)
	public void test15 () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		rq.enqueue("1");
		rq.enqueue("2");
		
		// Given
		Iterator<String> it = rq.iterator();
		
		// When queue is changed
		rq.enqueue("3");
		
		// Then throws ConcurrentModificationException
		it.next();		
	}
	
	@Test
	public void fillEmptyFillEmpty() {
		
		// Given an empty queue
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		// When...
		int N = (int) (1000 * Math.random() + 100);
		
		// ... filled...
		for (int i = 0; i < N; i++) {
			rq.enqueue("a");
		}
		
		// ... emptied...
		for (int i = 0; i < N; i++) {
			rq.dequeue();
		}
		
		N = (int) (1000 * Math.random() + 100);
		
		// ... filled again...
		for (int i = 0; i < N; i++) {
			rq.enqueue("a");
		}
		
		// ... emptied again...
		for (int i = 0; i < N; i++) {
			rq.dequeue();
		}
		
		// ... then queue must be empty
		Assert.assertTrue(rq.isEmpty());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void fillEmptyFillEmptyException() {
		
		// Given an empty queue
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		// When...
		int N = (int) (1000 * Math.random() + 100);
		
		// ... filled...
		for (int i = 0; i < N; i++) {
			rq.enqueue("a");
		}
		
		// ... emptied...
		for (int i = 0; i < N; i++) {
			rq.dequeue();
		}
		
		N = (int) (1000 * Math.random() + 100);
		
		// ... filled again...
		for (int i = 0; i < N; i++) {
			rq.enqueue("a");
		}
		
		// ... emptied again...
		for (int i = 0; i < N; i++) {
			rq.dequeue();
		}
		
		// ... then queue must be empty (throws an exception)
		rq.dequeue();
	}
	
	@Test
	public void hugeEnqueueDequeue () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int N = 10000;
		
		for (int i = 0; i < N; i++) {
			if (Math.random() < 0.5 && !rq.isEmpty()) rq.dequeue();
			else rq.enqueue("1");
		}
		
		Assert.assertTrue(rq.size() >= 0);
	}
	
	@Test
	public void doubleEnqueueDequeue() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int M = 100;
		int N = 1000;
		
		for (int j = 0; j < M; j++) {		
			for (int i = 0; i < N; i++) rq.enqueue("1");
			for (int i = 0; i < N; i++) rq.dequeue();
		}
		
		Assert.assertTrue(rq.isEmpty());
	}
	
	@Test
	public void hugeEnqueueDequeueSample () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int N = 10000;
		double third = 1.0/3;
		double rnd;
		
		for (int i = 0; i < N; i++) {
			rnd = Math.random();
			if (rnd < third && !rq.isEmpty()) rq.dequeue();
			else if (rnd >= third && rnd < 2*third && !rq.isEmpty()) rq.sample();
			else rq.enqueue("1");
		}
		
		Assert.assertTrue(rq.size() >= 0);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void iteratorReturnsCorrectNumberOfItems () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int N = 10000;
		
		// Given an randomly populated queue
		for (int i = 0; i < N; i++) {
			if (Math.random() < 0.5 && !rq.isEmpty()) rq.dequeue();
			else rq.enqueue("1");
		}
		
		// When iterate through all items
		Iterator<String> iterator = rq.iterator();
		int size = rq.size();
		for (int i = 0; i < size; i++) {
			iterator.next();
		}
		
		// Then next() throws exception (iterator list is empty)
		iterator.next();
	}
	
	@Test
	public void correctSizeAfterRandomSequenceOfEnqueueDequeue () {
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();		
		LinkedList<String> list = new LinkedList<String>();
		
		int N = 30000;
		
		String item;
		for (Integer i = 0; i < N; i++) {
			if (!rq.isEmpty() && Math.random() < 0.5) {
				item = rq.dequeue();
				list.remove(list.indexOf(item));
			}
			else {
				item = i.toString();
				rq.enqueue(item);
				list.push(item);
			}
		}		
		
		Assert.assertEquals(rq.size(), list.size());
	}
	
	@Test
	public void iteratorReturnsCorrectItemsAfterSequenceOfEnqueueAndDequeue () {
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();		
		LinkedList<String> list = new LinkedList<String>();
		
		int N = 30000;
		
		String item;
		for (Integer i = 0; i < N; i++) {
			if (!rq.isEmpty() && Math.random() < 0.5) {
				item = rq.dequeue();
				list.remove(list.indexOf(item));
			}
			else {
				item = i.toString();
				rq.enqueue(item);
				list.push(item);
			}
		}
		
		Iterator<String> iterator = rq.iterator();
		LinkedList<String> list2 = new LinkedList<String>();
		while(iterator.hasNext()) {
			item = iterator.next();
			list2.push(item);
		}
		
		Assert.assertTrue(equivalent(list,list2));
	}
	
	public static <T> boolean equivalent (LinkedList<T> l1, LinkedList<T> l2) {
		if (l1.size() != l2.size()) return false;
		
		boolean ans = true;
		for (int i = 0; i < l1.size() && ans; i++) {
			boolean localans = false;
			for (int j = 0; j < l2.size(); j++) {
				if (l1.get(i) == l2.get(j)) {
					localans = true;
					break;
				}
			}
			ans = ans && localans;			
		}
		
		return ans;
	}
	// Requires method capacity() on RandomizedQueue
	/*
	@Test
	public void resizing1 () {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int n = 12;
		
		// when inserting 2N+1 items
		int N = (int) Math.pow(2, n);
		for (int i = 1; i <= 2*N+1; i++) rq.enqueue("aa");
		int capacity_before = rq.capacity();
		
		// and removing N+1 items
		for (int i = 1; i <= N+1; i++) rq.dequeue();
		int capacity_after = rq.capacity();
				
		// then queue shrinks to its half
		Assert.assertEquals(2, capacity_before/capacity_after);
	}
	
	@Test
	public void resizing2 () {
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int n = 12;
		
		// when inserting N items
		int N = (int) Math.pow(2, n);
		for (int i = 1; i <= N; i++) rq.enqueue("aa");
		
		// and removing N-1 items
		for (int i = 1; i <= N-1; i++) rq.dequeue();
		int capacity_after = rq.capacity();
		
		// Capacity (memory) is 2
		Assert.assertEquals(2, capacity_after);
	}*/
	
	/**
	 * Generates a visual representation of the population/resizing of the queue.
	 */
	public static void main(String [] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		int N = 100;
				
		for (int i = 1; i <= N; i++) {			
			rq.enqueue("1");
			System.out.println(rq);
		}
		
		for (int i = 1; i <= N; i++) {			
			rq.dequeue();
			System.out.println(rq);
		}
	}	
}

