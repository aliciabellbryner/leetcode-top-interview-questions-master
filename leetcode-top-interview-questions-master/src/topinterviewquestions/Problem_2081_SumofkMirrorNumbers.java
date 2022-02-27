package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_2081_SumofkMirrorNumbers {
    class Solution {
        public long kMirror(int k, int n) {
            int len = 1;
            List<Long> res = new ArrayList<>();
            while(res.size() < n){
                char[] arr = new char[len];
                dfs(arr, (k+"").charAt(0), 0, res);
                len++;
            }

            while(res.size() > n) res.remove(res.size() -1);
            long sum = 0;
            for(Long s: res) {
                sum += s;
            }
            return sum;
        }


        void dfs(char[] arr, char k, int index, List<Long> res){
            if(index >= (arr.length+1)/2) {
                String s = new String(arr);
                long num10 = Long.parseLong(s, k-'0');
                String s10 = num10 +"";
                boolean valid = true;
                for(int left = 0, right = s10.length() -1; left < right; left++,right--){
                    if(s10.charAt(left) != s10.charAt(right)) {
                        valid = false;
                        break;
                    }
                }
                if(valid) res.add(num10);
                return ;
            }
            for(char i = '0'; i < k; i++){
                if(index == 0 && i == '0') continue;
                arr[index] = i;
                arr[arr.length -1 - index] = i;
                dfs(arr, k, index+1, res);
            }

        }
    }
}
