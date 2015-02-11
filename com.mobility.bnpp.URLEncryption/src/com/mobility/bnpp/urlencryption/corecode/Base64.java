package com.mobility.bnpp.urlencryption.corecode;

public class Base64 {
	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final int MASK_0X3F = 0x3F;
	private static final int MASK_0XFF = 0xFF;
    private static int[]  toInt   = new int[MR1Constants.HUNDRED_AND_TWENTY_EIGHT];

    static {
        for(int i=0; i< ALPHABET.length; i++){
            toInt[ALPHABET[i]]= i;
        }
    }

    /**
     * Translates the specified byte array into Base64 string.
     *
     * @param buf the byte array (not null)
     * @return the translated Base64 string (not null)
     */
    public static String encode(byte[] buf){
        int size = buf.length;
        char[] ar = new char[((size + MR1Constants.TWO) / MR1Constants.THREE) * MR1Constants.FOUR];
        int a = 0;
        int i=0;
        while(i < size){
            byte b0 = buf[i++];
            byte b1 = (i < size) ? buf[i++] : 0;
            byte b2 = (i < size) ? buf[i++] : 0;

            int mask = MASK_0X3F;
            ar[a++] = ALPHABET[(b0 >> 2) & mask];
            ar[a++] = ALPHABET[((b0 << 4) | ((b1 & MASK_0XFF) >> 4)) & mask];
            ar[a++] = ALPHABET[((b1 << 2) | ((b2 & MASK_0XFF) >> 6)) & mask];
            ar[a++] = ALPHABET[b2 & mask];
        }
        switch(size % MR1Constants.THREE){
            case 1: ar[--a]  = '=';  break;
            case 2: ar[--a]  = '=';  break;
            default: break;
        }
        return new String(ar);
    }

    /**
     * Translates the specified Base64 string into a byte array.
     *
     * @param s the Base64 string (not null)
     * @return the byte array (not null)
     */
    public static byte[] decode(String s){
        int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
        byte[] buffer = new byte[s.length()*MR1Constants.THREE/MR1Constants.FOUR - delta];
        int mask = MASK_0XFF;
        int index = 0;
        for(int i=0; i< s.length(); i+=MR1Constants.FOUR){
            int c0 = toInt[s.charAt( i )];
            int c1 = toInt[s.charAt( i + 1)];
            buffer[index++]= (byte)(((c0 << 2) | (c1 >> MR1Constants.FOUR)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c2 = toInt[s.charAt( i + 2)];
            buffer[index++]= (byte)(((c1 << MR1Constants.FOUR) | (c2 >> 2)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c3 = toInt[s.charAt( i + MR1Constants.THREE )];
            buffer[index++]= (byte)(((c2 << MR1Constants.SIX) | c3) & mask);
        }
        return buffer;
    } 
}
