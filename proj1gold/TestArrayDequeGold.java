import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void test() {
        String msg = "\n";
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution();
        StudentArrayDeque<Integer> samp = new StudentArrayDeque();

        for (int i = 0; i < 1000; i++) {
            double test = StdRandom.uniform(4);

            if (test == 0) {
                int Int = StdRandom.uniform(100);
                ans.addFirst(Int);
                samp.addFirst(Int);
                msg = msg + "addFirst(" + Int + ")\n";

            }

            else if (test == 1) {
                int Int = StdRandom.uniform(100);
                ans.addLast(Int);
                samp.addLast(Int);
                msg = msg + "addLast(" + Int + ")\n";
            }

            else if (test == 2) {
                if (ans.size() != 0) {
                    ans.removeFirst();
                    samp.removeFirst();
                    msg = msg + "removeFirst()\n";
                    assertEquals(msg, ans.removeFirst(), samp.removeFirst());
                }
            }

            else if (ans.size() != 0) {
                ans.removeLast();
                samp.removeLast();
                msg = msg + "removeLast()\n";
                assertEquals(msg, ans.removeLast(), samp.removeLast());
            }
        }
    }
}
