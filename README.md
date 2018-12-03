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

## Sintaxe e características basicas da linguagem.
 Scala possui uma ferramenta de edição e execução na nuvem chamada de scalafiddle.io, para
 acessar os exemplos desenvolvidos para essa apresentação, acesse o link:
 ```
 https://scalafiddle.io/sf/NT2g0rk/23
 ```

## Scala paralelo.
