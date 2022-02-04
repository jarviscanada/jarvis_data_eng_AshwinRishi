package java.ca.jrvs.practice.codingChallenge;

/**
 * <p>
 * Ticket:
 * {@link https://www.notion.so/jarvisdev/Two-Sum-4b453ae98a914f658db47381701b1450}
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class TwoSum {
	/**
	 * Big-O: O(n2). Justification: Have to iterate against each item, performing
	 * bubble search.
	 * 
	 * @param nums   on which the search of two sums will be performed.
	 * @param target to check the sums of two digits.
	 * @return the array of combination of digits equals to target;null otherwise.
	 */
	public int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] == target - nums[i]) {
					return new int[] { i, j };
				}
			}
		}
		return null;
	}
}
