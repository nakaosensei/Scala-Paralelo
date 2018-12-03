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
objeto Main que herde de App, o codigo dentro das chaves é o executado na execução
do projeto.
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

## Actors

## Threads
