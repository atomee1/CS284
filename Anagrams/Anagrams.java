package anagrams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// William Ee
// CS 284-E
// I pledge my honor that I have abided by the Stevens Honor System.

public class Anagrams {
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	public void buildLetterTable() {
		letterTable = new HashMap<Character, Integer>();
		char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for (int i = 0; i < alpha.length; i++) {
			letterTable.put(alpha[i], primes[i]);
		}
	}

	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	
	/** 
	 * @param s
	 */
	public void addWord(String s) {
		long hashCode = myHashCode(s);
		anagramTable.computeIfAbsent(hashCode, key -> new ArrayList<>()).add(s);
	}
	
	
	/** 
	 * @param s
	 * @return long
	 */
	public long myHashCode(String s) {
		long p = 1L;
		int i, val;
		char a;

		for (i = 0; i < s.length(); i++) {
			a = s.charAt(i);
			val = letterTable.get(a);
			p = p * val;
		}

		return p;
	}
	
	
	/** 
	 * @param s
	 * @throws IOException
	 */
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
		String strLine;

		while ((strLine = reader.readLine()) != null)   {
		  this.addWord(strLine);
		}
		reader.close();
	}
	
	
	/** 
	 * @return ArrayList<Entry<Long, ArrayList<String>>>
	 */
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		int max = 0;
	    ArrayList<Map.Entry<Long,ArrayList<String>>> list = new ArrayList<Map.Entry<Long,ArrayList<String>>>();

		for (Map.Entry<Long,ArrayList<String>> entry : anagramTable.entrySet()) {
			int v = entry.getValue().size();

			if (max < v) {
				list.clear();
				max = v;
				list.add(entry);
			} else if (max > v) {
				continue;
			} else if (max == v) {
				list.add(entry);
			}
		}

		return list;
	}
}
