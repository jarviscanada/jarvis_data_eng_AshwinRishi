package ca.jrvs.apps.Twitter.dao.helper;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

public class TwitterHttpHelper implements HttpHelper{
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey,String consumerSecret, String accessToken,String tokenSecret){
        consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
        consumer.setTokenWithSecret(accessToken,tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }
    @Override
    public HttpResponse httpPost(URI uri) {
        return null;
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        return null;
    }
}
