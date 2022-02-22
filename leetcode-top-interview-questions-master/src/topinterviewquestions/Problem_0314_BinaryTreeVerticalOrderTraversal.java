package topinterviewquestions;

import java.util.*;

/*
Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from left to right.



Example 1:


Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Example 2:


Input: root = [3,9,8,4,0,1,7]
Output: [[4],[9],[3,0,1],[8],[7]]
Example 3:


Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
Output: [[4],[9,5],[3,0,1],[8,2],[7]]


Constraints:

The number of nodes in the tree is in the range [0, 100].
-100 <= Node.val <= 100
 */
public class Problem_0314_BinaryTreeVerticalOrderTraversal {
    /*
    Solution
Overview
This is yet another problem about Binary Tree traversals. As one would probably know, the common strategies to traverse a Tree data structure are Breadth-First Search (a.k.a BFS) and Depth-First Search (a.k.a. DFS).

The DFS strategy can be further distinguished as preorder DFS, inorder DFS and postorder DFS, depending on the relative order of visit among the node itself and its child nodes.

If one is not familiar with the concepts of BFS and DFS, one can find the corresponding problems on LeetCode to practice with. Also, we have an Explore card called Queue & Stack where we cover both the BFS traversal as well as the DFS traversal. Hence, in this article, we won't repeat ourselves on these concepts.

In the problem description, we are asked to return the vertical order of a binary tree, which actually implies two sub-orders, where each node would have a 2-dimensional index (denoted as <column, row>)

tree in 2D coordinates

column-wise order

If we look at a binary tree horizontally, each node can be aligned to a specific column, based on its relative offset to the root node of the tree.

Let us assume that the root node has a column index of 0, then its left child node would have a column index of -1 and its right child node would have a column index of +1, and so on.

row-wise order
Now if we put the nodes into a vertical dimension, each node would be assigned to a specific row, based on its level (i.e. the vertical distance to the root node).

Let us assume that the root node has a row index of 0, then both its child nodes would have the row index of 1.

Given the above definitions, we can now formulate the problem as a task to order the nodes based on the 2-dimensional coordinates that we defined above.

More specifically, the nodes should be ordered by column first, and further the nodes on the same column should be ordered vertically based on their row indices.


Approach 1: Breadth-First Search (BFS)
Intuition

With the formulation of the problem in the overview section, one of the most intuitive solutions to tackle the problem would be applying the BFS traversal, where the nodes would be visited level by level.

With the BFS traversal, we naturally can guarantee the vertical order of the visits, i.e. the nodes at higher levels (large row values) would get visited later than the ones at lower levels.

However, we are still missing the horizontal order ( the column order). To ensure this order, we need to do some additional processing during the BFS traversal.

The idea is that we keep a hash table (let's denote it as columnTable<key, value>), where we keep the node values grouped by the column index.

The key in the hash table would be the column index, and the corresponding value would be a list which contains the values of all the nodes that share the same column index.

In addition, the values in the corresponding list should be ordered by their row indices, which would be guaranteed by the BFS traversal as we mentioned before.

Algorithm

We elaborate on the steps to implement the above idea.

First, we create a hash table named columnTable to keep track of the results.

As to the BFS traversal, a common code pattern would be to use a queue data structure to keep track of the order we need to visit nodes. We initialize the queue by putting the root node along with its column index value (0).

We then run the BFS traversal with a loop consuming the elements from the queue.

At each iteration within the BFS, we pop out an element from the queue. The element consists of a node and its corresponding column index. If the node is not empty, we then populate the columnTable with the value of the node. Subsequently, we then put its child nodes along with their respective column indices (i.e. column-1 and column+1) into the queue.

At the end of the BFS traversal, we obtain a hash table that contains the desired node values grouped by their column indices. For each group of values, they are further ordered by their row indices.

We then sort the hash table by its keys, i.e. column index in ascending order. And finally we return the results column by column.


Complexity Analysis

Time Complexity: \mathcal{O}(N \log N)O(NlogN) where NN is the number of nodes in the tree.

In the first part of the algorithm, we do the BFS traversal, whose time complexity is \mathcal{O}(N)O(N) since we traversed each node once and only once.

In the second part, in order to return the ordered results, we then sort the obtained hash table by its keys, which could result in the \mathcal{O}(N \log N)O(NlogN) time complexity in the worst case scenario where the binary tree is extremely imbalanced (for instance, each node has only left child node.)

As a result, the overall time complexity of the algorithm would be \mathcal{O}(N \log N)O(NlogN).

Space Complexity: \mathcal{O}(N)O(N) where NN is the number of nodes in the tree.

First of all, we use a hash table to group the nodes with the same column index. The hash table consists of keys and values. In any case, the values would consume \mathcal{O}(N)O(N) memory. While the space for the keys could vary, in the worst case, each node has a unique column index, i.e. there would be as many keys as the values. Hence, the total space complexity for the hash table would still be \mathcal{O}(N)O(N).

During the BFS traversal, we use a queue data structure to keep track of the next nodes to visit. At any given moment, the queue would hold no more two levels of nodes. For a binary tree, the maximum number of nodes at a level would be \frac{N+1}{2}
2
N+1
​
  which is also the number of leafs in a full binary tree. As a result, in the worst case, our queue would consume at most \mathcal{O}(\frac{N+1}{2} \cdot 2) = \mathcal{O}(N)O(
2
N+1
​
 ⋅2)=O(N) space.

Lastly, we also need some space to hold the results, which is basically a reordered hash table of size \mathcal{O}(N)O(N) as we discussed before.

To sum up, the overall space complexity of our algorithm would be \mathcal{O}(N)O(N).



Approach 2: BFS without Sorting
Intuition

In the previous approach, it is a pity that the sorting of results overshadows the main part of the algorithm which is the BFS traversal. One might wonder if we have a way to eliminate the need for sorting. And the answer is yes.

The key insight is that we only need to know the range of the column index (i.e. [min_column, max_column]). Then we can simply iterate through this range to generate the outputs without the need for sorting.

The above insight would work under the condition that there won't be any missing column index in the given range. And the condition always holds, since there won't be any broken branch in a binary tree.

Algorithm

To implement this optimization, it suffices to make some small modifications to our previous BFS approach.

During the BFS traversal, we could obtain the range of the column indices, i.e. with the variable of min_column and max_column.

At the end of the BFS traversal, we would then walk through the column range [min_column, max_column] and retrieve the results accordingly.

Current
1 / 14

Complexity Analysis

Time Complexity: \mathcal{O}(N)O(N) where NN is the number of nodes in the tree.
Following the same analysis in the previous BFS approach, the only difference is that this time we don't need the costy sorting operation (i.e. \mathcal{O}(N \log N)O(NlogN)).

Space Complexity: \mathcal{O}(N)O(N) where NN is the number of nodes in the tree. The analysis follows the same logic as in the previous BFS approach.


Approach 3: Depth-First Search (DFS)
Intuition

Although we applied a BFS traversal in both of the previous approaches, it is not impossible to solve the problem with a DFS traversal.

As we discussed in the overview section, once we assign a 2-dimensional index (i.e. <column, row>) for each node in the binary tree, to output the tree in vertical order is to sort the nodes based on the 2-dimensional index, firstly by column then by row, as shown in the following graph.

tree to table

Compared to the DFS traversal, the BFS traversal gives us a head start, since the nodes in higher rows would be visited later than the ones in the lower lows. As a result, we only need to focus on the column order.

That being said, we could simply traverse the tree in any DFS order (preorder, inorder or postorder), then we sort the resulting list strictly based on two keys <column, row>, which would give us the same results as the BFS traversal.

An important note is that two nodes might share the same <column, row>, in the case, as stated in the problem, the order between these two nodes should be from left to right as we did for BFS traversals. As a result, to ensure such a priority, one should make sure to visit the left child node before the right child node during the DFS traversal.

Algorithm

Here we implement the above algorithm, with the trick that we applied in Approach 2 (BFS without sorting) where we obtained the range of column during the traversal.

First, we conduct a DFS traversal on the input tree. During the traversal, we would then build a similar columnTable with the column index as the key and the list of (row, val) tuples as the value.

At the end of the DFS traversal, we iterate through the columnTable via the key of column index. Accordingly, we have a list of (row, val) tuples associated with each key. We then sort this list, based on the row index.

After the above steps, we would then obtain a list of node values ordered firstly by its column index and then by its row index, which is exactly the the vertical order traversal of binary tree as defined in the problem.


Complexity Analysis

Time Complexity: \mathcal{O}\big(W \cdot H \log{H})\big)O(W⋅HlogH)) where WW is the width of the binary tree (i.e. the number of columns in the result) and HH is the height of the tree.
In the first part of the algorithm, we traverse the tree in DFS, which results in \mathcal{O}(N)O(N) time complexity.

Once we build the columnTable, we then have to sort it column by column.

Let us assume the time complexity of the sorting algorithm to be \mathcal{O}(K \log K)O(KlogK) where KK is the length of the input. The maximal number of nodes in a column would be \frac{H}{2}
2
H
​
  where HH is the height of the tree, due to the zigzag nature of the node distribution. As a result, the upper bound of time complexity to sort a column in a binary tree would be \mathcal{O}(\frac{H}{2} \log \frac{H}{2})O(
2
H
​
 log
2
H
​
 ).

Since we need to sort WW columns, the total time complexity of the sorting operation would then be \mathcal{O}\big(W \cdot (\frac{H}{2} \log{\frac{H}{2}})\big) = \mathcal{O}(W \cdot H \log{H})O(W⋅(
2
H
​
 log
2
H
​
 ))=O(W⋅HlogH). Note that, the total number of nodes NN in a tree is bounded by W \cdot HW⋅H, i.e. N < W \cdot HN<W⋅H. As a result, the time complexity of \mathcal{O}\big(W \cdot H \log{H}\big)O(W⋅HlogH) will dominate the \mathcal{O}(N)O(N) of the DFS traversal in the first part.

At the end of the DFS traversal, we have to iterate through the columnTable in order to retrieve the values, which will take another \mathcal{O}(N)O(N) time.

To sum up, the overall time complexity of the algorithm would be \mathcal{O}\big(W \cdot H \log{H}\big)O(W⋅HlogH).

An interesting thing to note is that in the case where the binary tree is completely imbalanced (e.g. node has only left child.), this DFS approach would have the \mathcal{O}(N)O(N) time complexity, since the sorting takes no time on columns that contains only a single node. While the time complexity for our first BFS approach would be \mathcal{O}{(N \log N)}O(NlogN), since we have to sort the NN keys in the columnTable.

Space Complexity: \mathcal{O}(N)O(N) where NN is the number of nodes in the tree.

We kept the columnTable which contains all the node values in the binary tree. Together with the keys, it would consume \mathcal{O}(N)O(N) space as we discussed in previous approaches.

Since we apply the recursion for our DFS traversal, it would incur additional space consumption on the function call stack. In the worst case where the tree is completely imbalanced, we would have the size of call stack up to \mathcal{O}(N)O(N).

Finally, we have the output which contains all the values in the binary tree, thus \mathcal{O}(N)O(N) space.

So in total, the overall space complexity of this algorithm remains \mathcal{O}(N)O(N).
     */
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    /*
    class Solution1 {
        public List<List<Integer>> verticalOrder(TreeNode root) {
            List<List<Integer>> output = new ArrayList();
            if (root == null) {
                return output;
            }

            Map<Integer, ArrayList> columnTable = new HashMap();
            Queue<Pair<TreeNode, Integer>> queue = new ArrayDeque();
            int column = 0;
            queue.offer(new Pair(root, column));

            while (!queue.isEmpty()) {
                Pair<TreeNode, Integer> p = queue.poll();
                root = p.getKey();
                column = p.getValue();

                if (root != null) {
                    if (!columnTable.containsKey(column)) {
                        columnTable.put(column, new ArrayList<Integer>());
                    }
                    columnTable.get(column).add(root.val);

                    queue.offer(new Pair(root.left, column - 1));
                    queue.offer(new Pair(root.right, column + 1));
                }
            }

            List<Integer> sortedKeys = new ArrayList<Integer>(columnTable.keySet());
            Collections.sort(sortedKeys);
            for(int k : sortedKeys) {
                output.add(columnTable.get(k));
            }

            return output;
        }
    }

     */


/*
    class Solution2 {
        public List<List<Integer>> verticalOrder(TreeNode root) {
            List<List<Integer>> output = new ArrayList();
            if (root == null) {
                return output;
            }

            Map<Integer, ArrayList> columnTable = new HashMap();
            // Pair of node and its column offset
            Queue<Pair<TreeNode, Integer>> queue = new ArrayDeque();
            int column = 0;
            queue.offer(new Pair(root, column));

            int minColumn = 0, maxColumn = 0;

            while (!queue.isEmpty()) {
                Pair<TreeNode, Integer> p = queue.poll();
                root = p.getKey();
                column = p.getValue();

                if (root != null) {
                    if (!columnTable.containsKey(column)) {
                        columnTable.put(column, new ArrayList<Integer>());
                    }
                    columnTable.get(column).add(root.val);
                    minColumn = Math.min(minColumn, column);
                    maxColumn = Math.max(maxColumn, column);

                    queue.offer(new Pair(root.left, column - 1));
                    queue.offer(new Pair(root.right, column + 1));
                }
            }

            for(int i = minColumn; i < maxColumn + 1; ++i) {
                output.add(columnTable.get(i));
            }

            return output;
        }
    }

 */

    /*
    class Solution3 {
        Map<Integer, ArrayList<Pair<Integer, Integer>>> columnTable = new HashMap();
        int minColumn = 0, maxColumn = 0;

        private void DFS(TreeNode node, Integer row, Integer column) {
            if (node == null)
                return;

            if (!columnTable.containsKey(column)) {
                this.columnTable.put(column, new ArrayList<Pair<Integer, Integer>>());
            }

            this.columnTable.get(column).add(new Pair<Integer, Integer>(row, node.val));
            this.minColumn = Math.min(minColumn, column);
            this.maxColumn = Math.max(maxColumn, column);
            // preorder DFS traversal
            this.DFS(node.left, row + 1, column - 1);
            this.DFS(node.right, row + 1, column + 1);
        }

        public List<List<Integer>> verticalOrder(TreeNode root) {
            List<List<Integer>> output = new ArrayList();
            if (root == null) {
                return output;
            }

            this.DFS(root, 0, 0);

            // Retrieve the resuts, by ordering by column and sorting by row
            for (int i = minColumn; i < maxColumn + 1; ++i) {

                Collections.sort(columnTable.get(i), new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                        return p1.getKey() - p2.getKey();
                    }
                });

                List<Integer> sortedColumn = new ArrayList();
                for (Pair<Integer, Integer> p : columnTable.get(i)) {
                    sortedColumn.add(p.getValue());
                }
                output.add(sortedColumn);
            }

            return output;
        }
    }

     */


    //discussion
    //5ms Java Clean Solution
    //The following solution takes 5ms.
    //
    //BFS, put node, col into queue at the same time
    //Every left child access col - 1 while right child col + 1
    //This maps node into different col buckets
    //Get col boundary min and max on the fly
    //Retrieve result from cols
    //Note that TreeMap version takes 9ms.
    //
    //Here is an example of [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]. Notice that every child access changes one column bucket id. So 12 actually goes ahead of 11.
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();

        q.add(root);
        cols.add(0);

        int min = 0;
        int max = 0;

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            int col = cols.poll();

            if (!map.containsKey(col)) {
                map.put(col, new ArrayList<Integer>());
            }
            map.get(col).add(node.val);

            if (node.left != null) {
                q.add(node.left);
                cols.add(col - 1);
                min = Math.min(min, col - 1);
            }

            if (node.right != null) {
                q.add(node.right);
                cols.add(col + 1);
                max = Math.max(max, col + 1);
            }
        }

        for (int i = min; i <= max; i++) {
            res.add(map.get(i));
        }

        return res;
    }
//Alternatively, we can calculate the rang first, then insert into buckets. Credit to @Jinx_boom
public List<List<Integer>> verticalOrder2(TreeNode root) {
    List<List<Integer>> cols = new ArrayList<>();
    if (root == null) {
        return cols;
    }

    int[] range = new int[] {0, 0};
    getRange(root, range, 0);

    for (int i = range[0]; i <= range[1]; i++) {
        cols.add(new ArrayList<Integer>());
    }

    Queue<TreeNode> queue = new LinkedList<>();
    Queue<Integer> colQueue = new LinkedList<>();

    queue.add(root);
    colQueue.add(-range[0]);

    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        int col = colQueue.poll();

        cols.get(col).add(node.val);

        if (node.left != null) {
            queue.add(node.left);
            colQueue.add(col - 1);
        }
        if (node.right != null) {
            queue.add(node.right);
            colQueue.add(col + 1);
        }
    }

    return cols;
}

    public void getRange(TreeNode root, int[] range, int col) {
        if (root == null) {
            return;
        }
        range[0] = Math.min(range[0], col);
        range[1] = Math.max(range[1], col);

        getRange(root.left, range, col - 1);
        getRange(root.right, range, col + 1);
    }



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



}
