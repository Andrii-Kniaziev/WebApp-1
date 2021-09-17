import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model._
import scala.concurrent.ExecutionContext
import model.PingService

object Application extends App {
  implicit val system = ActorSystem(Behaviors.empty, "lowlevel")
  implicit val executionContext: ExecutionContext = system.executionContext

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = "Default page...")

    case HttpRequest(POST, Uri.Path("/ping"), _, _, _) =>
      PingService.callInfo()
      HttpResponse(entity = "PONG!")

    case r: HttpRequest =>
      r.discardEntityBytes()
      HttpResponse(404, entity = "Unknown resource!")
  }

  val bindingFuture = Http().newServerAt("0.0.0.0", 8000).bindSync(requestHandler).recoverWith {
    case _ => sys.exit(1)
  }

  sys.addShutdownHook {
    bindingFuture.map(_.unbind())
  }
}