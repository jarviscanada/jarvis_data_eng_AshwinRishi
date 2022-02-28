package ca.jrvs.apps.Twitter.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import ca.jrvs.apps.Twitter.JsonParser;
import ca.jrvs.apps.Twitter.Service.Service;
import ca.jrvs.apps.Twitter.Service.ServiceImpl;
import ca.jrvs.apps.Twitter.controller.Controller;
import ca.jrvs.apps.Twitter.controller.ControllerImpl;
import ca.jrvs.apps.Twitter.dao.CrdDao;
import ca.jrvs.apps.Twitter.dao.TwitterDao;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.Twitter.model.Tweet;

@Primary
@Component
public class TwitterCliApp {

	final Logger logger = LoggerFactory.getLogger(TwitterCliApp.class);
	public static final String ARGUMENTS = "Usage: TwitterCLIApp post|show|delete [options]";
	private Controller controller;

	@Autowired
	public TwitterCliApp(Controller controller) {
		this.controller = controller;
	}

	public static void main(String[] args) {
		String consumerKey = System.getenv("consumerKey");
		String consumerSecret = System.getenv("consumerSecret");
		String accessToken = System.getenv("accessToken");
		String tokenSecret = System.getenv("tokenSecret");

		HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
		CrdDao<Tweet, String> dao = new TwitterDao(httpHelper);
		Service service = new ServiceImpl(dao);
		Controller controller = new ControllerImpl(service);
		TwitterCliApp app = new TwitterCliApp(controller);

		try {
			app.run(args);
		} catch (Exception e) {
			app.logger.error(e.getMessage(), e);
		}
	}

	public void run(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException(ARGUMENTS);
		}
		switch (args[0].toLowerCase()) {
		case "post":
			printTweet(controller.postTweet(args));
			break;
		case "show":
			printTweet(controller.showTweet(args));
			break;
		case "delete":
			controller.deleteTweet(args).forEach(this::printTweet);
			break;
		default:
			throw new IllegalArgumentException(ARGUMENTS);
		}
	}

	private void printTweet(Tweet tweet) {
		try {
			System.out.println(JsonParser.toJson(tweet, true, false));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to convert Tweet to String", e);
		}
	}
}
