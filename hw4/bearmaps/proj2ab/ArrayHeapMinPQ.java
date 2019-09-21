package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    private ArrayList<Node> itemPQ;
    private HashMap<T, Integer> itemIndexMap;

    public ArrayHeapMinPQ() {
        itemPQ = new ArrayList<>();
        itemIndexMap = new HashMap<>();
    }

    private class Node<T>{
        public T item;
        public double priority;
        public Node(T i, double p) {
            this.item = i;
            this.priority= p;
        }
        public double getPriority() {
            return this.priority;
        }

        public T getItem() {
            return this.item;
        }

        public void setPriority(double newP) {
             this.priority = newP;
        }
    }
    private int parent(int k) {
        if (k == 0) {
            return 0;
        }
        return (k - 1) / 2;
    }

    private int leftChild(int k) {
        return 2 * k + 1;
    }

    private int rightChild(int k) {
        return 2 * (k + 1);
    }

    private boolean isEmpty() {
        if (size() <= 0) {
            return true;
        }
        else return false;
    }
    //from bottom to top
    private void swim(int k) {
        if(greater(parent(k),k) && k > 0) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    //from top to bottom
    private void sink(int k) {
        int parent = k;
        if (leftChild(parent) < size() && greater(parent, leftChild(parent))) {
            parent = leftChild(parent);
        }
        if (rightChild(k) < size() && greater(parent, rightChild(k))) {
            parent = rightChild(k);
        }
        if (parent != k){
            swap(parent, k);
            sink(parent);
        }

    }

    // Return true if ith node has LARGER priority than jth node.
    private boolean greater(int i, int j) {
        return itemPQ.get(i).getPriority() > itemPQ.get(j).getPriority();
    }

    private void swap(int i, int j) {
        Node swap = itemPQ.get(i);
        itemPQ.set(i, itemPQ.get(j));
        itemPQ.set(j, swap);
        itemIndexMap.put((T) itemPQ.get(i).getItem(), i);
        itemIndexMap.put((T)itemPQ.get(j).getItem(), j);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        itemPQ.add(new Node(item, priority));
        itemIndexMap.put(item, size() - 1);
        swim(size() - 1);

    }

    @Override
    public boolean contains(T item) {
        return itemIndexMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (T)itemPQ.get(0).getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T smallest = (T)itemPQ.get(0).getItem();
        swap(0, size() - 1);
        itemPQ.remove(size() - 1);
        itemIndexMap.remove(smallest);
        sink(0);
        return smallest;
    }

    @Override
    public int size() {
        return itemPQ.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if(!contains(item)|| isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = itemIndexMap.get(item);
        double oldPriority = itemPQ.get(index).getPriority();
        itemPQ.get(index).setPriority(priority);
        if (oldPriority < priority) {
            sink(index);
        } else {
            swim(index);
        }
    }
//    public static void main(String[] args) {
//        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
//        minHeap.add(1, 1);
//        minHeap.add(2, 0);
//
//        System.out.println(minHeap.getSmallest());
//        minHeap.changePriority(2, 2);
//        //minHeap.changePriority(6, 0);....
//        System.out.println(minHeap.getSmallest());
//..
//    }
}
