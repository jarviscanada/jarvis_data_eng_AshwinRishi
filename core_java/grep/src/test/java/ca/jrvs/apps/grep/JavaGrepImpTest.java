package ca.jrvs.apps.grep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit Test for the implementation {@link JavaGrep}
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 *
 * @author Ashwin Rishi
 *
 */
public class JavaGrepImpTest {

	private JavaGrep javaGrep = new JavaGrepImp();
	private String regex = "alpha";
	private String outFile = "./out/grep";
	private String rootPath = "./data/txt/";
	private List<File> expectedFiles, actualfiles;
	private List<String> resultLines;
	private File file = new File("./data/txt/shakespeare.txt");
	private List<String> lines = new ArrayList<String>() {
		{
			add("Shakespeare");
		}
	};
	private String[] args;

	@Test
	public void testContainsPattern() {
		javaGrep.setRegex("alpha");
		assertEquals(javaGrep.containsPattern(regex), true);
	}

	@Test
	public void testSetters() {

		javaGrep.setRegex(regex);
		javaGrep.setOutFile(outFile);
		javaGrep.setRootPath(rootPath);

		assertEquals(regex, javaGrep.getRegex());
		assertEquals(rootPath, javaGrep.getRootPath());
		assertEquals(outFile, javaGrep.getOutFile());

		assertThrows(IllegalArgumentException.class, () -> javaGrep.setRegex(""));
		assertThrows(IllegalArgumentException.class, () -> javaGrep.setRegex(null));
		assertThrows(IllegalArgumentException.class, () -> javaGrep.setOutFile(""));
		assertThrows(IllegalArgumentException.class, () -> javaGrep.setOutFile(null));
		assertThrows(IllegalArgumentException.class, () -> javaGrep.setRootPath(""));
		assertThrows(IllegalArgumentException.class, () -> javaGrep.setRootPath(null));
	}

	@Test
	public void testListFiles() {

		expectedFiles = new ArrayList<File>();
		expectedFiles.add(file);
		javaGrep.setRootPath(rootPath);

		actualfiles = javaGrep.listFiles(rootPath);
		assertEquals(expectedFiles, actualfiles);
	}

	@Test
	public void testIOExceptionOnProcess() {
		javaGrep.setRootPath("null");
		assertThrows(IOException.class, () -> javaGrep.process());
	}

	@Test
	public void testReadLines() throws FileNotFoundException {
		File fileTest = new File("./data/txt/shakespeare.png");
		assertThrows(FileNotFoundException.class, () -> javaGrep.readLines(fileTest));

		int resultLines = javaGrep.readLines(file).size();
		assertEquals(new BigDecimal(124456), new BigDecimal(resultLines));
	}

	@Test
	public void testWriteToFiles() throws IOException {
		javaGrep.setOutFile(outFile);

		javaGrep.writeToFile(lines);
		int resultLines = javaGrep.readLines(file).size();
		assertEquals(new BigDecimal(124456), new BigDecimal(resultLines));
	}

	@Test
	public void testProcess() {
		javaGrep.setRootPath(rootPath);
		assertThrows(IOException.class, () -> javaGrep.process());
	}

	@Test
	public void testMain() throws IOException {
		args = new String[] { "test", "test2" };

		assertThrows(IllegalArgumentException.class, () -> JavaGrepImp.main(args));
		args = new String[] { "Shakespeare", "./data/txt/", "./out/grep.txt" };
		JavaGrepImp.main(args);

		File outFile = new File("./out/grep.txt");
		resultLines = javaGrep.readLines(outFile);
		assertEquals(lines, resultLines);
	}
}
