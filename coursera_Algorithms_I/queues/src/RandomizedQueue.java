import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple generic Randomized Queue implementation.
 * @param <Item> The type of data to be stored.
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 * @version: 2013.02.14
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item [] list; // The list of items
    private int n; // Amount of items on the queue
    private int current; // Position to insert another item
    private long key; // Count the number of changes on queue

    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        list = (Item[]) new Object[2];
        n = 0;
        key = 0;
    }

    /**
     * Is the queue empty?
     * @return true if empty; false otherwise.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items on the queue.
     * @return Number of items on the queue.
     */
    public int size() {
        return n;
    }

    /**
     * Add an item.
     * @param item Element to insert.
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null.");

        if (current == list.length) resize();
        
        list[current++] = item;
        ++n;
        
        update();
    }

    /**
     * Deletes and return a random item.
     * @return The item removed from the queue.
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty.");

        int idx;
        Item item;

        do {
            idx = (int) StdRandom.uniform(0, list.length);
            item = list[idx];
        } while (item == null);

        --n;
        list[idx] = null;

        if (n > 0 && n == list.length / 4) resize();

        update();
        
        return item;
    }

    /**
     * Return (but do not delete) a random item.
     * @return The item sampled from the queue.
     */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty.");

        Item item;

        do {
            item = list[(int) StdRandom.uniform(0, list.length)];
        } while (item == null);

        return item;
    }

    /**
     * Returns an independent iterator over items in random order
     * @return The iterator
     */
    public Iterator<Item> iterator() {
        Iterator<Item> iterator = new RandomizedQueueIterator();
        return iterator;
    }    
    
    /**
     * Prints the items on the queue.
     */
    @Override
    public String toString() {
        String output = "[";
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                output += list[i] + (++count == n ? "" : ", ");
            }
        }
        output += "]";
        return output;
    }    
    
    /**
     * Prints a visual representation of the occupation of the list.
	 * Just for fun.
     */
    public String xray() {
        String str = "";
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) str += "-";
            else str += "x";
        }
        return str;
    }    
    
    /**
     * @private
     * Resizes the queue.
     */
    private void resize() {

        int size = 2 * n;
        if (size == 0) size = 2;
     
        Item[] copy = (Item[]) new Object[size];

        int j = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) copy[j++] = list[i];
        }

        current = j;
        list = copy;
    }
    
    /**
     * @private
     * Count changes on queue
     */
    private void update() {
     ++key;
    }

    /**
     * @private
     * The iterator of the queue.
     * @author irpagnossin
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item [] iteratorList; // Copy of the queue list
        private int idx; // Index of the next item
        private long iteratorKey; // # of changes on queue

        /**
         * Creates an iterator over queue.
         */
        public RandomizedQueueIterator() {
         
            iteratorList = (Item[]) new Object[size()];

            int j = 0;
            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) iteratorList[j++] = list[i];
            }
            
            StdRandom.shuffle(iteratorList);
            
            idx = 0;
            
            iteratorKey = key;
        }

        /**
         * There is another item on the queue?
         * @return true if yes; false otherwise.
         */
        public boolean hasNext() {
         if (modified())          
          throw new ConcurrentModificationException();
         
            return idx < iteratorList.length;
        }

        /**
         * Iterators cannot remove items from the queue.
         * Use RandomizedQueue API instead.
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "iterators cannot remove elements from the queue.");
        }

        /**
         * Returns the next item on the queue.
         */
        public Item next() {
         if (modified())          
          throw new ConcurrentModificationException();
         
            if (!hasNext())
                throw new NoSuchElementException("queue is empty.");

            return iteratorList[idx++];
        }
        
        /**
         * @private
         * Checks if the queue was changed since the creation of the iterator.
         * @return true if yes; false otherwise.
         */
        private boolean modified() {
         return iteratorKey != key;
        }
    }
}
