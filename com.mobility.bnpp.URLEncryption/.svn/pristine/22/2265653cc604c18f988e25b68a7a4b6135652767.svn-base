package com.mobility.bnpp.urlencryption.corecode;
public class MR1Utils {

	public static String seed = null;

	public static String getEncSeed() {
		if (seed == null) {
			seed = getDynamicValue(MR1Constants.EIGHT);
		}
		return seed;
	}

	public static String getDynamicValue(int size) {

		byte[] randomBytes = new byte[size];
		if (size == MR1Constants.EIGHT) {
			// 105,110,95,108,111,103,105,110
			byte[] bytearray = new byte[] { (byte) 0x69, (byte) 0x6e,
					(byte) 0x5f, (byte) 0x6c, (byte) 0x6f, (byte) 0x67,
					(byte) 0x69, (byte) 0x6e };

			randomBytes = bytearray;
		} else if (size == MR1Constants.ELEVEN) {
			// 105,109,95,105,110,95,108,111,103,105,110
			byte[] bytearray = new byte[] { (byte) 0x69, (byte) 0x6d,
					(byte) 0x5f, (byte) 0x69, (byte) 0x6e, (byte) 0x5f,
					(byte) 0x6c, (byte) 0x6f, (byte) 0x67, (byte) 0x69,
					(byte) 0x6e };

			randomBytes = bytearray;
		}

		int start = 0;
		char[] random = new char[size];
		while (start < size) {
			for (int counter = 0; counter < 255; counter++) {
				if ((byte) counter == randomBytes[start]) {
					random[start] = (char) counter;
				}
			}
			start++;
		}

		return (String.valueOf(random));

	}
}
