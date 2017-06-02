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
  def isAlive = alive

  /**
    * Setters for Cell attribute alive
    */
  def kill = alive = false
  def revive = alive = true
}