package net.rdrei.twime

import twitter4j.{TwitterFactory, Twitter}
import twitter4j.conf.ConfigurationBuilder

object T4JClient {

  private val client : Option[Twitter] = None

  def apply() : Twitter = client match {
    case Some(twitter) => twitter
    case None => createClient
  }

  private def createClient() : Twitter = {
    val conf = new ConfigurationBuilder()
      .setDebugEnabled(true)
      .setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
      .setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
      .setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(TwitterCredentials.ACCESS_SECRET)
      .build

    new TwitterFactory(conf).getInstance
  }

}
