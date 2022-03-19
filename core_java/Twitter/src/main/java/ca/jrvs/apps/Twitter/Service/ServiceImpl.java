package ca.jrvs.apps.Twitter.Service;

import ca.jrvs.apps.Twitter.dao.CrdDao;
import ca.jrvs.apps.Twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
	private final int maximumLength = 140;
	private CrdDao<Tweet, String> dao;

	@Autowired
	public ServiceImpl(CrdDao<Tweet, String> dao) {
		this.dao = dao;
	}

	@Override
	public Tweet postTweet(Tweet tweet) {
		int lengthTweet = tweet.getText().length();
		float lon = tweet.getCoordinates().getCoordinates().get(0);
		float lat = tweet.getCoordinates().getCoordinates().get(1);

		if ((lon <= -180 || lon >= 80) && (lat <= -90 || lat >= 90)) {
			throw new IllegalArgumentException("Incorrect lat & long values");
		}

		if (lengthTweet > maximumLength) {
			throw new IllegalArgumentException("Tweets should not exceed more than 140chars");
		}

		return dao.create(tweet);
	}

	@Override
	public Tweet showTweet(String id, String[] fields) {
		if (id != null && (id.matches("[0-9]+") && id.length() >= 19)) {
			return dao.findById(id);
		}
		throw new IllegalArgumentException("Invalid Id: " + id);
	}

	@Override
	public List<Tweet> deleteTweets(String[] ids) {
		List<Tweet> deletedTweets = new ArrayList<>();
		for (String id : ids) {
			if (!id.matches("[0-9]+") && (id.length() < 19)) {
				throw new IllegalArgumentException("Incorrect Id");
			} else {
				deletedTweets.add(dao.deleteById(id));
			}
		}
		return deletedTweets;
	}
}
