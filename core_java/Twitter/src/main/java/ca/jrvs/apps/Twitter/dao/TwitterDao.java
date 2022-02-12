package ca.jrvs.apps.Twitter.dao;

import ca.jrvs.apps.Twitter.JsonParser;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.model.Tweet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class TwitterDao implements CrdDao<Tweet,String> {
    private static final String apiBaseUri = "https://api.twitter.com";
    private static final String postPath = "/1.1/statuses/update.json";
    private static final String showPath = "/1.1/statuses/show.json";
    private static final String deletePath = "/1.1/statuses/destroy";

    private static final String querySym = "?";
    private static final String ampersand = "&";
    private static final String equal = "=";
    private static final String status = "status";

    private static final int httpOk = 200;
    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);
    private HttpHelper httpHelper;
    private URI uri;
    private HttpResponse response;
    private HttpEntity httpEntity;
    private String tweetStr;

    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    private Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        httpEntity = response.getEntity();

        if (httpEntity == null) {
            throw new RuntimeException("Twitter Entity response is null");
        }

        try {
            tweetStr = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            throw new RuntimeException("Entity response to String conversion failed:");
        }

        try {
            return JsonParser.toObjectFromJson(tweetStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to Tweet Object", e);
        }
    }

    private URI getPostUri(Tweet tweet) {
        try {
            return new URI(apiBaseUri + postPath + querySym + status + equal + tweet.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception in creating URI" + e);
        }
    }

    @Override
    public Tweet create(Tweet tweet) {
        try {
            uri = getPostUri(tweet);
            response = httpHelper.httpPost(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return parseResponseBody(response, httpOk);
    }

    @Override
    public Tweet findById(String s) {
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }
}
