import scala.collection.mutable

/**
 * Created by liuwei on 1/28/16.
 */
object Solution {

  case class Block(left: Int, top: Int, size: Int) {
    def xrange = Range(left, left + size)

    def yrange = Range(top, top + size)

    def contains(point: (Int, Int)) = {
      val (x, y) = point
      x >= left && x < left + size && y >= top && y < top + size
    }

    /*
     * 03
     * 12
     */
    def sub(id: Int) = {
      val halfSize = size / 2
      id match {
        case 0 => Block(left, top, halfSize)
        case 1 => Block(left, top + halfSize, halfSize)
        case 2 => Block(left + halfSize, top + halfSize, halfSize)
        case 3 => Block(left + halfSize, top, halfSize)
      }
    }

    def corner(id: Int) = {
      id match {
        case 0 => (left, top)
        case 1 => (left, top + size - 1)
        case 2 => (left + size - 1, top + size - 1)
        case 3 => (left + size - 1, top)
      }
    }
  }

  def place(block: Block, ox: Int, oy: Int) {
    if (block.size == 1) {
      // nothing needs to be done
    } else {
      // place at the corners of the right square
      val tri = mutable.Buffer[(Int, Int)]()
      for (id <- Range(0, 4)) {
        val subBlock = block.sub(id)
        if (!subBlock.contains((ox, oy))) {
          val (cx, cy) = subBlock.corner((id + 2) % 4)

          // fill cx, cy
          place(subBlock, cx, cy)
          tri += ((cx, cy))
        } else {
          place(subBlock, ox, oy)
        }
      }
      println(tri.map(p => p._1 + " " + p._2).reduce(_ + " " + _))
    }
  }

  def main(args: Array[String]) {
    val stdLines = io.Source.stdin.getLines()
    val size = 1 << stdLines.next().toInt
    val Array(x, y) = stdLines.next().split(" ").map(_.toInt)

    place(Block(1, 1, size), x, y)
  }
}
