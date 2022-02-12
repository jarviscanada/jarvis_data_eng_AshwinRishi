package ca.jrvs.apps.Twitter;

import ca.jrvs.apps.Twitter.dao.TwitterDao;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.dao.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@SpringBootApplication
public class TwitterApplication {

    private final static String consumerKey = System.getenv("consumerKey");
    private final static String consumerSecret = System.getenv("consumerSecret");
    private final static String accessToken = System.getenv("accessToken");
    private final static String tokenSecret = System.getenv("tokenSecret");

    private CommonsHttpOAuthConsumer consumer;
    private PercentEscaper percentEscaper;
    private HttpClient httpClient;
    private HttpResponse response;

    public void environmentalValues() {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);

        String status = "Testing from jarvis:";
        percentEscaper = new PercentEscaper("", false);
        HttpPost request = new HttpPost(
                "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status));

        try {
            consumer.sign(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Arrays.stream(request.getAllHeaders()).forEach(System.out::println);
        httpClient = HttpClientBuilder.create().build();
        try {
            response = httpClient.execute(request);
            System.out.println("ouput:" + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        TwitterApplication twitterApplication = new TwitterApplication();
//        twitterApplication.environmentalValues();

        HttpHelper httpHelper = new TwitterHttpHelper(twitterApplication.consumerKey, twitterApplication.consumerSecret, twitterApplication.accessToken, twitterApplication.tokenSecret);
//        HttpResponse httpResponse = twitterHttpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=ashwinrishipj&count=2"));
//        System.out.println(EntityUtils.toString(httpResponse.getEntity()));

        TwitterDao twitterDao = new TwitterDao(httpHelper);


    }
}
