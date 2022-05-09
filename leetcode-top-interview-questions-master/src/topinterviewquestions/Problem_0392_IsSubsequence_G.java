package topinterviewquestions;

import java.util.*;

public class Problem_0392_IsSubsequence_G {
    //https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments
    //    // Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
    //    // Eg-1. s="abc", t="bahbgdca"
    //    // idx=[a={1,7}, b={0,3}, c={6}]
    //    //  i=0 ('a'): prev=1
    //    //  i=1 ('b'): prev=3
    //    //  i=2 ('c'): prev=6 (return true)
    //    // Eg-2. s="abc", t="bahgdcb"
    //    // idx=[a={1}, b={0,6}, c={5}]
    //    //  i=0 ('a'): prev=1
    //    //  i=1 ('b'): prev=6
    //    //  i=2 ('c'): prev=? (return false)
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }

        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1;
            if (j == idx[s.charAt(i)].size()) return false;//说明最后一个了
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }


    //https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments/307593
    public boolean isSubsequence2(String s, String t) {
        if(s==null || t==null){
            return false;
        }
        Map<Character, List<Integer>> map = new HashMap();

        for(int i=0; i<t.length();i++){
            List<Integer> idx = map.getOrDefault(t.charAt(i),new ArrayList());
            idx.add(i);
            map.put(t.charAt(i), idx);
        }

        int lastFound = -1;
        for(int i=0; i<s.length(); i++) {
            if(!map.containsKey(s.charAt(i))){
                return false;
            }

            List<Integer> idxList = map.get(s.charAt(i));
            int idx = binSearchHelper(idxList, lastFound);
            if(idx ==-1){
                return false;
            }
            lastFound = idx;
        }

        return true;
    }
    private int binSearchHelper(List<Integer> idxList, int lastFound){
        int start=0;
        int end = idxList.size()-1;
        int result = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(idxList.get(mid)>lastFound){
                result = idxList.get(mid);
                end = mid-1;
            } else {
                start = mid+1;
            }

        }
        return result;
    }





    //time O(n)
    //https://leetcode.com/problems/is-subsequence/discuss/87254/Straight-forward-Java-simple-solution
    public class Solution {
        public boolean isSubsequence(String s, String t) {
            if (s.length() == 0) return true;
            int indexS = 0, indexT = 0;
            while (indexT < t.length()) {
                if (t.charAt(indexT) == s.charAt(indexS)) {
                    indexS++;
                    if (indexS == s.length()) return true;
                }
                indexT++;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        List<Integer> test = new LinkedList<>();
        test.add(1);
        test.add(3);
        test.add(4);
        test.add(6);
        test.add(9);

        System.out.println(Collections.binarySearch(test, 1));
    }
}
