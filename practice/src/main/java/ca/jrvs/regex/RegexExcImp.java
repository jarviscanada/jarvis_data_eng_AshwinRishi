package ca.jrvs.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Implements the interface {@link RegexExc}.
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 *
 */
public class RegexExcImp implements RegexExc {
	private String regex;
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public boolean matchJpeg(String fileName) {
		regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";

		if (fileName == null || fileName.length() <= 0) {
			return false;
		}
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(fileName);

		return matcher.matches();
	}

	@Override
	public boolean matchIp(String ip) {
		String ipRange = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
		regex = ipRange + "\\." + ipRange + "\\." + ipRange + "\\." + ipRange;

		if (ip == null || ip.length() <= 0) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);

		return m.matches();
	}

	@Override
	public boolean isEmptyLine(String line) {
		if (line == null || line.length() <= 0) {
			return true;
		}
		return false;
	}
}
