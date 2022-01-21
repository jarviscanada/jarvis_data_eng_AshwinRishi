package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Ticket:
 * {@link https://www.notion.so/jarvisdev/How-to-compare-two-maps-663ad0d229b94917a2870c183649a323}
 * </p>
 * <p>
 * copyright & copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class CompareMaps<K, V> {
	
	/**
	 * Big-O: O(1). Justification: it's an arithmetic operation.
	 * 
	 * @param K is of generic data type on which the comparison will be performed.
	 * @param V is of generic data type on which the comparison will be performed.
	 * @return {@code true} if the K & V 0f two maps are equals;false otherwise.
	 */
	public boolean validateMaps(Map<K, V> firstMap, Map<K, V> secondMap) {
		return firstMap.equals(secondMap);
	}
	
	public static void main(String[] args) {
		HashMap<Integer, String> map1 = new HashMap();
		HashMap<Integer, String> map2 = new HashMap();
		
		map1.put(1, "ashwin");
		map2.put(1, "ashwin");
		
		CompareMaps compareMaps = new CompareMaps();
		System.out.println(compareMaps.validateMaps(map1, map2));
	}
}
