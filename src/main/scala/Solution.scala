import scala.io.StdIn

/**
  * Created by liuwei on 10/19/16.
  */
object Solution {
  // TODO: mod
  def main(args: Array[String]) {
    val n = StdIn.readLine()
    val input = StdIn.readLine().split("\\s+").map(_.toLong)
    println(new SP(input).get())
  }


  def getOrElseUpdate[T](a: Array[T], index: Int, emptyValue: T)(compute: => T) = {
    var value = a(index)
    if (value == emptyValue) {
      value = compute
      a(index) = value
    }
    value
  }

  def getOrElseUpdate[T](a: Array[Array[T]], i: Int, j: Int, emptyValue: T)(compute: => T) = {
    var value = a(i)(j)
    if (value == emptyValue) {
      value = compute
      a(i)(j) = value
    }
    value
  }

  val MOD = 1e9.asInstanceOf[Long] + 7

  private def _multi(a: Long, b: Long) = ((a % MOD) * (b % MOD)) % MOD // https://en.wikipedia.org/wiki/Modular_exponentiation

  private def _add(a: Long, b: Long) = (a + b) % MOD

  private def _addSeq(a: Long*) = a.foldLeft(0L)(_add(_, _))

  private def _multiSeq(a: Long*) = a.foldLeft(1L)(_multi(_, _))

  class SP(a: Array[Long]) {
    private val length = a.length
    private val d_ = Array.fill[Long](length + 1)(-1)
    private val sum_ = Array.fill[Long](length + 1)(-1)
    private val f_ = Array.fill[Long](length + 1, length + 1)(-1)

    // result are already mod
    def d(l: Int): Long = getOrElseUpdate(d_, l, -1L) {
      if (l == 0) 1 else if (l == 1) 1 else _add(_addSeq(Range(1, l).map(d(_)): _*), 1)
    }

    def sum(end: Int): Long = getOrElseUpdate(sum_, end, -1L) {
      if (end == 0) 0 else sum(end - 1) + a(end - 1)
    }

    def sum(s: Int, e: Int): Long = sum(e) - sum(s)

    def f(s: Int, l: Int): Long = getOrElseUpdate(f_, s, l, -1L) {
      (for (firstLength <- Range.inclusive(1, l)) yield {
        _addSeq(_multiSeq(sum(s, s + firstLength), firstLength, d(l - firstLength)), f(s + firstLength, l - firstLength))
      }).foldLeft(0L)(_add(_, _))
    }

    def get() = f(0, a.length)
  }

}
