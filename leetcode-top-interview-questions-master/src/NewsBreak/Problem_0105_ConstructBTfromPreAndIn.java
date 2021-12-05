package NewsBreak;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Problem_0105_ConstructBTfromPreAndIn {
    //iterative
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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // deal with edge case(s)
        if (preorder.length == 0) {
            return null;
        }

        // build a map of the indices of the values as they appear in the inorder array
        Map<Integer, Integer> map = new HashMap<>();//(inorder[i] -> i)
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        // initialize the stack of tree nodes
        Stack<TreeNode> stack = new Stack<>();
        int value = preorder[0];
        TreeNode root = new TreeNode(value);
        stack.push(root);

        // for all remaining values...
        for (int i = 1; i < preorder.length; i ++) {
            // create a node
            value = preorder[i];
            TreeNode node = new TreeNode(value);

            if (map.get(value) < map.get(stack.peek().val)) {
                // the new node is on the left of the last node,
                // so it must be its left child (that's the way preorder works)
                stack.peek().left = node;
            } else {
                // the new node is on the right of the last node,
                // so it must be the right child of either the last node
                // or one of the last node's ancestors.
                // pop the stack until we either run out of ancestors
                // or the node at the top of the stack is to the right of the new node
                TreeNode parent = null;
                while(!stack.isEmpty() && map.get(value) > map.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = node;
            }
            stack.push(node);
        }

        return root;
    }

    //recursive
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        HashMap<Integer, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return helper(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, indexMap);
    }

    public TreeNode helper(int[] pre, int l1, int r1, int[] in, int l2, int r2, HashMap<Integer, Integer> map) {
        if (l1 > r1) {
            return null;
        }
        TreeNode cur = new TreeNode(pre[l1]);
        if (l1 == r1) {
            return cur;
        }
        int index = map.get(pre[l1]);
        cur.left = helper(pre, l1+1, index + l1 - l2, in, l2, index-1, map);
        cur.right = helper(pre, index+l1-l2+1, r1, in, index+1, r2, map);
        return cur;
    }
}
