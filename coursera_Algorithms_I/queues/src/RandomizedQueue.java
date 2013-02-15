/**
 * A simple generic Randomized Queue implementation.
 * @param <Item> The type of data to be stored.
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 * @version: 2013.02.14
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item [] list; // The list of items
    private int n; // Amount of items on the queue
    private int current; // Position to insert another item

    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        list = (Item[]) new Object[2];
        n = 0;
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
    }

    /**
     * Deletes and return a random item.
     * @return The item removed from the queue.
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty.");

        int idx;
        Item item;

        do {
            idx = (int) StdRandom.uniform(0, list.length);
            item = list[idx];
        } while (item == null);

        --n;
        list[idx] = null;

        if (n > 0 && n == list.length / 4) resize();

        return item;
    }

    /**
     * Return (but do not delete) a random item.
     * @return The item sampled from the queue.
     */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty.");

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
        Iterator<Item> iterator = new RandomizedQueueIterator(list, n);
        return iterator;
    }

    /*
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) str += "-";
            else str += "x";
        }
        return str;
    }
    */

    /**
     * @private
     * Resizes the queue.
     */
    private void resize() {

        Item[] copy = (Item[]) new Object[2 * n];

        int j = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) copy[j++] = list[i];
        }

        current = j;
        list = copy;
    }

    /**
     * @private
     * The iterator of the deque.
     * @author irpagnossin
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item [] iteratorList;
        private int idx;

        public RandomizedQueueIterator(Item[] list, int size) {
            iteratorList = (Item[]) new Object[size];

            int j = 0;
            for (int i = 0; i < size(); i++) {
                if (list[i] != null) iteratorList[j++] = list[i];
            }

            StdRandom.shuffle(iteratorList);
            idx = 0;
        }

        /**
         * There is another item on the deque?
         * @return true if yes; false otherwise.
         */
        public boolean hasNext() {
            return idx < iteratorList.length;
        }

        /**
         * Iterators cannot remove items from the deque.
         * Use Deque API instead.
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "iterators cannot remove elements from the deque.");
        }

        /**
         * Returns the next item on the deque.
         */
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("deque is empty.");

            return iteratorList[idx++];
        }
    }
}
