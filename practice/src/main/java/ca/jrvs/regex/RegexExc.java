package ca.jrvs.regex;

/**
 * <p>
 * interface to validate the Regex expression.
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public interface RegexExc {
	/**
	 * Checks whether the input FileType is of image file or not.
	 * 
	 * @param fileName is used to validate the FileType.
	 * @return true if the fileType is JPEG; false otherwise.
	 */
	boolean matchJpeg(String fileName);

	/**
	 * validates whether the given IP address is in the range or not.
	 * 
	 * @param ip is used to validate the IP address.
	 * @return true if the IP Address is valid;false otherwise.
	 */
	boolean matchIp(String ip);

	/**
	 * checks whether the given input is empty or not.
	 * 
	 * @param line is used to check the entered string.
	 * @return true if the string is empty;false otherwise.
	 */
	boolean isEmptyLine(String line);
}
