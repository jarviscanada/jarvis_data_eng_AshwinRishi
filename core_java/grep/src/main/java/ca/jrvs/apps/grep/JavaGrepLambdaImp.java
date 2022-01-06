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
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation for the class {@link JavaGrepLamda}
 * 
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class JavaGrepLambdaImp extends JavaGrepImp {
	public Scanner scanner;

	private List<String> lines;
	private List<File> filesList;
	private final static Logger logger = LoggerFactory.getLogger(JavaGrep.class);

	@Override
	public List<File> listFiles(String directory) {
		try {
			filesList = Files.list(Paths.get(directory)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
		} catch (Exception e) {
			JavaGrepLambdaImp.logger.error("Reading files error:" + e);
		}

		return filesList;
	}

	@Override
	public List<String> readLines(File inputFile) throws FileNotFoundException {
		lines = new ArrayList<String>();

		try {
			Files.lines(new File(inputFile.toString()).toPath()).map(line -> line.trim())
					.filter(line -> !line.isEmpty()).forEach(line -> lines.add(line));
		} catch (Exception e) {
			throw new FileNotFoundException("read file error");
		}

		return lines;
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException("need 3 parameters to perform the operation:");
		}

		JavaGrepLambdaImp javagrep = new JavaGrepLambdaImp();
		javagrep.setRegex(args[0]);
		javagrep.setRootPath(args[1]);
		javagrep.setOutFile(args[2]);

		try {
			javagrep.process();
		} catch (Exception e) {
			JavaGrepLambdaImp.logger.error("could not process:" + e);
		}
	}
}
