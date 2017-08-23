package test

import java.io.FileWriter

import org.apache.commons.lang3.StringEscapeUtils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by loick on 20/08/17.
  */
object VocabularyTrainerSplitter {

  def main(args: Array[String]): Unit = {

    val final_map = mutable.HashMap[Int, (String, String, Int)]()
    val lang = "es"


    for(i <- 1 to 5) {
      val regex = "\\{(.*)(\\},|\\}\\])".r
      val text = readFile(s"/home/loick/course.${lang}.${i}.fr.0.js")

      val hash1 = mutable.HashMap[Int, String]()
      val hash2 = mutable.HashMap[Int, String]()

      text
        .split("\"wns\":")
        .map(e => "\"wns\":" + e)
        .flatMap(e => e.split("\"wls\":").map(k => if (k.contains("\"wns\":")) k else "\"wls\":" + k))
        .filter(_.contains("word"))
        .foreach { el =>


          el.split("(\\[\\{|\\},\\{|\\}\\])").filter(_.contains("word")).foreach { word =>
            try {
              val w_value = word.split("""("word":"|,"wid":"|")""").filter(_.nonEmpty)(0)
              val n_value = word.split("""("word":"|,"wid":"|")""").filter(_.nonEmpty)(1).toInt
              if (el.contains("\"wns\":")) {
                hash1.put(n_value, w_value)
              } else {
                hash2.put(n_value, w_value)
              }
            } catch {
              case e: Throwable =>
            }
          }
        }


      hash1.foreach { case (key1, value1) =>
        if (hash2.contains(key1)) {
          final_map.put(key1, (StringEscapeUtils.unescapeJava(value1), StringEscapeUtils.unescapeJava(hash2(key1)), i))
        }
      }
    }

    val strbuilder = new mutable.StringBuilder()
    final_map.toSeq.sortBy(e=> (e._2._3, e._2._2)).foreach{ case (id,(french,other_langage,level)) =>

        strbuilder.append(s"${other_langage};${french};${level};${id}\n")

    }

    writeFile(s"/home/loick/file_${lang}.csv", strbuilder.toString())
  }

  def writeFile(path: String, content: String, appendToFile: Boolean = false) : Unit = {
    var fw = new FileWriter(path, appendToFile);
    fw.write(content);
    fw.close()
  }

  def readFile(path: String) : String ={
    return  scala.io.Source.fromFile(path, "UTF-8").mkString
  }
}
