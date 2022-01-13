package ca.jrvs.apps.Twitter;

import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class TwitterApplication {

	private String consumerKey = System.getenv("consumerKey");
	private String consumerSecret = System.getenv("consumerSecret");
	private String accessToken = System.getenv("accessToken");
	private String tokenSecret = System.getenv("tokenSecret");

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

		System.out.println("Http Request headers");
		Arrays.stream(request.getAllHeaders()).forEach(System.out::println);
		httpClient = HttpClientBuilder.create().build();
		try {
			response = httpClient.execute(request);
			System.out.println("ouput:" + EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		TwitterApplication twitterApplication = new TwitterApplication();
		twitterApplication.environmentalValues();
	}
}
