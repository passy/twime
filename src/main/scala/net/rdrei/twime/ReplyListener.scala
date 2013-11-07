package net.rdrei.twime

import twitter4j.{StatusUpdate, Status}
import org.joda.time.{DateTimeZone, DateTime}
import org.joda.time.format.DateTimeFormat

class ReplyListener extends AbstractReplyListener {
  private val TIME_FORMAT = "d.M.y HH:mm:ss"

  def onStatus(status: Status): Unit = {
    if (!isInteresting(status))
      return

    val update = handleTimeZoneQuery(status).getOrElse(handleUnspecifiedQuery(status))
    T4JClient().updateStatus(update)
  }

  private def isInteresting(status: Status): Boolean =
    status.getUser.getScreenName != "twimebot"

  private def handleTimeZoneQuery(status: Status): Option[StatusUpdate] = {
    val text = status.getText
    val pattern = "(?i)(?:(what is)|(what's)) the time in ([\\w\\s,./]+)\\??$" r

    pattern.findFirstMatchIn(text).flatMap(tzMatch => makeTimeZoneStatusUpdate(status, tzMatch.group(3)))
  }

  private def getTimeZone(id: String): Option[DateTimeZone] =
    if (DateTimeZone.getAvailableIDs.contains(id)) Some(DateTimeZone.forID(id)) else None

  private def makeTimeZoneStatusUpdate(status: Status, timezoneName: String): Option[StatusUpdate] = {
    // zomg, this is teh awesome
    getTimeZone(timezoneName).orElse(CityDatabase.find(timezoneName).flatMap(getTimeZone)).flatMap(timezone => {
      val screenName = status.getUser.getScreenName
      val time = DateTime.now(timezone).toString(DateTimeFormat.forPattern(TIME_FORMAT))

      Some(new StatusUpdate(s"@$screenName The current time in $timezoneName is $time."))
    })
  }

  private def handleUnspecifiedQuery(status: Status): StatusUpdate = {
    val screenName = status.getUser.getScreenName
    val time = DateTime.now.toString(DateTimeFormat.forPattern(TIME_FORMAT))
    val update = new StatusUpdate(s"@$screenName The current time is $time.")
    update.setInReplyToStatusId(status.getId)

    update
  }
}
