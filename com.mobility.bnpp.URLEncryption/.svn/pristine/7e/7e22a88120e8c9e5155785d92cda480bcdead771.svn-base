package com.mobility.bnpp.urlencryption.corecode;

import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncoderDecoder {
	
	private static final String TAG = EncoderDecoder.class.getSimpleName();
	private static final String DESEDE_ENCRYPTION_SCHEME = "AES";
	
    //Function to encrypt the data
    public static String encrypt(String seed, String cleartext) {
    	try {
    		if(null == cleartext || "".equals(cleartext.trim())){
    			return "";
    		} else {
    			byte[] rawKey = getRawKey(seed.getBytes());
        		byte[] result = encrypt(rawKey, cleartext.getBytes());
        		return new String(Base64.encode(toHex(result).getBytes()));
    		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    //Function to decrypt the data
    public static String decrypt(String seed, String encrypted) {
    	String decodedStr = "";
    	try {
    		if(null == encrypted || "".equals(encrypted.trim())){
    			return "";
    		} else {
    			decodedStr = new String(Base64.decode(encrypted));
    			byte[] rawKey = getRawKey(seed.getBytes());
        		byte[] enc = toByte(decodedStr);
        		byte[] result = decrypt(rawKey, enc);
        		return new String(result);
    		}
		} catch (Exception e) {
			if(!MR1Constants.BLANK.equals(decodedStr)) {
				return decodedStr;
			}
			return null;
		}
	}

    // function to generate the secret key
    private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(DESEDE_ENCRYPTION_SCHEME);
		SecureRandom sr = SecureRandom.getInstance( "SHA1PRNG", "Crypto" );
		sr.setSeed(seed);
	    kgen.init(128, sr); // 192 and 256 bits may not be available
	    SecretKey skey = kgen.generateKey();
	    byte[] raw = skey.getEncoded();
	    return raw;
	}
    
    //Function defining the mode and the algo to be used in encryption
    private static byte[] encrypt(byte[] raw, byte[] clear) {
    	try {
    		SecretKeySpec skeySpec = new SecretKeySpec(raw, DESEDE_ENCRYPTION_SCHEME);
    		Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
    		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    		byte[] encrypted = cipher.doFinal(clear);
    		return encrypted;
		} catch (Exception e) {
			return null;
		}
	}

  //Function defining the mode and the algo to be used in decryption
    private static byte[] decrypt(byte[] raw, byte[] encrypted) {
    	try {
    		SecretKeySpec skeySpec = new SecretKeySpec(raw, DESEDE_ENCRYPTION_SCHEME);
    		Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
    	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    	    byte[] decrypted = cipher.doFinal(encrypted);
    		return decrypted;
		} catch (Exception e) {
			return null;
		}
	}

    public static void main(String[] args) {
    	
    	Security.addProvider(new org.apache.harmony.security.provider.crypto.CryptoProvider());
    	String r = encrypt(MR1Utils.getEncSeed(), "bnppf-ios-pe-wrapper-secure/rest/ValidateStandingOrder");
    	String p = decrypt(MR1Utils.getEncSeed(), "MzREMUNBM0RCOTYxQjAxQzZFM0NCQ0NGQzczOENBQUU4NkRENjBEOEIwMEIwNTkxRUFDQjhGNDEyRDE1NDFFMzQxNEVBMkREOUFDQzk2NkRGMjcwREY1MDhFMTA5ODdFMENBMUJDM0YyNjRGMUIyNjI3RTlEM0ZCMTk4NjI0OTE=");
    	System.out.println(p);
	}
    
    // Converting the cipher text into hex
    public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}
    
	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}
	
	public static byte[] toByte(String hexString) {
		int len = hexString.length()/2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
		}
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null){
			return "";
		}
			
		StringBuffer result = new StringBuffer(2*buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}
	
	private final static String HEX = "0123456789ABCDEF";
	
	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
	}
}
