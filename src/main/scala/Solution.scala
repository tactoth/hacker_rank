/**
 * Created by liuwei on 1/27/16.
 */
object Solution {

  def main(args: Array[String]) {
    val lines = io.Source.stdin.getLines()

    val first = lines.next()
    val Array(rows, columns, rotations) = first.split("\\s+").map(_.toInt)

    val m = Array.ofDim[Int](rows, columns)

    for ((i, row) <- Range(0, rows) zip lines.toList;
         (j, v) <- Range(0, columns) zip row.split("\\s+").map(_.toInt)) {
      m(i)(j) = v
    }

    /*
    0 1 2 3 4
    0 1 2 3 4
    0 1 2 3 4
    0 1 2 3 4

    0 1 2 3
    0 1 2 3
    0 1 2 3
    0 1 2 3
    0 1 2 3
    0 1 2 3
     */

    case class Round(x1: Int, y1: Int, x2: Int, y2: Int) {
      val width = x2 - x1 + 1
      val height = y2 - y1 + 1
      val length = (width + height) * 2 - 4

      /* give vertex an id */
      def vertex(inId: Int): (Int, Int) = {
        var id: Int = inId % length

        if (id < height)
          return (x1, y1 + id)
        id -= (height - 1)
        if (id < width)
          return (x1 + id, y2)
        id -= (width - 1)
        if (id < height)
          return (x2, y2 - id)
        id -= (height - 1)

        if (!(id < width))
          throw new RuntimeException("Error")
        (x2 - id, y1)
      }
    }

    val roundCount = Math.min(rows, columns) / 2
    def mirror(x: Int, y: Int) = (columns - 1 - x, rows - 1 - y)

    val rounds = for ((x, y) <- Range(0, roundCount) zip Range(0, roundCount)) yield {
      val (x2, y2) = mirror(x, y)
      Round(x, y, x2, y2)
    }

    Console.err.println("roundCount=" + roundCount + ",rounds=" + rounds)

    val result = Array.ofDim[Int](rows, columns)
    for (round <- rounds) {
      val r = rotations % round.length
      Console.err.println("r=" + r)

      for (i <- Range(0, round.length)) {
        Console.err.println("i=" + i)

        val j = i + r
        val (xo, yo) = round.vertex(i)
        val (xn, yn) = round.vertex(j)

        Console.err.println(i + "->" + j + ", eqv: " +(xo, yo) + "=>" +(xn, yn))
        result(yn)(xn) = m(yo)(xo)
      }
    }

    // output the result
    for (row <- result)
      println(row.mkString(" "))
  }

}
