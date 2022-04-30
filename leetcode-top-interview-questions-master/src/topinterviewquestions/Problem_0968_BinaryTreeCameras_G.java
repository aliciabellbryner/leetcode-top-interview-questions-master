package topinterviewquestions;

public class Problem_0968_BinaryTreeCameras_G {
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
    //https://leetcode-cn.com/problems/binary-tree-cameras/solution/jian-kong-er-cha-shu-by-leetcode-solution/602386
/*
树形dp的通用解法

状态定义：

状态0：当前节点安装相机的时候，需要的最少相机数
状态1： 当前节点不安装相机，但是能被覆盖到的时候，需要的最少相机数
状态2：当前节点不安装相机，也不能被覆盖到的时候，需要的最少相机数
最后我们的解当然就是根节点的状态0和状态1中的最小值啦

而每一个节点的状态只和它的左孩子和右孩子有关，因此状态的转移方式如下：

安装相机，其左孩子节点和右孩子节点都可以安装或者不装，但是总相机数+1
dp[0] = Math.min(left[0], Math.min(left[1], left[2])) + Math.min(right[0], Math.min(right[1], right[2])) + 1
不安装相机，但是能被覆盖到，说明其孩子节点至少有一个安装了相机，因为自己不安装相机，如果孩子节点也不安装，那个节点只能是已被覆盖到的
 dp[1] = Math.min(left[0] + Math.min(right[0], right[1]), right[0] + Math.min(left[0], left[1]))
不安装相机，也不能被覆盖到，说明其孩子节点都没有安装相机，因为自己没有安装相机，其孩子节点也必须是已被覆盖到的
dp[2] = left[1] + right[1]
 */
    class Solution {
        public int minCameraCover(TreeNode root) {
            int[] ans = minCamera(root);
            return Math.min(ans[0], ans[1]);
        }

        public int[] minCamera(TreeNode root) {
            int[] dp = new int[3];
            if (root == null) {
                dp[0] = Integer.MAX_VALUE / 2;
                dp[2] = Integer.MAX_VALUE / 2;
                return dp;
            }
            int[] left = minCamera(root.left);
            int[] right = minCamera(root.right);
            dp[0] = Math.min(left[0], Math.min(left[1], left[2])) + Math.min(right[0], Math.min(right[1], right[2])) + 1;
            dp[1] = Math.min(left[0] + Math.min(right[0], right[1]), right[0] + Math.min(left[0], left[1]));
            dp[2] = left[1] + right[1];
            return dp;
        }
    }
}
