package net.rdrei.twime

import twitter4j.Status

class ReplyListener extends AbstractReplyListener {
  def onStatus(status: Status): Unit = {
    println("Interesting status, yo!" + status.toString)
  }
}
