package org.apache.minibase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Bytes {
  public static byte[] toBytes(byte b) {
    return new byte[] { b };
  }

  public static byte[] toBytes(String s) throws IOException {
    if (s == null) return new byte[0];
    return s.getBytes("UTF-8");
  }

  public static byte[] toBytes(int x) {
    byte[] b = new byte[4];
    b[3] = (byte) (x & 0xFF);
    b[2] = (byte) ((x >> 8) & 0xFF);
    b[1] = (byte) ((x >> 16) & 0xFF);
    b[0] = (byte) ((x >> 24) & 0xFF);
    return b;
  }

  public static byte[] toBytes(long x) {
    byte[] b = new byte[8];
    b[7] = (byte) (x & 0xFF);
    b[6] = (byte) ((x >> 8) & 0xFF);
    b[5] = (byte) ((x >> 16) & 0xFF);
    b[4] = (byte) ((x >> 24) & 0xFF);
    b[3] = (byte) ((x >> 32) & 0xFF);
    b[2] = (byte) ((x >> 40) & 0xFF);
    b[1] = (byte) ((x >> 48) & 0xFF);
    b[0] = (byte) ((x >> 56) & 0xFF);
    return b;
  }

  public static byte[] toBytes(byte[] a, byte[] b) {
    if (a == null) return b;
    if (b == null) return a;
    byte[] result = new byte[a.length + b.length];
    System.arraycopy(a, 0, result, 0, a.length);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }

  public static int toInt(byte[] a) {
    return (a[0] << 24) & 0xFF000000 | (a[1] << 16) & 0x00FF0000 | (a[2] << 8) & 0x0000FF00
        | (a[3] << 0) & 0x000000FF;
  }

  public static long toLong(byte[] a) {
    long x = 0;
    for (int i = 0; i < 8; i++) {
      int j = (7 - i) << 3;
      x |= ((0xFFL << j) & ((long) a[i] << j));
    }
    return x;
  }

  public static byte[] slice(byte[] buf, int offset, int len) throws IOException {
    if (buf == null) {
      throw new IOException("buffer is null");
    }
    if (offset < 0 || len < 0) {
      throw new IOException("Invalid offset: " + offset + " or len: " + len);
    }
    if (offset + len > buf.length) {
      throw new IOException("buffer overflow, offset: " + offset + ", len: " + len);
    }
    byte[] result = new byte[len];
    System.arraycopy(buf, offset, result, 0, len);
    return result;
  }

  public static int hash(byte[] key) {
    if (key == null) return 0;
    int h = 1;
    for (int i = 0; i < key.length; i++) {
      h = (h << 5) + h + key[i];
    }
    return h;
  }

  public static int compare(byte[] a, byte[] b) {
    if (a == b) return 0;
    if (a == null) return -1;
    if (b == null) return 1;
    for (int i = 0, j = 0; i < a.length && j < b.length; i++, j++) {
      int x = a[i] & 0xFF;
      int y = b[i] & 0xFF;
      if (x != y) {
        return x - y;
      }
    }
    return a.length - b.length;
  }

}
