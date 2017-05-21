/**
  */
class Cell {
  private var alive = false

  /**
    * Setter for att .alive
    *
    * @return (boolean) alive
    */
  val isAlive = alive

  val kill = alive = false
  val revive = alive = true

}
