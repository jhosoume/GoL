package GoL

import scala.collection.mutable.ListBuffer

/**
 * Representa a Game Engine do GoL 
 *
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
trait GameEngineOriginal {
  

  }

  /*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
  private def validPosition(line: Int, column: Int) =
    line >= 0 && line < height && column >= 0 && column < width;


  /**
	 * Torna a celula de posicao (i, j) viva
	 *
	 * @param line posicao vertical da celula
	 * @param column posicao horizontal da celula
	 *
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def makeCellAlive(line: Int, column: Int) = {
    if(validPosition(line, column)){
      cells(line)(column).revive
      Statistics.recordRevive
    } else {
      throw new IllegalArgumentException
    }
  }

  /**
	 * Verifica se uma celula na posicao (line, column) estah viva.
	 *
	 * @param line Posicao vertical da celula
	 * @param column Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (line,column) esteja viva.
	 *
	 * @throws InvalidParameterException caso a posicao (i,j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def isCellAlive(line: Int, column: Int): Boolean = {
    if(validPosition(line, column)) {
      cells(line)(column).isAlive
    } else {
      throw new IllegalArgumentException
    }
  }


  /**
	 * Retorna o numero de celulas vivas no ambiente.
	 * Esse metodo eh particularmente util para o calculo de
	 * estatisticas e para melhorar a testabilidade.
	 *
	 * @return  numero de celulas vivas.
	 */
  def numberOfAliveCells {
    var aliveCells = 0
    for(line <- (0 until height)) {
      for(column <- (0 until width)) {
        if(isCellAlive(line, column)) aliveCells += 1
      }
    }
  }


  /* verifica se uma celula deve ser mantida viva */
  private def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (cells(line)(column).isAlive) &&
      (numberOfNeighborhoodAliveCells(line, column) == 2 || numberOfNeighborhoodAliveCells(line, column) == 3)
  }

  /* verifica se uma celula deve (re)nascer */
  private def shouldRevive(line: Int, column: Int): Boolean = {
    (!cells(line)(column).isAlive) &&
      (numberOfNeighborhoodAliveCells(line, column) == 3)
  }


  /*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 */
  private def numberOfNeighborhoodAliveCells(line: Int, column: Int): Int = {
    var alive = 0
    for(adj_line <- (line - 1 to line + 1)) {
      for(adj_column <- (column - 1 to column + 1)) {
        if (validPosition(adj_line, adj_column)  &&
              (!(adj_line==line && adj_column == column)) &&
               cells(adj_line)(adj_column).isAlive) {
					alive += 1
				}
      }
    }
    alive
  }

}