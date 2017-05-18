package GoL

import scala.collection.mutable.ListBuffer

/**
  */
object CellsCaretaker {
  val cellsMementos = new ListBuffer[CellsMemento]
  private var current = -1

  def persist: Unit = {
    cellsMementos += Cells.save
    current += 1
  }

//  @throws(classOf[IllegalArgumentException])
  def undo: Unit = {
    if (validMemento(current - 1)) {
      current -= 1
      Cells.restore(cellsMementos(current))
    } else {
      Cells.restore(cellsMementos(0))
//      throw new IllegalArgumentException
    }
  }

//  @throws(classOf[IllegalArgumentException])
  def redo: Unit = {
    if (validMemento(current + 1)) {
      current += 1
      Cells.restore(cellsMementos(current))
    } else {
//      throw new IllegalArgumentException
      Cells.restore(cellsMementos.last)
    }
  }

  private def validMemento(position: Int): Boolean = {
    position >= 0 && position < cellsMementos.size
  }
}
