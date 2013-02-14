import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import junit.framework.Assert;
import org.junit.Test;


public class RandomizedQueueTest {

	@Test
	public void test1() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Assert.assertEquals(0, rq.size());		
		Assert.assertEquals(true, rq.isEmpty());
	}
	
	@Test
	public void test2() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		Assert.assertEquals(0, rq.size());
		rq.enqueue("a");
		Assert.assertEquals("a", rq.dequeue());
		Assert.assertEquals(0, rq.size());		
		Assert.assertEquals(true, rq.isEmpty());
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
	
	public static void main(String [] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		System.out.println("1 = " + rq.capacity());
		rq.enqueue("1");
		System.out.println("1 = " + rq.capacity());
		rq.enqueue("2");
		System.out.println("2 = " + rq.capacity());
		rq.enqueue("3");
		System.out.println("4 = " + rq.capacity());
		rq.enqueue("4");
		System.out.println("4 = " + rq.capacity());
		rq.enqueue("5");
		System.out.println("8 = " + rq.capacity());
		rq.enqueue("6");
		System.out.println("8 = " + rq.capacity());
		rq.enqueue("7");
		System.out.println("8 = " + rq.capacity());
		rq.enqueue("8");
		System.out.println("8 = " + rq.capacity());
		rq.enqueue("9");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("10");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("11");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("12");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("13");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("14");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("15");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("16");
		System.out.println("16 = " + rq.capacity());
		rq.enqueue("17");
		System.out.println("32 = " + rq.capacity());
		System.out.println("-----------------------------");
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 16
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 15
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 14
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 13
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 12
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 11
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 10
		rq.dequeue();
		System.out.println("32 = " + rq.capacity() + "\t" + rq.size()); // 9
		rq.dequeue();
		System.out.println("16 = " + rq.capacity() + "\t" + rq.size()); // 8
		rq.dequeue();
		System.out.println("16 = " + rq.capacity() + "\t" + rq.size()); // 7
		rq.dequeue();
		System.out.println("16 = " + rq.capacity() + "\t" + rq.size()); // 6
		rq.dequeue();
		System.out.println("16 = " + rq.capacity() + "\t" + rq.size()); // 5
		rq.dequeue();
		System.out.println("8 = " + rq.capacity() + "\t" + rq.size()); // 4
		rq.dequeue();
		System.out.println("8 = " + rq.capacity() + "\t" + rq.size()); // 3
		rq.dequeue();
		System.out.println("2 = " + rq.capacity() + "\t" + rq.size()); // 2
		rq.dequeue();
		System.out.println("1 = " + rq.capacity() + "\t" + rq.size()); // 1
		rq.dequeue();
		System.out.println("1 = " + rq.capacity() + "\t" + rq.size()); // 0
	}
}

