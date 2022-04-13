package topinterviewquestions;

public class Problem_0250_CountUnivalueSubtrees_G {
    //题意是说如果一个结点的root.val和左子树和右子树的值都相等, 那么就计数+1. 因此我们只需要一个递归的判定一个结点出发,是否其所有结点都相等即可.叶子结点也算是一个结果.

        /**
         * Definition for a binary tree node.
          public class TreeNode {
              int val;
              TreeNode left;
              TreeNode right;
              TreeNode(int x) { val = x; }
          }
         */

        // A subtree of a tree T is a tree S consisting of a node in T and all of its descendants in T.

        public static class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        //我们想想能不能一次遍历就都搞定，这样想，符合条件的相同值的字数肯定是有叶节点的，而且叶节点也都相同(注意单独的一个叶节点也被看做是一个相同值子树)，
        // 那么可以从下往上 check，采用后序遍历的顺序，左右根，这里还是递归调用函数，对于当前遍历到的节点，如果对其左右子节点分别递归调用函数，返回均为 true 的话，
        // 那么说明当前节点的值和左右子树的值都相同，那么又多了一棵树，所以结果自增1，然后返回当前节点值和给定值(其父节点值)是否相同，从而回归上一层递归调用。
        // 这里特别说明一下在子函数中要使用的那个单竖杠或，为什么不用双竖杠的或，因为单竖杠的或是位或，就是说左右两部分都需要被计算，然后再或，C++ 这里将 true 当作1，
        // false 当作0，然后进行 Bit OR 运算。不能使用双竖杠或的原因是，如果是双竖杠或，一旦左半边为 true 了，整个就直接是 true 了，右半边就不会再计算了，
        // 这样的话，一旦右子树中有值相同的子树也不会被计算到结果 res 中了，
        //https://www.cnblogs.com/grandyang/p/5206862.html


            static int res = 0;

            public static int countUnivalSubtrees(TreeNode root) {

                isUnival(root, -1);
                return res;
            }

            //从下往上 check，采用后序遍历的顺序，左右根，这里还是递归调用函数
            static boolean isUnival(TreeNode root, int val) {
                if (root == null) {
                    return true;
                }

                boolean left = isUnival(root.left, root.val); // return meaning left is, and left-val == root-val
                boolean right = isUnival(root.right, root.val);
                if (!left | !right) {//不用｜｜的原因是｜会算左右两边，再算这个值，但是｜｜的话如果左边是true它就直接返回不会计算右边了（short circuit），那么右边的res就不会加1了
                    return false;//意味着当前节点的值和左右子树的值至少有一个不相同
                }

                ++res;//那么说明当前节点的值和左右子树的值都相同，那么又多了一棵树，所以结果自增1

                return root.val == val;

            }






    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        System.out.println(countUnivalSubtrees(root));

    }
}
