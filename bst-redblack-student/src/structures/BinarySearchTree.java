package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinarySearchTree<T extends Comparable<T>> implements BstInterface<T> {
  protected BstNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BstNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  /**
   * contains method.
   * 
   * @param t : target element
   */
  public boolean contains(T t) {
    // TODO
    if (t == null) {
      throw new NullPointerException();
    }
    return (get(t) != null);
  }

  /**
   * remove target t.
   * 
   * @param t : target element
   */
  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BstNode<T> removeFromSubtree(BstNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      if (node.getLeft() != null) {
        node.getLeft().setParent(node);
      }
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      if (node.getRight() != null) {
        node.getRight().setParent(node);
      }
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        if (node.getLeft() != null) {
          node.getLeft().setParent(node);
        }
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BstNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BstNode<T> removeRightmost(BstNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      if (node.getRight() != null) {
        node.getRight().setParent(node);
      }
      return node;
    }
  }

  /**
   * get node contains target t.
   * 
   * @param t : target element
   */
  public T get(T t) {
    // TODO
    if (t == null) {
      throw new NullPointerException();
    }
    return get(t, root);
  }

  private T get(T t, BstNode<T> current) {
    if (current == null) {
      return null;
    }
    if (current.getData().equals(t)) {
      return current.getData();
    } else {
      if (current.getData().compareTo(t) > 0) {
        return get(t, current.getLeft());
      } else {
        return get(t, current.getRight());
      }
    }
  }

  /**
   * add t into the tree.
   * 
   * @param t : new element
   */
  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BstNode<T>(t, null, null));
  }

  protected BstNode<T> addToSubtree(BstNode<T> node, BstNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
      if (node.getLeft() != null) {
        node.getLeft().setParent(node);
      }
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
      if (node.getRight() != null) {
        node.getRight().setParent(node);
      }
    }
    return node;
  }

  @Override
  public T getMinimum() {
    // TODO
    if (root == null) {
      return null;
    }
    BstNode<T> current = root;
    while (current.getLeft() != null) {
      current = current.getLeft();
    }
    return current.getData();
  }

  @Override
  public T getMaximum() {
    if (root == null) {
      return null;
    }
    BstNode<T> current = root;
    while (current.getRight() != null) {
      current = current.getRight();
    }
    return current.getData();
  }

  @Override
  public int height() {
    // TODO
    if (root == null) {
      return -1;
    }
    return height(root);

  }

  private int height(BstNode<T> current) {
    int leftHeight = 0;
    int rightHeight = 0;

    if (current.getLeft() != null) {
      leftHeight = 1 + height(current.getLeft());
    }
    if (current.getRight() != null) {
      rightHeight = 1 + height(current.getRight());
    }

    return Math.max(leftHeight, rightHeight);
  }

  /**
   * pre-order traversal iterator.
   * 
   */
  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BstNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      inorderTraverse(queue, node.getLeft());
      inorderTraverse(queue, node.getRight());
    }
  }

  /**
   * in-order traversal iterator.
   * 
   */
  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }

  private void inorderTraverse(Queue<T> queue, BstNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }
  
  private void inorderTraverse(ArrayList<T> list, BstNode<T> node) {
    if (node != null) {
      inorderTraverse(list, node.getLeft());
      list.add(node.getData());
      inorderTraverse(list, node.getRight());
    }
  }

  /**
   * post-order traversal iterator.
   * 
   */
  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BstNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      inorderTraverse(queue, node.getRight());
      queue.add(node.getData());

    }
  }

  @Override
  public boolean equals(BstInterface<T> other) {

    if (size() != other.size()) { 
      return false;
    }
    Iterator<T> iter = postorderIterator();
    Iterator<T> iter2 = other.postorderIterator();

    while (iter.hasNext()) {
      T var1 = iter.next();
      T var2 = iter2.next();

      if (!var1.equals(var2)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean sameValues(BstInterface<T> other) {
    Iterator<T> iter = postorderIterator();

    while (iter.hasNext()) {
      T var = iter.next();
      if (!other.contains(var)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isBalanced() {
    double bound1 = Math.pow(2, height());
    double bound2 = Math.pow(2, height() + 1);
    return (bound1 <= size() && size() < bound2);
  }

  @Override
  @SuppressWarnings("unchecked")

  public void balance() {
    ArrayList<T> list = new ArrayList<T>();
    inorderTraverse(list, root);
    root = sortedArray2BST(0, list.size() - 1, list, false);
  }

  BstNode<T> sortedArray2BST(int lower, int upper, ArrayList<T> list, boolean firstNode) {
    if (lower > upper) {
      return null;
    }
    int mid = (lower + upper) / 2;
    BstNode<T> node = new BstNode<T>(list.get(mid), null, null);
    if (!firstNode) {
      root = node;
    }
    node.setLeft(sortedArray2BST(lower, mid - 1, list, true));
    node.setRight(sortedArray2BST(mid + 1, upper, list, true));
    return node;
  }

  

  @Override
  public BstNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  /**
   * helper method for formatting.
   * 
   * @param root : root of tree.
   */
  public static <T extends Comparable<T>> String toDotFormat(BstNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BstNode<T>> queue = new LinkedList<BstNode<T>>();
    queue.add(root);
    BstNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot += cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }

    }
    dot += "};";
    return dot;
  }

  /**
   * supporting main function.
   * 
   */
  public static void main(String[] args) {
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      BstInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BstInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}