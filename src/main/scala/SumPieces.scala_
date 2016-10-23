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


  val MOD = 1e9.asInstanceOf[Long] + 7

  private def _multi(a: Long, b: Long) = ((a % MOD) * (b % MOD)) % MOD // https://en.wikipedia.org/wiki/Modular_exponentiation

  private def _add(a: Long, b: Long) = (a + b) % MOD

  private def _addSeq(a: Long*) = {
    var res = 0L
    for (e <- a) {
      res = _add(res, e)
    }
    res
  }

  private def _multiSeq(a: Long*) = {
    var res = 1L
    for (e <- a) {
      res = _multi(res, e)
    }
    res
  }

  class Power(base: Long, maxExp: Int) {
    private val cache = Array.fill[Long](maxExp + 1)(-1)

    def get(exp: Int): Long = {
      if (cache(exp) != -1L)
        cache(exp)
      else {
        val result = if (exp == 0)
          1
        else {
          val half = exp / 2
          _multiSeq(get(half), get(half), if (exp % 2 == 0) 1 else base)
        }
        cache(exp) = result
        result
      }
    }
  }

  class SP(a: Array[Long]) {
    private val length = a.length
    private val power2_ = new Power(2, length)
    private val sum_ = Array.fill[Long](length + 1)(-1)
    private val f_ = Array.fill[Long](length + 1, length + 1)(-1)

    // result are already mod
    def d(l: Int): Long = {
      if (l == 0 || l == 1)
        1
      else
        power2_.get(l - 1)
    }

    def sum(end: Int): Long = if (sum_(end) != -1) sum_(end)
    else {
      val result = if (end == 0) 0 else sum(end - 1) + a(end - 1)
      sum_(end) = result
      result
    }

    // pre compute d and sum
    for (i <- Range.inclusive(0, length)) {
      d(i)
      sum(i)
    }

    def sum(s: Int, e: Int): Long = sum(e) - sum(s)

    def f(s: Int, l: Int): Long = if (f_(s)(l) != -1) f_(s)(l)
    else {
      var all = 0L
      for (firstLength <- Range.inclusive(1, l)) yield {
        val sub = _addSeq(_multiSeq(sum(s, s + firstLength), firstLength, d(l - firstLength)), f(s + firstLength, l - firstLength))
        all = _add(sub, all)
      }
      f_(s)(l) = all
      all
    }

    def get() = {
      // do this first to avoid recursive call
      for (s <- Range.inclusive(length - 1, 0, -1)) {
        f(s, length - s)
      }

      // eventually, we want this
      val result = f(0, length)
      result
    }
  }

}
