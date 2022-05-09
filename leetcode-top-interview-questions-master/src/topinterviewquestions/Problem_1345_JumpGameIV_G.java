package topinterviewquestions;

import java.util.*;

public class Problem_1345_JumpGameIV_G {
    //https://leetcode-cn.com/problems/jump-game-iv/solution/tiao-yue-you-xi-iv-by-leetcode-solution-zsix/
    //方法一：广度优先搜索
    //思路
    //
    //记数组 \textit{arr}arr 的长度为 nn。题目描述的数组可以抽象为一个无向图，数组元素为图的顶点，相邻下标的元素之间有一条无向边相连，所有值相同元素之间也有无向边相连。每条边的权重都为 11，即此图为无权图。求从第一个元素到最后一个元素的最少操作数，即求从第一个元素到最后一个元素的最短路径长度。求无权图两点间的最短路可以用广度优先搜索来解，时间复杂度为 O(V+E)O(V+E)，其中 VV 为图的顶点数，EE 为图的边数。
    //
    //在此题中，V = nV=n，而 EE 可达 O(n^2)
    // ) 数量级，按照常规方法使用广度优先搜索会超时。造成超时的主要原因是所有值相同的元素构成了一个稠密子图，普通的广度优先搜索方法会对这个稠密子图中的所有边都访问一次。但对于无权图的最短路问题，这样的访问是不必要的。在第一次访问到这个子图中的某个节点时，即会将这个子图的所有其他未在队列中的节点都放入队列。在第二次访问到这个子图中的节点时，就不需要去考虑这个子图中的其他节点了，因为所有其他节点都已经在队列中或者已经被访问过了。因此，在用广度优先搜索解决此题时，先需要找出所有的值相同的子图，用一个哈希表 \textit{idxSameValue}idxSameValue 保存。在第一次把这个子图的所有节点放入队列后，把该子图清空，就不会重复访问该子图的其他边了。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/jump-game-iv/solution/tiao-yue-you-xi-iv-by-leetcode-solution-zsix/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int minJumps(int[] arr) {
            Map<Integer, List<Integer>> idxSameValue = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < arr.length; i++) {
                idxSameValue.putIfAbsent(arr[i], new ArrayList<Integer>());
                idxSameValue.get(arr[i]).add(i);
            }
            Set<Integer> visitedIndex = new HashSet<Integer>();
            Queue<int[]> queue = new ArrayDeque<int[]>();
            queue.offer(new int[]{0, 0});
            visitedIndex.add(0);
            while (!queue.isEmpty()) {
                int[] idxStep = queue.poll();
                int idx = idxStep[0], step = idxStep[1];
                if (idx == arr.length - 1) {
                    return step;
                }
                int v = arr[idx];
                step++;
                if (idxSameValue.containsKey(v)) {
                    for (int i : idxSameValue.get(v)) {
                        if (visitedIndex.add(i)) {
                            queue.offer(new int[]{i, step});
                        }
                    }
                    idxSameValue.remove(v);
                }
                if (idx + 1 < arr.length && visitedIndex.add(idx + 1)) {
                    queue.offer(new int[]{idx + 1, step});
                }
                if (idx - 1 >= 0 && visitedIndex.add(idx - 1)) {
                    queue.offer(new int[]{idx - 1, step});
                }
            }
            return -1;
        }
    }

}
