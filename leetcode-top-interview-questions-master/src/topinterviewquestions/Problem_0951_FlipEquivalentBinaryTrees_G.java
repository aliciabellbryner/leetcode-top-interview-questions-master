package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Problem_0951_FlipEquivalentBinaryTrees_G {
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
    //方法一： 递归
    //思路
    //
    //如果二叉树 root1，root2 根节点值相等，那么只需要检查他们的孩子是不是相等就可以了。
    //
    //算法
    //
    //存在三种情况：
    //
    //如果 root1 或者 root2 是 null，那么只有在他们都为 null 的情况下这两个二叉树才等价。
    //如果 root1，root2 的值不相等，那这两个二叉树的一定不等价。
    //如果以上条件都不满足，也就是当 root1 和 root2 的值相等的情况下，需要继续判断 root1 的孩子节点是不是跟 root2 的孩子节点相当。因为可以做翻转操作，所以这里有两种情况需要去判断。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/flip-equivalent-binary-trees/solution/fan-zhuan-deng-jie-er-cha-shu-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //
    class Solution {
        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            if (root1 == root2)
                return true;
            if (root1 == null || root2 == null || root1.val != root2.val)
                return false;

            return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) ||
                    flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
        }
    }

    //方法二： 标准态遍历
    //思路
    //
    //让树中所有节点的左孩子都小于右孩子，如果当前不满足就翻转。我们把这种状态的二叉树称为 标准态。所有等价二叉树在转换成标准态后都是完全一样的。
    //
    //算法
    //
    //用深度优先遍历来对比这两棵树在标准态下是否完全一致。对于两颗等价树，在标准态下遍历的结果一定是一样的。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/flip-equivalent-binary-trees/solution/fan-zhuan-deng-jie-er-cha-shu-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            List<Integer> vals1 = new ArrayList();
            List<Integer> vals2 = new ArrayList();
            dfs(root1, vals1);
            dfs(root2, vals2);
            return vals1.equals(vals2);
        }

        public void dfs(TreeNode node, List<Integer> vals) {
            if (node != null) {
                vals.add(node.val);
                int L = node.left != null ? node.left.val : -1;
                int R = node.right != null ? node.right.val : -1;

                if (L < R) {
                    dfs(node.left, vals);
                    dfs(node.right, vals);
                } else {
                    dfs(node.right, vals);
                    dfs(node.left, vals);
                }

                vals.add(null);
            }
        }
    }


    //Iterative version: DFS
    //https://leetcode.com/problems/flip-equivalent-binary-trees/discuss/200514/JavaPython-3-DFS-3-liners-and-BFS-with-explanation-time-and-space%3A-O(n).
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        Stack<TreeNode> stk1 = new Stack<>(), stk2 = new Stack<>();
        stk1.push(root1);
        stk2.push(root2);
        while (!stk1.isEmpty() && !stk2.isEmpty()) {
            TreeNode n1 = stk1.pop(), n2 = stk2.pop();
            if (n1 == null && n2 == null) {
                continue;
            }else if (n1 == null || n2 == null || n1.val != n2.val) {
                return false;
            }

            if (n1.left == n2.left || n1.left != null && n2.left != null && n1.left.val == n2.left.val) {
                stk1.addAll(Arrays.asList(n1.left, n1.right));
            }else {
                stk1.addAll(Arrays.asList(n1.right, n1.left));
            }
            stk2.addAll(Arrays.asList(n2.left, n2.right));
        }
        return stk1.isEmpty() && stk2.isEmpty();
    }


}
