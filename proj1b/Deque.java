public interface Deque<T> {
    /**
     * Adds an item of type T to the front of the deque.
     * @param item
     */
    public void addFirst(T item);

    /**
     * Adds an item of type T to the front of the deque.
     * @param item
     */
    public void addLast(T item);

    /**
     * Returns true if deque is empty, false otherwise.
     * @return
     */

    public default boolean isEmpty(){
        if (size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * public void printDeque():
     * @return
     */
    public int size();

    /**Prints the items in the deque
    from first to last, separated by a space.
     */
    public void printDeque();

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return
     */
    public T removeFirst();


    public T removeLast();
}
