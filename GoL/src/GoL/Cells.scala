package GoL

/**
  */
class Cells(height: Int, width: Int) {
  val max_height = height; val max_width = width;
  val collection = Array.ofDim[Cell](max_height, max_width);

  for(line <- (0 until height)) {
    for(column <- (0 until width)) {
      collection(line)(column) = new Cell;
    }
  }

  def apply(height: Int, width: Int): Cell = {
    val line = positiveModulo(height, max_height)
    val column = positiveModulo(width, max_width)
    return collection(line)(column);
  }

  private def validPosition(line: Int, column: Int): Boolean = {
    line < max_height && line > -max_height &&
      column > -max_width && column < max_width
  }

  private def positiveModulo(num: Int, den: Int): Int = {
    val result = num % den
    return if (result >= 0) { result } else { result + den }
  }

}
