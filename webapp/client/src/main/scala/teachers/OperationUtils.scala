package client.teachers

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

/**
  * Created by loick on 27/08/17.
  */
object OperationUtils   {



  def getMultiplicationSteps(x:Double, y:Double) : Seq[String] = {

    var steps = Seq[String]()
    var i = 1
    println(s"  ${x}")
    println(s"* ${y}")
    println("------------")
    val point_index = x.toString.split("\\.").last.size + y.toString.split("\\.").last.size

    y.toString.filter(_.isDigit).reverse.foreach{ digit =>
      val step = x.toString.filter(_.isDigit).toInt*digit.toString.toInt*i

      val strstep = (0 until Math.max((Math.min(x.toString.filter(_.isDigit).length,y.toString.filter(_.isDigit).length)-step.toString.length),0)).map(e=>"0").mkString("")+step.toString

      steps :+= strstep.slice(0, strstep.size-point_index).mkString("")+(if(strstep.slice(0, strstep.size-point_index).nonEmpty)"." else "") +strstep.slice(strstep.size-point_index,strstep.size).mkString("")
      println(s"+ ${strstep.slice(0, strstep.size-point_index+1).mkString("")+"."+strstep.slice(strstep.size-point_index,strstep.size).mkString("")}")
      i*=10
    }
    println("------------")
    println(s"  ${x*y}")

    return steps
  }

  def computeDigitPosition(x:Double,y:Double, initial_xoffset:Int, initial_yoffset:Int, font_size:Int) : Seq[(String,Int,Int)] = {

    var list = Seq[(String,Int,Int)]()

    var current_x_offset = 0
    var current_y_offset = 0

    x.toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    current_x_offset=0
    current_y_offset+=font_size

    y.toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    list :+= ("x", initial_xoffset-Math.max(x.toString.length, y.toString.length)*font_size, initial_yoffset-current_y_offset)


    list :+= ("-----", initial_xoffset, initial_yoffset-current_y_offset-12)
    current_x_offset=0
    current_y_offset+=font_size+10

    getMultiplicationSteps(x,y).foreach{ step =>

      step.toString.reverse.foreach{ digit =>
        list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
        current_x_offset+=font_size
      }

      current_x_offset=0
      current_y_offset+=font_size
    }

    list :+= ("-----", initial_xoffset, initial_yoffset-current_y_offset+8)

    current_y_offset+=10

    (x*y).toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    return list
  }

}
