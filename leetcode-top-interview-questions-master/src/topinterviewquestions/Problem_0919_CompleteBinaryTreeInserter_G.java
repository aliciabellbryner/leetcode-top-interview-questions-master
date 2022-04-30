package topinterviewquestions;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0919_CompleteBinaryTreeInserter_G {
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

    //https://leetcode-cn.com/problems/complete-binary-tree-inserter/solution/wan-quan-er-cha-shu-cha-ru-qi-by-leetcode/
    //方法 1：双端队列
    //想法
    //
    //将所有节点编号，按照从上到下从左到右的顺序。
    //
    //在每个插入步骤中，我们希望插入到一个编号最小的节点（这样有 0 或者 1 个孩子）。
    //
    //通过维护一个 deque （双端队列），保存这些节点的编号，我们可以解决这个问题。插入一个节点之后，将成为最高编号的节点，并且没有孩子，所以插入到队列的后端。为了找到最小数字的节点，我们从队列前端弹出元素。
    //
    //算法
    //
    //首先，通过广度优先搜索将 deque 中插入含有 0 个或者 1 个孩子的节点编号。
    //
    //然后插入节点，父亲是 deque 的第一个元素，我们将新节点加入我们的 deque。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/complete-binary-tree-inserter/solution/wan-quan-er-cha-shu-cha-ru-qi-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class CBTInserter {
        TreeNode root;
        Deque<TreeNode> deque;
        public CBTInserter(TreeNode root) {
            this.root = root;
            deque = new LinkedList();
            Queue<TreeNode> queue = new LinkedList();
            queue.offer(root);

            // BFS to populate deque
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left == null || node.right == null)
                    deque.offerLast(node);
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }
        }

        public int insert(int v) {
            TreeNode node = deque.peekFirst();
            deque.offerLast(new TreeNode(v));
            if (node.left == null)
                node.left = deque.peekLast();
            else {
                node.right = deque.peekLast();
                deque.pollFirst();
            }

            return node.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }

}
