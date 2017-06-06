package GoL

/**
  * Collection of Cells
  * Stores a bundle of Cells
  */
object CellsRepository {

  /**
    * Get resources from Main and then creates an array to
    * store information about each Cell
    */
  val max_height = Main.height
  val max_width = Main.width
  val collection = Array.ofDim[Cell](max_height, max_width)

  /**
    * Code is executed when initialized. Creates a new instance
    * of Cell accordingly to height and width
    */
  for(line <- (0 until max_height)) {
    for(column <- (0 until max_width)) {
      collection(line)(column) = new Cell
    }
  }

  /**
    * Allows indexing a Cell from the repository using parentheses
    * @param height: Integer
    * @param width: Integer
    * @return Cell corresponding to the position given as parameter
    */
  def apply(height: Int, width: Int): Cell = {
    // Positive Module is called to allow for the infinite board
    val line = positiveModulo(height, max_height)
    val column = positiveModulo(width, max_width)
    collection(line)(column)
  }

  /**
    * Calculates the modulo of a number (returns the rest of a division)
    * Only returns positive numbers
    * @param num
    * @param den
    * @return Int
    */
  private def positiveModulo(num: Int, den: Int): Int = {
    val result = num % den
    if (result >= 0) { result } else { result + den }
  }

  /**
    * Does not delete instances of the cell!
    * Function to kill all Cells stored in the Repository
    */
  def clear: Unit ={
    for(i <- 0 until max_height) {
      for (j <- 0 until max_width) {
        CellsRepository(i, j).kill
      }
    }
  }

  /**
    * Saves a memory of the current state of the repository
    * (which cells are alive or dead)
    * Stores accordingly to the Memento definition
    * @return CellsMemento
    */
  def save: CellsMemento = {
    new CellsMemento(max_height, max_width, collection)
  }

  /**
    * Given a memory of the repository, it reestablishes a
    * state (which cells are alive or ded)
    * @param memento
    */
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
