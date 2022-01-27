package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0828_CountUniqueCharactersOfAllSubstringsOfAGivenString {
    //similiar: https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/C++JavaPython-One-pass-O(N)/135781
    //思路就是对每一个char出现的idx进行记录，放在record中，那么如果要保证某个char只有一个，那只能在这个char出现的idx相邻位置插括号，
    // 比如"XAXAXXAX"， A的出现位置是{1, 3, 6}, 那么A被计算unique的次数是(1-(-1))*(3-1) + (3-1)*(6-3) + (8-6}*(6-3) = 16, 对于X也是同理
    class Solution {
        public int uniqueLetterString(String S) {
            List<Integer>[] record = new List[128];
            int mod = 1000000007;
            int N = S.length();
            for (int i = 0; i < 128; i++) {
                record[i] = new ArrayList<>();
            }
            for (int i = 0; i < S.length(); i++) {
                record[S.charAt(i)].add(i);
            }
            long result = 0;
            for (int i = 0; i < 128; i++) {
                int size = record[i].size();
                for (int j = 0; j < size; j++) {
                    int index = record[i].get(j);
                    int left = j == 0 ? -1 : record[i].get(j - 1);
                    int right = j == size - 1 ? N : record[i].get(j + 1);
                    result += (index - left) * (right - index);
                    result %= mod;
                }
            }
            return (int)result;
        }
    }


    //https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/C%2B%2BJavaPython-One-pass-O(N)
    //One pass, time complexity O(N).
    //Space complexity O(1).
    public int uniqueLetterString(String S) {
        int[][] index = new int[26][2];
        for (int i = 0; i < 26; ++i) {
            Arrays.fill(index[i], -1);
        }
        int res = 0, N = S.length(), mod = (int)Math.pow(10, 9) + 7;
        for (int i = 0; i < N; ++i) {
            int c = S.charAt(i) - 'A';
            res = (res + (i - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
            index[c] = new int[] {index[c][1], i};
        }
        for (int c = 0; c < 26; ++c)
            res = (res + (N - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
        return res;
    }



}
