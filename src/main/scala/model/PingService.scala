package model

import java.util.Date
import DAO.DAOCall

object PingService {
    def callInfo(): Unit = {
        DAOCall.registerCall
    }
}