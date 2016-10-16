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
}
