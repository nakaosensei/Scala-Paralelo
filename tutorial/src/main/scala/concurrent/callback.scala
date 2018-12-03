package concurrent

// 1 - the imports
import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random


object FutureCallbacks extends App {
    val f = Future {
        sleep(Random.nextInt(500))
        val someNumber = Random.nextInt(500)
        println(someNumber) 
        if (someNumber > 250) 50 else 42
    }
    f onSuccess {
        case resultado => println(s"Success: $resultado")
    }
    f onFailure {
        case falha => println(s"Exception: ${falha.getMessage}")
    }

    // do the rest of your work
    println("A ..."); sleep(100)
    println("B ..."); sleep(100)
    println("C ..."); sleep(100)
    println("D ..."); sleep(100)
    println("E ..."); sleep(100)
    println("F ..."); sleep(100)
    sleep(2000)

    def sleep(time: Long) { Thread.sleep(time) }
}