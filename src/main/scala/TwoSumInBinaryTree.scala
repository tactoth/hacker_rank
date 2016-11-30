import scala.collection.mutable

/**
  * Created by liuwei on 11/23/16.
  */
object TwoSumInBinaryTree {

  case class Node(v: Int, left: Node = null, right: Node = null) {}


  def tranverse(root: Node, visit: Node => Unit): Unit = {
    val stack = mutable.Stack[Node](root)

    while (stack.nonEmpty) {
      val current = stack.top
      if (current.left != null) {
        stack.push(current.left)
      } else {
        // no node at left, so this is so far leftmost
        visit(current)

        // remove this node from the stack because this subtree has all been visited
        stack.pop()

        if (current.right != null) {
          stack.push(current.right)
        }
      }
    }
  }

  case class TreeTranverser(root: Node) {

  }

  def main(args: Array[String]) {
    val tree = Node(3, Node(2, Node(1)), Node(4, null, Node(5)))
    tranverse(tree, n => println(n.v))
  }
}
