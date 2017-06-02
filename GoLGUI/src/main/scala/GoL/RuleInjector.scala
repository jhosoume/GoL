package GoL
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

import Rules.{HighLife, CustomStrategy}
/**
  * Created by jhosoume on 23/05/17.
  */
class RuleInjector extends AbstractModule with ScalaModule {
  def configure(): Unit = {
//    bind[GameEngine.type].to[Rules.CustomStrategy.type]
//    bind[Rules.HighLife.type].to[Rules.HighLife.type].in[GameEngine.type]
  }

}
