import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.endpoint.UserstreamEndpoint
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import java.util.concurrent.LinkedBlockingQueue

object start {
  val MSG_START_SIZE = 100

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

    client.connect

    while (!client.isDone) {
      val msg = msgQueue.take
      println("msg: " + msg)
    }
  }

}
