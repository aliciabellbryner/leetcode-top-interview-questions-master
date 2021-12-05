package NewsBreak;

import java.util.Arrays;

public class CountSubstrings {


    static int count_substrings(String s, int k)
    {
        int res = 0;
        int distinct = 0;//to store how many distinct char in s
        boolean[] have = new boolean[26];//to store all the char in s
        for (int i = 0; i < s.length(); i++) {
            have[((int)(s.charAt(i) - 'a'))] = true;
        }
        for (int i = 0; i < 26; i++) {
            if (have[i]) {
                distinct++;
            }
        }
        for (int length = 1; length <= distinct; length++) {//length mean how many distinct elements are there in your substring to try
            int window_length = length * k;
            int[] freq = new int[26];
            Arrays.fill(freq, 0);
            int window_start = 0;
            int window_end = window_start + window_length - 1;
            for (int i = window_start; i <= Math.min(window_end, s.length() - 1); i++) {
                freq[((int)(s.charAt(i) - 'a'))]++;
            }
            while (window_end < s.length()) {
                if (have_same_frequency(freq, k)) {
                    res++;
                }
                freq[((int)(s.charAt(window_start) - 'a'))]--;
                window_start++;
                window_end++;
                if (window_end < s.length()) {
                    freq[((int)(s.charAt(window_end) - 'a'))]++;
                }
            }
        }
        return res;
    }

    private static boolean have_same_frequency(int[] freq, int k)
    {
        for (int i = 0; i < 26; i++) {
            if (freq[i] != 0 && freq[i] != k) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args)
    {
        String s = "aabbcc";
        int k = 2;
        System.out.println(count_substrings(s, k));
        s = "aabbc";
        k = 2;
        System.out.println(count_substrings(s, k));
    }
}
