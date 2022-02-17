package ca.jrvs.apps.Twitter.dao;

import ca.jrvs.apps.Twitter.JsonParser;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class TwitterDao implements CrdDao<Tweet, String> {
    private static final String BASE_URI = "https://api.twitter.com";
    private static final String POST_URI = "/1.1/statuses/update.json";
    private static final String GET_URI = "/1.1/statuses/show.json";
    private static final String DELETE_URI = "/1.1/statuses/destroy.json";

    // URI symbols
    private static final String QUERY = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // HTTP response code
    private static final int HTTP_OK = 200;

    private final HttpHelper httpHelper;
    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);
    private final PercentEscaper percentEscaper = new PercentEscaper("", false);

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet) {
        URI uri;
        HttpResponse response;
        try {
            uri = formatPostUri(tweet);
            response = httpHelper.httpPost(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("Usage: syntax exception : " + e);
        }

        return parseResponse(response);
    }

    private URI formatPostUri(Tweet tweet) throws URISyntaxException {
        List<Float> coordinates = tweet.getCoordinates().getCoordinates();
        return new URI(BASE_URI + POST_URI + QUERY
                + "status" + EQUAL + percentEscaper.escape(tweet.getText())
                + AMPERSAND + "long" + EQUAL + percentEscaper.escape(String.valueOf(coordinates.get(0)))
                + AMPERSAND + "lat" + EQUAL + percentEscaper.escape(String.valueOf(coordinates.get(1))));
    }

    protected Tweet parseResponse(HttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        if (status != TwitterDao.HTTP_OK) {
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        HttpEntity httpEntity = response.getEntity();
        String tweetStr;
        if (httpEntity == null) {
            throw new RuntimeException("Empty Entity body");
        } else {
            try {
                tweetStr = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert Entity to String");
            }
        }

        try {
            return JsonParser.toObjectFromJson(tweetStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to Tweet Object", e);
        }
    }

    @Override
    public Tweet findById(String id) {
        URI uri;
        HttpResponse response;
        try {
            uri = new URI(BASE_URI + GET_URI + QUERY + "id" + EQUAL + id);
            response = httpHelper.httpGet(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("Usage: Tweet ID not found:" + e);
        }
        return parseResponse(response);
    }

    @Override
    public Tweet deleteById(String tweetIdD) {
        URI uri;
        HttpResponse response;
        try {
            uri = new URI(BASE_URI + DELETE_URI + QUERY + "id" + EQUAL + tweetIdD);
            response = httpHelper.httpPost(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("Tweet ID not found:" + e);
        }
        return parseResponse(response);
    }
}

