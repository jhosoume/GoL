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

object drawMain extends JFXApp {
  val height = 17
  val width = 17
  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(650, 570) {

      //Menus
      val menuBar = new MenuBar

      val fileMenu = new Menu("File")
      val newIt = new MenuItem("New")     /** Partially Implemented **/
      val openIt = new MenuItem("Open")   /** To Implement **/
      val exitIt = new MenuItem("Exit")   /** Implemented **/
      fileMenu.items = List(newIt, openIt, exitIt)

      val ruleMenu = new Menu("Rules")
      val stratIt = new MenuItem("Strategy")    /** To Implement **/
      val templIt = new MenuItem("Template")    /** To Implement **/
      ruleMenu.items = List(stratIt, templIt)

      menuBar.menus = List(fileMenu, ruleMenu)
      menuBar.prefWidth = 650

      newIt.onAction = (event: ActionEvent) => startChart(true)

      exitIt.onAction = (event: ActionEvent) => sys.exit(0)

      val manualB = new RadioButton("Manual")
      manualB.layoutX = 10
      manualB.layoutY = 400

      manualB.onAction = (event: ActionEvent) => {startChart(true)}

      val autoB = new RadioButton("Automatic")
      autoB.layoutX = 10
      autoB.layoutY = 420

      autoB.onAction = (event: ActionEvent) => startChart(false)     /** Update later to calling a makeCells method **/

      val toggle = new ToggleGroup
      toggle.toggles = List(manualB, autoB)

      //Buttons
      val playB = new Button("PLAY")
      playB.layoutX = 10
      playB.layoutY = 470

      playB.onAction = (event: ActionEvent) => {
        GameEngine.nextGeneration
        updateChart
      }

      val haltB = new Button("HALT")
      haltB.layoutX = 60
      haltB.layoutY = 470

      val undoB = new Button("UNDO")
      undoB.layoutX = 5
      undoB.layoutY = 520
      val redoB = new Button("REDO")
      redoB.layoutX = 60
      redoB.layoutY = 520

      content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB)

      val cell = Array.ofDim[Rectangle](drawMain.height, drawMain.width)

      def startChart(mode: Boolean) {
        for (i <- 0 until drawMain.height) {
          for (j <- 0 until drawMain.width) {
            cell(i)(j) = Rectangle(120 + (30 * i), 40 + (30 * j), 29, 29)
            cell(i)(j).fill = Color.Gray

            if (mode){
              cell(i)(j).onMouseClicked = (event: MouseEvent) => {
                if(GameEngine.cells(i)(j).isAlive){
                  GameEngine.cells(i)(j).kill
                  cell(i)(j).fill = Color.Gray
                }else{
                  GameEngine.cells(i)(j).revive
                  cell(i)(j).fill = Color.Blue
                }
              }
            }else{
              var pSet = Array.ofDim[Int](drawMain.height, drawMain.width)
                             //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
              pSet = Array(Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                           Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0))

              for(l <- 0 until drawMain.height){
                for(c <- 0 until drawMain.width){
                  if(pSet(l)(c) == 0) {
                    GameEngine.cells(l)(c).kill
                    cell(l)(c).fill = Color.Gray
                  }else{
                    GameEngine.cells(l)(c).revive
                    cell(l)(c).fill = Color.Blue

                  }
                }
              }
            }
            content += cell(i)(j)
          }
        }
      }
      def updateChart: Unit ={
        for(i <- 0 until drawMain.height){
          for(j <- 0 until drawMain.width){
            cell(i)(j).fill = if(GameEngine.cells(i)(j).isAlive) Color.Blue else Color.Gray
          }
        }
      }
    }
    /**var n = 0
      def changeColor(c: Rectangle, a: Color, b: Color) {
        n += 1
        if (n % 2 == 0) {
          c.fill = a
        } else {
          c.fill = b
        }
      }

      def updateChart {
        for(i <- 0 until drawMain.height) {
          for(j <- 0 until drawMain.width) {

          }
        }
      }**/
  }
}