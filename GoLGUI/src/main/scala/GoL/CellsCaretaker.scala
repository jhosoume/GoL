package GoL

import scala.collection.mutable.ListBuffer

/** Deals with Mementos*/
object CellsCaretaker extends Caretaker[CellsMemento] {
  /** cellsMementos keeps all mementos created. Tests shows that a modern notebook can store
    * a lot of Mementos without overloading*/
  val mementosRepository = new ListBuffer[CellsMemento]
  /** current works as an index to provide information of the current state of the program
    * accordingly to the mementos stored */

  /**
    * Save a state of the CellsRepository
    */
  def persist: Unit = {
    mementosRepository += CellsRepository.save
    current += 1
  }

  /**
    * Restore CellsRepository to previous saved State
    */
  def undo: Unit = {
    if (validMemento(current - 1)) {
      current -= 1
      CellsRepository.restore(mementosRepository(current))
    } else {
      CellsRepository.restore(mementosRepository(0))
    }
  }

  /**
    * Restore CellsRepository to previous saved State
    */
  def redo: Unit = {
    if (validMemento(current + 1)) {
      current += 1
      CellsRepository.restore(mementosRepository(current))
    } else {
      CellsRepository.restore(mementosRepository.last)
    }
  }

  private def validMemento(position: Int): Boolean = {
    position >= 0 && position < mementosRepository.size
  }

  def clear: Unit = {
    current = -1
    mementosRepository.clear
  }
}