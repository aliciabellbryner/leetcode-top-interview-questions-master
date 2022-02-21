package topinterviewquestions;
/*
You are given the root of a binary tree and an integer distance. A pair of two different leaf nodes of a binary tree is said to be good if the length of the shortest path between them is less than or equal to distance.

Return the number of good leaf node pairs in the tree.

Example 1:


Input: root = [1,2,3,null,4], distance = 3
Output: 1
Explanation: The leaf nodes of the tree are 3 and 4 and the length of the shortest path between them is 3. This is the only good pair.
Example 2:


Input: root = [1,2,3,4,5,6,7], distance = 3
Output: 2
Explanation: The good pairs are [4,5] and [6,7] with shortest path = 2. The pair [4,6] is not good because the length of ther shortest path between them is 4.
Example 3:

Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3
Output: 1
Explanation: The only good pair is [2,5].


Constraints:

The number of nodes in the tree is in the range [1, 210].
1 <= Node.val <= 100
1 <= distance <= 10
 */
//https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/756198/Java-DFS-Solution-with-a-Twist-100-Faster-Explained
public class Problem_1530_NumberOfGoodLeafNodesPairs {

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

  //Post-Order - Cache in Array
  //https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/755784/Java-Detailed-Explanation-Post-Order-Cache-in-Array
    //Store distances of all leaf nodes, at each non-leaf node, find those leaf nodes satisfying the condition, and accumulate into res.
  //Distance is from 0 - 10. We could use a size 11 array to count frequency, and ignore the leaf node with distance larger than 10.
  //What helper function returns: the number of leaf nodes for each distance. Let's name the return array is arr for the current node:
  // arr[i]: the number of leaf nodes, i: distance to current node is i.
    private int res;

    public int countPairs(TreeNode root, int distance) {

        res = 0;
        helper(root, distance);
        return res;
    }

    private int[] helper(TreeNode node, int distance) {//arr[i]: the number of leaf nodes, i: distance to current node is i.

        if (node == null) return new int[11];

        int[] left = helper(node.left, distance);
        int[] right = helper(node.right, distance);

        int[] arr = new int[11];//arr[i]: the number of leaf nodes, i: distance to current node is i.

        // node is leaf node, no child, just return
        if (node.left == null && node.right == null) {
            arr[1] = 1;
            return arr;
        }

        // find all nodes satisfying distance
        for (int i = 0; i <= 10; ++i) {
            for (int j = 0; j <= 10; ++j) {
                if (i + j <= distance) {
                    res += (left[i] * right[j]);
                }
            }
        }

        // increment all by 1, ignore the node distance larger than 10
        for (int i = 0; i <= 9; ++i) {
            arr[i + 1] += left[i];
            arr[i + 1] += right[i];
        }

        return arr;
    }
}
