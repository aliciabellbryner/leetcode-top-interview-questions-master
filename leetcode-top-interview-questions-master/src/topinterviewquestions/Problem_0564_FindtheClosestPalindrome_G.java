package topinterviewquestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem_0564_FindtheClosestPalindrome_G {

    //https://leetcode.com/problems/find-the-closest-palindrome/discuss/122957/Java-compare-five-candidates-get-result-easy-to-understand
    class Solution {
        public String nearestPalindromic(String n) {
            // edge cases, no

            int len = n.length();
            int i = len % 2 == 0 ? len / 2 - 1: len / 2;
            long left = Long.parseLong(n.substring(0, i+1));

            // input: n 12345
            List<Long> candidate = new ArrayList<>();
            candidate.add(getPalindrome(left, len % 2 == 0)); // 12321
            candidate.add(getPalindrome(left+1, len % 2 == 0)); // 12421
            candidate.add(getPalindrome(left-1, len % 2 == 0)); // 12221
            candidate.add((long)Math.pow(10, len-1) - 1); // 9999
            candidate.add((long)Math.pow(10, len) + 1); // 100001

            long diff = Long.MAX_VALUE, res = 0, nl = Long.parseLong(n);
            for (long cand : candidate) {
                if (cand == nl) continue;
                if (Math.abs(cand - nl) < diff) {
                    diff = Math.abs(cand - nl);
                    res = cand;
                } else if (Math.abs(cand - nl) == diff) {
                    res = Math.min(res, cand);
                }
            }

            return String.valueOf(res);
        }

        private long getPalindrome(long left, boolean even) {
            long res = left;
            if (!even) left = left / 10;
            while (left > 0) {
                res = res * 10 + left % 10;
                left /= 10;
            }
            return res;
        }
    }

    /*
    get first half, then compare 5 cases: +0(itself not palindrome), +1 / -1 / 9...9 / 10..01 (itself palindrome)
    */


    class Solution2 {
        public String nearestPalindromic(String n) {
            if(n.length()==1){
                return String.valueOf(Integer.parseInt(n)-1);
            }
            int halflen = (n.length()+1)/2;
            long half = Long.parseLong(n.substring(0,halflen));

            List<Long> candidates = new ArrayList<>();
            candidates.add(getAllNine(n.length()));
            candidates.add(getAllNine(n.length()-1));
            candidates.add(getOneZero(n.length()));
            candidates.add(getOneZero(n.length()+1));

            getcandidates(candidates,half,n.length());
            long dif = Long.MAX_VALUE;
            long r = Long.parseLong(n);
            String res = "";
            Collections.sort(candidates);
            for(Long ele:candidates){
                if(ele == r){
                    continue;
                }
                if(dif>Math.abs(ele-r)){
                    dif = Math.abs(ele-r);
                    res = String.valueOf(ele);
                }
            }
            return res;
        }

        public void getcandidates(List<Long> ans,long m,int len){
            List<Long> reg = new ArrayList<>();
            reg.add(m);
            reg.add(m-1);
            reg.add(m+1);
            for(Long ele : reg){
                if(len%2==0){
                    String str = String.valueOf(ele);
                    str+=new StringBuilder(str).reverse().toString();
                    ans.add(Long.parseLong(str));
                }else{
                    String str = String.valueOf(ele);
                    StringBuilder sb = new StringBuilder(str.substring(0,str.length()-1));
                    str = str + sb.reverse().toString();
                    ans.add(Long.parseLong(str));
                }
            }
        }

        public Long getAllNine(int n){
            String str="";
            for(int i=0;i<n;i++){
                str+='9';
            }
            return Long.parseLong(str);
        }

        public Long getOneZero(int n){
            return (long)Math.pow(10,n-1)+1;
        }
    }
}
