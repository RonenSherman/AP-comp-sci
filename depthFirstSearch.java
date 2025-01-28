import javax.swing.tree.TreeNode;

public class depthFirstSearch { // maze solving with depth first search

    public int num;
    public depthFirstSearch left;
    public depthFirstSearch right;


    public depthFirstSearch(int num, depthFirstSearch left, depthFirstSearch right) {
        this.num = num;
        this.left = left;
        this.right = right;
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


  public void  depthFirstSearch(TreeNode root, int num,depthFirstSearch left, depthFirstSearch right)
  {
      if (root == null)
          return;

      // Traverse left
    //  depthFirstSearch(root.left);
      // Traverse root
      System.out.print(root.getParent() + "->");
      // Traverse right
    //  depthFirstSearch(root.right);
   }

}
