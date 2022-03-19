package ca.jrvs.apps.Twitter.util;

import ca.jrvs.apps.Twitter.model.Coordinates;
import ca.jrvs.apps.Twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {
	public static Tweet buildTweet(String text, float lon, float lat) {
		List<Float> coordinateList = new ArrayList<>();
		coordinateList.add(lon);
		coordinateList.add(lat);

		Coordinates coordinates = new Coordinates();
		Tweet tweet = new Tweet("laptop" + " " + System.currentTimeMillis(), coordinates);
		coordinates.setCoordinates(coordinateList);
		tweet.setCoordinates(coordinates);
		tweet.setText(text);
		return tweet;
	}

	public static Tweet buildTweet(String text) {
		Coordinates coordinates = new Coordinates();
		Tweet tweet = new Tweet("laptop" + " " + System.currentTimeMillis(), coordinates);
		tweet.setText(text);
		return tweet;
	}
}
