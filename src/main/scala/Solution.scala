object Solution {
  def main(args: Array[String]) = {
    val n = Console.readInt
    var x_y = Console.readLine.split(" ")
    val r = x_y(0).toInt
    val c = x_y(1).toInt
    val grid = new Array[String](n)
    for (i <- 0 until n) {
      grid.update(i, Console.readLine)
    }
    nextMove(n, r, c, grid)
  }


  /* Refer to Output format section for more details */
  def nextMove(player: Int, r: Int, c: Int, grid: Array[String]) = {
    var botOpt: Option[(Int, Int)] = None
    var princeOpt: Option[(Int, Int)] = None
    for (y <- 0 until grid.length; x <- 0 until grid(y).length) {
      val c = grid(y)(x)
      if (c == 'm')
        botOpt = Some(x, y)
      else if (c == 'p')
        princeOpt = Some(x, y)
    }

    val Some((bx, by)) = botOpt
    val Some((px, py)) = princeOpt

    val dx = px - bx
    val dy = py - by

    if (dy != 0) {
      println(if (dy > 0) "DOWN" else "UP")
    } else if (dx != 0) {
      println(if (dx > 0) "RIGHT" else "LEFT")
    }


  }
}

