package topinterviewquestions;

import java.util.*;

public class Problem_2092_FindAllPeopleWithSecret_G {
    //https://leetcode.com/problems/find-all-people-with-secret/discuss/1599815/C++-Union-Find/1163970
    class Union {
        int[] parents;
        Union(int n){
            parents = new int[n];
            for(int i = 0; i < n; i++){
                parents[i] = i;
            }
        }
        int find(int i){
            if(parents[i] == i) {
                return i;
            }
            parents[i] = find(parents[i]);
            return parents[i];
        }
        void union(int x, int y) {
            int px = find(x), py = find(y);
            if(px == py) {
                return;
            }
            if(px < py) {
                parents[py] = px;
            } else {
                parents[px] = py;
            }
        }
        void reset(int x) {
            parents[x] = x;
        }
    }
    class Solution {
        public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
            Arrays.sort(meetings, (a, b) -> a[2] - b[2]);//先按照实际排序，升序
            List<Integer> res = new ArrayList();
            Union union = new Union(n);
            union.union(0, firstPerson);
            int time = meetings[0][2];
            Set<Integer> visited = new HashSet();
            for(int[] meet : meetings){
                if(meet[2] == time){
                    union.union(meet[0], meet[1]);
                } else {
                    for(int i : visited) {
                        if(union.find(i) != 0) {
                            union.reset(i);
                        }
                    }
                    visited.clear();
                    time = meet[2];
                    union.union(meet[0], meet[1]);
                }
                visited.add(meet[0]);
                visited.add(meet[1]);
            }
            for(int i = 0; i < n; i++) {
                if(union.find(i) == 0) {
                    res.add(i);
                }
            }
            return res;
        }
    }
}
