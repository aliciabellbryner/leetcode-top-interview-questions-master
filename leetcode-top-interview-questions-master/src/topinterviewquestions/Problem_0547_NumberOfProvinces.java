package topinterviewquestions;

public class Problem_0547_NumberOfProvinces {

    //DFS https://leetcode.com/problems/number-of-provinces/solution/
    //Time complexity : O(n^2) The complete matrix of size n^2 is traversed.
    //Space complexity : O(n). visited array of size n is used.
    public class Solution {
        public void dfs(int[][] M, int[] visited, int i) {
            for (int j = 0; j < M.length; j++) {
                if (M[i][j] == 1 && visited[j] == 0) {
                    visited[j] = 1;
                    dfs(M, visited, j);
                }
            }
        }
        public int findCircleNum(int[][] isConnected) {
            int[] visited = new int[isConnected.length];//visited[i]代表i号有没有visited过
            int res = 0;
            for (int i = 0; i < isConnected.length; i++) {
                if (visited[i] == 0) {
                    dfs(isConnected, visited, i);
                    res++;
                }
            }
            return res;
        }
    }

    //Union find
    ////time O(N^3): two for loop O(n^2), union -> find() : O(N)
    ////space O(N) for the int[] parents; int[] size;
    class Solution1 {
        public int findCircleNum(int[][] isConnected) {
            int N = isConnected.length;
            UnionFind uf = new UnionFind(N);
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (isConnected[i][j] == 1) {
                        uf.union(i, j);
                    }
                }
            }
            return uf.sets();
        }
        public class UnionFind {
            int[] parents;
            int[] size;
            int sets;
            public UnionFind(int n) {
                parents = new int[n];
                size = new int[n];
                sets = n;
                for (int i = 0; i < n; i++) {
                    parents[i] = i;
                    size[i] = 1;
                }
            }
            public int find(int num) {
                while (parents[num] != num) {
                    num = parents[num];
                }
                return num;
            }
            public void union(int i, int j) {
                int x = find(i);
                int y = find(j);
                if (x != y) {
                    if (size[x] > size[y]) {
                        size[x] += size[y];
                        parents[y] = x;
                    } else {
                        size[y] += size[x];
                        parents[x] = y;
                    }
                    sets--;
                }
            }
            public int sets(){
                return sets;
            }
        }
    }
}
