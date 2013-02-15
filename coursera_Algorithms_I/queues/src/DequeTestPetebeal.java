/**
 * Test written by Petebeal:
 * https://gist.github.com/petebeal/4947952
 */
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DequeTestPetebeal {

    private Deque<Integer> underTest;


    @Before
    public void setup() {

        underTest = new Deque<Integer>();
    }

    @Test
    public void shouldCreateEmptyDeque() {

        // when
        final boolean isEmpty  = underTest.isEmpty();

        // then
        assertThat(isEmpty, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfAddFirstNull() {

        // when
        underTest.addFirst(null);
    }

    @Test
    public void shouldNotBeEmptyAfterAddFirst() {

        // when
        underTest.addFirst(Integer.valueOf("10"));

        // then
        final boolean isEmpty  = underTest.isEmpty();
        assertThat(isEmpty, is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionOnRemoveFirstIfEmpty() {

        // when
        underTest.removeFirst();
    }

    @Test
    public void shouldReturnFirstItem() {

        // given
        final Integer first = Integer.valueOf("10");
        underTest.addFirst(first);

        // then
        final Integer returnedFirst = underTest.removeFirst();

        // then
        assertThat(returnedFirst, is(first));
    }

    @Test
    public void shouldBeEmptyAfterRemovalOfItem() {

        // given
        underTest.addFirst(Integer.valueOf("10"));
        underTest.removeFirst();

        // when
        final boolean isEmpty  = underTest.isEmpty();

        // then
        assertThat(isEmpty, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionAfterAllItemsRemoved() {

        // given
        underTest.addFirst(Integer.valueOf("10"));
        underTest.removeFirst();

        // when
        underTest.removeFirst();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfAddLastNull() {

        // when
        underTest.addLast(null);
    }

    @Test
    public void shouldNotBeEmptyAfterAddLast() {

        // when
        underTest.addLast(Integer.valueOf("10"));

        // then
        final boolean isEmpty  = underTest.isEmpty();
        assertThat(isEmpty, is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionOnRemoveLastIfEmpty() {

        // when
        underTest.removeLast();
    }

    @Test
    public void shouldReturnLastItem() {

        // given
        final Integer last = Integer.valueOf("10");
        underTest.addLast(last);

        // then
        final Integer returnedLast = underTest.removeLast();

        // then
        assertThat(returnedLast, is(last));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionAfterAllItemsRemoved2() {

        // given
        underTest.addLast(Integer.valueOf("10"));
        underTest.removeLast();

        // when
        underTest.removeLast();
    }

    @Test
    public void shouldAddFirstAndRemoveLast() {

        // given
        final Integer first = Integer.valueOf("10");
        underTest.addFirst(first);
        final Integer removedLast = underTest.removeLast();

        // when
        assertThat(removedLast, is(first));
        final boolean isEmpty  = underTest.isEmpty();
        assertThat(isEmpty, is(true));
    }

    @Test
    public void shouldAddLastAndRemoveFirst() {

        // given
        final Integer last = Integer.valueOf("10");
        underTest.addLast(last);
        final Integer removedFirst = underTest.removeFirst();

        // when
        assertThat(removedFirst, is(last));
        final boolean isEmpty  = underTest.isEmpty();
        assertThat(isEmpty, is(true));
    }

    @Test
    public void shouldHaveFirstInLastOutBehaviourUsingAddFirstRemoveFirst() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);

        // when
        addFirstIntegers(numbers);
        final Integer[] numbersOut = new Integer[size];
        for (int i = 4; i > -1; i--) {
            numbersOut[i] = underTest.removeFirst();
        }

        // then
        assertIntegersMatch(numbers, numbersOut);
    }

    private void addFirstIntegers(Integer[] numbers) {
        for (Integer number : numbers) {
            underTest.addFirst(number);
        }
    }

    @Test
    public void shouldHaveFirstInLastOutBehaviourUsingAddLastRemoveLast() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);

        // when
        addLastIntegers(numbers);
        final Integer[] numbersOut = new Integer[size];
        for (int i = 4; i > -1; i--) {
            numbersOut[i] = underTest.removeLast();
        }

        // then
        assertIntegersMatch(numbers, numbersOut);
    }

    private void addLastIntegers(Integer[] numbers) {
        for (Integer number : numbers) {
            underTest.addLast(number);
        }
    }

    @Test
    public void shouldHaveFirstInFirstOutUsingAddFirstAndRemoveLast() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);

        // when
        addFirstIntegers(numbers);
        final Integer[] numbersOut = new Integer[size];
        for (int i = 0; i < size; i++) {
            numbersOut[i] = underTest.removeLast();
        }

        // then
        assertIntegersMatch(numbers, numbersOut);
    }

    @Test
    public void shouldHaveFirstInFirstOutUsingAddLastAndRemoveFirst() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);

        // when
        addLastIntegers(numbers);
        final Integer[] numbersOut = new Integer[size];
        for (int i = 0; i < size; i++) {
            numbersOut[i] = underTest.removeFirst();
        }

        // then
        assertIntegersMatch(numbers, numbersOut);
    }

    @Test
    public void shouldHaveInitialSizeOfZero() {

        // when
        final int size = underTest.size();

        // then
        assertThat(size, is(0));
    }

    @Test
    public void  shouldHaveCorrectSizeAfterAddingItems() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);
        addLastIntegers(numbers);

        // when
        final int actualSize = underTest.size();

        // then
        assertThat(actualSize, is(size));
    }

    @Test
    public void shouldHaveNoNextInIteratorIfNothingAdded() {

        // given
        final Iterator<Integer> iter = underTest.iterator();
        final boolean hasNext = iter.hasNext();

        // when
        assertThat(hasNext, is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionOnIteratorNextIfNothingAdded() {

        // when
        underTest.iterator().next();
    }

    @Test
    public void shouldIterateItemsInOrderAfterAddLast() {

        // given
        final int size = 5;
        final Integer[] numbers = createIntegers(size);
        addLastIntegers(numbers);

        // when
        final Integer[] numbersOut = new Integer[size];
        int i = 0;
        for (Integer number : underTest) {

            numbersOut[i++] = number;
        }

        // then
        assertIntegersMatch(numbers, numbersOut);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionOnIteratorRemove() {

        // when
        underTest.iterator().remove();
    }

    private Integer[] createIntegers(int size) {

        final Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = Integer.valueOf(i+1);
        }
        return numbers;
    }

    private void assertIntegersMatch(Integer[] numbers, Integer[] numbersOut) {
        for (int i = 0; i < numbers.length; i++) {
            assertThat(numbersOut[i], is(numbers[i]));
        }
    }

}