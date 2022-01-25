package topinterviewquestions;

public class Problem_2096_StepByStepDirectionsFromaBinaryTreeNodetoAnother_G {
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
    //https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/discuss/1612105/3-Steps
    //Build directions for both start and destination from the root.
    //Say we get "LLRRL" and "LRR".
    //Remove common prefix path.
    //We remove "L", and now start direction is "LRRL", and destination - "RR"
    //Replace all steps in the start direction to "U" and add destination direction.
    //The result is "UUUU" + "RR".
    public String getDirections(TreeNode root, int startValue, int destValue) {
        StringBuilder fromStart = new StringBuilder(), fromDest = new StringBuilder();
        process(root, startValue, fromStart);
        process(root, destValue, fromDest);
        int i = 0;
        while (i < Math.min(fromDest.length(), fromStart.length()) && fromStart.charAt(i) == fromDest.charAt(i)) {
            //找到fromStart和fromDest的开始不一样的位置
            ++i;
        }
        return "U".repeat(fromStart.length() - i) + fromDest.toString().substring(i);
    }

    private boolean process(TreeNode n, int val, StringBuilder sb) {
        if (n.val == val) {
            return true;
        }
        if (n.left != null && process(n.left, val, sb)) {
            sb.insert(0, "L");
        } else if (n.right != null && process(n.right, val, sb)) {
            sb.insert(0, "R");
        }
        return sb.length() > 0;
    }

}
