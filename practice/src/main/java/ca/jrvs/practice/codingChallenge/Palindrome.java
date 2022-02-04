package java.ca.jrvs.practice.codingChallenge;

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
	/**
	 * Big-O: O(n). Justification: String iterates till its length.
	 * 
	 * @param str to check Palindrome or not.
	 * @return true if its Palindrome;false otherwise.
	 */
	public boolean palindrome(String str) {
		int strLength = str.length();
		String reverseStr = null;
		for (int i = (strLength - 1); i >= 0; --i) {
			reverseStr = reverseStr + str.charAt(i);
		}

		return str.toLowerCase().equals(reverseStr.toLowerCase());
	}
}
