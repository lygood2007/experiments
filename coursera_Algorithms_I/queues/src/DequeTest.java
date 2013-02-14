import static org.junit.Assert.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeTest {
	
	@Test 
	public void test1() {			
		Deque<String> deque = new Deque<String>();			
		Assert.assertEquals(true, deque.isEmpty());
		Assert.assertEquals(0, deque.size());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test2() {
		Deque<String> deque = new Deque<String>();
		deque.removeFirst();
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test3() {
		Deque<String> deque = new Deque<String>();
		deque.removeLast();
	}
	
	@Test(expected=NullPointerException.class)
	public void test4() {
		Deque<String> deque = new Deque<String>();
		deque.addFirst(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void test5() {
		Deque<String> deque = new Deque<String>();
		deque.addLast(null);
	}
	
	@Test
	public void test6() {
		Deque<String> deque = new Deque<String>();
		deque.addFirst("i");	
		Assert.assertEquals(1, deque.size());
		String item = deque.removeFirst();
		Assert.assertEquals("i", item);
		Assert.assertEquals(true, deque.isEmpty());
	}
	
	@Test
	public void test7() {
		Deque<String> deque = new Deque<String>();
		deque.addFirst("i");	
		Assert.assertEquals(1, deque.size());
		String item = deque.removeLast();
		Assert.assertEquals("i", item);
		Assert.assertEquals(true, deque.isEmpty());
	}
	
	@Test
	public void test8() {
		Deque<String> deque = new Deque<String>();
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addFirst(i.toString());
		}
		
		for (Integer i = 1; i <= 10; i++) {
			Assert.assertEquals(i.toString(), deque.removeLast());
		}
	}
	
	@Test
	public void test9() {
		Deque<String> deque = new Deque<String>();
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addFirst(i.toString());
		}
		
		for (Integer i = 10; i >= 1; i--) {
			Assert.assertEquals(i.toString(), deque.removeFirst());
		}
	}
	
	@Test
	public void test10() {
		Deque<String> deque = new Deque<String>();
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addLast(i.toString());
		}
		
		for (Integer i = 1; i <= 10; i++) {
			Assert.assertEquals(i.toString(), deque.removeFirst());
		}
	}
	
	@Test
	public void test11() {
		Deque<String> deque = new Deque<String>();
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addLast(i.toString());
		}
		
		for (Integer i = 10; i >= 1; i--) {
			Assert.assertEquals(i.toString(), deque.removeLast());
		}
	}
	
	@Test
	public void test12() {
		Deque<String> deque = new Deque<String>();
		Iterator<String> iterator = deque.iterator();
		Assert.assertNotNull(iterator);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test13() {
		Deque<String> deque = new Deque<String>();		
		Iterator<String> iterator = deque.iterator();
		iterator.next();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void test14() {
		Deque<String> deque = new Deque<String>();		
		Iterator<String> iterator = deque.iterator();
		iterator.remove();
	}
	
	@Test
	public void test15() {
		Deque<String> deque = new Deque<String>();
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addFirst(i.toString());
		}
		
		Iterator<String> iterator = deque.iterator();
		
		Integer i = 10;
		while(iterator.hasNext()) {
			String item = iterator.next();
			Assert.assertEquals(i.toString(), item);
			--i;
		}
	}
}
