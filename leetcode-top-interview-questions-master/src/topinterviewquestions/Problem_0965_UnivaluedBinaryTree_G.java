package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0965_UnivaluedBinaryTree_G {
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
    //思路与算法
    //DFS
    //我们先进行一次深度优先搜索，获取这颗树中的所有节点的值。然后，就可以判断所有节点的值是不是都相等了。
    //时间复杂度：O(N)O(N)，其中 NN 是给定树中节点的数量。
    //
    //空间复杂度：O(N)O(N)。
    class Solution {
        List<Integer> vals;
        public boolean isUnivalTree(TreeNode root) {
            vals = new ArrayList();
            dfs(root);
            for (int v: vals)
                if (v != vals.get(0))
                    return false;
            return true;
        }

        public void dfs(TreeNode node) {
            if (node != null) {
                vals.add(node.val);
                dfs(node.left);
                dfs(node.right);
            }
        }
    }

//方法二：递归
//思路与算法
//
//一颗树是单值的，当且仅当根节点的子节点所在的子树也是单值的，同时根节点的值与子节点的值相同。
//
//我们可以使用递归实现这个判断的过程。left_correct 表示当前节点的左孩子是正确的，也就是说：左孩子所在的子树是单值的，并且当前节点的值等于左孩子的值。right_correct 对当前节点的右孩子表示同样的事情。递归处理之后，当根节点的这两种属性都为真的时候，我们就可以判定这颗二叉树是单值的。
//
//作者：LeetCode
//链接：https://leetcode-cn.com/problems/univalued-binary-tree/solution/dan-zhi-er-cha-shu-by-leetcode/
//来源：力扣（LeetCode）
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //时间复杂度：O(N)O(N)，其中 NN 是给定树中节点的数量。
//
//空间复杂度：O(H)O(H)，其中 HH 是给定树的高度。
class Solution2 {
    public boolean isUnivalTree(TreeNode root) {
        boolean left_correct = (root.left == null ||
                (root.val == root.left.val && isUnivalTree(root.left)));
        boolean right_correct = (root.right == null ||
                (root.val == root.right.val && isUnivalTree(root.right)));
        return left_correct && right_correct;
    }
}



}
