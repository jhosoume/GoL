package View

import javafx.event.EventHandler

import com.sun.org.apache.xpath.internal.operations.Bool

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.layout.GridPane

object drawMain extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(550, 600) {

      var manual = true
      //Menus
      val menuBar = new MenuBar
      val ruleMenu = new Menu("Rules")
      val stratIt = new MenuItem("Strategy")
      val templIt = new MenuItem("Template")
      ruleMenu.items = List(stratIt,templIt)
      val setMenu = new Menu("Set")
      val autoIt = new MenuItem("Automatic"){
        onAction = (event: ActionEvent) => {
          for (i <- 0 until 17) {
            for (j <- 0 until 17) {

              cell(i)(j) = Rectangle(20 + (30 * i), 40 + (30 * j), 29, 29)
              cell(i)(j).fill = Color.Blue
              content += cell(i)(j)
            }
          }
        }
      }

      val manualIt = new MenuItem("Manual"){
        onAction = (event: ActionEvent) => {
          for (i <- 0 until 17) {
            for (j <- 0 until 17) {

              cell(i)(j) = Rectangle(20 + (30 * i), 40 + (30 * j), 29, 29)
              cell(i)(j).fill = Color.Blue
              cell(i)(j).onMouseClicked = (event: MouseEvent) => changeColor(cell(i)(j),Color.Blue,Color.Gray)

              content += cell(i)(j)
            }
          }
        }
      }
      setMenu.items = List(autoIt,manualIt)
      menuBar.menus = List(ruleMenu, setMenu)
      menuBar.prefWidth = 600

      //Buttons
      val playB = new Button("PLAY")
      playB.layoutX = 27
      playB.layoutY = 570
      val haltB = new Button("HALT")
      haltB.layoutX = 70
      haltB.layoutY = 570
      val undoB = new Button("UNDO")
      undoB.layoutX = 418
      undoB.layoutY = 570
      val redoB = new Button("REDO")
      redoB.layoutX = 470
      redoB.layoutY = 570

      /**val rect1 = Rectangle(10, 30, 29, 29) /** Apply method **/
      rect1.fill = Color.Black
      rect1.onMouseClicked = (event: MouseEvent) => changeColor(rect1)

      val rect2 = Rectangle(40, 30, 29, 29) /** Apply method **/
      rect2.fill = Color.Gray
      rect2.onMouseClicked = (event: MouseEvent) => changeColor(rect2)**/

      content = List(menuBar, playB, haltB, undoB, redoB)

      val cell = Array.ofDim[Rectangle](17,17)
      for (i <- 0 until 17) {
        for (j <- 0 until 17) {

          cell(i)(j) = Rectangle(20 + (30 * i), 40 + (30 * j), 29, 29)
          cell(i)(j).fill = Color.Black
          //cell(i)(j).onMouseClicked = (event: MouseEvent) => changeColor(cell(i)(j), Color.Blue, Color.Gray)

          content += cell(i)(j)
        }
      }

      var n = 0
      def changeColor(c: Rectangle, a: Color, b: Color) {
        n += 1
        if (n % 2 == 0) {
          c.fill = a
        } else {
          c.fill = b
        }
      }
    }
  }
}
