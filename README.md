# Scala
```Scala``` é uma linguagem de programação multiparadigma, por ser orientada a objeto e funcional simultâneamente, é fortemente tipada.

É uma linguagem orientada a objeto no sentido que todo ```valor é um objeto```, tipos e comportamentos de objetos são descritos como ```classes``` e ```traits```.

Também se enquadra como funcional pois ```toda função é um valor```.
Scala provê uma sintaxe para definição de funções anônimas, disponibiliza funções de alta ordem, permite funções serem aglomeradas, e permite que métodos tenham diversas possiblidades de parâmetros```(currying)```,  além disso existem ```case classes``` que podem ser utilizadas para ```pattern matching```.

## Instalação
Idealmente, para usar os recursos da linguagem de forma adequada, deve-se
baixar o app da linguagem em ```https://www.scala-lang.org/download/```
Existem duas opções, uma delas é utilizar a biblioteca da linguagem na IDE Intellij IDEA(PAGA $$)
Ou alternativamente pode-se compilar e executar os códigos através do prompt do software sbt,
ambos estão disponíveis no site.

É importante notar que tanto para o IDE Intellij IDEA quanto para o sbt, nenhum código Scala
pode ser executado sem estar no escopo de uma classe ou objeto, isso só é possível no Scala Fiddle
ou em um prompt especifico, a chamada de um codigo Scala que tenha "chamadas soltas" retornará erros.

Guia do Sbt
Baixe e instale o SBT do site do Scala, depois disso, basta criar um diretorio onde deseja salvar
o seu projeto, executar o comando:
sbt new scala/myproject.h8, minimize o terminal e veja a estrutura criada.

Isso criará a estrutura do projeto em uma pasta com o nome myproject,
para esse mini-tutorial, atente-se que existe um arquivo gerado chamado
build.sbt, voce podera incluir dependencias nele acrescentando linhas no formato:
```libraryDependencies += "org.typelevel" %% "cats-core" % "1.4.0"```
Além disso, note que existe um diretório src/main/scala, o sbt deve ter
criado um arquivo 'Main' para você nesse caminho, abra-o e veja o código.
```object Main extends App { codigo }```
Para que um projeto no sbt seja executado, deve haver um arquivo com um
objeto que herde de App, o codigo dentro das chaves é o executado na execução
do projeto.
Se houver mais que um arquivo que herde de App, no momento da execução é possível selecionar qual será executado.
Retornando ao terminal, para entrar no prompt basta utilizar o comando sbt, caso voce
ainda nao tenha entrado nele, uma vez que isso foi feito, chame os comandos:
```
compile
run
```
Baixe o scala sbt em : ``` https://www.scala-sbt.org/ ```

## Sintaxe e características basicas da linguagem.
 Scala possui uma ferramenta de edição e execução na nuvem chamada de scalafiddle.io, para
 acessar os exemplos desenvolvidos para essa apresentação, acesse o link:
 ```
 https://scalafiddle.io/sf/NT2g0rk/23
 ```
 Para mais detalhes, acesse a documentação completa em:
 ```
 https://docs.scala-lang.org/tour/tour-of-scala.html
 ```

## Scala paralelo.
Scala é uma linguagem de programação que roda na máquina virtual do Java(JVM), assim sendo ela tem acesso as habilidades de multithread da mesma.

Uma vantagem de Scala sobre java é que ele não é limitado a Threads para concorrência(embora elas ainda sejam uma opção).

## Vantagens da concorrência no Scala
Scala é uma linguagem de programação funcional que clama evitar ```side effects``` através do encorajamento do uso de estruturas e valores imutáveis ao invez de variáveis.

E é por isso que o padrão de construção de ```List, Map e Case Class``` em Scala é imutavel e não pode ser alterada(no entanto existem as versões mutáveis dessas estruturas e ainda existem as variáveis).

Imutabilidade pode parecer não ter relação com paralelismo, mas pense em uma Thread que recebeu uma lista de Strings para processar.

No modelo java, essa lista pode ser atualizada por outras Threads ao mesmo tempo, para controlar isso voê precisará de uma thread-safe list, ou usar a keyword protected ou um Mutex.

 Como a lista em Scala é imutável, você tem certeza que ela não pode ser modificada por outras Threads, pois obviamente ela não pode ser modificada.

## Concorrência no Scala
As principais formas de trabalhar com tarefas simultâneas na linguagem são Futures, Actors e Threads.

## Futures
É uma forma simples de executar um algoritmo concorrentemente, basicamente ele começa a executar em sua criação e retorna um resultado eventualmente.

Os exemplos a seguir mostram algumas formas de criar futures e como trabalhar com seus resultados.

### Espera ocupada
O exemplo a seguir mostra como criar um future e então bloquear para esperar o seu resultado.
```
package actors

// 1 - the imports
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Futures1 extends App {

  // used by 'time' method
  implicit val baseTime = System.currentTimeMillis

  // 2 - create a Future
  val f = Future {
      sleep(500)
      1 + 1
  }

  // 3 - this is blocking (blocking is bad)
  val result = Await.result(f, 1 second)
  println(result)
  sleep(1000)

}
```
No entanto, vale lembrar que espera ocupada no geral não é uma boa estratégia e geralmente deve ser evitada.

### Callbacks
Uma aproximação mais interessante ao se trabalhar com future é o uso de seus métodos de callback ```onSucess e onFailure```.
```
import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random

object OnSuccessAndFailure extends App {
    val f = Future {
        sleep(Random.nextInt(500))
        if (Random.nextInt(500) > 250) throw new Exception("Yikes!") else 42
    }
    f onSuccess {
        case result => println(s"Success: $result")
    }
    f onFailure {
        case t => println(s"Exception: ${t.getMessage}")
    }

    // do the rest of your work
    println("A ..."); sleep(100)
    println("B ..."); sleep(100)
    println("C ..."); sleep(100)
    println("D ..."); sleep(100)
    println("E ..."); sleep(100)
    println("F ..."); sleep(100)
    sleep(2000)
}
```
A abordagem mostrada acima tem uma forma alternativa, onde os callbacks onSucess e onFailure são substituidos por onComplete, ficando:
```
import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random

object Example1 extends App {
    println("starting calculation ...")
    val f = Future {
        sleep(Random.nextInt(500))
        42
    }
    def sleep(time: Long) { Thread.sleep(time) }
    println("before onComplete")
    f.onComplete {
        case Success(value) => println(s"Got the callback, meaning = $value")
        case Failure(e) => e.printStackTrace
    }
    // do the rest of your work
    println("A ..."); sleep(100)
    println("B ..."); sleep(100)
    println("C ..."); sleep(100)
    println("D ..."); sleep(100)
    println("E ..."); sleep(100)
    println("F ..."); sleep(100)
    sleep(2000)
}
```
### Criando um método que retorna um Future[T]
É comum querer criar metodos que retornam futures, o exemplo a seguir define um metodo longRunningComputation e retorna um Future[Int], é importante notar que os callbacks são definidos na chamada.
```
import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Futures2 extends App {
    implicit val baseTime = System.currentTimeMillis
    def sleep(time: Long) { Thread.sleep(time) }
    def longRunningComputation(i: Int): Future[Int] = future {
        sleep(100)
        i + 1
    }

    // this does not block
    longRunningComputation(11).onComplete {
        case Success(result) => println(s"result = $result")
        case Failure(e) => e.printStackTrace
    }

    // important: keep the jvm from shutting down
    sleep(1000)
}
```
### Como usar multiplos futures em um for loop
Esses exemplos mostraram como rodar UM processo em paralelo, por motivos didáticos, no entanto muitas vezes se faz neccessário executar diversas operações concorrentemente, esperar que todas completem, para então fazer algo com seus resultados combinados.

```
import scala.concurrent.{Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random

object Cloud {
    def runAlgorithm(i: Int): Future[Int] = future {
        sleep(Random.nextInt(500))
        val result = i + 10
        println(s"returning result from cloud: $result")
        result
    }
    def sleep(time: Long) { Thread.sleep(time) }
}

object RunningMultipleCalcs extends App {
    println("starting futures")
    val result1 = Cloud.runAlgorithm(10)
    val result2 = Cloud.runAlgorithm(20)
    val result3 = Cloud.runAlgorithm(30)

    println("before for-comprehension")
    val result = for {
        r1 <- result1
        r2 <- result2
        r3 <- result3
    } yield (r1 + r2 + r3)

    println("before onSuccess")
    result onSuccess {
        case result => println(s"total = $result")
    }
    def sleep(time: Long) { Thread.sleep(time) }
    println("before sleep at the end")
    sleep(2000)  // important: keep the jvm alive
}
```

## Actors
Atores são processos concorrentes que se comunicam por meio de trocas de messagens.
A biblioteca de atores em Scala provê métodos sincronos e assincronos para troca de mensagens, eles podem usar futures quando as requisições forem assíncronas.

### Ping Pong
O nosso primeiro exemplo consiste em dois atores que trocam diversas mensagens para então terminar. O primeiro ator enviar a mensagem "ping", e o segundo envia a mensagem "pong" de volta.

Vamos iniciar definindo as mensagens de troca, para tal usaremos case objects(semelhantes as case class), que serão:
```
case object Ping
case object Pong
case object Stop
```

Segue o código do exemplo:
```
import scala.actors.Actor
import scala.actors.Actor._

class Ping(count: int, pong: Actor) extends Actor {
  def act() {
    var pingsLeft = count - 1
    pong ! Ping
    while (true) {
      receive {
        case Pong =>
          if (pingsLeft % 1000 == 0)
            Console.println("Ping: pong")
          if (pingsLeft > 0) {
            pong ! Ping
            pingsLeft -= 1
          } else {
            Console.println("Ping: stop")
            pong ! Stop
            exit()
          }
      }
    }
  }
}

class Pong extends Actor {
  def act() {
    var pongCount = 0
    while (true) {
      receive {
        case Ping =>
          if (pongCount % 1000 == 0)
            Console.println("Pong: ping "+pongCount)
          sender ! Pong
          pongCount = pongCount + 1
        case Stop =>
          Console.println("Pong: stop")
          exit()
      }
    }
  }
}

object pingpong extends Application {
  val pong = new Pong
  val ping = new Ping(100000, pong)
  ping.start
  pong.start
}
```

## Threads
Alternativamente pode-se usar threads oriundas do Java, como segue no exemplo:
```
// this is terrible code and you should not use it in production
class OrderProcessor extends Thread {
  override def run() {
    // runs forever
    while(true) {
      val orders = Orders.getOrders()
      orders.foreach{ order =>
        process(order)
      }
    }
  }
}

val thread = new OrderProcessor()
thread.start()
// waits for the thread to finish
thread.join()
```

Além das Threads tradicionais do java, é possível utilizar tarefas e ThreadPools, segue o exemplo:
```
class OrderProcessorRunnable(order: Order) extends Runnable {
  override def run() {
    order.process() // runs a long time
    order.save()
    order.user.save()
  }
}

val pool = Executors.newFixedThreadPool(2) // 2 threads
pool.submit(new OrderProcessorRunable(new Order()))
```
