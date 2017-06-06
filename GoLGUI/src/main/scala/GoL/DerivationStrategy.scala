package GoL

/**
  * Contract to implement new rules for the game of life
  */
trait DerivationStrategy {
  /* instructs if cell should still be alive in the next generation */
  def shouldKeepAlive(line: Int, column: Int): Boolean

  /* instructs if cell should be revived in the next generation */
  def shouldRevive(line: Int, column: Int): Boolean

}
