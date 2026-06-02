/*
 * William Ee
 * CS 284-E
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

import rolodex.Rolodex;

public class rolodexTest {

	public static void main(String[] args) {

		Rolodex r = new Rolodex();

		testAddCard(r);
		System.out.println('Size: ' + r.size() + ' == 9');
		testRemoveAllCards(r);
		System.out.println(r);
		testRemoveCard(r);
		System.out.println(r);
		testNextSeparator(r);
		System.out.println(r);
	}

	static void testAddCard(Rolodex r) {
		System.out.println('\nTest for addCard function\n');
		r.addCard("Bob", "123");
		r.addCard("zBob", "23");
		r.addCard("Monitor", "3");
		r.addCard("Monitor", "4");
		r.addCard("zMonitor", "5");

		try {
			r.addCard('zBob', '55');
		} catch (Exception e) {
			System.out.println('Test caught ' + e);
		}
		
		try {
			r.addCard(null, null);
		} catch (Exception e) {
			System.out.println('Test caught ' + e);
		}
	}

	static void testRemoveAllCards(Rolodex r) {
		System.out.println('\nTest for removeAllCards function\n');
		r.removeAllCards("Bob");
		r.removeAllCards("zBob");
		r.removeAllCards("Monitor");
		
		try {
			r.removeAllCards('Bobb');
		} catch (Exception e) {
			System.out.println('Test caught ' + e);
		}
	}

	static void testRemoveCard(Rolodex r) {
		System.out.println('\nTest for removeCard function\n');
		r.addCard("Bob", "123");
		r.addCard("zBob", "123");
		r.addCard("zBob", "23");
		r.addCard("Monitor", "3");
		r.addCard("Monitor", "4");

		r.removeCard("Bob", "123");
		r.removeCard("zBob", "23");
		r.removeCard("Monitor", "4");
		r.removeCard("Monitor", "5");
		
		try {
			r.removeCard("zBob", "3");
		} catch (Exception e) {
			System.out.println('Test caught ' + e);
		}
		
		try {
			r.removeCard('Monitor', '123');
		} catch (Exception e) {
			System.out.println('Test caught ' + e);
		}
	}

	static void testNextSeparator(Rolodex r) {
		System.out.println('\nTest for nextSeparator function\n');
		r.addCard('Bobb', '123');
		System.out.println(r.currentEntryToString());
		r.nextSeparator();
		System.out.println(r.currentEntryToString());
		
		for (int i = 0; i < 5; i++) {
			r.nextEntry();
			System.out.println(r.currentEntryToString());
		}
	}
}