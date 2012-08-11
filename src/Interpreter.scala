package com.joepritzel.bf

import scala.io.Source

object Interpreter {

  type Ins = () => Unit
  type InsList = List[Ins]

  def main(args : Array[String]) {
    val file = Source.fromFile(args(0))
    val program = file.getLines.mkString
    file.close

    val opens = program.count('['==)
    val closes = program.count(']'==)

    if (opens > closes)
      throw new RuntimeException("There is a [ without a ]")
    if (opens < closes)
      throw new RuntimeException("There is a ] without a [")

    val ptr = args.length match {
      case 2 => new BytePtr(args(1).toInt)
      case 3 => new BytePtr(args(1).toInt, args(2).toInt)
      case _ => new BytePtr
    }

    parse(program, ptr)
  }

  def parse(ins : String, ptr : BytePtr) : InsList = {

    val i = ins.takeWhile(c => c != '[' && c != ']').toList.map(parseIns(_, ptr))
    i.foreach(_())

    val ins1 = ins.drop(i.size)
    if (ins1.size > 0) {
      val ins2 = ins1.substring(1)
      val matcher = findMatcher(ins2)
      if (ins1(0) == '[')
        loop(ins1.substring(1, matcher), ptr)
      parse(ins1.substring(matcher + 1), ptr)
    }
    
    i
  }

  def findMatcher(ins : String) = {
    var s = ins
    var ct = 1
    var i = 0
    while (ct != 0) {
      val o = s.indexOf('[')
      val c = s.indexOf(']')
      val idx = {
        if (o >= 0 && c > o) o
        else if (c >= 0) c
        else throw new RuntimeException("There is a ] before an opening [")
      }
      if (s.charAt(idx) == '[') ct += 1
      else if (s.charAt(idx) == ']') ct -= 1
      s = s.substring(idx + 1)
      i = ins.length - s.length
    }
    i
  }

  def loop(ins : String, ptr : BytePtr) {
    var lastExec : InsList = List[Ins]()
    while (ptr.* != 0) {
      lastExec = parse(ins, ptr)
    }
  }

  def parseIns(c : Char, ptr : BytePtr) : Ins = {
    c match {
      case '>' => ptr.++ _
      case '<' => ptr.-- _
      case '+' => ptr.*++ _
      case '-' => ptr.*-- _
      case '.' => ptr.putchar _
      case ',' => ptr.getchar _
      case _   => ptr.noop _
    }
  }
}