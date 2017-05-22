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

object View extends JFXApp {
  /**
    * Size of the board, not the window
    */
  val height = Main.height
  val width = Main.width

  // Define rectangles that will change color accordinlgy to the state of the cell
  val cell = Array.ofDim[Rectangle](View.height, View.width)

  /**
    * Uppdate view accordingly to information in CellsRepository
    */
  def updateChart: Unit = {
    for(line <- 0 until View.height) {
      for(column <- 0 until View.width) {
        cell(line)(column).fill = if (GameEngine.isCellAlive(line, column)) Color.Blue else Color.Gray
      }
    }
  }

  def initializeCells(line: Int, column: Int): Rectangle = {
    cell(line)(column) = Rectangle(120 + (30 * line), 40 + (30 * column), 29, 29)
    cell(line)(column).fill = Color.Gray
    // Make button clickable
    cell(line)(column).onMouseClicked = (event: MouseEvent) => {
      if (CellsRepository(line, column).isAlive) {
        //TODO
        CellsRepository(line, column).kill
        cell(line)(column).fill = Color.Gray
      } else {
        CellsRepository(line, column).revive
        cell(line)(column).fill = Color.Blue
      }
    }
    cell(line)(column)
  }

  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(650, 570) {

      // Menus
      val menuBar = new MenuBar

      val fileMenu = new Menu("File")
      val newIt = new MenuItem("New")     /** TODO Partially Implemented **/
      val openIt = new MenuItem("Open")   /** TODO To Implement **/
      val exitIt = new MenuItem("Exit")   /** Implemented **/
      fileMenu.items = List(newIt, openIt, exitIt)

      val ruleMenu = new Menu("Rules")
      val stratIt = new MenuItem("Strategy")    /** TODO Implement **/
      val templIt = new MenuItem("Template")    /** TODO Implement **/
      ruleMenu.items = List(stratIt, templIt)

      menuBar.menus = List(fileMenu, ruleMenu)
      menuBar.prefWidth = 650

      newIt.onAction = (event: ActionEvent) => {
        startChart
        GameController.start
      }

      exitIt.onAction = (event: ActionEvent) => GameController.halt

//      val manualB = new RadioButton("Manual")
//      manualB.layoutX = 10
//      manualB.layoutY = 400
//      manualB.onAction = (event: ActionEvent) => {
//        CellsRepository.clear
//        //TODO
//        startChart(true)}

      val autoB = new RadioButton("Seed")
      autoB.layoutX = 10
      autoB.layoutY = 420
      autoB.onAction = (event: ActionEvent) => {
        GameController.clear
        seed
      }/** Update later to calling a makeCells method **/

      val toggle = new ToggleGroup
      toggle.toggles = List(autoB)
//      toggle.toggles = List(manualB, autoB)

      //Buttons
      val playB = new Button("PLAY")
      playB.layoutX = 10
      playB.layoutY = 470

      playB.onAction = (event: ActionEvent) => GameController.nextGeneration

      val haltB = new Button("HALT")
      haltB.layoutX = 60
      haltB.layoutY = 470

      val undoB = new Button("UNDO")
      undoB.layoutX = 5
      undoB.layoutY = 520

      undoB.onAction = (event: ActionEvent) => GameController.goBack

      val redoB = new Button("REDO")
      redoB.layoutX = 60
      redoB.layoutY = 520

      redoB.onAction = (event: ActionEvent) => GameController.goFoward

      content = List(menuBar, playB, haltB, undoB, redoB, autoB)
//      content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB)

      def seed {
        var pSet = Array.ofDim[Int](View.height, View.width)
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

        for (l <- 0 until View.height) {
          for (c <- 0 until View.width) {
            if (pSet(l)(c) == 0) {
              CellsRepository(l, c).kill
              //TODO
              cell(l)(c).fill = Color.Gray
            } else {
              CellsRepository(l, c).revive
              cell(l)(c).fill = Color.Blue
            }
          }
        }
      }

      def startChart {
        for (line <- 0 until View.height) {
          for (column <- 0 until View.width) {
            content += initializeCells(line, column)
          }
        }
      }
    }
  }
}

