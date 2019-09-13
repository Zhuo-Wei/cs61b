package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 1);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertTrue(minHeap.size() == 6);
        minHeap.removeSmallest();
        minHeap.removeSmallest();
        assertTrue(minHeap.size() == 4);
        //assertEquals(minHeap.contains(6), true);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 1);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertEquals(minHeap.contains(1), true);
        assertEquals(minHeap.contains(8), false);
        assertEquals(minHeap.contains(2), true);
        assertEquals(minHeap.contains(3), true);
        assertEquals(minHeap.contains(4), true);
        assertEquals(minHeap.contains(5), true);
        assertEquals(minHeap.contains(6), true);
    }
    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 5);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        assertTrue(minHeap.getSmallest() == 5);
        minHeap.removeSmallest();
        assertTrue(minHeap.getSmallest() == 1);

    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 0);
        minHeap.add(5, 5);
        minHeap.add(6, 6);
        assertTrue(minHeap.removeSmallest() == 4);
        assertTrue(minHeap.getSmallest() == 1);

    }
    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        minHeap.changePriority(5, 5);
        minHeap.changePriority(6, 0);
        assertTrue(minHeap.getSmallest() == 6);
    }


}
