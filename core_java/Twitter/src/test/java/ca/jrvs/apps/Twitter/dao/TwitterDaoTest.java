package ca.jrvs.apps.Twitter.dao;

import ca.jrvs.apps.Twitter.JsonParser;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.Twitter.model.Coordinates;
import ca.jrvs.apps.Twitter.model.Tweet;
import ca.jrvs.apps.Twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class TwitterDaoTest {

    private TwitterDao dao;
    static Tweet createdTweet;
    Tweet tweet;
    Coordinates coordinates;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
                accessToken, tokenSecret);
        dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() {
        //create a new tweet with coordinates
        String hashtags = "#testJarvisCreate";
        List<Float> coordinatesList = new ArrayList<>();
        coordinatesList.add(15.00f);
        coordinatesList.add(10.00f);

        coordinates = new Coordinates(coordinatesList, "Point");
        tweet = new Tweet("ashwin" + " " + System.currentTimeMillis(), coordinates);

        createdTweet = dao.create(tweet);
        System.out.println("string :" + createdTweet.toString());
    }

    /**
     * Test case for findPostById.
     */
    @Test
    public void findPostById() {
        create();
        Tweet tweetById = dao.findById(createdTweet.getId_str());
        assertEquals(createdTweet.getText(), tweetById.getText());
        assertNotNull(tweetById.getCoordinates());
        assertEquals(2, tweetById.getCoordinates().getCoordinates().size());
        assertEquals(createdTweet.getCoordinates().getCoordinates().get(0),
                tweetById.getCoordinates().getCoordinates().get(0));
        assertEquals(createdTweet.getCoordinates().getCoordinates().get(1),
                tweetById.getCoordinates().getCoordinates().get(1));
    }

    /**
     * Test case for deletePostById.
     */
    @Test
    public void deletePostById() throws JsonProcessingException {
        create();
        Tweet deletedTweet = dao.deleteById(createdTweet.getId_str());

        assertEquals(createdTweet.getText(), deletedTweet.getText());
        assertNotNull(deletedTweet.getCoordinates());
        assertEquals(2, deletedTweet.getCoordinates().getCoordinates().size());
        assertEquals(createdTweet.getCoordinates().getCoordinates().get(0),
                deletedTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(createdTweet.getCoordinates().getCoordinates().get(1),
                deletedTweet.getCoordinates().getCoordinates().get(1));
    }
}