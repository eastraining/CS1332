import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

  /**
   * DO NOT ADD ANY GLOBAL VARIABLES!
   */

  /**
   * Given the root of a binary search tree, generate a
   * pre-order traversal of the tree. The original tree
   * should not be modified in any way.
   *
   * This must be done recursively.
   *
   * Must be O(n).
   *
   * @param <T> Generic type.
   * @param root The root of a BST.
   * @return List containing the pre-order traversal of the tree.
   */
  public List<T> preorder(TreeNode<T> root) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    List<T> traversedList = new LinkedList<>();
    recurPreOrder(root, traversedList);
    return traversedList;
  }

  /**
   * Given the root of a binary search tree, generate an
   * in-order traversal of the tree. The original tree
   * should not be modified in any way.
   *
   * This must be done recursively.
   *
   * Must be O(n).
   *
   * @param <T> Generic type.
   * @param root The root of a BST.
   * @return List containing the in-order traversal of the tree.
   */
  public List<T> inorder(TreeNode<T> root) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    List<T> traversedList = new LinkedList<>();
    recurInOrder(root, traversedList);
    return traversedList;
  }

  /**
   * Given the root of a binary search tree, generate a
   * post-order traversal of the tree. The original tree
   * should not be modified in any way.
   *
   * This must be done recursively.
   *
   * Must be O(n).
   *
   * @param <T> Generic type.
   * @param root The root of a BST.
   * @return List containing the post-order traversal of the tree.
   */
  public List<T> postorder(TreeNode<T> root) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    List<T> traversedList = new LinkedList<>();
    recurPostOrder(root, traversedList);
    return traversedList;
  }

  private void recurPreOrder(TreeNode<T> node, List<T> traversedList) {
    if (node != null) {
      traversedList.add(node.getData());
      recurPreOrder(node.getLeft(), traversedList);
      recurPreOrder(node.getRight(), traversedList);
    }
  }

  private void recurInOrder(TreeNode<T> node, List<T> traversedList) {
    if (node != null) {
      recurInOrder(node.getLeft(), traversedList);
      traversedList.add(node.getData());
      recurInOrder(node.getRight(), traversedList);
    }
  }

  private void recurPostOrder(TreeNode<T> node, List<T> traversedList) {
    if (node != null) {
      recurPostOrder(node.getLeft(), traversedList);
      recurPostOrder(node.getRight(), traversedList);
      traversedList.add(node.getData());
    }
  }

  // Testing
  public static void main(String[] args) {
    int[] testNums = { 50, 25, 100, 10, 75, 125, 110 };
    TreeNode<Integer> testRoot = new TreeNode<>(50);
    testRoot.setLeft(new TreeNode<Integer>(25));
    testRoot.setRight(new TreeNode<Integer>(100));
    testRoot.getLeft().setLeft(new TreeNode<Integer>(10));
    testRoot.getRight().setLeft(new TreeNode<Integer>(75));
    testRoot.getRight().setRight(new TreeNode<Integer>(125));
    testRoot.getRight().getRight().setLeft(new TreeNode<Integer>(110));

    Traversals<Integer> testTraversal = new Traversals<>();
    List<Integer> results = testTraversal.preorder(testRoot);
    System.out.print("Preorder traversal:");
    for (Integer result : results) {
      System.out.print(" " + result);
    }
    System.out.println();

    results = testTraversal.inorder(testRoot);
    System.out.print("Inorder traversal:");
    for (Integer result : results) {
      System.out.print(" " + result);
    }
    System.out.println();

    results = testTraversal.postorder(testRoot);
    System.out.print("Postorder traversal:");
    for (Integer result : results) {
      System.out.print(" " + result);
    }
    System.out.println();
  }
}
