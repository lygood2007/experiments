import static org.junit.Assert.*;

import java.util.Iterator;
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
	public static void test13() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		for (int i = 1; i <= 10; i++) rq.enqueue(((Integer) i).toString());
		Iterator<String> iterator1 = rq.iterator();
		Iterator<String> iterator2 = rq.iterator();
		
		String str1 = "";
		String str2 = "";
		
		while(iterator1.hasNext()) str1 += iterator1.next();
		while(iterator2.hasNext()) str2 += iterator2.next();
		
		Assert.assertFalse(str1 == str2); // obs.: eventually this can be equal. Run the test again.
	}
	
	/**
	 * Generates a visual representation of the population/resizing of the queue.
	 */
	public static void main(String [] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		/*
		int N = 100;
				
		for (int i = 1; i <= N; i++) {			
			rq.enqueue("1");
			System.out.println(rq);
		}
		
		for (int i = 1; i <= N; i++) {			
			rq.dequeue();
			System.out.println(rq);
		}*/
		test13();
	}	
}

