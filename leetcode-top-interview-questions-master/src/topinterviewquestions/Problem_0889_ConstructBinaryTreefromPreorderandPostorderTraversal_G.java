package topinterviewquestions;

import java.util.Arrays;

public class Problem_0889_ConstructBinaryTreefromPreorderandPostorderTraversal_G {
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

    //https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/solution/gen-ju-qian-xu-he-hou-xu-bian-li-gou-zao-er-cha-sh/
    //方法一：递归
    //思路
    //
    //前序遍历为：
    //
    //(根结点) (前序遍历左分支) (前序遍历右分支)
    //而后序遍历为：
    //
    //(后序遍历左分支) (后序遍历右分支) (根结点)
    //例如，如果最终的二叉树可以被序列化的表述为 [1, 2, 3, 4, 5, 6, 7]，那么其前序遍历为 [1] + [2, 4, 5] + [3, 6, 7]，而后序遍历为 [4, 5, 2] + [6, 7, 3] + [1].
    //
    //如果我们知道左分支有多少个结点，我们就可以对这些数组进行分组，并用递归生成树的每个分支。
    //
    //算法
    //
    //我们令左分支有 LL 个节点。我们知道左分支的头节点为 pre[1]，但它也出现在左分支的后序表示的最后。所以 pre[1] = post[L-1]（因为结点的值具有唯一性），因此 L = post.indexOf(pre[1]) + 1。
    //
    //现在在我们的递归步骤中，左分支由 pre[1 : L+1] 和 post[0 : L] 重新分支，而右分支将由 pre[L+1 : N] 和 post[L : N-1] 重新分支。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/solution/gen-ju-qian-xu-he-hou-xu-bian-li-gou-zao-er-cha-sh/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //时间复杂度：O(N^2)O(N
    //2
    // )，其中 NN 是结点的数量。
    //空间复杂度：O(N^2)O(N
    //2
    // )。
    class Solution {
        public TreeNode constructFromPrePost(int[] pre, int[] post) {
            int N = pre.length;
            if (N == 0) return null;
            TreeNode root = new TreeNode(pre[0]);
            if (N == 1) return root;

            int L = 0;
            for (int i = 0; i < N; ++i)
                if (post[i] == pre[1])
                    L = i+1;

            root.left = constructFromPrePost(Arrays.copyOfRange(pre, 1, L+1),
                    Arrays.copyOfRange(post, 0, L));
            root.right = constructFromPrePost(Arrays.copyOfRange(pre, L+1, N),
                    Arrays.copyOfRange(post, L, N-1));
            return root;
        }
    }


    //方法二：递归（节约空间的变体）
    //说明
    //
    //我们这里提出一个方法一的变体，使用索引指代子数组 pre 和 post，而不是通过那些子数组的副本。这里，(i0, i1, N) 指的是 pre[i0:i0+N], post[i1:i1+N].
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/solution/gen-ju-qian-xu-he-hou-xu-bian-li-gou-zao-er-cha-sh/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //时间复杂度：O(N^2)O(N
    //2
    // )，其中 NN 是结点的数目。
    //
    //空间复杂度：O(N)O(N)，答案所用去的空间。
    class Solution2 {
        int[] pre, post;
        public TreeNode constructFromPrePost(int[] pre, int[] post) {
            this.pre = pre;
            this.post = post;
            return make(0, 0, pre.length);
        }

        public TreeNode make(int i0, int i1, int N) {
            if (N == 0) return null;
            TreeNode root = new TreeNode(pre[i0]);
            if (N == 1) return root;

            int L = 1;
            for (; L < N; ++L)
                if (post[i1 + L-1] == pre[i0 + 1])
                    break;

            root.left = make(i0+1, i1, L);
            root.right = make(i0+L+1, i1+L, N-1-L);
            return root;
        }
    }


}
