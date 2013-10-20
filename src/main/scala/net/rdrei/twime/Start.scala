package net.rdrei.twime

import com.google.common.collect.Lists
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.endpoint.UserstreamEndpoint
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import com.twitter.hbc.twitter4j.v3.Twitter4jUserstreamClient
import java.util.concurrent.{Executors, LinkedBlockingQueue}
import twitter4j.UserStreamListener

object Start {
  val MSG_START_SIZE = 100
  val THREAD_COUNT = 2

  def main(args: Array[String]) {
    val endpoint = new UserstreamEndpoint
    val msgQueue = new LinkedBlockingQueue[String](MSG_START_SIZE)

    endpoint.allReplies(true)
    endpoint.withFollowings(false)
    endpoint.addQueryParameter(Constants.WITH_PARAM, Constants.WITH_USER)

    val auth = new OAuth1(TwitterCredentials.CONSUMER_KEY,
      TwitterCredentials.CONSUMER_SECRET,
      TwitterCredentials.ACCESS_TOKEN,
      TwitterCredentials.ACCESS_SECRET)

    val client = new ClientBuilder()
      .name("Twime Bot v0.1")
      .hosts(Constants.USERSTREAM_HOST)
      .authentication(auth)
      .endpoint(endpoint)
      .processor(new StringDelimitedProcessor(msgQueue))
      .build

    val pool = Executors.newFixedThreadPool(THREAD_COUNT)
    val listeners = Lists.newArrayList[UserStreamListener](new ReplyListener)
    val t4jClient = new Twitter4jUserstreamClient(client, msgQueue, listeners, pool)

    CityDatabase.load()
    t4jClient.connect

    for (i <- 1 to THREAD_COUNT) {
      t4jClient.process
    }

    while (!t4jClient.isDone) {
      // There must be a better way for this ...
      Thread.sleep(1000)
    }

    client.stop
  }

}
