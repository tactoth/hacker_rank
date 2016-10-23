/**
  * Created by liuwei on 10/15/16.
  */
object TestUtils {
  def test[I, O](method: I => O, input: I, expect: O): Unit = {
    val output = method(input)
    if (output.equals(expect)) {
      println("Pass")
    } else {
      println(s"Fail, input=$input, output=$output, expect=$expect")
    }
  }

  var lastStart: Long = 0

  def markStart(): Unit = {
    lastStart = System.currentTimeMillis()
  }

  def reportDuration(label: String): Unit = {
    val durationMs = System.currentTimeMillis() - lastStart
    if (durationMs < 10000) {
      Console.err.println(s"$label => $durationMs ms")
    } else {
      Console.err.println(s"$label => ${durationMs / 1000} s")
    }
  }
}
