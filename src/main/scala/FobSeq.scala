import scala.collection.mutable

/**
 * Created by liuwei on 1/28/16.
 */
object FobSeq {

  def sq(n: BigInt) = n * n

  case class FibMod(first: BigInt, second: BigInt) {
    val cache = mutable.Map[Int, BigInt]()

    def get(i: Int): BigInt = {
      i match {
        case 1 => first
        case 2 => second
        case _ => cache.getOrElseUpdate(i, sq(get(i - 1)) + get(i - 2))
      }
    }
  }

  def main(args: Array[String]) {
    val Array(a, b, n) = io.Source.stdin.getLines().next().split(" ").map(_.toInt)
    println(FibMod(a, b).get(n))
  }
}
