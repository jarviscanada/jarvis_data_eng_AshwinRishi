package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface that handles the following file operations:
 * <ul>
 * <li>ListFiles: To display the files available in the given directory</li>
 * <li>ReadLines: To read and display the lines in a given file</li>
 * <li>PatterSearch: To search a particular regex pattern for the given
 * string</li>
 * <li>WriteToFile: To write the given string to a particular file</li>
 * </ul>
 * 
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public interface JavaGrep {
	/**
	 * Performs a top level search of workflow.
	 * 
	 * @throws IOException if there is an invalid search or fail in search.
	 */
	void process() throws IOException;

	/**
	 * Fetches list of files available for the given directory.
	 * 
	 * @param rootDirectory on which the filesSearch to be performed.
	 * @return {@link List<File>} present in the given directories.
	 */
	List<File> listFiles(String directory);

	/**
	 * Reads lines for the given input file and displays all the lines from the
	 * file.
	 * 
	 * @param inputFile on which the read line operation will be performed.
	 * @return {@link List<String>} from the input file after performing the read
	 *         operation.
	 * @throws IllegalArgumentException if the given input file is not a file Type.
	 */
	List<String> readLines(File inputFile) throws IllegalArgumentException;

	/**
	 * Verifies the regex pattern to the user given line.
	 * 
	 * @param line on which the regex operation to be performed.
	 * @return true if given line matches with the regex;false otherwise.
	 */
	boolean containsPattern(String line);

	/**
	 * Writes into a new file from the user given data.
	 * 
	 * @param lines to be inserted to the new file.
	 * @throws IOException if the insertion to the new file fails.
	 */
	void writeToFile(List<String> lines) throws IOException;

	/**
	 * Initializes the rootPath from the parameter.
	 * 
	 * @param rootPath and this cannot be {@code null} or empty.
	 * @throws IllegalArgumentException if the argument is {@code null} or empty.
	 */
	void setRootPath(String rootPath);

	/**
	 * Initializes the regex from the parameter.
	 * 
	 * @param regex and this cannot be {@code null} or empty.
	 * @throws IllegalArgumentException if the argument is {@code null} or empty.
	 */
	void setRegex(String regex);

	/**
	 * Initializes the setOutFile from the parameter.
	 * 
	 * @param filename on which the operation {@link #writeToFile(List)} will be
	 *                 performed.
	 * @throws IOException if the write operation fails.
	 */
	void setOutFile(String fileName);

	/**
	 * @return rootPath of type String. This value will never be {@code null} or
	 *         empty.
	 */
	String getRootPath();

	/**
	 * @return regex of type String. This value will never be {@code null} or empty.
	 */
	String getRegex();

	/**
	 * @return files of type String. This value will never be {@code null} or empty.
	 */
	String getOutFile();

}
