package topinterviewquestions;

public class Problem_0997_FindtheTownJudge_G {

    //方法一：计算各节点的入度和出度indegree outdegree
    //思路及解法
    //
    //题干描述了一个有向图。每个人是图的节点，\textit{trust}trust 的元素 \textit{trust}[i]trust[i] 是图的有向边，从 \textit{trust}[i][0]trust[i][0]
    // 指向 \textit{trust}[i][1]trust[i][1]。我们可以遍历 \textit{trust}trust，统计每个节点的入度和出度，存储在 \textit{inDegrees}inDegrees 和 \textit{outDegrees}outDegrees 中。
    //
    //根据题意，在法官存在的情况下，法官不相信任何人，每个人（除了法官外）都信任法官，且只有一名法官。因此法官这个节点的入度是 n-1n−1, 出度是 00。
    //
    //我们可以遍历每个节点的入度和出度，如果找到一个符合条件的节点，由于题目保证只有一个法官，我们可以直接返回结果；如果不存在符合条件的点，则返回 -1−1。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/find-the-town-judge/solution/zhao-dao-xiao-zhen-de-fa-guan-by-leetcod-0dcg/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int findJudge(int n, int[][] trust) {
            int[] inDegrees = new int[n + 1];
            int[] outDegrees = new int[n + 1];
            for (int[] edge : trust) {
                int x = edge[0], y = edge[1];
                ++inDegrees[y];
                ++outDegrees[x];
            }
            for (int i = 1; i <= n; ++i) {
                if (inDegrees[i] == n - 1 && outDegrees[i] == 0) {
                    return i;
                }
            }
            return -1;
        }
    }

}
