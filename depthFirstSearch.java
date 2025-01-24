public class depthFirstSearch { // maze solving with depth first search AKA inorder traversal

    public int num;
    public depthFirstSearch left;
    public depthFirstSearch right;

    depthFirstSearch(int num) {
        this.num = num;
    }

    public depthFirstSearch(int num, depthFirstSearch left, depthFirstSearch right) {
        this.num = num;
        this.left = left;
        this.right = right;
    }

    public depthFirstSearch() {

    }

    public depthFirstSearch(char[] chars, depthFirstSearch left, depthFirstSearch right) {
    }


    static depthFirstSearch arrayToTree(char[][] array, int index) {
        if (index >= array.length)
            return null;
        if (array[index] == null)
            return null;
        return new depthFirstSearch(array[index], arrayToTree(array, index * 2 + 1), arrayToTree(array, index * 2 + 2));
    }


//    public void  depthFirstSearch(TreeNode root, int num)//
//    {
//        if(root != null)
//            root.left.traverseFrom(root.left);
//        depthFirstSearch(root.,  num+1);
//
//    }

}
