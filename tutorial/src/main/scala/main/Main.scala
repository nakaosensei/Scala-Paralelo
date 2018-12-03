package main
import basics.Dog
import basics.BasicOperations
import basics.Iterator
import basics.IntIterator

object LanguageBasics extends App {
  //Codigos de sintaxe simples.	
  println(s"Hello World!")
  val dogObject = new Dog("greyhound")
  val basics = new BasicOperations()
  println(dogObject.name)
  println(dogObject.latir)
  println(dogObject.dormir(10))
  println(basics.showMathOperations(3,2))
  println(basics.showStringsBasicFunctions())
  basics.showBasicDataStructures()
  val iterator = new IntIterator(10)
  iterator.next()  // returns 0
  iterator.next()
  
  //BusyWait
  
}
