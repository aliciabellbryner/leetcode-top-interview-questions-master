import java.util.*;

//https://leetcode.com/problems/painting-a-grid-with-three-different-colors/discuss/1330185/C++Python-DP-and-DFS-and-Bitmask-Picture-explain-Clean-and-Concise/1006918
//Share my Java solution with the concept of statemachine. I use ruleMap to record {currStates:{nextValidState1,nextValidState2,nextValidState3,...}} E.g. m = 1, {1:{2,3},2:{1,3},3:{1,2}};
//another countMap to record {currStatus,count of currStatus}} pair E.g. m = 1, {1:1,2:1,3:1}}. Then create a tempCountMap and loop n-1 steps to update the countMap using the ruleMap.
//E.g m = 1, countMap {1:1,2:1,3:1}}, I loop through this countMap
//{1:1} pair in countMap, rulemap shows status 1:{2,3}, so tempCountMap {2:+1,3:+1}
//{2:1} pair in countMap, rulemap shows status 2:{1,3}, so tempCountMap {1:+1,2:1,3:1+1}
//{3:1} pair in countMap, ruleMap shows status 3:{1,2}, so tempCountMap {1:1+1,2:1+1,3:2},
public class Problem_1931_PaintingAGridWithThreeDifferentColors {
    public int colorTheGrid(int m, int n) {
        final int M = 1000000007;
        HashMap<String, List<String>> ruleMap = new HashMap<>();
        HashMap<String,Integer> countMap = new HashMap<>();
        //Generate valid string len = m when n = 1;
        List<String> list = new LinkedList<>();
        list.add("");
        for(int i = 0;i<m;i++){
            List<String> next = new LinkedList<>();
            for(int j = 1;j<=3;j++){
                for(String s: list){
                    if(s.length() == 0 || s.charAt(s.length() - 1) - '0' != j){
                        next.add(s+j);
                    }
                }
            }
            list = next;
        }
        //generate rules to match previous state and next state
        //E.g. m=1 {1:{2,3},2:{1,3},3:{1,2}}
        //E.g  m=2 {12:{21,31,21,23,31},...,}
        for(String s1: list){
            loop:
            for(String s2: list){
                for(int i = 0;i<s1.length();i++){
                    if(s1.charAt(i) == s2.charAt(i)) continue loop;
                }
                List<String> tempList = ruleMap.getOrDefault(s1,new LinkedList<>());
                tempList.add(s2);
                ruleMap.put(s1,tempList);
            }
        }
        //initialize countMap which record {state:count of apperance of that state}

        for(String s: list){
            countMap.put(s,1);
        }
        //loop for n-1 times to update the countMap
        //to reduce space complexity, I create a temp hashmap called nextCountMap
        for(int i = 1;i<n;i++){
            HashMap<String,Integer> nextCountMap = new HashMap<>();//dp  let val add to next valid state
            for(Map.Entry<String,Integer> entry: countMap.entrySet()){
                //{state:count of this state} pair in countMap
                //E.g. m=1 {1:1,2:1,3:1}
                for(String s: ruleMap.get(entry.getKey())){
                    //state:{nextValidState1,nextValidState2,...} in ruleMap
                    //entry will be E.g.{1:{2,3},2:{1,3},{3:{1,2}}  if m = 1
                    //entry will be E.g.{12:{21,23,31},{13:{21,31,32},...} if m = 2
                    int count = nextCountMap.getOrDefault(s,0);
                    nextCountMap.put(s,(count+entry.getValue()) % M);
                    //E.g. m = 1, n = 2
                    //nextCountMap will be updated as follows:
                    //for {1:1} pair in countMap, rulemap shows status 1:{2,3}, so {2:+1,3:+1}
                    //for {2:1} pair in countMap, rulemap shows status 2:{1,3}, so {1:+1,2:1,3:1+1}
                    //for {3:1} pair in countMap, ruleMap shows status 3:{1,2}, so {1:1+1,2:1+1,3:2}
                }
            }
            countMap = nextCountMap;//assign to countMap to reduce space complexity
        }
        int ans = 0;
        //add value state map/ count Map
        for(Map.Entry<String,Integer> entry: countMap.entrySet()){
            ans = (ans + entry.getValue()) % M;
        }
        return ans;
    }


    //https://leetcode.com/problems/painting-a-grid-with-three-different-colors/discuss/1330205/Java-Simple-Column-by-Column-DP-Solution-O(482*N)
    //For m = 5, there are at most 48 valid states for a single column so we can handle it column by column.
    //We encode the color arrangement by bit mask (3 bit for a position) and use dfs to generate the all valid states.
    //Then for each column, we iterator all the states and check if it's still valid with the previous column.
    class Solution {
        static int mod = (int) 1e9 + 7;
        public int colorTheGrid(int m, int n) {
            Map<Integer, Long> map = new HashMap();
            dfs(map, 0, m, -1, 0);
            Set<Integer> set = new HashSet(map.keySet());
            for (int i = 1; i < n; ++i) {
                Map<Integer, Long> dp = new HashMap();
                for (int a : set) {
                    for (int b : set) {
                        if (0 == (a & b)) {
                            dp.put(a, (dp.getOrDefault(a, 0L) + map.get(b)) % mod);
                        }
                    }
                }
                map = dp;
            }
            long res = 0L;
            for (long val : map.values()) res = (res + val) % mod;
            return (int) res;
        }
        private void dfs(Map<Integer, Long> state, int j, int m, int prev, int cur) {
            if (j == m) {
                state.put(cur, state.getOrDefault(cur, 0L) + 1);
                return;
            }
            for (int i = 0; i < 3; ++i) {
                if (i == prev) continue;
                dfs(state, j + 1, m, i, (cur << 3) | (1 << i));
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
