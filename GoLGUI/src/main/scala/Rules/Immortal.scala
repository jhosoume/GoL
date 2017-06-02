package Rules

import GoL.{DerivationStrategy, CellsRepository, GameEngine}

/**
  * Created by Avell 1513 on 23/05/2017.
  */
object Immortal extends DerivationStrategy {
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 0)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 1)
  }

}