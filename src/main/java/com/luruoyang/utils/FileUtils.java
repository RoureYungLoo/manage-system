package com.luruoyang.utils;

public class FileUtils {
  public static String getDirsByFileName(String filename) {
    int filenameHash = filename.hashCode();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      int subDir = filenameHash & 15;
      sb.append(subDir).append("/");
      filenameHash >>= 4;
    }
    return sb.toString();
  }
}
