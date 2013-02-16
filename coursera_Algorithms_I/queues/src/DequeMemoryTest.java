import com.javamex.classmexer.MemoryUtil;

public class DequeMemoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int N = (int) Math.pow(2, 12);

		System.out.println("N\t+(2N+1)-(N+1)\t+(N)\t+(N)-(N-1)");
		for (int n = 2; n <= 2*N+1; n *= 2) {
			long bytes1 = test1(n);
			long bytes2 = test2(n);
			long bytes3 = test3(n);
			
			System.out.println(n + "\t" + bytes1 + "\t\t" + bytes2 + "\t" + bytes3);
		}
	}
	
	public static long test1(int N) {
		Deque<String> deque = new Deque<String>();
		
		for (int i = 1; i <= 2*N; i++) deque.addFirst("1");			
		for (int i = 1; i <= N; i++) deque.removeLast();
		
		long bytes = MemoryUtil.deepMemoryUsageOf(deque);
		
		return bytes;
	}
	
	public static long test2(int N) {
		Deque<String> deque = new Deque<String>();
		
		for (int i = 1; i <= N; i++) deque.addFirst("1");
		
		long bytes = MemoryUtil.deepMemoryUsageOf(deque);
		
		return bytes;
	}
	
	public static long test3(int N) {
		Deque<String> deque = new Deque<String>();
		
		for (int i = 1; i <= N; i++) deque.addFirst("1");			
		for (int i = 1; i <= N-1; i++) deque.removeLast();
		
		long bytes = MemoryUtil.deepMemoryUsageOf(deque);
		
		return bytes;
	}
}
