/**
 * Created by liuwei on 1/28/16.
 */
object Floyd {
  def main(args: Array[String]) {
    val lines = io.Source.stdin.getLines()
    val Array(n, m) = lines.next().split(" ").map(_.toInt)

    val BIG = Int.MaxValue / 3

    val cache = Array.fill[Int](n, n)(BIG)
    for (i <- Range(0, n))
      cache(i)(i) = 0
    for (i <- Range(0, m)) {
      val Array(x, y, r) = lines.next().split(" ").map(_.toInt)
      cache(x - 1)(y - 1) = r
    }

    for (k <- Range(0, n); i <- Range(0, n); j <- Range(0, n)) {
      cache(i)(j) = math.min(cache(i)(j), cache(i)(k) + cache(k)(j))
    }

    val q = lines.next().toInt
    for (i <- Range(0, q)) {
      val Array(x, y) = lines.next().split(" ").map(_.toInt)
      val dist = cache(x - 1)(y - 1)
      println(if (dist < BIG) dist else -1)
    }

  }
}
