import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
Uncomment this class once you've created your Palindrome class. */

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("abcba"));
        assertTrue(palindrome.isPalindrome("aa"));
    }

    @Test
    public void testIsPalindrome2() {
        OffByOne offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("abbba", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("%", offByOne));
    }

    @Test
    public void testPalindromeByN() {
        OffByN offByN = new OffByN(6);
        assertTrue(palindrome.isPalindrome("gmdjgm", offByN));
        assertFalse(palindrome.isPalindrome("aabbbaa", offByN));
        assertTrue(palindrome.isPalindrome("", offByN));
        assertTrue(palindrome.isPalindrome("&", offByN));
    }

}
