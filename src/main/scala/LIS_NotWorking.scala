import scala.collection.mutable

/**
 * Created by liuwei on 1/28/16.
 */
object LIS_NotWorking {
  def main(args: Array[String]) {
    val lines = io.Source.stdin.getLines()
    val n = lines.next().toInt
    val a = (for (i <- Range(0, n)) yield lines.next().toInt).toSeq

    val cache = mutable.Map[Int, Int]()

    def lis(end: Int): Int = {
      cache.getOrElseUpdate(
      end, {
        if (end == 0) 1
        else
          (for (subEnd <- Range.inclusive(0, end - 1)) yield {
            val subLis = lis(subEnd)
            if (a(subEnd) < a(end))
              subLis + 1
            else
              subLis
          }).max
      })
    }

    println(lis(n - 1))

  }

}
