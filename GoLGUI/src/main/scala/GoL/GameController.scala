package GoL

/**
 * Keeps the relation between View and Model
  * Actions from View, should only be reflect in models thru the controller
  * @author Jonatas, Juliana and Luisa
  *         Adapted from code from Breno Xavier
 */
object GameController {

  /**
    * Start engine and view, clearing anything done before and
    * saving initial state, updating the view to the start
    */
  def start {
    clear
    CellsCaretaker.persist
    View.updateChart
  }

  /**
    * Just stops everything, returning Statistics
    */
  def halt() {
    //oops, nao muito legal fazer sysout na classe Controller
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

  /**
    * If called, connects statement to a state in the Models
    * Changes a specific Cell positioned in the given line and
    * column to alive state
    * @param line: Int
    * @param column: Int
    */
  def makeCellAlive(line: Int, column: Int) {
    try {
			GameEngine.makeCellAlive(line, column)
			View.updateChart
		}
		catch {
		  case ex: IllegalArgumentException => {
		    println(ex.getMessage)
		  }
		}
  }

  /**
    * If called, connects statement to a state in the Models
    * Changes a specific Cell positioned in the given line and
    * column to a ded state
    * @param line: Int
    * @param column: Int
    */
  def makeCellDead(line: Int, column: Int) {
    try {
      GameEngine.makeCellDead(line, column)
      View.updateChart
    }
    catch {
      case ex: IllegalArgumentException => {
        println(ex.getMessage)
      }
    }
  }

  /**
    * Access Game Engine to check if a position has a cell that is alive
    * @param line: Int
    * @param column: Int
    * @return Boolean (Returns true if cell is alive)
    */
  def checkCellAlive(line: Int, column: Int): Boolean = {
    GameEngine.isCellAlive(line, column)
  }

  /**
    * Calls the GameEngine to compute the next generation
    * The new state is saved as a Memento and the GameView is update
    */
  def nextGeneration {
    GameEngine.nextGeneration
    CellsCaretaker.persist
    View.updateChart
  }

  /**
    * Undo command, connects event in view to Memento model
    */
  def goBack: Unit = {
    CellsCaretaker.undo
    View.updateChart
  }

  /**
    * Redo command, connects event in view to Memento model
    */
  def goFoward: Unit = {
    CellsCaretaker.redo
    View.updateChart
  }

  /**
    * Clears every aspect of the Game
    */
  private def clear: Unit = {
    CellsCaretaker.clear
    CellsRepository.clear
    View.updateChart
  }

  /**
    * Changes a rule of the GameEngine specified in the View
    * @param rule: DerivationStrategy
    */
  def setRule(rule: DerivationStrategy): Unit = {
    GameEngine.rule = rule
  }
  
}