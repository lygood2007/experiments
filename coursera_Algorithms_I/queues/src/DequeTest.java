import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeTest {
    
    private Deque<String> deque;
    
    @Before
    public void setup() {
        deque = new Deque<String>();
    }
	
	@Test 
	public void test1() {
		Assert.assertEquals(true, deque.isEmpty());
		Assert.assertEquals(0, deque.size());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test2() {
		deque.removeFirst();
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test3() {
		deque.removeLast();
	}
	
	@Test(expected=NullPointerException.class)
	public void test4() {
		deque.addFirst(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void test5() {
		deque.addLast(null);
	}
	
	@Test
	public void test6() {
		deque.addFirst("i");	
		Assert.assertEquals(1, deque.size());
		String item = deque.removeFirst();
		Assert.assertEquals("i", item);
		Assert.assertEquals(true, deque.isEmpty());
	}
	
	@Test
	public void test7() {
		deque.addFirst("i");	
		Assert.assertEquals(1, deque.size());
		String item = deque.removeLast();
		Assert.assertEquals("i", item);
		Assert.assertEquals(true, deque.isEmpty());
	}
	
	@Test
	public void test8() {
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addFirst(i.toString());
		}
		
		for (Integer i = 1; i <= 10; i++) {
			Assert.assertEquals(i.toString(), deque.removeLast());
		}
	}
	
	@Test
	public void test9() {
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addFirst(i.toString());
		}
		
		for (Integer i = 10; i >= 1; i--) {
			Assert.assertEquals(i.toString(), deque.removeFirst());
		}
	}
	
	@Test
	public void test10() {
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addLast(i.toString());
		}
		
		for (Integer i = 1; i <= 10; i++) {
			Assert.assertEquals(i.toString(), deque.removeFirst());
		}
	}
	
	@Test
	public void test11() {
		
		for (Integer i = 1; i <= 10; i++) {
			deque.addLast(i.toString());
		}
		
		for (Integer i = 10; i >= 1; i--) {
			Assert.assertEquals(i.toString(), deque.removeLast());
		}
	}
	
	@Test
	public void test12() {
		Iterator<String> iterator = deque.iterator();
		Assert.assertNotNull(iterator);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void test13() {	
		Iterator<String> iterator = deque.iterator();
		iterator.next();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void test14() {
		Iterator<String> iterator = deque.iterator();
		iterator.remove();
	}
	
	@Test
	public void test15() {
		
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
	
	@Test
    public void test16() {
        deque.addFirst("1");
        deque.addLast("2");
        Assert.assertEquals("1", deque.removeFirst());
        Assert.assertEquals("2", deque.removeLast());
        
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addLast("3");
        Assert.assertEquals("2", deque.removeFirst());
        deque.addLast("4");
        Assert.assertEquals("1", deque.removeFirst());
        Assert.assertEquals("4", deque.removeLast());
        deque.addLast("5");
        Assert.assertEquals("3", deque.removeFirst());
        Assert.assertEquals("5", deque.removeFirst());        
    }
	
	@Test(expected=java.util.ConcurrentModificationException.class)
	public void test17 () {
		
		deque.addFirst("1");
		deque.addFirst("2");
		
		// Given
		Iterator<String> it = deque.iterator();
		
		// When deque is changed
		deque.addFirst("3");
		
		// Then throws ConcurrentModificationException
		it.next();		
	}
	
	@Test(expected=java.util.ConcurrentModificationException.class)
	public void test18 () {
		
		deque.addFirst("1");
		deque.addFirst("2");
		
		// Given
		Iterator<String> it = deque.iterator();
		
		// When deque is changed
		deque.addFirst("3");
		
		// Then throws ConcurrentModificationException
		it.hasNext();		
	}
}
