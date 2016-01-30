

object Solution {
  def fit(a: Seq[Int], value: Int, index: Int) = (index == 0 || a(index - 1) < value) && (index == a.length - 1 || a(index + 1) > value)

  case class Swapped(a: Seq[Int], i: Int, j: Int) extends Seq[Int] {
    override def apply(index: Int) = index match {
      case `i` => a(j)
      case `j` => a(i)
      case _ => a(index)
    }

    override def length = a.length

    override def iterator: Iterator[Int] = throw new UnsupportedOperationException()
  }

  case class Reversed(a: Seq[Int], from: Int, toExclusive: Int) extends Seq[Int] {
    override def apply(index: Int) = index match {
      // 0|12[3]4 -> 0|4[3]21
      case i if i >= from && i < toExclusive => a((toExclusive - from) - 1 - (i - from) + from)
      case _ => a(index)
    }

    override def length = a.length

    override def iterator: Iterator[Int] = throw new UnsupportedOperationException()
  }


  /* 3x2x1  2x1 4x23x1 04x23x15 013x24 */
  def fail(a: Seq[Int])(i: Int) = a(i) > a(i + 1)

  def getFails(a: Seq[Int]) = Range(0, a.length - 1).filter(fail(a)).toSeq

  def canSortBySingleSwap(a: Seq[Int]) = {
    val fails = getFails(a)
    Console.err.println(fails)

    def goodSwap(i: Int, j: Int) = {
      val swapped = Swapped(a, i, j)
      fit(swapped, swapped(i), i) && fit(swapped, swapped(j), j)
    }

    fails match {
      case Seq(i) if goodSwap(i, i + 1) => Some(i, i + 1)
      case Seq(i, j) if goodSwap(i, j + 1) => Some(i, j + 1)
      case _ => None
    }
  }

  def canSortByReverse(a: Seq[Int]): Option[(Int, Int)] = {
    val fails = getFails(a)

    // fails need to be connected
    val adj = fails.zip(fails.tail).forall { case (i, j) => i + 1 == j }
    if (!adj)
      return None

    val from = fails.head
    val toInclusive = fails.last + 1
    val toExclusive = toInclusive + 1
    val reversed = Reversed(a, from, toExclusive)

    if (fit(reversed, reversed(from), from) && fit(reversed, reversed(toInclusive), toInclusive))
      Some(from, toInclusive)
    else
      None
  }

  def main(args: Array[String]): Unit = {
    //    val reversed = Reversed("01234".toSeq.map(_.toString.toInt), 1, 5)
    //    for (i <- Range(0, reversed.length)) println(reversed(i))

    //    for ((input, expected) <- Seq[(Seq[Int], Option[(Int, Int)])](
    //      Seq(3, 2, 1) -> Some(0, 2),
    //      Seq(2, 1) -> Some(0, 1),
    //      Seq(4, 2, 3, 1) -> Some(0, 3),
    //      Seq(0, 4, 2, 3, 1, 5) -> Some(1, 4),
    //      Seq(0, 1, 3, 2, 4) -> Some(2, 3)
    //    )
    //    ) {
    //      println(canSortBySingleSwap(input) == expected)
    //    }
    //
    //    for ((input, expected) <- Seq[(Seq[Int], Option[(Int, Int)])](
    //      Seq(3, 2, 1) -> Some(0, 2),
    //      Seq(2, 1) -> Some(0, 1),
    //      Seq(0, 1, 2, 3, 4, 8, 7, 6, 5) -> Some(5, 8),
    //      Seq(4, 3, 2, 1, 0, 5, 6, 7, 8) -> Some(0, 4)
    //    )
    //    ) {
    //      println(canSortByReverse(input) == expected)
    //    }

    val lines = io.Source.stdin.getLines()
    lines.next()
    val a = lines.next().split(" ").map(_.toInt).toSeq

    if (getFails(a).isEmpty) {
      println("yes")
      return
    }

    def format(range: (Int, Int)) = {
      range match {
        case (i, j) => (i + 1) + " " + (j + 1)
      }
    }

    val swap = canSortBySingleSwap(a)
    if (swap.isDefined) {
      println("yes")
      println("swap " + format(swap.get))
      return
    }

    val reverse = canSortByReverse(a)
    if (reverse.isDefined) {
      println("yes")
      println("reverse " + format(reverse.get))
      return
    }

    println("no")
  }
}
