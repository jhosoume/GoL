package GoL

/**
  * @author Jonatas, Juliana and Luisa
  *         Adapted from code from Breno Xavier
 */
class Cell {
  
  private var alive = false

  /**
    * Getter for att .alive
    * @return (Boolean) alive
    */
  def isAlive: Boolean = alive

  /**
    * Setters for Cell attribute alive
    */
  def kill: Unit = { alive = false }
  def revive: Unit = { alive = true }

  /**
    * Modifies the state of the cell to
    * the opposite one
    */
  def changeState: Unit = {
    if (this.isAlive) { this.kill }
    else { this.revive }
  }
}