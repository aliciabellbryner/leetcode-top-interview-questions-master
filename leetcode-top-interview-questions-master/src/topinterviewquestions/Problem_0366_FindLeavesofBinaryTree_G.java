package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0366_FindLeavesofBinaryTree_G {
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

  //https://leetcode.com/problems/find-leaves-of-binary-tree/solution/
  //Approach 2: DFS (Depth-First Search) without sorting
  //Time Complexity: Assuming N is the total number of nodes in the binary tree,
  // traversing the tree takes O(N) time and storing all the pairs at the correct position also takes O(N) time.
  // Hence overall time complexity of this approach is O(N).
  //Space Complexity: O(N), the space used by solution array.
    private List<List<Integer>> solution;

    private int getHeight(TreeNode root) {
        // return -1 for null nodes
        if (root == null) {
            return -1;
        }

        // first calculate the height of the left and right children
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        int currHeight = Math.max(leftHeight, rightHeight) + 1;

        if (this.solution.size() == currHeight) {//比如是leaf的话，他的左右两边都return -1，
            // 那么他的currheight = -1 + 1 = 0, 这个时候solution.size()==0,所以加一个arraylist
            this.solution.add(new ArrayList<>());
        }

        this.solution.get(currHeight).add(root.val);

        return currHeight;
    }

    public List<List<Integer>> findLeaves(TreeNode root) {
        this.solution = new ArrayList<>();
        getHeight(root);
        return this.solution;
    }
}
