public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        private Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }
    /**
    * create an empty Slist
    */
    public LinkedListDeque() {
        sentinel = new Node(null,null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public int size() {
        return size;
    }
    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        Node p = new Node(item, sentinel.next, sentinel);
        sentinel.next.prev = p;
        sentinel.next = p;
        size += 1;
    }
    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        Node p = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.next = p;
        sentinel.prev = p;
        size += 1;

    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        else return false;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        Node n = sentinel.next;
        while(n != sentinel) {
            System.out.print(n.item + " ");
            n = n.next;
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T firstItem = (T) sentinel.next.item;
        Node x = sentinel.next.next;
        x.prev = sentinel;
        sentinel.next = x;
        size -= 1;
        return firstItem;
    }

    /**
     *Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T lastItem =  sentinel.prev.item;
        Node x = sentinel.prev.prev;
        x.next = sentinel;
        sentinel.prev= x;
        size -=1;
        return lastItem;
    }

    /**
     *  Gets the item at the given index,
     *  where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if(size == 0) {
            return null;
        }
        Node p = sentinel;
        T x = p.item;
        for(int i = 0; i <= index; i++) {
            x = p.next.item;
            p = p.next;
        }
        return x;
    }
    private T getHelper (int index, int n, Node p) {
       if(index == n) {
           return p.item;
       }

        return getHelper(index, n+1, p.next);

    }
    public T getRecursive(int index) {
        if(index >= size || index < 0 ) {
            return null;
        }
        int n = 0;
        Node p = sentinel.next;
        return getHelper(index, n, p);

    }


}
