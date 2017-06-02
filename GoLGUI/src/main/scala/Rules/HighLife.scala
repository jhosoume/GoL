package Rules

import GoL.{CellsRepository, DerivationStrategy, GameEngine}

object HighLife extends DerivationStrategy {
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == 2 || GameEngine.numberOfNeighborhoodAliveCells(line, column) == 3)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == 6)
  }

}
