package topinterviewquestions;

//https://leetcode.com/problems/string-compression/
public class Problem_0443_StringCompression {
    public static int compress(char[] chars) {
        int len = 0; // also a pointer to modify array in-place
        for (int i = 0; i < chars.length; ) {
            chars[len] = chars[i];
            int j = i + 1;

            while (j < chars.length && chars[j] == chars[i])
                j++;

            if (j - i > 1) { // need compression
                String freq = j - i + "";
                for (char c : freq.toCharArray())
                    chars[++len] = c;
            }
            len++;
            i = j;
        }
        return len;
    }

    public static void main(String[] args) {
        char[] test = new char[]{'a','a','b','b','c','c','c'};
        char[] test2 = new char[]{'a','a','a','b','b','a','a'};
        System.out.println(compress(test2));

    }
}
