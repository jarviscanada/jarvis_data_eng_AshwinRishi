package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the interface {@link JavaGrep}
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class JavaGrepImp implements JavaGrep {

	public Scanner scanner;

	private String regex;
	private String outFile;
	private String rootPath;
	private Pattern pattern;
	private Matcher matcher;

	private List<String> lines;
	private List<File> filesList;
	private List<String> matchedLines;
	private final static Logger logger = LoggerFactory.getLogger(JavaGrep.class);

	@Override
	public void process() throws IOException {
		matchedLines = new ArrayList<String>();

		listFiles(getRootPath());
		for (File file : filesList) {
			readLines(file);
			for (String line : lines) {
				if (containsPattern(line)) {
					matchedLines.add(line);
				}
			}
		}
		writeToFile(matchedLines);
	}

	@Override
	public List<File> listFiles(String directory) {
		try {
			filesList = Files.list(Paths.get(directory)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
		} catch (Exception e) {
			JavaGrepImp.logger.error("Reading files error:" + e);
		}

		return filesList;
	}

	@Override
	public List<String> readLines(File inputFile) throws IllegalArgumentException {
		lines = new ArrayList<String>();
		try {
			scanner = new Scanner(inputFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lines.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			JavaGrepImp.logger.error("Reading files error:" + e);
		}

		return lines;
	}

	@Override
	public boolean containsPattern(String line) {
		pattern = Pattern.compile(getRegex());
		matcher = pattern.matcher(line);

		return matcher.matches();
	}

	public boolean validateNotNullOrEmpty(String argument) {
		try {
			if (argument == null || argument.length() <= 0) {
				throw new IllegalArgumentException(argument + "cannot be null or empty");
			}
		} catch (Exception e) {
			JavaGrepImp.logger.error("Illegal String Argument:" + e);
		}

		return true;
	}

	@Override
	public void writeToFile(List<String> lines) throws IOException {
		System.out.println("writing lines:");

		for (String string : lines) {
			System.out.println(string);
		}
		FileWriter writer = new FileWriter(getOutFile());
		for (String string : lines) {
			writer.write(string + System.lineSeparator());
		}

		writer.close();
	}

	public void setRegex(String regex) {
		if (validateNotNullOrEmpty(regex)) {
			this.regex = regex;
		}
	}

	public String getOutFile() {
		return outFile;
	}

	public void setOutFile(String outFile) {
		if (validateNotNullOrEmpty(outFile)) {
			this.outFile = outFile;
		}
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		if (validateNotNullOrEmpty(rootPath)) {
			this.rootPath = rootPath;
		}
	}

	public String getRegex() {
		return regex;
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException("need 3 parameters to perform the operation:");
		}

		JavaGrep javagrep = new JavaGrepImp();
		javagrep.setRegex(args[0]);
		javagrep.setRootPath(args[1]);
		javagrep.setOutFile(args[2]);

		try {
			javagrep.process();
		} catch (Exception e) {
			JavaGrepImp.logger.error("could not process:" + e);
		}
	}
}
