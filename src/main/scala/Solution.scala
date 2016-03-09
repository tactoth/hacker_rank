
/**
 * Created by liuwei on 3/8/16.
 */
object Solution {

  val DEBUG = true

  def err(o: Object): Unit = {
    if (DEBUG) Console.err.println(o)
  }

  def bitsetAdd(bitset: Int, value: Int) = bitset | (0x1 << value)

  def bitsetContains(bitset: Int, value: Int) = (bitset & (0x1 << value)) != 0

  def bitsetRemove(bitset: Int, value: Int) = bitset & ~(0x1 << value)

  def bitsetRemoveAll(bitset: Int, bitsetToRemove: Int) = bitset & ~(bitsetToRemove)

  def bitsetAll(max: Int) = (0x1 << max) - 1

  def bitsetSize(bitset: Int) = Integer.bitCount(bitset)

  def bitsetMinValue(bitset: Int) = Integer.numberOfTrailingZeros(bitset)

  def getSubId(x: Int, y: Int) = (y / 3) * 3 + (x / 3)

  def add(sets: Array[Int], index: Int, v: Int): Unit = {
    val updated = bitsetAdd(sets(index), v)
    sets(index) = updated
  }

  abstract class WithCells(m: Array[Array[Int]]) {
    def onCell(x: Int, y: Int, subId: Int, valueSet: Int): Int

    def run(): Int = {
      var result = 0
      for (y <- Range(0, 9); x <- Range(0, 9)) {
        val bitsetValue = m(y)(x)
        val subId = getSubId(x, y)
        result += onCell(x, y, subId, bitsetValue)
      }
      result
    }
  }


  def dumpSet(bitset: Int): String = {
    Range(0, 9).filter(i => bitsetContains(bitset, i)).mkString("")
  }

  def dumpMat(m: Array[Array[Int]]): String = {
    m map {
      row =>
        row map {
          bitset =>
            "[" + dumpSet(bitset) + "]"
        } mkString ", "
    } mkString "\n"
  }

  class DuplicateCheckException(val message: String) extends Exception(message) {}

  def optimize(m: Array[Array[Int]]): Unit = {
    err("start optimizing...")
    val rowFills = Array.ofDim[Int](9)
    val colFills = Array.ofDim[Int](9)
    val subFills = Array.ofDim[Int](9)

    /** returns (undetermined, optionsRemoved) */
    def round(): (Int, Int) = {
      val undetermined = new WithCells(m) {
        override def onCell(x: Int, y: Int, subId: Int, valueSet: Int): Int = {
          if (bitsetSize(valueSet) == 1) {
            val v = bitsetMinValue(valueSet)


            add(rowFills, y, v)
            add(colFills, x, v)
            add(subFills, subId, v)
            0
          }
          else
            1
        }
      }.run()

      val optionsRemoved = new WithCells(m) {
        override def onCell(x: Int, y: Int, subId: Int, valueSet: Int): Int = {
          val size = bitsetSize(valueSet)
          if (size > 1) {
            m(y)(x) = bitsetRemoveAll(m(y)(x), rowFills(y))
            m(y)(x) = bitsetRemoveAll(m(y)(x), colFills(x))
            m(y)(x) = bitsetRemoveAll(m(y)(x), subFills(subId))
            size - bitsetSize(m(y)(x))
          } else
            0
        }
      }.run()

      (undetermined, optionsRemoved)
    }


    var undeterminedAndOptionsRemoved: (Int, Int) = null
    do {
      err(dumpMat(m))
      err("")
      undeterminedAndOptionsRemoved = round()
      err(undeterminedAndOptionsRemoved)
    } while (undeterminedAndOptionsRemoved._1 != 0 && undeterminedAndOptionsRemoved._2 != 0)
    err(dumpMat(m))
  }

  def first(m: Array[Array[Int]])(pred: (Int, Int) => Boolean): Option[(Int, Int)] = {
    (for (x <- Range(0, 9); y <- Range(0, 9)) yield (x, y)).find {
      case (x, y) => pred(x, y)
    }
  }


  def firstUndetermined(m: Array[Array[Int]]): Option[(Int, Int)] = first(m) {
    (x, y) =>
      bitsetSize(m(y)(x)) > 1
  }

  def firstEmpty(m: Array[Array[Int]]): Option[(Int, Int)] = first(m) {
    (x, y) =>
      bitsetSize(m(y)(x)) == 0
  }

  def asResult(m: Array[Array[Int]]) = m map {
    row =>
      row map { bitset => bitsetMinValue(bitset) + 1 } mkString ""
  } mkString "\n"

  def findSolutionFrom(m: Array[Array[Int]]): Boolean = {
    err("find solution from: ")
    err(dumpMat(m))

    optimize(m)

    // check for duplication
    val rowFills = Array.ofDim[Int](9)
    val colFills = Array.ofDim[Int](9)
    val subFills = Array.ofDim[Int](9)
    try {
      new WithCells(m) {
        override def onCell(x: Int, y: Int, subId: Int, valueSet: Int): Int = {
          if (bitsetSize(valueSet) == 1) {
            val v = bitsetMinValue(valueSet)

            def addWithCheck(label: String, sets: Array[Int], index: Int, v: Int): Unit = {
              val old = sets(index)
              add(sets, index, v)

              err(s"label=$label, index=$index, x=$x, y=$y, nextValue=$v, set=${dumpSet(old)}")
              if (sets(index) == old)
                throw new DuplicateCheckException(s"label=$label, index=$index, x=$x, y=$y, nextValue=$v, set=${dumpSet(old)}")
            }

            addWithCheck("row", rowFills, y, v)
            addWithCheck("col", colFills, x, v)
            addWithCheck("sub", subFills, subId, v)
          }
          0
        }
      }.run()
    } catch {
      case e: DuplicateCheckException =>
        if (DEBUG)
          e.printStackTrace(Console.err)
        return false
    }

    // if some cells doesn't have a value
    val optEmpty = firstEmpty(m)
    if (optEmpty.isDefined)
      return false

    val optUndetermined = firstUndetermined(m)
    optUndetermined match {
      case None =>
        println(asResult(m))
        true
      case Some((x, y)) =>
        var bitsetValue = m(y)(x)
        var solutionFound = false
        while (!solutionFound && bitsetValue != 0) {
          // pop one bit
          val v = bitsetMinValue(bitsetValue)
          bitsetValue = bitsetRemove(bitsetValue, v)

          err(s"trying $v at ($x, $y)...")

          // deep clone!
          val subM = m.map(_.clone())
          subM(y)(x) = bitsetAdd(0, v)

          solutionFound = solutionFound || findSolutionFrom(subM)
        }
        solutionFound
    }
  }


  def main(args: Array[String]) {
    val lines = io.Source.stdin.getLines()

    // stores all valid values
    val m = Array.ofDim[Int](9, 9)
    for ((y, line) <- Range(0, 9) zip lines.toIterable; x <- Range(0, 9)) {
      val v = line.charAt(x).toString.toInt
      m(y)(x) = if (v == 0) bitsetAll(9) else bitsetAdd(0, v - 1)
    }


    findSolutionFrom(m)
  }

}
