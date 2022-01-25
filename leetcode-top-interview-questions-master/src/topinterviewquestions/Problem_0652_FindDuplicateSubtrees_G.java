package topinterviewquestions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Problem_0652_FindDuplicateSubtrees_G {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //https://leetcode.com/problems/find-duplicate-subtrees/discuss/106011/Java-Concise-Postorder-Traversal-Solution
    //time complexity is O(n^2)
    //We perform postorder traversal, serializing and hashing the serials of subtrees in the process.
    // We can recognize a duplicate subtree by its serialization.
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> res = new LinkedList<>();
        postorder(root, new HashMap<>(), res);
        return res;
    }

    public String postorder(TreeNode cur, Map<String, Integer> map, List<TreeNode> res) {
        if (cur == null) {
            return "#";
        }
        String serial = cur.val + "," + postorder(cur.left, map, res) + "," + postorder(cur.right, map, res);
        //why this is postorder not preorder: https://leetcode.com/problems/find-duplicate-subtrees/discuss/106011/Java-Concise-Postorder-Traversal-Solution/213062
        map.put(serial, map.getOrDefault(serial, 0) + 1);
        if (map.get(serial) == 2) {
            res.add(cur);
        }
        return serial;
    }

    //followup: We can improve this to O(n) by replacing full serializations with serial ids instead.
    //思路就相当于还是用postorder，每个leaf节点就相当于是serialId = 1, 再往上一层依次加一，所以如果同个id有两个一样的serial的话说明找到了duplicate
    class Solution {
        int curId = 1;

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            Map<String, Integer> serialToId = new HashMap<>();//key是serial的string， value是serialId
            Map<Integer, Integer> idToCount = new HashMap<>();//key是serialid, value是同个serial string且同个serialId的数量
            List<TreeNode> res = new LinkedList<>();
            postorder(root, serialToId, idToCount, res);
            return res;
        }

        private int postorder(TreeNode root, Map<String, Integer> serialToId, Map<Integer, Integer> idToCount, List<TreeNode> res) {
            if (root == null) {
                return 0;
            }
            int leftId = postorder(root.left, serialToId, idToCount, res);
            int rightId = postorder(root.right, serialToId, idToCount, res);
            String curSerial = leftId + "," + root.val + "," + rightId;
            int serialId = serialToId.getOrDefault(curSerial, curId);
            if (serialId == curId) {
                curId++;
            }
            serialToId.put(curSerial, serialId);
            idToCount.put(serialId, idToCount.getOrDefault(serialId, 0) + 1);
            if (idToCount.get(serialId) == 2) {
                res.add(root);
            }
            return serialId;
        }

    }

}
