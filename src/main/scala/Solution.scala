import scala.io.StdIn

/**
  * Created by liuwei on 10/19/16.
  */
object Solution {
  def main(args: Array[String]) {


    //    val n = StdIn.readLine()
    //    val input = StdIn.readLine().split("\\s+").map(_.toInt)
    //    println(new SP(input).get())

    val in = Array(4, 2, 9, 10, 1)

    val sp = new SP(in)
    for (i <- Range.inclusive(0, in.length)) {
      Console.err.println(sp.get(i))
    }
  }


  def getOrElseUpdate[T](a: Array[T], index: Int, emptyValue: T)(compute: => T) = {
    var value = a(index)
    if (value == emptyValue) {
      value = compute
      a(index) = value
    }
    value
  }

  val MOD = 1e9.asInstanceOf[Int] + 7

  private def _add(a: Int, b: Int) = (a + b) % MOD

  private def _addSeq(a: Int*) = a.foldLeft(0)(_add(_, _))

  class SP(a: Array[Int]) {

    val length = a.length
    private val sum_ = Array.fill[Int](length + 1)(-1)

    def sum(end: Int): Int = getOrElseUpdate(sum_, end, -1) {
      if (end == 0) 0 else a(end - 1) + sum(end - 1)
    }

    def sum(start: Int, end: Int): Int = sum(end) - sum(start)

    private val combines = Array.fill[Int](length + 1)(-1)

    private def getCombines(end: Int): Int = getOrElseUpdate(combines, end, -1) {
      if (end == 0)
        return 0
      if (end == 1)
        return 1
      getCombines(end - 1) * 2
    }

    private val result_ = Array.fill[Int](length + 1)(-1)

    def get(end: Int): Int = {
      getOrElseUpdate(result_, end, -1) {
        if (end == 0)
          return 0
        if (end == 1)
          return a(0)

        val last = end - 1
        val lastElement = a(last)

        Console.err.println(s"=== Last: $last => $lastElement")
        var result: Int = 0
        for (pieceBeforeLastStart <- Range.inclusive(0, last - 1)) {
          val pieceBeforeLastLength = last - pieceBeforeLastStart

          val pieceBeforeLastSum = sum(pieceBeforeLastStart, last)
          val lastContribution = lastElement * (pieceBeforeLastLength + 1)

          Console.err.println(s"Join: [$pieceBeforeLastStart, $last) => " +
            s" $pieceBeforeLastSum/pieceSum + $lastElement x ${pieceBeforeLastLength + 1}")

          result = _addSeq(
            result,
            lastContribution,
            pieceBeforeLastSum
          )
        }

        Console.err.println(s"Non-Join: $lastElement x ${getCombines(last)} + get($last)/${get(last)}")
        _addSeq(result, lastElement * getCombines(last), get(last), get(last))
      }
    }

    def get(): Int = get(a.length)
  }

}
