package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

	private List<String> lines;
	private List<File> filesList;
	private List<String> matchedLines;
	private final static Logger logger = LoggerFactory.getLogger(JavaGrep.class);

	@Override
	public void process() throws IOException {
		matchedLines = new ArrayList<String>();

		listFiles(getRootPath());
		try {
			for (File file : filesList) {
				for (String line : readLines(file)) {
					if (containsPattern(line)) {
						matchedLines.add(line);
					}
				}
			}
		} catch (Exception e) {
			throw new IOException("Error in reading files" + e);
		}
		writeToFile(matchedLines);
	}

	@Override
	public List<File> listFiles(String directory) throws FileNotFoundException {
		try {
			filesList = Files.list(Paths.get(directory)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new FileNotFoundException("Error in listing files" + e);
		}

		return filesList;
	}

	@Override
	public List<String> readLines(File inputFile) throws FileNotFoundException {
		lines = new ArrayList<String>();
		try {
			scanner = new Scanner(inputFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lines.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("file not found" + e);
		}

		return lines;
	}

	@Override
	public boolean containsPattern(String line) {
		return line.contains(getRegex());
	}

	public String validateNotNullOrEmpty(String argument) {
		if (argument == null || argument.length() <= 0) {
			throw new IllegalArgumentException(argument + "cannot be null or empty");
		}

		return argument;
	}

	@Override
	public void writeToFile(List<String> lines) throws IOException {
		FileWriter writer = new FileWriter(getOutFile());
		try {
			for (String string : lines) {
				writer.write(string + System.lineSeparator());
				JavaGrepImp.logger.info("inserted a line " + string + " at " + getOutFile());
			}
		} catch (Exception e) {
			throw new IOException("could not write to a file" + e);
		}

		writer.close();
	}

	public void setRegex(String regex) {
		this.regex = validateNotNullOrEmpty(regex);
	}

	public String getOutFile() {
		return outFile;
	}

	public void setOutFile(String outFile) {
		this.outFile = validateNotNullOrEmpty(outFile);
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = validateNotNullOrEmpty(rootPath);
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
