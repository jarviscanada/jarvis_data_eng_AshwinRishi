package ca.jrvs.apps.Twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.jrvs.apps.Twitter.Service.Service;
import ca.jrvs.apps.Twitter.model.Tweet;
import ca.jrvs.apps.Twitter.util.TweetUtil;

@Component
public class ControllerImpl implements Controller {

	private static final String coordinateSeparation = ":";
	private static final String COMMA = ",";
	private float lon;
	private float lat;
	private String text;
	private String coordinate;
	private String[] coordinates;
	private Service service = null;

	@Autowired
	public ControllerImpl(Service service) {
		this.service = service;
	}

	@Override
	public Tweet postTweet(String[] args) {

		if (args.length != 3) {
			throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"text\" \"latitude:longitude\"");
		}

		float lon;
		float lat;
		text = args[1];
		coordinate = args[2];
		coordinates = coordinate.split(coordinateSeparation);

		if (coordinates.length != 2 || text.isEmpty()) {
			throw new IllegalArgumentException(
					"Invalid format\nUSAGE: TwitterCLIApp post \"text\" \"latitude:longitude\"");
		}

		try {
			lon = Float.parseFloat(coordinates[0]);
			lat = Float.parseFloat(coordinates[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid location format. Kindly recheck", e);
		}

		Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
		return service.postTweet(postTweet);
	}

	@Override
	public Tweet showTweet(String[] args) {
		String[] fields = null;
		if (args.length < 2) {
			throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"id\" \"options..\"");
		}
		String id = args[1];

		if (args.length > 2) {
			String field = args[2];
			fields = field.split(COMMA);
		}

		return service.showTweet(id, fields);
	}

	@Override
	public List<Tweet> deleteTweet(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"id\" \"options..\"");
		}
		String id = args[1];
		String[] ids = id.split(COMMA);
		return service.deleteTweets(ids);
	}

}