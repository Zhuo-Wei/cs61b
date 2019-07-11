public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;

    public class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }
    /**
    * create an empty Slist
    */
    public LinkedListDeque(){
        sentinel = new Node(null,null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * create an SSList
     */
    public LinkedListDeque(T x){
        sentinel = new Node(null,null, null);
        sentinel.next = new Node(x, null, null);
        sentinel.prev = new Node(x, null, null);
        size = 1;

    }
    public int size(){
        return size;
    }
    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item){
        Node p = sentinel;
        p.next = new Node(item, p.next, p);
        size += 1;
    }
    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item){
        Node p = sentinel;
        p.prev = new Node(item, p, p.prev);
        size += 1;

    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        else return false;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque(){
        Node n = sentinel.next;
        while(n != sentinel){
            System.out.print(n.item + " ");
            n = n.next;
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst(){
        T x = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return x;
    }

    /**
     *Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast(){

        T x = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        size -=1;
        return x;

    }

    /**
     *  Gets the item at the given index,
     *  where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index){
        if(size == 0){
            return null;
        }
        Node p = sentinel;
        T x = p.item;
        for(int i = 0; i <= index; i++){
            x = p.next.item;
            p = p.next;
        }
        return x;

    }

}
