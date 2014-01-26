import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    // list[0] store max total sum, list[1] store max height sum
    // WTF!!
    private void maxPathSum(TreeNode root, ArrayList<Integer> list) {
        if(root == null) {
            list.add(null);
            list.add(null);
            return;
        }
        ArrayList<Integer> leftList = new ArrayList<Integer>();
        maxPathSum(root.left, leftList);
        ArrayList<Integer> rightList = new ArrayList<Integer>();
        maxPathSum(root.right, rightList);
        Integer leftTotal = leftList.get(0), leftHeight = leftList.get(1);
        Integer rightTotal = rightList.get(0), rightHeight = rightList.get(1);
        Integer curHeight, curTotal;
        if(leftTotal == null && rightTotal == null) {
            curHeight = root.val;
            curTotal = root.val;
        }
        else if(leftTotal == null || rightTotal == null) {
            Integer height = (leftHeight == null ? rightHeight : leftHeight);
            Integer total = (leftTotal == null) ? rightTotal : leftTotal;
            curHeight = Math.max(root.val, height + root.val);
            curTotal = Math.max(Math.max(root.val, height + root.val), total);
        }
        else {
            curHeight = Math.max(Math.max(leftHeight, rightHeight) + root.val, root.val);
            curTotal = Math.max(Math.max(Math.max(leftTotal, rightTotal), leftHeight + rightHeight + root.val), root.val);
            curTotal = Math.max(curTotal, Math.max(leftHeight + root.val, rightHeight + root.val));
        }
        list.add(curTotal);
        list.add(curHeight);
    }
    
    public int maxPathSum(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        maxPathSum(root, list);
        return list.get(0);
    }

    public int maxPathSum2(TreeNode root) {  
        // pass in an Object that will be filled in the two values
        Data data = maxSubPath(root);
        return data.sum;
    }

    private class Data {
        int path = 0;
        int sum = Integer.MIN_VALUE;
    }

    private Data maxSubPath(TreeNode root) {
        Data data = new Data();
        if (root == null) return data;

        Data l = maxSubPath(root.left);
        Data r = maxSubPath(root.right);

        data.path = Math.max(0, Math.max(l.path, r.path) + root.val);
        data.sum = Math.max(Math.max(l.sum, r.sum), l.path+root.val+r.path);
        return data;
    }
}

class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode root = new TreeNode(1);
        TreeNode t1 = new TreeNode(2);
        root.left = t1;
        TreeNode t2 = new TreeNode(3);
        root.right = t2;
        System.out.println(solution.maxPathSum2(root));
    }
}