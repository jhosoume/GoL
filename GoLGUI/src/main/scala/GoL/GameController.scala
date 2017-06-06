package GoL

/**
 * Keeps the relation between View and Model
  * @author Jonatas, Juliana and Luisa
  *         Adapted from code from Breno Xavier
 */
object GameController {

  def start {
    clear
    CellsCaretaker.persist
    View.updateChart
  }
  
  def halt() {
    //oops, nao muito legal fazer sysout na classe Controller
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

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

  def checkCellAlive(line: Int, column: Int): Boolean = {
    GameEngine.isCellAlive(line, column)
  }

  def nextGeneration {
    GameEngine.nextGeneration
    CellsCaretaker.persist
    View.updateChart
  }

  def goBack: Unit = {
    CellsCaretaker.undo
    View.updateChart
  }

  def goFoward: Unit = {
    CellsCaretaker.redo
    View.updateChart
  }

  def clear: Unit = {
    CellsCaretaker.clear
    CellsRepository.clear
    View.updateChart
  }
  
}