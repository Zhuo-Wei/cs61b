// Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//Make sure to make this class and all of its methods public
// Make sure to make this class extend AbstractBoundedQueue<t>

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek.*/
    private int first;
    /* Index for the next enqueue */
    private int last;
    /* Array for storing the buffer data */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //  Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.fillCount = 0;
        first = 0;
        last = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    private int plus1(int x) {
        if (x == (capacity - 1)) {
            return 0;
        } else {
            return (x + 1);
        }
    }

    public void enqueue(T x) {
        // TEnqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }

        rb[last] = x;
        last = plus1(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        //  Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        first = plus1(first);
        fillCount -= 1;
        return temp;
    }

    /**
     *  Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        // Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // When you get to part 5, implement the needed code to support iteration.
    private class BufferIterator implements Iterator<T> {
        private int i;
        private int pos;
        public BufferIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < fillCount();
        }

        public T next() {
            T returnItem = rb[pos];
            pos = plus1(pos);
            i++;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }
}

