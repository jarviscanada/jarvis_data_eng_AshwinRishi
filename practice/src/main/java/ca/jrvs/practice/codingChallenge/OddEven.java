package java.ca.jrvs.practice.codingChallenge;

/**
 * <p>
 * Ticket:
 * {@link https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-6daf27e895a142b283f6a7cd3d647bc5}
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class OddEven {
	/**
	 * Big-O: O(1). Justification: it's an arithmetic operation.
	 * 
	 * @param number on which Even or Odd operation to be performed.
	 * @return odd if the number is not an divisible by 2; even otherwise.
	 */
	public String validateOddEven(int number) {
		return number % 2 == 0 ? "even" : "odd";
	}
}
