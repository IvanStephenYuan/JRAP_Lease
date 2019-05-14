/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@7279c409$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.util;

import java.nio.charset.Charset;

public class Base64 {

    /**
     * how we separate lines, e.g. \n, \r\n, \r etc.
     */
    private String lineSeparator = System.getProperty("line.separator");

    /**
     * max chars per line, excluding lineSeparator.  A multiple of 4.
     */
    private int lineLength = 108;

    /* constructor */
    public Base64() {
    }

    /**
     * Encode an arbitrary array of bytes as Base64 printable ASCII.
     * It will be broken into lines of 72 chars each.  The last line is not
     * terminated with a line separator.
     * The output will always have an even multiple of data characters,
     * exclusive of \n.  It is padded out with =.
     */
    public String encode(byte[] b) {
        // Each group or partial group of 3 bytes becomes four chars
        // covered quotient
        int outputLength = ((b.length + 2) / 3) * 4;

        // account for trailing newlines, on all but the very last line
        if (lineLength != 0) {
            int lines = (outputLength + lineLength - 1) / lineLength - 1;
            if (lines > 0) {

                outputLength += lines * lineSeparator.length();
            }
        }

        // must be local for recursion to work.
        StringBuffer sb = new StringBuffer(outputLength);

        // must be local for recursion to work.
        int linePos = 0;

        // first deal with even multiples of 3 bytes.
        int len = (b.length / 3) * 3;
        int leftover = b.length - len;
        for (int i = 0; i < len; i += 3) {
            // Start a new line if next 4 chars won't fit on the current line
            // We can't encapsulete the following code since the variable need to
            // be local to this incarnation of encode.
            linePos += 4;
            if (linePos > lineLength) {
                if (lineLength != 0) {
                    sb.append(lineSeparator);
                }
                linePos = 4;
            }

            // get next three bytes in unsigned form lined up,
            // in big-endian order
            int combined = b[i + 0] & 0xff;
            combined <<= 8;
            combined |= b[i + 1] & 0xff;
            combined <<= 8;
            combined |= b[i + 2] & 0xff;

            // break those 24 bits into a 4 groups of 6 bits,
            // working LSB to MSB.
            int c3 = combined & 0x3f;
            combined >>>= 6;
            int c2 = combined & 0x3f;
            combined >>>= 6;
            int c1 = combined & 0x3f;
            combined >>>= 6;
            int c0 = combined & 0x3f;

            // Translate into the equivalent alpha character
            // emitting them in big-endian order.
            sb.append(valueToChar[c0]);
            sb.append(valueToChar[c1]);
            sb.append(valueToChar[c2]);
            sb.append(valueToChar[c3]);
        }

        // deal with leftover bytes
        switch (leftover) {
            case 0:
            default:
                // nothing to do
                break;
            case 1:
                // One leftover byte generates xx==
                // Start a new line if next 4 chars won't fit on the current line
                linePos += 4;
                if (linePos > lineLength) {

                    if (lineLength != 0) {
                        sb.append(lineSeparator);
                    }
                    linePos = 4;
                }
                // Handle this recursively with a faked complete triple.
                // Throw away last two chars and replace with ==
                sb.append(encode(new byte[]{b[len], 0, 0}
                ).substring(0, 2));
                sb.append("==");
                break;

            case 2:

                // Two leftover bytes generates xxx=
                // Start a new line if next 4 chars won't fit on the current line
                linePos += 4;
                if (linePos > lineLength) {
                    if (lineLength != 0) {
                        sb.append(lineSeparator);
                    }
                    linePos = 4;
                }
                // Handle this recursively with a faked complete triple.
                // Throw away last char and replace with =
                sb.append(encode(new byte[]{b[len], b[len + 1], 0}
                ).substring(0, 3));
                sb.append("=");
                break;

        } // end switch;

        if (outputLength != sb.length()) {
            System.out.println("oops: minor program flaw: output length mis-estimated");
            System.out.println("estimate:" + outputLength);
            System.out.println("actual:" + sb.length());
        }
        return sb.toString();
    } // end encode

    /**
     * decode a well-formed complete Base64 string back into an array of bytes.
     * It must have an even multiple of 4 data characters (not counting \n),
     * padded out with = as needed.
     */
    public byte[] decode(String s) {

        // estimate worst case size of output array, no embedded newlines.
        byte[] b = new byte[(s.length() / 4) * 3];

        // tracks where we are in a cycle of 4 input chars.
        int cycle = 0;

        // where we combine 4 groups of 6 bits and take apart as 3 groups of 8.
        int combined = 0;

        // how many bytes we have prepared.
        int j = 0;
        // will be an even multiple of 4 chars, plus some embedded \n
        int len = s.length();
        int dummies = 0;
        for (int i = 0; i < len; i++) {

            int c = s.charAt(i);
            int value = (c <= 255) ? charToValue[c] : IGNORE;
            // there are two magic values PAD (=) and IGNORE.
            switch (value) {
                case IGNORE:

                    // e.g. \n, just ignore it.
                    break;

                case PAD:
                    value = 0;
                    dummies++;
                    // fallthrough
                default:

                    /* regular value character */
                    switch (cycle) {
                        case 0:
                            combined = value;
                            cycle = 1;
                            break;

                        case 1:
                            combined <<= 6;
                            combined |= value;
                            cycle = 2;
                            break;

                        case 2:
                            combined <<= 6;
                            combined |= value;
                            cycle = 3;
                            break;

                        case 3:
                            combined <<= 6;
                            combined |= value;
                            // we have just completed a cycle of 4 chars.
                            // the four 6-bit values are in combined in big-endian order
                            // peel them off 8 bits at a time working lsb to msb
                            // to get our original 3 8-bit bytes back

                            b[j + 2] = (byte) combined;
                            combined >>>= 8;
                            b[j + 1] = (byte) combined;
                            combined >>>= 8;
                            b[j] = (byte) combined;
                            j += 3;
                            cycle = 0;
                            break;
                    }
                    break;
            }
        } // end for
        if (cycle != 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "Input to decode not an even multiple of 4 characters; pad with =.");
        }
        j -= dummies;
        if (b.length != j) {
            byte[] b2 = new byte[j];
            System.arraycopy(b, 0, b2, 0, j);
            b = b2;
        }
        return b;

    } // end decode

    /**
     * determines how long the lines are that are generated by encode.
     * Ignored by decode.
     *
     * @param length 0 means no newlines inserted. Must be a multiple of 4.
     */
    public void setLineLength(int length) {
        this.lineLength = (length / 4) * 4;
    }

    /**
     * How lines are separated.
     * Ignored by decode.
     *
     * @param lineSeparator may be "" but not null.
     *                      Usually contains only a combination of chars \n and \r.
     *                      Could be any chars not in set A-Z a-z 0-9 + /.
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * letter of the alphabet used to encode binary values 0..63
     */
    static final char[] valueToChar = new char[64];

    /**
     * binary value encoded by a given letter of the alphabet 0..63
     */
    static final int[] charToValue = new int[256];

    /**
     * Marker value for chars we just ignore, e.g. \n \r high ascii
     */
    static final int IGNORE = -1;

    /**
     * Marker for = trailing pad
     */
    static final int PAD = -2;

    static
        /* initialise valueToChar and charToValue tables */ {
        // build translate valueToChar table only once.
        // 0..25 -> 'A'..'Z'
        for (int i = 0; i <= 25; i++) {
            valueToChar[i] = (char) ('A' + i);
            // 26..51 -> 'a'..'z'
        }
        for (int i = 0; i <= 25; i++) {
            valueToChar[i + 26] = (char) ('a' + i);
            // 52..61 -> '0'..'9'
        }
        for (int i = 0; i <= 9; i++) {
            valueToChar[i + 52] = (char) ('0' + i);
        }
        valueToChar[62] = '+';
        valueToChar[63] = '/';

        // build translate charToValue table only once.
        for (int i = 0; i < 256; i++) {
            charToValue[i] = IGNORE; // default is to ignore
        }

        for (int i = 0; i < 64; i++) {
            charToValue[valueToChar[i]] = i;
        }

        charToValue['='] = PAD;
    }

    /**
     * used to disable test driver
     */
    private static final boolean debug = false;

    /**
     * debug display array
     */
    public static void show(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            System.out.print(Integer.toHexString(b[i] & 0xff) + " ");
        }
        System.out.println();
    }

    /**
     * debug display array
     */
    public static void display(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            System.out.print((char) b[i]);
        }
        System.out.println();
    }

    /**
     * Translates the specified Base64 string (as per Preferences.get(byte[]))
     * into a byte array.
     *
     * @throw IllegalArgumentException if <tt>s</tt> is not a valid Base64
     * string.
     */
    public static byte[] base64ToByteArray(String s) {
        return base64ToByteArray(s, false);
    }

    /**
     * Translates the specified character, which is assumed to be in the
     * "Base 64 Alphabet" into its equivalent 6-bit positive integer.
     *
     * @throw IllegalArgumentException or ArrayOutOfBoundsException if
     * c is not in the Base64 Alphabet.
     */
    private static int base64toInt(char c, byte[] alphaToInt) {
        int result = alphaToInt[c];
        if (result < 0) {
            throw new IllegalArgumentException("Illegal character " + c);
        }
        return result;
    }

    /**
     * This array is a lookup table that translates unicode characters
     * drawn from the "Base64 Alphabet" (as specified in Table 1 of RFC 2045)
     * into their 6-bit positive integer equivalents.  Characters that
     * are not in the Base64 alphabet but fall within the bounds of the
     * array are translated to -1.
     */
    private static final byte base64ToInt[] = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54,
            55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
            35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
    };

    /**
     * Translates the specified "aternate representation" Base64 string
     * into a byte array.
     *
     * @throw IllegalArgumentException or ArrayOutOfBoundsException
     * if <tt>s</tt> is not a valid alternate representation
     * Base64 string.
     */
    public static byte[] altBase64ToByteArray(String s) {
        return base64ToByteArray(s, true);
    }

    private static byte[] base64ToByteArray(String s, boolean alternate) {
        byte[] alphaToInt = (alternate ? altBase64ToInt : base64ToInt);
        int sLen = s.length();
        int numGroups = sLen / 4;
        if (4 * numGroups != sLen) {
            throw new IllegalArgumentException(
                    "String length must be a multiple of four.");
        }
        int missingBytesInLastGroup = 0;
        int numFullGroups = numGroups;
        if (sLen != 0) {
            if (s.charAt(sLen - 1) == '=') {
                missingBytesInLastGroup++;
                numFullGroups--;
            }
            if (s.charAt(sLen - 2) == '=') {
                missingBytesInLastGroup++;
            }
        }
        byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];

        // Translate all full groups from base64 to byte array elements
        int inCursor = 0, outCursor = 0;
        for (int i = 0; i < numFullGroups; i++) {
            int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
            int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
            int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
            int ch3 = base64toInt(s.charAt(inCursor++), alphaToInt);
            result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));
            result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
            result[outCursor++] = (byte) ((ch2 << 6) | ch3);
        }

        // Translate partial group, if present
        if (missingBytesInLastGroup != 0) {
            int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
            int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
            result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));

            if (missingBytesInLastGroup == 1) {
                int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
                result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
            }
        }
        // assert inCursor == s.length()-missingBytesInLastGroup;
        // assert outCursor == result.length;
        return result;
    }

    /**
     * This array is the analogue of base64ToInt, but for the nonstandard
     * variant that avoids the use of uppercase alphabetic characters.
     */
    private static final byte altBase64ToInt[] = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1,
            2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57,
            58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33,
            34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 22, 23, 24, 25
    };

    /**
     * test driver
     */
    public static void main(String[] args) {
        if (debug) {
            byte[] a = {(byte) 0xfc, (byte) 0x0f, (byte) 0xc0};
            byte[] b = {(byte) 0x03, (byte) 0xf0, (byte) 0x3f};
            byte[] c = {(byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] d = {(byte) 0xff, (byte) 0xff, (byte) 0xff};
            byte[] e = {(byte) 0xfc, (byte) 0x0f, (byte) 0xc0, (byte) 1};
            byte[] f = {(byte) 0xfc, (byte) 0x0f, (byte) 0xc0, (byte) 1, (byte) 2};
            byte[] g = {(byte) 0xfc, (byte) 0x0f, (byte) 0xc0, (byte) 1, (byte) 2, (byte) 3};
            byte[] h = "AAAAAAAAAAB".getBytes();

            show(a);
            show(b);
            show(c);
            show(d);
            show(e);
            show(f);
            show(g);
            show(h);
            Base64 b64 = new Base64();
            show(b64.decode(b64.encode(a)));
            show(b64.decode(b64.encode(b)));
            show(b64.decode(b64.encode(c)));
            show(b64.decode(b64.encode(d)));
            show(b64.decode(b64.encode(e)));
            show(b64.decode(b64.encode(f)));
            show(b64.decode(b64.encode(g)));
            show(b64.decode(b64.encode(h)));
            b64.setLineLength(8);
            show((b64.encode(h)).getBytes());
        }
    } // end main

    /**
     * 对指定的字符串进行base64编码，编码过程中将使用默认的字符集
     *
     * @param str 需要进行base64编码的字符串
     * @return 编码后的字符串
     */
    public static String encodeStr(String str) {
        return new Base64().encode(str.getBytes());
    }

    /**
     * 对指定的字符串进行base64编码，编码过程中将采用指定的字符集
     *
     * @param str     需要进行base64编码的字符串
     * @param charset 编码过程中使用的字符集
     * @return 编码后的字符串
     */
    public static String encodeStr(String str, Charset charset) {
        return new Base64().encode(str.getBytes(charset));
    }

    /**
     * 对base64编码后的字符串进行解码，解码过程中将采用默认的字符集
     *
     * @param base64Str 需要进行解码的base64字符串
     * @return 解码后的字符串
     */
    public static String decodeStr(String base64Str) {
        byte[] bytes = new Base64().decode(base64Str);
        return new String(bytes);
    }

    /**
     * 对base64编码后的字符串进行解码，解码过程中将采用指定的字符集
     *
     * @param base64Str 需要进行解码的base64字符串
     * @param charset   解码过程中需要使用的字符集
     * @return 解码后的字符串
     */
    public static String decodeStr(String base64Str, Charset charset) {
        byte[] bytes = new Base64().decode(base64Str);
        return new String(bytes, charset);
    }

} // end Base64
