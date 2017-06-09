package GoL

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Created by Avell 1513 on 08/06/2017.
  */

class PopupError (path: String) {

  new Alert(AlertType.Error) {
    initOwner(View.stage)
    title = "Error"
    headerText = "No such file or directory found."
    contentText = "Exception in: " + path
  }.showAndWait()
}
