package com.luruoyang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FileUploadTest {

  @Test
  public void testFileUpload() throws IOException {

    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    File file = new File("aaa.txt");

    if (!file.exists()) file.createNewFile();
    System.out.println(file.getName());
    System.out.println(file.getAbsolutePath());
    System.out.println(file.getCanonicalPath().toString());
  }
}
