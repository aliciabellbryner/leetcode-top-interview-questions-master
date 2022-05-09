package topinterviewquestions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Problem_0894_AllPossibleFullBinaryTrees_G {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //方法：递归
    //思路与算法
    //
    //令 \text{FBT}(N)FBT(N) 作为所有含 NN 个结点的可能的满二叉树的列表。
    //
    //每个满二叉树 TT 含有 3 个或更多结点，在其根结点处有 2 个子结点。这些子结点 left 和 right 本身就是满二叉树。
    //
    //因此，对于 N \geq 3N≥3，我们可以设定如下的递归策略：\text{FBT}(N) =FBT(N)= [对于所有的 xx，所有的树的左子结点来自 \text{FBT}(x)FBT(x) 而右子结点来自 \text{FBT}(N-1-x)FBT(N−1−x)]。
    //
    //此外，通过简单的计数参数，没有满二叉树具有正偶数个结点。
    //
    //最后，我们应该缓存函数 \text{FBT}FBT 之前的结果，这样我们就不必在递归中重新计算它们。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/all-possible-full-binary-trees/solution/suo-you-ke-neng-de-man-er-cha-shu-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    //时间复杂度：O(2^N)O(2
    //N
    // )，对于 NN 是奇数的情况，令 N = 2k + 1N=2k+1。然后，\Big| \text{FBT}(N) \Big| = C_k

    // ，第 kk 个卡特兰数（Catalan Number）；以及 \sum\limits_{k < \frac{N}{2}} C_k

    // （计算中间结果所涉及的复杂度） 限制在 O(2^N)O(2
    //N
    // )内。但是，证明超出了本文的范围。
    //
    //空间复杂度：O(2^N)
    class Solution {
        Map<Integer, List<TreeNode>> memo = new HashMap();

        public List<TreeNode> allPossibleFBT(int N) {
            if (!memo.containsKey(N)) {
                List<TreeNode> ans = new LinkedList();
                if (N == 1) {
                    ans.add(new TreeNode(0));
                } else if (N % 2 == 1) {
                    for (int x = 0; x < N; ++x) {
                        int y = N - 1 - x;
                        for (TreeNode left: allPossibleFBT(x))
                            for (TreeNode right: allPossibleFBT(y)) {
                                TreeNode bns = new TreeNode(0);
                                bns.left = left;
                                bns.right = right;
                                ans.add(bns);
                            }
                    }
                }
                memo.put(N, ans);
            }

            return memo.get(N);
        }
    }

}
