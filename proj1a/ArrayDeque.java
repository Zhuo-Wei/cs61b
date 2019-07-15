public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * create an empty Alist
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public int size() {
        return size;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }

        return false;
    }

    private int minus1(int index) {
        index -= 1;
        if (index < 0) {
            return items.length - 1;
            // index = items.length + index;
        }
        return index;
    }

    private int plus1(int index) {
        index += 1;
        if (index >= items.length) {
           // index = index - items.length;
            return 0;

        }
        return index;
    }

    private void resize(int newS) {
        T[] a = (T[]) new Object[newS];
        int head = plus1(nextFirst);
        //int tale = minus1(nextLast);
        for(int i = 0; i < size; i++){
            a[i] = items[head];
            head = plus1(head);
        }
        items = a ;
        nextLast = size;
        nextFirst = items.length -1;

    }

    /**
     * Adds an items of type T to the front of the deque.
     */
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        nextFirst = minus1(nextFirst);
        size += 1;
    }

    /**
     * Adds an items of type T to the back of the deque.
     */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = x;
        nextLast = plus1(nextLast);
        size += 1;
    }

    /**
     * Prints the itemss in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        // resize(size);
        int x = plus1(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[x] + " ");
            x = plus1(x);
        }
    }

    /**
     * Removes and returns the items at the front of the deque.
     * If no such items exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = items[plus1(nextFirst)];
        items[plus1(nextFirst)] = null;
        nextFirst = plus1(nextFirst);
        size -= 1;
        if (size / items.length < 0.25 && items.length >= 8) {
            resize(items.length / 2);
        }
        return x;
    }

    /**
     * Removes and returns the items at the back of the deque.
     * If no such items exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if ((float) size / items.length < 0.25 && items.length >= 8) {
            resize(items.length / 2);
        }
        T x = items[minus1(nextLast)];
        items[minus1(nextLast)] = null;
        nextLast = minus1(nextLast);
        size -= 1;

        return x;

    }

    /**
     * Gets the items at the given index
     * where 0 is the front, 1 is the next items, and so forth.
     * If no such items exists, returns null. Must not alter the deque!
     */
    public T get(int index) {

        if (index < 0 || index >= size) {
            return null;
        }

        int n = plus1(nextFirst);
        for (int i = 0; i < index; i++) {
            n = plus1(n);
        }

        return items[n];
    }
}

