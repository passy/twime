package net.rdrei.twime

import twitter4j.{StatusUpdate, Status}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class ReplyListener extends AbstractReplyListener {
  def onStatus(status: Status): Unit = {
    if (!isInteresting(status))
      return

    val screenName = status.getUser.getScreenName
    val time = DateTime.now.toString(DateTimeFormat.forPattern("d.M.y H:m:s"))
    val update = new StatusUpdate(s"@$screenName The current time is $time.")
    update.setInReplyToStatusId(status.getId)

    T4JClient().updateStatus(update)
  }

  private def isInteresting(status: Status): Boolean =
    status.getUser.getScreenName != "twimebot"
}
