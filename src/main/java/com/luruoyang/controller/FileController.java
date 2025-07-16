package com.luruoyang.controller;


import com.luruoyang.properties.FileUploadProperties;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@CrossOrigin("*")
@RestController
@RequestMapping("/file")
//@RequestMapping
@Slf4j
public class FileController {
  @Autowired
  FileUploadProperties fileUploadProperties;

  @PostMapping("/upload")
  public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
    //System.out.println(photo.getName()); // input标签的name值

    String originalFilename = file.getOriginalFilename(); // 文件名

    if (originalFilename == null || originalFilename.isEmpty()) {
      throw new RuntimeException("originalFilename is empty");
    }

    String subDirs = getDir(originalFilename);

//    File destPath = new File(fileUploadProperties.getPath(), subDirs);
    String parent = System.getProperty("user.dir");
    parent = parent + File.separator + fileUploadProperties.getPath();
    File destPath = new File(parent, subDirs);

    if (!destPath.exists()) {
      if (!destPath.mkdirs()) {
        throw new RuntimeException("文件夹创建失败");
      } else {
        log.info("文件夹创建成功, 请使用live-server打开{}", parent);
      }
    } else {
      log.info("文件夹已经存在, 请使用live-server打开{}", parent);
    }


    String newName = UUID.randomUUID() + "_" + originalFilename;
    File dest = new File(destPath, newName);
    try {
      file.transferTo(dest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String imgUrl = String.format("%s%s%s", fileUploadProperties.getLiverServer(), subDirs, newName);
    log.info("file uploaded access path:  {}", imgUrl);

    return Result.success(imgUrl);
  }

  /* 根据文件名hash, 得到多级目录 */
  private String getDir(String filename) {
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
