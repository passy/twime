package net.rdrei.twime

import twitter4j.Status
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class ReplyListener extends AbstractReplyListener {
  def onStatus(status: Status): Unit = {
    val screenName = status.getUser.getScreenName
    val time = DateTime.now.toString(DateTimeFormat.forPattern("d.M.y H:m:s"))
    T4JClient().updateStatus(s"@$screenName The current time is $time.")
  }
}
