/*
 * William Ee
 * CS 284-E
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

package rolodex;

import java.util.ArrayList;

public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	/*
	 * Constructor for Rolodex
	 * Initializes index array given alphabetical separators
	 * Each separator's prev / next values assigned to neighboring separators
	 * Initializes cursor on separator for A
	 */
	
	public Rolodex() {
		index = new Entry[26];
		index[0] = new Separator(null, null, 'A');

		for (int i = 1; i < 26; i++) // Creates new pointers
			index[i] = new Separator(index[i - 1], null, (char) (i + 65)); // Uses stored ASCII characters for alphabet
		
		for (int i = 0; i < 25; i++) // Sets succeeding pointers
			index[i].next = index[i + 1];

		index[25].next = index[0]; // Connects Z separator to A separator
		index[0].prev = index[25]; // Connects A separator to Z separator

		this.initializeCursor();
	}

	/*
	 * Checks Rolodex for given String name (returns true if so, false if not)
	 * Throws IllegalArgumentException if name has length 0
	 */
	
	public Boolean contains(String name) throws IllegalArgumentException {
		if (name.length() < 1 || name == null) // Checks given name for length 0 or null value
			throw new IllegalArgumentException('Name cannot be a string of length 0');
		
		String next = first == 'Z' ? 'A' : (char) (first + 1) + '';
		Entry current = index[first - 65];

		// Returns true if entry has matching name
		while (!current.getName().equals(next)) {
			if (current.getName().equals(name))
				return true;
			
			current = current.next;
		}
		
		return false; // Otherwise false
	}

	/*
	 * Returns number of Card objects in Rolodex
	 */
	
	public int size() {
		Entry current = index[0];
		int total = 0;
		
		// Adds to total count if current entry is a Card
		do {
			if (!current.isSeparator())
				total++;
			
			current = current.next;
			
		} while (current != index[0]);
		
		return total;
	}

	/*
	 * Returns ArrayList of all Cards with given String name
	 * Throws IllegalArgumentException if has length 0
	 * Throws IllegalArgumentException if no Cards exist in Rolodex with given name
	 */
	
	public ArrayList<String> lookup(String name) throws IllegalArgumentException {
		if (name.length() < 1 || name == null)
			throw new IllegalArgumentException('Name cannot be a string of length 0');

		name = name.strip().replaceAll('\n|\r', '');
		char first = Character.toUpperCase(name.charAt(0)); // Reduces name to uppercase character
		
		if (!this.contains(name))
			throw new IllegalArgumentException('lookup: name not found');

		ArrayList<String> holder = new ArrayList<String>();
		String next = first == 'Z' ? 'A' : (char) (first + 1) + '';
		Entry current = index[first - 65];

		// Adds toString of current entry to holder if name matches
		while (!current.getName().equals(next)) {
			if (current.getName().equals(name))
				holder.add(current.toString());
			
			current = current.next;
		}
		
		return holder;
	}

	public String toString() {
		Entry current = index[0];

		StringBuilder b = new StringBuilder();
		while (current.next!=index[0]) {
			b.append(current.toString()+"\n");
			current=current.next;
		}
		b.append(current.toString()+"\n");
		return b.toString();
	}

	/*
	 * Adds Card with String name and String cell to Rolodex
	 * Throws IllegalArgumentException if Card with equivalent fields already exists in Rolodex
	 * Throws IllegalArgumentException if name and/or cell have length 0
	 */
	
	public void addCard(String name, String cell) throws IllegalArgumentException {
		if (name.length() < 1 || name == null)
			throw new IllegalArgumentException('Name cannot be a string of length 0');
		if (cell.length() < 1 || cell == null)
			throw new IllegalArgumentException('Cell cannot be a string of length 0');

		Entry addedCard = new Card(null, null, name, cell);
		ArrayList<String> currentCards;
		
		try { // Finds all current instances of String name
			currentCards = lookup(name);
		} catch (IllegalArgumentException e) {
			currentCards = new ArrayList<String>();
		}

		for (String card : currentCards) // Checks for duplicate entries
			if (card.equals(addedCard.toString()))
				throw new IllegalArgumentException('addCard: duplicate entry');

		String next = first == 'Z' ? 'A' : (char) (first + 1) + '';
		Entry current = index[first - 65];

		while (!current.next.getName().equals(next)) {
			if (name.compareTo(current.getName()) >= 0 && name.compareTo(current.next.getName()) <= 0)
				break;
			
			current = current.next;
		}

		addedCard.prev = current;
		addedCard.next = current.next;
		current.next.prev = addedCard;
		current.next = addedCard;
	}

	/*
	 * Removes Card with String name and String cell from Rolodex
	 * Throws IllegalArgumentException if no Card with equivalent fields exists
	 * Throws IllegalArgumentException if name and/or cell have length 0
	 */
	
	public void removeCard(String name, String cell) {
		if (name.length() < 1 || name == null)
			throw new IllegalArgumentException('Name cannot be a string of length 0');
		if (cell.length() < 1 || cell == null)
			throw new IllegalArgumentException('Cell cannot be a string of length 0');

		name = name.strip().replaceAll('\n|\r', '');
		cell = cell.strip().replaceAll('\n|\r', '');
		
		char first = Character.toUpperCase(name.charAt(0));
		
		if (!this.contains(name))
			throw new IllegalArgumentException('removeCard: name does not exist');

		String cardLine = 'Name: ' + name + ', Cell: ' + cell;
		String next = first == 'Z' ? 'A' : (char) (first + 1) + '';
		Entry current = index[first - 65];

		while (!current.getName().equals(next)) {
			if (current.toString().equals(cardLine)) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
				return;
			}
			current = current.next;
		}
		
		// Throws IllegalArgumentException for nonexistent corresponding cell if no other condition applies
		throw new IllegalArgumentException('removeCard: cell for that name does not exist');
	}

	/*
	 * Removes all Cards with String name from Rolodex
	 * Throws IllegalArgumentException if name has length 0
	 * Throws IllegalArgumentException if no Card with given name exists
	 */
	
	public void removeAllCards(String name) {
		if (name.length() < 1 || name == null)
			throw new IllegalArgumentException('Name cannot be a string of length 0');

		name = name.strip().replaceAll(''\n|\r', '');
		char first = Character.toUpperCase(name.charAt(0));
		
		if (!this.contains(name))
			throw new IllegalArgumentException('removeAllCards: name does not exist');

		String next = first == 'Z' ? 'A' : (char) (first + 1) + '';
		Entry current = index[first - 65];

		// Resets pointers of current entry if name matches
		while (!current.getName().equals(next)) {
			if (current.getName().equals(name)) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
			}
			current = current.next;
		}
	}

	/*
	 * Resets position of cursor to separator for A
	 */
	
	public void initializeCursor() {
		cursor = index[0];
	}

	/*
	 * Sets position of cursor to next separator
	 */
	
	public void nextSeparator() {
		int separator = Character.toUpperCase(cursor.getName().charAt(0)) - 65;
		
		cursor = index[++separator % 26];
	}

	/*
	 * Sets position of cursor to next entry
	 */
	
	public void nextEntry() {
		cursor = cursor.next;
	}

	/*
	 * Returns toString of cursor's current entry
	 */
	
	public String currentEntryToString() {
		return cursor.toString();
	}
}