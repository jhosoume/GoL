package GoL_Template
import scala.collection.mutable.ListBuffer

/**
 * Representa a Game Engine do GoL 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameEngineOriginal extends GameEngine{
  

  /**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
	 * uma celula viva.
	 * 
	 * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
	 * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
  

  /* verifica se uma celula deve ser mantida viva */
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (cells(line)(column).isAlive) &&
      (numberOfNeighborhoodAliveCells(line, column) == 2 || numberOfNeighborhoodAliveCells(line, column) == 3)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!cells(line)(column).isAlive) &&
      (numberOfNeighborhoodAliveCells(line, column) == 3)
  }

}