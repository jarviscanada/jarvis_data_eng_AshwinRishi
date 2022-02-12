package ca.jrvs.apps.Twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.InvalidPropertiesFormatException;

public class TwitterHttpHelper implements HttpHelper {
    private OAuthConsumer consumer;
    private HttpClient httpClient;
    private HttpResponse response;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
    }

    @Override
    public HttpResponse httpPost(URI uri) throws IOException {
        HttpPost postRequest = new HttpPost(uri);
        try {
            consumer.sign(postRequest);
            httpClient = HttpClientBuilder.create().build();
        } catch (Exception e) {
            throw new InvalidPropertiesFormatException("e:" + e);
        }
        return httpClient.execute(postRequest);
    }

    @Override
    public HttpResponse httpGet(URI uri) throws IOException {
        HttpGet getRequest = new HttpGet(uri);
        try {
            consumer.sign(getRequest);
            httpClient = HttpClientBuilder.create().build();
        } catch (Exception e) {
            throw new InvalidPropertiesFormatException("e:" + e);
        }
        return httpClient.execute(getRequest);
    }
}
