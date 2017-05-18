package GoL

/**
  */

//object Cells {
//
//  def apply(height: Int, width: Int): Cells = {
//    val colls = Array.ofDim[Cell](height, width)
//    for (line <- (0 until height)) {
//      for (column <- (0 until width)) {
//        colls(line)(column) = new Cell;
//      }
//    }
//    new Cells(height, width, colls)
//  }
//
//}

//class Cells(max_height: Int, max_width: Int, collection: Array[Array[Cell]]) {
//  val max_height = height; val max_width = width;
//  val collection = Array.ofDim[Cell](max_height, max_width);

object Cells {
  val max_height = Main.height; val max_width = Main.width;
  val collection = Array.ofDim[Cell](max_height, max_width);

    for (line <- (0 until max_height)) {
      for (column <- (0 until max_width)) {
        collection(line)(column) = new Cell;
      }
    }

  def apply(height: Int, width: Int): Cell = {
    val line = positiveModulo(height, max_height)
    val column = positiveModulo(width, max_width)
    return collection(line)(column);
  }

  private def positiveModulo(num: Int, den: Int): Int = {
    val result = num % den
    return if (result >= 0) { result } else { result + den }
  }

  def save: CellsMemento = {
    return new CellsMemento(max_height, max_width, collection)
  }

  def restore(memento: CellsMemento) = {
    for (line <- (0 until max_height)) {
      for (column <- (0 until max_width)) {
        if (memento.collection(line)(column).isAlive) {
          collection(line)(column).revive
        } else {
          collection(line)(column).kill
        }
      }
    }
  }

}
