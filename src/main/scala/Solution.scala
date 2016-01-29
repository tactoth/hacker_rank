import scala.collection.mutable

/**
 * Created by liuwei on 1/28/16.
 */
object Solution {
  def main(args: Array[String]) {
    val lines = io.Source.stdin.getLines()
    val n = lines.next().toInt
    val a = (for (i <- Range(0, n)) yield lines.next().toInt).toSeq

    val cache = mutable.Map[Int, Int]()

    /**
     * Length of longest increasing sequence ending at <code>end</code> and with <code>end</code> included
     * @param end
     * @return
     */
    def lisEndAt(end: Int): Int = {
      cache.getOrElseUpdate(
      end, {
        if (end == 0) 1
        else
          (for (subEnd <- Range.inclusive(0, end - 1)) yield {
            val subLis = lisEndAt(subEnd)
            if (a(subEnd) < a(end))
              subLis + 1
            else
              subLis
          }).max
      })
    }

    println(Range.inclusive(0, n - 1).map(end => lisEndAt(end)).max)
  }

}
