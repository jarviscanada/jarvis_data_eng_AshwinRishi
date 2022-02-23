package ca.jrvs.apps.Twitter.Service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.jrvs.apps.Twitter.dao.TwitterDao;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.Twitter.model.Tweet;
import ca.jrvs.apps.Twitter.util.TweetUtil;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@Ignore
public class ServiceImplIntTest {

  private Service service;

  @Before
  public void setUp() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);

    TwitterDao dao = new TwitterDao(httpHelper);
    service = new ServiceImpl(dao);
  }

  @Test
  public void shouldPostTweet() {
    Tweet tweetWillBePosted = TweetUtil.buildTweet("post tweet Int Testing " + System.currentTimeMillis(),
        30.0f, 31.0f);

    Tweet postTweet = service.postTweet(tweetWillBePosted);

    assertEquals(tweetWillBePosted.getText(), postTweet.getText());
    assertNotNull(postTweet.getCoordinates());
    assertEquals(2, postTweet.getCoordinates().getCoordinates().size());
    assertEquals(tweetWillBePosted.getCoordinates().getCoordinates().get(0),
        postTweet.getCoordinates().getCoordinates().get(0));
    assertEquals(tweetWillBePosted.getCoordinates().getCoordinates().get(1),
        postTweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void shouldDeleteTweet() {
    Tweet deleteTweet = TweetUtil.buildTweet(
        "Deleting post " + System.currentTimeMillis(), 50.0f, 51.0f);
   
    Tweet deletedTweet = service.postTweet(deleteTweet);
    String id = deletedTweet.getId_str();

    assertEquals(deletedTweet.getText(), id);
  }

}