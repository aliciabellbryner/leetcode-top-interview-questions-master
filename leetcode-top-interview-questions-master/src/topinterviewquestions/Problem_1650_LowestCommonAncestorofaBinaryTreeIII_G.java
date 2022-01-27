package topinterviewquestions;

public class Problem_1650_LowestCommonAncestorofaBinaryTreeIII_G {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/discuss/932914/Java-in-6-lines
    public Node lowestCommonAncestor(Node p, Node q) {
        Node a = p, b = q;
        while (a != b) {
            a = a == null? q : a.parent;
            b = b == null? p : b.parent;
        }
        return a;
    }



}

