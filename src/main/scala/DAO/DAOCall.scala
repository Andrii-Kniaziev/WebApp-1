package DAO

import java.sql.{Connection, DriverManager}

object DAOCall {
  // connect to the database named "requests" on port 5432 of localhost
  val url = "jdbc:postgresql://172.18.0.2:5432/requests"
  val username = "postgres"
  val password = "1111"
  var connection: Connection = _

  def registerCall(): Boolean = {
    try {
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeUpdate(Constants.INSERT_PING_REQUEST)
      true
    } catch {
      case e: Exception => e.printStackTrace
      false
    }
  }
}