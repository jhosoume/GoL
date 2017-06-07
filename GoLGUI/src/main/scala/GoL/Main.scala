package GoL

/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object Main {

  /**
    * Stated height and width are passed to other objects
    * Any changes to height and width should be done here
    */
  val height = 17
  val width = 17

  /**
    * Starts the game independently from the game view
    * TODO main should start view
    * @param args
    */
  def main(args: Array[String]){
    GameController.start
  }
}