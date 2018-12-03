package basics

class BasicOperations() {
	
	def showMathOperations(x:Int,y:Int): String = {
		var output = "Seguem as operações matemáticas simples:\n"
		output+=s"Operacao +: $x + $y = ${x+y}\n"
		output+=s"Operacao -: $x - $y = ${x-y}\n"
		output+=s"Operacao *: $x * $y = ${x*y}\n"
		output+=s"Operacao /: $x / $y = ${x/y}"
		output
	} 

	def showStringsBasicFunctions() :String = {
		var output = "Strings tem os metodos comuns do java embutidos, como:\n"
		output+="Length: varStr.length\n"
		output+="substring: varStr.substring(2,5)\n"
		output+="""replace: varStr.replace("C", "3")"""
		output+="\nO uso de aspas triplas pode ser feito para secoes de texto com aspas internas, comuns em tags HTML\n"
		output+="E possível interpolar strings e valores, utilizando o caracter s antes do inicio da string, e denotando a variavel com $, por exemplo\n"
		output+="""s"Operacao +: $x + $y = ${x+y}""""
		output
	}

	def showBasicDataStructures() :String = {
		//Static Array
		var a = Array(1, 2, 3, 5, 8, 13)
		println("Array pos 0")
		println(a(0))     // Int = 1
		
		//Static Map
		val m = Map("fork" -> "tenedor", "spoon" -> "cuchara", "knife" -> "cuchillo")
		
		println(m("fork"))
		val safeM = m.withDefaultValue("no lo se")
		safeM("bottle")   // java.lang.String = no lo se
		
		var m2 = Map("chave"->"valor")
		m2+=("key1" -> "value1")
		println(m2)
		""
	}


}

class Dog(br: String) {
  // Constructor code here
  var name = br

  // Define a method called bark, returning a String
  def latir = "Chamada de metodo latir - > Woof, woof!"

  // Values and methods are assumed public. "protected" and "private" keywords
  // are also available.
  def dormir(hours: Int) =
    s"Chamada de metodo dormir - > I'm sleeping for $hours hours"

  // Abstract methods are simply methods with no body. If we uncomment the
  // def line below, class Dog would need to be declared abstract like so:
  //   abstract class Dog(...) { ... }
  // def chaseAfter(what: String): String
}

trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}

class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0
  override def hasNext: Boolean = current < to
  override def next(): Int =  {
    if (hasNext) {
      val t = current
      current += 1
      t
    } else 0
  }
}


