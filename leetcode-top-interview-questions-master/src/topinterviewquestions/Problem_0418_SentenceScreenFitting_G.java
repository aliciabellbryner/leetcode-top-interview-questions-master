package topinterviewquestions;

public class Problem_0418_SentenceScreenFitting_G {

    //https://leetcode.com/problems/sentence-screen-fitting/discuss/90845/21ms-18-lines-Java-solution
    public int wordsTyping(String[] sentence, int rows, int cols) {
        String s = String.join(" ", sentence) + " ";
        int index = 0, N = s.length();
        for (int i = 0; i < rows; i++) {
            index += cols;
            if (s.charAt(index % N) == ' ') {
                index++;
            } else {
                while (index > 0 && s.charAt((index-1) % N) != ' ') {
                    index--;
                }
            }
        }

        return index / N;
    }

    //奇葩解法，高速但不容易想
    //https://leetcode.com/problems/sentence-screen-fitting/discuss/90845/21ms-18-lines-Java-solution
    //Optimized to 12ms. O(m + n), m: length of sentence by char, n: rows.
    public int wordsTyping2(String[] sentence, int rows, int cols) {
        String s = String.join(" ", sentence) + " ";
        int N = s.length(), res = 0;
        int[] map = new int[N];
        for (int i = 1; i < N; ++i) {
            map[i] = s.charAt(i) == ' ' ? 1 : map[i-1] - 1;
        }
        for (int i = 0; i < rows; ++i) {
            res += cols;
            res += map[res % N];
        }
        return res / N;
    }

    public static void main(String[] args) {
        String[] test = new String[]{"I", "Love", "You"};
        System.out.println(String.join(" ", test) + " ");
    }
}
