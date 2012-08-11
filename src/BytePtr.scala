import java.nio.ByteBuffer

class BytePtr(var size : Int = 1, var sizeIncrement : Int = 1) {

  private var buf = ByteBuffer.allocateDirect(size)
  var pos = 0

  private def resize = {
    size += sizeIncrement
    val b = ByteBuffer.allocateDirect(size)
    buf.rewind
    b.put(buf)
    b.position(pos)
    buf = b
  }

  /**
    * Returns the current value of the pointer.
    * Equivalant to *ptr
    */
  def * = {
    buf.get(pos)
  }

  def noop = {}

  /**
    * Increments the pointer.
    * Equivalent to ++ptr
    */
  def ++ {
    pos += 1
    if (2 * pos > size - 1)
      resize
    buf.position(pos)
  }

  /**
    * Decrements the pointer.
    * Equivalent to --ptr
    */
  def -- {
    pos -= 1
    if (pos < 0) pos = size
    buf.position(pos)
  }

  /**
    * Increment the value of the pointer.
    * Equivalent to ++*ptr
    */
  def *++ {
    buf.put(pos, (buf.get(pos) + 1).toByte)
  }

  /**
    * Decrement the value of the pointer.
    * Equivalent to --*ptr
    */
  def *-- {
    buf.put(pos, (buf.get(pos) - 1).toByte)
  }

  def putchar {
    print(buf.get(pos).toChar)
  }

  def getchar {
    buf.put(pos, System.in.read.toByte)
  }

}