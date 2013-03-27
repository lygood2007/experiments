
public class Main {

	private int n_merge = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] list = {70, 54, 42, 35, 21, 57, 30, 12, 17, 77, 69, 55};
		Main main = new Main();
		main.sort(list);
	}
	
	public void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length-1);
	}	
	
	private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		//assert isSorted(a, lo, mid);
		//assert isSorted(a, mid+1, hi);
		
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if      (i > mid)              a[k] = aux[j++];
			else if (j > hi)               a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else                           a[k] = aux[i++];
		}
		
		++n_merge;
		print(n_merge, a);
		
		//assert isSorted(a, lo, hi);
	}
	
	private void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid+1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	private boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
	private void print(int n, Comparable[] a) {
		StringBuffer output = new StringBuffer(n + "\t");
		for (int i = 0; i < a.length; i++) {
			output.append(a[i]);
			if (i < a.length-1) output.append(", ");
		}
		System.out.println(output);
	}
}
