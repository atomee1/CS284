package Homework;

/*
* William Ee
* CS 284-E
* I pledge my honor that I have abided by the Stevens Honor System.
*/

public class BinaryNumber {
	private int[] data;
    private int length;
    
    public void prepend(int amount) {
		int len = this.getLength() + amount;
		int[] prep = new int[len];

		for (int k = amount; k < len; k++) {
			prep[k] = this.getDigit(k - amount);

		}
		
		this.data = prep;
		this.length += amount;
	}
    
	public BinaryNumber(int length) throws Exception {
		if (length < 0 || length == 0) {
			throw new Exception("Error: Length of sequence must be positive integer");
		}
		
		int[] iHold = new int[length];
		this.length = length;
		this.data = iHold;
	}

	public BinaryNumber(String str) throws Exception {
		int len = str.length();
		int[] sHold = new int[len];
		
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			int num = Character.getNumericValue(c);
			if ((len == 0 || len < 0) || (num != 0 && num != 1)) {
				throw new Exception("Error: String can only have positive integer length + contain binary numbers");
			}
			
			sHold[i] = num;
		}

		this.length = len;
		this.data = sHold;
	}

	public int getLength() {
		this.length = this.data.length;
		return this.length;
	}

	public int getDigit(int index) {
		try {
			return this.data[index];
		} catch (ArrayIndexOutOfBoundsException exception) {
			throw new IllegalArgumentException("Error: Index " + index + " out of bounds");
		}
	}

	public int[] getInnerArray() {
		return this.data;
	}

	public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2) throws Exception {
		if (bn1.getLength() != bn2.getLength()) {
			throw new Exception("Error: Lengths of sequences must be equal");
		}
		
		int[] bwor = new int[bn1.getLength()];
		
		for (int i = 0; i < bn1.getLength(); i++) {
			if (bn1.getDigit(i) == 1 || bn2.getDigit(i) == 1) {
				bwor[i] = 1;
			} else {
				bwor[i] = 0;
			}
		}
		
		return bwor;
	}

	public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2) throws Exception {
		if (bn1.getLength() != bn2.getLength()) {
			throw new Exception("Error: Lengths of sequences must be equal");
		}
		
		int[] bwand = new int[bn1.getLength()];
		
		for (int i = 0; i < bn1.getLength(); i++) {
			if (bn1.getDigit(i) == 1 && bn2.getDigit(i) == 1) {
				bwand[i] = 1;
			} else {
				bwand[i] = 0;
			}
		}
		return bwand;
	}

	public void bitShift(int direction, int amount) throws Exception {
		int add = this.getLength() + amount;
		int sub = this.getLength() - amount;

		if (amount <= 0) {
			throw new Exception("Error: Amount must be positive integer");
		}
		
		if (direction == 1) {
			int[] shift = new int[sub];
			
			for (int i = 0; i < sub; i++) {
				shift[i] = this.getDigit(i);
			}
			
			this.data = shift;
		} else if (direction == -1) {
			int[] shift = new int[add];
			
			for (int i = 0; i < this.getLength(); i++) {
				shift[i] = this.getDigit(i);
			}
			
			for (int i = this.getLength(); i < add; i++) {
				shift[i] = 0;
			}
			
			this.data = shift;
		} else {
			throw new Exception("Error: Direction must be -1 or 1");
		}
	}

	public void add(BinaryNumber aBinaryNumber) {
		if (this.length > aBinaryNumber.getLength()) {
			int len = this.length - aBinaryNumber.getLength();
			
			aBinaryNumber.prepend(len);
		} else if (this.length < aBinaryNumber.getLength()) {
			int len = aBinaryNumber.getLength() - this.length;
			
			this.prepend(len);
		}
		
		int[] addBinary = new int[this.length];
		int holder = 0;
		
		for (int i = this.length - 1; i > -1; i--) {
			int adder = this.data[i] + aBinaryNumber.getDigit(i) + holder;
			
			if (adder == 0) {
				addBinary[i] = 0;
				holder = 0;
			} else if (adder == 1) {
				addBinary[i] = 1;
				holder = 0;
			} else if (adder == 2) {
				addBinary[i] = 0;
				holder = 1;
			} else if (adder == 3) {
				addBinary[i] = 1;
				holder = 1;
			}

		}
		this.data = addBinary;
		this.length = addBinary.length;

		if (holder == 1) {
			this.prepend(1);
			this.data[0] = 1;
			this.length += 1;
		}

	}

	public String toString() {
		String str = new String();
		
		for (int i = 0; i < this.getLength(); i++) {
			str += this.getDigit(i);
		}
		return str;
	}

	public int toDecimal() {
		int dec = Integer.parseInt(this.toString(), 2);
		
		return dec;
	}
}