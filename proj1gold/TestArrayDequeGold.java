import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testAddFirst() {
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution();
        StudentArrayDeque<Integer> samp = new StudentArrayDeque();
        for (int i = 0; i < 30; i += 1) {
            int Int = StdRandom.uniform(100);
            ans.addFirst(Int);
            samp.addFirst(Int);
        }

        for (int i=0; i<30; i++) {
            int actual = ans.get(i);
            int expected = samp.get(i);
            assertEquals("Oh noooo!\nThis is bad in addFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testAddLast() {
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution();
        StudentArrayDeque<Integer> samp = new StudentArrayDeque();
        for (int i = 0; i < 30; i += 1) {
            int Int = StdRandom.uniform(100);
            ans.addLast(Int);
            samp.addLast(Int);
        }

        for (int i = 0; i < 30; i++) {
            int actual = ans.get(i);
            int expected = samp.get(i);
            assertEquals("Oh noooo!\nThis is bad in addLast():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testRemoveFirst() {
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution();
        StudentArrayDeque<Integer> samp = new StudentArrayDeque();
        for (int i = 0; i < 30; i += 1) {
            int Int = StdRandom.uniform(100);
            ans.addFirst(Int);
            samp.addFirst(Int);
        }
         ans.removeFirst();
        samp.removeFirst();
       // assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Removed item " + act
                       // + " not equal to " + exp + "!", exp, act);
        if (ans.size() != samp.size()) {
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Size " + samp.size()
                    + " not equal to " + ans.size() + "!", samp.size(), ans.size());
        }
        else for (int i = 0; i < 29; i++) {
            int actual = ans.get(i);
            int expected = samp.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }
    @Test
    public void testRemoveLast() {
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution();
        StudentArrayDeque<Integer> samp = new StudentArrayDeque();
        for (int i = 0; i < 30; i += 1) {
            int Int = StdRandom.uniform(100);
            ans.addLast(Int);
            samp.addLast(Int);
        }
        Integer exp = ans.removeLast();
        Integer act = samp.removeLast();
        //assertEquals("Oh noooo!\nThis is bad in removeLirst():\n   Removed item " + act
                //+ " not equal to " + exp + "!", exp, act);
        if (ans.size() != samp.size()) {
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Size " + samp.size()
                    + " not equal to " + ans.size() + "!", samp.size(), ans.size());
        }

        else for (int i = 0; i < 29; i++) {
            int actual = ans.get(i);
            int expected = samp.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeLast():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

    }

}
