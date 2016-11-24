/**
  * 3SUM
  * Created by liuwei on 11/23/16.
  */
object Solution {
  def main(args: Array[String]) {
    def test(a: Array[Int]): Unit = {
      println("Input: " + java.util.Arrays.toString(a))
      getSumZero(a)
    }

    test(Array(-1, 0, 1))
    test(Array(0, 1))
    test(Array(0, 0, 0))
    test(Array(-1, -2, 1, 2, 3, -3, 0, 0, 0))
  }

  // 1 2 3 4 5 6 7 8 9
  def getSumZero(a: Array[Int]) = {
    java.util.Arrays.sort(a)

    for (i <- 0 to a.length - 3) {
      var s = i + 1
      var e = a.length - 1
      while (s < e) {
        val sum = a(i) + a(s) + a(e)
        if (sum == 0) {
          println((a(i), a(s), a(e)))
          e -= 1
        } else if (sum > 0) {
          e -= 1
        } else /* sum < 0 */ {
          s += 1
        }
      }
    }
  }
}
