public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            d.addLast(c);
        }

        return d;
    }

    public boolean isPalindrome(String word) {
        Deque a = wordToDeque(word);
        int i = word.length();
        return isPHelper(i, a);
    }

    private boolean isPHelper(int i, Deque wordDeque) {
        if (i <= 1) {
            return true;
        }

        if (wordDeque.removeFirst() != wordDeque.removeLast()) {
            return false;
        }
        return isPHelper(i-2, wordDeque);
    }

    /**
     * isPalindrome function using offbyone
     * @param word
     * @param cc
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque a = wordToDeque(word);
        int i = word.length();
        return isPHelper(i, a, cc);
    }

    private boolean isPHelper(int i, Deque wordDeque, CharacterComparator cc) {
        if (i <= 1) {
            return true;
        }

        if (!cc.equalChars((char)wordDeque.removeFirst(),(char)wordDeque.removeLast())){
            return false;
        }
        return isPHelper(i-2, wordDeque, cc);
    }
}
