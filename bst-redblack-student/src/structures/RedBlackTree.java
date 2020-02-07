package structures;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  /**
   * printTree.
   */
  public void printTree() {
    System.out.println("------------------------");
    if (root != null) {
      root.printSubtree(0);
    }
  }

  private boolean isRed(BstNode<T> node) {
    if (node == null) {
      return false;
    }
    return node.getColor() == BstNode.RED;
  }

  private boolean isBlack(BstNode<T> node) {
    if (node == null) {
      return true;
    }
    return node.getColor() == BstNode.BLACK;
  }

  private void rotateLeft(BstNode<T> node) {
    BstNode<T> r = node.getRight();
    node.setRight(r.getLeft());

    if (r.getLeft() != null) {
      r.getLeft().setParent(node);
    }
    r.setParent(node.getParent());
    if (node.getParent() == null) {
      this.root = r;
    } else {
      if (node.getParent().getLeft() == node) {
        node.getParent().setLeft(r);
      } else {
        node.getParent().setRight(r);
      }
    }

    r.setLeft(node);
    node.setParent(r);
  }

  private void rotateRight(BstNode<T> node) {
    BstNode<T> l = node.getLeft();
    node.setLeft(l.getRight());

    if (l.getRight() != null) {
      l.getRight().setParent(node);
    }
    l.setParent(node.getParent());
    if (node.getParent() == null) {
      this.root = l;
    } else {
      if (node == node.getParent().getRight()) {
        node.getParent().setRight(l);
      } else {
        node.getParent().setLeft(l);
      }
    }

    l.setRight(node);
    node.setParent(l);
  }

  @Override
  public void add(T t) {

    BstNode<T> newNode = new BstNode<T>(t, null, null, BstNode.RED);
    root = super.addToSubtree(root, newNode);
    if (root == newNode) {
      newNode.setColor(BstNode.BLACK);
      return;
    }
    if (isRed(newNode.getParent())) {
      recursion(newNode);
    }
  }

  private void recursion(BstNode<T> node) {
    if (isRed(getUncle(node))) {
      node.getParent().setColor(BstNode.BLACK);
      getUncle(node).setColor(BstNode.BLACK);
      getGrandparent(node).setColor(BstNode.RED);
      recursion(getGrandparent(node));
    }
    if (isBlack(getUncle(node))) {
      if (isOutside(node)) {
        node.getParent().setColor(BstNode.BLACK);
        getGrandparent(node).setColor(BstNode.RED);
        if (getGrandparent(node).getParent().getLeft() == getGrandparent(node)) {
          rotateRight(getGrandparent(node));
        } else {
          rotateLeft(getGrandparent(node));
        }
      }
      if (isInside(node)) {
        if (node.getParent().getLeft() == node) {
          rotateRight(node.getParent());
        } else {
          rotateLeft(node.getParent());
        }
      }

    }

  }

  private boolean isInside(BstNode<T> node) {
    return (getGrandparent(node).getRight().getLeft() == node || getGrandparent(node).getLeft().getRight() == node);
  }
  
  private boolean isOutside(BstNode<T> node) {
    return (getGrandparent(node).getRight().getRight() == node || getGrandparent(node).getLeft().getLeft() == node);
  }

  private BstNode<T> getUncle(BstNode<T> node) {

    if (getGrandparent(node).getLeft() == node.getParent()) {
      return getGrandparent(node).getRight();
    } else {
      return getGrandparent(node).getLeft();
    }
  }

  private BstNode<T> getGrandparent(BstNode<T> node) {
    return node.getParent().getParent();
  }
}
