package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem_1110_DeleteNodesAndReturnForest_G {

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


    //https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328860/Simple-Java-Sol
    class Solution {
        public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
            List<TreeNode> res = new ArrayList<>();
            if (root == null) return res;
            Set<Integer> todel_set = new HashSet<>();
            for (int i : to_delete) {
                todel_set.add(i);
            }
            deleteNodes(root, todel_set, res);
            if (!todel_set.contains(root.val)) {
                res.add(root);
            }
            return res;
        }

        private TreeNode deleteNodes(TreeNode node, Set<Integer> todel_set, List<TreeNode> res) {
            if (node == null) return null;

            node.left = deleteNodes(node.left, todel_set, res);
            node.right = deleteNodes(node.right, todel_set, res);

            if (todel_set.contains(node.val)) {
                if (node.left != null) res.add(node.left);
                if (node.right != null) res.add(node.right);
                return null;
            }

            return node;
        }

    }


    //As I keep saying in my video "courses",
    //solve tree problem with recursion first.
    //https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328853/JavaC%2B%2BPython-Recursion-Solution
    //
    //Explanation
    //If a node is root (has no parent) and isn't deleted,
    //when will we add it to the result.
    //
    //
    //Complexity
    //Time O(N)
    //Space O(H + N), where H is the height of tree.
    class Solution2 {
        Set<Integer> to_delete_set;
        List<TreeNode> res;
        public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
            to_delete_set = new HashSet<>();
            res = new ArrayList<>();
            for (int i : to_delete)
                to_delete_set.add(i);
            helper(root, true);
            return res;
        }

        private TreeNode helper(TreeNode node, boolean is_root) {
            if (node == null) return null;
            boolean deleted = to_delete_set.contains(node.val);
            if (is_root && !deleted) res.add(node);
            node.left = helper(node.left, deleted);
            node.right =  helper(node.right, deleted);
            return deleted ? null : node;
        }
    }

}
