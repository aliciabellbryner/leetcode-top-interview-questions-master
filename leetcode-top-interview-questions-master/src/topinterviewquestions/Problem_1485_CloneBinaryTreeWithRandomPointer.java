package topinterviewquestions;

import java.util.HashMap;
import java.util.Stack;

/*
A binary tree is given such that each node contains an additional random pointer which could point to any node in the tree or null.

Return a deep copy of the tree.

The tree is represented in the same input/output way as normal binary trees where each node is represented as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (in the input) where the random pointer points to, or null if it does not point to any node.
You will be given the tree in class Node and you should return the cloned tree in class NodeCopy. NodeCopy class is just a clone of Node class with the same attributes and constructors.



Example 1:


Input: root = [[1,null],null,[4,3],[7,0]]
Output: [[1,null],null,[4,3],[7,0]]
Explanation: The original binary tree is [1,null,4,7].
The random pointer of node one is null, so it is represented as [1, null].
The random pointer of node 4 is node 7, so it is represented as [4, 3] where 3 is the index of node 7 in the array representing the tree.
The random pointer of node 7 is node 1, so it is represented as [7, 0] where 0 is the index of node 1 in the array representing the tree.
Example 2:


Input: root = [[1,4],null,[1,0],null,[1,5],[1,5]]
Output: [[1,4],null,[1,0],null,[1,5],[1,5]]
Explanation: The random pointer of a node can be the node itself.
Example 3:


Input: root = [[1,6],[2,5],[3,4],[4,3],[5,2],[6,1],[7,0]]
Output: [[1,6],[2,5],[3,4],[4,3],[5,2],[6,1],[7,0]]


Constraints:

The number of nodes in the tree is in the range [0, 1000].
1 <= Node.val <= 106
 */
public class Problem_1485_CloneBinaryTreeWithRandomPointer {
    /*Approach 1: Iterative Tree Traversal + Hashmap
Intuition

Anytime we encounter a tree problem, we automatically know that we have to traverse the tree. Why? Well, we have to look at the tree to get an answer, right? So we will be (1) traversing the tree and (2) processing each node we look at.

There are 2 types of traversals: Depth-First-Search (DFS) and Breadth-First-Search (BFS). The simplest type of traversal is a preorder Depth-First Search. See below for a template we'll use later on.

# DFS Template

stack = [root]

while stack:
    node = stack.pop()
    if node.left:
        stack.append(node.left)
    if node.right:
        stack.append(node.right)
Algorithm

Okay. So we know we will be doing a DFS tree traversal. But what next? We mentioned earlier that we will be processing the nodes as we traverse the tree. Let's break that down into easy, actionable steps.

As we traverse the tree, we need to...

Create a copy of each node
Connect the copied nodes together
Bingo, that's it. You're done.

Kidding, let's figure out what this will look like a little more concretely. We're going to do two traversals; one to copy each node, and another to connect the copied nodes together.

Step 1: Create a copy of each node

We just established that our first processing step is creating a copy of each node in the original tree. Good, but we also need a place to store the copied nodes with easy lookup access for step 2.

What type of data structure fits this profile? Hashmap!

Why? Hashmaps let us map a key to value AND access that value in O(1) time.

Let's grab our DFS Template from earlier and add Step 1 to it.

# Step 1. Create a copy of each node

copy = {}   # Store our copied nodes here
stack = [root]

while stack:
    node = stack.pop()
    copy[node] = NodeCopy(node.val)  # Map the original node to its NodeCopy
    if node.left:
        stack.append(node.left)
    if node.right:
        stack.append(node.right)
This is how it looks visually:

Current
1 / 5
Interview Tip: You might be tempted to store the value of the node as the key (e.g.: copy[node.val]). This would be fine if all node values in the tree were unique, but the sample inputs show that this is not the case. We can guarantee uniqueness by storing the actual node as the key instead. This is an excellent assumption/topic to discuss with your interviewer.

Step 2: Connect the copied nodes together

At this point, we have a copy of each node in our tree stored in a hashmap. Our next step is to connect the left, right, and random pointers of the copied nodes together to form our original tree's structure. We will do this step in a second DFS traversal.

Interview Tip: Don't worry about prematurely optimizing your solution. Do this connecting step in a second DFS traversal because the Big O complexity of a two pass DFS is the same as a one pass DFS. Mention this thought process to your interviewer.

The code snippet is below. copy[node] looks up and retrieves the current node's copy. We do copy[node].left to set the current node's copy left pointer equal to the copy of the current node's left node. Same idea for right and random pointers.

# Step 2. Connect the copied nodes together

stack = [root]

while stack:
    node = stack.pop()
    if node.left:
        copy[node].left = copy[node.left]      # connect left pointer
        stack.append(node.left)
    if node.right:
        copy[node].right = copy[node.right]    # connect right pointer
        stack.append(node.right)
    if node.random:
        copy[node].random = copy[node.random]  # connect random pointer
This is how it looks visually:

Current
1 / 5
Code

Congrats, you've solved the problem! Put the steps together and we get the following solution:


Pop Quiz: What would happen if we added node.random to the stack in Step 1?

Answer: We would enter an infinite recursive loop and receive a TLE error because we would be creating a cycle within our tree traversal.

Complexity Analysis

Let nn be the number of nodes in the tree.

Time complexity: O(n + n) = O(2n) = O(n)O(n+n)=O(2n)=O(n).

Each traversal costs O(n)O(n) because we check every node once. We traverse the tree twice, which gives us O(n + n)= O(2n)O(n+n)=O(2n). In Big O, we drop any constants to get O(n)O(n) as our final time complexity.
Space complexity: O(n)O(n) linear space to store a copy of each node in our hashmap. Also worth mentioning that the stack space for our DFS can grow to worst-case O(n) for a skewed binary tree.

Approach 2: Recursive Tree Traversal + Hashmap
All is well. You've finished the problem. But your interviewer asks you the awesome follow up question.

"Can you do it recursively?"

More information on recursive traversals can be found on the Binary Tree Explore Card

Code


Complexity Analysis

Let nn be the number of nodes in the tree.

Time complexity: O(n + n) = O(2n) = O(n)O(n+n)=O(2n)=O(n) Same reasoning as the iterative approach.

Space complexity: O(n)O(n) Same reasoning as the iterative approach.

     */

    /*
    class Solution1 {
        public NodeCopy copyRandomBinaryTree(Node root) {
            if (root == null) {
                return null;
            }

            // Step 1. Create a copy of each node
            Map<Node, NodeCopy> copy = new HashMap<>();
            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node node = stack.pop();
                NodeCopy copyNode = new NodeCopy(node.val);
                copy.put(node, copyNode);

                if (node.left != null) {
                    stack.push(node.left);
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
            }

            // Step 2. Connect the copied nodes together
            stack.push(root);
            while (!stack.isEmpty()) {
                Node node = stack.pop();

                if (node.left != null) {
                    copy.get(node).left = copy.get(node.left);
                    stack.push(node.left);
                }
                if (node.right != null) {
                    copy.get(node).right = copy.get(node.right);
                    stack.push(node.right);
                }
                if (node.random != null) {
                    copy.get(node).random = copy.get(node.random);
                }
            }

            return copy.get(root);
        }

class Solution2 {
    public NodeCopy copyRandomBinaryTree(Node root) {
        Map<Node, NodeCopy> copy = new HashMap<>();
        dfsCopy(root, copy);
        dfsConnect(root, copy);
        return copy.get(root);
    }

    private NodeCopy dfsCopy(Node root, Map<Node, NodeCopy> copy){
        if (root == null) {
            return null;
        }
        NodeCopy copyNode = new NodeCopy(root.val);
        copy.put(root, copyNode);
        dfsCopy(root.left, copy);
        dfsCopy(root.right, copy);
        return copyNode;
    }

    private void dfsConnect(Node node, Map<Node, NodeCopy> copy){
        if (node == null) {
            return;
        }
        if (node.left != null) {
            copy.get(node).left = copy.get(node.left);
        }
        if (node.right != null) {
            copy.get(node).right = copy.get(node.right);
        }
        if (node.random != null) {
            copy.get(node).random = copy.get(node.random);
        }
        dfsConnect(node.left, copy);
        dfsConnect(node.right, copy);
    }
}


     */


    /*
    //diss

    This can be solved with a simple DFS with a HashMap.
First we check if the node is null, if not, we check if it's in our map, if not - we create a copy of our current node and run DFS again on the left, right and random nodes.

One edge case to notice is that if there's a cycle in the tree (a random pointer looking at previous visited node) then the map would never be filled.
To solve this we need to create the CopyNode, and immediately put it in the map.
That way, when there's a cycle - we'd just return the node we put in the map and end the infinite loop.

class Solution {
    public NodeCopy copyRandomBinaryTree(Node root) {
        Map<Node,NodeCopy> map = new HashMap<>();
        return dfs(root, map);
    }

    public NodeCopy dfs(Node root, Map<Node,NodeCopy> map){
        if (root==null) return null;

        if (map.containsKey(root)) return map.get(root);

        NodeCopy newNode = new NodeCopy(root.val);
        map.put(root,newNode);

        newNode.left=dfs(root.left,map);
        newNode.right=dfs(root.right,map);
        newNode.random=dfs(root.random,map);

        return newNode;

    }
}
Time complexity: O(n) - we run on the entire tree once. Our map acts as memoization to prevent redoing work.
Space copmlexity: O(n) - We're creating a hashmap to save all nodes in the tree.

     */
  public class Node {
      int val;
      Node left;
      Node right;
      Node random;
      Node() {}
      Node(int val) { this.val = val; }
      Node(int val, Node left, Node right, Node random) {
          this.val = val;
          this.left = left;
          this.right = right;
          this.random = random;
      }
  }


}
