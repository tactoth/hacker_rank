/**
 * Created by liuwei on 1/28/16.
 */
object PowerE {

  def f(x: Float): Float = {
    // Compute and Return the value of e^x
    def facto(n: Int): Int = if (n == 1) 1 else n * facto(n - 1)
    def sq(v: Float) = v * v
    def pow(b: Float, n: Int): Float = n match {
      case 0 => 1
      case n if n % 2 == 0 => sq(pow(b, n / 2))
      case _ => pow(b, n - 1) * b
    }
    1 + Range.inclusive(1, 9).map(i => pow(x, i) / facto(i)).sum
  }


  def main(args: Array[String]) {
    for (input <- Seq(20f, 5f, 0.5f, -0.5f))
      println(input + " => " + f(input))
  }
}
