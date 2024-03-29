package GoL

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.Includes._
import scalafx.event.ActionEvent
import java.util.Timer

import Rules._

/**
  * Creates the Graphical Interface for the Game of Life
  */
object GameView extends JFXApp {
  /**
    * Size of the board, not the window
    */
  val height = Main.height
  val width = Main.width

  /** Define rectangles that will change color accordinlgy to the state of the cell **/
  val cell = Array.ofDim[Rectangle](GameView.height, GameView.width)

  /** Sets defaualt color fot alive Cells **/
  var aliveColor = Color.Blue

  /**
    * Update view accordingly to information in CellsRepository
    */
  def updateChart(): Unit = {
    for(line <- 0 until GameView.height) {
      for(column <- 0 until GameView.width) {
        cell(line)(column).fill = if (GameController.checkCellAlive(line, column)) aliveColor else Color.Gray
      }
    }
  }

  /**
    * Instantiate rectangles to create board. Each rectangle corresponds to a Cell
    * @param line: Int
    * @param column: Int
    * @return cell rectangle to be included in a list of listeners
    */
  def initializeCell(line: Int, column: Int): Rectangle = {
    cell(line)(column) = Rectangle(120 + (30 * line), 40 + (30 * column), 29, 29)
    cell(line)(column).fill = Color.Gray
    // Make button clickable
    cell(line)(column).onMouseClicked = (event: MouseEvent) => {
      cellOnClick(line, column)
    }
    cell(line)(column)
  }

  /**
    * Define actions to be done if a cell rectangle is cliked
    * When clicked, a cell just swap its state
    * @param line: Int
    * @param column: Int
    */
  def cellOnClick(line: Int, column: Int): Unit = {
    if (GameController.checkCellAlive(line, column)) {
      GameController.makeCellDead(line, column)
      cell(line)(column).fill = Color.Gray
    } else {
      GameController.makeCellAlive(line, column)
      cell(line)(column).fill = aliveColor
    }
    CellsCaretaker.persist
  }

  /** Stage is the main holder of the ScalaFX window **/
  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(650, 570) {

      /** Sets timer for undefined generations **/
      val timer = new java.util.Timer()
      var task = new java.util.TimerTask {
        def run(): Unit = GameController.nextGeneration
      }

      /** Top Menu **/
      val menuBar = new MenuBar
      /** Instantiate Menu Items **/
      val fileMenu = new Menu("File")
      val newIt = new MenuItem("New")     /** TODO Partially Implemented **/
      //val openIt = new MenuItem("Open")
      val exitIt = new MenuItem("Exit")
      /** Add Menu items to the list of listeners of the Menu **/
      fileMenu.items = List(newIt, exitIt)

      /** Creates the rules items **/
      val ruleMenu = new Menu("Rules")
      val originIt = new MenuItem("Original")
      val immortIt = new MenuItem("Immortal")    /** TODO Implement **/
      val highIt = new MenuItem("High Life")    /** TODO Implement **/
      val importIt = new MenuItem("Import Rule from file")
      val defRule = new MenuItem("Set your own rule!")
      /** Add Rule items to the list of listeners of the Menu **/
      ruleMenu.items = List(originIt, highIt, immortIt, importIt, defRule)
      menuBar.menus = List(fileMenu, ruleMenu)
      menuBar.prefWidth = 650
      /** Define actions for each rule button **/
      originIt.onAction = (event: ActionEvent) => { GameController.setRule(OriginalStrategy) }
      highIt.onAction = (event: ActionEvent) => { GameController.setRule(Rules.HighLife) }
      immortIt.onAction = (event: ActionEvent) => { GameController.setRule(Rules.Immortal) }
      importIt.onAction = (event: ActionEvent) => { new PopupSearch }
      defRule.onAction = (event: ActionEvent) => { new PopupW }
      /** Define actions for Main Menu Items **/
      newIt.onAction = (event: ActionEvent) => {
        startChart
        GameController.start
      }
      exitIt.onAction = (event: ActionEvent) => GameController.halt()

      /** Implementations for different colors of the board **/
      val blueB = new ToggleButton("Blue")
      blueB.layoutX = 10
      blueB.layoutY = 50
      blueB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Blue
        updateChart()}
      val redB = new ToggleButton("Red")
      redB.layoutX = 10
      redB.layoutY = 80
      redB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Red
        updateChart()}
      val greenB = new ToggleButton("Green")
      greenB.layoutX = 10
      greenB.layoutY = 110
      greenB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Green
        updateChart()}
      val colors = new ToggleGroup
      colors.toggles = List(blueB, redB, greenB)

      /** Interesting Seed for the Game **/
        //TODO! Seeds should be in its own class
      //val autoB = new RadioButton("Seed")
      val pulsarB = new Button("Pulsar")
      pulsarB.layoutX = 10
      pulsarB.layoutY = 170
      pulsarB.onAction = (event: ActionEvent) => {
        GameController.start
        seed1
      }
      val gliderB = new Button("Glider")
      gliderB.layoutX = 10
      gliderB.layoutY = 200
      gliderB.onAction = (event: ActionEvent) => {
        GameController.start
        seed2
      }

      /** Left Main Buttons  **/
      val nextB = new Button("Next Generation")
      nextB.layoutX = 5
      nextB.layoutY = 420
      nextB.onAction = (event: ActionEvent) => GameController.nextGeneration

      val playB = new Button("PLAY")
      playB.layoutX = 10
      playB.layoutY = 470
      playB.onAction = (event: ActionEvent) => {
        task = new java.util.TimerTask {
          def run(): Unit = GameController.nextGeneration
        }
        timer.schedule(task, 500 ,500)
      }

      val stopB = new Button("STOP")
      stopB.layoutX = 60
      stopB.layoutY = 470
      stopB.onAction = (event: ActionEvent) => {
        task.cancel()
      }

      val undoB = new Button("UNDO")
      undoB.layoutX = 5
      undoB.layoutY = 520
      undoB.onAction = (event: ActionEvent) => GameController.goBack

      val redoB = new Button("REDO")
      redoB.layoutX = 60
      redoB.layoutY = 520
      redoB.onAction = (event: ActionEvent) => GameController.goFoward
      /** Add left main buttons to event listener list **/
      content = List(menuBar, nextB, playB, stopB, undoB, redoB, pulsarB, gliderB, blueB, redB, greenB)

      def seed1 {
        var pSet = Array.ofDim[Int](GameView.height, GameView.width)
        //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
        pSet = Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))


        for (l <- 0 until GameView.height) {
          for (c <- 0 until GameView.width) {
            if (pSet(l)(c) == 0) {
              CellsRepository(l, c).kill
              //TODO
              cell(l)(c).fill = Color.Gray
            } else {
              CellsRepository(l, c).revive
              cell(l)(c).fill = aliveColor
            }
          }
        }
        //TODO
        CellsCaretaker.persist
      }

      def seed2 {
        var pSet = Array.ofDim[Int](GameView.height, GameView.width)
        //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
        pSet = Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))

        for (l <- 0 until GameView.height) {
          for (c <- 0 until GameView.width) {
            if (pSet(l)(c) == 0) {
              CellsRepository(l, c).kill
              //TODO
              cell(l)(c).fill = Color.Gray
            } else {
              CellsRepository(l, c).revive
              cell(l)(c).fill = aliveColor
            }
          }
        }
        //TODO
        CellsCaretaker.persist
      }

      def startChart {
        for (line <- 0 until GameView.height) {
          for (column <- 0 until GameView.width) {
            content += initializeCell(line, column)
          }
        }
      }
    }
  }
}

