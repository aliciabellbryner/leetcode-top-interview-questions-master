package topinterviewquestions;

public class Problem_0543_DiameterofBinaryTree {
    public static class TreeNode {
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

    //recursive-dfs
    //Time complexity: O(N). This is because in our recursion function longestPath, we only enter and exit from each node once. We know this because each node is entered from its parent, and in a tree, nodes only have one parent.
    //
    //Space complexity: O(N). The space complexity depends on the size of our implicit call stack, which relates to the height of the tree. In the worst case, the tree is skewed so the height of the tree is O(N). If the tree is balanced, it'd be O(logN).
    public static class Info{
        int height;
        int maxDistance;
        public Info(int h, int m) {
            height = h;
            maxDistance = m;
        }
    }
    public static int diameterOfBinaryTree(TreeNode root) {
        return process(root).maxDistance;
    }

    public static Info process(TreeNode root){
        if (root == null) {
            return new Info(0, 0);
        }
        Info lInfo = process(root.left);
        Info rInfo = process(root.right);
        int height = Math.max(lInfo.height, rInfo.height) + 1;
        int maxDistance = Math.max(Math.max(lInfo.maxDistance, rInfo.maxDistance), lInfo.height + rInfo.height);
        return new Info(height, maxDistance);
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right = new TreeNode(3);
        System.out.println(diameterOfBinaryTree(root));
    }
}
