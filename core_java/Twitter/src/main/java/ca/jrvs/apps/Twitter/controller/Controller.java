package ca.jrvs.apps.Twitter.controller;

import ca.jrvs.apps.Twitter.model.Tweet;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface Controller {

	/**
	 * Parse user argument and post a tweet by calling service classes
	 *
	 * @param args
	 * @return a posted tweet
	 * @throws IllegalArgumentException if args are invalid
	 */
	Tweet postTweet(String[] args);

	/**
	 * Parse user argument and search a tweet by calling service classes
	 *
	 * @param args
	 * @return a tweet
	 * @throws IllegalArgumentException if args are invalid
	 */
	Tweet showTweet(String[] args);

	/**
	 * Parse user argument and delete tweets by calling service classes
	 *
	 * @param args
	 * @return a list of deleted tweets
	 * @throws IllegalArgumentException if args are invalid
	 */
	List<Tweet> deleteTweet(String[] args);
}
