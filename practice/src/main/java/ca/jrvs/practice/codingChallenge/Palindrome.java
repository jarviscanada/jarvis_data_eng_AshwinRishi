package ca.jrvs.practice.codingChallenge;

/**
 * <p>
 * Ticket:
 * {@link https://www.notion.so/jarvisdev/Valid-Palindrome-8cb643954a404807849e82baa8450407}
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class Palindrome {
	public void palindrome(String str, String reverseStr) {
		int strLength = str.length();

		for (int i = (strLength - 1); i >= 0; --i) {
			reverseStr = reverseStr + str.charAt(i);
		}

		if (str.toLowerCase().equals(reverseStr.toLowerCase())) {
			System.out.println(str + " is a Palindrome String.");
		} else {
			System.out.println(str + " is not a Palindrome String.");
		}
	}
}
