package concurrent

import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object ReturnFutureExample extends App {
    implicit val baseTime = System.currentTimeMillis

    def longRunningComputation(i: Int): Future[Int] = future {
        sleep(100)
        i + 1
    }

    def sleep(time: Long) { Thread.sleep(time) }
    
    // this does not block
    longRunningComputation(11).onComplete {
        case Success(result) => println(s"result = $result")
        case Failure(e) => e.printStackTrace
    }

    // important: keep the jvm from shutting down
    sleep(1000)
}
