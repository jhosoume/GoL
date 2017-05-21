/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {
  
  def start {
    CellsCaretaker.persist
    GameView.update
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
      CellsCaretaker.persist
			GameView.update
		}
		catch {
		  case ex: IllegalArgumentException => {
		    println(ex.getMessage)
		  }
		}
  }
  
  def nextGeneration {
    GameEngine.nextGeneration
    CellsCaretaker.persist
    GameView.update
  }

  def gensUntilTotalDeath {
    while (GameEngine.numberOfAliveCells > 0) {
      GameEngine.nextGeneration
      GameView.update
    }
  }

  def goBack {
    CellsCaretaker.undo
    GameView.update
  }

  def goFoward {
    CellsCaretaker.redo
    GameView.update
  }

}