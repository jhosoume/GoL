package GoL
// Could be a implicit class extending a Memento Class

/**
  * Memory of a Cell
  * @param height: Int
  * @param width: Int
  * @param cells: Plane space of Cells
  */
class CellsMemento(height: Int, width: Int, cells: Array[Array[Cell]]) {
  /**
    * Basic representation of a collection of cells accordingly to the
    * implementation of CellsRepository
    */
  val collection = Array.ofDim[Cell](height, width)

  /**
    * Sets the state in consonance to a given CellsRepository state
    */
  for(line <- (0 until height)) {
    for(column <- (0 until width)) {
      collection(line)(column) = new Cell
      if (cells(line)(column).isAlive ) { collection(line)(column).revive }
    }
  }

}
